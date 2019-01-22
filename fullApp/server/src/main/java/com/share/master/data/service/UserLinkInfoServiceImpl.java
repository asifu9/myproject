package com.share.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.UserLinkInfo;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.repo.UserLinkInfoRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class UserLinkInfoServiceImpl implements UserLinkInfoService {

	@Autowired
	private UserLinkInfoRepository repo;

	@Override
	public UserLinkInfo getByTenantIdAndId(String tenantId, String id) throws AppException{
		String referenceId = Utils.getReferenceId("USER-LINK");
		try {
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("user-link-001", "Error while fetching user link details for id "	 + id + " tenant id  " + tenantId, referenceId, ex);
		}
	}

	@Override
	public UserLinkInfo getById(String id) throws AppException{
		String referenceId = Utils.getReferenceId("USER-LINK");
		try {
			return repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("user-link-002", "Error while fetching user link details for id "	 + id , referenceId, ex);
		}
	}

	@Override
	public UserLinkInfo saveOrUpdate(UserLinkInfo userLinkInfo) throws AppException{
		String referenceId = Utils.getReferenceId("USER-LINK");
		try {
			return repo.save(userLinkInfo);
		} catch (Exception ex) {
			throw new UnableToPersistException("user-link-003", "Error while saving/updating user link details ", referenceId, ex,new Gson().toJson(userLinkInfo));
		}
	}

	@Override
	public void delete(String id)throws AppException {
		String referenceId = Utils.getReferenceId("USER-LINK");
		try {
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("user-link-004", "Error while deleting link details for id "	 + id , referenceId, ex);
		}

	}


	@Override
	public void update(Query query, Update update) throws AppException{
		// TODO Auto-generated method stub

	}



}
