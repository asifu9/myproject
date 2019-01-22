package com.share.company.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.TimeDifference;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.company.repo.CompanySettingRepository;
import com.share.domain.CompanySetting;
import com.share.domain.Group;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.repositories.GroupRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class CompanySettingServiceImpl implements CompanySettingService {

	private CompanySettingRepository repo;

	@Autowired
	MongoTemplate template;

	@Autowired
	public CompanySettingServiceImpl(CompanySettingRepository f) throws AppException{
		this.repo = f;
	}

	@Override
	public CompanySetting getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("COM-SETTING");
		try{
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("company-setting-001", "Error while reading company setting details for id " + id , referenceId, ex);
		}
	}

	@Override
	public CompanySetting saveOrUpdate(CompanySetting feed) throws AppException{
		String referenceId=Utils.getReferenceId("COM-SETTING");
		try{
			return repo.save(feed);
		} catch (Exception ex) {
			throw new UnableToPersistException("company-setting-002", "Error while saving/updating company setting details ", referenceId, ex,new Gson().toJson(feed));
		}
	}

	@Override
	public void update(Query query, Update update) throws AppException{
		// TODO Auto-generated method stub

	}


}
