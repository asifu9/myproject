package com.share.company.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.base.domain.Company;

public interface CompanyRepository extends MongoRepository<Company, String>{


	Company findById(String userId);
	List<Company> findByIsActive(int isActive);
	Company findByUname(String uname);
	
}
