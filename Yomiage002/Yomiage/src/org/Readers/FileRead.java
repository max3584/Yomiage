package org.Readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.util.ArrayList;

import org.Datas.ChatData;
import org.Datas.TabDatas;
import org.Readers.Directory.DirectoryUseSearch;

/**
 * ファイル読み込みに使用するためのクラス
 * @author max
 *
 */
public class FileRead {
	
	/**
	 *  ディレクトリ保存用
	 */
	private File dir;
	/**
	 * メインで使用するファイル読み込み
	 */
	private BufferedReader br;
	
	/**
	 * コンストラクタ
	 * @param dir ディレクトリの指定されたファイルクラス
	 * @param fileName ファイル名の指定
	 * @param encode ファイルのエンコード指定
	 * @exception IOException 存在しないフォルダやファイルを指定した場合に発生します
	 */
	public FileRead(File dir, String fileName, Charset encode) throws IOException {
		this.dir = new File(dir.getPath() + "\\" + fileName);
		this.br = Files.newBufferedReader(this.dir.toPath(),  encode);
	}
	
	/**
	 *  コンストラクタ２
	 * @param dir ファイルまでのフルパスが設定されたファイルクラス
	 * @param encode ファイルのエンコード指定
	 * @exception IOException フルパス取得時に存在しないものがある場合発生します
	 */
	public FileRead(File dir, Charset encode) throws IOException {
		this.dir = dir;
		this.br = Files.newBufferedReader(this.dir.toPath(),  encode);
	}
	
	/**
	 * コンストラクタ
	 * @param dir フルパスの文字列
	 * @param encode ファイルのエンコード指定
	 * @exception IOException 存在しない場合発生します
	 */
	public FileRead(String dir, Charset encode) throws IOException {
		this(new File(dir),  encode);
	}
	
	/**
	 * 引数なしコンストラクタ
	 */
	public FileRead() {
		this.dir = null;
		this.br = null;
	}
	
	/**
	 * ファイル読み込み関数
	 * @return 中身のデータを1行ごとにリストとして取得
	 * @exception IOException ファイルが読み込み専用などアクセスができない場合に発生します
	 * @exception InterruptedException スレッドしょりうんぬン・・・((発生することはまずない
	 */
	public ArrayList<String> Reads() throws IOException, InterruptedException {
		if (this.dir.equals(null)) {
			return WhatRead();
		}
		
		ArrayList<String> result = new ArrayList<String>();
		while (br.ready()) {
			result.add(br.readLine());
		}
		this.br.close();
		return result;
	}
	
	/**
	 * 項目数で取得する場合に使用します
	 * @param col 項目数
	 * @return 項目ごとのデータのリスト
	 * @exception IOException アクセスができないまたは存在しない場合に発生します。
	 */
	public ArrayList<ChatData> formatRead(int col) throws IOException {
		if(this.dir.equals(null)) {
			new IOException("ディレクトリに何も入っていないため");
		}
		
		ArrayList<ChatData> result = new ArrayList<ChatData>();
		TabDatas td = new TabDatas();
		try {
			for(String line : this.Reads()) {
				String[] dump = td.TabInsert(line);
				if(dump.length == col) {
					result.add(new ChatData(dump));
				} else {
					if("".equals(line)) {
						continue;
					}
					String prevComment = result.get(result.size() - 1).getComment();
					String dumpComment = "";
					for (int i = 0; i < dump.length; i++) {
						dumpComment += dump[i];
					}
					result.get(result.size() - 1).setComment(prevComment + dumpComment);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.br.close();
		return result;
		
	}
	
	/**
	 * ユーザがCLIベースでファイル検索を行うようなメソッド
	 * @return ファイルの中身を1行ごとにリスト化したもの
	 * @exception FileNotFoundException ファイルが存在しない場合に発生
	 * @exception IOException ファイルが読み込めない場合に発生
	 * @exception InterruptedException スレッド処理うんぬん・・・((発生することはまずない
	 */
	public ArrayList<String> WhatRead() throws FileNotFoundException, IOException, InterruptedException{
		
		DirectoryUseSearch dus = new DirectoryUseSearch();
		
		String dir = dus.search();
		
		this.dir = new File(dir);
		this.br = Files.newBufferedReader(this.dir.toPath());
		
		return Reads();
	}
}
