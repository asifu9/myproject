package com.share.custom.form.service;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.ReportConfig;
import com.share.exception.AppException;
/**
 * Created by jt on 1/10/17.
 */
public interface ReportConfigService {


    ReportConfig getById(String id)throws AppException;
    

    ReportConfig saveOrUpdate(ReportConfig reportConfig)throws AppException;

    ReportConfig getByReportAndTenantId(String reportId,String tenantId)throws AppException;

	
	void update(Query query, Update update)throws AppException;

}
