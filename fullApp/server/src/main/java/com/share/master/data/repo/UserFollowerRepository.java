package com.share.master.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.UserFollower;

public interface UserFollowerRepository extends CrudRepository<UserFollower, String>{


	UserFollower findById(String id);
	
}
