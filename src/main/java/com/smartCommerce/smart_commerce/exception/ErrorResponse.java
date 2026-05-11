package com.smartCommerce.smart_commerce.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private LocalDateTime timeStamp;
	private int status;
	private String error;
	private String message;
	private Map<String,String> fieldErrors;
}
