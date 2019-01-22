package com.share.master.data.api;

import java.sql.Timestamp;
import java.util.List;
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

import com.base.domain.Address;
import com.share.api.DateTimeCal;
import com.share.exception.AppException;
import com.share.master.data.service.AddressService;
import com.share.master.data.service.UserService;


@RestController
@RequestMapping("/Address")
@CrossOrigin(origins = "http://localhost:4200")
public class AddressApi {

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private UserService userService;

	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Address> create(@RequestBody Address user)throws AppException{
			//its a new record to create
			user.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		
		user.setId(UUID.randomUUID().toString());
		addressService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Address>(user, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Address> update(@RequestBody Address user)throws AppException{
			//its a new record to create
			user.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		
		addressService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Address>(user, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Address>> getById(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Address> user=addressService.getByUserId(id);
		
		return new ResponseEntity<List<Address>>(user ,headers,HttpStatus.OK);
	}
	
}
