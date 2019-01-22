package com.share.photo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.domain.PhotoAlbum;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.photo.repo.PhotoAlbumRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class PhotoAlbumServiceImpl implements PhotoAlbumService {

	private PhotoAlbumRepository photoAlbumRepository;

	//    @Autowired
	//    public PhotoAlbumServiceImpl(PhotoAlbumRepository photoRepo) throws AppException{
	//        this.photoRepo = photoRepo;
	//    }
	@Autowired
	public PhotoAlbumServiceImpl(PhotoAlbumRepository photoAlbumRepository) throws AppException{
		this.photoAlbumRepository = photoAlbumRepository;
	}



	@Override
	public PhotoAlbum getById(String id) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO-ALBUM");
		try{
			return photoAlbumRepository.findById(id);

		} catch (Exception ex) {
			throw new UnableToReadException("photo-album-001", "Error while reading Photo Alubm details by id " + id , referenceId, ex);
		}
	}

	@Override
	public PhotoAlbum saveOrUpdate(PhotoAlbum feed) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO-ALBUM");
		try{
		photoAlbumRepository.save(feed);
		return feed;
		} catch (Exception ex) {
			throw new UnableToPersistException("photo-album-002", "Error while saving/updating Photo Alubm details ", referenceId, ex,new Gson().toJson(feed));
		}
	}

	@Override
	public void delete(String id) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO-ALBUM");
		try{
	
		photoAlbumRepository.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("photo-album-003", "Error while deleting Photo Alubm details by id " + id , referenceId, ex);
		}

	}

	@Override
	public List<PhotoAlbum> listByCreatedBy(String createdBy) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO-ALBUM");
		try{
		return photoAlbumRepository.findByCreatedBy(createdBy);
		} catch (Exception ex) {
			throw new UnableToReadException("photo-album-004", "Error while reading Photo Alubm details by created By user id " + createdBy , referenceId, ex);
		}
	}




}
