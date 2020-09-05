package org.Execution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.DataBase.DBAccess;
import org.Datas.DataLists;
import org.Date.CalcDate;
import org.Readers.FileReadThred;
import org.Readers.Directory.DirectoryUseSearch;
import org.Request.DatabaseInsert;
import org.Request.InitPropertiesFile;
import org.Request.RequestTime;
import org.Request.UserRequest;

/**
 * 初期データ格納用()
 * 
 * @author max
 */

public class Initialized {

	// init data field
	private File file;
	private DBAccess db;
	private InitPropertiesFile ipf;
	private CalcDate date;

	// constructor
	/**
	 * フィールドの中身
	 * 
	 * @param file プロパティファイルと、ログ取得時に使用します
	 * @param db   データベースへの操作を実装しているクラス
	 * @param ipf  プロパティファイルに読み書きを行うクラス
	 * @param date 自作関数クラスで時間関連をいじるためのクラス(実行ＯＳの時間変更などはしない)
	 */
	public Initialized(String dir) throws FileNotFoundException, IOException {
		this.date = new CalcDate(new Date(), new SimpleDateFormat("yyyyMM"));
		try {

			// properties file inits data
			this.ipf = new InitPropertiesFile(".\\ExtendFiles\\Yomiage.properties");
			boolean flg = Boolean.parseBoolean((String) ipf.getProperties().get("first"));
			// create Files
			this.file = new File(".\\ExtendFiles\\");

			// mkdir
			if (this.file.mkdir()) {
				System.out.println("ExtendFile Create Complete!");
			}
			// database file
			File DatabaseFile = new File(".\\ExtendFiles\\controlData.db");
			if (DatabaseFile.createNewFile()) {
				System.out.println("DataBase File Create Complete!");
				DefaultSQL defaultsql = new DefaultSQL();
				this.db = new DBAccess("JDBC:sqlite:" + DatabaseFile.toString());

				// String[] sql = defaultsql.getSql().replaceAll("\r\n", "").split(";");
				/*
				 * for (int i = 0; i < sql.length; i++) { this.db.UpdateSQLExecute(sql[i] +
				 * ";"); }
				 */
				this.db.UpdateSQLExecute(defaultsql.getSql());
				/*
				 * try { InitializedDatabaseCreate idc = new
				 * InitializedDatabaseCreate(this.file);
				 * this.db.UpdateSQLExecute(idc.getSb().substring(0)); } catch (IOException e) {
				 * this.db.UpdateSQLExecute(defaultsql.getSql()); }
				 */
				this.db.close();
			}
			this.db = new DBAccess("JDBC:sqlite:" + DatabaseFile.toString());

			// create Database
			DirectoryUseSearch dus = new DirectoryUseSearch();

			// init use datas
			// File name
			String[] fileName = null;
			String dir_local = dus.search(dir);
			fileName = dus.Files();

			// user request
			UserRequest ur = new UserRequest();
			int use = 0;
			boolean date_flg = true;

			// static Text
			final String text = (flg) ? "\n※初回に限り何も入力しなかった場合すべてを取得します" : "";
			// user input request
			do {

				System.out.println("特定の年月日のみ取得する場合設定してください<半角文字>(Default:1ヵ月)");
				System.out.printf("例:\n>%s\n", this.date.getCalcData());
				System.out.println("特に何もない場合は[Enter]を押してください" + text);
				try {
					String use_local = ur.UserInputRequest(">");
					if (use_local.length() == 0 & use_local.equals("")) {
						date_flg = false;
					} else {
						use = Integer.parseInt(use_local);
						break;
					}
				} catch (Exception e) {
					System.out.println("数字以外を入力することはできません(半角文字で入力)");
				}

			} while (date_flg);

			// ファイル名指定処理
			ArrayList<String> fileList = new ArrayList<String>();

			for (int i = 0; i < fileName.length; i++) {
				if (fileName[i].indexOf(String.format("ChatLog%s",
						date_flg ? String.valueOf(use) : flg ? "" : date.getData())) == 0) {
					fileList.add(fileName[i]);
				}
			}

			// 並列処理実行用
			ExecutorService es = Executors
					.newFixedThreadPool((int) (fileList.size() > 0 ? fileList.size() : 1 / 2));
			// SQL文作成までを実行するためのクラス
			ArrayList<FileReadThred> frt = new ArrayList<FileReadThred>();

			for (int i = 0; i < fileList.size(); i++) {
				int num = i;
				frt.add(new FileReadThred(dir_local + "\\" + fileList.get(i)));
				// 並列処理
				es.execute(() -> frt.get(num).fileReaders());
			}

			es.shutdown();
			try {
				es.awaitTermination(30, TimeUnit.SECONDS);
			} catch (InterruptedException err) {
				err.printStackTrace();
			}

			RequestTime rt = new RequestTime();
			// どれだけのファイル数があるかを表示
			int row = frt.size();
			System.out.print(String.format("\n\nMaxPage:%s\n", row));
			// ＳＱＬを実行しながら、進捗を表示
			for (int i = 0; i < row; i++)
				for (int j = 0; j < frt.get(i).getSqls().size(); j++) {

					System.out.print("ＳＱＬを実行中ですしばらくお待ちください\t\t\t\t\t\t\t\t\r");
					// sql execution (1ファイルごとの実行)
					int num = this.db.UpdateSQLExecute(frt.get(i).getSqls().get(j));
					if (num > -1) {
						// Time Page Percent datas
						System.out.print(String.format("%s\tPage:%d\t%3.2f%%\tDatas:%d\r",
								rt.request(System.currentTimeMillis()), i + 1,
								(double) ((double) (i + 1) / (double) frt.size()) * 100, num));
					} else {
						String[] sqls = frt.get(i).getSqls().get(j).split(";");
						int count = 1;

						double page = 0;
						double query = 0;

						for (String sql : sqls) {
							num = this.db.UpdateSQLExecute(sql + ";");

							page = (double) ((double) (i + 1) / (double) frt.size()) * 100;
							query = (double) ((double) count / (double) sqls.length) * 100;

							// Time Page pageEndPercent SQLinePercent flg
							System.out.print(String.format(
									"%s\tPage:%d\tPagePercent:%s%%\tQueryLine:%s%%\tflg:%d \r",
									rt.request(System.currentTimeMillis()), i + 1,
									String.format("%s%3.2f", page < 10 ? "0" : "", page),
									String.format("%s%3.2f", query < 10? "0" : "", query), num));
							count++;
						}
						System.out.print("\t\t\t\t 差分データ挿入完了\t\t\t\t\t\n");
					}
				}
			System.out.println(
					String.format("\n処理終了\nかかった時間は [%s]です", rt.request(System.currentTimeMillis())));
			this.db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.ipf.getProperties().setProperty("read", "none");
			this.ipf.getProperties().setProperty("first", "false");
			this.ipf.getProperties().setProperty("date",
					new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis()));
			this.ipf.getProperties().store(new FileOutputStream(this.ipf.getFileName()), "Yomiage Properties");
		}
		this.file = new File(".\\ExtendFiles\\");
		this.db.close();
	}

	// class method...
	/**
	 * データベースの更新を行うメソッド
	 * 
	 * @param list データリストクラスの入ったリストを引数に指定
	 * @apiNote DataListsは格納データのことでここのパッケージではよく使う保存用のクラス
	 *          また、これを実行する前にデータベースを実行可能状態にしてから実行してください
	 * @exception NullPointerException データベースが存在しない
	 */
	public void UpdateDatabase(ArrayList<DataLists> list) {
		DatabaseInsert di = new DatabaseInsert();
		for (int i = 0; i < list.size(); i++) {
			String[] values = { list.get(i).getDate(), list.get(i).getNo(), list.get(i).getGroup(),
					list.get(i).getSirial(), list.get(i).getUser(), list.get(i).getComment() };
			String sql = di.CreateInsertSQLFormat(values);

			this.db.UpdateSQLExecute(sql);
		}
	}

	// getter and setter
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public DBAccess getDb() {
		return db;
	}

	public void setDb(DBAccess db) {
		this.db = db;
	}

	public InitPropertiesFile getIpf() {
		return ipf;
	}

	public void setIpf(InitPropertiesFile ipf) {
		this.ipf = ipf;
	}
}
