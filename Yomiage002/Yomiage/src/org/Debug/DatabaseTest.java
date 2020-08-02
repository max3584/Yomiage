package org.Debug;

import java.io.IOException;

import org.DataBase.DBAccess;

public class DatabaseTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		DBAccess db = new DBAccess("JDBC:sqlite:");
		db.close();
	}
}
