package com.share.custom.form.repo;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.ReportConfig;

public interface ReportConfigRepository extends CrudRepository<ReportConfig, String>{

	
	
	ReportConfig findById(String id);
	
	ReportConfig findByReportIdAndTenantId(String reportId,String tenantId);
	
}
