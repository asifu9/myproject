package com.share.project.api;

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

import com.base.enums.ProjectStatus;
import com.share.domain.core.Projects;
import com.share.exception.AppException;
import com.share.project.service.ProjectsService;



@RestController
@RequestMapping("/Projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectsApi {

	
	
	@Autowired
	private ProjectsService service;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Projects> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Projects list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<Projects>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Projects>> getByOrgId(
			@PathVariable(value="tenantId") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Projects> list=service.getByTenantId(id);
		System.out.println(list);
		return new ResponseEntity<List<Projects>>( list,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Projects> update(@RequestBody Projects org)throws AppException{
		Projects orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Projects>(orgs, headers, HttpStatus.CREATED);
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Projects> create(@RequestBody Projects org)throws AppException{
		org.setId(UUID.randomUUID().toString());
		Projects orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Projects>(orgs, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/user/{tenantId}/{ownerId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Projects>> getByTenantAndOwner(
			@PathVariable(value="tenantId") String tenantId,@PathVariable(value="ownerId") String ownerId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Projects> list=service.getByTenantIdAndOwnerId(tenantId, ownerId);
		System.out.println(list);
		return new ResponseEntity<List<Projects>>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/status/{tenantId}/{status}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Projects>> getByTenantAndStatus(
			@PathVariable(value="tenantId") String tenantId,
			@PathVariable(value="status") ProjectStatus status)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Projects> list=service.getByTenantIdAndStatus(tenantId, status);
		System.out.println(list);
		return new ResponseEntity<List<Projects>>( list,headers,HttpStatus.OK);
	}
	
}
