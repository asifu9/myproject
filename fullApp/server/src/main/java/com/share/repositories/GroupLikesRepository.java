package com.share.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.share.domain.FeedLikes;
import com.share.domain.GroupLikes;

public interface GroupLikesRepository extends MongoRepository<GroupLikes, String>{

	
	List<GroupLikes> findByLikedBy(String likedBy);
	GroupLikes findById(String id);
	
	void deleteById(String id);
	void delete(GroupLikes user);
	void deleteByTenantIdAndId(String pkey,String id);
}
