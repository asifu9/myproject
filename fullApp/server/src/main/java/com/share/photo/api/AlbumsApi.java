package com.share.photo.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.base.domain.Photo;
import com.base.domain.PhotoAlbum;
import com.base.util.TimeDifference;
import com.share.api.DateTimeCal;
import com.share.exception.AppException;
import com.share.master.data.service.UserService;
import com.share.photo.service.PhotoAlbumService;
import com.share.photo.service.PhotoLikeService;
import com.share.photo.service.PhotoService;


@RestController
@RequestMapping("/Albums")
@CrossOrigin(origins = "http://localhost:4200")
public class AlbumsApi {

	@Autowired
	private PhotoAlbumService photoAlbumService;
	
	@Autowired
	private PhotoLikeService photoLikeService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.POST,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<PhotoAlbum> create(@RequestBody PhotoAlbum user) throws AppException{
			//its a new record to create
			user.setCreatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		
		user.setId(UUID.randomUUID().toString());
		user.setTenantId(userService.getById(user.getCreatedBy()).getTenantId());
		photoAlbumService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<PhotoAlbum>(user, headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/photo/update/{id}",consumes=MediaType.TEXT_PLAIN_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Photo> update(@PathVariable(value="id") String id,
			@RequestBody String description) throws AppException{
			//its a new record to create
			if(description.equals(" ")){
				description="";
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Photo f=photoService.getById(id);
			f.setDescription(description);
			f.setUpdatecOn(DateTimeCal.getCurrentDateTimeSeconds());        
			//its an update record
			//first fetch the data for given id
		
		photoService.saveOrUpdate(f);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Photo>(f, headers, HttpStatus.ACCEPTED);
		
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<PhotoAlbum> update(@RequestBody PhotoAlbum user) throws AppException{
			//its a new record to create
			user.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
			//its an update record
			//first fetch the data for given id
		
		photoAlbumService.saveOrUpdate(user);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<PhotoAlbum>(user, headers, HttpStatus.CREATED);
		
	}
	@RequestMapping(method=RequestMethod.GET,value="/user/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<PhotoAlbum>> getByUserId(@PathVariable(value = "id") String id ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<PhotoAlbum> pa=photoAlbumService.listByCreatedBy(id);
		if(pa!=null && pa.size()>0){
			for(PhotoAlbum i:pa){

				if(i.getPhotoId()!=null){
					i.setPhoto(photoService.getById(i.getPhotoId()));
				}else{
					List<Photo> ll=photoService.listByAlbumId(i.getId());
					if(ll!=null && ll.size()>0){
						i.setPhoto(ll.get(0));
					}
				}
			}
		}
		return new ResponseEntity<List<PhotoAlbum>>( pa,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/filter/{albumId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Photo>> getByAlbumWithExcludePhotos(@PathVariable(value = "albumId") String albumId) throws AppException{
		List<Photo> photoList=photoService.listByAlbumId(albumId);
		List<Photo> finalList=new ArrayList<>();
		
		if(photoList!=null && photoList.size()>0){
			for(Photo i:photoList){
				
						i.setLikes(photoLikeService.getById(i.getId()));
						try {
							i.setCreatedByObj(userService.getById(i.getCreatedBy()));
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//i.setCreatedOnStr(new TimeDifference(Calendar.getInstance().getTime(),Calendar.get i.getCreatedOn()).getDifferenceString());
						finalList.add(i);
			}
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<List<Photo>>( finalList,headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/photos/{albumId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Photo>> getPhotosByAlbumId(@PathVariable(value = "albumId") String albumId ) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		List<Photo> photos=photoService.listByAlbumId(albumId);
		for(Photo item:photos){
			item.setLikes(photoLikeService.getById(item.getId()));
			try {
				item.setCreatedByObj(userService.getById(item.getCreatedBy()));
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	item.setCreatedOnStr(new TimeDifference(Calendar.getInstance().getTime(), item.getCreatedOn()).getDifferenceString());
			
		}
		return new ResponseEntity<List<Photo>>(photos ,headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/albums/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<PhotoAlbum> getById(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<PhotoAlbum>(photoAlbumService.getById(id),headers,HttpStatus.OK);
	}
	@RequestMapping(method=RequestMethod.GET,value="/photo/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Photo> getPhotoById(@PathVariable(value = "id") String id )throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<Photo>(photoService.getById(id),headers,HttpStatus.OK);
	}
//	@RequestMapping(method=RequestMethod.GET,value="/",produces=MediaType.APPLICATION_JSON_VALUE)
//	private ResponseEntity<List<PhotoAlbum>> getAll()throws AppException{
//		MultiValueMap<String, String> headers = new HttpHeaders();
//		return new ResponseEntity<List<PhotoAlbum>>( photoAlbumService.listAll(),headers,HttpStatus.OK);
//	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/delete/{id}")
	private ResponseEntity deleteById(@PathVariable(value="id") String id) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		photoAlbumService.delete(id);
		return new ResponseEntity( headers,HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/delete/photo/{id}")
	private ResponseEntity deletePhoto(@PathVariable(value="id") String id) throws AppException{
		MultiValueMap<String, String> headers = new HttpHeaders();
		photoService.delete(id);
		return new ResponseEntity( headers,HttpStatus.OK);
	}
}
