package com.enote.exception;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.enote.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handalException(Exception e)
	{
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handalNullPointerException(Exception e)
	{
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handalResourceNotFoundException(Exception e)
	{
		log.error("Controller :: handalResourceNotFoundException :: "+e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handalValidationException(ValidationException e)
	{
		log.error("Controller :: handalValidationException :: "+e.getMessage());
//		return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorResponse(e.getErrors(),HttpStatus.BAD_REQUEST)	;
	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handalExistDataException(ExistDataException e)
	{
		log.error("Controller :: handalExistDataException :: "+e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handalHttpMessageNotReadableException(HttpMessageNotReadableException e)
	{
		log.error("Controller :: handalHttpMessageNotReadableException :: "+e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handalFileNotFoundException(FileNotFoundException e)
	{
		log.error("NotesServiceImpl :: handalFileNotFoundException :: "+e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		
	}

}
