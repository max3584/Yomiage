package org.CLI;

import java.io.IOException;

/*  
 * 
 * 没クラス
 * 理由：ソケット通信のほうが絶対きれい！
 * 
 * */

public class CEExpress implements ConsoleExecution {

	private String initCommand;
	private ProcessBuilder pb;

	// constructer
	public CEExpress() {
		this.initCommand = ".\\ExtendFiles\\YomiageRequest.exe";
	}

	public CEExpress(String... args) {
		this.pb = new ProcessBuilder(args);
	}

	@Override
	public void ConsoleCommand(String msg) {
		this.pb = new ProcessBuilder(this.initCommand, "\"" + msg + "\"");

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

	public void ConsoleCommand() throws IOException, InterruptedException {
		if (this.pb.inheritIO().start().waitFor() != 0) {
			throw new IOException("実行時例外");
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
