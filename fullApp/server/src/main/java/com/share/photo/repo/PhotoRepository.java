package com.share.photo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.base.domain.Photo;

public interface PhotoRepository extends CrudRepository<Photo, String>{

    List<Photo> findAll();

    Photo findById(String id);


    void delete(String id);
    
    List<Photo> findByAlbumId(String albumId);
    
    List<Photo> findByCreatedBy(String createdBy);

	void deleteById(String id);
	
	void delete(Photo feed); 
	
	
}
