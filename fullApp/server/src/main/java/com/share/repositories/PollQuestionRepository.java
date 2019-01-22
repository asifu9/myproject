package com.share.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.PollQuestion;

public interface PollQuestionRepository extends CrudRepository<PollQuestion, String>{

	
	List<PollQuestion> findByCreatedBy(String likedBy);
	PollQuestion findById(String id);
	
	void deleteById(String id);
	void delete(PollQuestion user);
	void deleteByTenantIdAndId(String pkey,String id);
}
