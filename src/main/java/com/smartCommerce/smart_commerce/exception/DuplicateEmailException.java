package com.smartCommerce.smart_commerce.exception;

public class DuplicateEmailException extends RuntimeException{

	public DuplicateEmailException(String email) {
		super("User already exist with email: "+email);
	}
}
