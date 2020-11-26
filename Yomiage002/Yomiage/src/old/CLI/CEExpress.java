package old.CLI;

import java.io.IOException;

/**
 * 
 * 現在使用している受け渡しクラス
 * 
 * @param	initCommand			初期コマンドが入っているフィールド
 * @param pb						コマンドプロンプトを実行するためのクラス
 * 
 */

public class CEExpress implements ConsoleExecution {

	private String initCommand;
	private ProcessBuilder pb;

	// constructer
	public CEExpress() {
		this.initCommand = ".\\ExtendFiles\\YomiageCPR.exe";
	}

	public CEExpress(String... args) {
		this.pb = new ProcessBuilder(args);
	}

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
