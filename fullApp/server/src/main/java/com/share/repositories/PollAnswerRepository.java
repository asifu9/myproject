package com.share.repositories;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.PollAnswer;

public interface PollAnswerRepository extends CrudRepository<PollAnswer, String>{


	PollAnswer findById(String id);
	
	void deleteById(String id);
	void delete(PollAnswer user);
	void deleteByTenantIdAndId(String pkey,String id);
}
