package com.share.custom.form.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.ReportConfig;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.custom.form.repo.ReportConfigRepository;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class ReportConfigServiceImpl implements ReportConfigService {

	@Autowired
    private ReportConfigRepository repo;

	@Override
	public ReportConfig getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("REPORT-CONFIG");
		try{
		return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("report-config-001", "Error while fetching Report Config details by id " + id , referenceId, ex);
		}
	}

	@Override
	public ReportConfig saveOrUpdate(ReportConfig reportConfig) throws AppException{
		String referenceId=Utils.getReferenceId("REPORT-CONFIG");
		try{
		return repo.save(reportConfig);
		} catch (Exception ex) {
			throw new UnableToPersistException("report-config-002", "Error while saving/updating Report Config details", referenceId, ex,new Gson().toJson(reportConfig));
		}
	}

	@Override
	public ReportConfig getByReportAndTenantId(String reportId, String tenantId) throws AppException{
		String referenceId=Utils.getReferenceId("REPORT-CONFIG");
		try{
		return repo.findByReportIdAndTenantId(reportId, tenantId);
		} catch (Exception ex) {
			throw new UnableToReadException("report-config-003", "Error while fetching Report Config details by report id " + reportId + "  company id " + tenantId , referenceId, ex);
		}
	}

	@Override
	public void update(Query query, Update update) throws AppException{
		// TODO Auto-generated method stub
		
	}




 
}
