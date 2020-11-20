package old.Execution;

import java.io.IOException;

import org.DataBase.DBAccess;
import org.Datas.Version;

public class ChangeDatabaseTable {

	public static void main(String[] args) {
		boolean msg = false;
		try {
			DBAccess dba = new DBAccess("JDBC:sqlite:" + args[0]);
			DefaultSQL sql = new DefaultSQL();
			Version ipf = new Version(".\\ExtendFiles\\Version.Properties");
			if (!ipf.getProperties().getProperty("version").equals("v0.0.8")) {
				msg = true;
				System.out.println("データベースのテーブル更新実行中");
				System.out.println("テーブル作成中＆更新中");
				dba.UpdateSQLExecute(sql.getUpdateTables());
				System.out.println("テーブル作成＆更新が終了しました");
			}
			ipf.StampVersion("v0.0.8");
			dba.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println(msg? "実行完了しました。" : "特に更新がないため、スキップしました。");
		}
	}
}
