package com.share.api;

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

import com.share.domain.GroupLikes;
import com.share.exception.AppException;
import com.share.services.GroupLikeService;
import com.share.services.GroupService;


@RestController
@RequestMapping("/GroupLike")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupLikeApi {

	@Autowired
	private GroupLikeService groupLikeService;
	
	@Autowired
	private GroupService groupService;
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/{groupId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<GroupLikes> createUpdate(@PathVariable(value="groupId") String groupId,@PathVariable(value="userId") String userId)throws AppException{
			//its a new record to create
		GroupLikes likes= groupLikeService.getById(groupId);
		Map<String,Date> map=null;
		if(null==likes){
			likes=new GroupLikes();
			likes.setId(groupId);
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
		
		groupLikeService.saveOrUpdate(likes);
		Update up=new Update();
		up.set("likesCount", map.size());
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(groupId));
		groupService.update(query, up);
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<GroupLikes>(likes, headers, HttpStatus.CREATED);
		
	}
	
	
}
