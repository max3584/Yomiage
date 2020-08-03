package org.Datas;

/**
 * 
 *  非参照データ用
 * 
 * @author max
 *
 *@param comment		チャットの内容
 *@param priority			優先度
 *@param flg				有効かどうか
 */

public class ExceptionReferenceData {
	
	private String comment;
	private int priority;
	private int flg;
	
	public ExceptionReferenceData(String comment, int priority, int flg) {
		this.comment = comment;
		this.priority = priority;
		this.flg = flg;
	}

	// getter and setter
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int isFlg() {
		return flg;
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
