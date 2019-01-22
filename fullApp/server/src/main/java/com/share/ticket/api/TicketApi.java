package com.share.ticket.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

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
import com.share.domain.MapUserActions;
import com.share.domain.Ticket;
import com.share.exception.AppException;
import com.share.master.data.service.ConfigValuesService;
import com.share.master.data.service.MapUserActionsService;
import com.share.master.data.service.UserService;
import com.share.ticket.service.TicketService;
import com.share.util.UserCache;


@RestController
@RequestMapping("/Ticket")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketApi {

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MapUserActionsService userActionService;
	
	@Autowired
	private ConfigValuesService configService;
	
	 @Resource(name = "userCache")
	 UserCache userCache;

	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Ticket> create(@RequestBody Ticket ticket) throws AppException{
			//its a new record to create
		
		ticket.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		ticket.setActive(true);
		ticket.setId(UUID.randomUUID().toString());
		ticketService.saveOrUpdate(ticket);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Ticket>(ticket, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Ticket> update(@RequestBody Ticket ticket) throws AppException{
			//its a new record to create
		ticket.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		if(ticket!=null && ticket.getUpdates()!=null){
			ticket.getUpdates().forEach(i->{
				if(i.getCreatedOn()==0){
					i.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
				}
			});
		}
		ticketService.saveOrUpdate(ticket);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Ticket>(ticketService.getByIdFull(ticket.getId()), headers, HttpStatus.CREATED);
		
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Ticket> getById(@PathVariable(value = "id") String id ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();

		return new ResponseEntity<Ticket>(ticketService.getByIdFull(id) ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{tenantId}/{createdBy}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Ticket>> getByTenantIdAndStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "createdBy") String createdBy  ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Ticket> ticketCategory=ticketService.getByTenantIdAndCreatedById(tenantId, createdBy);
		if(ticketCategory!=null){
			ticketCategory.forEach(i->{
				if(i.getAssignedTo()!=null){
					i.setAssignedToUser(userCache.get(i.getAssignedTo()));
				}
			});
		}
		return new ResponseEntity<List<Ticket>>(ticketCategory ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/assigned/{tenantId}/{assignedTo}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Ticket>> getAssignedTickets(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "assignedTo") String assignedTo  ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Ticket> ticketCategory=ticketService.getByTenantIdAndAssignedTo(tenantId, assignedTo);
		if(ticketCategory!=null){
			ticketCategory.forEach(i->{
				if(i.getAssignedTo()!=null){
					i.setAssignedToUser(userCache.get(i.getAssignedTo()));
				}
			});
		}
		return new ResponseEntity<List<Ticket>>(ticketCategory ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/categories/{tenantId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<MapUserActions>> getCategories(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "userId") String userId  
			) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<MapUserActions> categories = userActionService.getByTenantAndTypeAndUser(tenantId, "TicketCategory", userId);
		
		
		return new ResponseEntity<List<MapUserActions>>(categories ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/new/{tenantId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Ticket>> getOthers(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "userId") String userId  
			) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Ticket> list=new ArrayList<>();
		List<MapUserActions> categories = userActionService.getByTenantAndTypeAndUser(tenantId, "TicketCategory", userId);
		System.out.println("categoryies " + categories);
		if(null!=categories){
			for(MapUserActions m:categories){
				System.out.println("tenantId " + tenantId + " : " + m.getTypeValue());
				List<Ticket> res=ticketService.getByTenantIdAndCategoryAndStatus(tenantId, m.getTypeValue(),"1");
				
				if(res!=null && res.size()>0){
					System.out.println("res " + res.size());
					list.addAll(res);
				}
			}
		}
		if(list!=null){
			if(list!=null){
				list.forEach(i->{
					if(i.getAssignedTo()!=null){
						i.setAssignedToUser(userCache.get(i.getAssignedTo()));
					}
				});
			}
		}
		
		return new ResponseEntity<List<Ticket>>(list ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/assign/{tenantId}/{userId}/{ticketId}/{status}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Ticket> assignTicket(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "userId") String userId,
			@PathVariable(value = "ticketId") String ticketId,
			@PathVariable(value = "status") String status
			) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		
		Ticket t=ticketService.getById(ticketId);
		
		t.setAssignedTo(userId);
		t.setUpdatedBy(userId);
		t.setStatus(status);
		t.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		ticketService.saveOrUpdate(t);
		
		return new ResponseEntity<Ticket>(ticketService.getByIdFull(ticketId) ,headers,HttpStatus.OK);
	}
	
	
}
