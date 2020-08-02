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
		 * Initialized Setups この設定はいらない可能性がある？
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
			// date setup
			String date;
			if (cd.getHour() < 7 | cd.getHour() > 23) {
				cd.prevDay(1);
			}
			date = cd.getCalcData();
			// directoryを入れる
			// dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, date));
			try {
				dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, date));
			} catch (FileNotFoundException e) {
				dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, date));
			}

			System.out.printf("\n\nPath: %s\n\n", dir);

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
			//読み上げない文字列を消すための領域
			//String regex = "/[a-zA-Z]*";
			String regex = "^\\\";(^|\\s)/ci\\d+?((\\s(\\d+|nw|t\\d|s\\d+)){1,4});(^|\\s)/(f|m|c)?la\\s([_a-zA-Z0-9'-]+)(\\ss\\d)?;(^|\\s)/((ca?o?(s|mo?u?f(lage)?)\\w*)\\s([*＊・\\w]+)){1,2}\\s*;/\\w+\\s*(on|off)\\s*\\d*\\s*;(^|\\s)/\\w*;^\\s";
			String regex2 = "\\{[a-zA-Z+-]*\\}";
			String regex3 = "[\\\\\\{\\}\\|\\[\\]'\\(\\)<>#\\%*\\+\\-\\?_？ωー＿／＼]+";
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
			 * AI実装部 データ定義
			 */
			ArrayList<String> Reference = new ArrayList<String>();
			ArrayList<String> EReference = new ArrayList<String>();

			for (int i = 0; i < ai.getReferenceData().size(); i++) {
				Reference.add(String.format("%s,%s", ai.getReferenceData().get(i).getUsername(),
						ai.getReferenceData().get(i).getComment()));
			}
			for (int i = 0; i < ai.getERData().size(); i++) {
				EReference.add(String.format("%s,%s", ai.getERData().get(i).getUsername(),
						ai.getERData().get(i).getComment()));
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

						// 読上げ判定式用
						boolean request = false;
						request = Arrays.asList(properties).indexOf(natuData.getGroup()) > -1;
						
						// Database統計
						ai.DatabaseUpdate(tmp);
						
						//debug
						/*
						System.out.println(String.format("boolean1: %d boolean2: %s\n andbool: %s\n properties: %s, request: %s",
								Arrays.asList(properties).indexOf("any") , request, Arrays.asList(setup.getProperties()).indexOf("any") > -1 | request, setup.getProperties(), request));
						*/
						// 統計データ更新
						for (int i = 0; i < ai.getReferenceData().size(); i++) {
							Reference.add(String.format("%s", ai.getReferenceData().get(i).getComment()));
						}
						
						for (int i = 0; i < ai.getERData().size(); i++) {
							EReference.add(String.format("%s", ai.getERData().get(i).getComment()));
						}
						
						// 棒読みに送るための処理を記述
						if (properties[0].equals("any") | request) {
							// System.out.println("1対象");
							// 読上げ例外処理
							if (Reference.indexOf(natuData.getComment()) == -1
									| EReference.indexOf(natuData.getComment()) != -1) {
								
								// console execute
								// to C Packet Request Execute
								cee.ConsoleCommand(String.format("%s:%s", natuData.getUser(), natuData.getComment().replaceAll(regex, "").replaceAll(regex2, "").replaceAll(regex3, "")));
							}
						}
					}
				}

				// debugs
				// System.out.println(String.format("reference : %s EReference : %s",
				// Reference.indexOf(natuData.getComment()) == -1,
				// EReference.indexOf(natuData.getComment()) != -1));
				// System.out.println(properties[0]);

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

				// database 処理・・・ｗ
				// database update
				/*
				 * 旧 if (minutes + 15 % 60 <= cd.getMin()) { minutes = cd.getMin(); //
				 * System.out.println("2対象"); // database update ai.DatabaseUpdate(tmp); if
				 * (cd.getHour() < 7 | cd.getHour() > 23) { cd.prevDay(1); } } cd.update();
				 */
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
