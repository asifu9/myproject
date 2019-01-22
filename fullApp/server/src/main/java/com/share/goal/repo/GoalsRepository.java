package com.share.goal.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.Events;
import com.base.domain.Goals;

public interface GoalsRepository extends CrudRepository<Goals, String>{

	
	Goals findByUserIdAndFinancialYear(String userId,String financialYear);
	Goals findByManagerIdAndFinancialYear(String managerId,String financialYear);
	Goals findById(String id);
	
	
}
