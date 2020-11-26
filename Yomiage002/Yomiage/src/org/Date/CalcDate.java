package org.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 時間関連の自己定義クラス
 * @author max
 *
 */
public class CalcDate {

	// int datas
	/**
	 * クラス生成時の時間
	 */
	private Date date;
	/**
	 * フォーマット用
	 */
	private SimpleDateFormat sdf;

	// init propaty
	/**
	 * 計算するための年
	 */
	private int year;
	/**
	 * 計算するための月
	 */
	private int month;
	/**
	 * 計算するための日
	 */
	private int day;
	/**
	 * 計算するための時
	 */
	private int hour;
	/**
	 * 計算するための分
	 */
	private int min;
	/**
	 * 計算するための秒
	 */
	private int sec;

	/**
	 *  コンストラクタ(標準ライブラリの日付クラスを使用する場合)
	 * @param date 標準ライブラリの日付クラス
	 */
	public CalcDate(Date date) {
		this.sdf = new SimpleDateFormat("yyyy,MM,dd,kk,mm,ss");
		String[] datas = sdf.format(date).split(",");
		this.year = Integer.parseInt(datas[0]);
		this.month = Integer.parseInt(datas[1]);
		this.day = Integer.parseInt(datas[2]);
		this.hour = Integer.parseInt(datas[3]);
		this.min = Integer.parseInt(datas[4]);
		this.sec = Integer.parseInt(datas[5]);

	}

	/**
	 * 
	 *  コンストラクタ2(指定フォーマットが存在する場合)
	 * @param date 標準ライブラリの日付クラス
	 * @param sdf 標準ライブラリの日付表示フォーマット設定クラス
	 */
	public CalcDate(Date date, SimpleDateFormat sdf) {
		this(date);
		this.sdf = sdf;
	}
	
	/**
	 * コンストラクタ3　年から秒までを指定する場合
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param hour 時
	 * @param min 分
	 * @param sec 秒
	 */
	public CalcDate(int year, int month, int day, int hour, int min, int sec) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.sec = sec;
	}

	/**
	 *  月から、日付を取る関数
	 * @param Month 月
	 * @return 日付
	 */
	public int CalcDay(int Month) {

		int init = 28;

		// 月から、日数を出す
		/*
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
	/**
	 * 前の日付にする
	 * @param num 数値
	 */
	public void prevDay(int num) {
		this.day -= num;
		if (this.day <= 0) {
			this.prevMonth(1);
			this.day = this.CalcDay(this.getMonth()) - this.day;
		}
	}

	/**
	 * 先の日付にする
	 * @param num 数値
	 */
	public void nextDay(int num) {
		this.day += num;
		//int debug = this.CalcDay(this.month);
		if (this.day > this.CalcDay(this.month)) {
			this.nextMonth(1);
			this.day -= this.CalcDay(this.month);
		}
	}

	// month calcs
	/**
	 * 前の月にする
	 * @param num 数値
	 */
	public void prevMonth(int num) {
		this.month -= num;
		if (this.month <= 0) {
			int mon = this.month == 0? 1 : Math.abs(this.month);
			this.prevYear(mon / 12 > 0? (mon / 12) : 1);
			this.month += 12 ;
		}
	}

	/**
	 * 次の月にする
	 * @param num 数値
	 */
	public void nextMonth(int num) {
		this.month += num;
		if (this.month > 12) {
			this.nextYear((int) (this.month / 12));
			this.month %= 12;
		}
	}

	// year calcs
	/**
	 * 前の年にする
	 * @param num 数値
	 */
	public void prevYear(int num) {
		this.year -= num;
	}
	/**
	 * 次の年にする
	 * @param num 数値
	 */
	public void nextYear(int num) {
		this.year += num;
	}

	// print Method
	/**
	 * 今現状の時間を取得する
	 * @return フォーマットで指定された形で文字列で出力される
	 */
	public String getData() {
		return this.sdf.format(this.date);
	}

	/**
	 * 指定したフォーマットで現在時間を取得する
	 * @param format　フォーマット指定
	 * @return　表示フォーマットの現在取得時間
	 */
	public String getData(String format) {
		return new SimpleDateFormat(format).format(this.date);
	}

	// print method2
	/**
	 * 時間計算された値を返す関数
	 * @return 年月日
	 */
	public String getCalcData() {
		Calendar newDate = Calendar.getInstance();
		newDate.set(this.year, this.month - 1, this.day);
		return this.sdf.format(newDate.getTime());
	}

	/**
	 * 時間計算された値をフォーマット指定された文字で返す関数
	 * @param format フォーマット指定
	 * @return　フォーマット指定された値
	 */
	public String getCalcData(String format) {
		Calendar newDate = Calendar.getInstance();
		newDate.set(this.year, this.month, this.day, this.hour, this.min, this.sec);

		return new SimpleDateFormat(format).format(newDate.getTime());
	}

	/**
	 * 時刻同期用
	 */
	public void update() {
		this.date = new Date();
	}

	// gettear and setter
	/**
	 * 日付クラスを取得
	 * @return 日付クラス
	 */
	public Date getDateClass() {
		return date;
	}

	/**
	 * 日付クラス設定
	 * @param date　日付クラス
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * フォーマット指定されたものを取得
	 * @return　フォーマット指定
	 */
	public SimpleDateFormat getSdf() {
		return sdf;
	}

	/**
	 * フォーマット指定設定
	 * @param sdf フォーマット指定設定情報
	 */
	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	/**
	 * 計算された値の年
	 * @return 年
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 年の設定
	 * @param year　年
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * 計算された月を取得
	 * @return 月
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * 月の設定
	 * @param month 月
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * 計算された日付を取得
	 * @return 日付
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 日付設定
	 * @param day　日付
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * 計算された時取得
	 * @return 時
	 */
	public int getHour() {
		return hour;
	}
	/**
	 * 時の設定
	 * @param hour　時
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * 計算された分の取得
	 * @return　分
	 */
	public int getMin() {
		return min;
	}

	/**
	 * 分の設定
	 * @param min 分
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * 計算された秒数を取得
	 * @return 秒
	 */
	public int getSec() {
		return sec;
	}

	/**
	 * 秒を設定
	 * @param sec 秒
	 */
	public void setSec(int sec) {
		this.sec = sec;
	}

}
