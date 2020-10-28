package org.Execution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.ParserConfigurationException;

import org.CLI.MenuData.MenuExcute.easySetup;
import org.DataBase.DocumentDatabase;
import org.DataBase.NetworkDocumentDatabase;
import org.DataBase.NoSQLDocumentFormatXml;
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
		String psoLogFileDir = args[0];
		String holdFile = ".\\ExtendFiles\\hold.txt";
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

		try {
			dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, startDate.getData()));
			if (setup.isLogPreview()) {
				System.out.print(String.format("Path: %s", dir));
			}
		} catch (FileNotFoundException e) {
			dir = dus.search(holdFile);
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

		ExecutorService es = Executors.newFixedThreadPool(1);
		LRU_System orgSys = new LRU_System();
		FileRead fr = new FileRead(dir, StandardCharsets.UTF_16LE);
		ArrayList<DataLists> tmp = fr.formatRead(6);
		String[] properties = setup.getProperties().replaceAll("\\s", "").split(",");
		CalcDate now = new CalcDate(new Date());
		
		do {
			if (tmp.size() > prevSize) {

				// 読上げ判定式用
				boolean request = false;

				// 一度取得したものに大して、最新のデータまでを読み込む
				for (int prev = tmp.size() - prevSize; prev > 0; prev--) {
					// 現在のカーソル位置でグループ設定的にあっているかの比較式
					request = Arrays.asList(properties).indexOf(tmp.get(tmp.size() - prev).getGroup()) > -1;
					// 棒読みに送るための処理を記述
					if (properties[0].equals("any") | request) {
						orgSys.addData(tmp.get(prev));
						
						String user = tmp.get(prev).getUser();
						String comment = tmp.get(prev).getComment();
						if (!comment.equals("")) {
							// 読み上げ対象の条件式
							yomi.Request(portNumber, user, yomi.regexs(comment));
						}
					}
				}
				// △△△△△△△△△△△△△ Loop end △△△△△△△△△△△△△△

				prevSize = tmp.size();
			}
			orgSys.Check();
			
			if (now.getDay() != today & now.getHour() > 7 | dir.equals(holdFile)) {
				startDate = now;
				today = now.getDay();
				try {
					dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, startDate.getData()));
					if (setup.isLogPreview()) {
						System.out.print(String.format("Path: %s", dir));
					}
				} catch (FileNotFoundException e) {
					dir = dus.search(holdFile);
				}
			}
			
			now = new CalcDate(new Date());
			
			// user input key type execution
			if (setup.getExcution()) {
				es.execute(setup);
			}

		} while (!setup.getProperties().equals("exit"));
	}
}
