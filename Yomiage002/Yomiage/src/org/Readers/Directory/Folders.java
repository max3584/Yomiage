package org.Readers.Directory;

/**
 * フォルダーを格納するためのクラス
 * @author max
 *
 */
public class Folders {
	
	/**
	 * ディレクトリの名前
	 * 
	 */
	private String directoryName;
	/**
	 * デバッグ用メッセージ
	 */
	public final static String msg = "Directory\t";
	
	/**
	 * フォルダー名を取得するためのコンストラクタ
	 * @param dir フォルダ名
	 */
	public Folders(String dir) {
		this.directoryName = dir;
	}

	// getter and setter
	/**
	 * フォルダ名取得
	 * @return ファイル名
	 */
	public String getDirectoryName() {
		return this.directoryName;
	}

	/**
	 * フォルダ名設定
	 * @param directoryName フォルダ名
	 */
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	/**
	 * デバッグ用メッセージ
	 * @return 疑似ラベル
	 */
	public String getMsg() {
		return msg;
	}
	
}