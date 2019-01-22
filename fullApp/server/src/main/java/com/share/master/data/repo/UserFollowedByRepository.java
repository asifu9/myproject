package com.share.master.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.UserFollowedBy;

public interface UserFollowedByRepository extends CrudRepository<UserFollowedBy, String>{


	UserFollowedBy findById(String id);
	
}
