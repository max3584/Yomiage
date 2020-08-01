package org.Request;

public class DatabaseInsert {

	/**
	 *  insert into 'table_name' value (datas...); 
	 */
	
	public DatabaseInsert() {
		
	}
	
	// insert 
	public String  CreateInsertSQLFormat(String[] values) {
		String text = "";
		for(int i = 0; i < values.length; i++) {
			text += String.format("%s%s", values[i], (i < values.length - 1) ? "," : "" );
		}
		return this.CreateInsertSQLFormat(text);
	}
	
	public String CreateInsertSQLFormat(String value) {
		return "insert into Natu_data values (" + value + ");";
	}
}
