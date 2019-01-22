package com.share.notification.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.notification.LikeNotification;

public interface LikeNotificationRepository extends CrudRepository<LikeNotification, String>{

	

	List<LikeNotification> findByDestinationUserIdAndStatus(String userId,int status);
	List<LikeNotification> findBySourceUserIdAndStatus(String userId,int status);
	LikeNotification findById(String id);
	
	void deleteById(String id);
	void delete(LikeNotification user);
	void deleteByTenantIdAndId(String pkey,String id);
}
