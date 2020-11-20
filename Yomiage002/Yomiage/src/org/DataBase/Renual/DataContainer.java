package org.DataBase.Renual;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.DataBase.DBAccess;

public class DataContainer {

	private DBAccess db;
	
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
	
	public ArrayList<String> update(String sql) throws SQLException {
		var data = new ArrayList<String>();
		ResultSet result = this.db.SearchSQLExecute(sql);
		while (result.next()) {
			data.add(result.getString("comment"));
		}
		return data;
	}
	
	public void Close() {
		this.db.close();
	}
}
