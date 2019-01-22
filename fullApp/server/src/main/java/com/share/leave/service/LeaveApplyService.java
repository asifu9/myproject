package com.share.leave.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.share.domain.core.LeaveApply;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface LeaveApplyService {

    List<LeaveApply> getByUserId(String userId) throws AppException;
    List<LeaveApply> getByUserIdAndYearRange(String userId,String yearRange) throws AppException;
    List<LeaveApply> getByUserIdAndStatus(String userId,String status) throws AppException;
    List<LeaveApply> getByUserIdAndLeaveName(String userId,String leaveName) throws AppException;
    List<LeaveApply> getByCompanyIdAndLeaveName(String companyId,String leaveName) throws AppException;
    
    List<LeaveApply> getByAssignedTo(String assignedTo) throws AppException;
    List<LeaveApply> getByAssignedToAndStatus(String assignedTo,String status) throws AppException;

    LeaveApply getById(String id) throws AppException;
    

    LeaveApply saveOrUpdate(LeaveApply leaveApply) throws AppException;

    void delete(LeaveApply id) throws AppException;
    


	
	
	void update(Query query, Update update);

}
