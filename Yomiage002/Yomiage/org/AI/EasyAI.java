package org.AI;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.DataBase.DBAccess;
import org.Datas.ExceptionReferenceData;
import org.Datas.ReferenceData;

public class EasyAI {
	
	// database
	private DBAccess db;
	private ResultSet result;
	
	// reference data inits
	private ArrayList<ReferenceData> referenceData;
	private ArrayList<ExceptionReferenceData> ERData;
	
	// sql format (default) 
	private String sqlFormat;
	
	public EasyAI(String DatabaseName) throws SQLException, IOException, InterruptedException {
		this.db = new DBAccess(DatabaseName);
		this.referenceData = new ArrayList<ReferenceData>();
		this.ERData = new ArrayList<ExceptionReferenceData>();
		this.sqlFormat = "select * from referenceData";
		this.result = this.db.SearchSQLExecute(this.sqlFormat);
		while(this.result.next()) {
			this.referenceData.add(new ReferenceData(this.result.getString("username"), this.result.getString("comments"), this.result.getFloat("percent")));
		}
		this.sqlFormat = "select * from exceptionreferenceData";
		while(this.result.next()) {
			this.ERData.add(new ExceptionReferenceData(this.result.getString("username"), this.result.getString("comments"), this.result.getFloat("percent"), this.result.getBoolean("flg")));
		}
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

	public String getSqlFormat() {
		return sqlFormat;
	}

	public void setSqlFormat(String sqlFormat) {
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