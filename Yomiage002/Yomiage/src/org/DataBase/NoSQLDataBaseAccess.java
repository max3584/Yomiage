package org.DataBase;

import java.io.IOException;

/**
 * 
 * NoSQL用のモジュールを定義するためのインターフェースです
 * 主に、ドキュメント型、カラム型、キーバリュー型などに使用します。
 * ※グラフ型には対応していません。
 * 
 * 処理終了後必ず閉じるように(注意喚起)
 * 
 * @author max
 * 
 */

public interface NoSQLDataBaseAccess {

	
	//▲▲▲▲▲▲▲▲▲　準備処理用メソッド　▲▲▲▲▲▲▲▲▲▲▲
	
	/**
	 * カーソル位置での取り出しが可能かどうかを確認するためのメソッド
	 * 
	 * @return boolean 取り出し可能であればTrueそうでなければFalse
	 * 
	 * @throws IOException データ出力に失敗した場合
	 */
	public boolean ready() throws IOException;
	
	/**
	 * カーソルを次の位置に動かしデータが存在するかどうかを確認するためのメソッド
	 * 
	 * @return boolean 取り出しが可能であればTrueそうでなければFalse
	 * 
	 * @throws IOException データ読み込みに失敗した場合
	 * 
	 */
	
	public boolean next() throws IOException;
	
	/**
	 * カーソル位置のデータを取り出す
	 * 
	 * @return String Datas
	 * 
	 * @throws IOException 呼び出しエラー
	 */
	
	//▲▲▲▲▲▲▲▲▲　実行用メソッド　▲▲▲▲▲▲▲▲▲▲▲
	
	public String readString() throws IOException;
	
	/**
	 *  StringReadMethod OverLoad
	 * @param flg 読み込み可能かどうか
	 * @return String Datas
	 * @throws IOException 呼び出しエラー
	 */
	
	public String readString(boolean flg) throws IOException;
	
	/**
	 *  カーソル位置のデータを取り出す
	 *  @return int Datas
	 * 
	 * @throws IOException 呼び出しエラー
	 */
	
	public int readInt() throws IOException;
	
	/**
	 *  ReadMethod OverLoad
	 * @param flg 読み込み可能かどうか
	 * @return 数値データ
	 * @throws IOException 呼び出しエラー
	 */
	
	public int readInt(boolean flg) throws IOException;
	
	//▲▲▲▲▲▲▲▲▲　終了処理用メソッド　▲▲▲▲▲▲▲▲▲▲▲
	
	/**
	 * 終了処理用のメソッド
	 */
	
	public void close();
	
}
