package org.DataBase.Renual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.DataBase.DBAccess;
import org.Datas.DataLists;
import org.Datas.TempDataSave;

public class LRU_System {
	
	private ArrayList<TempDataSave> tds;
	private int timer;
	
	public LRU_System(int timer) {
		this.setTds(new ArrayList<TempDataSave>());
		this.setTimer(timer);
	}
	
	public void addData(DataLists data) {
		this.tds.forEach(datalist -> {
			if (datalist.getData().getComment().equals(data.getComment())) {
				try {
					DBAccess db = new DBAccess("JDBC:sqlite:.\\ExtendFiles\\Comment.db");
					int db_flg = db.UpdateSQLExecute(String.format("insert into reference values ('%s');", data.getComment()));
					
				} catch (IOException | InterruptedException e) {
					
				}
			}
		});
		this.tds.add(new TempDataSave(data));
		
	}
	
	public void rmData(int index) {
		this.tds.remove(index);
	}
	
	public void Check() {
		long nowTime = new Date().getTime();
		for (int i = 0; i < this.tds.size(); i++) {
			if (nowTime - this.tds.get(i).getTimestamp().getDateClass().getTime() > this.timer) {
				this.rmData(i);
			}
		}
	}

	public ArrayList<TempDataSave> getTds() {
		return tds;
	}

	protected void setTds(ArrayList<TempDataSave> tds) {
		this.tds = tds;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}
	
}
