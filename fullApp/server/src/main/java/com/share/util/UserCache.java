package com.share.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.domain.User;
import com.base.util.LRUMap;
import com.share.exception.AppException;
import com.share.master.data.service.UserService;



@Service
public class UserCache {
	

	@Autowired
	private UserService userService;
	
	  private static  LRUMap<String, User> map;
	  
	    public UserCache(){
	    	map = new LRUMap<String, User>(30);
	    }
	    
	    public User get(String key) {
	    	User value = map.get(key);
	        if(value==null) {
	        	//fetch from db
	        	try {
					value=userService.getByIdWithPhoto(key);
				} catch (AppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        	
	        
	        map.remove(key);
	        map.put(key, value);
	        return value;
	    }
	        
	    public void set(String key, User value) {
	        if(map == null) return;
	        get(key);
	        map.put(key, value);
	    }
	    
	    
	}