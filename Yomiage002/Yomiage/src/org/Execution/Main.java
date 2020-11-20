package org.Execution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.CLI.MenuData.MenuExcute.easySetup;
import org.DataBase.DocumentDatabase;
import org.DataBase.NetworkDocumentDatabase;
import org.DataBase.NoSQLDocumentFormatXml;
import org.DataBase.Renual.DataContainer;
import org.DataBase.Renual.LRU_System;
import org.Datas.DataLists;
import org.Date.CalcDate;
import org.Execution.subject.*;
import org.Readers.FileRead;
import org.Readers.Directory.DirectoryUseSearch;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		/*
		 * 引数がちゃんとあるかを確認
		 */
		if (args.length < 1) {
			throw new IndexOutOfBoundsException("引数が存在しないため");
		}
		int portNumber = Integer.parseInt(args[1]);
		String psoLogFileDir = args[0], holdFile = ".\\ExtendFiles\\hold.txt";
		easySetup setup = new easySetup();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		CalcDate startDate = new CalcDate(new Date(), sdf);
		String dir = "";
		DirectoryUseSearch dus = new DirectoryUseSearch();
		int prevSize = 0;
		int today = startDate.getDay();

		if (startDate.getHour() < 7 | startDate.getHour() > 23) {
			startDate.prevDay(1);
		}

		setup.setLogPreview(true);
		
		System.out.println(String.format("%s", startDate.getCalcData()));
		
		try {
			dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, startDate.getCalcData()));
		} catch (FileNotFoundException e) {
			dir = dus.search(holdFile);
		}
		if (setup.isLogPreview()) {
			System.out.print(String.format("Path: %s", dir));
		}
		Yomiage yomi = null;

		// HTML5 Comment.xml取得するための処理
		if (args.length > 2) {
			NoSQLDocumentFormatXml dd = null;
			try {
				if (args[2].indexOf("http") > -1) {
					dd = new NetworkDocumentDatabase(args[2], "r");
				} else {
					dd = new DocumentDatabase(args[2]);
				}
				dd = new DocumentDatabase(String.format("%s\\comment.xml", dd.getValue("HcgPath")));
				yomi = new CGYomiage(dd);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		} else {
			yomi = new NCGYomiage();
		}

		setup.setLogPreview(false);

		int TimerBase = 5 * 60 * 1000;
		ExecutorService es = Executors.newFixedThreadPool(12);
		ArrayList<LRU_System> orgSys = new ArrayList<LRU_System>();
		orgSys.add(new LRU_System(TimerBase, 1));
		orgSys.add(new LRU_System(2 * TimerBase, 2));
		orgSys.add(new LRU_System(3 * TimerBase, 3));
		orgSys.add(new LRU_System(6 * TimerBase, 4));
		FileRead fr = new FileRead(dir, StandardCharsets.UTF_16LE);
		ArrayList<DataLists> tmp = fr.formatRead(6);
		CalcDate now = new CalcDate(new Date());
		DataContainer dc = new DataContainer();
		String sql = "select * from reference where id <= 2;";

		do {
			if (tmp.size() > prevSize) {

				// 読上げ判定式用
				boolean request = false;
				boolean requestAcl = false;
				String[] properties = setup.getProperties().replaceAll("\\s", "").split(",");

				// 一度取得したものに大して、最新のデータまでを読み込む
				for (; prevSize < tmp.size() & !properties[0].equals("none"); prevSize++) {

					String chatGroup = tmp.get(prevSize).getGroup();

					// 現在のカーソル位置でグループ設定的にあっているかの比較式
					request = Arrays.asList(properties).indexOf(chatGroup) > -1;
					requestAcl = chatGroup.equals(setup.getDataInitialize()[1][2])
							| chatGroup.equals(setup.getDataInitialize()[1][1]);

					// 棒読みに送るための処理を記述
					if (properties[0].equals("any") | request) {

						String user = tmp.get(prevSize).getUser();
						String comment = yomi.regexs(tmp.get(prevSize).getComment());

						try {
							ArrayList<String> reference = dc.update(sql);
							if (!comment.equals("") & reference.indexOf(comment) == -1) {
								// 読み上げ対象の条件式
								yomi.Request(portNumber, user, comment);
								if (setup.isLogPreview()) {
									System.out.println(
											String.format("%s : %s", user, comment));
								}
								orgSys.forEach(lruSys -> lruSys.Check(comment));
							}
						} catch (SQLException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
					}
					if (requestAcl) {
						int current = prevSize;
						var commentData = tmp;
						es.execute(() -> orgSys
								.forEach(lruSys -> lruSys.addData(commentData.get(current))));
					}
				}
				// △△△△△△△△△△△△△ Loop end △△△△△△△△△△△△△△

				prevSize = tmp.size();
			}
			
			orgSys.forEach(lruSys -> lruSys.Check());
			
			//Thread.currentThread().sleep(200);
			
			if (now.getDay() != today & now.getHour() > 7 | dir.equals(holdFile)) {
				setup.setLogPreview(true);
				startDate = now;
				today = now.getDay();
				try {
					dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir,
							startDate.getCalcData()));
					if (setup.isLogPreview()) {
						System.out.print(String.format("Path: %s", dir));
					}
				} catch (FileNotFoundException e) {
					dir = dus.search(holdFile);
				} finally {
					setup.setLogPreview(false);
				}
			}

			fr = new FileRead(dir, StandardCharsets.UTF_16LE);
			tmp = fr.formatRead(6);

			now = new CalcDate(new Date());

			// user input key type execution
			if (setup.getExcution()) {
				es.execute(setup);
			}

		} while (!setup.getProperties().equals("exit"));
		es.shutdown();
		es.awaitTermination(300, TimeUnit.MILLISECONDS);
	}
}
