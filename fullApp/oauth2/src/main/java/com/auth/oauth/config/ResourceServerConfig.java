package com.auth.oauth.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
/**
 *The @EnableResourceServer annotation adds a filter of type OAuth2AuthenticationProcessingFilter automatically
 *to the Spring Security filter chain.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	 @Autowired private ResourceServerTokenServices tokenServices;
	 
	 @Override
	    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	        resources.resourceId("foo").tokenServices(tokenServices);
	    }
	 
    @Override
    public void configure(HttpSecurity http) throws Exception {
  //      http
//            .headers()
//                .frameOptions()
//                .disable()
//                .and()
//            .authorizeRequests()
//                .antMatchers("/auth-service","/auth-service/home","/auth-service/oauth/token","/register","/login","**/login","/**/login").permitAll()
//                .antMatchers("/auth-service/private/**","/api/**").authenticated();
//    	
    	   http
           .authorizeRequests()
           .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read-foo')")
           .and()
           .headers().addHeaderWriter(new HeaderWriter() {
       @Override
       public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
           response.addHeader("Access-Control-Allow-Origin", "*");
           if (request.getMethod().equals("OPTIONS")) {
               response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
               response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
           }
       }
   });
    }


}
