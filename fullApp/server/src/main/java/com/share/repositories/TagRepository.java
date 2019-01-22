package com.share.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.FeedLikes;
import com.share.domain.Tag;

public interface TagRepository extends CrudRepository<Tag, String>{


	List<Tag> findByObjectId(String objectId);
	List<Tag> findByUserId(String userId);
	Tag findById(String id);
	
	void deleteById(String id);
	void delete(Tag user);
	void deleteByTenantIdAndId(String pkey,String id);
}
