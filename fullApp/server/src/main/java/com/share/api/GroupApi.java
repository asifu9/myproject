package com.share.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.base.domain.Photo;
import com.base.domain.User;
import com.base.enums.PrivacyStatus;
import com.base.util.TimeDifference;
import com.share.domain.Group;
import com.share.domain.GroupMembers;
import com.share.exception.AppException;
import com.share.feeds.api.FeedApi;
import com.share.feeds.service.FeedService;
import com.share.master.data.service.UserService;
import com.share.photo.service.PhotoService;
import com.share.services.GroupLikeService;
import com.share.services.GroupMembersService;
import com.share.services.GroupService;
import com.share.util.FileThumbnails;



@RestController
@RequestMapping("/Group")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupApi {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private UserService userService;


	@Autowired
	private FeedService feedService;

	@Autowired
	private FeedApi feedApi;
	@Autowired
	private GroupService groupService;
	@Autowired
	private FileThumbnails fileThumbs;
	@Autowired
	private GroupLikeService groupLikeService;
	@Autowired
	private GroupMembersService groupMemberService;


	@Value("${filePath}")
	private String filePathConfig;

	@RequestMapping(method=RequestMethod.GET,value="/name/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Group> getByName(
			@PathVariable(value="name") String name) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Group list=groupService.getByName(name); 
		System.out.println(list);

		return new ResponseEntity<Group>( convertFull(list),headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.GET,value="/listuser/{wallId}/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<User>> fetchAllUsersByWallId(@PathVariable(value="wallId") String wallId,
			@PathVariable(value="name") String name) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<User> userss=userService.listByWallId(wallId);
		Group list=groupService.getByName(name);
		Set<String> members = list.getMembers();
		members.add(list.getCreatedBy());
		List<User> finalUsers=new ArrayList<>();
		for(User u:userss){
			if(members!=null && !members.contains(u.getId())){
				if(u.getPhotoId()!=null){
					u.setPhoto(photoService.getById(u.getPhotoId()));
				}
				finalUsers.add(u);
			}
		}
		System.out.println(list);

		return new ResponseEntity<List<User>>( finalUsers,headers,HttpStatus.OK);
	}



	private String getStringDate(GroupMembers member){
		if(member.getStatus().equalsIgnoreCase("joined")){
			return new TimeDifference(Calendar.getInstance().getTime(), member.getAcceptedOn()).getDifferenceString();
		}else if(member.getStatus().equalsIgnoreCase("rejected")){
			return new TimeDifference(Calendar.getInstance().getTime(), member.getRejectedOn()).getDifferenceString();
		}else if(member.getStatus().equalsIgnoreCase("waiting")){
			return new TimeDifference(Calendar.getInstance().getTime(), member.getRequestedOn()).getDifferenceString();
		}
		return "";
	}

	@RequestMapping(method=RequestMethod.GET,value="/name/{name}/users",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Group> getByNameForUsers(
			@PathVariable(value="name") String name) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Group list=groupService.getByName(name); 
		Map<String,List<GroupMembers>> statusUsers=new HashMap<>();
		//now fetch waiting users
		List<GroupMembers> members=groupMemberService.listByGroupIdWithStatus(list.getId(), "Waiting");
		if(null!=members && members.size()>0){
			members.forEach(i->{
				try {
					i.setUser(userService.getByIdWithPhoto(i.getUserId()));
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i.setStatusDateStr(getStringDate(i));
			});
		}
		statusUsers.put("Waiting", members);
		//now fetch waiting users
		List<GroupMembers> membersJoined=groupMemberService.listByGroupIdWithStatus(list.getId(), "Joined");
		if(null!=membersJoined && membersJoined.size()>0){
			membersJoined.forEach(i->{
				try {
					i.setUser(userService.getByIdWithPhoto(i.getUserId()));
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i.setStatusDateStr(getStringDate(i));
			});
		}
		statusUsers.put("Joined", membersJoined);
		List<GroupMembers> membersRejected=groupMemberService.listByGroupIdWithStatus(list.getId(), "Rejected");
		if(null!=membersRejected && membersRejected.size()>0){
			membersRejected.forEach(i->{
				try {
					i.setUser(userService.getByIdWithPhoto(i.getUserId()));
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i.setStatusDateStr(getStringDate(i));
			});
		}
		statusUsers.put("Rejected", membersRejected);
		list.setStatusMembers(statusUsers);
		System.out.println(list);

		return new ResponseEntity<Group>(list,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.GET,value="/approve/{reqestId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<GroupMembers> approveUser(
			@PathVariable(value="reqestId") String reqestId

			) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		GroupMembers member=groupMemberService.getById(reqestId);
		Group g=groupService.getById(member.getGroupId()); 
		member.setAcceptedOn(Calendar.getInstance().getTime());
		member.setStatus("Joined");
		groupMemberService.saveOrUpdate(member);
		g.getMembers().add(member.getUserId());
		g.setMemberCount(g.getMembers().size());
		groupService.saveOrUpdate(g);

		synchronized (g) {
			List<GroupMembers> memb= groupMemberService.listByUserIdWithStatus(member.getUserId(), "Joined");
			if(memb!=null && !memb.isEmpty()){
				Update up=new Update();
				up.set("groupSubscriptionCount", memb.size());
				Query query = new Query();
				query.addCriteria(Criteria.where("id").is(member.getUserId()));
				userService.update(query, up);
			}
		}
		//System.out.println(list);

		return new ResponseEntity<GroupMembers>(member,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.GET,value="/join/{groupName}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<GroupMembers> groupJoiners(
			@PathVariable(value="groupName") String groupName,
			@PathVariable(value="userId") String userId

			) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Calendar cal=Calendar.getInstance();
		Group g=groupService.getByName(groupName); 
		System.out.println("privacy type " + g.getPrivacyStatus());
		if(g!=null && g.getPrivacyStatus()==PrivacyStatus.PUBLIC){
			//direct join
			g.getMembers().add(userId);

		}
		g.setMemberCount(g.getMembers().size());
		groupService.saveOrUpdate(g);

		//create entry in groupMember
		GroupMembers member=udpateGroupMemberEntry(g,userId,cal.getTime(),g.getPrivacyStatus());
		//System.out.println(list);

		return new ResponseEntity<GroupMembers>(member,headers,HttpStatus.OK);
	}

	private GroupMembers udpateGroupMemberEntry(Group g, String userId,Date date,PrivacyStatus status)throws AppException {

		String id=g.getId()+"_"+userId;
		GroupMembers member= groupMemberService.getById(id);
		if(member==null){
			member=new GroupMembers();
			member.setId(id);
			member.setGroupId(g.getId());
			member.setUserId(userId);
			member.setRequestedOn(date);
			if(status==PrivacyStatus.PUBLIC){
				member.setAcceptedOn(date);
				member.setStatus("Joined");

			}else if(status==PrivacyStatus.PRIVATE){
				member.setStatus("Waiting");
			}
			member.setStatusDateStr(new TimeDifference(Calendar.getInstance().getTime(),date).getDifferenceString());

		}
		member=groupMemberService.saveOrUpdate(member);

		return member;
	}

	@RequestMapping(method=RequestMethod.GET,value="/byuser/{wallId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Group>> getGroupsByWallUserId(
			@PathVariable(value="wallId") String wallId) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Group> list=groupService.listAll(wallId);
		System.out.println(list);
		List<Group> finalList=new ArrayList<>();
		if(null!=list && list.size()>0){
			list.forEach(i->{
				try {
					finalList.add(convertFull(i));
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}

		Collections.sort(finalList);
		return new ResponseEntity<List<Group>>( finalList,headers,HttpStatus.OK);
	}

	public Group convertFull(Group g) throws AppException{
		if(g.getProfilePhotoId()!=null){
			g.setProfilePhoto(photoService.getById(g.getProfilePhotoId()));
		}
		if(g.getPhotos()!=null && g.getPhotos().size()>0){
			for(String o:g.getPhotos()){
				g.getPhotoList().add(photoService.getById(o));
			}
		}
		if(g.getCreatedBy()!=null){
			g.setCreatedByUser(userService.getByIdWithPhoto(g.getCreatedBy()));
		}
		g.setCreatedByUser(userService.getByIdWithPhoto(g.getCreatedBy()));
		g.setCreatedOnStr(new TimeDifference(Calendar.getInstance().getTime(), g.getCreatedOn()).getDifferenceString());
		Map<String,GroupMembers> details=new HashMap<>();
		if(g.getMembers()!=null &&g.getMembers().size()>0){
			for(String i:g.getMembers()){
				GroupMembers data=groupMemberService.getById(g.getId()+"_"+i);
				data.setStatusDateStr(new TimeDifference(Calendar.getInstance().getTime(), data.getAcceptedOn()).getDifferenceString());
				details.put(i, data);
			}
		}
		g.setLikes(groupLikeService.getById(g.getId()));
		g.setMemberDetails(details);

		return g;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{userId}/{wallId}", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity uploadPhotoInFeed(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "wallId") String wallId,
			@RequestParam("file") MultipartFile[] uploadFiles, 
			@RequestParam(value="name")String name,
			@RequestParam(value="privacy")String privacy,
			@RequestParam(value="uniqueName")String uniqueName,
			@RequestParam(value="description") String description)  throws AppException{
		System.out.println(uploadFiles);
		System.out.println(name + " : " + privacy + " : " + description);
		String groupId=UUID.randomUUID().toString();
		List<String> photoIdProfile=new ArrayList<>();
		List<String> profilePhoto = createUploads(uploadFiles,userId,groupId,"000-");
		if(profilePhoto!=null && profilePhoto.size()>0){
			for(String i:profilePhoto){
				Photo p=new Photo();
				p.setAlbumId(groupId);
				p.setCreatedBy(userId);
				p.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
				p.setDescription("");
				p.setId(UUID.randomUUID().toString());
				try {
					p.setTenantId(userService.getById(userId).getTenantId());
				} catch (AppException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				p.setPath(i);
				try {
					p.setDetails(fileThumbs.createThumbNail(userId, groupId,i));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				photoService.saveOrUpdate(p);
				photoIdProfile.add(p.getId());
			}
		}
		List<String> photoIdDisplay=new ArrayList<>();
		List<String> displayPhotos = createUploads(uploadFiles,userId,groupId,"111-");
		if(displayPhotos!=null && displayPhotos.size()>0){
			for(String i:displayPhotos){
				Photo p=new Photo();
				p.setAlbumId(groupId);
				p.setCreatedBy(userId);
				p.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
				p.setDescription("");
				p.setId(UUID.randomUUID().toString());
				try {
					p.setTenantId(userService.getById(userId).getTenantId());
				} catch (AppException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				p.setPath(i);
				try {
					p.setDetails(fileThumbs.createThumbNail(userId, groupId,i));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				photoService.saveOrUpdate(p);
				photoIdDisplay.add(p.getId());
			}
		}
		Group g=new Group();
		g.setCreatedBy(userId);
		g.setCreatedOn(Calendar.getInstance().getTime());
		g.setId(groupId);
		g.setName(name);
		g.setGroupUniqueName(uniqueName);
		g.setDescription(description);
		g.setPrivacyStatus(getStatus(privacy));
		g.setPhotos(photoIdDisplay);
		g.setTenantId(wallId);
		if(photoIdProfile!=null && photoIdProfile.size()>0){
			g.setProfilePhotoId(photoIdProfile.get(0));
		}
		groupService.saveOrUpdate(g);
		synchronized (g) {
			List<Group> gg=groupService.listByCreatedByAndPkey(userId, wallId);
			if(gg!=null && !gg.isEmpty()){
				Update up=new Update();
				up.set("groupCount", gg.size());
				Query query = new Query();
				query.addCriteria(Criteria.where("id").is(userId));
				userService.update(query, up);
			}
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Group>(g, headers, HttpStatus.CREATED);
	}
	//    public FeedDisplay createFeed(List<Photo> photos,List<String> photoIds,String createdBy,String wallId){
	//    	Feed f=new Feed();
	//    	f.setCommentCount(0);
	//    	f.setCreatedBy(createdBy);
	//    	f.setCreatedOn(Calendar.getInstance().getTime());
	//    	f.setDescription("desc");
	//    	f.setFeedStatus(ShowHide.SHOW);
	//    	f.setFeedType(FeedType.WALL_PHOTO);
	//    	f.setId(UUID.randomUUID().toString());
	//    	f.setLikeCount(0);
	//    	f.setObjectId(photoIds);
	//    	f.setWallId(wallId);
	//    	feedService.saveOrUpdateDoc(f, wallId);
	//    	return feedApi.convertFeed(f);
	//    }
	//    @RequestMapping(method = RequestMethod.POST, value = "/photos/{userId}/{albumId}", 
	//    		consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
	//    		produces = MediaType.APPLICATION_JSON_VALUE)
	//    public ResponseEntity createAttachments(@PathVariable(value = "userId") String userId,
	//    		@PathVariable(value = "albumId") String albumId,
	//    		@RequestParam("file") MultipartFile[] uploadFiles)
	//             {
	//    	List<String> att = createUploads(uploadFiles,userId,albumId,);
	//    	att.forEach((i)->{
	//    		Photo p=new Photo();
	//    		p.setAlbumId(albumId);
	//    		p.setCreatedBy(userId);
	//    		p.setCreatedOn(Calendar.getInstance().getTime());
	//    		p.setDescription("");
	//    		p.setId(UUID.randomUUID().toString());
	//    		p.setPkey(userService.getById(userId).getPkey());
	//    		p.setPath(i);
	//    		try {
	//				fileThumbs.createThumbNail(userId, albumId,i);
	//			} catch (IOException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//    		photoService.saveOrUpdate(p);
	//    		
	//    	});
	//        return ResponseEntity.ok(att);
	//    }
	public List<String> createUploads(MultipartFile[] files,String userId,String albumId,String prefix){
		List<String> attachmentIds = new ArrayList<>();
		try {
			for (MultipartFile file : files) {
				String contentType = file.getContentType();
				String fileName = file.getOriginalFilename();
				long size = file.getSize();
				System.out.println(fileName);
				if(!fileName.startsWith(prefix)){
					continue;
				}
				fileName=fileName.replaceFirst(prefix, "");
				// extension validation
				int dotPos = fileName.lastIndexOf('.');
				if (dotPos < 0) {
					//throw exception
				}
				String fileExtension = fileName.substring(dotPos + 1, fileName.length());
				//                 if (!allowableExtensions.contains(fileExtension.toLowerCase())) {
				//                     throw new AttachmentExtensionNotAllowedException(
				//                             fileExtension, allowableExtensions);
				//                 }
				InputStream in = null;
				try {
					in = file.getInputStream();
					String attachmentId = create(userId,albumId,contentType, fileName, size, in);
					attachmentIds.add(attachmentId);
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		finally {
			for (MultipartFile file : files) {
				try {
					IOUtils.closeQuietly(file.getInputStream());
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return attachmentIds;
	}
	private String create (
			String userId,
			String albumId,
			String contentType,
			String fileName,
			long fileLength,
			InputStream fileContentStream)
	{
		try {
			String filePath=filePathConfig+userId+"/"+albumId;
			File f=new File(filePath);
			if(!f.isDirectory()){
				f.mkdirs();
			}
			String fileNamee=Calendar.getInstance().getTimeInMillis()+"_"+fileName;
			File targetFile = new File(filePath+"/"+""+fileNamee);
			OutputStream outStream = new FileOutputStream(targetFile);

			byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = fileContentStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			return fileNamee;

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "failure";

	}

	//    @RequestMapping(method = RequestMethod.POST, value = "/profile/photos/{userId}/{albumId}/{wallId}", 
	//    		consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
	//    		produces = MediaType.APPLICATION_JSON_VALUE)
	//    public ResponseEntity uploadProfilePhoto(@PathVariable(value = "userId") String userId,
	//    		@PathVariable(value = "albumId") String albumId,
	//    		@PathVariable(value = "wallId") String wallId,
	//    		@RequestParam("file") MultipartFile[] uploadFiles) throws Exception
	//             {
	//    	List<String> att = createUploads(uploadFiles,userId,albumId);
	//    	List<String> photoIds=new ArrayList<>();
	//    	List<Photo> list=new ArrayList<>();
	//    	User user=userService.getById(userId);
	//    	att.forEach((i)->{
	//    		Photo p=new Photo();
	//    		p.setAlbumId(albumId);
	//    		p.setCreatedBy(userId);
	//    		p.setCreatedOn(Calendar.getInstance().getTime());
	//    		p.setDescription("");
	//    		p.setId(UUID.randomUUID().toString());
	//    		p.setPkey(userService.getById(userId).getPkey());
	//    		p.setPath(i);
	//    		p.setType(1);
	//    		try {
	//				p.setDetails(fileThumbs.createThumbNail(userId, albumId,i));
	//			} catch (IOException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//
	//    		photoService.saveOrUpdate(p);
	//
	//			user.setPhotoId(p.getId());
	//			userService.saveOrUpdate(user);
	//    		photoIds.add(p.getId());
	//    		list.add(p);
	//    	});
	//    	FeedDisplay dis=createFeed(list,photoIds,userId,wallId);
	//    	MultiValueMap<String, String> headers = new HttpHeaders();
	//        return new ResponseEntity<FeedDisplay>(dis, headers, HttpStatus.CREATED);
	//    }

	PrivacyStatus getStatus(String value){
		if(value.equalsIgnoreCase("friends")){
			return PrivacyStatus.FRIENDS;
		}else if(value.equalsIgnoreCase("public")){
			return PrivacyStatus.PUBLIC;
		}else if(value.equalsIgnoreCase("private")){
			return PrivacyStatus.PRIVATE;
		}
		return null;
	}
}
