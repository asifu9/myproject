package com.share.custom.form.api;

import java.util.Calendar;

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

import com.base.domain.ReportConfig;
import com.share.api.DateTimeCal;
import com.share.custom.form.service.ReportConfigService;
import com.share.exception.AppException;




@RestController
@RequestMapping("/reportconfig")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportConfigApi {

	
	
	@Autowired
	private ReportConfigService service;
	
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ReportConfig> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		ReportConfig list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<ReportConfig>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{tenantId}/{reportId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ReportConfig> getByTenantAndReportId(
			@PathVariable(value="tenantId") String tenantId,@PathVariable(value="reportId") String reportId)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		System.out.println("report id " + reportId + " : " + tenantId);
		ReportConfig list=service.getByReportAndTenantId(reportId, tenantId); 
		System.out.println(list);
		
		return new ResponseEntity<ReportConfig>( list,headers,HttpStatus.OK);
	}


	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ReportConfig> update(@RequestBody ReportConfig org)throws AppException{
		org.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		ReportConfig actt=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<ReportConfig>(actt, headers, HttpStatus.CREATED);
		
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ReportConfig> create(@RequestBody ReportConfig org)throws AppException{
		org.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		System.out.println("ok cool good");
		ReportConfig actt=service.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<ReportConfig>(actt, headers, HttpStatus.CREATED);
		
	}
	
	
}
