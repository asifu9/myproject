package com.share.leave.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.LeaveApply;

public interface LeaveApplyRepository extends CrudRepository<LeaveApply, String>{

	LeaveApply findById(String userId);
	List<LeaveApply> findByUserId(String userId);
	List<LeaveApply> findByUserIdAndLeaveName(String userId,String leaveName);
	List<LeaveApply> findByAssignedTo(String assignedTo);
	List<LeaveApply> findByUserIdAndStatus(String userId,String status);
	List<LeaveApply> findByUserIdAndFinancialYear(String userId,String yearRange);
	List<LeaveApply> findByAssignedToAndStatus(String assignedTo,String status);
	List<LeaveApply> findByAssignedToAndFinancialYear(String assignedTo,String yearRange);
	List<LeaveApply> findByCompanyIdAndLeaveName(String companyId,String leaveName);
}
