package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.Team;

public interface TeamRepository extends CrudRepository<Team, String>{

	
	List<Team> findByTenantId(String tenantId);
	Team findById(String id);
	
}
