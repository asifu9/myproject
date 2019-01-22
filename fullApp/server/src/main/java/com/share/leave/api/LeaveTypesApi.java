package com.share.leave.api;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bouncycastle.crypto.tls.DatagramTransport;
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
import com.share.domain.core.LeaveTypes;
import com.share.exception.AppException;
import com.share.leave.service.LeaveTypesService;


@RestController
@RequestMapping("/leavetype")
@CrossOrigin(origins = "http://localhost:4200")
public class LeaveTypesApi {

	@Autowired
	private LeaveTypesService leaveTypeService;
	
	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<LeaveTypes> create(@RequestBody LeaveTypes leaveType)throws AppException{
			//its a new record to create
		leaveType.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		if(leaveType.getCount()>0){
			leaveType.setPerMonth((double)leaveType.getCount()/12);
		}
		leaveType.setId(UUID.randomUUID().toString());
		leaveTypeService.saveOrUpdate(leaveType);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<LeaveTypes>(leaveType, headers, HttpStatus.CREATED);
		
	}
	

	
	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<LeaveTypes> update(@RequestBody LeaveTypes user)throws AppException{
			//its a new record to create
			user.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		if(user.getCount()>0){
			user.setPerMonth((double)user.getCount()/12);
		}
		leaveTypeService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<LeaveTypes>(user, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<LeaveTypes> getById(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		LeaveTypes user=leaveTypeService.getById(id);
		
		return new ResponseEntity<LeaveTypes>(user ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity delete(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		leaveTypeService.delete(id);
		Map<String,String> result=new HashMap<>();
		result.put("status","success");
		return new ResponseEntity (result ,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/company/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<LeaveTypes>> getByTenantId(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<LeaveTypes> user=leaveTypeService.getByCompanyId(id);
		
		return new ResponseEntity<List<LeaveTypes>>(user ,headers,HttpStatus.OK);
	}
	
}
