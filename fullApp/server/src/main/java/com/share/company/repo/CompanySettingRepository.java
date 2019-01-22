package com.share.company.repo;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.CompanySetting;

public interface CompanySettingRepository extends CrudRepository<CompanySetting, String>{


	CompanySetting findById(String id);

	
}
