package com.share.message.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.Messages;

public interface MessageRepository extends CrudRepository<Messages, String>{


	List<Messages> findByMessagedByAndTenantId(String messagedBy,String pkey);
	List<Messages> findByMessagedToAndTenantId(String messagedTo,String pkey);
	List<Messages> findByGroupId(String groupId);
	Messages findById(String id);
	
}
