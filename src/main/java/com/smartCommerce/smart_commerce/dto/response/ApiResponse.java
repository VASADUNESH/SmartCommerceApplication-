package com.smartCommerce.smart_commerce.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private Boolean success;
	private String message;
	private T data;
	private Object error;
	private LocalDateTime timeStamp;
	private int status;
	
	public static <T> ApiResponse<T> success(T data){
		return ApiResponse.<T>builder()
				.success(true)
				.message("Success")
				.data(data)
				.timeStamp(LocalDateTime.now())
				.status(200)
				.build();
	}
	
	public static <T> ApiResponse<T> success(T data, String message){
		return ApiResponse.<T>builder()
				.success(true)
				.message(message)
				.data(data)
				.timeStamp(LocalDateTime.now())
				.status(200)
				.build();
	}
	
	public static <T> ApiResponse<T> created(T data){
		return ApiResponse.<T>builder()
				.success(true)
				.message("Created")
				.data(data)
				.timeStamp(LocalDateTime.now())
				.status(201)
				.build();
	}
	
	public static <T> ApiResponse<T> error(String message, int status){
		return ApiResponse.<T>builder()
				.success(false)
				.message(message)
				.timeStamp(LocalDateTime.now())
				.status(status)
				.build();
	}
	
	public static <T> ApiResponse<T> validationError(String message, Object error){
		return ApiResponse.<T>builder()
				.success(false)
				.message(message)
				.error(error)
				.timeStamp(LocalDateTime.now())
				.status(400)
				.build();
				
	}
}

