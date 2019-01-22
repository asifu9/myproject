package com.share.exception;

import java.util.HashMap;
import java.util.Map;

public class AppException extends Exception{
	private String code;
	private String message;
	private String errorId;
	private Throwable exception;
	private Object data;
	
	public AppException(String code, String message, String errorId, Throwable exception) {
		super();
		this.code = code;
		this.message = message;
		this.errorId = errorId;
		this.exception = exception;
	}
	
	public AppException(String code, String message, String errorId, Throwable exception, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.errorId = errorId;
		this.exception = exception;
		this.data=data;
	}
	public AppException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public AppException() {
		super();
	}
	Map<String,Object> items=new HashMap<>();

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getItems() {
		return items;
	}
	public void setItems(Map<String, Object> items) {
		this.items = items;
	}
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
}
