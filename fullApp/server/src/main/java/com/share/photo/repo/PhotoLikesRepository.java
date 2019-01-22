package com.share.photo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.base.domain.PhotoLikes;

public interface PhotoLikesRepository extends MongoRepository<PhotoLikes, String>{

	
	List<PhotoLikes> findByLikedBy(String likedBy);
	PhotoLikes findById(String id);
	
	void deleteById(String id);
	void delete(PhotoLikes user);
	void deleteByTenantIdAndId(String pkey,String id);
}
