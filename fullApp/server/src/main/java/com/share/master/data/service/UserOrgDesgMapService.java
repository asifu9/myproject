package com.share.master.data.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.share.domain.core.UserOrgDesgMap;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface UserOrgDesgMapService {

    List<UserOrgDesgMap> listAll(String tenantId);

    UserOrgDesgMap getById(String id);
    
    List<UserOrgDesgMap> getByTenantAndUserId(String tenantId,String userId);
    
    UserOrgDesgMap saveOrUpdate(UserOrgDesgMap product);

    void delete(String id);
    

	void deleteById(String id);
	
	void delete(UserOrgDesgMap user); 
	
	
	void update(Query query, Update update);

}
