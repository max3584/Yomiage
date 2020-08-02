package org.Datas;

public class ReferenceData {

	private String username;
	private String comment;
	private float percent;
	
	public ReferenceData(String username, String comment, float percent) {
		this.username = username;
		this.comment = comment;
		this.percent = percent;
	}

	// getter and setter
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
	
}
