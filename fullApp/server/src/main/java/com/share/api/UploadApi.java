package com.share.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.base.enums.FeedType;
import com.base.enums.ShowHide;
import com.base.util.Constants;
import com.share.domain.Feed;
import com.share.domain.Item;
import com.share.domain.MessageChannel;
import com.share.domain.MessageGroup;
import com.share.domain.Ticket;
import com.share.exception.AppException;
import com.share.feeds.api.FeedApi;
import com.share.feeds.service.FeedService;
import com.share.master.data.service.UserService;
import com.share.message.service.MessageChannelService;
import com.share.photo.service.PhotoService;
import com.share.ticket.service.TicketService;
import com.share.util.FileThumbnails;
import com.share.util.UserCache;

@RestController
@RequestMapping("/Upload")
@CrossOrigin(origins = "http://localhost:4200")
public class UploadApi {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private UserService userService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private MessageChannelService messageChannelService;

	@Autowired
	private FeedApi feedApi;

	@Autowired
	private FileThumbnails fileThumbs;

	@Value("${filePath}")
	private String filePathConfig;

	@Resource(name = "userCache")
	UserCache userCache;

	@RequestMapping(method = RequestMethod.POST, value = "/feed/photos/{userId}/{albumId}/{wallId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity uploadPhotoInFeed(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "albumId") String albumId, @PathVariable(value = "wallId") String wallId,
			@RequestParam("file") MultipartFile[] uploadFiles) throws AppException {
		Map<String, String> att = createUploads(uploadFiles, userId, albumId);
		List<String> photoIds = new ArrayList<>();
		List<Photo> list = new ArrayList<>();
		for (Entry<String, String> obj : att.entrySet()) {
			String i = obj.getKey();
			String j = obj.getValue();

			Photo p = new Photo();
			p.setAlbumId(albumId);
			p.setCreatedBy(userId);
			p.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			p.setDescription("");
			p.setId(UUID.randomUUID().toString());
			p.setTenantId(userService.getById(userId).getTenantId());
			p.setPath(i);
			try {
				p.setDetails(fileThumbs.createThumbNail(userId, albumId, i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			photoService.saveOrUpdate(p);

			photoIds.add(p.getId());
			list.add(p);
		}
		Feed dis = createFeed(list, photoIds, userId, wallId);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Feed>(dis, headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/messagegroup/photos/{messageGroupId}/{userId}/{tenantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity uploadGroupMessagePhoto(@PathVariable(value = "messageGroupId") String messageGroupId,
			@PathVariable(value = "userId") String userId, @PathVariable(value = "tenantId") String tenantId,

			@RequestParam("file") MultipartFile[] uploadFiles) throws AppException {
		System.out.println("ticket file upload " + messageGroupId + " : " + userId);
		Map<String, String> att = createUploads(uploadFiles, userId, messageGroupId);
		System.out.println("att is " + att);
		System.out.println("ticket file upload 2");
		MessageChannel t = messageChannelService.getById(messageGroupId);
		System.out.println("ticket file upload 3");
		Photo p = new Photo();
		for (Entry<String, String> obj : att.entrySet()) {
			String i = obj.getKey();
			String j = obj.getValue();
	
			
			p.setAlbumId(messageGroupId);
			p.setCreatedBy(userId);
			p.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			p.setDescription("");
			p.setId(UUID.randomUUID().toString());
			p.setTenantId(tenantId);
			p.setPath(i);
		try {
			p.setDetails(fileThumbs.createThumbNail(userId, messageGroupId, i));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		photoService.saveOrUpdate(p);
		t.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		t.setPhotoId(p.getId());
		messageChannelService.saveOrUpdate(t);
		t.setPhoto(p);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<MessageChannel>(t, headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ticket/photos/{ticketId}/{userId}/{tenantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity ticketPhotoUpdloDS(@PathVariable(value = "ticketId") String ticketId,
			@PathVariable(value = "userId") String userId, @PathVariable(value = "tenantId") String tenantId,

			@RequestParam("file") MultipartFile[] uploadFiles) throws AppException {
		System.out.println("ticket file upload " + ticketId + " : " + userId);
		Map<String, String> att = createUploads(uploadFiles, ticketId, ticketId);
		System.out.println("att is " + att);
		System.out.println("ticket file upload 2");
		Ticket t = ticketService.getById(ticketId);
		List<com.share.domain.Item> list = t.getAttachments();
		System.out.println("ticket file upload 3");
		for (String i : att.keySet()) {
			if (null == list) {
				list = new ArrayList<Item>();
			}
			Item item = new Item();
			item.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			item.setPath(i);
			item.setDisplayName(att.get(i));
			item.setUploadedBy(userId);
			list.add(item);
		}
		t.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		t.setAttachments(list);
		ticketService.saveOrUpdate(t);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Ticket>(ticketService.getByIdFull(ticketId), headers, HttpStatus.CREATED);
	}

	public Feed createFeed(List<Photo> photos, List<String> photoIds, String createdBy, String wallId)
			throws AppException {
		Feed f = new Feed();
		f.setCommentCount(0);
		f.setCreatedBy(createdBy);
		f.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		f.setDescription("desc");
		f.setFeedStatus(ShowHide.SHOW);
		f.setFeedType(FeedType.WALL_PHOTO);
		f.setId(UUID.randomUUID().toString());
		f.setLikeCount(0);
		f.setObjectId(photoIds);
		f.setWallId(wallId);
		feedService.saveOrUpdateDoc(f, wallId);
		return feedApi.convertFeed(f, new HashMap<String, Object>(), new HashMap<String, Object>());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/photos/{userId}/{albumId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity createAttachments(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "albumId") String albumId, @RequestParam("file") MultipartFile[] uploadFiles)
			throws AppException {
		Map<String, String> att = createUploads(uploadFiles, userId, albumId);
		for (Entry<String, String> obj : att.entrySet()) {
			String i = obj.getKey();
			String j = obj.getValue();
			Photo p = new Photo();
			p.setAlbumId(albumId);
			p.setCreatedBy(userId);
			p.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			p.setDescription("");
			p.setId(UUID.randomUUID().toString());
			p.setTenantId(userService.getById(userId).getTenantId());
			p.setPath(i);
			try {
				fileThumbs.createThumbNail(userId, albumId, i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			photoService.saveOrUpdate(p);

		}
		return ResponseEntity.ok(att);
	}

	public Map<String, String> createUploads(MultipartFile[] files, String userId, String albumId) throws AppException {
		Map<String, String> attachmentIds = new HashMap<>();
		try {
			for (MultipartFile file : files) {
				String contentType = file.getContentType();
				String fileName = file.getOriginalFilename();
				long size = file.getSize();
				System.out.println(fileName);
				// extension validation
				int dotPos = fileName.lastIndexOf('.');
				if (dotPos < 0) {
					// throw exception
				}
				String fileExtension = fileName.substring(dotPos + 1, fileName.length());
				// if
				// (!allowableExtensions.contains(fileExtension.toLowerCase()))
				// {
				// throw new AttachmentExtensionNotAllowedException(
				// fileExtension, allowableExtensions);
				// }
				InputStream in = null;
				try {
					in = file.getInputStream();
					String attachmentId = create(userId, albumId, contentType, fileName, size, in);
					attachmentIds.put(attachmentId, fileName);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			for (MultipartFile file : files) {
				try {
					IOUtils.closeQuietly(file.getInputStream());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return attachmentIds;
	}

	private String create(String userId, String albumId, String contentType, String fileName, long fileLength,
			InputStream fileContentStream) {
		try {
			String filePath = filePathConfig + userId + "/" + albumId;
			File f = new File(filePath);
			if (!f.isDirectory()) {
				f.mkdirs();
			}
			String fileNamee = DateTimeCal.getCurrentDateTimeSeconds() + "_" + fileName;
			File targetFile = new File(filePath + "/" + "" + fileNamee);
			OutputStream outStream = new FileOutputStream(targetFile);

			byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = fileContentStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			return fileNamee;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "failure";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/profile/photos/{userId}/{albumId}/{wallId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity uploadProfilePhoto(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "albumId") String albumId, @PathVariable(value = "wallId") String wallId,
			@RequestParam("file") MultipartFile[] uploadFiles) throws AppException {
		Map<String, String> att = createUploads(uploadFiles, userId, albumId);
		List<String> photoIds = new ArrayList<>();
		List<Photo> list = new ArrayList<>();
		User user = userService.getById(userId);
		for (Entry<String, String> obj : att.entrySet()) {
			String i = obj.getKey();
			String j = obj.getValue();
			Photo p = new Photo();
			p.setAlbumId(albumId);
			p.setCreatedBy(userId);
			p.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			p.setDescription("");
			p.setId(UUID.randomUUID().toString());
			p.setTenantId(userService.getById(userId).getTenantId());
			p.setPath(i);
			p.setType(1);
			try {
				p.setDetails(fileThumbs.createThumbNail(userId, albumId, i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			photoService.saveOrUpdate(p);
			user.setPhotoPath(
					Constants.photoPathPrefix + "/" + userId + "/" + albumId + "/" + p.getDetails().get(0).getName());
			user.setPhotoId(p.getId());
			userService.saveOrUpdate(user);
			userCache.set(userId, user);
			photoIds.add(p.getId());
			list.add(p);
		}
		Feed dis = createFeed(list, photoIds, userId, wallId);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Feed>(dis, headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/backgroundprofile/photos/{userId}/{albumId}/{wallId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity uploadBackgroundProfilePhoto(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "albumId") String albumId, @PathVariable(value = "wallId") String wallId,
			@RequestParam("file") MultipartFile[] uploadFiles) throws AppException {
		Map<String, String> att = createUploads(uploadFiles, userId, albumId);
		List<String> photoIds = new ArrayList<>();
		List<Photo> list = new ArrayList<>();
		User user = userService.getById(userId);
		synchronized (list) {

			for (Entry<String, String> obj : att.entrySet()) {
				String i = obj.getKey();
				String j = obj.getValue();
				Photo p = new Photo();
				p.setAlbumId(albumId);
				p.setCreatedBy(userId);
				p.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
				p.setDescription("");
				p.setId(UUID.randomUUID().toString());
				p.setTenantId(userService.getById(userId).getTenantId());
				p.setPath(i);
				p.setType(1);
				try {
					p.setDetails(fileThumbs.createThumbNail(userId, albumId, i));
				} catch (IOException e) {
					e.printStackTrace();
				}

				photoService.saveOrUpdate(p);

				userService.saveOrUpdate(user);
				photoIds.add(p.getId());
				list.add(p);
			}
		}
		Feed dis = createFeed(list, photoIds, userId, wallId);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
	}
}
