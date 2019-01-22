package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.core.UserDepartmentManage;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.UserDepartmentManageRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class UserDepartmentServiceImpl implements UserDepartmentService {


	@Autowired
    private UserDepartmentManageRepository repo;

	@Override
	public List<UserDepartmentManage> listAll(String tenantId) throws AppException{
		String referenceId=Utils.getReferenceId("USER-DEP-MANGEMENT");
		try{
		return (List<UserDepartmentManage>) repo.findByTenantId(tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("user-depart-manage-001", "Error while fetching user department details for company id " + tenantId , referenceId, ex);
		}
	}

	@Override
	public UserDepartmentManage getById(String id) throws AppException {
		String referenceId=Utils.getReferenceId("USER-DEP-MANGEMENT");
		try{
		return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("user-depart-manage-002", "Error while fetching user department details for id " + id , referenceId, ex);
		}
	}

	@Override
	public List<UserDepartmentManage> getByDepartmentIdAndTenantId(String departmentId, String tenantId)  throws AppException{
		String referenceId=Utils.getReferenceId("USER-DEP-MANGEMENT");
		try{
		return repo.findByDepartmentIdAndTenantId(departmentId, tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("user-depart-manage-003", "Error while fetching user department details for company id " + tenantId + 
					" department id " + departmentId, referenceId, ex);
		}
	}

	@Override
	public List<UserDepartmentManage> getByUserIdAndTenantId(String userId, String tenantId) throws AppException{
		String referenceId=Utils.getReferenceId("USER-DEP-MANGEMENT");
		try{
		return repo.findByUserIdAndTenantId(userId, tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("user-depart-manage-004", "Error while fetching user department details for company id " + tenantId +
					" user id " + userId, referenceId, ex);
		}
	}

	@Override
	public UserDepartmentManage getByDepartmentIdAndUserIdAndTenantId(String departmentId, String userId,
			String tenantId) throws AppException {
		String referenceId=Utils.getReferenceId("USER-DEP-MANGEMENT");
		try{
		return repo.findByDepartmentIdAndUserIdAndTenantId(departmentId, userId, tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("user-depart-manage-005", "Error while fetching user department details for company id " + tenantId + 
					" user id " + userId + " deparment id " + departmentId, referenceId, ex);
		}
	}

	@Override
	public UserDepartmentManage saveOrUpdate(UserDepartmentManage product)  throws AppException{
		String referenceId=Utils.getReferenceId("USER-DEP-MANGEMENT");
		try{
		return repo.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("user-depart-manage-006", "Error while saving/updating user department details " , referenceId, ex,new Gson().toJson(product));
		}
	}

	@Override
	public void delete(String id)   throws AppException{
		String referenceId=Utils.getReferenceId("USER-DEP-MANGEMENT");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("user-depart-manage-007", "Error while deleting user department details for id " + id , referenceId, ex);
		}
		
	}


	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub
		
	}


	

   



 
}
