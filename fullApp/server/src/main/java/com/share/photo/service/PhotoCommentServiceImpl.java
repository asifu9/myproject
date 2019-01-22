package com.share.photo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.domain.PhotoComments;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.photo.repo.PhotoCommentsRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class PhotoCommentServiceImpl implements PhotoCommentService {

	private PhotoCommentsRepository feedCommentsRepository;

	@Autowired
	public PhotoCommentServiceImpl(PhotoCommentsRepository feedCommentsRepository) throws AppException{
		this.feedCommentsRepository = feedCommentsRepository;
	}




	@Override
	public PhotoComments getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO-COMMENT");
		try{
			return feedCommentsRepository.findById(id);
		} catch (Exception ex) {
			throw new UnableToReadException("photo-comment-001", "Error while fetching Photo Comments details by id " + id , referenceId, ex);
		}
	}

	@Override
	public PhotoComments saveOrUpdate(PhotoComments feed) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO-COMMENT");
		try{
			feedCommentsRepository.save(feed);
			return feed;
		} catch (Exception ex) {
			throw new UnableToPersistException("photo-comment-002", "Error while saving/updating Photo Comments detail", referenceId, ex,new Gson().toJson(feed));
		}
	}

	@Override
	public void delete(String id) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO-COMMENT");
		try{

			feedCommentsRepository.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("photo-comment-003", "Error while deleting Photo Comments details by id " + id , referenceId, ex);
		}

	}







}
