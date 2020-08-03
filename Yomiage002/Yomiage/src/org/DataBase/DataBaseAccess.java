package org.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * データベースを扱うための共通インターフェース
 * @author max
 *
 */

public interface DataBaseAccess {

	// SQL実行
	/**
	 * DML文のSelectが主に使用されます
	 * @param sql SQL文を受け取る変数
	 * @return ResultSetクラスのメタデータを返します
	 * @throws SQLException         SQLが受け付けなかった場合に発生する例外
	 * @throws NullPointerException アクセスができない場合に発生します
	 */
	// select文
	public ResultSet SearchSQLExecute(String sql) throws SQLException;

	/**
	 * DDL,DCL,トランザクション処理などのデータベース管理やチューニングなどに使用するためのＳＱＬで使用します
	 * @param sql SQL文を受け取る変数
	 * @return ResultSetクラスのメタデータを返します
	 * @throws SQLException         SQLが受け付けなかった場合に発生する例外
	 * @throws NullPointerException アクセスができない場合に発生します
	 */
	// insert, update, delete文
	public int UpdateSQLExecute(String sql) throws SQLException;

	/**
	 * <p>
	 * データベースを閉じるためのメソッド
	 * </p>
	 */
	public void close();

}
