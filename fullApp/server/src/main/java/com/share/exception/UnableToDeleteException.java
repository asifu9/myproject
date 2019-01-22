package com.share.exception;

public class UnableToDeleteException extends AppException {

	public UnableToDeleteException(String code,String message,String errorId,Throwable ex) {
		// TODO Auto-generated constructor stub
		super(code,message,errorId,ex);
	}
	public UnableToDeleteException(String code,String message) {
		// TODO Auto-generated constructor stub
		super(code,message);
	}
	public UnableToDeleteException() {
		// TODO Auto-generated constructor stub
	}
}
