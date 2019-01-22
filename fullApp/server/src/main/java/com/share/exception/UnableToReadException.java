package com.share.exception;

public class UnableToReadException extends AppException {

	public UnableToReadException(String code,String message,String errorId,Throwable ex) {
		// TODO Auto-generated constructor stub
		super(code,message,errorId,ex);
	}
	public UnableToReadException(String code,String message) {
		// TODO Auto-generated constructor stub
		super(code,message);
	}
	public UnableToReadException() {
		// TODO Auto-generated constructor stub
	}
}
