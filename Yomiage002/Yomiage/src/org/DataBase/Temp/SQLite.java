package org.DataBase.Temp;

//import org.sqlite.JDBC;

/**
 * SQLiteのインタフェース
 * 主に、JDBC接続で使用する固有の設定を入れているインタフェース
 * @author max
 *
 */
public interface SQLite {

	/**
	 * SQLiteの固有設定フィールド
	 */
	public static final String url = "JDBC:sqlite:";
}
