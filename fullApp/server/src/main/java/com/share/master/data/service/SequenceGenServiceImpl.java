package com.share.master.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.share.domain.SequenceGen;
import com.share.repositories.SequenceGenRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class SequenceGenServiceImpl implements SequenceGenService {

	
	@Autowired
	private SequenceGenRepository repo;

	@Override
	public List<SequenceGen> getByTenantId(String tenantId) {
		// TODO Auto-generated method stub
		return repo.findByTenantId(tenantId);
	}

	@Override
	public SequenceGen findByUniqueId(String tenantId) {
		// TODO Auto-generated method stub
		return repo.findByUniqueId(tenantId);
	}

	@Override
	public SequenceGen getById(String id) {
		// TODO Auto-generated method stub
		return repo.findOne(id);
	}

	@Override
	public SequenceGen saveOrUpdate(SequenceGen product) {
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
	public void delete(SequenceGen user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub
		
	}
	
   



 
}
