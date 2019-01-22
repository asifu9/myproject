package com.share.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.base.domain.Company;
import com.base.domain.User;
import com.base.domain.UserCredential;
import com.google.gson.Gson;
import com.share.company.service.CompanyService;
import com.share.company.service.CompanySettingService;
import com.share.exception.AppException;
import com.share.master.data.service.UserCredentialService;
import com.share.master.data.service.UserService;


@RestController
@RequestMapping("/UserLogin")
@CrossOrigin(origins = {"http://localhost","http://localhost:4200","http://localhost:4200/"})
public class UserLoginApi {

	@Autowired
	private UserCredentialService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService compService;
	
	@Autowired
	private CompanySettingService companySettingService;

	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<UserCredential> create(@RequestBody UserCredential user)throws AppException{
			//its a new record to create
			//its an update record
			//first fetch the data for given id
		
		user.setId(UUID.randomUUID().toString());
		service.createUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<UserCredential>(user, headers, HttpStatus.CREATED);
		
	}
	
	   @Bean
	    public static PasswordEncoder getPasswordEncoder(){
	    	String idForEncode = "bcrypt";
	    	Map encoders = new HashMap<>();
	    	encoders.put(idForEncode, new BCryptPasswordEncoder());
	    	encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
	    	encoders.put("scrypt", new SCryptPasswordEncoder());

	    	return 
	    	    new DelegatingPasswordEncoder(idForEncode, encoders);
	    }  
	
	@RequestMapping(method=RequestMethod.POST,value="/login",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Map<String,Object>> getByWallId(@RequestBody Map<String, String> body)throws AppException{
		System.out.println("ok in login ok cool");
		MultiValueMap<String, String> headers = new HttpHeaders();
		Map<String,Object> result=new HashMap<String, Object>();
		User user=null;
		UserCredential credential =service.login(body.get("userName"));
		System.out.println("new credentaila "+new Gson().toJson(credential));
		if(null!=credential ){
			if(getPasswordEncoder().matches(credential.getPassword(), getPasswordEncoder().encode(body.get("password")))){
				
			}
			user = userService.getByIdWithPhoto(credential.getId());
			System.out.println("now fetching company details "+body.get("comp"));
			 Company comps=compService.getByUniqueName(body.get("comp"));
			 System.out.println("compan " + new Gson().toJson(comps));
			 if(comps!=null && user!=null && comps.getId().equals(user.getTenantId())){
				result.put("token", getTokenDetails(body.get("userName"),body.get("password")));
				result.put("user", user);
				result.put("company", comps);
				result.put("roles",credential.getRoles());
				result.put("setting", companySettingService.getById(user.getTenantId()));
			 }
			 
			 
			

		}
		return new ResponseEntity<Map<String,Object>>(result ,headers,HttpStatus.OK);
	}



	private DefaultOAuth2AccessToken getTokenDetails(String uname,String password) {
		// TODO Auto-generated method stub

		RestTemplate restTemplate = new RestTemplate();

		// According OAuth documentation we need to send the client id and secret key in the header for authentication
		String credentials = "client1:client1-secret";
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		HttpEntity<String> request = new HttpEntity<String>(headers);

		String access_token_url = "http://localhost:7001/auth-service/oauth/token";
		//access_token_url += "?code=" + code;
		access_token_url += "?grant_type=password&"+"username="+uname+"&password="+password;
		access_token_url += "&redirect_uri=http://localhost:7001/asif/login";
		System.out.println("access token url "+access_token_url);
		System.out.println("encodedCredentials "+encodedCredentials);
		DefaultOAuth2AccessToken res=restTemplate.postForEntity(access_token_url, request, DefaultOAuth2AccessToken.class).getBody();
		System.out.println("got token is here " + new Gson().toJson(res));
		return res;
		
	}
	
	
}
