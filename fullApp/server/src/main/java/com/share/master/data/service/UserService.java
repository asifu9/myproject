package com.share.master.data.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.base.domain.User;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface UserService {


    User getById(String id) throws AppException;
    
    List<User> listByWallId(String id) throws AppException; 

    User saveOrUpdate(User product) throws AppException;

    void delete(String id) throws AppException;
	

	User getByIdWithPhoto(String id) throws AppException;
	
	void update(Query query, Update update) throws AppException;
	
	User getByIdOnFewFields(String id) throws AppException;
	
	List<User> searchByName(String name) throws AppException;
 

}
