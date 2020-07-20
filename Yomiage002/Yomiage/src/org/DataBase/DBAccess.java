package org.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.JDBC;

public class DBAccess implements DataBaseAccess {

	// inits
	private Statement st;
	private Connection con;
	private Class<?> database;

	// debug init

	private final boolean debugflg = true;

	private final Class<?> sqlite = JDBC.class;

	/**
	 * 
	 * DBへアクセスするためのコンストラクタ
	 * 
	 * @param className org.[DataBaseName].JDBC の書式で書くと処理できます
	 * @param url       jdbc:[DataBaseName].URL の書式で書くと処理できます
	 */

	public DBAccess(String className, String url) {

		String comment = "disttributions....";

		try {
			Class.forName("org.sqlite.JDBC");

			// クラスの存在確認
			this.setDatabase(this.sqlite);
			// 指定接続フォーマットでの接続
			this.con = DriverManager.getConnection(url);
			// 接続完了後、DBを実行可能状態に移行
			this.st = this.con.createStatement();

			// debug log
			comment = "connect";
		} catch (SQLException e) {
			comment = "not connection";
		} catch (ClassNotFoundException e) {
			comment = "Not Classes!!!!!!";
			e.printStackTrace();
		} finally {
			// log
			if (this.debugflg) {
				System.out.println(comment);
			}
		}
	}

	@Override
	public ResultSet SearchSQLExecute(String sql) {
		try {
			return this.st.executeQuery(sql);
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public int UpdateSQLExecute(String sql) {
		try {
			return this.st.executeUpdate(sql);
		} catch (SQLException e) {
			//e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void close() {
		try {
			if (this.con != null) {
				this.con.close();
			}
			if (this.st != null) {
				this.st.close();
			}
		} catch (SQLException e) {

		}
	}

	public Class<?> getDatabase() {
		return database;
	}

	public void setDatabase(Class<?> database) {
		this.database = database;
	}

	public Class<?> getSqlite() {
		return sqlite;
	}
}
