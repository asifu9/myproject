package com.share.notification.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.core.Notification;

public interface NotificationRepository extends CrudRepository<Notification, String>{
/**
 * 	private String id;
	private String type;
	private String url;
	private String valueId;
	private long createdOn;
	private int status;
	private String details;
	private String sourceId;
	private String destinationId;
 * @param tenantId
 * @return
 */
	
	List<Notification> findByUserId(String userId);
	List<Notification> findByUserIdAndCompanyId(String userId,String companyId);
	List<Notification> findByUserIdAndCompanyIdAndStatus(String userId,String companyId,String status);
	Notification findById(String id);
	Notification findByValueId(String id);
	List<Notification> findByUserIdAndStatus(String userId,int status);
	
	
}
