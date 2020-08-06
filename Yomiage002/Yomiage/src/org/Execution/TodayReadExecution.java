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
import java.util.concurrent.TimeUnit;

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
		int portNumber = Integer.parseInt(args[1]);
		/**
		 * Initialized Setups 初期の動作確認に使用する
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
			throw new FileNotFoundException("今日のログファイルがありません(起動してから、再度お試しください)");
		}
		// loop data update(real time comment readers)
		// 読み上げた対象のデータが入っているクラス
		DataLists dl = null;
		// 生データが入っているクラス
		//DataLists natuData = null;
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
			// la
			String regex1 = "(^|\\s)/(f|m|c)?la\\s\\w*(\\ss\\d)?";
			// sr
			String regex2 = "/(sr|s\\w*)?\\s[(r|l|R|C)+\\w\\W]+\\s";
			// ci
			String regex3 = "(^|\\s)/ci\\d+?((\\s(\\d+|nw|t\\d|s\\d+)){1,4})";
			// on off
			String regex4 = "/\\w+\\s*(on|off)\\s*\\d*\\s*;(^|\\s)/\\w*";
			// cmf
			String regex5 = "(^|\\s)/((ca?o?(s|mo?u?f(lage)?)\\w*)\\s([*＊・\\W]+)){1,2}\\s*";
			// {colorchanges}
			String regex6 = "\\{[a-zA-Z+-]*\\}";
			// 伏字
			String regex7 = "[\\\\\\{\\}\\|\\[\\]'\\(\\)<>#\\%*\\+\\-\\?_？ωー＿／＼]+";

			String regex8 = "^\\s";
			String regex9 = "(^|\\s)?/\\w*";

			// 読み取りに必要なクラス
			FileRead fr = new FileRead(dir, StandardCharsets.UTF_16LE);
			// 一時保存用の領域
			ArrayList<DataLists> tmp = fr.formatRead(6);
			// Console Execution Express
			CEExpress cee = new CEExpress();
			// プロパティの値
			String[] properties;
			// database update timer
			// int minutes = cd.getMin();
			// Easy AI
			EasyAI ai = new EasyAI("JDBC:sqlite:.\\ExtendFiles\\controlData.db");

			// 並列処理実行用2
			ExecutorService thread_loop = Executors
					.newFixedThreadPool(ai.getReferenceData().size() > 0? (int) Math.sqrt(ai.getReferenceData().size()) : 1);

			/**
			 * AI実装部 データ定義
			 */
			ArrayList<String> Reference = new ArrayList<String>();
			ArrayList<String> EReference = new ArrayList<String>();

			for (int i = 0; i < ai.getReferenceData().size(); i++) {
				int num = i;
				thread_loop.execute(() -> {
					Reference.add(String.format("%s", ai.getReferenceData().get(num).getComment()));
				});
			}

			for (int i = 0; i < ai.getERData().size(); i++) {
				int num = i;
				thread_loop.execute(() -> {
					if (ai.getERData().get(num).isFlg() == 0)
						EReference.add(String.format("%s,%d", ai.getERData().get(num).getComment(),
								ai.getERData().get(num).getPriority()));
				});
			}
			do {
				// fileData読込み
				fr = new FileRead(dir, StandardCharsets.UTF_16LE);
				// 一時保存領域
				tmp = fr.formatRead(6);
				
				// 読み取り機構
				//natuData = tmp.get(tmp.size() - 1);
				
				// プロパティの値
				properties = setup.getProperties().split(",");
				// 読み上げする前の前処理
				if (dl instanceof DataLists) {
					if (!tmp.get(tmp.size() - 1).getNo().equals(dl.getNo())) {

						// 読上げ判定式用
						boolean request = false;
						//旧の判定式(番号ごとに変えるように変更した)
						//request = Arrays.asList(properties).indexOf(natuData.getGroup()) > -1;

						// Database統計
						ai.DatabaseUpdate(tmp);

						// スレッド処理終了
						thread_loop.shutdown();
						// 再度、入れる
						thread_loop = Executors.newFixedThreadPool(ai.getReferenceData().size() > 0? (int) Math.sqrt(ai.getReferenceData().size()) : 1);

						// 統計データ更新
						for (int i = 0; i < ai.getReferenceData().size(); i++) {
							int num = i;
							thread_loop.execute(() -> {
								Reference.add(String.format("%s",
										ai.getReferenceData().get(num).getComment()));
							});
						}

						for (int i = 0; i < ai.getERData().size(); i++) {
							int num = i;
							thread_loop.execute(() -> {
								if (ai.getERData().get(num).isFlg() == 0)
									EReference.add(String.format("%s,%d",
											ai.getERData().get(num).getComment(),
											ai.getERData().get(num).getPriority()));
							});
						}
						//一度取得したものに大して、最新のデータまでを読み込む
						for (int prev =Integer.parseInt(tmp.get(tmp.size() - 1).getNo()) - (Integer.parseInt(dl.getNo())) ; prev > 0; prev--) {
							//　現在のカーソル位置でグループ設定的にあっているかの比較式
							request = Arrays.asList(properties).indexOf(tmp.get(tmp.size() - prev).getGroup()) > -1;
							// 棒読みに送るための処理を記述
							if (properties[0].equals("any") | request) {
								// System.out.println("1対象");
								
								// 読上げ例外処理
								if (Reference.indexOf(tmp.get(tmp.size() - prev).getComment()) == -1 & 
										EReference.indexOf(tmp.get(tmp.size() - prev).getComment()) == -1) {

									String comment = tmp.get(tmp.size() - prev).getComment()
											.replaceAll(regex1, "")
											.replaceAll(regex2, "")
											.replaceAll(regex3, "")
											.replaceAll(regex4, "")
											.replaceAll(regex5, "")
											.replaceAll(regex6, "")
											.replaceAll(regex7, "")
											.replaceAll(regex8, "")
											.replaceAll(regex9, "").replace("\'", "")
											.replace("\"", "");
									
									if (!comment.equals("")) {
										// console execute
										// to C Packet Request Execute
										// portnumber, comment
										
										cee.ConsoleCommand(String.valueOf(portNumber), String.format("\'%s:%s\'",
												tmp.get(tmp.size() - prev).getUser(), comment));
										//debug
										//System.out.println(String.format("%d \'%s:%s\'", portNumber, tmp.get(tmp.size() - prev).getUser(), comment));
									}
								}
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
					es.execute(setup);
				}
				if(setup.getProperties().equals("exit")) {
					flg = false;
				}
				// dlが過去データとなるようにすること
				dl = tmp.get(tmp.size() - 1);
				
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
			thread_loop.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			es.shutdown();
			
			try {
				es.awaitTermination(60, TimeUnit.MILLISECONDS);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Finish!!");
	}
}
