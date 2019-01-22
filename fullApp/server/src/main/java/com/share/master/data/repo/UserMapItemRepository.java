package com.share.master.data.repo;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.UserMapItems;

public interface UserMapItemRepository extends CrudRepository<UserMapItems, String>{


	UserMapItems findByIdAndTenantId(String userId,String TenantId);
	UserMapItems findById(String userId);
	
}
