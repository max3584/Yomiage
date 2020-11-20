package Test.Debug;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.DataBase.DBAccess;
import org.Datas.DataLists;
import org.Date.CalcDate;

import old.Execution.Initialized;

public class GatheringDebug {

	public static void main(String[] args) throws IOException, InterruptedException, SQLException {
		Initialized init = new Initialized(args[0]);
		
		File DatabaseFile = new File(".\\ExtendFiles\\controlData.db");
		DBAccess db = new DBAccess("JDBC:sqlite:" + DatabaseFile.toString());
		
		ResultSet result = db.SearchSQLExecute("select * from Natu_data");
		
		ArrayList<DataLists> datalist = new ArrayList<DataLists>();
		while(result.next()) {
			datalist.add(new DataLists(result.getString("tmstamp"), String.valueOf(result.getInt("number")), result.getString("groups"), result.getString("sirial"), result.getString("username"), result.getString("comments")));
		}
		
		System.out.println(datalist.get(0).getDate().substring(0, 15));
		
		int year = Integer.parseInt(datalist.get(0).getDate().substring(0, 3));
		int month = Integer.parseInt(datalist.get(0).getDate().substring(5, 6));
		int day = Integer.parseInt(datalist.get(0).getDate().substring(8, 9));
		int hour = Integer.parseInt(datalist.get(0).getDate().substring(11, 12));
		int min = Integer.parseInt(datalist.get(0).getDate().substring(14, 15));
		
		CalcDate date = new CalcDate(year, month, day, hour, min, 0);
		date.setSdf(new SimpleDateFormat("yyyy-MM-ddTkk:mm"));
		
	}
}
