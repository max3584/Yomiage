package org.Execution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.CLI.MenuData.MenuData;
import org.Datas.DataLists;
import org.Datas.TabDatas;
import org.Date.CalcDate;
import org.Readers.FileRead;
import org.Readers.Directory.DirectoryUseSearch;
import org.Request.UserRequest;

public class TodayReadExecution {

	public static void main(String... args) throws IndexOutOfBoundsException, FileNotFoundException {

		if (args.length < 2) {
			throw new IndexOutOfBoundsException("引数が存在しないため");
		}
		
		// PSO2ログファイルパス格納用データ
		String psoLogFileDir = args[0];
		// 棒読みちゃんの格納データ
		//String bouyomi = args[1];

		// init datas
		// 日付出力のフォーマット指定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 今日の日付を取得
		CalcDate cd = new CalcDate(new Date(), sdf);
		// ディレクトリpath格納用
		String dir = "";

		// satart up datas
		try {
			// directoryを入れる
			if (cd.getHour() + 1 > 7) {
				dir = new DirectoryUseSearch()
						.search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, cd.getData()));
			} else {
				cd.prevDay(1);
				dir = new DirectoryUseSearch().search(String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, cd.getCalcData()));
			}

		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			// ファイルがない場合の処理
			throw new FileNotFoundException("not File");
		} 
		// loop data update(real time comment readers)
		//読み上げた対象のデータが入っているクラス
		DataLists dl = null;
		// 生データが入っているクラス
		DataLists natuData = null;
		
		// 並列処理実行用
		ScheduledExecutorService es = Executors.newScheduledThreadPool(2);
		
		try {
			
			// inits
			boolean flg = true;
			MenuData md = new MenuData();
			// 読み取りに必要なクラス
			FileRead fr = new FileRead(dir);
			//一時保存用の領域
			ArrayList<String> tmp = fr.Reads();
			//初期データ格納
			dl = new DataLists(new TabDatas().TabInsert(tmp.get(tmp.size() - 1)));
			
			String[][] dataInitialize = {
					{"A", "a", "p", "private", "t", "g"},
					{"any", "PUBLIC", "PARTY", "REPLY", "GUILD", "group"}
			};
			
			String[] readSelect = null;
			
			
			
			Runnable CLIclass = new Runnable() {
				
				private String[] list;
				
				@Override
				public void run() {
					//((Object) new Future(es.submit(CLIclass))).chansel(true);
					
					String player = "none";
					UserRequest ur= new UserRequest();
					
					System.out.print("読み上げる対象はこのようになっています\n");
					System.out.print(String.format("現在：%s\n詳細：\nexit=終了,\n none = なし,\n A= any,\n a= 全体チャット,\n p=パーティーチャット,\n private= プライベートチャット(wis),\n t= チームチャット,\n g= グループチャット", player));
					System.out.print("\n変更する場合は、例の通りに入力してください >A[Enter] 複数入れる場合は[,]で区切ってください");
					
					player = ur.UserInputRequest(">");
					
					this.list = player.split(",");
				}
			};
			//Future future = es.submit(CLIclass);
			
			do {
				fr = new FileRead(dir);
				//一時保存領域
				tmp = fr.Reads();
				// 読み取り機構
				natuData = new DataLists(new TabDatas().TabInsert(tmp.get(tmp.size() - 1)));
				
				es.schedule(CLIclass, 150 , TimeUnit.MILLISECONDS);
				
				// 棒読みに送るための処理を記述
				
				Thread.sleep(200);
				
			} while (flg);
		} catch (Exception e) {

		} finally {
			es.shutdown();
		}

	}
}
