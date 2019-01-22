package com.share.custom.form.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

import com.base.domain.Form;
import com.google.gson.Gson;
import com.share.api.DateTimeCal;
import com.share.custom.form.service.FormService;
import com.share.exception.AppException;




@RestController
@RequestMapping("/customform")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomFormApi {

	
	
	@Autowired
	private FormService service;
	@Autowired
    MongoTemplate template;
	

	@RequestMapping(method=RequestMethod.GET,value="/{name}/{inputId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Object> getById(
			@PathVariable(value="name") String name,
		@PathVariable(value="inputId") String inputId) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Form f=service.getByName(name);
		Map<String, String> map=new HashMap<>();
		map.put("id", inputId);
		System.out.println("collection name " + f.getCollectionName() + " : " + inputId);
		Query q=new Query();
		Query query = new Query();
	    query.addCriteria(new Criteria("id").is(inputId));
		Object obj= template.findOne(query, Map.class, f.getCollectionName());
		System.out.println(obj);
		
		return new ResponseEntity<Object>( obj,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/{name}/{inputId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Object> delete(
			@PathVariable(value="name") String name,
		@PathVariable(value="inputId") String inputId) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Form f=service.getByName(name);
		Map<String, String> map=new HashMap<>();
		map.put("id", inputId);
		System.out.println("collection name " + f.getCollectionName() + " : " + inputId);
		Query q=new Query();
		Query query = new Query();
	    query.addCriteria(new Criteria("id").is(inputId));
		Object obj= template.remove(query, Map.class, f.getCollectionName());
		System.out.println(obj);
		return new ResponseEntity<Object>( obj,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/name/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Form> getByName(
			@PathVariable(value="name") String name)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Form list=service.getByName(name); 
		System.out.println(list);
		
		return new ResponseEntity<Form>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/data/name/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Object> getByFilters(
			@PathVariable(value="name") String name,@RequestBody Map<String,Object> filters)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Form form=service.getByName(name); 
		System.out.println(form);
		//Object result=template.findAll(Object.class, form.getCollectionName());
		Query query = new Query();
		if(filters!=null && filters.size()>0){
			filters.forEach((k,v)->{
				query.addCriteria(Criteria.where(k).is(v));
			});
		}
		System.out.println("query " + query);
		System.out.println("form nmae " + form.getCollectionName());
		Object result=template.find(query, Object.class, form.getCollectionName());
		
		return new ResponseEntity<Object>( result,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/data/form/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Object> getFormDataByName(
			@PathVariable(value="name") String name)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Form form=service.getByName(name); 
		System.out.println(form);
		Object result=template.findAll(Object.class, form.getCollectionName());
		
		return new ResponseEntity<Object>( result,headers,HttpStatus.OK);
	}


	@RequestMapping(method=RequestMethod.PUT,value="/{formName}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Object> update(@PathVariable(value="formName") String formName,@RequestBody Map<String,Object> org)throws AppException{
		System.out.println("ok here my data " + new Gson().toJson(org));
		org.put("updatedOn",DateTimeCal.getCurrentDateTimeSeconds());
		Form f=service.getByName(formName);
		template.save(org, f.getCollectionName());
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Object>(org, headers, HttpStatus.CREATED);
		
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/{formName}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Object> create(@PathVariable(value="formName") String formName,@RequestBody Map<String,Object> org)throws AppException{
		
		org.put("createdOn", DateTimeCal.getCurrentDateTimeSeconds());
		String id= UUID.randomUUID().toString();
		org.put("_id", id);
		org.put("id", id);
		Form f=service.getByName(formName);
		template.save(org, f.getCollectionName());
		System.out.println("ok cool good");
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Object>(org, headers, HttpStatus.CREATED);
		
	}
	
	
}
