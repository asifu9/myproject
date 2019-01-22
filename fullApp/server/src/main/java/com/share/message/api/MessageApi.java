package com.share.message.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.share.domain.MessageGroup;
import com.share.domain.Messages;
import com.share.domain.UserMapItems;
import com.share.exception.AppException;
import com.share.master.data.service.UserMapItemsService;
import com.share.master.data.service.UserService;
import com.share.message.service.MessageChannelService;
import com.share.message.service.MessageGroupService;
import com.share.message.service.MessageService;
import com.share.photo.service.PhotoService;
import com.share.services.NotifyUser;
import com.share.util.UserCache;


@RestController
@RequestMapping("/Message")
//@CrossOrigin(origins = {"http://localhost","http://localhost:4200","http://localhost:4200/"})
public class MessageApi {

	@Autowired
	private UserService userService;

	@Autowired
	private MessageGroupService messageGroupService;
	
	@Autowired
	private PhotoService photoService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserMapItemsService userMapItemsService;

	@Autowired
	private MessageChannelService messageChannelService;

	@Resource(name = "userCache")
	UserCache userCache;

	@Autowired
	NotifyUser notifyUser;
	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Messages> create(@RequestBody Messages user) throws AppException{
		//its a new record to create
		user.setId(UUID.randomUUID().toString());
		user.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		messageService.saveOrUpdate(user);
		if(user.getMessagedBy()!=null){
			user.setMessagedByUser(userCache.get(user.getMessagedBy()));
		}
		if(user.getMessagedTo()!=null){
			user.setMessageToUser(userCache.get(user.getMessagedTo()));
		}
		if(user.getGroupId()==null && !user.getMessagedBy().equals(user.getMessagedTo())){
			notifyUser.addNotificationCountToUser(user.getMessagedBy(), user.getMessagedTo());
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Messages>(user, headers, HttpStatus.CREATED);

	}


	@RequestMapping(method=RequestMethod.POST,value="/comment/{messageId}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Messages> createComment(@PathVariable(name="messageId") String messageId, @RequestBody Messages comment) throws AppException{
		//its a new record to create


		Messages messsage=messageService.getById(messageId);

		comment.setId(UUID.randomUUID().toString());
		comment.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		//comment.setMessagedTo(messageTo);
		//messageService.saveOrUpdate(user);
		List<Messages> list=messsage.getComments();
		if(list!=null && list.size()>0){
			list.add(0, comment);
		}else{
			list=new ArrayList<>();
			list.add(comment);
		}
		messsage.setComments(list);
		messageService.saveOrUpdate(messsage);
		comment.setMessagedByUser(userCache.get(comment.getMessagedBy()));
		messsage.setMessagedByUser(userCache.get(messsage.getMessagedBy()));
		//		
		//		user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		//		user.setId(UUID.randomUUID().toString());
		//		user.setStatus(1);
		//		messageService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Messages>(messsage, headers, HttpStatus.CREATED);

	}

	@RequestMapping(method=RequestMethod.DELETE,value="/delete/{messageId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Messages> dleteItem(@PathVariable(name="messageId") String messageId) throws AppException{
		//its a new record to create

		Object data="time";
		Messages messsage=messageService.getById(messageId);
		messageService.delete(messsage);


		//		
		//		user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		//		user.setId(UUID.randomUUID().toString());
		//		user.setStatus(1);
		//		messageService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Messages>(messsage, headers, HttpStatus.OK);

	}

	@RequestMapping(method=RequestMethod.DELETE,value="/comment/{messageId}/{commentId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Messages> deleteComment(@PathVariable(name="messageId") String messageId,@PathVariable(name="commentId") String commentId) throws AppException{
		//its a new record to create

		Object data="time";
		Messages messsage=messageService.getById(messageId);
		synchronized (data) {
			if(messsage.getComments()!=null){
				Iterator<Messages> itr=messsage.getComments().iterator();
				Messages itemToDelete=null;
				while(itr.hasNext()){
					Messages m = itr.next();	
					if(m.getId().equals(commentId)){
						itemToDelete=m;
						break;
					}
				}
				messsage.getComments().remove(itemToDelete);
				messageService.saveOrUpdate(messsage);
			}

		}

		//		
		//		user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		//		user.setId(UUID.randomUUID().toString());
		//		user.setStatus(1);
		//		messageService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Messages>(messsage, headers, HttpStatus.CREATED);

	}


	@RequestMapping(method=RequestMethod.GET,value="/flowData/{userId}/{otherUserId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Messages>> getSpecData(@PathVariable(value = "userId") String userId ,@PathVariable(value = "otherUserId") String otherUserId ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Map<String,User> userMap=new HashMap<>();
		System.out.println("inside india " + userId + " : " + otherUserId);
		List<Messages> ggg= messageService.listByUsers(userId,otherUserId);
		System.out.println("result " + ggg);
		if(null!=ggg && ggg.size()>0){
			ggg.forEach(v->{

				if(v.getMessagedBy()!=null){
					//v.setMessagedByUser(userService.getByIdWithPhoto(v.getMessagedBy()));
					v.setMessagedByUser(userCache.get(v.getMessagedBy()));
				}
				if(v.getMessagedTo()!=null){
					//v.setMessageToUser(userService.getByIdWithPhoto(v.getMessagedTo()));
					v.setMessageToUser(userCache.get(v.getMessagedTo()));
				}
				if(v.getComments()!=null){
					v.getComments().forEach(itt->{
						if(itt.getMessagedBy()!=null){
							//v.setMessagedByUser(userService.getByIdWithPhoto(v.getMessagedBy()));
							itt.setMessagedByUser(userCache.get(itt.getMessagedBy()));
						}
						if(itt.getMessagedTo()!=null){
							//v.setMessageToUser(userService.getByIdWithPhoto(v.getMessagedTo()));
							itt.setMessageToUser(userCache.get(itt.getMessagedTo()));
						}
					});
				}
			});


		}
		Collections.sort(ggg);
		return new ResponseEntity<List<Messages>>(ggg ,headers,HttpStatus.OK);
	}


	@RequestMapping(method=RequestMethod.GET,value="/channelData/{channelId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Messages>> getSpecData(@PathVariable(value = "channelId") String channelId ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Map<String,User> userMap=new HashMap<>();
		System.out.println("inside india " + channelId);
		List<Messages> ggg= messageService.listByChannelId(channelId);
		System.out.println("result " + ggg);
		if(null!=ggg && ggg.size()>0){
			ggg.forEach(v->{

				if(v.getMessagedBy()!=null){
					//v.setMessagedByUser(userService.getByIdWithPhoto(v.getMessagedBy()));
					v.setMessagedByUser(userCache.get(v.getMessagedBy()));
				}
				if(v.getMessagedTo()!=null){
					//v.setMessageToUser(userService.getByIdWithPhoto(v.getMessagedTo()));
					v.setMessageToUser(userCache.get(v.getMessagedTo()));
				}
				if(v.getComments()!=null){
					v.getComments().forEach(itt->{
						if(itt.getMessagedBy()!=null){
							//v.setMessagedByUser(userService.getByIdWithPhoto(v.getMessagedBy()));
							itt.setMessagedByUser(userCache.get(itt.getMessagedBy()));
						}
						if(itt.getMessagedTo()!=null){
							//v.setMessageToUser(userService.getByIdWithPhoto(v.getMessagedTo()));
							itt.setMessageToUser(userCache.get(itt.getMessagedTo()));
						}
					});
				}
			});


		}
		Collections.sort(ggg);
		return new ResponseEntity<List<Messages>>(ggg ,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/{userId}/{wallId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<MessageGroup> getAllMessageByUser(@PathVariable(value = "userId") String userId ,@PathVariable(value = "wallId") String wallId ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		System.out.println("inside india " + userId + " : " + wallId);

		MessageGroup ggg= messageGroupService.findById(userId);
		System.out.println("result " + ggg);
		if(null!=ggg){
			if(null!= ggg.getMessage()){
				ggg.getMessage().forEach((k,v)->{

					if(v.getMessagedBy()!=null){
						try {
							v.setMessagedByUser(userService.getByIdWithPhoto(v.getMessagedBy()));
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(v.getMessagedTo()!=null){
						try {
							v.setMessageToUser(userService.getByIdWithPhoto(v.getMessagedTo()));
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			}
		}
		if(ggg==null){
			ggg=new MessageGroup();
		}
		return new ResponseEntity<MessageGroup>(ggg ,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.GET,value="/addUser/{currentUser}/{targetUser}/{flow}/{type}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<User> addUser(@PathVariable(value = "currentUser") String currentUser,
			@PathVariable(value = "targetUser") String targetUser,
			@PathVariable(value = "flow") String flow,
			@PathVariable(value = "type") String type ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserMapItems items=null;
		synchronized (headers) {
			items=userMapItemsService.getById(currentUser);
			if(items==null){
				items=new UserMapItems();
				items.setId(currentUser);
				userMapItemsService.saveOrUpdate(items);
			}
			if("messsage".equals(flow)){
				if("user".equals(type)){



					Query query = new Query();
					query.addCriteria(Criteria
							.where("id").is(currentUser));

					Update update6 = new Update();
					update6.push("messageUser").each(targetUser);
					userMapItemsService.executeUpdate(query,update6);
				}

			}

		}

		User u=userCache.get(targetUser);

		return new ResponseEntity<User>(u ,headers,HttpStatus.OK);
	}



	@RequestMapping(method=RequestMethod.GET,value="/UserMapItems/fetch/{currentUser}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserMapItems> addUser(@PathVariable(value = "currentUser") String currentUser) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();

		UserMapItems items=userMapItemsService.getById(currentUser);

		if(items!=null){
			if(items.getMessageChannel()!=null){
				for(String ii:items.getMessageChannel()){
					MessageChannel ms= messageChannelService.getById(ii);
					if(ms.getPhotoId()!=null){
						ms.setPhoto(photoService.getById(ms.getPhotoId()));
					}
					items.getMessageChannelObj().add(ms);
				}
			}
			if(items.getMessageUser()!=null){
				items.getMessageUser().forEach(ii->{
					
					items.getMessageUserObj().add(userCache.get(ii));
				});
			}

		}

		return new ResponseEntity<UserMapItems>(items ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/clearCounter/messageuser/{currentUser}/{targetUser}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserMapItems> clearUserNofitication(@PathVariable(value = "currentUser") String currentUser,
			@PathVariable(value = "targetUser") String targetUser) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();

		UserMapItems items=userMapItemsService.getById(currentUser);

		if(items!=null){
			if(items.getMessageUserCounter()!=null){
				items.getMessageUserCounter().put(targetUser,0);
			}

		}
		userMapItemsService.saveOrUpdate(items);

		return new ResponseEntity<UserMapItems>(items ,headers,HttpStatus.OK);
	}



}
