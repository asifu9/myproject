package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.Department;


public interface DepartmentRepository extends CrudRepository<Department, String>{

	List<Department> findByTenantId(String TenantId);
	List<Department> findByTenantIdAndIsActive(String TenantId,boolean isActive);
	List<Department> findByParentDepartmentIdAndIsActive(String parentDepartmentId,boolean isActive);
	List<Department> findBychildDepartmentIdAndIsActive(String childDepartmentId,boolean isActive);
	Department findById(String id);
	
}
