package com.smartCommerce.smart_commerce.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
		ErrorResponse error = ErrorResponse.builder().timeStamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value()).error("Not Found").message(ex.getMessage()).build();

		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            fieldErrors.put(fieldName, message);
        });
        ErrorResponse error = ErrorResponse.builder()
        		.timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())   // 400
                .error("Validation Failed")
                .message("One or more fields are invalid")
                .fieldErrors(fieldErrors)
                .build();
        return ResponseEntity.badRequest().body(error);
    
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())   // 500
                .error("Internal Server Error")
                .message("Something went wrong. Please try again later.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
	    ErrorResponse error = ErrorResponse.builder()
	            .timeStamp(LocalDateTime.now())
	            .status(HttpStatus.NOT_FOUND.value())
	            .error("Not Found")
	            .message(ex.getMessage())
	            .build();
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateEmail(DuplicateEmailException ex) {
	    ErrorResponse error = ErrorResponse.builder()
	            .timeStamp(LocalDateTime.now())
	            .status(HttpStatus.CONFLICT.value())   // 409 Conflict
	            .error("Conflict")
	            .message(ex.getMessage())
	            .build();
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
}
