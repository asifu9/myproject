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

import com.base.domain.ConfigValues;
import com.share.api.DateTimeCal;
import com.share.exception.AppException;
import com.share.master.data.service.ConfigValuesService;


@RestController
@RequestMapping("/ConfigValues")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigValuesApi {

	@Autowired
	private ConfigValuesService configValuesService;
	

	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ConfigValues> create(@RequestBody ConfigValues ticketCategory)throws AppException{
			//its a new record to create
		
		ticketCategory.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		ticketCategory.setActive(true);
		ticketCategory.setId(UUID.randomUUID().toString());
		configValuesService.saveOrUpdate(ticketCategory);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<ConfigValues>(ticketCategory, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ConfigValues> update(@RequestBody ConfigValues ticketCategory)throws AppException{
			//its a new record to create
		ticketCategory.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		
		configValuesService.saveOrUpdate(ticketCategory);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<ConfigValues>(ticketCategory, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ConfigValues> getById(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		ConfigValues ticketCategory=configValuesService.getById( id);
		
		return new ResponseEntity<ConfigValues>(ticketCategory ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{tenantId}/{isActive}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<ConfigValues>> getData(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "isActive") boolean isActive  )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<ConfigValues> ticketCategory=configValuesService.getByTenant(tenantId, isActive);
		
		return new ResponseEntity<List<ConfigValues>>(ticketCategory ,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/{tenantId}/{type}/{isActive}/{locale}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<ConfigValues>> getByTenantIdAndStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "type") String type,
			@PathVariable(value = "isActive") boolean isActive,
				@PathVariable(value = "locale") String locale  )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		if(locale==null){
			locale="en_us";
		}
		List<ConfigValues> ticketCategory=configValuesService.getByTenandTypeActive(tenantId, type, isActive, locale);
		
		return new ResponseEntity<List<ConfigValues>>(ticketCategory ,headers,HttpStatus.OK);
	}
	
}
