package com.share.master.data.api;

import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.domain.Roles;
import com.share.api.DateTimeCal;
import com.share.exception.AppException;
import com.share.master.data.service.RoleService;



@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "http://localhost:4200")
public class RolesApi {

	
	
	@Autowired
	private RoleService rolesService;
	
	

	@RequestMapping(method=RequestMethod.GET,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Roles> getById(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		Roles list=rolesService.getById(id); 
		System.out.println(list);
		if(list==null){
			list=new Roles();
		}
		
		return new ResponseEntity<Roles>( list,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(
			@PathVariable(value="id") String id)throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		rolesService.delete(id); 
		Map<String,String> result=new HashMap<>();
		result.put("status", "success");
		return new ResponseEntity( result,headers,HttpStatus.OK);
	}



	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Roles> update(@RequestBody Roles org)throws AppException{
		org.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		Roles actt=rolesService.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Roles>(actt, headers, HttpStatus.CREATED);
		
	}
//	public static void main(String[] args) {
//		 String str = new String(DatatypeConverter.parseBase64Binary("web-client:web-client-secret"));
//         String res = DatatypeConverter.printBase64Binary(str.getBytes());
//         System.out.println(res);
//         
//         String clientIdPlusSecret = "client1:client1-secret";
// 		byte[] authStr = Base64.getEncoder().encode(clientIdPlusSecret.getBytes());
// 		Map<String, String> headers = new HashMap<>();
// 		System.out.println(new String(authStr));
//	}

	//@PreAuthorize("#oauth2.hasScope('read-write') and hasRole('ROLE_canCreateRole')")
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Roles> create(@RequestBody Roles org)throws AppException{
		org.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		if(org.getIsActive()==0){
			org.setIsActive(1);
		}
		Roles actt=rolesService.saveOrUpdate(org);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Roles>(actt, headers, HttpStatus.CREATED);
		
	}
	@RequestMapping(method=RequestMethod.GET,value="/wall/{wallId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Roles>> getData(@PathVariable(value="wallId") String wallId)throws AppException{
		
		List<Roles> actt=rolesService.getTenantIdAndActive(wallId, 1);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<List<Roles>>(actt, headers, HttpStatus.CREATED);
		
	}
	
}
