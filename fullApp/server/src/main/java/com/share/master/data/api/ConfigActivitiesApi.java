package com.share.master.data.api;

import java.util.Calendar;
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

import com.base.domain.ConfigActivities;
import com.share.api.DateTimeCal;
import com.share.exception.AppException;
import com.share.master.data.service.ConfigActivitiesService;



@RestController
@RequestMapping("/config/activities")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigActivitiesApi {

	
	
	@Autowired
	private ConfigActivitiesService service;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ConfigActivities> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		ConfigActivities list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<ConfigActivities>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}/{isActive}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<ConfigActivities>> getByOrgId(
			@PathVariable(value="tenantId") String tenantId,@PathVariable(value="isActive") boolean isActive)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<ConfigActivities> list=service.getTenantIdAndActive(tenantId, isActive);
		System.out.println(list);
		return new ResponseEntity<List<ConfigActivities>>( list,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ConfigActivities> update(@RequestBody ConfigActivities org)throws AppException{
		org.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		ConfigActivities orgs=service.saveOrUpdate(org);
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<ConfigActivities>(orgs, headers, HttpStatus.CREATED);
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ConfigActivities> create(@RequestBody ConfigActivities org)throws AppException{
		org.setId(UUID.randomUUID().toString());
		org.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		org.setActive(true);
		ConfigActivities orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<ConfigActivities>(orgs, headers, HttpStatus.CREATED);
		
	}
	
	
}
