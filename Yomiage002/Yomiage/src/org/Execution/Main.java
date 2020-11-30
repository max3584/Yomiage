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

import javax.xml.parsers.ParserConfigurationException;

import org.CLI.MenuData.MenuExcute.easySetup;
import org.DataBase.DocumentDatabase;
import org.DataBase.NetworkDocumentDatabase;
import org.DataBase.NoSQLDocumentFormatXml;
import org.DataBase.Renual.DataContainer;
import org.DataBase.Renual.LRU_System;
import org.Datas.ChatData;
import org.Date.CalcDate;
import org.Exception.ApplicationException;
import org.Exception.ArgsException;
import org.Exception.FileReadException;
import org.Exception.InitFileException;
import org.Exception.SQLExecutionException;
import org.Exception.YomiageClassException;
import org.Execution.subject.*;
import org.Readers.FileRead;
import org.Readers.Directory.Directory;
import org.Readers.Directory.DirectoryUseSearch;
import org.xml.sax.SAXException;

/**
 * テキストを読上げソフトに転送するためのメインクラス
 * 
 * @author max
 *
 */
public class Main {

	/**
	 * 
	 * @param args
	 *             <dl>
	 *             <dt>index: 0</dt>
	 *             <dd>読上げ対象のパス</dd>
	 *             <dt>index: 1</dt>
	 *             <dd>ポート番号</dd>
	 *             <dt>index: 2</dt>
	 *             <dd>コメントジェネレータのファイルパスまたはURL</dd>
	 *             </dl>
	 * @throws InterruptedException  予期せぬエラーが発生した場合
	 * @throws NumberFormatException 数値に変換できない場合に発生
	 */
	public static void main(String[] args) {
		/*
		 * 引数がちゃんとあるかを確認
		 */
		try {
			boolean lineCheck = CheckArgs(args);
			boolean commentGenereterCheck = CheckCommentGenereter(args.length);
			if (lineCheck) {
				String path = args[0];
				int portNumber = Integer.parseInt(args[1]);
				Yomiage yomi = (commentGenereterCheck)?FindYomiage(args[2]) : FindYomiage(null);
				easySetup userInterface = new easySetup();
				String dir = FindFile(path, userInterface.isLogPreview());
				
				ArrayList<ChatData> chatDataList = FileReads(dir);
				ExecutorService es = Executors.newFixedThreadPool(1);
				do {
					int prevSize = chatDataList.size();
					chatDataList = FileReads(dir);
					ArrayList<ChatData> targetDatas = CreateTargetVoiceReadData(yomi, prevSize,
							chatDataList, userInterface.getProperties().split(","));
					targetDatas.forEach(target -> ChatVoiceReadRequest(yomi, target, portNumber));
					if (userInterface.isExecution()) {
						es.execute(userInterface);
					}
				} while (!userInterface.getProperties().equals("exit"));
				es.shutdown();
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 引数の確認用
	 * 
	 * @param args 引数に入っているデータ
	 * @return 要素数が2つ以上の場合trueそれ以外はfalse
	 * @throws ApplicationException 2つ以上の要素が入っていないとエラー
	 */
	private static boolean CheckArgs(String[] args) throws ApplicationException {
		if (args.length < 1) {
			ApplicationException error = new ArgsException("引数が存在しないため");
			throw error;
		}

		return true;
	}
	
	private static boolean CheckCommentGenereter(int len) {
		return len >= 3;
	}

	/**
	 * アプリケーションのスタートした時間を取得(実際に使う値まで計算)
	 * 
	 * @param format
	 * @return 日付
	 */
	private static String appStartDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		CalcDate startDate = new CalcDate(new Date(), sdf);
		if (startDate.getHour() < 7 | startDate.getHour() > 23) {
			startDate.prevDay(1);
		}
		return startDate.getCalcData();
	}

	/**
	 * ファイルを探す関数
	 * 
	 * @param dir         ディレクトリのパス
	 * @param showMessage メッセージを表示するかのパラメータ
	 * @return 情報取得を行うフルパス
	 * @throws ApplicationException 初期ファイルが存在しない場合に発生(ソフトウェアエラー)
	 */
	private static String FindFile(String dir, boolean showMessage) throws ApplicationException {
		String psoLogFileDir = dir, holdFile = ".\\ExtendFiles\\hold.txt";

		Directory dus = new DirectoryUseSearch();

		String export = null;
		try {
			export = dus.search(
					String.format("%s\\ChatLog%s_00.txt", psoLogFileDir, appStartDate("yyyyMMdd")));
		} catch (FileNotFoundException e) {
			try {
				export = dus.search(holdFile);
			} catch (FileNotFoundException e1) {
				ApplicationException error = new InitFileException("初期ファイルが存在しません");
				throw error;
			}
		}
		if (showMessage) {
			System.out.println(String.format("%s", appStartDate("yyyyMMdd")));
			System.out.print(String.format("Path: %s", export));
		}
		return export;
	}

	/**
	 * 読上げの処理判定を行う 何もない場合はNullが入っている
	 * 
	 * @param url プログラム呼び出しの第３引数を使用する
	 * @return コメントジェネレータを使用するクラスか使用しないクラスが返ってくる
	 * @throws ApplicationException 転送用のクラスが読み取れない場合発生
	 */
	private static Yomiage FindYomiage(String url) throws ApplicationException {
		Yomiage yomi = null;
		if (!(url == null)) {
			NoSQLDocumentFormatXml dd = null;
			try {
				if (url.indexOf("http") > -1) {
					dd = new NetworkDocumentDatabase(url, "r");
				} else {
					dd = new DocumentDatabase(url);
				}
				dd = new DocumentDatabase(String.format("%s\\comment.xml", dd.getValue("HcgPath")));
				yomi = new CGYomiage(dd);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				ApplicationException error = new YomiageClassException("転送用クラスが読み込めない");
				throw error;
			}
		} else {
			yomi = new NCGYomiage();
		}
		return yomi;
	}

	/**
	 * ファイルを読み込む関数
	 * 
	 * @param dir 読み込み対象のフルパス
	 * @param day 日付
	 * @return チャットデータクラスが格納されたリスト
	 * @throws ApplicationException ファイルが読み込めなかった場合に発生
	 */
	private static ArrayList<ChatData> FileReads(String dir) throws ApplicationException {
		try {
			FileRead fr = new FileRead(dir, StandardCharsets.UTF_16LE);
			return fr.formatRead(6);
		} catch (IOException e) {
			ApplicationException error = new FileReadException("ファイル読込ができなかった");
			throw error;
		}
	}

	/**
	 * 読み込みができるかを見る
	 * 
	 * @param naturalSize 最新の配列の数
	 * @param prevSize    古い配列の数
	 * @return 最新のが古いのより大きければtrueそれ以外はfalse;
	 */
	private static boolean CanRead(int naturalSize, int prevSize) {
		return naturalSize > prevSize;
	}

	/**
	 * リクエストができるかどうかチェック
	 * 
	 * @param properties リクエスト対象のグループ
	 * @param chat       対象データ
	 * @return リクエストが遅れる場合trueそれ以外はfalse
	 */
	private static boolean CanRequest(String[] properties, ChatData chat) {
		String chatGroup = chat.getGroup();

		return Arrays.asList(properties).indexOf(chatGroup) > -1 | properties[0].equals("any");
	}

	/**
	 * 読上げソフトに転送するための関数
	 * 
	 * @param yomi 読上げソフトへの転送定義変数
	 * @param chat 対象データ
	 * @param port ポート番号
	 */
	private static void ChatVoiceReadRequest(Yomiage yomi, ChatData chat, int port) {
		String user = chat.getUser();
		String comment = chat.getComment();

		yomi.Request(port, user, comment);
	}

	/**
	 * 読上げ対象かのチェック
	 * @param reference 読み上げ対象ではないものが入っている参照用データ
	 * @param comment コメント内容
	 * @return 読み上げ対象の場合trueそれ以外はfalse
	 */
	private static boolean CanChatVoiceReadRequest(ArrayList<String> reference, String comment) {
		return reference.indexOf(comment) == -1 & !comment.equals("");
	}

	/**
	 * 読上げ対象データの作成
	 * @param yomi 文字を削る処理のためのクラス
	 * @param start 対象開始位置
	 * @param chatList チャットデータのリスト
	 * @param parameter 読み上げ対象のグループ
	 * @return 読み上げ対象のリスト
	 */
	private static ArrayList<ChatData> CreateTargetVoiceReadData(Yomiage yomi, int start,
			ArrayList<ChatData> chatList, String[] parameter) throws ApplicationException {
		DataContainer dc = new DataContainer();
		String sql = "select * from reference where id <= 2;";
		ArrayList<ChatData> targetVoiceReadData = new ArrayList<ChatData>(0);
		for (int current = start; CanRead(current, chatList.size()); current++) {
			String group = chatList.get(current).getGroup();
			boolean requestAcl = group.equals("PUBLIC") | group.equals("PARTY");
			ArrayList<String> reference;
			try {
				reference = dc.ReferenceDataRequest(sql);
				String tmp = chatList.get(current).getComment();
				String comment = yomi.regexs(tmp);
				boolean canReq = CanRequest(parameter, chatList.get(current));
				boolean canChatVoiceReadReq = CanChatVoiceReadRequest(reference, comment);

				if (canReq & canChatVoiceReadReq) {
					chatList.get(current).setComment(comment);
					targetVoiceReadData.add(chatList.get(current));
				}
				if (canReq & canChatVoiceReadReq & requestAcl) {
					ReferenceDataRun(chatList.get(current));
				}
			} catch (SQLException e) {
				ApplicationException error = new SQLExecutionException("SQLが間違っているか実行できない");
				throw error;
			}
		}
		return targetVoiceReadData;
	}

	/**
	 * 読み上げないものを作るための処理
	 * @param chat チェックを行う対象チャットデータ
	 */
	private static void ReferenceDataRun(ChatData chat) {
		ExecutorService es = Executors.newFixedThreadPool(2);
		int TimerBase = 5 * 60 * 1000;
		ArrayList<LRU_System> orgSys = new ArrayList<LRU_System>();
		orgSys.add(new LRU_System(TimerBase, 1));
		orgSys.add(new LRU_System(2 * TimerBase, 2));
		orgSys.add(new LRU_System(3 * TimerBase, 3));
		orgSys.add(new LRU_System(6 * TimerBase, 4));

		es.execute(() -> ReferenceDataAddRun(orgSys, chat));
		es.execute(() -> ReferenceDataCheckRun(orgSys, chat.getComment()));

		es.shutdown();
	}

	/**
	 * 読み上げない処理のチェック
	 * @param orgSys 読み上げないものを管理するためのクラス
	 * @param comment コメント内容
	 */
	private static void ReferenceDataCheckRun(ArrayList<LRU_System> orgSys, String comment) {
		if (comment.isEmpty()) {
			orgSys.forEach(lruSys -> lruSys.Check());
		} else {
			orgSys.forEach(lruSys -> lruSys.Check(comment));
		}
	}

	/**
	 * 参照用データ作成(データの追加)
	 * @param orgSys 読み上げないものを管理するためのクラス
	 * @param chat チャット対象データ
	 */
	private static void ReferenceDataAddRun(ArrayList<LRU_System> orgSys, ChatData chat) {
		orgSys.forEach(lruSys -> lruSys.addData(chat));
	}
}
