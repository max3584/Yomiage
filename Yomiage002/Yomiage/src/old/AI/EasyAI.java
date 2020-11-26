package old.AI;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.DataBase.DBAccess;
import org.Datas.DataLists;
import old.Request.DatabaseInsert;

import old.Datas.ExceptionReferenceData;
import old.Datas.ReferenceData;

/**
 * 簡易例外処理用(統計データからの除外等で使用)
 * 
 * @author max
 *
 * @param field_db            データベースを操作するためのクラス
 * @param field_result        データベースのselect文を実行したときに取得データの一時格納用
 * @param field_DatabaseName  データベースの名前を格納するためのフィールド
 * @param field_referenceData 参照用データ(ここに存在するデータがある場合処理をしないなどの指定に使用する)
 * @param field_ERData        非参照用データ(ここに存在するデータが参照用データに存在する場合は実行するといった指定などに使用する)
 * @param field_sqlformat     参照用データ、非参照用データ取り出しに使用する固定文のＳＱＬ
 */

public class EasyAI {

	// database
	private DBAccess db;
	private ResultSet result;
	private String DatabaseName;

	// reference data inits
	private ArrayList<ReferenceData> referenceData;
	private ArrayList<ExceptionReferenceData> ERData;

	// sql format (default)
	private String[] sqlFormat;

	/**
	 * 簡易ＡＩ
	 * 
	 * @param DatabaseName データベースの名前(引数)
	 * 
	 * @throws SQLException         SQL実行時例外が起こった場合発生
	 * @throws IOException          書込みや出力時にエラーが起こった場合発生
	 * @throws InterruptedException 割り込み処理が発生した場合に発生
	 */
	public EasyAI(String DatabaseName) throws SQLException, IOException, InterruptedException {
		// database init
		this.DatabaseName = DatabaseName;
		this.db = new DBAccess(this.DatabaseName);
		// reference datas
		this.referenceData = new ArrayList<ReferenceData>();
		this.ERData = new ArrayList<ExceptionReferenceData>();
		// sql formats
		this.setSqlFormat((String[]) Arrays.asList("select * from referenceData", "select * from exceptionreferenceData").toArray());
		// reference data insert
		this.result = this.db.SearchSQLExecute(this.sqlFormat[0]);
		while (this.result.next()) {
			this.referenceData.add(new ReferenceData(this.result.getString("comments"), this.result.getString("totals"),this.result.getFloat("percent")));
		}
		// ErrorReference Data insert
		this.result = this.db.SearchSQLExecute(this.sqlFormat[1]);
		while (this.result.next()) {
			this.ERData.add(new ExceptionReferenceData(this.result.getString("comments"),
					this.result.getInt("priority"), this.result.getInt("flg")));
		}
		this.db.close();
	}

	/**
	 * 
	 * データベースの再リロード
	 * 
	 * @param list						データベースの内容を更新するためのナチュラルデータ
	 * @throws IOException				データベースが書き込みができないときにスローします
	 * @throws InterruptedException	スレッドの割り込みが発生した場合スローされます
	 * @throws SQLException			ＳＱＬが失敗した場合にスローされます
	 */
	public void DatabaseUpdate(ArrayList<DataLists> list) throws IOException, InterruptedException, SQLException {
		this.db = new DBAccess(this.DatabaseName);
		DatabaseInsert di = new DatabaseInsert();
		for (int i = 0; i < list.size(); i++) {
			String[] values = { list.get(i).getDate(), list.get(i).getNo(), list.get(i).getGroup(),
					list.get(i).getSirial(), list.get(i).getUser(), list.get(i).getComment() };
			String sql = di.CreateInsertSQLFormat(values);

			this.db.UpdateSQLExecute(sql);
		}
		
		// referenceData
		this.db.UpdateSQLExecute("insert into referenceData (comments, totals, percent) "
				+ "select rf.comments, rf.totals, rf.percent * 100 from referencedataview rf where not exists ( select * from referencedata );");
		
		this.referenceData.clear();
		this.ERData.clear();

		// reference data insert
		this.result = this.db.SearchSQLExecute(this.sqlFormat[0]);
		while (this.result.next()) {
			this.referenceData.add(new ReferenceData(this.result.getString("comments"),
					this.result.getString("totals"), this.result.getFloat("percent")));
		}
		// ErrorReference Data insert
		this.result = this.db.SearchSQLExecute(this.sqlFormat[1]);
		while (this.result.next()) {
			this.ERData.add(new ExceptionReferenceData(this.result.getString("comments"),
					this.result.getInt("priority"), this.result.getInt("flg")));
		}
		this.db.close();
	}

	// getter and setter
	public void DatabaseClose() {
		this.db.close();
	}

	// getter and setter
	public DBAccess getDb() {
		return db;
	}

	public void setDb(DBAccess db) {
		this.db = db;
	}

	public ResultSet getResult() {
		return result;
	}

	public void setResult(ResultSet result) {
		this.result = result;
	}

	public String[] getSqlFormat() {
		return sqlFormat;
	}

	public void setSqlFormat(String[] sqlFormat) {
		this.sqlFormat = sqlFormat;
	}

	public ArrayList<ReferenceData> getReferenceData() {
		return referenceData;
	}

	public void setReferenceData(ArrayList<ReferenceData> referenceData) {
		this.referenceData = referenceData;
	}

	public ArrayList<ExceptionReferenceData> getERData() {
		return ERData;
	}

	public void setERData(ArrayList<ExceptionReferenceData> eRData) {
		ERData = eRData;
	}

}