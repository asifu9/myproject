package com.share.master.data.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.share.domain.core.Team;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface TeamService {

    List<Team> getByTenantId(String tenantId) throws AppException;

    Team getById(String id) throws AppException;
    

    Team saveOrUpdate(Team product) throws AppException;

    void delete(String id) throws AppException;
    
	
	
	
	void update(Query query, Update update) throws AppException;

}
