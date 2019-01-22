package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Designation;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.core.UserProjectMap;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class UserProjectMapServiceImpl implements UserProjectMapService {

	@Autowired
    private UserProjectMapService repo;

	@Override
	public List<UserProjectMap> getByTenantId(String tenantId) throws AppException{
		String referenceId = Utils.getReferenceId("USER-PRO-MAP");
		try {
		return repo.getByTenantId(tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("user-pro-map-001", "Error while fetching user project map details for company id  "	 +  tenantId, referenceId, ex);
		}
	}

	@Override
	public UserProjectMap getById(String id)throws AppException {
		String referenceId = Utils.getReferenceId("USER-PRO-MAP");
		try {
		return repo.getById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("user-pro-map-002", "Error while fetching user project map details for id   "	 +  id, referenceId, ex);
		}
	}

	@Override
	public List<UserProjectMap> getByTenantIdAndUserId(String tenantId, String userId)throws AppException {
		String referenceId = Utils.getReferenceId("USER-PRO-MAP");
		try {
		return repo.getByTenantIdAndUserId(tenantId, userId);
		} catch (Exception ex) {
			throw new UnableToReadException("user-pro-map-003", "Error while fetching user project map details for company id  "	 +  tenantId + " user id " + userId, referenceId, ex);
		}
	}

	@Override
	public List<UserProjectMap> getByTenantIdAndProjectId(String tenantId, String projectId) throws AppException{
		String referenceId = Utils.getReferenceId("USER-PRO-MAP");
		try {
		return repo.getByTenantIdAndProjectId(tenantId, projectId);
		} catch (Exception ex) {
			throw new UnableToReadException("user-pro-map-004", "Error while fetching user project map details for company id  "	 +  tenantId + " project id " + projectId, referenceId, ex);
		}
	}

	@Override
	public UserProjectMap saveOrUpdate(UserProjectMap product) throws AppException{
		String referenceId = Utils.getReferenceId("USER-PRO-MAP");
		try {
		return repo.saveOrUpdate(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("user-pro-map-005", "Error while saving/updating user project map details ", referenceId, ex,new Gson().toJson(product));
		}
	}

	@Override
	public void delete(String id) throws AppException{
		
	}

	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub
		
	}

	

   



 
}
