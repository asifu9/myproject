package com.share.exception;

import java.util.Map;

public class ErrorDetails {
	private long timestamp;
	  private String message;
	  private String code;
	  private String errorId;
	  private Throwable ex;
	  private Map<String,Object> fields;
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public Throwable getEx() {
		return ex;
	}
	public void setEx(Throwable ex) {
		this.ex = ex;
	}
	public Map<String, Object> getFields() {
		return fields;
	}
	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}
	  
	  
}
