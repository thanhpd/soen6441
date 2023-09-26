package com.w10.risk_game.exceptions;

/**
 * The ApplicationException is a custom exception class that wraps
 * all checked standard Java exceptions and can be used to handle
 * risk-game-specific errors in Java.
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
