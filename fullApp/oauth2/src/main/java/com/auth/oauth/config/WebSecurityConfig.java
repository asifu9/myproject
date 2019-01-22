package com.auth.oauth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import com.auth.oauth.config.library.MongoUserDetailsService;
//
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService customUserDetailsService;
    
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
        
    
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new MongoUserDetailsService();
    }
    
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(getPasswordEncoder());    
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                .anyRequest().authenticated()
                .and()
            .httpBasic()
                .and()
            .csrf().disable();
        
      //  http.csrf().ignoringAntMatchers("/oauth/token/");
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
    
    /**
         * See: https://github.com/spring-projects/spring-boot/issues/11136
         *
         * @return
         * @throws Exception
         */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }    

}
