package com.share.feeds.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.share.domain.FeedLikes;

public interface FeedLikesRepository extends MongoRepository<FeedLikes, String>{

	
	List<FeedLikes> findByLikedBy(String likedBy);
	FeedLikes findById(String id);
	
	void deleteById(String id);
	void delete(FeedLikes user);
	void deleteByTenantIdAndId(String TenantId,String id);
}
