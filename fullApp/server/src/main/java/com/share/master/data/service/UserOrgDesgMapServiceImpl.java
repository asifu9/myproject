package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.share.domain.core.Organization;
import com.share.domain.core.UserOrgDesgMap;
import com.share.master.data.repo.OrganizationRepository;
import com.share.master.data.repo.UserOrgDesgMapRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class UserOrgDesgMapServiceImpl implements UserOrgDesgMapService {

	@Autowired
    private UserOrgDesgMapRepository repo;

	@Override
	public List<UserOrgDesgMap> listAll(String tenantId) {
		// TODO Auto-generated method stub
		return (List<UserOrgDesgMap>) repo.findAll();
	}

	@Override
	public UserOrgDesgMap getById(String id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public List<UserOrgDesgMap> getByTenantAndUserId(String tenantId, String userId) {
		// TODO Auto-generated method stub
		return repo.findByTenantIdAndUserId(tenantId, userId);
	}

	@Override
	public UserOrgDesgMap saveOrUpdate(UserOrgDesgMap product) {
		// TODO Auto-generated method stub
		return repo.save(product);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(UserOrgDesgMap user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub
		
	}


 
}
