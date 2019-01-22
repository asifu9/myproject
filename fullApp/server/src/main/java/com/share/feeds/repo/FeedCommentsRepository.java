package com.share.feeds.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.share.domain.FeedComments;

public interface FeedCommentsRepository extends MongoRepository<FeedComments, String>{


	FeedComments findById(String id);
	
	void deleteById(String id);
	void delete(FeedComments user);
	void deleteByTenantIdAndId(String pkey,String id);
}
