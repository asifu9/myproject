package com.share.ticket.service;

import java.util.List;

import com.share.domain.Ticket;
import com.share.exception.AppException;
import com.share.exception.AppException;
/**
 * Created by jt on 1/10/17.
 */
public interface TicketService {


    Ticket getById(String id) throws AppException;
    
    Ticket getByIdFull(String id)throws AppException;

    Ticket saveOrUpdate(Ticket product)throws AppException;

	List<Ticket> getByTenantIdAndCreatedById(String tenantId,String createdById)throws AppException;
	
	List<Ticket> getByTenantIdAndAssignedTo(String tenantId,String assignedTo)throws AppException;
	
	List<Ticket> getByTenantIdAndCategory(String tenantId,String createdById)throws AppException;
	
	List<Ticket> getByTenantIdAndCategoryAndStatus(String tenantId,String createdById,String status)throws AppException;
	
	

}
