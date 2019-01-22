package com.share.feeds.api;

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
import com.share.domain.FeedComments;
import com.share.exception.AppException;
import com.share.feeds.service.FeedCommentService;
import com.share.feeds.service.FeedService;
import com.share.master.data.service.UserService;


@RestController
@RequestMapping("/FeedComment")
@CrossOrigin(origins = "http://localhost:4200")
public class FeedCommentApi {

	@Autowired
	private FeedCommentService feedCommentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FeedService feedService;
	
	@RequestMapping(method=RequestMethod.GET,value="/like/{feedId}/{commentId}/{userId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<CBlock> updateLikesForComments(@PathVariable(value="feedId") String feedId,
			@PathVariable(value="commentId") String commentId,
			@PathVariable(value="userId") String userId) throws AppException{
	
		CBlock comments=updateFeedCommentLikes(feedId,commentId,userId);
		comments.setCreatedOnStr(new TimeDifference(Calendar.getInstance().getTime(),comments.getCreatedOn()).getDifferenceString());
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<CBlock>(comments, headers, HttpStatus.CREATED);
	}
	
	private synchronized CBlock updateFeedCommentLikes(String feedId,String commentId,String userId) throws AppException{
		FeedComments comments= feedCommentService.getById(feedId);
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
		
		feedCommentService.saveOrUpdate(comments);
		
		return bb;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/{feedId}",produces=MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<FeedComments> createUpdate(@PathVariable(value="feedId") String feedId,@RequestBody CBlock cblock) throws AppException{
			//its a new record to create
		FeedComments comments= feedCommentService.getById(feedId);
		Date date=Calendar.getInstance().getTime();
		List<CBlock> block=null;
		if(null==comments){
			comments=new FeedComments();
			comments.setId(feedId);
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
		feedCommentService.saveOrUpdate(comments);
		Update up=new Update();
		up.set("commentCount", block.size());
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(feedId));
		feedService.update(query, up);
		if(comments!=null && comments.getComments()!=null ){
			for (CBlock c : comments.getComments()) {
				if(c.getUser()==null){
					c.setUser(userService.getByIdOnFewFields(c.getUserId()));
				}
			}
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<FeedComments>(comments, headers, HttpStatus.CREATED);
		
	}
	
	
}
