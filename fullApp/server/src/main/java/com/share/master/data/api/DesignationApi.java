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

import com.base.domain.Designation;
import com.share.exception.AppException;
import com.share.master.data.service.OrgDesignationService;
import com.share.util.SequenceGenerator;



@RestController
@RequestMapping("/designation")
@CrossOrigin(origins = "http://localhost:4200")
public class DesignationApi {

	
	
	@Autowired
	private OrgDesignationService organizationService;
	
	@Autowired
	private SequenceGenerator sequence;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Designation> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Designation list=organizationService.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<Designation>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Designation>> getByOrgId(
			@PathVariable(value="tenantId") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Designation> list=organizationService.getByTenantId(id);
		System.out.println(list);
		return new ResponseEntity<List<Designation>>( list,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Designation> update(@RequestBody Designation org)throws AppException{
		Designation orgs=organizationService.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Designation>(orgs, headers, HttpStatus.CREATED);
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Designation> create(@RequestBody Designation org)throws AppException{
		org.setId(sequence.getNextValue("DESIGNATION-", org.getTenantId()));
		Designation orgs=organizationService.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Designation>(orgs, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	private ResponseEntity delete(@PathVariable(value="id") String id)throws AppException{
		organizationService.delete(id);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity(id, headers, HttpStatus.OK);
		
	}
	
}
