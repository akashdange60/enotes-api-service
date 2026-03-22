package com.enote.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.enote.handler.GenericResponse;

public class CommonUtil {
	
	public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status)
	{
		GenericResponse response= GenericResponse.builder()
				.responseStatus(status)
				.status("succes")
				.message("succes")
				.data(data)
				.build();
		return response.create();
	}
	
	public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status)
	{
		GenericResponse response= GenericResponse.builder()
				.responseStatus(status)
				.status("succes")
				.message(message)
				.build();
		return response.create();
	}
	
	public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status)
	{
		GenericResponse response= GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message("failed")
				.data(data)
				.build();
		return response.create();
	}
	
	public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status)
	{
		GenericResponse response= GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message(message)
				.build();
		return response.create();
	}

	public static String getContentType(String originalFileName) {
		String extension = FilenameUtils.getExtension(originalFileName);
		
		switch (extension) {
		case "pdf": {	
			return "application/pdf";
		}
		case "xlsx": {
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		}
		case "txt": {
			return "text/plain";
		}
		case "png": {
			return "image/png";
		}
		case "jpeg": {
			return "image/jpeg";
		}
		case "jpg": {
			return "image/jpg";
		}

		default:
			return "application/octet-stream";
			// throw new IllegalArgumentException("Unexpected value: " + extension);
		}
	}

	
}
