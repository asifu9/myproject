package com.share.master.data.api;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

import com.share.domain.UserFollower;
import com.share.exception.AppException;
import com.share.master.data.service.UserFollowerService;
@RestController
@RequestMapping("/FollowedBy")
@CrossOrigin(origins = "http://localhost:4200")
public class UserFollowedByApi {

	@Autowired
	private UserFollowerService userFollowerService;
	
	@RequestMapping(method=RequestMethod.POST,value="/{fromId}/{wallId}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.TEXT_HTML_VALUE)
	private ResponseEntity<String> addFollowers(@PathVariable(value="fromId") String fromId,
												@PathVariable(value="wallId") String wallId,
												@RequestBody String toId)throws AppException{
		
		UserFollower follower= userFollowerService.getById(toId);
		if(null==follower){
			follower=new UserFollower();
			follower.setId(toId);
			follower.setTenantId(wallId);
			Set<String> set=new HashSet<>();
			set.add(fromId);
			follower.setFollowers(set);
			Map<String, Date> map=new HashMap<>();
			map.put(fromId, Calendar.getInstance().getTime());
			follower.setFollowsOn(map);
			userFollowerService.saveOrUpdate(follower);
		}else{
			Set<String> set=follower.getFollowers();
			set.add(fromId);
			Map<String,Date> map=follower.getFollowsOn();
			map.put(fromId, Calendar.getInstance().getTime());
			follower.setFollowers(set);
			follower.setFollowsOn(map);
			userFollowerService.saveOrUpdate(follower);
		}
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<String>("Success", headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Set<String>> getFollowers(@PathVariable(value = "userId") String userId )throws AppException{
		Set<String> data=new HashSet<>();
		userFollowerService.getById(userId);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Set<String>>(data, headers, HttpStatus.CREATED);
	}
}
