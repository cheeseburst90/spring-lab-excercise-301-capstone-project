package com.eatza.customer.customerregistration.exceptionhandler;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5673062961113146753L;

	public CustomerException(String msg) {
		super(msg);
	}

}
