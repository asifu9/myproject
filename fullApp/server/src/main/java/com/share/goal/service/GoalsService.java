package com.share.goal.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Goals;
import com.share.domain.core.LeaveApply;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface GoalsService {

   Goals getById(String id) throws AppException;
   Goals getByUserIdAndFinancialYear(String userId,String financialYear) throws AppException;
   Goals getByManagerIdAndFinancialUYear(String managerId,String financialYear) throws AppException;
   

   Goals saveOrUpdate(Goals goals) throws AppException;

    void delete(String  id) throws AppException;
    



	
	
	void update(Query query, Update update);

}
