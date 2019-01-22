package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.core.Team;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.TeamRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class TeamServiceImpl implements TeamService {


	@Autowired
	private TeamRepository repo;

	@Override
	public List<Team> getByTenantId(String tenantId)  throws AppException{
		String referenceId=Utils.getReferenceId("TEAM");
		try{
			return repo.findByTenantId(tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("team-001", "Error while fetching team details for company id " + tenantId  
					, referenceId, ex);
		}
	}

	@Override
	public Team getById(String id)  throws AppException{
		String referenceId=Utils.getReferenceId("TEAM");
		try{
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("team-002", "Error while fetching team detail for id "+ id , referenceId, ex);
		}
	}

	@Override
	public Team saveOrUpdate(Team product)  throws AppException{
		String referenceId=Utils.getReferenceId("TEAM");
		try{
			return repo.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("team-003", "Error while saving/updting team details " , referenceId, ex,new Gson().toJson(product));
		}
	}

	@Override
	public void delete(String id)  throws AppException{
		String referenceId=Utils.getReferenceId("TEAM");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("team-004", "Error while deleting team details for id " + id ,referenceId, ex);
		}

	}




	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub

	}








}
