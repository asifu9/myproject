package com.share.photo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.share.domain.PhotoComments;

public interface PhotoCommentsRepository extends MongoRepository<PhotoComments, String>{


	PhotoComments findById(String id);
	
	void deleteById(String id);
	void delete(PhotoComments user);
	void deleteByTenantIdAndId(String TenantId,String id);
}
