package org.Execution.subject;

import org.CLI.CEExpress;

/**
 * 読み上げの抽象クラス
 * @author max
 *
 */

public abstract class Yomiage {

	
	/**
	 * 正規表現で使用します
	 */
	private String[] regexs = {
			"(^|\\s)/(f|m|c)*la\\s\\w*(\\ss\\d)?",	//la
			"/(sr|s\\w*)?\\s[(r|l|R|C|L)+(\\w|\\W)*]+\\s?", //sr
			"(^|\\s)/ci\\d+?((\\s(\\d+|nw|t\\d|s\\d+)){1,4})", //ci
			 "/\\w+\\s*(on|off)\\s*\\d*\\s*", //on off
			 "(^|\\s)/((ca?o?(s|mo?u?f(lage)?)\\w*)\\s([*＊・\\W]+)){1,2}\\s*", //cmf
			 "\\{[a-zA-Z+-]*\\}", // {color Change}
			 "/\\w+", // /[a-zA-Z]*
			 "\\s" // 空白
			};

	/**
	 * ＤＢにデータを入れる場合にエスケープするための処理
	 */
	private String sanitiza = "&27";
	
	/**
	 * OSのコマンドを実行するための変数
	 */
	private CEExpress cee;

	/**
	 * 正規表現で文字列を消す処理
	 * @param text 処理の対象データ
	 * @return　正規表現で対象データが消えた後のデータ
	 */
	public String regexs(String text) {
		for (int i = 0; i < this.regexs.length; i++) {
			text = text.replaceAll(this.regexs[i], "");
		}
		return text;
	}
	
	/**
	 * ＤＢに格納する前にサニタイズするための処理(簡易版)
	 * @param text 対象データ
	 * @return サニタイズ後のデータ
	 */
	public String sanitiza(String text) {
		return text.replaceAll("'", this.sanitiza);
	}
	
	/**
	 * 読上げソフトウェアに転送するための処理
	 * @param port ポート番号
	 * @param user ユーザ名
	 * @param comment コメント内容
	 */
	public abstract void Request(int port, String user, String comment); 

	/**
	 * ceeのフィールド変数取得
	 * @return cee
	 */
	public CEExpress getCee() {
		return cee;
	}

	/**
	 * ceeフィールド変数に値を入れる
	 * @param cee CEExpressクラスのOSコマンドが含まれたものを入れる
	 */
	public void setCee(CEExpress cee) {
		this.cee = cee;
	}
}
