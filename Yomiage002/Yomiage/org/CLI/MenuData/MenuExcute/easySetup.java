package org.CLI.MenuData.MenuExcute;

import java.io.IOException;
import java.nio.charset.Charset;

import org.CLI.CEExpress;
import org.CLI.ConsoleExecution;
import org.Request.InitPropertiesFile;
import org.Request.UserRequest;

public class easySetup implements Runnable {

	// プロファイル
	private String properties;

	// データイニシャライズ
	private final String[][] dataInitialize = { { "all", "public", "party", "private", "team", "group" },
			{ "any", "PUBLIC", "PARTY", "REPLY", "GUILD", "GROUP" } };

	// プロファイルが存在するかどうか
	private InitPropertiesFile ipf;

	// ユーザ入力用
	private UserRequest ur;

	// 実行可能設定
	private boolean isExecution;
	// find text
	private final String findText;
	
	private CEExpress console;

	public easySetup() {
		this.ur = new UserRequest();
		this.ipf = new InitPropertiesFile(".\\ExtendFiles\\Yomiage.properties");
		this.isExecution = true;
		this.properties = "none";
		this.findText = new String( String.format("読み上げる対象はこのようになっています\n現在：%s\n詳細：\nexit=終了,\n"
				+ "none = なし,\n%s = any,\n%s = 全体チャット,\n%s = パーティーチャット,\n%s = プライベートチャット(wis),\n%s = チームチャット,\n%s = グループチャット"
				+ "\n変更する場合は、例の通りに入力してください >A[Enter] 複数入れる場合は[,]で区切ってください\n",
				this.properties, this.dataInitialize[0][0], this.dataInitialize[0][1],
				this.dataInitialize[0][2], this.dataInitialize[0][3], this.dataInitialize[0][4],
				this.dataInitialize[0][5]).getBytes(Charset.forName("UTF-16LE")),Charset.forName("UTF-16LE") );
		
		this.console = new CEExpress("cmd", "/u", "chcp", "65001", ">" , "NUL");
	}

	@Override
	public void run() {
		if (this.getExcution()) {
			this.setExecution(false);
			//this.console.ConsoleCommand();
			System.out.println(this.findText);
			this.properties = this.ur.UserInputRequest(">");
		}
		
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.setExecution(true);
		}
	}

	public String getProperties() {
		return this.properties;
	}

	public boolean getExcution() {
		return this.isExecution;
	}

	public void setExecution(boolean flg) {
		this.isExecution = flg;
	}

}
