package com.share.master.data.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.share.domain.core.UserLeaves;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface UserLeavesService {


    UserLeaves getById(String id) throws AppException;
    List<UserLeaves> getByUserId(String id) throws AppException;
    UserLeaves getByUserIdAndCurrent(String userId,boolean isCurrent) throws AppException;

    UserLeaves createLeavesForUser(String userId,String tenantId,int year) throws AppException;
    UserLeaves saveOrUpdate(UserLeaves product) throws AppException;

    void delete(String id) throws AppException;
    


	
	
	void update(Query query, Update update);
	UserLeaves getByUserIdAndYear(String userId, int year) throws AppException;
	UserLeaves getByUserIdAndFinancialYear(String userId, String year) throws AppException;

}
