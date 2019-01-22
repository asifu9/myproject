package com.share.master.data.api;

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

import com.share.domain.core.UserProjectMap;
import com.share.exception.AppException;
import com.share.master.data.service.UserProjectMapService;



@RestController
@RequestMapping("/user/project/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserProjectMapApi {

	
	
	@Autowired
	private UserProjectMapService service;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserProjectMap> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserProjectMap list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<UserProjectMap>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserProjectMap>> getByOrgId(
			@PathVariable(value="tenantId") String tenantId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserProjectMap> list=service.getByTenantId(tenantId);
		System.out.println(list);
		return new ResponseEntity<List<UserProjectMap>>( list,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserProjectMap> update(@RequestBody UserProjectMap org)throws AppException{
		UserProjectMap orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<UserProjectMap>(orgs, headers, HttpStatus.CREATED);
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserProjectMap> create(@RequestBody UserProjectMap org)throws AppException{
		org.setId(UUID.randomUUID().toString());
		UserProjectMap orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<UserProjectMap>(orgs, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/user/{tenantId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserProjectMap>> getByUserAndTenant(
			@PathVariable(value="tenantId") String tenantId,@PathVariable(value="userId") String userId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserProjectMap> list=service.getByTenantIdAndUserId(tenantId, userId);
		System.out.println(list);
		return new ResponseEntity<List<UserProjectMap>>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}/{projectId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserProjectMap>> getBydDepartmentAndTenant(
			@PathVariable(value="tenantId") String tenantId,@PathVariable(value="projectId") String projectId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserProjectMap> list=service.getByTenantIdAndProjectId(tenantId, projectId);
		System.out.println(list);
		return new ResponseEntity<List<UserProjectMap>>( list,headers,HttpStatus.OK);
	}
	

	
}
