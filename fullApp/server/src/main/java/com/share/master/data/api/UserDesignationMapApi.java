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

import com.share.domain.core.UserDesignationMap;
import com.share.exception.AppException;
import com.share.master.data.service.UserDesignationMapService;



@RestController
@RequestMapping("/user/designation/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserDesignationMapApi {

	
	
	@Autowired
	private UserDesignationMapService service;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserDesignationMap> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserDesignationMap list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<UserDesignationMap>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserDesignationMap>> getByOrgId(
			@PathVariable(value="tenantId") String tenantId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserDesignationMap> list=service.getByTenantId(tenantId);
		System.out.println(list);
		return new ResponseEntity<List<UserDesignationMap>>( list,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserDesignationMap> update(@RequestBody UserDesignationMap org)throws AppException{
		UserDesignationMap orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<UserDesignationMap>(orgs, headers, HttpStatus.CREATED);
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserDesignationMap> create(@RequestBody UserDesignationMap org)throws AppException{
		org.setId(UUID.randomUUID().toString());
		UserDesignationMap orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<UserDesignationMap>(orgs, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/user/{tenantId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserDesignationMap>> getByUserAndTenant(
			@PathVariable(value="tenantId") String tenantId,@PathVariable(value="userId") String userId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserDesignationMap> list=service.getByTenantIdAndUserId(tenantId, userId);
		System.out.println(list);
		return new ResponseEntity<List<UserDesignationMap>>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}/{designationId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserDesignationMap>> getBydDepartmentAndTenant(
			@PathVariable(value="tenantId") String tenantId,@PathVariable(value="designationId") String designationId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserDesignationMap> list=service.getByTenantIdAndDesignationId(tenantId, designationId);
		System.out.println(list);
		return new ResponseEntity<List<UserDesignationMap>>( list,headers,HttpStatus.OK);
	}
	

	
}
