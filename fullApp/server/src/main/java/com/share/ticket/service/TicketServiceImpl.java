package com.share.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.Ticket;
import com.share.exception.AppException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.service.UserService;
import com.share.ticket.repo.TicketRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
    private TicketRepository ticketRepo;
	
	@Autowired
	private UserService userService;

	@Override
	public Ticket getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET");
		try{
		return ticketRepo.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("ticket-001", "Error while fetching Ticket details by id " + id , referenceId, ex);
		}
	}

	@Override
	public Ticket saveOrUpdate(Ticket ticket) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET");
		try{
		return ticketRepo.save(ticket);
		} catch (Exception ex) {
			throw new UnableToPersistException("ticket-002", "Error while fetching Ticket details" , referenceId, ex,new Gson().toJson(ticket));
		}
	}

	@Override
	public List<Ticket> getByTenantIdAndCreatedById(String tenantId, String createdById) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET");
		try{
		return ticketRepo.findByTenantIdAndCreatedBy(tenantId, createdById);
		} catch (Exception ex) {
			throw new UnableToReadException("ticket-003", "Error while fetching Ticket details by tenant id " + 
					tenantId + " and created by id " + createdById , referenceId, ex);
		}
	}



	@Override
	public List<Ticket> getByTenantIdAndCategory(String tenantId, String ticketCategoryId) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET");
		try{
		return ticketRepo.findByTenantIdAndTicketCategoryId(tenantId, ticketCategoryId);
		} catch (Exception ex) {
			throw new UnableToReadException("ticket-004", "Error while fetching Ticket details by tenant id " +
					tenantId + " and ticket category id " + ticketCategoryId , referenceId, ex);
		}
	}

	@Override
	public List<Ticket> getByTenantIdAndAssignedTo(String tenantId, String assignedTo) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET");
		try{
		return ticketRepo.findByTenantIdAndAssignedTo(tenantId, assignedTo);
		} catch (Exception ex) {
			throw new UnableToReadException("ticket-005", "Error while fetching Ticket details by company id  " + 
					tenantId + " and assigned to user id " + assignedTo , referenceId, ex);
		}
	}


	@Override
	public List<Ticket> getByTenantIdAndCategoryAndStatus(String tenantId, String categoryId, String status) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET");
		try{
		return ticketRepo.findByTenantIdAndTicketCategoryIdAndStatus(tenantId, categoryId,status);
		} catch (Exception ex) {
			throw new UnableToReadException("ticket-005", "Error while fetching Ticket details by company id  " + 
					tenantId + " and category id " + categoryId + "  and status "  + status, referenceId, ex);
		}
	}

	@Override
	public Ticket getByIdFull(String id) throws AppException{
		String referenceId=Utils.getReferenceId("TICKET");
		try{
			Ticket ticket=ticketRepo.findById(id);
			ticket.setCreatedByUser(userService.getByIdWithPhoto(id));
			if(ticket.getAssignedTo()!=null){
				ticket.setAssignedToUser(userService.getByIdWithPhoto(ticket.getAssignedTo()));
			}
			if(ticket.getCreatedBy()!=null){
				ticket.setCreatedByUser(userService.getByIdWithPhoto(ticket.getCreatedBy()));
			}
			if(ticket.getAttachments()!=null){
				ticket.getAttachments().forEach(i->{
					if(i.getUploadedBy()!=null){
						try {
							i.setUploadedByUser(userService.getByIdWithPhoto(i.getUploadedBy()));
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			
			if(ticket.getUpdates()!=null && ticket.getUpdates().size()>0){
				ticket.getUpdates().forEach(i->{
					try {
						i.setUpdatedByUser(userService.getByIdWithPhoto(i.getUpdatedBy()));
					} catch (AppException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
			return ticket;
		} catch (Exception ex) {
			throw new UnableToReadException("ticket-006", "Error while fetching Ticket details by id full data " + id , referenceId, ex);
		}
	}

 
}
