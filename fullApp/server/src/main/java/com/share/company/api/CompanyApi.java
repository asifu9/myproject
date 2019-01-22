package com.share.company.api;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.domain.Company;
import com.share.api.DateTimeCal;
import com.share.api.GenerateDefaultValues;
import com.share.company.service.CompanyService;
import com.share.exception.AppException;
import com.share.photo.service.PhotoService;

@RestController
@RequestMapping("/Company")
// @CrossOrigin(origins =
// {"http://localhost","http://localhost:4200","http://localhost:4200/"})
public class CompanyApi {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private GenerateDefaultValues defaultValues;

	@RequestMapping(method = RequestMethod.POST, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Company> create(@RequestBody Company user) throws AppException{
		// its a new record to create
		Calendar cal=Calendar.getInstance();
		//user.setCreatedOn(cal.getTime());
		user.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		// its an update record
		// first fetch the data for given id
		user.setIsActive(1);

		user.setId(UUID.randomUUID().toString());
		companyService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Company>(user, headers, HttpStatus.CREATED);

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/generate/setting", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Company> generateSetting(@RequestBody Company user)throws AppException {
		// its a new record to create
		
		defaultValues.createTicketCategory(user.getId());
		defaultValues.createTicketPriority(user.getId());
		defaultValues.createTicketStatus(user.getId());
		//defaultValues.createTicketCategory(user.getId());
//		defaultValues.generateClientEntry(user);
//		defaultValues.generateDefaultUser(user.getId());
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Company>(user, headers, HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Company> update(@RequestBody Company user)throws AppException {
		// its a new record to create
		user.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		// its an update record
		// first fetch the data for given id

		companyService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Company>(user, headers, HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Company>> getAllCOmpany() throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Company> users = companyService.listAll();
		return new ResponseEntity<List<Company>>(users, headers, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Company> getById(@PathVariable(name="id") String id)throws AppException {
		MultiValueMap<String, String> headers = new HttpHeaders();
		Company users = companyService.getById(id);
		return new ResponseEntity<Company>(users, headers, HttpStatus.OK);
	}
}
