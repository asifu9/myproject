package com.share.custom.form.api;

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

import com.share.api.DateTimeCal;
import com.share.domain.core.Reminders;
import com.share.exception.AppException;
import com.share.master.data.service.RemindersService;



@RestController
@RequestMapping("/reminders")
@CrossOrigin(origins = "http://localhost:4200")
public class RemindersApi {

	
	
	@Autowired
	private RemindersService service;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Reminders> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Reminders list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<Reminders>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}/{type}/{typeValue}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Reminders> getOneByDetails(
			@PathVariable(value="tenantId") String id,
			@PathVariable(value="type") String type,
			@PathVariable(value="typeValue") String typeValue)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Reminders list=service.getByTenantIdAndTypeAndTypeValue(id, type, typeValue);
		System.out.println(list);
		return new ResponseEntity<Reminders>( list,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Reminders> update(@RequestBody Reminders org)throws AppException{
		org.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		Reminders orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Reminders>(orgs, headers, HttpStatus.CREATED);
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Reminders> create(@RequestBody Reminders org)throws AppException{
		org.setId(UUID.randomUUID().toString());
		Reminders orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Reminders>(orgs, headers, HttpStatus.CREATED);
		
	}
	
	
}
