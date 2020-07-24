package org.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataBaseAccess {

	// SQL実行
	/**
	 * 
	 * @param sql SQL文を受け取る変数
	 * @return ResultSetクラスのメタデータを返します
	 * @throws SQLException         SQLが受け付けなかった場合に発生する例外
	 * @throws NullPointerException アクセスができない場合に発生します
	 */
	// select文
	public ResultSet SearchSQLExecute(String sql) throws SQLException;

	/**
	 * 
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
