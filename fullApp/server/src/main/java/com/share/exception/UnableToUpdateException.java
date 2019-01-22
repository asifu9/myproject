package com.share.exception;

public class UnableToUpdateException extends AppException {

	public UnableToUpdateException(String code,String message,String errorId,Throwable ex) {
		// TODO Auto-generated constructor stub
		super(code,message,errorId,ex);
	}
	public UnableToUpdateException(String code,String message) {
		// TODO Auto-generated constructor stub
		super(code,message);
	}
	public UnableToUpdateException() {
		// TODO Auto-generated constructor stub
	}
}
