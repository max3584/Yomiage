package org.Datas;

public class ExceptionReferenceData {

	private String username;
	private String comment;
	private float percent;
	private boolean flg;
	
	public ExceptionReferenceData(String user, String comment, float percent, boolean flg) {
		this.username = user;
		this.comment = comment;
		this.percent = percent;
		this.flg = flg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public boolean isFlg() {
		return flg;
	}

	public void setFlg(boolean flg) {
		this.flg = flg;
	}
	
}
