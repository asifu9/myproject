package com.share.feeds.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import com.share.domain.Feed;

public interface FeedsRepository extends MongoRepository<Feed, String>{

	List<Feed> findByCreatedBy(String createdBy);
	Feed findById(String id);
	void deleteById(String id);
	void delete(Feed user);
	
}
