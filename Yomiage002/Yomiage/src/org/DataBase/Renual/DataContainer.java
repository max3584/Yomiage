package org.DataBase.Renual;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.DataBase.DBAccess;

/**
 * 分析されたデータから規定値を超えたものをデータベースに入れるためのクラス
 * @author max
 *
 */
public class DataContainer {

	/**
	 * データベースクラス
	 */
	private DBAccess db;
	
	/**
	 * 引数なしコンストラクタ
	 * データベースコネクション確立設定
	 */
	public DataContainer() {
		
		try {
			this.db = new DBAccess("JDBC:sqlite:.\\ExtendFiles\\chatData.db");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	/**
	 * データベース内のデータを再取得するための関数
	 * @param sql クエリ
	 * @return データベースのレコードのリスト
	 * @exception SQLException SQLが実行できない場合に発生
	 */
	public ArrayList<String> ReferenceDataRequest(String sql) throws SQLException {
		var data = new ArrayList<String>();
		ResultSet result = this.db.SearchSQLExecute(sql);
		while (result.next()) {
			data.add(result.getString("comment"));
		}
		return data;
	}
	
	/**
	 * データベースを閉じる場合に使用する関数
	 * ※必ず最後に実行するようにする
	 */
	public void Close() {
		this.db.close();
	}
}
