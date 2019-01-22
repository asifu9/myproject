package com.share.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.base.domain.Company;
import com.base.domain.ConfigValues;
import com.base.domain.MongoClientDetails;
import com.base.domain.User;
import com.base.domain.UserCredential;
import com.google.common.collect.Sets;
import com.share.exception.AppException;
import com.share.master.data.service.ConfigValuesService;

@Service
public class GenerateDefaultValues {
	
	@Autowired
	private ConfigValuesService service;
	
	@Autowired
	private MongoTemplate template;
	
    @SuppressWarnings("unchecked")
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
    
	public void generateClientEntry(Company comp){
      MongoClientDetails clientDetails = new MongoClientDetails();
      clientDetails.setClientId(comp.getUname());
      clientDetails.setClientSecret(getPasswordEncoder().encode(comp.getUname()));
      clientDetails.setSecretRequired(true);
      clientDetails.setResourceIds(Sets.newHashSet("myResourceId"));
      clientDetails.setScope(Sets.newHashSet("read-write"));
      clientDetails.setAuthorizedGrantTypes(Sets.newHashSet("authorization_code", "refresh_token",
              "password", "client_credentials"));
      clientDetails.setRegisteredRedirectUri(Sets.newHashSet("http://localhost:4200/resource-service"));
      clientDetails.setAuthorities(getListOfGrantedAuthorities());
      clientDetails.setAccessTokenValiditySeconds(60);
      clientDetails.setRefreshTokenValiditySeconds(14400);
      clientDetails.setAutoApprove(false);
      template.save(clientDetails);
	}
	
	public void generateDefaultUser(String companyId){
      User u=new User();
      u.setName("Super User");
      u.setUserName("superuser");
      u.setId(UUID.randomUUID().toString());
      u.setTenantId(companyId);
      u.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
      u.setEmailId("email@sss.com");
      template.save(u);
      
   // init the users
      UserCredential mongoUser = new UserCredential();
      mongoUser.setId(u.getId());
      mongoUser.setUsername("superuser");
      mongoUser.setTenantId(companyId);
      mongoUser.setPassword(getPasswordEncoder().encode("superuser"));
      mongoUser.setRoles(Sets.newHashSet("USER","ADMIN","canCreateRole"));
      template.save(mongoUser);
	}

	  List<GrantedAuthority> getListOfGrantedAuthorities(){
	    	List<GrantedAuthority> list=new ArrayList<>();
	    	list.add(new SimpleGrantedAuthority("USER"));
	    	list.add(new SimpleGrantedAuthority("ADMIN"));
	    	list.add(new SimpleGrantedAuthority("canCreateRole"));
	    	list.add(new SimpleGrantedAuthority("canUpdateRole"));
	    	list.add(new SimpleGrantedAuthority("canDeleteRole"));
	    	
	    	
	    	return list;
	    }
	public void createTicketPriority(String companyId)throws AppException{
		Map<String,String> maps=new HashMap<>();
		maps.put("1", "Low");
		maps.put("2", "Normal");
		maps.put("3", "High");
		maps.put("4", "Critical");

		for(Entry<String, String> value: maps.entrySet()){
			ConfigValues value1=new ConfigValues();
			value1.setActive(true);
			value1.setStoredValue(value.getKey());
			value1.setDisplayValue(value.getValue());
			value1.setLocale("en_us");
			value1.setType("TicketPriority");
			value1.setTenantId(companyId);
			service.saveOrUpdate(value1);
		}
		
	}
	
	public void createTicketStatus(String companyId)throws AppException{
		Map<String,String> maps=new HashMap<>();
		maps.put("1", "New");
		maps.put("2", "Assigned");
		maps.put("3", "Work In Progress");
		maps.put("4", "Resolved");
		maps.put("5", "Verification");
		maps.put("6", "Close");
		maps.put("7", "Reopen");
		maps.put("8", "Defered");

		for(Entry<String, String> value: maps.entrySet()){
			ConfigValues value1=new ConfigValues();
			value1.setActive(true);
			value1.setStoredValue(value.getKey());
			value1.setDisplayValue(value.getValue());
			value1.setLocale("en_us");
			value1.setType("TicketStatus");
			value1.setTenantId(companyId);
			service.saveOrUpdate(value1);
			
		}
		
	}
	
	public void createTicketCategory(String companyId)throws AppException{
		Map<String,String> maps=new HashMap<>();
		maps.put("1", "HR");
		maps.put("2", "FM");
		maps.put("3", "IT");
		maps.put("4", "GYM");

		for(Entry<String, String> value: maps.entrySet()){
			ConfigValues value1=new ConfigValues();
			value1.setActive(true);
			value1.setStoredValue(value.getKey());
			value1.setDisplayValue(value.getValue());
			value1.setLocale("en_us");
			value1.setType("TicketCategory");
			value1.setTenantId(companyId);
			service.saveOrUpdate(value1);
			
		}
		
	}
}
