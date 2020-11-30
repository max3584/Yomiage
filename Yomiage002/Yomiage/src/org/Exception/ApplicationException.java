package org.Exception;

public abstract class ApplicationException extends Exception {

	/**
	 * @param serialVersionUID 固有コード
	 */
	private static final long serialVersionUID = 6958292579087034190L;
	
	public ApplicationException(String msg) {
		super(msg);
	}


	@Override
	public String getMessage () {
		return String.format("%s\nOriginalClass: %s message: %s",ApplicationException.serialVersionUID, ApplicationException.class.getName(), super.getMessage());
	}
}