package com.share.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.custom.form.repo.FormAttachmentRepository;
import com.share.domain.core.FormAttachment;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class FormAttachmentServiceImpl implements FormAttachmentService {

	@Autowired
	private FormAttachmentRepository repo;

	@Override
	public FormAttachment getByTenantIdAndFormId(String tenantId, String formId) throws AppException{
		String referenceId=Utils.getReferenceId("ATTACHMENT");
		try{
			return repo.findByTenantIdAndFormId(tenantId, formId);
		} catch (Exception ex) {
			throw new UnableToReadException("attachment-001", "Error while fetching attachments for form id " + formId + "  tenand id " + tenantId, referenceId, ex);
		}
	}

	@Override
	public FormAttachment getById(String id)  throws AppException{
		String referenceId=Utils.getReferenceId("ATTACHMENT");
		try{
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("attachment-002", "Error while fetching attachment for  id " + id, referenceId, ex);
		}
	}

	@Override
	public FormAttachment saveOrUpdate(FormAttachment formAttachment) throws AppException {
		String referenceId=Utils.getReferenceId("ATTACHMENT");
		try{
			return repo.save(formAttachment);
		} catch (Exception ex) {
			throw new UnableToPersistException("attachment-003", "Error while saving/updating attachment details " , referenceId, ex, new Gson().toJson(formAttachment));
		}
	}

	@Override
	public void delete(String id) throws AppException {
		String referenceId=Utils.getReferenceId("ATTACHMENT");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("attachment-004", "Error while deleting attachment for id " + id, referenceId, ex);
		}
	}


	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub

	}


}
