package com.share.leave.service;

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
public class LeaveApplyServiceImpl implements LeaveApplyService {

	@Autowired
    private LeaveApplyRepository repo;

	@Override
	public List<LeaveApply> getByUserId(String userId) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAV-APP");
		List<LeaveApply> result=null;
		try{
			result=repo.findByUserId(userId);
		} catch (Exception ex) {
			throw new UnableToReadException("applyleave-001", "Error while reading Leave apply details for user id " + userId , referenceId, ex);
		}
		return result;
	}

	@Override
	public List<LeaveApply> getByUserIdAndYearRange(String userId, String yearRange) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAV-APP");
		List<LeaveApply> result=null;
		try{
			result=repo.findByUserIdAndFinancialYear(userId, yearRange);
			//throw new UnableToPersistException("applyleave-002", "Error while reading Leave apply details for user id " + userId + " year range " + yearRange , referenceId, null);
		} catch (Exception ex) {
			throw new UnableToReadException("applyleave-002", "Error while reading Leave apply details for user id " + userId + " year range " + yearRange , referenceId, ex);
		}
		return result;
	}

	@Override
	public List<LeaveApply> getByUserIdAndStatus(String userId, String status) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAV-APP");
		List<LeaveApply> result=null;
		try{
			result=repo.findByUserIdAndStatus(userId, status);
		} catch (Exception ex) {
			throw new UnableToReadException("applyleave-003", "Error while reading Leave apply details for user id " + userId + " status " + status , referenceId, ex);
		}
		return result;
	}

	@Override
	public List<LeaveApply> getByUserIdAndLeaveName(String userId, String leaveName) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAV-APP");
		List<LeaveApply> result=null;
		try{
			result=repo.findByUserIdAndLeaveName(userId, leaveName);
		} catch (Exception ex) {
			throw new UnableToReadException("applyleave-004", "Error while reading Leave apply details for user id " + userId + " leaveName " + leaveName , referenceId, ex);
		}
		return result;
	}

	@Override
	public List<LeaveApply> getByCompanyIdAndLeaveName(String companyId, String leaveName) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAV-APP");
		List<LeaveApply> result=null;
		try{
			result=repo.findByCompanyIdAndLeaveName(companyId, leaveName);
		} catch (Exception ex) {
			throw new UnableToReadException("applyleave-005", "Error while reading Leave apply details for company id " + companyId + " leave name " + leaveName , referenceId, ex);
		}
		return result;
	}

	@Override
	public List<LeaveApply> getByAssignedTo(String assignedTo) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAV-APP");
		List<LeaveApply> result=null;
		try{
			result=repo.findByAssignedTo(assignedTo);
			
		} catch (Exception ex) {
			throw new UnableToReadException("applyleave-006", "Error while reading Leave apply details for assinged to user id " + assignedTo  , referenceId, ex);
		}
		return result;
	}

	@Override
	public List<LeaveApply> getByAssignedToAndStatus(String assignedTo, String status) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAV-APP");
		List<LeaveApply> result=null;
		try{
			result=repo.findByAssignedToAndStatus(assignedTo, status);
		} catch (Exception ex) {
			throw new UnableToReadException("applyleave-007", "Error while reading Leave apply details for assinged to user id " + assignedTo + " status " + status  , referenceId, ex);
		}
		return result;
	}

	@Override
	public LeaveApply getById(String id) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAV-APP");
		LeaveApply result=null;
		try{
			result=repo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("applyleave-008", "Error while reading Leave apply details for user id " + id  , referenceId, ex);
		}
		return result;
	}

	@Override
	public LeaveApply saveOrUpdate(LeaveApply leaveApply) throws AppException {
		// TODO Auto-generated method stub
		String referenceId=Utils.getReferenceId("LEAV-APP");
		try{
			repo.save(leaveApply);
		} catch (Exception ex) {
			throw new UnableToPersistException("applyleave-009", "Error while saving/updating Leave apply details"  , referenceId, ex,new Gson().toJson(leaveApply));
		}
		return leaveApply;
	}

	@Override
	public void delete(LeaveApply id) throws AppException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Query query, Update update) {
		// TODO Auto-generated method stub
		
	}



	
 
}
