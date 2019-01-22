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

import com.share.domain.core.UserOrgDesgMap;
import com.share.master.data.service.UserOrgDesgMapService;



@RestController
@RequestMapping("/userorgdesignationmap")
@CrossOrigin(origins = "http://localhost:4200")
public class UserOrgDesgMapApi {

	
	
	@Autowired
	private UserOrgDesgMapService service;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserOrgDesgMap> getById(
			@PathVariable(value="id") String id){
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserOrgDesgMap list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<UserOrgDesgMap>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/{tenantId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserOrgDesgMap>> getByOrgId(
			@PathVariable(value="tenantId") String tenantId,
			@PathVariable(value="userId") String userId){
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserOrgDesgMap> list=service.getByTenantAndUserId(tenantId, userId);
		System.out.println(list);
		return new ResponseEntity<List<UserOrgDesgMap>>( list,headers,HttpStatus.OK);
	}


	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserOrgDesgMap> create(@RequestBody UserOrgDesgMap org){
		org.setId(UUID.randomUUID().toString());
		UserOrgDesgMap orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<UserOrgDesgMap>(orgs, headers, HttpStatus.CREATED);
		
	}
	
	
}
