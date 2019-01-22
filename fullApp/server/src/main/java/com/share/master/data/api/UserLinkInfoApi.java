package com.share.master.data.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.domain.User;
import com.base.domain.UserCreate;
import com.base.domain.UserCredential;
import com.share.domain.FriendList;
import com.share.domain.UserFollower;
import com.share.domain.UserLinkInfo;
import com.share.exception.AppException;
import com.share.master.data.service.DepartmentService;
import com.share.master.data.service.FriendListService;
import com.share.master.data.service.OrgDesignationService;
import com.share.master.data.service.TeamService;
import com.share.master.data.service.UserCredentialService;
import com.share.master.data.service.UserFollowerService;
import com.share.master.data.service.UserLinkInfoService;
import com.share.master.data.service.UserService;
import com.share.photo.service.PhotoService;
import com.share.util.SequenceGenerator;


@RestController
@RequestMapping("/UserLinkInfo")
@Component
//@CrossOrigin(origins = {"http://localhost","http://localhost:4200","http://localhost:4200/"})
public class UserLinkInfoApi {

	@Autowired
	private UserLinkInfoService userLinkInfoService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private OrgDesignationService designationService;;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private UserService userService;

    
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserLinkInfo> create(@RequestBody UserLinkInfo user) throws Exception{
			//its a new record to create
			user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			//its an update record
			//first fetch the data for given id

		user=userLinkInfoService.saveOrUpdate(user);
		if(user!=null){
			if(user.getDepartmentId()!=null){
				user.setDepartment(departmentService.getById(user.getDepartmentId()));
			}
			if(user.getDesignationId()!=null){
				user.setDesignation(designationService.getById(user.getDesignationId()));
			}
			if(user.getTeamId()!=null){
				user.setTeam(teamService.getById(user.getTeamId()));
			}
			if(user.getManagerId()!=null){
				user.setManager(userService.getById(user.getManagerId()));
			}
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<UserLinkInfo>(user, headers, HttpStatus.CREATED);
		
	}
	
	
	

	
	@RequestMapping(method=RequestMethod.GET,value="/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserLinkInfo> getByWallId(@PathVariable(value = "userId") String userId ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserLinkInfo users=userLinkInfoService.getById(userId);
		if(users!=null){
			if(users.getDepartmentId()!=null){
				users.setDepartment(departmentService.getById(users.getDepartmentId()));
			}
			if(users.getDesignationId()!=null){
				users.setDesignation(designationService.getById(users.getDesignationId()));
			}
			if(users.getTeamId()!=null){
				users.setTeam(teamService.getById(users.getTeamId()));
			}
			if(users.getManagerId()!=null){
				users.setManager(userService.getById(users.getManagerId()));
			}
		}
		
		return new ResponseEntity<UserLinkInfo>(users ,headers,HttpStatus.OK);
	}
	

	
}
