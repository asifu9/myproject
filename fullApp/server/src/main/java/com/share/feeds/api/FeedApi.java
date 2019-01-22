package com.share.feeds.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.domain.Photo;
import com.base.domain.User;
import com.base.enums.FeedType;
import com.base.util.Utils;
import com.share.api.DateTimeCal;
import com.share.domain.CBlock;
import com.share.domain.Feed;
import com.share.domain.FeedComments;
import com.share.domain.FeedLikes;
import com.share.exception.AppException;
import com.share.feeds.service.FeedCommentService;
import com.share.feeds.service.FeedLikeService;
import com.share.feeds.service.FeedService;
import com.share.master.data.service.UserService;
import com.share.photo.service.PhotoService;


@RestController
@RequestMapping("/Feed")
//@CrossOrigin(origins = "http://localhost:4200")
public class FeedApi {

	@Autowired
	private FeedService feedService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private FeedLikeService feedLikeService;
	
	@Autowired
	private FeedCommentService feedCommentService;
	
	
	@RequestMapping(method=RequestMethod.POST,value="/{wallId}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Feed> create(@PathVariable(value="wallId") String wallId, @RequestBody Feed feed) throws AppException{
			//its a new record to create
		feed.setId(UUID.randomUUID().toString());
		feed.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		feed.setWallId(wallId);
		feedService.saveOrUpdateDoc(feed,  wallId);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Feed>(feed, headers, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/{wallId}/{feedId}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Feed> upateSetting(@PathVariable(value="wallId") String wallId,@PathVariable(value="feedId") String feedId,
			@RequestBody Map<String,Boolean> setting) throws AppException{
			//its a new record to create
		System.out.println("feed setting is  " + setting);
		Feed f=feedService.getById(feedId, wallId);
		f.setFeedSetting(setting);
		feedService.saveOrUpdateDoc(f,  wallId);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Feed>(f, headers, HttpStatus.CREATED);
	}

	
	@RequestMapping(method=RequestMethod.GET,value="/createdby/{input}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Feed>> getFeedsCreatedBy(@PathVariable(value = "input") String input ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<List<Feed>>( feedService.listByUserId(input),headers,HttpStatus.OK);
	
	}
	
//	@RequestMapping(method=RequestMethod.GET,value="/",produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<List<Feed>> getAllFeeds()throws AppException{
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		return new ResponseEntity<List<Feed>>( feedService.listAll(),headers,HttpStatus.OK);
//	}
	
	@RequestMapping(method=RequestMethod.GET,value="/wallId/{wallId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Object> getFeedsByWallId(@PathVariable(value="wallId") String wallId) throws AppException{
		long start = Calendar.getInstance().getTimeInMillis();
		MultiValueMap<String, String> headers = new HttpHeaders();
		System.out.println("groupId " + wallId);
		Map<String,Object> usersMap=new HashMap<>();
		Map<String,Object> photoMap=new HashMap<>();
		List<Feed> items=new ArrayList<>();
		
		feedService.listAll(wallId).forEach((item)->{
			
			try {
				items.add(convertFeed(item,usersMap,photoMap));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Collections.sort(items);
		long end= Calendar.getInstance().getTimeInMillis();
		System.out.println("total time taken is " + (end-start));
		Map<String,Object> ddd=new HashMap<>();
		ddd.put("feeds", items);
		ddd.put("users",usersMap);
		ddd.put("photos",photoMap);
		return new ResponseEntity<Object>( ddd,headers,HttpStatus.OK);
	}
	
	public  Feed convertFeed(Feed item,Map<String,Object> userMap,Map<String,Object> photoMap) throws AppException{
		Feed dis=new Feed();
		dis=item;
		//dis.setCommentCount( );
		User userDetails1= userService.getById(item.getCreatedBy());
		
		if(null!=userDetails1 && null!=userDetails1.getPhotoId()){
			userMap.put(item.getCreatedBy(), userDetails1);
			if(!photoMap.containsKey(userDetails1.getPhotoId())){
				photoMap.put(userDetails1.getPhotoId(), photoService.getById(userDetails1.getPhotoId()));
			}
			
		}
		dis.setDescription(item.getDescription());
		dis.setFeedStatus(item.getFeedStatus());
		dis.setFeedType(item.getFeedType());
		dis.setId(item.getId());
		FeedLikes likes=feedLikeService.getById(dis.getId());
		FeedComments comm=feedCommentService.getById(dis.getId());
		dis.setCreatedOn(item.getCreatedOn());
		if(comm!=null && comm.getComments()!=null){
			for(CBlock i:comm.getComments()){
				if(!userMap.containsKey(i.getUserId())){
					User userDetails=userService.getById(i.getUserId());

					if(null!=userDetails && null!=userDetails.getPhotoId()){
						//userDetails.setPhoto(photoService.getById(userDetails.getPhotoId()));
						userMap.put(i.getUserId(), userDetails);
						if(!photoMap.containsKey(userDetails.getPhotoId())){
							photoMap.put(userDetails.getPhotoId(), photoService.getById(userDetails.getPhotoId()));
						}
						
					}
				}
				i.setCreatedOnStr(Utils.convertDate(i.getCreatedOn()));
			}
			Collections.sort(comm.getComments());
			
		}
		dis.setLikeCountObj(likes);
		dis.setCommentCountObj(comm);
		dis.setObjectId(item.getObjectId());
		if(item.getFeedType().equals(FeedType.WALL_PHOTO)){
			Object obj=(List) item.getObjectId();
			if(item.getOriginalPostId()!=null){
				//its an shared feed
				Feed dd=feedService.getById(item.getOriginalPostId(),item.getWallId());
				
				if(!userMap.containsKey(item.getCreatedBy())){
					User u = userService.getById(item.getCreatedBy());
					userMap.put(item.getCreatedBy(), u);
					if(!photoMap.containsKey(u.getPhotoId())){
						photoMap.put(u.getPhotoId(), photoService.getById(u.getPhotoId()));
					}
					//User userDetails21=(User) userService.getByIdWithPhoto(item.getCreatedBy());
				}
				if(dd.getObjectId() instanceof List){
					List<String> ls=(List<String>) dd.getObjectId();
					List<Photo> photos=new ArrayList<>();
					for(String pp : ls){
						Photo ii=photoService.getById(pp);
						if(!userMap.containsKey(pp)){
							userMap.put(pp, ii);
							if(ii.getCreatedBy()!=null && !userMap.containsKey(ii.getCreatedBy())){
								User uuuu= userService.getById(ii.getCreatedBy());
	
					    		userMap.put(ii.getCreatedBy(), uuuu);
					    		if(!photoMap.containsKey(uuuu.getPhotoId())){
					    			photoMap.put(uuuu.getPhotoId(), photoService.getById(uuuu.getPhotoId()));
					    		}
					    	}
						}
						
						photos.add(ii);
					}
					dd.setObjectIdObj(photos);
				}
				dis.setOriginalPostIdObj(dd);
				if(dd.getObjectId() instanceof List){
					obj= dd.getObjectId();
				}
			}
			if(obj instanceof List){
				List<String> ls=(List<String>) obj;
				List<Photo> photos=new ArrayList<>();
				for(String pp:ls){
					if(!photoMap.containsKey(pp)){
						photoMap.put(pp, photoService.getById(pp));
					}
				}
				dis.setObjectIdObj(photos);
			}
		}
		dis.setWallId(item.getWallId());
		return dis;
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/delete/{id}")
	private ResponseEntity deleteById(@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		feedService.delete(id);
		return new ResponseEntity( headers,HttpStatus.OK);
	}
}
