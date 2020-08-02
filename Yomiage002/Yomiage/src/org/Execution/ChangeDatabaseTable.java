package org.Execution;

import java.io.IOException;

import org.DataBase.DBAccess;

public class ChangeDatabaseTable {

	public static void main(String[] args) {
		try {
			DBAccess dba = new DBAccess("JDBC:sqlite:" + args[0]);
			DefaultSQL sql = new DefaultSQL();
			dba.UpdateSQLExecute(sql.getPrevDelete());
			dba.UpdateSQLExecute(sql.getUpdateTables());
			dba.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
