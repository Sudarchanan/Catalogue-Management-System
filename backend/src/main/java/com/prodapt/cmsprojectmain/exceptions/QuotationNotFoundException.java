package com.prodapt.cmsprojectmain.exceptions;

public class QuotationNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public QuotationNotFoundException() {
		super();

	}

	public QuotationNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public QuotationNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public QuotationNotFoundException(String message) {
		super(message);

	}

	public QuotationNotFoundException(Throwable cause) {
		super(cause);
	}

}
