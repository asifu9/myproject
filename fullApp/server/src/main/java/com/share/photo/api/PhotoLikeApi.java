package com.share.photo.api;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.domain.PhotoLikes;
import com.share.api.DateTimeCal;
import com.share.exception.AppException;
import com.share.feeds.service.FeedService;
import com.share.photo.service.PhotoLikeService;


@RestController
@RequestMapping("/PhotoLike")
@CrossOrigin(origins = "http://localhost:4200")
public class PhotoLikeApi {

	@Autowired
	private PhotoLikeService feedLikeService;
	
	@Autowired
	private FeedService feedService;
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/{photoId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<PhotoLikes> createUpdate(@PathVariable(value="photoId") String feedId,@PathVariable(value="userId") String userId)throws AppException{
			//its a new record to create
		PhotoLikes likes= feedLikeService.getById(feedId);
		Map<String,Long> map=null;
		if(null==likes){
			likes=new PhotoLikes();
			likes.setId(feedId);
			likes.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			 map=new HashMap<>();
			 
		}else{
			map = likes.getLikedBy();
		}
		if(map.containsKey(userId)){
			//remove it
			map.remove(userId);
		}else{
			//add it to the like count
			map.put(userId, DateTimeCal.getCurrentDateTimeSeconds());
		}
		likes.setCount(map.size());
		likes.setLikedBy(map);
		
		feedLikeService.saveOrUpdate(likes);
		System.out.println(likes);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<PhotoLikes>(likes, headers, HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/likes/{photoId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getLikes(@PathVariable(value="photoId")String photoId)throws AppException{
		PhotoLikes comm= feedLikeService.getById(photoId);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<PhotoLikes>(comm, headers, HttpStatus.OK);
	}
}
