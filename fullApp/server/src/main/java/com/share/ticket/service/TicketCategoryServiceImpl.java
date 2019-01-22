package com.share.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.TicketCategory;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.ticket.repo.TicketCategoryRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class TicketCategoryServiceImpl implements TicketCategoryService {

	@Autowired
    private TicketCategoryRepository ticketCategoryRepo;;

	@Override
	public TicketCategory getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET-CATEGORY");
		try{
		return ticketCategoryRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("ticket-category-001", "Error while fetching Ticket Category details by id " + id , referenceId, ex);
		}
	}

	@Override
	public TicketCategory getById(String tenantId, String id) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET-CATEGORY");
		try{
		return ticketCategoryRepo.findByTenantIdAndId(tenantId, id);
		} catch (Exception ex) {
			throw new UnableToReadException("ticket-category-002", "Error while fetching Ticket Category details by company Id " +
					tenantId + " and id " + id , referenceId, ex);
		}
	}

	@Override
	public TicketCategory saveOrUpdate(TicketCategory product) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET-CATEGORY");
		try{
		return ticketCategoryRepo.save(product);
		} catch (Exception ex) {
			throw new UnableToPersistException("ticket-category-003", "Error while fetching Ticket Category details ", referenceId, ex,new Gson().toJson(product));
		}
	}

	@Override
	public List<TicketCategory> getByTenantAndActive(String tenantId, boolean isActive) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET-CATEGORY");
		try{
		return ticketCategoryRepo.findByTenantIdAndIsActive(tenantId, isActive);
		} catch (Exception ex) {
			throw new UnableToReadException("ticket-category-004", "Error while fetching Ticket Category details by company Id " +
					tenantId + " and isActive " + isActive , referenceId, ex);
		}
	}

	@Override
	public List<TicketCategory> getByUserId(String userId) throws AppException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) throws AppException {
		String referenceId=Utils.getReferenceId("TICKET-CATEGORY");
		try{
		 ticketCategoryRepo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("ticket-category-005", "Error while deleting Ticket Category details by  Id " +
					id  , referenceId, ex);
		}		
	}


 
}
