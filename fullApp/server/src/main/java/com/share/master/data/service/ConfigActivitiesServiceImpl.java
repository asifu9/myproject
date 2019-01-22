package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.domain.ConfigActivities;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.ConfigActivitiesRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class ConfigActivitiesServiceImpl implements ConfigActivitiesService {

	@Autowired
	private ConfigActivitiesRepository repo;

	@Override
	public ConfigActivities getById(String id)throws AppException {
		String referenceId=Utils.getReferenceId("CONFIG-ACTIVITY");
		try{
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("config-activity-001", "Error while reading Config Activity details for id " + id , referenceId, ex);
		}
	}

	@Override
	public ConfigActivities saveOrUpdate(ConfigActivities product)throws AppException {
		String referenceId=Utils.getReferenceId("CONFIG-ACTIVITY");
		try{
			return repo.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("config-activity-002", "Error while saving/updating Config Activity details" , referenceId, ex,new Gson().toJson(product));
		}
	}

	@Override
	public List<ConfigActivities> getTenantIdAndActive(String tenantId, boolean isActive)throws AppException {
		String referenceId=Utils.getReferenceId("CONFIG-ACTIVITY");
		try{
			return repo.findByTenantIdAndIsActive(tenantId, isActive);
		} catch (Exception ex) {
			throw new UnableToReadException("config-activity-003", "Error while reading Config Activity details for company id "
					+tenantId + " and is Active " + isActive , referenceId, ex);
		}
	}

	@Override
	public ConfigActivities getByTenantAndName(String tenantId,String name)throws AppException {
		String referenceId=Utils.getReferenceId("CONFIG-ACTIVITY");
		try{
			return repo.findByTenantIdAndName(tenantId, name);
		} catch (Exception ex) {
			throw new UnableToReadException("config-activity-004", "Error while reading Config Activity details for company id " + tenantId + " name " + name , referenceId, ex);
		}
	}




}
