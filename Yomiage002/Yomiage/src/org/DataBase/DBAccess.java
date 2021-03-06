package org.DataBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.CLI.CEExpress;
/**
 * データベースを使用するためのクラス
 * 
 * <a href="https://www.sqlite.org/copyright.html">copyright https://www.sqlite.org/copyright.html</a>
 * 
 * (public domain)
 * @author max
 */
public class DBAccess implements DataBaseAccess {

	// inits
	/**
	 * SQL情報などを処理するときに使用
	 */
	private Statement st;
	/**
	 * データベースに接続時に使用
	 */
	private Connection con;
	/**
	 * クラス情報
	 */
	private Class<?> database;

	// debug init
	/**
	 * デバッグ表示を行うか行わないか
	 */
	private final boolean debugflg = false;

	/**
	 * SQLiteクラスを格納するためのフィールド(引用)
	 */
	private Class<?> sqlite;

	/**
	 * 
	 * DBへアクセスするためのコンストラクタ
	 * 
	 * @param url       jdbc:[DataBaseName].URL の書式で書くと処理できます
	 * @exception IOException 読み書きができない場合に発生
	 * @exception InterruptedException (このソフトウェアでは発生しない)
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
			
			System.out.println(comment);
			
			String dir = System.getProperty("java.home");
			File run_java = new File(String.format("%s\\lib\\ext\\", dir));
			File pkg = new File(".\\lib\\sqlite-jdbc-3.27.2.1.jar");
			Files.copy(pkg.toPath(), run_java.toPath());
			System.out.println("コピー完了");
			System.out.println("再度起動よろしくお願いします");
			new CEExpress("cmd" , "/c", "pause").ConsoleCommand();
			/*
			System.out.println("クラスが存在しないようです。URLを出すのでこれをブラウザにコピーしてそのサイトからダウンロードしてください");
			System.out.println("その後、javaのランタイムが入っている C:\\Program Files\\Java\\jre<JRE_version>\\jre\\lib\\ext に入れてください");
			new CEExpress("cmd", "/c", "start","https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.27.2.1.jar").ConsoleCommand();;
			System.out.println("終了次第、キーを押してください");
			new CEExpress("cmd", "/c", "pause", ">", "Nul").ConsoleCommand();
			System.out.println("デフォルトの場所を開きます((ダウンロードフォルダーが別の場合は自分でやってください))");
			new CEExpress("cmd", "/c", "explorer", "%userprofile%\\downloads\\").ConsoleCommand();;
			new CEExpress("cmd", "/c", "explorer", "C:\\Program Files\\Java\\").ConsoleCommand();;
			new CEExpress("cmd", "/c", "pause");
			throw new Error("このソフトウェアで扱うクラスがないため、一度終了処理を行います。");
			*/
		} finally {
			// log
			if (this.debugflg) {
				System.out.println("\n" + comment);
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
