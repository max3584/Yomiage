package org.Exception;

public class YomiageClassException extends ApplicationException {

	public YomiageClassException(String msg) {
		super(msg);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String getMessage() {
		return String.format("%s\nThrow: %s",super.getMessage(), YomiageClassException.class.getName());
	}
}
