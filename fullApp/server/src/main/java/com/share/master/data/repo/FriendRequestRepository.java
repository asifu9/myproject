package com.share.master.data.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.FriendRequest;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, String>{
	List<FriendRequest> findByRequestedFromAndTenantIdAndActive(String id,String TenantId,boolean active);
	List<FriendRequest> findByRequestedToAndTenantIdAndActive(String id,String TenantId,boolean active);
	FriendRequest findById(String id);
	List<FriendRequest> findByRequestedFromAndTenantIdAndStatusAndActive(String id,String TenantId,String status,boolean active);
	List<FriendRequest> findByRequestedToAndTenantIdAndStatusAndActive(String id,String TenantId,String status,boolean active);
}
