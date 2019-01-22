package com.share.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.share.domain.FeedLikes;
import com.share.domain.GroupLikes;
import com.share.exception.AppException;
import com.share.repositories.GroupLikesRepository;

/**
 * Created by jt on 1/10/17.
 */
@Service
public class GroupLikeServiceImpl implements GroupLikeService {

    private GroupLikesRepository feedLikesRepository;

    @Autowired
    public GroupLikeServiceImpl(GroupLikesRepository feedCommentsRepository) throws AppException{
        this.feedLikesRepository = feedCommentsRepository;
    }


    @Override
    public List<GroupLikes> listAll()throws AppException {
        List<GroupLikes> products = new ArrayList<>();
        feedLikesRepository.findAll().forEach(products::add); //fun with Java 8
        return products;
    }

    @Override
    public GroupLikes getById(String id) throws AppException{
        return feedLikesRepository.findById(id);
    }

    @Override
    public GroupLikes saveOrUpdate(GroupLikes feed) throws AppException{
        feedLikesRepository.save(feed);
        return feed;
    }

    @Override
    public void delete(String id) throws AppException{
        feedLikesRepository.delete(id);

    }
    
    @Override
    public void deleteById(String id)throws AppException {
        feedLikesRepository.delete(id);

    }
    
    





	@Override
	public void delete(FeedLikes user) {
		feedLikesRepository.deleteByTenantIdAndId(user.getTenantId(), user.getId());
		
	}




	@Override
	public List<GroupLikes> listByLikedBy(String likedBy) {
		return feedLikesRepository.findByLikedBy(likedBy);
	
	}

 
}
