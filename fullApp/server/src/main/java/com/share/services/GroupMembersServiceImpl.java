package com.share.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.share.domain.GroupMembers;
import com.share.exception.AppException;
import com.share.repositories.GroupMembersRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class GroupMembersServiceImpl implements GroupMembersService {

    private GroupMembersRepository repo;
    @Autowired
    MongoTemplate template;
    @Autowired
    public GroupMembersServiceImpl(GroupMembersRepository f) throws AppException{
        this.repo = f;
    }


    @Override
    public List<GroupMembers> listAll() throws AppException{
        List<GroupMembers> products = new ArrayList<>();
        repo.findAll().forEach(products::add); //fun with Java 8
        return products;
    }

    @Override
    public GroupMembers getById(String id)throws AppException {
        return repo.findById(id);
    }

    @Override
    public GroupMembers saveOrUpdate(GroupMembers feed) throws AppException{
        repo.save(feed);
        return feed;
    }

    @Override
    public void delete(String id)throws AppException {
        repo.delete(id);

    }
    
    @Override
    public void deleteById(String id)throws AppException {
        repo.delete(id);

    }

	@Override
	public void delete(GroupMembers user)throws AppException {
		
	}


	@Override
	public List<GroupMembers> listByGroupId(String userId)throws AppException {
		return repo.findByGroupId(userId);
	}


	@Override
	public List<GroupMembers> listByGroupIdWithStatus(String groupId, String status) throws AppException{
		return repo.findByGroupIdAndStatus(groupId, status);
	}


	@Override
	public List<GroupMembers> listByUserIdWithStatus(String userId, String status)throws AppException {
		// TODO Auto-generated method stub
			return repo.findByUserIdAndStatus(userId, status);
	}
 
}
