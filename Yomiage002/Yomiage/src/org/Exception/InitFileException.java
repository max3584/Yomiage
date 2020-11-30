package org.Exception;

public class InitFileException extends ApplicationException {
	
	public InitFileException(String message) {
		super(message);
	}

	
	@Override
	public String getMessage() {
		return String.format("%s\nThrow: %s",super.getMessage(), InitFileException.class.getName());
	}
}
