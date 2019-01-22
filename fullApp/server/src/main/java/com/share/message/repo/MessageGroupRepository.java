package com.share.message.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.MessageGroup;

public interface MessageGroupRepository extends CrudRepository<MessageGroup, String>{


	List<MessageGroup> findByTenantId(String pkey);
	MessageGroup findById(String id);
	
}
