package org.CLI;

import java.io.IOException;

/**
 * 
 * 現在使用している受け渡しクラス
 * @author max
 */

public class CEExpress implements ConsoleExecution {

	/**
	 * 初期コマンドが入っているフィールド
	 */
	private String initCommand;
	/**
	 * コマンドプロンプトを実行するためのクラス
	 */
	private ProcessBuilder pb;

	/**
	 * クラス固有の定義(読み上げソフトウェアに転送するための実行ファイル呼び出し設定)
	 */
	public CEExpress() {
		this.initCommand = ".\\ExtendFiles\\YomiageCPR.exe";
	}
	
	/**
	 * 任意のOSコマンドを設定(管理実行権限は有していないことに注意)
	 * @param args OSコマンドを実行するための設定
	 */
	public CEExpress(String... args) {
		this.pb = new ProcessBuilder(args);
	}

	/**
	 * 読み上げ転送用のプログラムに通信するためのもの
	 * @param port ポート番号
	 * @param msg 読み上げる内容
	 */
	public void ConsoleCommand(int port, String msg) {
		this.pb = new ProcessBuilder(this.initCommand, String.valueOf(port), msg);

		try {
			if (this.pb.inheritIO().start().waitFor() != 0) {
				throw new IOException("実行時例外");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ConsoleCommand() {
		try {
			if (this.pb.inheritIO().start().waitFor() != 0) {
				throw new IOException("実行時例外");
			}
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
	// getter and setter

	public String getInitCommand() {
		return initCommand;
	}

	public void setInitCommand(String initCommand) {
		this.initCommand = initCommand;
	}

	public ProcessBuilder getPb() {
		return pb;
	}

	public void setPb(ProcessBuilder pb) {
		this.pb = pb;
	}
}
