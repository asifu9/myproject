package com.share.photo.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

import com.base.util.TimeDifference;
import com.base.util.Utils;
import com.share.domain.CBlock;
import com.share.domain.PhotoComments;
import com.share.exception.AppException;
import com.share.master.data.service.UserService;
import com.share.photo.service.PhotoCommentService;
import com.share.photo.service.PhotoService;


@RestController
@RequestMapping("/PhotoComment")
@CrossOrigin(origins = "http://localhost:4200")
public class PhotoCommentApi {

	@Autowired
	private PhotoCommentService photoCommentService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(method=RequestMethod.GET,value="/like/{photoId}/{commentId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<CBlock> updateLikesForComments(@PathVariable(value="photoId") String photoId,
			@PathVariable(value="commentId") String commentId,
			@PathVariable(value="userId") String userId) throws AppException{
	
		CBlock comments=updatePhotoCommentLikes(photoId,commentId,userId);
		comments.setCreatedOnStr(new TimeDifference(Calendar.getInstance().getTime(),comments.getCreatedOn()).getDifferenceString());
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<CBlock>(comments, headers, HttpStatus.CREATED);
	}
	
	private synchronized CBlock updatePhotoCommentLikes(String photoId,String commentId,String userId) throws AppException{
		PhotoComments comments= photoCommentService.getById(photoId);
		CBlock bb=null;
		for (CBlock b : comments.getComments()) {
			if(b.getId().equals(commentId)){
				if(b.getLikedBy()!=null){
					if(b.getLikedBy().containsKey(userId)){
						b.getLikedBy().remove(userId);
					}else{
						b.getLikedBy().put(userId,Calendar.getInstance().getTime());
					}
					b.setLikeCount(b.getLikedBy().size());
				}else{
					Map<String,Date> map=new HashMap<>();
					map.put(userId, Calendar.getInstance().getTime());
					b.setLikedBy(map);
					b.setLikeCount(b.getLikedBy().size());
				}
				bb=b;
				bb.setUser(userService.getById(b.getUserId()));
				bb.setCreatedOnStr(Utils.convertDate(b.getCreatedOn()));
				break;
			}
		}
		
		photoCommentService.saveOrUpdate(comments);
		
		return bb;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/commemnts/{photoId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getComments(@PathVariable(value="photoId")String photoId) throws AppException{
		PhotoComments comm= photoCommentService.getById(photoId);
		if(comm!=null && comm.getComments()!=null && comm.getComments().size()>0){
			for(CBlock i :comm.getComments()){
				if(i.getUser()==null){
					i.setUser(userService.getByIdWithPhoto(i.getUserId()));
					i.setCreatedOnStr(new TimeDifference(Calendar.getInstance().getTime(),i.getCreatedOn()).getDifferenceString());
				}
			}
		}
		

		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<PhotoComments>(comm, headers, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/{photoId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<PhotoComments> createUpdate(@PathVariable(value="photoId") String photoId,@RequestBody CBlock cblock) throws AppException{
			//its a new record to create
		PhotoComments comments= photoCommentService.getById(photoId);
		Date date=Calendar.getInstance().getTime();
		List<CBlock> block=null;
		if(null==comments){
			comments=new PhotoComments();
			comments.setId(photoId);
			block=new ArrayList<>();
			
			comments.setComments(block);
			comments.setTenantId(userService.getById(cblock.getUserId()).getTenantId());
			 
		}else{
			block=comments.getComments();
		}
		cblock.setId(UUID.randomUUID().toString());
		cblock.setCreatedOn(date);
		cblock.setUser(userService.getById(cblock.getUserId()));
		cblock.setCreatedOnStr(new TimeDifference(Calendar.getInstance().getTime(),date).getDifferenceString());
		block.add(cblock);
		comments.setCount(block.size());
		Collections.sort(block);
		photoCommentService.saveOrUpdate(comments);
		Update up=new Update();
		up.set("count", block.size());
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(photoId));
		photoService.update(query, up);
		PhotoComments comm= photoCommentService.getById(photoId);
		if(comm!=null && comm.getComments()!=null && comm.getComments().size()>0){
			for(CBlock i:comm.getComments()){
				if(i.getUser()==null){
					i.setUser(userService.getByIdWithPhoto(i.getUserId()));
					i.setCreatedOnStr(new TimeDifference(Calendar.getInstance().getTime(),i.getCreatedOn()).getDifferenceString());
				}
			}
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<PhotoComments>(comm, headers, HttpStatus.CREATED);
		
	}
	
	
}
