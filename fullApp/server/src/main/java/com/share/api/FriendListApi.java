package com.share.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.share.domain.FriendList;
import com.share.domain.FriendRequest;
import com.share.master.data.service.FriendListService;
import com.share.master.data.service.UserService;
import com.share.photo.service.PhotoService;


@RestController
@RequestMapping("/FriendList")
@CrossOrigin(origins = {"http://localhost","http://localhost:4200","http://localhost:4200/"})
public class FriendListApi {

	@Autowired
	private UserService userService;
	@Autowired
	private PhotoService photoService;
	@Autowired
	private FriendListService friendListService;;

	
	@RequestMapping(method=RequestMethod.POST,value="/{userId}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<FriendRequest> create(@PathVariable(value="userId") String userId,@RequestBody FriendList user){
			//its a new record to create
	return null;
		
	}
	
//	
//	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<User> update(@RequestBody User user){
//			//its a new record to create
//			user.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
//			//its an update record
//			//first fetch the data for given id
//		
//		userService.saveOrUpdate(user);
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
//		
//	}
//	
//	@RequestMapping(method=RequestMethod.GET,value="/wall/{wallId}",produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<List<User>> getByWallId(@PathVariable(value = "wallId") String wallId ){
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		List<User> users=userService.listByWallId(wallId);
//		if(null!=users && users.size()>0){
//			users.forEach(user->{
//				if(user.getPhotoId()!=null){
//					user.setPhoto(photoService.getById(user.getPhotoId()));
//				}
//			});
//		}
//		return new ResponseEntity<List<User>>(users ,headers,HttpStatus.OK);
//	}
//	
//	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<User> getById(@PathVariable(value = "id") String id ){
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		User user=userService.getById(id);
//		if(user.getPhotoId()!=null){
//			user.setPhoto(photoService.getById(user.getPhotoId()));
//		}
//		return new ResponseEntity<User>(user ,headers,HttpStatus.OK);
//	}
//	@RequestMapping(method=RequestMethod.GET,value="/userName/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<List<User>> getByUserName(@PathVariable(value = "name") String name ){
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		List<User> user=userService.getByUserName(name);
//		if(null!=user && user.size()>0){
//			user.forEach(i->{
//				if(i.getPhotoId()!=null){
//					i.setPhoto(photoService.getById(i.getPhotoId()));
//				}
//			});
//		}
//		return new ResponseEntity<List<User>>(user ,headers,HttpStatus.OK);
//	}
//	@RequestMapping(method=RequestMethod.GET,value="/",produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<List<User>> getAll(){
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		return new ResponseEntity<List<User>>( userService.listAll(),headers,HttpStatus.OK);
//	}
//	
//	@RequestMapping(method=RequestMethod.DELETE,value="/delete/{id}")
//	private ResponseEntity deleteById(@PathVariable(value="id") String id){
//		System.out.println("delete keuy is " + id);
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		User user=userService.getById(id);
//		System.out.println("user is " + user);
//		userService.delete(user);
//		return new ResponseEntity( headers,HttpStatus.OK);
//	}
}
