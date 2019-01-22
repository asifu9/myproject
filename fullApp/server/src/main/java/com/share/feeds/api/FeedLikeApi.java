package com.share.feeds.api;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.share.domain.FeedLikes;
import com.share.exception.AppException;
import com.share.feeds.service.FeedLikeService;
import com.share.feeds.service.FeedService;


@RestController
@RequestMapping("/FeedLike")
@CrossOrigin(origins = "http://localhost:4200")
public class FeedLikeApi {

	@Autowired
	private FeedLikeService feedLikeService;
	
	@Autowired
	private FeedService feedService;
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/{feedId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<FeedLikes> createUpdate(@PathVariable(value="feedId") String feedId,@PathVariable(value="userId") String userId)throws AppException{
			//its a new record to create
		FeedLikes likes= feedLikeService.getById(feedId);
		Map<String,Date> map=null;
		if(null==likes){
			likes=new FeedLikes();
			likes.setId(feedId);
			likes.setCreatedOn(Calendar.getInstance().getTime());
			 map=new HashMap<>();
			 
		}else{
			map = likes.getLikedBy();
		}
		if(map.containsKey(userId)){
			//remove it
			map.remove(userId);
		}else{
			//add it to the like count
			map.put(userId, Calendar.getInstance().getTime());
		}
		likes.setCount(map.size());
		likes.setLikedBy(map);
		
		feedLikeService.saveOrUpdate(likes);
		Update up=new Update();
		up.set("likeCount", map.size());
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(feedId));
		feedService.update(query, up);
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<FeedLikes>(likes, headers, HttpStatus.CREATED);
		
	}
	
	
}
