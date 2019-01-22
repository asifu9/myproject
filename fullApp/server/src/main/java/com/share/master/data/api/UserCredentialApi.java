package com.share.master.data.api;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.base.domain.UserCredential;
import com.share.api.DateTimeCal;
import com.share.exception.AppException;
import com.share.master.data.service.UserCredentialService;


@RestController
@RequestMapping("/UserCredential")
@Component
//@CrossOrigin(origins = {"http://localhost","http://localhost:4200","http://localhost:4200/"})
public class UserCredentialApi {

	@Autowired
	private UserCredentialService userService;
	

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
    @Bean
    public static PasswordEncoder getPasswordDecoder(){
    	String idForEncode = "bcrypt";
    	Map encoders = new HashMap<>();
    	encoders.put(idForEncode, new BCryptPasswordEncoder());
    	encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
    	encoders.put("scrypt", new SCryptPasswordEncoder());

    	return 
    	    new DelegatingPasswordEncoder(idForEncode, encoders);
    }   
    
	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> create(@RequestBody UserCredential user) throws Exception{
			//its a new record to create
			user.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		UserCredential existingData = userService.getById(user.getId());
		Object result=null;
		HttpStatus status=HttpStatus.CREATED;
		System.out.println("etPasswordEncoder().encode(user.getPassword() "+getPasswordEncoder().encode(user.getPassword()));
		System.out.println("existingData.getPassword() "+existingData.getPassword());
		if(getPasswordEncoder().matches(user.getCurrentPassword(), existingData.getPassword())  ){
			user.setPassword(getPasswordEncoder().encode(user.getPassword()));
			result=userService.createUpdate(user);
		}else{
			status=HttpStatus.INTERNAL_SERVER_ERROR;
			result="Current password not matching";
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Object>(result, headers, status);
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update/roles/{userId}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateRoles(@PathVariable(value="userId") String userId,@RequestBody Set<String> roles) throws Exception{
			//its a new record to create
			
			//its an update record
			//first fetch the data for given id
		UserCredential existingData = userService.getById(userId);
		Object result=null;
		HttpStatus status=HttpStatus.CREATED;
		existingData.setRoles(roles);
		userService.createUpdate(existingData);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Object>(result, headers, status);
		
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserCredential> getByWallId(@PathVariable(value = "userId") String userId )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserCredential users=userService.getById(userId);
		users.setPassword("");
		return new ResponseEntity<UserCredential>(users ,headers,HttpStatus.OK);
	}
	

	
}
