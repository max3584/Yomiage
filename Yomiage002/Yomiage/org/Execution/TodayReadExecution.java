package org.Execution;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.CLI.CEExpress;
import org.CLI.MenuData.MenuExcute.easySetup;
import org.Datas.DataLists;
import org.Date.CalcDate;
import org.Readers.FileRead;
import org.Readers.Directory.DirectoryUseSearch;

public class TodayReadExecution {

	public static void main(String... args) throws IndexOutOfBoundsException, FileNotFoundException {

		if (args.length < 1) {
			throw new IndexOutOfBoundsException("引数が存在しないため");
		}
		
		/**
		 * Initialized Setups
		 * この設定はいらない可能性がある？
		 */
		

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
			// directoryを入れる
			if (cd.getHour() + 1 > 7) {
				dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, cd.getData()));
			} else {
				try {
					cd.prevDay(1);
					dir = dus.search(String.format("%s\\ChatLog%s_00.txt",
							psoLogFileDir, cd.getCalcData()));
				} catch (FileNotFoundException e) {
					dir = dus.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, cd.getData()));
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
			FileRead fr = new FileRead(dir, Charset.forName("UTF-16LE"));
			// 一時保存用の領域
			ArrayList<DataLists> tmp = fr.formatRead(6);
			// Console Execution Express
			CEExpress cee = new CEExpress();
			// プロパティの値
			String properties;

			do {
				fr = new FileRead(dir, Charset.forName("UTF-16LE"));
				// 一時保存領域
				tmp = fr.formatRead(6);
				// 読み取り機構
				natuData = tmp.get(tmp.size() - 1);

				// プロパティの値
				properties = setup.getProperties();
				// 読み上げする前の前処理
				if (dl instanceof DataLists) {
					if (!natuData.getNo().equals(dl.getNo())) {

						// 棒読みに送るための処理を記述
						if (properties.indexOf(natuData.getGroup()) > -1 | properties.equals("any")) {
							// console execute
							//System.out.println(String.format("%s:%s",natuData.getUser(), natuData.getComment()));
							// to C Packet Request Execute
							cee.ConsoleCommand(String.format("%s:%s", natuData.getUser(), natuData.getComment()));
						}
					}
				}

				if (setup.getExcution()) {
					// profile education
					if ("exit".equals(properties)) {
						flg = false;
					} else {
						es.execute(setup);
					}

				}

				if ("exit".equals(properties)) {
					flg = false;
				}

				// dlが過去データとなるようにすること
				dl = natuData;
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
