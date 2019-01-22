package com.share.company.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Features;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.company.repo.FeaturesRepository;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class FeaturesServiceImpl implements FeaturesService {

	@Autowired
	private FeaturesRepository repo;

	@Override
	public Features getByName(String name)  throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("FEATURE");
		try{
			return repo.findByName(name);
		} catch (Exception ex) {
			throw new UnableToReadException("feature-001", "Error while fetching feature details by name " + name, referenceId, ex);
		}
	}

	@Override
	public Features getById(String id)  throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("FEATURE");
		try{
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("feature-002", "Error while fetching feature details by id " + id, referenceId, ex);
		}
	}

	@Override
	public Features saveOrUpdate(Features features)  throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("FEATURE");
		try{
			return repo.save(features);
		} catch (Exception ex) {
			throw new UnableToPersistException("feature-003", "Error while saving/updating feature details " , referenceId, ex, new Gson().toJson(features));
		}
	}

	@Override
	public void delete(Features id)  throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("FEATURE");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("feature-004", "Error while deleting feature details for id " + id, referenceId, ex);
		}
	}

	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Features> getAll() throws AppException{
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("FEATURE");
		List<Features> list=new ArrayList<>();
		try{
			 repo.findAll().forEach(i-> list.add(i));
		} catch (Exception ex) {
			throw new UnableToReadException("feature-005", "Error while fetching all feature ", referenceId, ex);
		}
		return list;
	}



}
