package com.share.custom.form.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

import com.base.domain.Column;
import com.base.domain.Form;
import com.base.domain.MetaData;
import com.base.domain.ReportConfig;
import com.share.api.DateTimeCal;
import com.share.custom.form.service.FormService;
import com.share.custom.form.service.ReportConfigService;
import com.share.exception.AppException;





@RestController
@RequestMapping("/form")
@CrossOrigin(origins = "http://localhost:4200")
public class FormApi {

	
	
	@Autowired
	private FormService service;
	
	@Autowired
	private ReportConfigService reportService;
	
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Form> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Form list=service.getById(id); 
		System.out.println(list);
		
		return new ResponseEntity<Form>( list,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/name/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Form> getByName(
			@PathVariable(value="name") String name)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Form list=service.getByName(name); 
		Collections.sort(list.getMetaData());
		System.out.println(list);
		
		return new ResponseEntity<Form>( list,headers,HttpStatus.OK);
	}


	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Form> update(@RequestBody Form org)throws AppException{
		org.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		Form actt=service.saveOrUpdate(org);
		buildReporting(actt,false);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Form>(actt, headers, HttpStatus.CREATED);
		
	}
	
	private void buildReporting(Form form,boolean createFlow)throws AppException{
		ReportConfig conf=new ReportConfig();
		if(createFlow){
			conf.setCreatedBy(form.getCreatedBy());
			conf.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		}else{
			conf.setUpdatedBy(form.getUpdatedBy());
			conf.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		}
		conf.setReportId(form.getName());
		conf.setId(form.getId());
		conf.setTenantId(form.getTenantId());
		int index=0;
		List<Column> cols=new ArrayList<>();
		for(MetaData c:form.getMetaData()){
			System.out.println("c value "+c.isReporting());
			if(c.isReporting()){
				Column col=new Column();
				col.setColumnDisplayName(c.getWidgetDisplayName());
				col.setColumnName(c.getWidgetName());
				col.setColumnType(c.getWidgetType());
				col.setFilterType(c.getWidgetDataType());
				col.setIndex(index);
				cols.add(col);
				index+=1;
			}
		}
		conf.setColumns(cols);
		reportService.saveOrUpdate(conf);
		
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Form> create(@RequestBody Form org)throws AppException{
		org.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		org.setId(UUID.randomUUID().toString());
		System.out.println("ok cool good");
		Form actt=service.saveOrUpdate(org);
		buildReporting(actt,true);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Form>(actt, headers, HttpStatus.CREATED);
		
	}
	
	
}
