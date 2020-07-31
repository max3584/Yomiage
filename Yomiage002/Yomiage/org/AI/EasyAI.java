package org.AI;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.DataBase.DBAccess;
import org.Datas.DataLists;
import org.Datas.ExceptionReferenceData;
import org.Datas.ReferenceData;
import org.Request.DatabaseInsert;

public class EasyAI {
	
	// database
	private DBAccess db;
	private ResultSet result;
	private String DatabaseName;
	
	// reference data inits
	private ArrayList<ReferenceData> referenceData;
	private ArrayList<ExceptionReferenceData> ERData;
	
	// sql format (default) 
	private String[] sqlFormat;
	
	public EasyAI(String DatabaseName) throws SQLException, IOException, InterruptedException {
		this.DatabaseName = DatabaseName;
		this.db = new DBAccess(this.DatabaseName);
		this.referenceData = new ArrayList<ReferenceData>();
		this.ERData = new ArrayList<ExceptionReferenceData>();
		this.setSqlFormat( (String[])Arrays.asList("select * from referenceData" , "select * from exceptionreferenceData").toArray() );
		this.result = this.db.SearchSQLExecute(this.sqlFormat[0]);
		while(this.result.next()) {
			this.referenceData.add(new ReferenceData(this.result.getString("username"), this.result.getString("comments"), this.result.getFloat("percent")));
		}
		this.result = this.db.SearchSQLExecute(this.sqlFormat[1]);
		while(this.result.next()) {
			this.ERData.add(new ExceptionReferenceData(this.result.getString("username"), this.result.getString("comments"), this.result.getFloat("percent"), this.result.getBoolean("flg")));
		}
		this.db.close();
	}
	
	public void DatabaseUpdate(ArrayList<DataLists> list) throws IOException, InterruptedException {
		this.db = new DBAccess(this.DatabaseName);
		DatabaseInsert di = new DatabaseInsert();
		for (int i = 0; i < list.size(); i++) {
			String[] values = {list.get(i).getDate(), list.get(i).getNo(), list.get(i).getGroup(), list.get(i).getSirial(), list.get(i).getUser(), list.get(i).getComment()};
			String sql = di.CreateInsertSQLFormat(values);

			this.db.UpdateSQLExecute(sql);
		}
	}
	
	public void DatabaseClose() {
		this.db.close();
	}
	
	// getter and setter
	public DBAccess getDb() {
		return db;
	}

	public void setDb(DBAccess db) {
		this.db = db;
	}

	public ResultSet getResult() {
		return result;
	}

	public void setResult(ResultSet result) {
		this.result = result;
	}

	public String[] getSqlFormat() {
		return sqlFormat;
	}

	public void setSqlFormat(String[] sqlFormat) {
		this.sqlFormat = sqlFormat;
	}

	public ArrayList<ReferenceData> getReferenceData() {
		return referenceData;
	}

	public void setReferenceData(ArrayList<ReferenceData> referenceData) {
		this.referenceData = referenceData;
	}

	public ArrayList<ExceptionReferenceData> getERData() {
		return ERData;
	}

	public void setERData(ArrayList<ExceptionReferenceData> eRData) {
		ERData = eRData;
	}
	
	
}