package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.UserDepartmentManage;

public interface UserDepartmentManageRepository extends CrudRepository<UserDepartmentManage, String>{

	
	List<UserDepartmentManage> findByTenantId(String tenantId);
	UserDepartmentManage findById(String id);
	List<UserDepartmentManage> findByUserIdAndTenantId(String userId,String tenantId);
	List<UserDepartmentManage> findByDepartmentIdAndTenantId(String departmentId,String tenantId);
	UserDepartmentManage findByDepartmentIdAndUserIdAndTenantId(String departmentId,String userId,String tenantId);
	
}
