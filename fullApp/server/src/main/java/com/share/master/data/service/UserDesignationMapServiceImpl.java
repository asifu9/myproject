package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Designation;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.core.UserDesignationMap;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class UserDesignationMapServiceImpl implements UserDesignationMapService {

	@Autowired
    private UserDesignationMapService repo;

	@Override
	public List<UserDesignationMap> getByTenantId(String tenantId) throws AppException{
		String referenceId=Utils.getReferenceId("USER-DESIGNATION-MAP");
		try{
		return repo.getByTenantId(tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("user-designation-map-001", "Error while fetching user designation map details for company id " + tenantId , referenceId, ex);
		}
	}

	@Override
	public UserDesignationMap getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("USER-DESIGNATION-MAP");
		try{
		return repo.getById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("user-designation-map-002", "Error while fetching user designation map details for id " + id , referenceId, ex);
		}
	}

	@Override
	public List<UserDesignationMap> getByTenantIdAndUserId(String tenantId, String userId) throws AppException{
		String referenceId=Utils.getReferenceId("USER-DESIGNATION-MAP");
		try{
		return repo.getByTenantIdAndUserId(tenantId, userId);
		} catch (Exception ex) {
			throw new UnableToReadException("user-designation-map-003", "Error while fetching user designation map details for company id " + tenantId + " user id "+userId, referenceId, ex);
		}
	}

	@Override
	public List<UserDesignationMap> getByTenantIdAndDesignationId(String tenantId, String designationId)throws AppException {
		String referenceId=Utils.getReferenceId("USER-DESIGNATION-MAP");
		try{
		return repo.getByTenantIdAndDesignationId(tenantId, designationId);
		} catch (Exception ex) {
			throw new UnableToReadException("user-designation-map-004", "Error while fetching user designation map details for company id " + tenantId+
					" desination id " + designationId, referenceId, ex);
		}
	}

	@Override
	public UserDesignationMap saveOrUpdate(UserDesignationMap product) throws AppException{
		String referenceId=Utils.getReferenceId("USER-DESIGNATION-MAP");
		try{
		return repo.saveOrUpdate(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("user-designation-map-004", "Error while saving/updating user designation map details ", referenceId, ex,new Gson().toJson(product));
		}
	}

	@Override
	public void delete(String id) throws AppException{
		String referenceId=Utils.getReferenceId("USER-DESIGNATION-MAP");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("user-designation-map-005", "Error while deleting user designation map details for  id " + id , referenceId, ex);
		}
		
	}

	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub
		
	}

   



 
}
