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

import com.share.domain.core.UserDepartmentManage;
import com.share.exception.AppException;
import com.share.master.data.service.UserDepartmentService;



@RestController
@RequestMapping("/user/UserDepartmentManage/manage/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserDepartmentManageApi {

	
	
	@Autowired
	private UserDepartmentService service;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserDepartmentManage> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserDepartmentManage list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<UserDepartmentManage>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserDepartmentManage>> getByOrgId(
			@PathVariable(value="tenantId") String tenantId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserDepartmentManage> list=service.listAll(tenantId);
		System.out.println(list);
		return new ResponseEntity<List<UserDepartmentManage>>( list,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserDepartmentManage> update(@RequestBody UserDepartmentManage org)throws AppException{
		UserDepartmentManage orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<UserDepartmentManage>(orgs, headers, HttpStatus.CREATED);
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserDepartmentManage> create(@RequestBody UserDepartmentManage org)throws AppException{
		org.setId(UUID.randomUUID().toString());
		UserDepartmentManage orgs=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<UserDepartmentManage>(orgs, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserDepartmentManage>> getByUserAndTenant(
			@PathVariable(value="tenantId") String tenantId,@PathVariable(value="userId") String userId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserDepartmentManage> list=service.getByUserIdAndTenantId(userId, tenantId);
		System.out.println(list);
		return new ResponseEntity<List<UserDepartmentManage>>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}/{departmentId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserDepartmentManage>> getBydDepartmentAndTenant(
			@PathVariable(value="tenantId") String tenantId,@PathVariable(value="departmentId") String departmentId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserDepartmentManage> list=service.getByDepartmentIdAndTenantId(departmentId, tenantId);
		System.out.println(list);
		return new ResponseEntity<List<UserDepartmentManage>>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}/{departmentId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserDepartmentManage> getByUserAndDepartmentIdAndTenantId(
			@PathVariable(value="tenantId") String tenantId,@PathVariable(value="departmentId") String departmentId,
			@PathVariable(value="userId") String userId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserDepartmentManage list=service.getByDepartmentIdAndUserIdAndTenantId(departmentId, userId, tenantId);
		System.out.println(list);
		return new ResponseEntity<UserDepartmentManage>( list,headers,HttpStatus.OK);
	}
	
}
