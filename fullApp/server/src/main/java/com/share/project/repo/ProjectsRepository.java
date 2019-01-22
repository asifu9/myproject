package com.share.project.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.enums.ProjectStatus;
import com.share.domain.core.Projects;

public interface ProjectsRepository extends CrudRepository<Projects, String>{

	
	List<Projects> findByTenantId(String tenantId);
	Projects findById(String id);
	List<Projects> findByTenantIdAndOwnerId(String tenantId,String ownerId);
	List<Projects> findByTenantIdAndStatus(String tenantId,ProjectStatus status);
	
}
