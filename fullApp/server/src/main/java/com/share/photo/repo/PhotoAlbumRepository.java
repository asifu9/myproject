package com.share.photo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.base.domain.PhotoAlbum;

@Repository
public interface PhotoAlbumRepository extends CrudRepository<PhotoAlbum, String>{

	
	  List<PhotoAlbum> findAll();

	  PhotoAlbum findById(String id);


	    void delete(String id);
	    
	    List<PhotoAlbum> findByCreatedBy(String createdBy);
	    
	   // List<PhotoAlbum> findByName(String name);

		void deleteById(String id);
		
		void delete(PhotoAlbum feed); 
		
}
