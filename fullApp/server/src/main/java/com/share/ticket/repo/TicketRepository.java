package com.share.ticket.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, String>{

	
	Ticket findByTenantIdAndId(String tenantId,String id);
	Ticket findById(String id);
	List<Ticket> findByTenantIdAndCreatedBy(String tenantId,String createdBy);
	List<Ticket> findByTenantIdAndAssignedTo(String tenantId, String assignedTo);
	List<Ticket> findByTenantIdAndTicketCategoryId(String tenantId, String ticketCategoryId);
	List<Ticket> findByTenantIdAndTicketCategoryIdAndStatus(String tenantId, String categoryId, String status);
	
}
