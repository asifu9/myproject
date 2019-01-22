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
import com.share.api.DateTimeCal;
import com.share.domain.FriendList;
import com.share.domain.UserFollower;
import com.share.exception.AppException;
import com.share.master.data.service.FriendListService;
import com.share.master.data.service.UserCredentialService;
import com.share.master.data.service.UserFollowerService;
import com.share.master.data.service.UserService;
import com.share.photo.service.PhotoService;
import com.share.util.SequenceGenerator;


@RestController
@RequestMapping("/User")
@Component
//@CrossOrigin(origins = {"http://localhost","http://localhost:4200","http://localhost:4200/"})
public class UserApi {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private FriendListService friendListService;
	
	@Autowired
	private UserCredentialService userCredentialService;
	
	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Autowired
	private UserFollowerService userFollowerService;

    @Bean
    public static PasswordEncoder getPasswordEncoder(){
    	String idForEncode = "bcrypt";
    	Map encoders = new HashMap<>();
    	encoders.put(idForEncode, new BCryptPasswordEncoder());
    	encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
    	encoders.put("scrypt", new SCryptPasswordEncoder());

    	return 
    	    new DelegatingPasswordEncoder(idForEncode, encoders);
    }   
    
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> create(@RequestBody User user) throws AppException{
			//its a new record to create
			user.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		
		user.setId(sequenceGenerator.getNextValue("USER", user.getTenantId()));
		userService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
		
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/create/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUserCrednential(@RequestBody UserCreate userCreate) throws Exception{
			//its a new record to create
			//its an update record
			//first fetch the data for given id
		User users=new User();
		users.setTenantId(userCreate.getTenantId());
		users.setName(userCreate.getName());
		users.setId(sequenceGenerator.getNextValue("USER", users.getTenantId()));
		users.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		userService.saveOrUpdate(users);
		
		UserCredential cre=new UserCredential();
		cre.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		cre.setId(users.getId());
		cre.setPassword(getPasswordEncoder().encode(userCreate.getPassword()));
		cre.setUsername(userCreate.getUserName());
		cre.setTenantId(userCreate.getTenantId());
		Set<String> roles=new HashSet<>();
		roles.add("USER");
		cre.setRoles(roles);
		
		userCredentialService.createUpdate(cre);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<User>(users, headers, HttpStatus.CREATED);
		
	}
	
	
	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> update(@RequestBody User user) throws AppException{
			//its a new record to create
			user.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		System.out.println("update " + user);
		userService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/wall/{wallId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getByWallId(@PathVariable(value = "wallId") String wallId )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<User> users=userService.listByWallId(wallId);
		if(null!=users && users.size()>0){
			for(User user:users){
				if(user.getPhotoId()!=null){
					user.setPhoto(photoService.getById(user.getPhotoId()));
				}
			}
		}
		return new ResponseEntity<List<User>>(users ,headers,HttpStatus.OK);
	}
	

	
	@RequestMapping(method=RequestMethod.GET,value="/wall/{wallId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getByWallUsersByIdExclude(@PathVariable(value = "wallId") String wallId,
			@PathVariable(value = "userId") String userId  )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<User> finalUsers=new ArrayList<>();
		List<User> users=userService.listByWallId(wallId);
		if(null!=users && users.size()>0){
			for(User user:users){
				if(user.getPhotoId()!=null){
					user.setPhoto(photoService.getById(user.getPhotoId()));
				}
				finalUsers.add(user);
			}
		}
		return new ResponseEntity<List<User>>(finalUsers ,headers,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('USER') or hasAuthority('canCreateUser')")
	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getById(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		User user=userService.getById(id);
		if(user!=null){
			if(user.getPhotoId()!=null){
				user.setPhoto(photoService.getById(user.getPhotoId()));
			}
		}
		
		
		return new ResponseEntity<User>(user ,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/main/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getByIdMain(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		User user=userService.getById(id);
		if(user.getPhotoId()!=null){
			user.setPhoto(photoService.getById(user.getPhotoId()));
		}
//		FriendList friendList=friendListService.getById(id);
//		if(null!=friendList){
//			if(friendList.getFriends()!=null){
//				user.setFriends(friendList.getFriends());
//				user.setFriendsCount(friendList.getFriends().size());
//			}
//		}
//		UserFollower ff= userFollowerService.getById(id);
//		if(null!=ff){
//			if(ff.getFollowers()!=null){
//				user.setFollowCount(ff.getFollowers().size());
//			}
//		}
	
		return new ResponseEntity<User>(user ,headers,HttpStatus.OK);
	}
	
	
	
//	@RequestMapping(method=RequestMethod.GET,value="/userName/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<User> getByUserName(@PathVariable(value = "name") String name ){
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		User user=userService.getByUserName(name);
//		System.out.println("user " + user);
//		if(null!=user ){
//			if(user.getPhotoId()!=null){
//				user.setPhoto(photoService.getById(user.getPhotoId()));
//			}
//			FriendList friendList=friendListService.getById(user.getId());
//			if(null!=friendList){
//				if(friendList.getFriends()!=null){
//					user.setFriends(friendList.getFriends());
//					user.setFriendsCount(friendList.getFriends().size());
//				}
//			}
//			UserFollower ff= userFollowerService.getById(user.getId());
//			if(null!=ff){
//				if(ff.getFollowers()!=null){
//					user.setFollowCount(ff.getFollowers().size());
//				}
//			}
//				if(user.getBackgroundPhotoId()!=null){
//					//user.setBackgroundPhoto(photoService.getById(user.getBackgroundPhotoId()));
//				}
//		}
//		return new ResponseEntity<User>(user ,headers,HttpStatus.OK);
//	}
	
	//@PreAuthorize("#oauth2.hasScope('read-foo')")
//	@RequestMapping(method=RequestMethod.GET,value="/",produces=MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<User>> getAll()throws AppException{
//		System.out.println("ok here i am good" +userService);
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		return new ResponseEntity<List<User>>( userService.listAll(),headers,HttpStatus.OK);
//	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/delete/{id}")
	public ResponseEntity deleteById(@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		userService.delete(id);
		return new ResponseEntity( headers,HttpStatus.OK);
	}
	
//	@RequestMapping(method=RequestMethod.GET,value="/userName/plain/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<User> getByUserNamePlain(@PathVariable(value = "name") String name ){
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		User user=userService.getByUserName(name);
//		System.out.println("user " + user);
//		return new ResponseEntity<User>(user ,headers,HttpStatus.OK);
//	}
	
	@RequestMapping(method=RequestMethod.GET,value="/search/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getByIdSimle(@PathVariable(value = "name") String name )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		
		List<User> user=userService.searchByName(name);
		if(null!=user && user.size()>0){
			for(User ii:user){
				if(ii.getPhotoId()!=null){
					ii.setPhoto(photoService.getById(ii.getPhotoId()));
				}
			}
		}
		System.out.println("user " + user);
		return new ResponseEntity<List<User>>(user ,headers,HttpStatus.OK);
	}
	
	
}
