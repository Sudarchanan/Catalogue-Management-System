package com.prodapt.cmsprojectmain.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class CredentialsExceptions extends BadCredentialsException{

	private static final long serialVersionUID = 1L;

	public CredentialsExceptions(String msg, Throwable cause) {
		super(msg, cause);
		
	}

	public CredentialsExceptions(String msg) {
		super(msg);
		
	}
	
	

}
