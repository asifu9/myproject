package com.share.services;

import java.util.List;

import com.share.domain.GroupMembers;
import com.share.exception.AppException;

/**
 * Created by jt on 1/10/17.
 */
public interface GroupMembersService {

    List<GroupMembers> listAll()throws AppException;

    GroupMembers getById(String id)throws AppException;

    GroupMembers saveOrUpdate(GroupMembers feed)throws AppException;

    void delete(String id)throws AppException;
    
    List<GroupMembers> listByGroupId(String userId)throws AppException;
    
    List<GroupMembers> listByGroupIdWithStatus(String groupId,String status)throws AppException;
    
    List<GroupMembers> listByUserIdWithStatus(String userId,String status)throws AppException;

	void deleteById(String id)throws AppException;
	
	void delete(GroupMembers feed)throws AppException; 
	
	

}
