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

import com.share.domain.MapUserActions;
import com.share.exception.AppException;
import com.share.master.data.service.MapUserActionsService;
import com.share.master.data.service.UserService;


@RestController
@RequestMapping("/mapuseraction")
@CrossOrigin(origins = "http://localhost:4200")
public class MapUserActionsApi {

	@Autowired
	private MapUserActionsService mapUserActionService;
	
	@Autowired
	private UserService userService;

	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<MapUserActions> create(@RequestBody MapUserActions mapUserAction) throws AppException{
			//its a new record to create
		
		mapUserAction.setCreatedOn(Calendar.getInstance().getTime());
			//its an update record
			//first fetch the data for given id
		mapUserAction.setActive(true);
		mapUserAction.setId(UUID.randomUUID().toString());
		mapUserActionService.saveOrUpdate(mapUserAction);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<MapUserActions>(mapUserAction, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<MapUserActions> update(@RequestBody MapUserActions mapUserActions) throws AppException{
			//its a new record to create
		mapUserActions.setUpdatedOn(Calendar.getInstance().getTime());
			//its an update record
			//first fetch the data for given id
		
		mapUserActionService.saveOrUpdate(mapUserActions);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<MapUserActions>(mapUserActions, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<MapUserActions> delete(@PathVariable(value = "id") String id) throws AppException{
			//its a new record to create
			//its an update record
			//first fetch the data for given id
		MapUserActions action= mapUserActionService.getById(id);
		mapUserActionService.deleteMap(action);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<MapUserActions>(action, headers, HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<MapUserActions> getById(@PathVariable(value = "id") String id ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		MapUserActions ticketCategory=mapUserActionService.getById(id);
		
		return new ResponseEntity<MapUserActions>(ticketCategory ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{tenantId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<MapUserActions>> getByTenantIdAndStatus(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "userId") String userId  ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<MapUserActions> ticketCategory=mapUserActionService.getByTenantAndUserId(tenantId, userId);
		
		return new ResponseEntity<List<MapUserActions>>(ticketCategory ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/action/{tenantId}/{type}/{typedValue}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<MapUserActions>> getByTenantIdAndType(@PathVariable(value = "tenantId") String tenantId,
			@PathVariable(value = "type") String type ,@PathVariable(value = "typedValue") String typedValue  ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		System.out.println("ok here is " + tenantId + " : " +type);
		List<MapUserActions> ticketCategory=mapUserActionService.getByTenantAndTypedValueAndType(tenantId,typedValue, type);
		if(ticketCategory!=null && ticketCategory.size()>0){
			for(MapUserActions i : ticketCategory){
				i.setUser(userService.getByIdOnFewFields(i.getUserId()));
			};
		}
		System.out.println("result is " + ticketCategory);
		return new ResponseEntity<List<MapUserActions>>(ticketCategory ,headers,HttpStatus.OK);
	}
	
}
