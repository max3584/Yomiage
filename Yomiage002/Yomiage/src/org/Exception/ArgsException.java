package org.Exception;

/**
 * 引数が足りていない場合に吐き出されるエラークラス
 * @author max
 *
 */
public class ArgsException extends ApplicationException {

	/**
	 * カスタマイズ用(例：引数の数が足りないなど)
	 * @param message
	 */
	public ArgsException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return String.format("%s\nThrow: %s",super.getMessage(), ArgsException.class.getName());
	}
}
