package org.DataBase.Renual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.DataBase.DBAccess;
import org.Datas.ChatData;
import org.Datas.TempDataSave;

/**
 * メモリ置換のLRUを参考に作成された傾向分析用クラス
 * @author max
 *
 */
public class LRU_System {

	/**
	 * コメント詳細データとタイムスタンプ
	 */
	private ArrayList<TempDataSave> tds;
	/**
	 * 保存期間用
	 */
	private int timer;
	/**
	 * スレッド処理管理番号用
	 */
	private int id;

	/**
	 * コンストラクタ
	 * 初期設定
	 * @param timer　時間
	 * @param id　識別番号
	 */
	public LRU_System(int timer, int id) {
		this.setTds(new ArrayList<TempDataSave>());
		this.setTimer(timer);
		this.id = id;
	}

	/**
	 * 分析用のリストにコメント詳細データ追加
	 * @param data コメント詳細データ
	 */
	public void addData(ChatData data) {
		this.tds.add(new TempDataSave(data));

	}

	/**
	 * 分析用のリストから指定番号の配列に対してリストから削除する処理
	 * @param index　添え字
	 */
	public void rmData(int index) {
		this.tds.remove(index);
	}

	/**
	 * 規定時間を超えていないかをチェックするための関数
	 */
	public void Check() {
		long nowTime = new Date().getTime();
		for (int i = this.tds.size() - 1; i > 0; i--) {
			if (this.tds.isEmpty() &&nowTime - this.tds.get(i).getTimestamp().getDateClass().getTime() > this.timer) {
				this.rmData(i);
			}
		}
	}
	
	/**
	 * 規定時間を超えていないかまた、同一の内容がないかチェックするための関数
	 * @param comment コメント
	 */
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

	/**
	 * 分析用リストデータ取得
	 * @return 分析用リスト
	 */
	public ArrayList<TempDataSave> getTds() {
		return tds;
	}

	/**
	 * 分析用リストデータ設定
	 * @param tds 分析用リストデータ
	 */
	protected void setTds(ArrayList<TempDataSave> tds) {
		this.tds = tds;
	}

	/**
	 * 規定時間取得
	 * @return 規定時間
	 */
	public int getTimer() {
		return timer;
	}

	/**
	 * 規定時間設定
	 * @param timer 規定時間
	 */
	public void setTimer(int timer) {
		this.timer = timer;
	}

	/**
	 * 識別番号取得
	 * @return 識別番号
	 */
	public int getId() {
		return id;
	}

}
