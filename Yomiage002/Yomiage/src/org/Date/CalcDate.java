package org.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalcDate {

	// int datas
	private Date date;
	private SimpleDateFormat sdf;

	// init propaty
	private int year;
	private int month;
	private int day;
	private int hour;
	private int min;
	private int sec;
	
	// constructor1
	public CalcDate(Date date) {
		this.date = date;
		this.sdf = new SimpleDateFormat("yyyy,MM,dd,kk,mm,ss");
		String[] datas = sdf.format(date).split(",");
		this.year = Integer.parseInt(datas[0]);
		this.month = Integer.parseInt(datas[1]);
		this.day = Integer.parseInt(datas[2]);
		this.hour = Integer.parseInt(datas[3]);
		this.min = Integer.parseInt(datas[4]);
		this.sec = Integer.parseInt(datas[5]);
		
	}
	// constructor2
	public CalcDate(Date date, SimpleDateFormat sdf) {
		this(date);
		this.sdf = sdf;
	}
	
	// CalcDays(Month)
	public int CalcDay(int Month) {
		return (28 
				+ Math.abs(Math.signum((this.month - 2) * (this.month - 4) * (this.month - 6) * (this.month - 9) * (this.month - 11))) * 3
				+ Math.abs(Math.signum((this.month - 2) * (this.month - 1) * (this.month - 3) * (this.month - 5) * (this.month - 7) * (this.month - 8) * (this.month - 10) * (this.month - 12))) * 2
				+ (this.year % 100) != 0 & (this.year % 400) == 0 ? (this.month == 2)? 1 : 0 : 0);
	}
	
	
	// day Calcs
	public int  prevDay(int num) {
		this.day -= num;
		return this.day >= 0 ? this.day : this.CalcDay(this.prevMonth(1) + 1) - this.day;
	}
	
	public int nextDay(int num) {
		this.day += num;
		return this.day <= this.CalcDay(this.month+1)? this.day : this.day % this.CalcDay(this.nextMonth(1) + 1);
	}
	
	// month calcs
	
	public int prevMonth(int num) {
		this.month -= num;
		if(this.month >= 0) {
			return this.month;
		} else {
			this.prevYear(Math.abs(this.month));
			return Math.abs(this.month) % 12;
		}
	}
	
	public int nextMonth(int num) {
		this.month += num;
		if (this.month < 12) {
			return this.month;
		} else {
			this.nextYear((int)(this.month / 12));
			 return this.month % 12;
		}
	}
	
	// year calcs
	public int prevYear(int num) {
		return this.year -= num;
	}
	
	public int nextYear(int num) {
		return this.year += num;
	}
	
	// print Method
	public String getData() {
		return this.sdf.format(this.date);
	}
	
	public String getData(String format) {
		return  new SimpleDateFormat(format).format(this.date);
	}
	
	// print method2
	public String getCalcData() {
		Calendar newDate = Calendar.getInstance();
		newDate.set(this.year, this.month, this.day, this.hour, this.min, this.sec);
		
		return this.sdf.format(newDate);
	}
	
	public String getCalcData(String format) {
		Calendar newDate = Calendar.getInstance();
		newDate.set(this.year, this.month, this.day, this.hour, this.min, this.sec);
		
		return new SimpleDateFormat(format).format(newDate);
	}

	//gettear and setter
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}
	
}
