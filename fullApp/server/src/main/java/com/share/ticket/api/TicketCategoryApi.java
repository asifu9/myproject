package com.share.ticket.api;

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

import com.share.api.DateTimeCal;
import com.share.domain.TicketCategory;
import com.share.exception.AppException;
import com.share.master.data.service.UserService;
import com.share.ticket.service.TicketCategoryService;


@RestController
@RequestMapping("/TicketCategory")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketCategoryApi {

	@Autowired
	private TicketCategoryService ticketCategoryService;
	
	@Autowired
	private UserService userService;

	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<TicketCategory> create(@RequestBody TicketCategory ticketCategory)throws AppException{
			//its a new record to create
		
		ticketCategory.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		ticketCategory.setActive(true);
		ticketCategory.setId(UUID.randomUUID().toString());
		ticketCategoryService.saveOrUpdate(ticketCategory);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<TicketCategory>(ticketCategory, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<TicketCategory> update(@RequestBody TicketCategory ticketCategory)throws AppException{
			//its a new record to create
		ticketCategory.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		
		ticketCategoryService.saveOrUpdate(ticketCategory);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<TicketCategory>(ticketCategory, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<TicketCategory> getById(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		TicketCategory ticketCategory=ticketCategoryService.getById(id);
		
		return new ResponseEntity<TicketCategory>(ticketCategory ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity delete(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		ticketCategoryService.delete(id);
		
		return new ResponseEntity(headers ,headers,HttpStatus.OK);
	}


	
	@RequestMapping(method=RequestMethod.GET,value="/{tenantId}/{isActive}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<TicketCategory>> getByTenantIdAndStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "isActive") boolean isActive  )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<TicketCategory> ticketCategory=ticketCategoryService.getByTenantAndActive(tenantId, isActive);
		
		return new ResponseEntity<List<TicketCategory>>(ticketCategory ,headers,HttpStatus.OK);
	}
	
}
