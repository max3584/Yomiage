package old.Datas;

public class DataLists {

	private String date;
	private String no;
	private String group;
	private String sirial;
	private String user;
	private String comment;
	
	public DataLists(String date, String no, String group, String sirial, String user, String comment) {
		this.date = date;
		this.no = no;
		this.group = group;
		this.sirial = sirial;
		this.user = user;
		this.comment = comment;
	}
	
	public DataLists(String... datas) {
		this((String)datas[0], (String)datas[1], (String)datas[2], (String)datas[3], (String)datas[4], (String)datas[5]);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSirial() {
		return sirial;
	}

	public void setSirial(String sirial) {
		this.sirial = sirial;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
