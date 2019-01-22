package com.share.exception;

public class UnableToPersistException extends AppException {

	public UnableToPersistException(String code,String message,String errorId,Throwable ex,Object data) {
		// TODO Auto-generated constructor stub
		super(code,message,errorId,ex,data);
	}

	public UnableToPersistException(String code,String message) {
		// TODO Auto-generated constructor stub
		super(code,message);
	}
	public UnableToPersistException() {
		// TODO Auto-generated constructor stub
	}
}
