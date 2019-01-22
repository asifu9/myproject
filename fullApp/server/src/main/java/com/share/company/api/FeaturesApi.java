package com.share.company.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
import com.base.domain.Features;
import com.share.api.DateTimeCal;
import com.share.company.service.FeaturesService;
import com.share.exception.AppException;


@RestController
@RequestMapping("/features")
@CrossOrigin(origins = "http://localhost:4200")
public class FeaturesApi {

	
	
	@Autowired
	private FeaturesService service;
	
	@Autowired
    MongoTemplate template;
	

	@RequestMapping(method=RequestMethod.GET,value="/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Features> getById(
			@PathVariable(value="name") String name) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Features f=service.getByName(name);
		return new ResponseEntity<Features>( f,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Features> getByName(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Features list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<Features>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Features>> getAll()throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Features> list=service.getAll();
		System.out.println(list);
		
		return new ResponseEntity<List<Features>>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Features> getByFilters(@RequestBody Features features)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		features.setId(UUID.randomUUID().toString());
		Features form=service.saveOrUpdate(features);
		return new ResponseEntity<Features>( form,headers,HttpStatus.OK);
	}
	

	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Features> update(@RequestBody Features features)throws AppException{
		features.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		Features f=service.saveOrUpdate(features);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Features>(f, headers, HttpStatus.CREATED);
	}
	

	@RequestMapping(method=RequestMethod.DELETE,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Features> create(@PathVariable(value="id") String id)throws AppException{
		Features f=service.getById(id);
		service.delete(f);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Features>(f, headers, HttpStatus.OK);
		
	}
	
	
}
