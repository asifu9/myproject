package com.share.photo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.domain.PhotoLikes;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.photo.repo.PhotoLikesRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class PhotoLikeServiceImpl implements PhotoLikeService {

    private PhotoLikesRepository feedLikesRepository;

    @Autowired
    public PhotoLikeServiceImpl(PhotoLikesRepository feedCommentsRepository) throws AppException{
        this.feedLikesRepository = feedCommentsRepository;
    }


    @Override
    public PhotoLikes getById(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("PHOTO-LIKE");
		try{
        return feedLikesRepository.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("photo-like-001", "Error while fetching Photo Like details by id " + id , referenceId, ex);
		}
    }

    @Override
    public PhotoLikes saveOrUpdate(PhotoLikes feed) throws AppException{
    	String referenceId=Utils.getReferenceId("PHOTO-LIKE");
		try{
    	feedLikesRepository.save(feed);
        return feed;
		} catch (Exception ex) {
			throw new UnableToPersistException("photo-like-002", "Error while saving/updating Photo Like details" , referenceId, ex,new Gson().toJson(feed));
		}
    }

    @Override
    public void delete(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("PHOTO-LIKE");
		try{
    	feedLikesRepository.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("photo-like-003", "Error while deleting Photo Like details by id " + id , referenceId, ex);
		}

    }
    
	@Override
	public List<PhotoLikes> listByLikedBy(String likedBy) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO-LIKE");
		try{
		return feedLikesRepository.findByLikedBy(likedBy);
		} catch (Exception ex) {
			throw new UnableToReadException("photo-like-004", "Error while fetching Photo Like details by liked by user id " + likedBy , referenceId, ex);
		}
	
	}

 
}
