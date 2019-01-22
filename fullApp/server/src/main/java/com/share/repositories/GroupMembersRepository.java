package com.share.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.share.domain.GroupMembers;

public interface GroupMembersRepository extends CrudRepository<GroupMembers, String>{


	List<GroupMembers> findByGroupId(String groupId);
	List<GroupMembers> findByGroupIdAndStatus(String groupId,String status);
	List<GroupMembers> findByUserIdAndStatus(String userId,String status);
	GroupMembers findById(String id);
	
	void deleteById(String id);
	void delete(GroupMembers user);
	void deleteByTenantIdAndId(String pkey,String id);
}
