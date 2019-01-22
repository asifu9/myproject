package com.auth.oauth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import com.auth.oauth.config.domain.MongoAccessToken;
import com.auth.oauth.config.domain.MongoAuthorizationCode;
import com.auth.oauth.config.domain.MongoClientDetails;
import com.auth.oauth.config.domain.User;
import com.auth.oauth.config.domain.UserCredential;
import com.google.common.collect.Sets;

@SpringBootApplication
public class OuathApplication {
    
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

    public static void main(String[] args) {
   //     SpringApplication.run(OuathApplication.class, args);
        
        final ConfigurableApplicationContext context = SpringApplication.run(OuathApplication.class, args);


            MongoTemplate mongoTemplate = (MongoTemplate) context.getBean(MongoTemplate.class);

//            mongoTemplate.dropCollection(UserCredential.class);
//            mongoTemplate.dropCollection(MongoClientDetails.class);
//            mongoTemplate.dropCollection(MongoAccessToken.class);
//            mongoTemplate.dropCollection(MongoAuthorizationCode.class);
//            mongoTemplate.dropCollection(User.class);
            
            
            
            User u=new User();
            u.setName("User One");
            u.setUserName("user1");
            u.setId(UUID.randomUUID().toString());
            u.setTenantId("6c3a472e-974d-4681-9509-09c6ba413c11");
            mongoTemplate.save(u);
            
         // init the users
            UserCredential mongoUser = new UserCredential();
            mongoUser.setId(u.getId());
            mongoUser.setUsername("user1");
            mongoUser.setPassword(getPasswordEncoder().encode("user1"));
            mongoUser.setRoles(Sets.newHashSet("USER","ADMIN","canCreateRole"));
            mongoTemplate.save(mongoUser);
            
            
            // init the client details
//            MongoClientDetails clientDetails = new MongoClientDetails();
//            clientDetails.setClientId("client1");
//            clientDetails.setClientSecret(getPasswordEncoder().encode("client1-secret"));
//            clientDetails.setSecretRequired(true);
//            clientDetails.setResourceIds(Sets.newHashSet("myResourceId"));
//            clientDetails.setScope(Sets.newHashSet("read-write"));
//            clientDetails.setAuthorizedGrantTypes(Sets.newHashSet("authorization_code", "refresh_token",
//                    "password", "client_credentials"));
//            clientDetails.setRegisteredRedirectUri(Sets.newHashSet("http://localhost:4200/resource-service"));
//            clientDetails.setAuthorities(getListOfGrantedAuthorities());
//            clientDetails.setAccessTokenValiditySeconds(60);
//            clientDetails.setRefreshTokenValiditySeconds(14400);
//            clientDetails.setAutoApprove(false);
//            mongoTemplate.save(clientDetails);

    }
    
   static List<GrantedAuthority> getListOfGrantedAuthorities(){
    	List<GrantedAuthority> list=new ArrayList<>();
    	list.add(new SimpleGrantedAuthority("USER"));
    	list.add(new SimpleGrantedAuthority("ADMIN"));
    	list.add(new SimpleGrantedAuthority("canCreateRole"));
    	list.add(new SimpleGrantedAuthority("canUpdateRole"));
    	list.add(new SimpleGrantedAuthority("canDeleteRole"));
    	
    	
    	return list;
    }

}
