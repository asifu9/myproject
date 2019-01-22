package com.share.message.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.MessageChannel;

public interface MessageChannelRepository extends CrudRepository<MessageChannel, String>{


	List<MessageChannel> findByTenantId(String pkey);
	List<MessageChannel> findByCreatedByAndTenantId(String createdBy,String pkey);
	MessageChannel findById(String id);
	 List<MessageChannel> findByNameRegex(String name);
	
}
