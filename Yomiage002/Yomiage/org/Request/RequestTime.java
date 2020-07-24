package org.Request;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RequestTime {

	private long sttime;
	private long edtime;
	private boolean isFinish;
	
	public RequestTime() {
		this.sttime = System.currentTimeMillis();
		this.isFinish = false;
	}

	public String request(long edtime) {
		this.edtime = edtime;
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Calendar result = Calendar.getInstance();

		start.setTimeInMillis(this.sttime);
		end.setTimeInMillis(this.edtime);

		long sa = end.getTimeInMillis() - start.getTimeInMillis() - result.getTimeZone().getRawOffset();

		result.setTimeInMillis(sa);

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
		return sdf.format(result.getTime());
	}

	public long getSttime() {
		return sttime;
	}

	public void setSttime(long sttime) {
		this.sttime = sttime;
	}

	public long getEdtime() {
		return edtime;
	}

	public void setEdtime(long edtime) {
		this.edtime = edtime;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

}
