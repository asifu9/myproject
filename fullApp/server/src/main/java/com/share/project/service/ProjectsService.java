package com.share.project.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Designation;
import com.base.enums.ProjectStatus;
import com.share.domain.core.Projects;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public interface ProjectsService {

    List<Projects> getByTenantId(String tenantId) throws AppException;

    Projects getById(String id) throws AppException;
    
    List<Projects> getByTenantIdAndOwnerId(String tenantId,String ownerId) throws AppException;
    List<Projects> getByTenantIdAndStatus(String tenantId,ProjectStatus status) throws AppException;
    Projects saveOrUpdate(Projects product) throws AppException;

    void delete(String id) throws AppException;
   
	
	
	void update(Query query, Update update);

}
