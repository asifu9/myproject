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

import com.share.domain.ResStatus;
import com.share.domain.UserFollowedBy;
import com.share.domain.UserFollower;
import com.share.exception.AppException;
import com.share.master.data.service.UserFollowedByService;
import com.share.master.data.service.UserFollowerService;
@RestController
@RequestMapping("/Follower")
@CrossOrigin(origins = "*", maxAge = 360000)
//@CrossOrigin(origins = {"http://localhost","http://localhost:4200","http://localhost:4200/"})
public class UserFollowerApi {

	@Autowired
	private UserFollowerService userFollowerService;
	
	@Autowired
	private UserFollowedByService userFollowedByService;
	
	@RequestMapping(method=RequestMethod.POST,value="/{fromId}/{wallId}",consumes=MediaType.TEXT_PLAIN_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ResStatus> addFollowers(@PathVariable(value="fromId") String fromId,
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
		UserFollowedBy f=userFollowedByService.getById(fromId);
		if(null==f){
			f=new UserFollowedBy();
			Set<String> set=new HashSet<>();
			set.add(toId);
			Map<String, Date> map=new HashMap<>();
			map.put(toId, Calendar.getInstance().getTime());
			f.setFollowedBy(set);
			f.setFollowedByOn(map);
			f.setTenantId(wallId);
			f.setId(fromId);
			userFollowedByService.saveOrUpdate(f);
		}else{
			Set<String> set=f.getFollowedBy();
			set.add(toId);
			Map<String,Date> map=f.getFollowedByOn();
			map.put(toId, Calendar.getInstance().getTime());
			f.setFollowedBy(set);
			f.setFollowedByOn(map);
			userFollowedByService.saveOrUpdate(f);
		}
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<ResStatus>(new ResStatus("success"), headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Set<String>> getFollowers(@PathVariable(value = "userId") String userId )throws AppException{
		Set<String> data=new HashSet<>();
		userFollowerService.getById(userId);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Set<String>>(data, headers, HttpStatus.CREATED);
	}
}
