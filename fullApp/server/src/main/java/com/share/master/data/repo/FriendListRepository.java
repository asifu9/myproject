package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.FriendList;

public interface FriendListRepository extends CrudRepository<FriendList, String>{

	
	FriendList findById(String id);
	
	
}
