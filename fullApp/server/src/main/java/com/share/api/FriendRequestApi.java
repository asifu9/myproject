package com.share.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

import com.base.domain.User;
import com.base.enums.FriendStatus;
import com.share.domain.FriendList;
import com.share.domain.FriendRequest;
import com.share.domain.UserFollowedBy;
import com.share.exception.AppException;
import com.share.master.data.service.FriendListService;
import com.share.master.data.service.FriendRequestService;
import com.share.master.data.service.UserFollowedByService;
import com.share.master.data.service.UserService;
import com.share.photo.service.PhotoService;


@RestController
@RequestMapping("/FriendRequest")
@CrossOrigin
public class FriendRequestApi {

	@Autowired
	private UserService userService;
	@Autowired
	private PhotoService photoService;
	@Autowired
	private FriendRequestService friendRequestService;
	@Autowired
	private FriendListService friendListService;
	@Autowired
	private UserFollowedByService userFollowedByService;
	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<FriendRequest> create(@RequestBody FriendRequest user) throws AppException{
			//its a new record to create
		user.setId(UUID.randomUUID().toString());
			user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			user.setStatus(FriendStatus.REQUESTED.toString());
			user.setActive(true);
		friendRequestService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<FriendRequest>(user, headers, HttpStatus.CREATED);
		
	}
	@RequestMapping(method=RequestMethod.PUT,value="/UPDATE/{status}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<FriendRequest> updateMeokcook(@PathVariable(value="status") String status,@RequestBody FriendRequest user) throws AppException{
			//its a new record to create
			user.setStatus(status);
			if(status.equals(FriendStatus.ACCEPTED.toString())){
				user.setAcceptedOn(Calendar.getInstance().getTime());
				user.setActive(true);
			}else if(status.equals(FriendStatus.REJECTED.toString())){
				user.setRejectedOn(Calendar.getInstance().getTime());
				user.setActive(false);
			}
		
		friendRequestService.saveOrUpdate(user);
		if(status.equals(FriendStatus.ACCEPTED.toString())){
			FriendList flist1=friendListService.getById(user.getRequestedFrom());
			if(null==flist1){
				flist1=new FriendList();
				flist1.setId(user.getRequestedFrom());
				flist1.setTenantId(user.getTenantId());
				Set<String> str=new HashSet<>();
				str.add(user.getRequestedTo());
				flist1.setFriends(str);
				friendListService.saveOrUpdate(flist1);
			}else{
				Set<String> set=flist1.getFriends();
				set.add(user.getRequestedTo());
				friendListService.saveOrUpdate(flist1);
			}
			//do vise versa
			FriendList flist=friendListService.getById(user.getRequestedTo());
			if(null==flist){
				flist=new FriendList();
				flist.setId(user.getRequestedTo());
				flist.setTenantId(user.getTenantId());
				Set<String> str=new HashSet<>();
				str.add(user.getRequestedFrom());
				flist.setFriends(str);
				friendListService.saveOrUpdate(flist);
			}else{
				Set<String> set=flist.getFriends();
				set.add(user.getRequestedFrom());
				friendListService.saveOrUpdate(flist);
			}
		}
		List<FriendRequest> ll=new ArrayList<>();
		ll.add(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<FriendRequest>(user, headers, HttpStatus.CREATED);
		
	}
	@RequestMapping(method=RequestMethod.GET,value="/requestList/{userId}/{pkey}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<FriendRequest>> getRequestToList(@PathVariable(value = "userId") String userId ,
			@PathVariable(value = "pkey") String pkey ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<FriendRequest> users=friendRequestService.listByRquestTo(userId,pkey,FriendStatus.REQUESTED.toString());
		if(null!=users && users.size()>0){
			users.forEach(user->{
				if(user.getRequestedFrom()!=null){
					//user.setRequestedFromUser(userService.getByIdWithPhoto(user.getRequestedFrom()));
				}
			});
		}
		return new ResponseEntity<List<FriendRequest>>(users ,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/wall/{wallId}/{currentUserId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<User>> getByWallId(@PathVariable(value = "wallId") String wallId,
			@PathVariable(value="currentUserId") String currentUserId ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<User> finalList=new ArrayList<>();
		List<com.base.domain.User> users=userService.listByWallId(wallId);
		//get list of request this current user as been send to friends
		List<FriendRequest> thisUserRequest=friendRequestService.listByRquestFrom(currentUserId, wallId);
		List<FriendRequest> otherUserRequested=friendRequestService.listByRquestTo(currentUserId, wallId);
		FriendList friends=friendListService.getById(currentUserId);
		UserFollowedBy followers=userFollowedByService.getById(currentUserId);
		System.out.println("this user request " +  thisUserRequest);
		//System.out.println("user " + currentUserId + "  firends are " + friends.getFriends());
//		if(null!=users && users.size()>0){
//			
//			users.forEach(user->{
//				String userId=user.getId();
//				System.out.println("------- Current User ---- " + userId + " : " + user.getName());
//				if(!userId.equals(currentUserId)){
//					if(user.getPhotoId()!=null){
//						user.setPhoto(photoService.getById(user.getPhotoId()));
//					}
//					if(null!=followers && followers.getFollowedBy()!=null && followers.getFollowedBy().contains(userId)){
//						user.setFollow(true);
//					}else{
//						user.setFollow(false);
//					}
//					
//					if(friends!=null && friends.getFriends()!=null && friends.getFriends().contains(userId)){
//						user.setRequestStatus(FriendStatus.ACCEPTED.toString());
//					}else{
//						//lets check if this user has send request to other users.
//						
//						if(thisUserRequest!=null && thisUserRequest.size()>0){
//							thisUserRequest.forEach(i->{
//								if(userId.equals(i.getRequestedTo())){
//									user.setRequestStatus(FriendStatus.REQUESTED.toString());
//									
//									
//								}
//							});
//						}
//						if(null==user.getRequestStatus() && otherUserRequested!=null && otherUserRequested.size()>0){
//							otherUserRequested.forEach(i->{
//								System.out.println("other user requested " + i.getRequestedFrom() + " : " + userId);
//								if(userId.equals(i.getRequestedFrom())){
//									user.setRequestStatus("AcceptMe");
//									
//								}
//							});
//						}
//					}
//				
//					finalList.add(user);
//				}
//			});
//			
//			finalList.forEach(u->{
//				if(u.getRequestStatus()==null){
//					for(FriendRequest friendRequest:list){
//						if(friendRequest.getRequestedFrom().equals(u.getId())){
//							u.setRequestStatus("AcceptMe");
//							u.setFriendRequest(friendRequest);
//							break;
//						}
//					}
//				}
//			});
//		}
		return new ResponseEntity<List<User>>(finalList ,headers,HttpStatus.OK);
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
