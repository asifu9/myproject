package com.share.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.Updates;

public interface UpdateRepository extends CrudRepository<Updates, String>{
	


	List<Updates> findByUpdatedBy(String userId);
	List<Updates> findByUpdatedTo(String userId);
	Updates findById(String id);
	
	void deleteById(String id);
	void delete(Updates user);
	void deleteByTenantIdAndId(String pkey,String id);
}
