package com.share.company.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.base.domain.Photo;
import com.base.domain.User;
import com.base.enums.PrivacyStatus;
import com.base.util.TimeDifference;
import com.google.gson.Gson;
import com.share.api.DateTimeCal;
import com.share.company.service.CompanySettingService;
import com.share.domain.CompanySetting;
import com.share.domain.Group;
import com.share.domain.GroupMembers;
import com.share.exception.AppException;
import com.share.feeds.service.FeedService;
import com.share.master.data.service.UserService;
import com.share.photo.service.PhotoService;
import com.share.services.GroupLikeService;
import com.share.services.GroupMembersService;
import com.share.services.GroupService;
import com.share.util.FileThumbnails;

@RestController
@RequestMapping("/CompanySetting")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanySettingApi {

	@Autowired
	private UserService userService;

	@Autowired
	private CompanySettingService companySettingService;

	@Autowired
	private FileThumbnails fileThumbs;

	@Value("${filePath}")
	private String filePathConfig;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<CompanySetting> getByName(@PathVariable(value = "id") String id) throws AppException {
		MultiValueMap<String, String> headers = new HttpHeaders();
		CompanySetting setting = this.companySettingService.getById(id);
		System.out.println("company data is " + new Gson().toJson(setting));
		if (setting != null && setting.getLastUpdatedBy() != null) {
			setting.setLastUpdatedByUser(userService.getByIdWithPhoto(setting.getLastUpdatedBy()));
		}else{
			setting = new CompanySetting();
		}
		return new ResponseEntity<CompanySetting>(setting, headers, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/{featureId}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<CompanySetting> updateData(@PathVariable(value = "id") String id,@PathVariable(value = "featureId") String featureId) throws AppException {
		MultiValueMap<String, String> headers = new HttpHeaders();
		CompanySetting setting = this.companySettingService.getById(id);
		if (setting != null && setting.getLastUpdatedBy() != null) {
			setting.setLastUpdatedByUser(userService.getByIdWithPhoto(setting.getLastUpdatedBy()));
		}else{
			setting = new CompanySetting();
		}
		if(setting !=null){
			if(setting.getFeatureList().contains(featureId)){
				setting.getFeatureList().remove(featureId);
			}else{
				setting.getFeatureList().add(featureId);
			}
		}
		this.companySettingService.saveOrUpdate(setting);
		return new ResponseEntity<CompanySetting>(setting, headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompanySetting> uploadPhotoInFeed(@RequestBody CompanySetting setting)  throws AppException {
		setting.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		setting = companySettingService.saveOrUpdate(setting);
		if (setting != null && setting.getLastUpdatedBy() != null) {
			setting.setLastUpdatedByUser(userService.getByIdWithPhoto(setting.getLastUpdatedBy()));
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<CompanySetting>(setting, headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompanySetting> updated(@RequestBody CompanySetting setting)  throws AppException {
		setting.setUpdatedOn(DateTimeCal.getCurrentDateTimeSeconds());
		setting = companySettingService.saveOrUpdate(setting);
		if (setting != null && setting.getLastUpdatedBy() != null) {
			setting.setLastUpdatedByUser(userService.getByIdWithPhoto(setting.getLastUpdatedBy()));
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<CompanySetting>(setting, headers, HttpStatus.CREATED);
	}
}
