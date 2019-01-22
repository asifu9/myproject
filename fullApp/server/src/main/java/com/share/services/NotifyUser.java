package com.share.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.share.domain.UserMapItems;
import com.share.exception.AppException;
import com.share.master.data.service.UserMapItemsService;

@Service
public class NotifyUser {

	@Autowired
	UserMapItemsService userMapItemsService;
	
	
	 public void addNotificationCountToUser(String sourceUserId,String targetUserId) throws AppException{
		UserMapItems item= userMapItemsService.getById(targetUserId);
		if(item==null){
			item=new UserMapItems();
			item.setId(targetUserId);
			item.getMessageUser().add(sourceUserId);
		}
		if(item.getMessageUserCounter()==null){
			item.setMessageUserCounter(new HashMap<>());
			item.getMessageUserCounter().put(sourceUserId, 1);
			if(!item.getMessageUser().contains(sourceUserId)){
				item.getMessageUser().add(sourceUserId);
			}
		}else{
			//invoke update 
			System.out.println(new Gson().toJson(item));
			if(item.getMessageUserCounter()!=null){
				if(item.getMessageUserCounter().get(sourceUserId)!=null){
					int total=item.getMessageUserCounter().get(sourceUserId)+1;
					item.getMessageUserCounter().put(sourceUserId,total);
				}else{
					
					item.getMessageUserCounter().put(sourceUserId,1);
				}
				
				if(!item.getMessageUser().contains(sourceUserId)){
					item.getMessageUser().add(sourceUserId);
				}
			}else{
				
				item.getMessageUserCounter().put(sourceUserId,1);
				if(!item.getMessageUser().contains(sourceUserId)){
					item.getMessageUser().add(sourceUserId);
				}
			}
		}
		userMapItemsService.saveOrUpdate(item);
	}
}
