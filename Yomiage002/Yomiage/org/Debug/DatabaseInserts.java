package org.Debug;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import org.DataBase.DBAccess;
import org.Readers.FileReadThred;
import org.Readers.Directory.DirectoryUseSearch;
import org.Request.RequestTime;

public class DatabaseInserts {

	public static void main(String[] args) {
		// DirectoryUseSearch のデバッグ
		DirectoryUseSearch dus = new DirectoryUseSearch();

		// init use datas
		// directory;
		String dir = "";

		String[] fileName = null;
		try {
			dir = dus.search("C:\\users\\pukug\\documents\\sega\\phantasystaronline2\\log\\");
			fileName = dus.Files();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		// ファイル名指定処理
		ArrayList<String> fileList = new ArrayList<String>();

		for (int i = 0; i < fileName.length; i++) {
			if (fileName[i].indexOf("ChatLog") == 0) {
				fileList.add(fileName[i]);
			}
		}

		// 並列処理実行用
		ExecutorService es = Executors.newFixedThreadPool((int) (fileList.size() / 2));
		// SQL文作成までを実行するためのクラス
		ArrayList<FileReadThred> frt = new ArrayList<FileReadThred>();
		try {

			for (int i = 0; i < fileList.size(); i++) {
				/*
				 * System.out.println("debug0: " + fileList.get(i));
				 * System.out.println("debug1: " + dir + "\\" + fileList.get(i));
				 */
				int num = i;
				frt.add(new FileReadThred(dir + "\\" + fileList.get(i)));
				// 並列処理
				es.execute(() -> frt.get(num).fileReaders());
			}

			RequestTime rt = new RequestTime();

			// databaseに入れるかの作業･･･?
			DBAccess dba = new DBAccess("JDBC:sqlite:K:\\DB\\Storage\\ProgramContesnts\\YomiageDatas\\controlData.db");
			int row = frt.size();

			for (int i = 0; i < row; i++)
				for (int j = 0; j < frt.get(i).getSqls().size(); j++) {
					// System.out.println("debug:" + frt.get(i).getSqls().get(j));
					System.out.print(String.format("%s\tpage:%d\t%3.2f%%\tflg:%d \r",
							rt.request(System.currentTimeMillis()), i + 1,
							(double)((double)(j + 1) / (double)frt.get(i).getSqls().size()) * 100,
							dba.UpdateSQLExecute(frt.get(i).getSqls().get(j))));
				}

			dba.close();
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		} finally {
			es.shutdown();
		}
	}
}
