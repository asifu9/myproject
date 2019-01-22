package com.share.ticket.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.TicketCategory;

public interface TicketCategoryRepository extends CrudRepository<TicketCategory, String>{

	
	List<TicketCategory> findByTenantIdAndIsActive(String TenantId,boolean isActive);
	TicketCategory findById(String id);
	TicketCategory findByTenantIdAndId(String TenantId,String  id);
	
}
