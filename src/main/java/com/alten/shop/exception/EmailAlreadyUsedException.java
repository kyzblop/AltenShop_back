package com.alten.shop.exception;

public class EmailAlreadyUsedException extends Exception {


	private static final long serialVersionUID = 1L;
	
	public EmailAlreadyUsedException(String message) {
		super(message);
	}

}
