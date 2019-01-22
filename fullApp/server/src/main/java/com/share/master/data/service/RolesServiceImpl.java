package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.domain.Roles;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.RolesRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class RolesServiceImpl implements RoleService {

	@Autowired
	private RolesRepository repo;

	@Override
	public Roles getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("ROLES");
		try{
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("roles-001", "Error while fetching Roles details by id " + id , referenceId, ex);
		}
	}

	@Override
	public Roles saveOrUpdate(Roles activities) throws AppException{
		String referenceId=Utils.getReferenceId("ROLES");
		try{
			return repo.save(activities);
		} catch (Exception ex) {
			throw new UnableToPersistException("roles-002", "Error while saving/updating Roles details" , 
					referenceId, ex,new Gson().toJson(activities));
		}
	}


	@Override
	public List<Roles> getTenantIdAndActive(String tenantId, int isActive) throws AppException{
		String referenceId=Utils.getReferenceId("ROLES");
		try{
			return repo.findByTenantIdAndIsActive(tenantId, isActive);
		} catch (Exception ex) {
			throw new UnableToReadException("roles-003", "Error while fetching Roles details by is Active " +
					isActive + "  company id "+ tenantId , referenceId, ex);
		}
	}

	@Override
	public void delete(String id) throws AppException{
		String referenceId=Utils.getReferenceId("ROLES");
		try{
			 repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("roles-004", "Error while deleting Roles details by id " +
					id, referenceId, ex);
		}
		
	}




}
