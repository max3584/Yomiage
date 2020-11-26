package org.Datas;

import java.util.Date;

import org.Date.CalcDate;

/**
 * コメント内容を一時的に保持しておくためのクラス
 * @author max
 *
 */
public class TempDataSave {
	/**
	 * 時刻スタンプ
	 */
	private CalcDate timestamp;
	/**
	 * コメント詳細データ
	 */
	private DataLists data;
	
	/**
	 * コンストラクタ
	 * コメントデータを入れるための初期設定
	 * @param data コメント詳細データ
	 */
	public TempDataSave(DataLists data) {
		this.setTimestamp(new CalcDate(new Date()));
		this.setData(data);
	}

	/**
	 * コメント詳細データ取得
	 * @return コメント詳細データ
	 */
	public DataLists getData() {
		return data;
	}

	/**
	 * コメント詳細データ設定
	 * @param data コメント詳細データ
	 */
	public void setData(DataLists data) {
		this.data = data;
	}

	/**
	 * タイムスタンプ取得
	 * @return タイムスタンプ
	 */
	public CalcDate getTimestamp() {
		return timestamp;
	}

	/**
	 * タイムスタンプ設定
	 * @param timestamp タイムスタンプ
	 */
	public void setTimestamp(CalcDate timestamp) {
		this.timestamp = timestamp;
	}
	
}
