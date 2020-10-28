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
	
	public CalcDate(int year, int month, int day, int hour, int min, int sec) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.sec = sec;
	}

	// CalcDays(Month)
	public int CalcDay(int Month) {

		int init = 28;

		// 月から、日数を出す
		/**
		 *  上段：31日以外の月
		 *  下段：30日以外の月
		 *  
		 *  注釈：2月は上段下段ともにあるため０が返ります
		 */
		init += (Math.abs(Math.signum((Month - 2) * (Month - 4) * (Month - 6) * (Month - 9) * (Month - 11))) * 3
				+ Math.abs(Math.signum((Month - 2) * (Month - 1) * (Month - 3) * (Month - 5) * (Month - 7)
						* (Month - 8) * (Month - 10) * (Month - 12))) * 2);
		//うるう年用の計算
		init +=  (Month % 100) != 0 & (Month % 400) == 0 ? (Month == 2) ? 1 : 0 : 0;

		return init;
		/*
		return 28 + (Math.abs(Math.signum((Month - 2) * (Month - 4) * (Month - 6) * (Month - 9) * (Month - 11)))
				* 3
				+ Math.abs(Math.signum((Month - 2) * (Month - 1) * (Month - 3) * (Month - 5) * (Month - 7)
						* (Month - 8) * (Month - 10) * (Month - 12))) * 2)
				+ (Month % 100) != 0 & (Month % 400) == 0 ? (Month == 2) ? 1 : 0 : 0;
				*/
	}

	// day Calcs
	public void prevDay(int num) {
		this.day -= num;
		if (this.day <= 0) {
			this.prevMonth(1);
			this.day = this.CalcDay(this.getMonth()) - this.day;
		}
	}

	public void nextDay(int num) {
		this.day += num;
		//int debug = this.CalcDay(this.month);
		if (this.day > this.CalcDay(this.month)) {
			this.nextMonth(1);
			this.day -= this.CalcDay(this.month);
		}
	}

	// month calcs
	public void prevMonth(int num) {
		this.month -= num;
		if (this.month <= 0) {
			int mon = this.month == 0? 1 : Math.abs(this.month);
			this.prevYear(mon / 12 > 0? (mon / 12) : 1);
			this.month += 12 ;
		}
	}

	public void nextMonth(int num) {
		this.month += num;
		if (this.month > 12) {
			this.nextYear((int) (this.month / 12));
			this.month %= 12;
		}
	}

	// year calcs
	public void prevYear(int num) {
		this.year -= num;
	}

	public void nextYear(int num) {
		this.year += num;
	}

	// print Method
	public String getData() {
		return this.sdf.format(this.date);
	}

	public String getData(String format) {
		return new SimpleDateFormat(format).format(this.date);
	}

	// print method2
	public String getCalcData() {
		Calendar newDate = Calendar.getInstance();
		newDate.set(this.year, this.month - 1, this.day);
		return this.sdf.format(newDate.getTime());
	}

	public String getCalcData(String format) {
		Calendar newDate = Calendar.getInstance();
		newDate.set(this.year, this.month, this.day, this.hour, this.min, this.sec);

		return new SimpleDateFormat(format).format(newDate.getTime());
	}

	public void update() {
		this.date = new Date();
	}

	// gettear and setter
	public Date getDateClass() {
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
