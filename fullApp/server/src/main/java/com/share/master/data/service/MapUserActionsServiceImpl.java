package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.MapUserActions;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.MapUserActionsRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class MapUserActionsServiceImpl implements MapUserActionsService {

	@Autowired
    private MapUserActionsRepository repo;

	@Override
	public MapUserActions getById(String id)throws AppException {

		String referenceId=Utils.getReferenceId("MAP-USER-ACTION");
		try{
		return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("map-user-action-001", "Error while reading Map User Action details by id " + id , referenceId, ex);
		}
	}

	@Override
	public MapUserActions saveOrUpdate(MapUserActions mapUserActions) throws AppException{
		String referenceId=Utils.getReferenceId("MAP-USER-ACTION");
		try{
		return repo.save(mapUserActions);
		} catch (Exception ex) {
			throw new UnableToPersistException("map-user-action-002", "Error while saving/updating Map User Action details" , referenceId, ex,new Gson().toJson(mapUserActions));
		}
	}

	@Override
	public List<MapUserActions> getByTenantAndType(String tenantId, String type) throws AppException{
		String referenceId=Utils.getReferenceId("MAP-USER-ACTION");
		try{
		return repo.findByTenantIdAndType(tenantId, type);
		} catch (Exception ex) {
			throw new UnableToReadException("map-user-action-003", "Error while reading Map User Action details by company Id " + 
					tenantId + " type " + type , referenceId, ex);
		}
	}

	@Override
	public List<MapUserActions> getByTenantAndUserId(String tenantId, String userId)throws AppException {
		String referenceId=Utils.getReferenceId("MAP-USER-ACTION");
		try{
		return repo.findByTenantIdAndUserId(tenantId, userId);
		} catch (Exception ex) {
			throw new UnableToReadException("map-user-action-004", "Error while reading Map User Action details by company id " +
					tenantId + " user id "  + userId, referenceId, ex);
		}
	}

	@Override
	public List<MapUserActions> getByTenantAndTypeAndUser(String tenantId, String type, String user)throws AppException {
		String referenceId=Utils.getReferenceId("MAP-USER-ACTION");
		try{
		return repo.findByTenantIdAndUserIdAndType(tenantId, user, type);
		} catch (Exception ex) {
			throw new UnableToReadException("map-user-action-005", "Error while reading Map User Action details by company id " +
					tenantId + " type " + type + " user id "  + user, referenceId, ex);
		}
	}

	@Override
	public List<MapUserActions> getByTenantAndTypedValueAndType(String tenantId, String typedValue, String type)throws AppException {
		String referenceId=Utils.getReferenceId("MAP-USER-ACTION");
		try{
		return repo.findByTenantIdAndTypeValueAndType(tenantId, typedValue, type);
		} catch (Exception ex) {
			throw new UnableToReadException("map-user-action-006", "Error while reading Map User Action details by company id " +
					tenantId + " typed Value " + typedValue + " type  "  + type, referenceId, ex);
		}
	}

	@Override
	public void deleteMap(MapUserActions action)throws AppException {
		String referenceId=Utils.getReferenceId("MAP-USER-ACTION");
		try{
		repo.delete(action);
		} catch (Exception ex) {
			throw new UnableToDeleteException("map-user-action-007", "Error while deleting Map User Action details by company id " + action.getId(), referenceId, ex);
		}
	}



 
}
