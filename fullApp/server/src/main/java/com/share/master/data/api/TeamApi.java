package com.share.master.data.api;

import java.util.Calendar;
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

import com.share.domain.core.Team;
import com.share.exception.AppException;
import com.share.master.data.service.TeamService;
import com.share.util.SequenceGenerator;



@RestController
@RequestMapping("/team")
@CrossOrigin(origins = "http://localhost:4200")
public class TeamApi {

	
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private SequenceGenerator sequence;
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Team> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Team list=teamService.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<Team>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/tenant/{tenantId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Team>> getByTenantId(
			@PathVariable(value="tenantId") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Team> list=teamService.getByTenantId(id);
		System.out.println(list);
		return new ResponseEntity<List<Team>>( list,headers,HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Team> update(@RequestBody Team org)throws AppException{
		org.setUpdatedOn(Calendar.getInstance().getTime());
		Team orgs=teamService.saveOrUpdate(org);
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Team>(orgs, headers, HttpStatus.CREATED);
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Team> create(@RequestBody Team org)throws AppException{
		org.setId(sequence.getNextValue("TEAM-", org.getTenantId()));
		org.setCreatedOn(Calendar.getInstance().getTime());
		Team orgs=teamService.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Team>(orgs, headers, HttpStatus.CREATED);
		
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity deleteById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		teamService.delete(id);
		Map<String,String> result=new HashMap<>();
		result.put("status", "success");
		return new ResponseEntity(result,headers,HttpStatus.OK);
	}
	
	
}
