package com.gtech.iarc.base.persistence.exception;

public class DuplicatedDomainDataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2979648573695905262L;
	
    public DuplicatedDomainDataException(String message) {
    	super(message);
    }
}
