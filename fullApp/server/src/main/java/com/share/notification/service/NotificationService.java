package com.share.notification.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.base.domain.Company;
import com.share.domain.core.Notification;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface NotificationService {
	
	
	List<Notification> findByUserId(String userId)throws AppException;
	List<Notification> findByUserIdAndCompanyId(String userId,String companyId)throws AppException;
	List<Notification> findByUserIdAndCompanyIdAndStatus(String userId,String companyId,String status)throws AppException;
	Notification findById(String id)throws AppException;
	List<Notification> findByUserIdAndStatus(String userId,int status)throws AppException;
	
	Notification findByValueId(String valueId)throws AppException;

	Notification saveOrUpdate(Notification notification)throws AppException;

    void deleteById(String id)throws AppException;
    
	void update(Query query, Update update)throws AppException;

	Notification getByUniqueName(String string)throws AppException;

}
