package com.share.feeds.api;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.domain.DropDownValues;
import com.base.enums.FeedType;
import com.base.enums.ShowHide;
import com.share.api.DateTimeCal;
import com.share.domain.Feed;
import com.share.domain.Group;
import com.share.exception.AppException;
import com.share.feeds.service.FeedCommentService;
import com.share.feeds.service.FeedLikeService;
import com.share.feeds.service.FeedService;
import com.share.master.data.service.UserService;
import com.share.services.GroupService;


@RestController
@RequestMapping("/share")
@CrossOrigin(origins = "http://localhost:4200")
public class ShareApi {

	@Autowired
	private FeedService feedService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FeedLikeService feedLikeService;
	
	@Autowired
	private FeedCommentService feedCommentService;
	
	@Autowired
	GroupService groupService;
	
	
	@RequestMapping(method=RequestMethod.POST,value="/{objectId}/{userId}/{type}/{feedWallId}/{desc}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Feed> create(@PathVariable(value = "objectId") String objectId ,
			@PathVariable(value = "userId") String userId ,
			@PathVariable(value = "type") String type ,
			@PathVariable(value="feedWallId") String feedWallId,
			@PathVariable(value = "desc") String desc ,
			@RequestBody List<String> string)throws AppException{
			//its a new record to create
		System.out.println("-----------------------------------------------------------------------");
		System.out.println(string);
		System.out.println("userid " + userId + " : " + type + " : " + feedWallId +  " :" + desc);
		Feed f=new Feed();
		Set<String> walls=new HashSet<>();
		string.forEach((wall)->{
			walls.add(wall);
		});
		if(type.equals("photo")){
			
			f.setCommentCount(0);
			f.setCreatedBy(userId);
			
			f.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			f.setDescription(desc);
			f.setFeedStatus(ShowHide.SHOW);
			f.setFeedType(FeedType.PHOTO);
			f.setId(UUID.randomUUID().toString());
			f.setLikeCount(0);
			f.setObjectId(objectId);
			
		}else if(type.equalsIgnoreCase("feed")){
			Feed temp=feedService.getById(objectId,feedWallId);
			System.out.println("going to update the feed is "+temp);
			temp.setShared(true);
			temp.setShareCount(temp.getShareCount()+walls.size());
			f.setCreatedBy(userId);
			f.setCommentCount(0);
			
			f.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			f.setDescription(desc);
			f.setFeedStatus(ShowHide.SHOW);
			f.setFeedType(temp.getFeedType());
			f.setId(UUID.randomUUID().toString());
			f.setLikeCount(0);
			f.setOriginalPostId(objectId);
			
			feedService.saveOrUpdateDoc(temp,feedWallId);
		}
		for(String wall:walls){
			
			System.out.println("for wall " + wall);
			f.setWallId(wall);
			feedService.saveOrUpdateDoc(f, wall);
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Feed>(new Feed(), headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/groupDetails/{userId}/{wallId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SortedSet<DropDownValues>> getList(@PathVariable(value="userId") String userId,@PathVariable(value="wallId") String wallId)throws AppException{
		SortedSet<DropDownValues> list=new TreeSet<>();
		//get the groups by him and also public groups
		List<Group> gg=groupService.listByCreatedByAndPkey(userId, wallId);
		if(gg!=null && !gg.isEmpty()){
			gg.forEach(i->{
				list.add(new DropDownValues(i.getId(),i.getName(),i,"Group"));
			});
		}
		list.add(new DropDownValues(wallId, "Current Wall", null, "Wall"));
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<SortedSet<DropDownValues>>(list, headers, HttpStatus.OK);
	}
}
