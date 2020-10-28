package org.Datas;

import java.util.Date;

import org.Date.CalcDate;

public class TempDataSave {
	private CalcDate timestamp;
	private DataLists data;
	
	public TempDataSave(DataLists data) {
		this.setTimestamp(new CalcDate(new Date()));
		this.setData(data);
	}

	public DataLists getData() {
		return data;
	}

	public void setData(DataLists data) {
		this.data = data;
	}

	public CalcDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(CalcDate timestamp) {
		this.timestamp = timestamp;
	}
	
}
