package com.vikky.rest.webservices.webrestservices.test1;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
	
		ExceptionResponse resp = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(true));
		
		return new ResponseEntity(resp, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotException(Exception ex, WebRequest request){
	
		ExceptionResponse resp = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(true));
		
		return new ResponseEntity(resp, HttpStatus.NOT_FOUND);
	}
	
}
