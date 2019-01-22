package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.UserLeaves;

public interface UserLeavesRepository extends CrudRepository<UserLeaves, String>{

	
	UserLeaves findByUserIdAndIsCurrent(String userId,boolean isCurrent);
	UserLeaves findByUserIdAndYear(String userId,int year);
	UserLeaves findById(String id);
	List<UserLeaves> findByUserId(String id);
	UserLeaves findByUserIdAndFinancialYear(String userId, String year);
	
}
