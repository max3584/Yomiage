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
	private int id;

	public LRU_System(int timer, int id) {
		this.setTds(new ArrayList<TempDataSave>());
		this.setTimer(timer);
		this.id = id;
	}

	public void addData(DataLists data) {
		this.tds.add(new TempDataSave(data));

	}

	public void rmData(int index) {
		this.tds.remove(index);
	}

	public void Check() {
		long nowTime = new Date().getTime();
		for (int i = this.tds.size() - 1; i > 0; i--) {
			if (this.tds.isEmpty() &&nowTime - this.tds.get(i).getTimestamp().getDateClass().getTime() > this.timer) {
				this.rmData(i);
			}
		}
	}
	
	public void Check(String comment) {
		this.Check();
		for (TempDataSave tmp : this.getTds()) {
			if (tmp.getData().getComment().equals(comment)) {
				try {
					DBAccess db = new DBAccess("JDBC:sqlite:.\\ExtendFiles\\chatData.db");
					int flg = db.UpdateSQLExecute(
							String.format("insert into reference (id, comment) values (%d,'%s');",
									this.getId(), comment));
					if (flg == -1) {
						db.UpdateSQLExecute(String.format(
								"update reference set id=%d where comment = %s and id <= %d",
								this.getId(), comment, this.getId()));
					}
				} catch (IOException | InterruptedException e) {

				}
			}
		};
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

	public int getId() {
		return id;
	}

}
