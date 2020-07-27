package org.Execution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.AI.EasyAI;
import org.CLI.CEExpress;
import org.CLI.MenuData.MenuExcute.easySetup;
import org.Datas.DataLists;
import org.Date.CalcDate;
import org.Readers.FileRead;
import org.Readers.Directory.DirectoryUseSearch;

public class TodayReadExecution {

	public static void main(String... args) throws IndexOutOfBoundsException, IOException {

		if (args.length < 1) {
			throw new IndexOutOfBoundsException("引数が存在しないため");
		}
		
		/**
		 * Initialized Setups
		 * この設定はいらない可能性がある？
		 */
		Initialized init = new Initialized(args[0]);

		// PSO2ログファイルパス格納用データ
		String psoLogFileDir = args[0];
		// Html5 CommentGenerater機能を入れるかどうか
		// boolean commentgenerater = Integer.parseInt(args[1]) == 1;
		// String commentGeneraterUrl = args[2];

		// init datas
		// 日付出力のフォーマット指定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 今日の日付を取得
		CalcDate cd = new CalcDate(new Date(), sdf);
		// ディレクトリpath格納用
		String dir;
		// directory use search
		DirectoryUseSearch dus = new DirectoryUseSearch();
		// satart up datas
		try {
			//date setup
			String date;
			if(cd.getHour() < 7 | cd.getHour() > 23) {
				cd.prevDay(1);
			}
			date = cd.getCalcData();
			// directoryを入れる
			//dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, date));
			if (cd.getHour() > 6 & cd.getHour() < 24) {
				dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, date));
			} else {
				cd.prevDay(1);
				try {
					dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, cd.getCalcData()));
				} catch (FileNotFoundException e) {
					dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, date));
				}
			}
			

		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			// ファイルがない場合の処理
			throw new FileNotFoundException("not File");
		}
		// loop data update(real time comment readers)
		// 読み上げた対象のデータが入っているクラス
		DataLists dl = null;
		// 生データが入っているクラス
		DataLists natuData = null;
		// setup start
		easySetup setup = new easySetup();

		// 並列処理実行用
		ExecutorService es = Executors.newFixedThreadPool(1);

		try {
			// inits
			boolean flg = true;
			// 読み取りに必要なクラス
			FileRead fr = new FileRead(dir, StandardCharsets.UTF_16LE);
			// 一時保存用の領域
			ArrayList<DataLists> tmp = fr.formatRead(6);
			// Console Execution Express
			CEExpress cee = new CEExpress();
			// プロパティの値
			String[] properties;
			// database update timer
			int minutes = cd.getMin();
			// Easy AI
			EasyAI ai = new EasyAI("JDBC:sqlite:.\\ExtendFiles\\controlData.db");
			/**
			 * AI実装部
			 * データ定義
			 */
			ArrayList<String> Reference = new ArrayList<String>();
			ArrayList<String> EReference = new ArrayList<String>();
			
			for(int i = 0; i < ai.getReferenceData().size(); i++) {
				Reference.add(String.format("%s,%s",ai.getReferenceData().get(i).getUsername(), ai.getReferenceData().get(i).getComment()));
				EReference.add(String.format("%s,%s", ai.getERData().get(i).getUsername(), ai.getERData().get(i).getComment()));
			}
			
			do {
				fr = new FileRead(dir, StandardCharsets.UTF_16LE);
				// 一時保存領域
				tmp = fr.formatRead(6);
				// 読み取り機構
				natuData = tmp.get(tmp.size() - 1);

				// プロパティの値
				properties = setup.getProperties().split(",");
				// 読み上げする前の前処理
				if (dl instanceof DataLists) {
					if (!natuData.getNo().equals(dl.getNo())) {
						
						//読上げ判定式用
						boolean request = false;
						//判定処理
						for(int i = 0; i < properties.length; i++) {
							if(request) {
								break;
							}
							request = properties[i].indexOf(natuData.getGroup()) > -1; 
						}
						// 棒読みに送るための処理を記述
						if (properties[0].equals("any") | request) {
							//読上げ例外処理
							if(Reference.indexOf(natuData.getComment()) == -1 & EReference.indexOf(natuData.getComment()) != -1) {
								// console execute
								// to C Packet Request Execute
								cee.ConsoleCommand(String.format("%s:%s", natuData.getUser(), natuData.getComment()));
							}
						}
					}
				}
				
				// user input key type execution
				if (setup.getExcution()) {
					// profile education
					if ("exit".equals(properties[0])) {
						flg = false;
					} else {
						es.execute(setup);
					}

				}

				if ("exit".equals(properties[0])) {
					flg = false;
				}

				// dlが過去データとなるようにすること
				dl = natuData;
				
				//database update
				if(minutes + 15 % 60 >= cd.getMin()) {
					minutes = cd.getMin();
					// database update
					init.UpdateDatabase(tmp);
				}
				// sleep time
				Thread.sleep(200);
			} while (flg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			es.shutdown();
		}
		System.out.println("Finish!!");
	}
}
