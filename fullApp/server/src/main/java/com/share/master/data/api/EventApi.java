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

import com.base.domain.Events;
import com.share.api.DateTimeCal;
import com.share.exception.AppException;
import com.share.master.data.service.UserService;
import com.share.services.EventService;


@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventApi {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;

	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Events> create(@RequestBody Events event)throws AppException{
		if(event.getId()==null){
			//its a new record to create
			event.setId(UUID.randomUUID().toString());
			event.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		
		}else{
			//its an update record
			//first fetch the data for given id
			event.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			
		}
		
		eventService.saveOrUpdate(event);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Events>(event, headers, HttpStatus.CREATED);
		
	}

	
	@RequestMapping(method=RequestMethod.GET,value="/{tenantId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Events>> getById(
			@PathVariable(value="tenantId") String id,@PathVariable(value="userId") String userId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Events> list=eventService.listByTenantIdAndCreatedBy(id, userId);
		System.out.println(list);
		
		return new ResponseEntity<List<Events>>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Events> getByIdOnly(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Events list=eventService.getById(id);
		System.out.println(list);
		
		return new ResponseEntity<Events>( list,headers,HttpStatus.OK);
	}
	

}
