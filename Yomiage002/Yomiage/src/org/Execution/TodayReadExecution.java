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

		/**
		 * /p /cmf ＊神刀スサノオ /mpal1 /spal18 /sr Ｒ／Ｃストライク Ｃスト(迷彩変更)
		 */

		try {
			// inits
			boolean flg = true;
			// 読み上げない文字列を消すための領域

			String regex1 = "(^|\\s)?/\\w*";
			// カラー変更削除
			String regex2 = "\\{[a-zA-Z+-]*\\}";
			// 伏字
			String regex3 = "[\\\\\\{\\}\\|\\[\\]'\\(\\)<>#\\%*\\+\\-\\?_？ωー＿／＼]+";
			// cmf
			String regex4 = "(^|\\s)/cmf(\\s|\\b)?\\w*";
			// la
			String regex5 = "(^|\\s)/(f|m|c)?la\\s\\w*(\\ss\\d)?";
			// ci
			String regex6 = "(^|\\s)/ci\\d+?((\\s(\\d+|nw|t\\d|s\\d+)){1,4})";
			// ca
			String regex7 = "(^|\\s)/((ca?o?(s|mo?u?f(lage)?)\\w*)\\s([*＊・\\w]+)){1,2}\\s*";
			// on offの変更の削除
			String regex8 = "/\\w+\\s*(on|off)\\s*\\d*\\s*;(^|\\s)/\\w*";
			String regex9 = "^\\s";
			// String regex =
			// "^/[a-zA-Z]*;(^|\\s)/(m|s)pal?\\d;(^|\\s)/ci\\d+?((\\s(\\d+|nw|t\\d|s\\d+)){1,4});(^|\\s)/cmf?\\b\\w?;(^|\\s)/(f|m|c)?la\\s([_a-zA-Z0-9'-]+)(\\ss\\d)?;(^|\\s)/((ca?o?(s|mo?u?f(lage)?)\\w*)\\s([*＊・\\w]+)){1,2}\\s*;/\\w+\\s*(on|off)\\s*\\d*\\s*;(^|\\s)/\\w*;^\\s";
			// ^\";(^|\s)/ci\d+?((\s(\d+|nw|t\d|s\d+)){1,4});(^|\s)/cmf?\\b\\w?;(^|\s)/(f|m|c)?la\s([_a-zA-Z0-9'-]+)(\ss\d)?;(^|\s)/((ca?o?(s|mo?u?f(lage)?)\w*)\s([*＊・\w]+)){1,2}\s*;/\w+\s*(on|off)\s*\d*\s*;(^|\s)/\w*;^\s

			// 読み取りに必要なクラス
			FileRead fr = new FileRead(dir, StandardCharsets.UTF_16LE);
			// 一時保存用の領域
			ArrayList<DataLists> tmp = fr.formatRead(6);
			// Console Execution Express
			CEExpress cee = new CEExpress();
			// プロパティの値
			String[] properties;
			// database update timer
			//int minutes = cd.getMin();
			// Easy AI
			EasyAI ai = new EasyAI("JDBC:sqlite:.\\ExtendFiles\\controlData.db");
			
			// 並列処理実行用2
			ExecutorService thread_loop = Executors.newFixedThreadPool((int)Math.sqrt(ai.getReferenceData().size()));
			
			/**
			 * AI実装部 データ定義
			 */
			ArrayList<String> Reference = new ArrayList<String>();
			ArrayList<String> EReference = new ArrayList<String>();
			
			for (int i = 0; i < ai.getReferenceData().size(); i++) {
				int num = i;
				thread_loop.execute(() -> {
					Reference.add(String.format("%s,%s", ai.getReferenceData().get(num).getUsername(),ai.getReferenceData().get(num).getComment()));
				});
			}
			
			for (int i = 0; i < ai.getERData().size(); i++) {
				int num = i;
				thread_loop.execute(() -> {
					EReference.add(String.format("%s,%s", ai.getERData().get(num).getUsername(), ai.getERData().get(num).getComment()));
				});
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

						// スレッド処理終了
						thread_loop.shutdown();
						//再度、入れる
						thread_loop = Executors.newFixedThreadPool((int)Math.sqrt(ai.getReferenceData().size()));
						
						// 統計データ更新
						for (int i = 0; i < ai.getReferenceData().size(); i++) {
							int num = i;
							thread_loop.execute(() -> {
								Reference.add(String.format("%s,%s", ai.getReferenceData().get(num).getUsername(),ai.getReferenceData().get(num).getComment()));
							});
						}
						
						for (int i = 0; i < ai.getERData().size(); i++) {
							int num = i;
							thread_loop.execute(() -> {
								EReference.add(String.format("%s,%s", ai.getERData().get(num).getUsername(), ai.getERData().get(num).getComment()));
							});
						}

						// 棒読みに送るための処理を記述
						if (properties[0].equals("any") | request) {
							// System.out.println("1対象");
							// 読上げ例外処理
							if (Reference.indexOf(natuData.getComment()) == -1
									| EReference.indexOf(natuData.getComment()) != -1) {

								// console execute
								// to C Packet Request Execute
								cee.ConsoleCommand(String.format("%s:%s", natuData.getUser(),
										natuData.getComment().replaceAll(regex4, "")
												.replaceAll(regex2, "")
												.replaceAll(regex3, "")
												.replaceAll(regex4, "")
												.replaceAll(regex5, "")
												.replaceAll(regex6, "")
												.replaceAll(regex7, "")
												.replaceAll(regex8, "")
												.replaceAll(regex9, "")
												.replaceAll(regex1, "")));
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
