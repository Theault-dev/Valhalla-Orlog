package com.game.orlog.exception;

public class NoMoreDieToRollException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoMoreDieToRollException() {
		super("No more dices to roll");
	}

	public NoMoreDieToRollException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoMoreDieToRollException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NoMoreDieToRollException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoMoreDieToRollException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
