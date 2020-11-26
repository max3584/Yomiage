package org.Datas;

/**
 * コメントの詳細データを保持するためのクラス
 * @author max
 *
 */
public class DataLists {

	/**
	 * 日付
	 */
	private String date;
	/**
	 * コメント番号
	 */
	private String no;
	/**
	 * コメントの種類
	 */
	private String group;
	/**
	 * ユーザのシリアルID
	 */
	private String sirial;
	/**
	 * ユーザ名
	 */
	private String user;
	/**
	 * コメント内容
	 */
	private String comment;
	
	/**
	 * コンストラクタ
	 * 初期データ入力用
	 * @param date　タイムスタンプ
	 * @param no　発言番号
	 * @param group　種類
	 * @param sirial　ユーザのシリアルID
	 * @param user　ユーザ名
	 * @param comment　コメント内容
	 */
	public DataLists(String date, String no, String group, String sirial, String user, String comment) {
		this.date = date;
		this.no = no;
		this.group = group;
		this.sirial = sirial;
		this.user = user;
		this.comment = comment;
	}
	
	/**
	 * コンストラクタ2
	 * コンストラクタの順番で配列のまま取り込めるようにしたもの
	 * @param datas　データ配列
	 */
	public DataLists(String... datas) {
		this((String)datas[0], (String)datas[1], (String)datas[2], (String)datas[3], (String)datas[4], (String)datas[5]);
	}

	/**
	 * タイムスタンプデータ取得
	 * @return タイムスタンプ
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * タイムスタンプデータ設定
	 * @param date タイムスタンプ
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 発言番号取得
	 * @return 発言番号
	 */
	public String getNo() {
		return no;
	}

	/**
	 * 発言番号設定
	 * @param no 発言番号
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * 種類取得
	 * @return 種類
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * 種類設定
	 * @param group 種類
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * ユーザのシリアルID取得
	 * @return ユーザのシリアルID
	 */
	public String getSirial() {
		return sirial;
	}

	/**
	 * ユーザのシリアルＩＤ設定
	 * @param sirial ユーザのシリアルID
	 */
	public void setSirial(String sirial) {
		this.sirial = sirial;
	}

	/**
	 * ユーザ名取得
	 * @return ユーザ名
	 */
	public String getUser() {
		return user;
	}

	/**
	 * ユーザ名設定
	 * @param user ユーザ名
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * コメント内容取得
	 * @return コメント内容
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * コメント内容設定
	 * @param comment コメント内容
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
