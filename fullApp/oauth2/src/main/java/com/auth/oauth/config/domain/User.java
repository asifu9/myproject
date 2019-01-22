package com.auth.oauth.config.domain;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * Created by jt on 1/10/17.
 */
@Document
public class User implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tenantId;
	@Id
    private String id;
    private String name;
    private long dob;
    private long createdOn;
    private long updatedOn;
    private String emailId;
    private String photoId;
    private String primaryContact;
    private String secondaryContact;
    private String photoPath;
    private String userName;
    
    
    
    
    

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getSecondaryContact() {
		return secondaryContact;
	}

	public void setSecondaryContact(String secondaryContact) {
		this.secondaryContact = secondaryContact;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}



	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public void setDob(long dob) {
		this.dob = dob;
	}

	public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public long getDob() {
		return dob;
	}

	public User() {
        id = UUID.randomUUID().toString();
    }

	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public long getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(long updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}



	
	
	
	
}
