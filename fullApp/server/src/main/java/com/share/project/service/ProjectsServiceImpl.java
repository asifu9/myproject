package com.share.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Designation;
import com.base.enums.ProjectStatus;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.core.Projects;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class ProjectsServiceImpl implements ProjectsService {

	@Autowired
	private ProjectsService repo;

	@Override
	public List<Projects> getByTenantId(String tenantId)  throws AppException{
		String referenceId=Utils.getReferenceId("PROJECT");
		try{
			return repo.getByTenantId(tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("project-001", "Error while fetching project details for company id " + tenantId, referenceId, ex);
		}

	}

	@Override
	public Projects getById(String id) throws AppException {
		String referenceId=Utils.getReferenceId("PROJECT");
		try{
			return repo.getById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("project-002", "Error while fetching project detail for id " + id, referenceId, ex);
		}
	}

	@Override
	public List<Projects> getByTenantIdAndOwnerId(String tenantId, String ownerId)  throws AppException{
		String referenceId=Utils.getReferenceId("PROJECT");
		try{
			return repo.getByTenantIdAndOwnerId(tenantId, ownerId);
		} catch (Exception ex) {
			throw new UnableToReadException("project-003", "Error while fetching project details for company id " + tenantId + " owner id " + ownerId, referenceId, ex);
		}
	}

	@Override
	public List<Projects> getByTenantIdAndStatus(String tenantId, ProjectStatus status)  throws AppException{
		String referenceId=Utils.getReferenceId("PROJECT");
		try{
			return repo.getByTenantIdAndStatus(tenantId, status);
		} catch (Exception ex) {
			throw new UnableToReadException("project-004", "Error while fetching project details for company id " + tenantId + " status " + status, referenceId, ex);
		}
	}

	@Override
	public Projects saveOrUpdate(Projects product) throws AppException {
		String referenceId=Utils.getReferenceId("PROJECT");
		try{
			return repo.saveOrUpdate(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("project-005", "Error while saving/updating project details", referenceId, ex,new Gson().toJson(product));
		}
	}

	@Override
	public void delete(String id)  throws AppException{
		String referenceId=Utils.getReferenceId("PROJECT");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToReadException("project-006", "Error while deleting project details for id " + id, referenceId, ex);
		}

	}



	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub

	}








}
