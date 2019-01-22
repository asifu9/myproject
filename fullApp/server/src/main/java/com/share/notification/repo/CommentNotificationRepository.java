package com.share.notification.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.Feed;
import com.share.domain.FeedComments;
import com.share.domain.notification.CommentNotification;

public interface CommentNotificationRepository extends CrudRepository<CommentNotification, String>{


	List<CommentNotification> findByDestinationUserIdAndStatus(String userId,int status);
	List<CommentNotification> findBySourceUserIdAndStatus(String userId,int status);
	CommentNotification findById(String id);
	
	void deleteById(String id);
	void delete(CommentNotification user);
	void deleteByTenantIdAndId(String pkey,String id);
}
