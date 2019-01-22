package com.share.api;

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

import com.base.domain.Goals;
import com.share.exception.AppException;
import com.share.goal.service.GoalsService;



@RestController
@RequestMapping("/goals")
@CrossOrigin(origins = "http://localhost:4200")
public class GoalsApi {

	
	
	@Autowired
	private GoalsService goalService;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Goals> getById(
			@PathVariable(value="id") String id) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Goals list=goalService.getById(id); 
		System.out.println(list);

		
		return new ResponseEntity<Goals>( list,headers,HttpStatus.OK);
	}


	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Goals> update(@RequestBody Goals org) throws AppException{
		org.setLastUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		System.out.println("list for config list " + org);
		
		if(org!=null && org.getGoals()!=null){
			org.getGoals().forEach(goalItem->{
				if(goalItem.getId()==null){
					goalItem.setId(UUID.randomUUID().toString());
					goalItem.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
				}
				
				if(goalItem.getComments()!=null){
					goalItem.getComments().forEach(comment->{
						if(comment.getId()==null){
							comment.setId(UUID.randomUUID().toString());
							comment.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
						}
					});
				}
			});
		}
		goalService.saveOrUpdate(org);
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Goals>(org, headers, HttpStatus.CREATED);
		
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Goals> create(@RequestBody Goals org) throws AppException{
		org.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		if(org!=null && org.getGoals()!=null){
			org.getGoals().forEach(goalItem->{
				if(goalItem.getId()==null){
					goalItem.setId(UUID.randomUUID().toString());
					goalItem.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
				}
				
				if(goalItem.getComments()!=null){
					goalItem.getComments().forEach(comment->{
						if(comment.getId()==null){
							comment.setId(UUID.randomUUID().toString());
							comment.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
						}
					});
				}
			});
		}
		goalService.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Goals>(org, headers, HttpStatus.CREATED);
	}
	
	
}
