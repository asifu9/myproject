package com.share.master.data.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.base.domain.Department;
import com.share.exception.AppException;
import com.share.master.data.service.DepartmentService;
import com.share.util.SequenceGenerator;



@RestController
@RequestMapping("/department")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentApi {

	
	
	@Autowired
	private DepartmentService service;
	
	@Autowired
	private SequenceGenerator sequence;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Department> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Department list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<Department>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Department>> getByOrgId(
			@PathVariable(value="tenantId") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Department> list=service.getByTenantId(id);
		System.out.println(list);
		return new ResponseEntity<List<Department>>( list,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Department> update(@RequestBody Department org)throws AppException{
		Department orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Department>(orgs, headers, HttpStatus.CREATED);
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Department> create(@RequestBody Department org)throws AppException{
		org.setId(sequence.getNextValue("DEPARTMENT-", org.getTenantId()));
		Department orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Department>(orgs, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity deleteById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		service.delete(id);
		Map<String,String> result=new HashMap<>();
		result.put("status", "success");
		return new ResponseEntity(result,headers,HttpStatus.OK);
	}

	
	
}
