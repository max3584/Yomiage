package old.Datas;

/**
 * 参照データ用
 * @author max
 *
 *@param comment		チャットの内容
 *@param totals			チャットの内容の発言回数
 *@param percent			頻出率
 */

public class ReferenceData {

	private String comment;
	private String totals;
	private float percent;
	
	public ReferenceData(String comment, String totals, float percent) {
		this.comment = comment;
		this.totals = totals;
		this.percent = percent;
	}

	// getter and setter
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

	public String getTotals() {
		return totals;
	}

	public void setTotals(String totals) {
		this.totals = totals;
	}
	
}
