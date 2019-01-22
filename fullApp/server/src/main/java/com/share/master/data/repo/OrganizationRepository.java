package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, String>{

	
	List<Organization> findByTenantId(String tenantId);
	List<Organization> findByTenantIdAndParentOrgId(String tenantId,String parentOrgId);
	Organization findById(String id);
	
}
