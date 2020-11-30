package org.Exception;

public class SQLExecutionException extends ApplicationException {

	public SQLExecutionException(String msg) {
		super(msg);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	@Override
	public String getMessage() {
		return String.format("%s\nThrow: %s",super.getMessage(), SQLExecutionException.class.getName());
	}

}
