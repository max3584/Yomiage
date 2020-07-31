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
 *
 */

public class Initialized {

	// init data field
	private File file;
	private DBAccess db;
	private InitPropertiesFile ipf;
	private CalcDate date;

	// constructor
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
				if (fileName[i].indexOf(
						String.format("ChatLog%s", date_flg ? String.valueOf(use) : flg? "" : date.getData())) == 0) {
					fileList.add(fileName[i]);
				}
			}

			// 並列処理実行用
			ExecutorService es = Executors.newFixedThreadPool((int) (fileList.size() / 2));
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

			int row = frt.size();
			System.out.println(String.format("\n\nMaxPage:%s", row));
			for (int i = 0; i < row; i++)
				for (int j = 0; j < frt.get(i).getSqls().size(); j++) {
					System.out.print(String.format("%s\tpage:%d\t%3.2f%%\tflg:%d \r",
							rt.request(System.currentTimeMillis()), i + 1,
							(double) ((double) (j + 1) / (double) frt.get(i).getSqls().size())
									* 100,
							this.db.UpdateSQLExecute(frt.get(i).getSqls().get(j))));
				}

			this.db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.ipf.getProperties().setProperty("first", "false");
			this.ipf.getProperties().store(new FileOutputStream(this.ipf.getFileName()), "Yomiage Properties");
		}
		this.file = new File(".\\ExtendFiles\\");
		this.db.close();
	}

	// class method...
	public void UpdateDatabase(ArrayList<DataLists> list) {
		DatabaseInsert di = new DatabaseInsert();
		for (int i = 0; i < list.size(); i++) {
			String[] values = {list.get(i).getDate(), list.get(i).getNo(), list.get(i).getGroup(), list.get(i).getSirial(), list.get(i).getUser(), list.get(i).getComment()};
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
