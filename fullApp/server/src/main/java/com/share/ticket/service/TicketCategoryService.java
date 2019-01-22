package com.share.ticket.service;

import java.util.List;

import com.share.domain.TicketCategory;
import com.share.exception.AppException;
/**
 * Created by jt on 1/10/17.
 */
public interface TicketCategoryService {


    TicketCategory getById(String id)throws AppException;
    
    TicketCategory getById(String tenantId,String id)throws AppException;

    TicketCategory saveOrUpdate(TicketCategory product)throws AppException;
    
    List<TicketCategory> getByTenantAndActive(String tenantId,boolean isActive)throws AppException;

	List<TicketCategory> getByUserId(String userId)throws AppException;
	
	void delete(String id) throws AppException;

}
