package com.share.exception;

public class NoRecordFoundException extends AppException {

	public NoRecordFoundException(String code,String message,String errorId,Throwable ex) {
		// TODO Auto-generated constructor stub
		super(code,message,errorId,ex);
	}
	public NoRecordFoundException(String code,String message) {
		// TODO Auto-generated constructor stub
		super(code,message);
	}
	public NoRecordFoundException() {
		// TODO Auto-generated constructor stub
	}
}
