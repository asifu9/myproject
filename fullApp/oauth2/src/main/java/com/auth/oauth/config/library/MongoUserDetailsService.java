package com.auth.oauth.config.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.auth.oauth.config.domain.UserCredential;
import com.google.gson.Gson;


public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        UserCredential user =mongoTemplate.findOne(query, UserCredential.class);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
        System.out.println("ok got it here "+new Gson().toJson(user));
        String[] roles = new String[user.getRoles().size()];

        return new User(user.getUsername(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRoles().toArray(roles)));
    }
}