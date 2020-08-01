package org.DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.CLI.CEExpress;
import org.sqlite.*;

public class DBAccess implements DataBaseAccess {

	// inits
	private Statement st;
	private Connection con;
	private Class<?> database;

	// debug init

	private final boolean debugflg = false;

	private Class<?> sqlite;

	/**
	 * 
	 * DBへアクセスするためのコンストラクタ
	 * 
	 * @param className org.[DataBaseName].JDBC の書式で書くと処理できます
	 * @param url       jdbc:[DataBaseName].URL の書式で書くと処理できます
	 */

	public DBAccess(String url) throws IOException, InterruptedException {

		String comment = "disttributions....";

		try {
			this.sqlite = Class.forName("org.sqlite.JDBC");

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
			System.out.println("クラスが存在しないようです。URLを出すのでこれをブラウザにコピーしてそのサイトからダウンロードしてください");
			System.out.println("その後、javaのランタイムが入っている C:\\Program Files\\Java\\jdk<JDK_version>\\jre\\lib\\ext に入れてください");
			new CEExpress("cmd", "/c", "start","https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.27.2.1.jar").ConsoleCommand();;
			throw new Error("このソフトウェアで扱うクラスがないため、一度終了処理を行います。");
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
