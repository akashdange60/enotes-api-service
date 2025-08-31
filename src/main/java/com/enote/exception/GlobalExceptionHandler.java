package com.enote.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handalException(Exception e)
	{
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handalNullPointerException(Exception e)
	{
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handalResourceNotFoundException(Exception e)
	{
		log.error("Controller :: getCategoryDetailsById :: "+e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handalValidationException(ValidationException e)
	{
		log.error("Controller :: getCategoryDetailsById :: "+e.getMessage());
		return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
		
	}

}
