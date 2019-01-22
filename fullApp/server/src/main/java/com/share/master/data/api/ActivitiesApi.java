package com.share.master.data.api;

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
import com.base.domain.ConfigActivities;
import com.share.api.DateTimeCal;
import com.share.exception.AppException;
import com.share.master.data.service.ActivityService;
import com.share.master.data.service.ConfigActivitiesService;



@RestController
@RequestMapping("/activities")
@CrossOrigin(origins = "http://localhost:4200")
public class ActivitiesApi {

	
	
	@Autowired
	private ActivityService activitiesService;
	
	@Autowired
	private ConfigActivitiesService configActivityService;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Activities> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Activities list=activitiesService.getById(id); 
		System.out.println(list);
		if(list==null){
			list=new Activities();
		}
		
		return new ResponseEntity<Activities>( list,headers,HttpStatus.OK);
	}


	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Activities> update(@RequestBody Activities org)throws AppException{
		org.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		List<ConfigActivities> list= configActivityService.getTenantIdAndActive(org.getTenantId(), true);
		System.out.println("list for config list " + list);
		Activities act=activitiesService.createUpdate(list, org.getId(),org,org.getDefaultValue());
		System.out.println("after conversion " + act);
		act.setActive(true);
		Activities actt=activitiesService.saveOrUpdate(act);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Activities>(actt, headers, HttpStatus.CREATED);
		
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Activities> create(@RequestBody Activities org)throws AppException{
		org.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		org.setActive(true);
		System.out.println("ok cool good");
		List<ConfigActivities> list= configActivityService.getTenantIdAndActive(org.getTenantId(), true);
		System.out.println("list for config list " + list);
		Activities act=activitiesService.createUpdate(list, org.getId(),org,org.getDefaultValue());
		System.out.println("after conversion " + act);
		act.setActive(true);
		Activities actt=activitiesService.saveOrUpdate(act);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Activities>(actt, headers, HttpStatus.CREATED);
		
	}
	
	
}
