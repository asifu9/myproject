package com.share.photo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.base.domain.Photo;
import com.base.util.Utils;
import com.google.gson.Gson;
import com.share.exception.AppException;
import com.share.exception.UnableToDeleteException;
import com.share.exception.UnableToPersistException;
import com.share.exception.UnableToReadException;
import com.share.master.data.service.UserService;
import com.share.photo.repo.PhotoRepository;
/**
 * Created by jt on 1/10/17.
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    private PhotoRepository photoRepo;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    MongoTemplate template;

    @Autowired
    public PhotoServiceImpl(PhotoRepository f) throws AppException{
        this.photoRepo = f;
    }




    @Override
    public Photo getById(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("PHOTO");
		try{
    	Photo ii=photoRepo.findById(id);
    	
        return ii;
		} catch (Exception ex) {
			throw new UnableToReadException("photo-001", "Error while fetching Photo details by id " + id , referenceId, ex);
		}
    }

    @Override
    public Photo saveOrUpdate(Photo feed) throws AppException{
    	String referenceId=Utils.getReferenceId("PHOTO");
		try{
    	photoRepo.save(feed);
        return feed;
    } catch (Exception ex) {
		throw new UnableToPersistException("photo-002", "Error while saving/updating Photo details" , referenceId, ex,new Gson().toJson(feed));
	}
    }

    @Override
    public void delete(String id) throws AppException{
    	String referenceId=Utils.getReferenceId("PHOTO");
		try{
        photoRepo.delete(id);
		} catch (Exception ex) {
			throw new UnableToDeleteException("photo-003", "Error while deleting Photo details by id " + id , referenceId, ex);
		}
    }

    


	@Override
	public List<Photo> listByAlbumId(String feedId) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO");
		try{
		return photoRepo.findByAlbumId(feedId);
		} catch (Exception ex) {
			throw new UnableToReadException("photo-004", "Error while fetching Photo details by feed id " + feedId , referenceId, ex);
		}
	}





	@Override
	public List<Photo> listByCreatedBy(String createdBy) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO");
		try{
		return photoRepo.findByCreatedBy(createdBy);
		} catch (Exception ex) {
			throw new UnableToReadException("photo-005", "Error while fetching Photo details by created by user id " + createdBy , referenceId, ex);
		}
	
	}


	@Override
	public void update(Query query, Update update) throws AppException{
		String referenceId=Utils.getReferenceId("PHOTO");
		try{
		template.updateFirst(query, update, Photo.class);
		} catch (Exception ex) {
			throw new UnableToPersistException("photo-006", "Error while updating Photo details by query " +
					new Gson().toJson(query) , referenceId, ex,new Gson().toJson(update));
		}
	}




 
}
