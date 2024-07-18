package com.prodapt.cmsprojectmain.exceptions;

public class ParameterNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public ParameterNotFoundException() {
		super();
		
	}

	public ParameterNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ParameterNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ParameterNotFoundException(String message) {
		super(message);
		
	}

	public ParameterNotFoundException(Throwable cause) {
		super(cause);
		
	}
	
	

}

