package com.share.api;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.enums.ReadStatus;
import com.share.domain.Page;
import com.share.exception.AppException;
import com.share.services.PageService;


@RestController
@RequestMapping("/Page")
@CrossOrigin(origins = "http://localhost:4200")
public class PageApi {

	@Autowired
	private PageService pageService;
	
	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Page> create(@RequestBody Page page)throws AppException{
		if(page.getId()==null){
			//its a new record to create
			page.setId(UUID.randomUUID().toString());
			page.setCreatedOn(Calendar.getInstance().getTime());
			page.setCreatedBy(UUID.randomUUID().toString());
			page.setDescription("my description");
			page.setStatus(ReadStatus.UNREAD);
			Set<String> followers=new HashSet<>();
			followers.add("follower1");
			followers.add("follower2");
			followers.add("follower3");
			page.setFollowers(followers);
		}else{
			//its an update record
			//first fetch the data for given id
//			User updateUser=pageService.getById(page.getId());
//			updateUser.setAge(page.getAge());
//			updateUser.setDob(page.getDob());
//			updateUser.setName(page.getName());
//			updateUser.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
//			page=updateUser;
		}
		
		pageService.saveOrUpdate(page);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Page>(page, headers, HttpStatus.CREATED);
		
	}
	
//	@RequestMapping(method=RequestMethod.GET,value="/age/{input}",produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<List<User>> getAllByAge(@PathVariable(value = "input") int input ){
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		return new ResponseEntity<List<User>>( pageService.listByAge(input),headers,HttpStatus.OK);
//	}
	
//	@RequestMapping(method=RequestMethod.GET,value="/",produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<List<Page>> getAll()throws AppException{
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		return new ResponseEntity<List<Page>>( pageService.listAll(),headers,HttpStatus.OK);
//	}
//	
//	@RequestMapping(method=RequestMethod.DELETE,value="/delete/{id}")
//	private ResponseEntity deleteById(@PathVariable(value="id") String id){
//		System.out.println("delete keuy is " + id);
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		User user=pageService.getById(id);
//		System.out.println("user is " + user);
//		pageService.delete(user);
//		return new ResponseEntity( headers,HttpStatus.OK);
//	}
}
