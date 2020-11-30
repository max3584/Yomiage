package org.Exception;

public class FileReadException extends ApplicationException {

	public FileReadException(String msg) {
		super(msg);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	@Override
	public String getMessage() {
		return String.format("%s\nThrow: %s",super.getMessage(), FileReadException.class.getName());
	}

}
