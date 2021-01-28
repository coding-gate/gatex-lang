package org.gatex.lang.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalControlarAdvice {
	Logger log = LoggerFactory.getLogger(GlobalControlarAdvice.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<String>> handleInputValidation(MethodArgumentNotValidException ex, WebRequest req) {

		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		log.error("handleInputValidation {} {}", ex.getMessage(), ex);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	ResponseEntity<String> handleCompilationandRun(Exception e, WebRequest req) {
		log.error("handleCompilationandRun {} {}", e.getMessage(), e);
		return new ResponseEntity<>("Something went wrong with the server", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
