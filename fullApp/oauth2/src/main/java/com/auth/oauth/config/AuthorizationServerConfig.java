package com.auth.oauth.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.auth.oauth.config.library.MongoAuthorizationCodeServices;
import com.auth.oauth.config.library.MongoClientDetailsService;

/**
 * Configures the authorization server.
 * The @EnableAuthorizationServer annotation is used to configure the OAuth 2.0 Authorization Server mechanism,
 * together with any @Beans that implement AuthorizationServerConfigurer (there is a handy adapter implementation with empty methods).
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired private TokenStore tokenStore;
    @Autowired(required = false) private JwtAccessTokenConverter accessTokenConverter;

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

    @Bean
    public MongoClientDetailsService clientDetailsService() {
        return new MongoClientDetailsService();
    }
    
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new MongoAuthorizationCodeServices();
    }
    /**
     * Setting up the endpointsconfigurer authentication manager.
     * The AuthorizationServerEndpointsConfigurer defines the authorization and token endpoints and the token services.
     * @param endpoints
     * @throws Exception
     */

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        oauthServer.allowFormAuthenticationForClients().passwordEncoder(getPasswordEncoder());
        oauthServer.passwordEncoder(getPasswordEncoder());
    }

    /**
     * Setting up the clients with a clientId, a clientSecret, a scope, the grant types and the authorities.
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
//                .inMemory()
//                .withClient("my-trusted-client")
//                .authorizedGrantTypes("client_credentials", "password","refresh_token")
//                .authorities("ROLE_CLIENT","ROLE_TRUSTED_CLIENT")
//                .scopes("read","write","trust")
//                .resourceIds("oauth2-resource")
//                .accessTokenValiditySeconds(5000)
//                .secret(passwordEncoder.encode("secret"));
    }

    /**
     * We here defines the security constraints on the token endpoint.
     * We set it up to isAuthenticated, which returns true if the user is not anonymous
     * @param security the AuthorizationServerSecurityConfigurer.
     * @throws Exception
     */

    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authorizationCodeServices(authorizationCodeServices())
                .tokenServices(tokenServices())
               
                .authenticationManager(authenticationManager);
    }

    @Primary
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);

        List<TokenEnhancer> enhancers = new ArrayList<>();
        if (accessTokenConverter != null) {
            enhancers.add(accessTokenConverter);
        }

        //Some custom enhancer
        enhancers.add(new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                final Authentication userAuthentication = authentication.getUserAuthentication();

                final DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
                Set<String> existingScopes = new HashSet<>();
                existingScopes.addAll(defaultOAuth2AccessToken.getScope());
                if (userAuthentication != null) {
                    //User has logged into system
                    existingScopes.add("read-write");
                } else {
                    //service is trying to access system
                    existingScopes.add("another-scope");
                }

                defaultOAuth2AccessToken.setScope(existingScopes);
                return defaultOAuth2AccessToken;
            }
        });

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(enhancers);
        tokenServices.setTokenEnhancer(enhancerChain);
        return tokenServices;
    }

}
