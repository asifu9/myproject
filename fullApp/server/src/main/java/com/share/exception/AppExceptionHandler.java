package com.share.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	  @ExceptionHandler(UnableToPersistException.class)
	  public final ResponseEntity<ErrorDetails> persistException(AppException ex, WebRequest request) {
	    return new ResponseEntity<ErrorDetails>( buildErrorObject(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  
	  @ExceptionHandler(NoRecordFoundException.class)
	  public final ResponseEntity<ErrorDetails> noRecordFoundException(AppException ex, WebRequest request) {
	   
	    return new ResponseEntity<ErrorDetails>( buildErrorObject(ex), HttpStatus.NOT_FOUND);
	  }
	  
	  @ExceptionHandler(UnableToDeleteException.class)
	  public final ResponseEntity<ErrorDetails> deleteException(AppException ex, WebRequest request) {
	   
	    return new ResponseEntity<ErrorDetails>( buildErrorObject(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  
	  @ExceptionHandler(UnableToUpdateException.class)
	  public final ResponseEntity<ErrorDetails> updateException(AppException ex, WebRequest request) {
	   
	    return new ResponseEntity<ErrorDetails>( buildErrorObject(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  
	  @ExceptionHandler(UnableToReadException.class)
	  public final ResponseEntity<ErrorDetails> unaableToRead(AppException ex, WebRequest request) {
	   
	    return new ResponseEntity<ErrorDetails>( buildErrorObject(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  
	  
	  
	  ErrorDetails buildErrorObject(AppException ex){
		  ErrorDetails errorDetails = new ErrorDetails();
		    errorDetails.setCode(ex.getCode());
		    errorDetails.setErrorId(ex.getErrorId());
		    errorDetails.setEx(ex.getException());
		    errorDetails.setFields(ex.getItems());
		    errorDetails.setMessage(ex.getMessage());
		    return errorDetails;
	  }
	  
	  
}