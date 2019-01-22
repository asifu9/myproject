package com.share.message.api;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.domain.User;
import com.share.api.DateTimeCal;
import com.share.domain.MessageChannel;
import com.share.domain.UserMapItems;
import com.share.exception.AppException;
import com.share.master.data.service.UserMapItemsService;
import com.share.master.data.service.UserService;
import com.share.message.service.MessageChannelService;
import com.share.photo.service.PhotoService;
import com.share.util.UserCache;


@RestController
@RequestMapping("/MessageChannel")
//@CrossOrigin(origins = {"http://localhost","http://localhost:4200","http://localhost:4200/"})
public class MessageChannelApi {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageChannelService service;
	
	@Autowired
	private UserMapItemsService userMapService;
	
	@Autowired
	private PhotoService photoService;
	
	
	@Autowired
	private UserMapItemsService userMapItemsService;
	
	 @Resource(name = "userCache")
	 UserCache userCache;

	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<MessageChannel> create(@RequestBody MessageChannel msgChannel)throws AppException{
		//its a new record to create
		if(msgChannel.getId()==null){
			msgChannel.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			msgChannel.setId(UUID.randomUUID().toString());
		}else{
			msgChannel.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		}
		msgChannel=service.saveOrUpdate(msgChannel);
		
		UserMapItems items=userMapItemsService.getById(msgChannel.getCreatedBy());
		if(items==null){
			 items=new UserMapItems();
			 items.setId(msgChannel.getCreatedBy());
			 userMapItemsService.saveOrUpdate(items);
		}
		Query query = new Query();
		query.addCriteria(Criteria
				.where("id").is(msgChannel.getCreatedBy()));

		Update update6 = new Update();
		update6.push("messageChannel").each(msgChannel.getId());
		userMapItemsService.executeUpdate(query,update6);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<MessageChannel>(msgChannel, headers, HttpStatus.CREATED);
		
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/add/{channelId}/{userId}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<User> createComment(@PathVariable(name="channelId") String channelId, @PathVariable(name="userId") String userId)throws AppException{
		//its a new record to create
		
		MessageChannel channel = service.getById(channelId);
		if(!channel.getMembers().contains(userId)){
			
			channel.getMembers().add(userId);
			channel.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			service.saveOrUpdate(channel);
			
	
			
		}
		UserMapItems items=userMapItemsService.getById(userId);
		if(items==null){
			 items=new UserMapItems();
			 items.setId(userId);
			 userMapItemsService.saveOrUpdate(items);
		}
		Query query = new Query();
		query.addCriteria(Criteria
				.where("id").is(userId));
		
		Update update6 = new Update();
		update6.push("messageChannel").each(channelId);
		userMapItemsService.executeUpdate(query,update6);
//		
//		user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
//		user.setId(UUID.randomUUID().toString());
//		user.setStatus(1);
//		messageService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		User u=userCache.get(userId);
		return new ResponseEntity<User>(u, headers, HttpStatus.CREATED);
		
	}
	

	@RequestMapping(method=RequestMethod.GET,value="/user/{createdBy}/{tenantId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<MessageChannel>> getSpecData(@PathVariable(value = "createdBy") String createdBy ,@PathVariable(value = "tenantId") String tenantId )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<MessageChannel> ggg= service.listByCreatedBy(createdBy, tenantId);
		System.out.println("result " + ggg);
		if(null!=ggg && ggg.size()>0){
				ggg.forEach(v->{
					
					if(v.getCreatedBy()!=null){
						v.setCreatedByUser(userCache.get(v.getCreatedBy()));
					}
					if(v.getPhoto()!=null){
						try {
							v.setPhoto(photoService.getById(v.getPhotoId()));
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			
				
		}
		Collections.sort(ggg);
		return new ResponseEntity<List<MessageChannel>>(ggg ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{channelId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<MessageChannel> getChannelbyId(@PathVariable(value = "channelId") String channelId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		MessageChannel ggg= service.getById(channelId);
		if(ggg.getCreatedBy()!=null){
			ggg.setCreatedByUser(userCache.get(ggg.getCreatedBy()));
		}
		if(ggg.getPhotoId()!=null){
			ggg.setPhoto(photoService.getById(ggg.getPhotoId()));
		}

		return new ResponseEntity<MessageChannel>(ggg ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<MessageChannel>> getAllMessageByUser(@PathVariable(value = "tenantId") String tenantId  )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		
		List<MessageChannel> ggg= service.listByTenantId(tenantId);
		System.out.println("result " + ggg);
		if(null!=ggg){
			ggg.forEach(v->{
				
				if(v.getCreatedBy()!=null){
					v.setCreatedByUser(userCache.get(v.getCreatedBy()));
				}
				if(v.getPhotoId()!=null){
					try {
						v.setPhoto(photoService.getById(v.getPhotoId()));
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
		}
		return new ResponseEntity<List<MessageChannel>>(ggg ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/search/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<MessageChannel>> searchByName(@PathVariable(value = "name") String name )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		
		List<MessageChannel> user=service.searchByName(name);

		System.out.println("user " + user);
		return new ResponseEntity<List<MessageChannel>>(user ,headers,HttpStatus.OK);
	}
	
	
}
