package com.share.goal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Goals;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.core.LeaveApply;
import com.share.domain.core.LeaveTypes;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.goal.repo.GoalsRepository;
import com.share.leave.repo.LeaveApplyRepository;
import com.share.leave.repo.LeaveTypesRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class GoalsServiceImpl implements GoalsService {

	@Autowired
    private GoalsRepository repo;

	@Override
	public Goals getById(String id) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("GOAL");
		Goals result=null;
		try{
			result=repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("goal-001", "Error while reading Goal details for id " + id , referenceId, ex);
		}
		return result;
	}

	@Override
	public Goals getByUserIdAndFinancialYear(String userId, String financialYear) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("GOAL");
		Goals result=null;
		try{
			result=repo.findByUserIdAndFinancialYear(userId, financialYear);
		} catch (Exception ex) {
			throw new UnableToReadException("goal-002", "Error while reading Goal details for user id  " +userId + "  financial Year " +financialYear, referenceId, ex);
		}
		return result;
	}

	@Override
	public Goals getByManagerIdAndFinancialUYear(String managerId, String financialYear) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("GOAL");
		Goals result=null;
		try{
			result=repo.findByManagerIdAndFinancialYear(managerId, financialYear);
		} catch (Exception ex) {
			throw new UnableToReadException("goal-003", "Error while reading Goal details for managerId id  " +managerId + "  financial Year " +financialYear, referenceId, ex);
		}
		return result;
	}

	@Override
	public Goals saveOrUpdate(Goals goals) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("GOAL");
		try{
			repo.save(goals);
		} catch (Exception ex) {
			throw new UnableToPersistException("goal-004", "Error while savinng/updating Goal details for   ", referenceId, ex,new Gson().toJson(goals));
		}
		return goals;
	}

	@Override
	public void delete(String id) throws AppException {
		String referenceId=Utils.getReferenceId("GOAL");
		try{
			repo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("goal-005", "Error while deleting goal details for id " + id, referenceId, ex);
		}
		
	}

	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub
		
	}


 
}
