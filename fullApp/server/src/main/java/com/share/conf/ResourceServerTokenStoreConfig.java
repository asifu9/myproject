package com.share.conf;


import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class ResourceServerTokenStoreConfig {

	
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
    
    @Primary
    @Bean
    @Profile("!jwttoken")
    public ResourceServerTokenServices remoteTokenServices() {
    	System.out.println("ok here i a checking, cool now here");
        RemoteTokenServices tokenService = new RemoteTokenServices();
        
        tokenService.setCheckTokenEndpointUrl(
                "http://localhost:7001/auth-service/oauth/check_token");
        tokenService.setClientId("client1");
        tokenService.setClientSecret("client1-secret");
        return tokenService;
    }

    @Bean
    @Primary
    @Profile("jwttoken")
    public ResourceServerTokenServices jwtTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    @Profile("jwttoken")
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Profile("jwttoken")
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }
}