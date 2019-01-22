package com.share.leave.api;

import java.util.Calendar;
import java.util.List;

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

import com.base.domain.Activities;
import com.google.gson.Gson;
import com.share.api.DateTimeCal;
import com.share.domain.core.UserLeaves;
import com.share.exception.AppException;
import com.share.master.data.service.ConfigActivitiesService;
import com.share.master.data.service.UserLeavesService;



@RestController
@RequestMapping("/userleaves")
@CrossOrigin(origins = "http://localhost:4200")
public class UserLeavesApi {

	
	
	@Autowired
	private UserLeavesService service;
	
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserLeaves> getById(
			@PathVariable(value="id") String id) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserLeaves list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<UserLeaves>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/user/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<UserLeaves>> getByUserId(
			@PathVariable(value="userId") String userId) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<UserLeaves> list=service.getByUserId(userId); 
		System.out.println(list);
		
		return new ResponseEntity<List<UserLeaves>>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{userId}/{year}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserLeaves> getByUserIdAndYear(
			@PathVariable(value="userId") String userId,@PathVariable(value="year") String year) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserLeaves list=service.getByUserIdAndFinancialYear(userId, year);
		System.out.println(list);
		
		return new ResponseEntity<UserLeaves>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{userId}/{companyId}/{year}/generate",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserLeaves> generateLeaves(
			@PathVariable(value="userId") String userId,
			@PathVariable(value="companyId") String companyId,
			@PathVariable(value="year") String year) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		UserLeaves leaves= service.createLeavesForUser(userId, companyId, Integer.parseInt(year));
		System.out.println(new Gson().toJson(leaves));
		
		return new ResponseEntity<UserLeaves>( leaves,headers,HttpStatus.OK);
	}
	
	
	


//	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<UserLeaves> update(@RequestBody UserLeaves org){
//		org.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		return new ResponseEntity<UserLeaves>(org, headers, HttpStatus.CREATED);
//		
//	}
	

//	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<Activities> create(@RequestBody UserL org){
////		org.setCreatedOn(Calendar.getInstance().getTime());
////		org.setActive(true);
////		System.out.println("ok cool good");
////		List<ConfigActivities> list= configActivityService.getTenantIdAndActive(org.getTenantId(), true);
////		System.out.println("list for config list " + list);
////		Activities act=service.createUpdate(list, org.getId(),org,org.getDefaultValue());
////		System.out.println("after conversion " + act);
////		act.setActive(true);
////		Activities actt=service.saveOrUpdate(act);
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		return new ResponseEntity<Activities>(org, headers, HttpStatus.CREATED);
//		
//	}
//	
	
}
