package org.Readers.Directory;

/**
 * ファイルを管理するためのクラス
 * @author max
 *
 */
public class Files {

	/**
	 * ファイル名を格納するための変数
	 */
	private String FileName;
	/**
	 * デバッグ用メッセージ
	 */
	public final static String msg = "File\t\t";
	
	/**
	 * ファイル名を格納するためのコンストラクタ
	 * @param fileName ファイル名
	 */
	public Files(String fileName) {
		this.FileName = fileName;
	}

	// getter and setter
	/**
	 * ファイル名取得
	 * @return ファイル名
	 */
	public String getFileName() {
		return FileName;
	}
	
	/**
	 * ファイル名設定
	 * @param fileName ファイル名
	 */
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	
	/**
	 * デバッグ用メッセージ
	 * @return　疑似ラベル(`File\t\t`)
	 */
	public String getMsg() {
		return this.msg;
	}
}
