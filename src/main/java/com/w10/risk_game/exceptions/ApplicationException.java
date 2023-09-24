package com.w10.risk_game.exceptions;

/**
 * ApplicationException wraps all checked standard Java exceptions
 *
 * @author Darlene-Naz
 * @version 1.0
 */
public class ApplicationException extends Exception {

	public ApplicationException() {
		super();
	}

	public ApplicationException(Throwable error) {
		super(error);
	}

	public ApplicationException(String errorMessage) {
		super(errorMessage);
	}

	public ApplicationException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
