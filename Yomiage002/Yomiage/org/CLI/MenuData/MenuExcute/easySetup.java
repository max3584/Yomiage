package org.CLI.MenuData.MenuExcute;

import java.io.IOException;
import java.util.Arrays;

import org.CLI.CEExpress;
import org.Request.InitPropertiesFile;
import org.Request.UserRequest;

public class easySetup implements Runnable {

	// プロファイル
	private String properties;

	// データイニシャライズ
	private final String[][] dataInitialize = { { "all", "public", "party", "private", "team", "group", "none" },
			{ "any", "PUBLIC", "PARTY", "REPLY", "GUILD", "GROUP", "NONE" } };

	// プロファイルが存在するかどうか
	private InitPropertiesFile ipf;

	// ユーザ入力用
	private UserRequest ur;

	// 実行可能設定
	private boolean isExecution;
	// find text
	private String findText;

	private CEExpress console;

	public easySetup() {
		this.ur = new UserRequest();
		this.ipf = new InitPropertiesFile(".\\ExtendFiles\\Yomiage.properties");
		this.isExecution = true;
		this.properties = "none";
		this.TextEditter(this.properties, this.dataInitialize[0][0], this.dataInitialize[0][1],
				this.dataInitialize[0][2], this.dataInitialize[0][3], this.dataInitialize[0][4],
				this.dataInitialize[0][5]);

		this.console = new CEExpress("cmd", "/c", "cls");
	}

	@Override
	public void run() {
		if (this.getExcution()) {
			this.setExecution(false);
			// this.console.ConsoleCommand();
			this.TextEditter(this.properties, this.dataInitialize[0][0], this.dataInitialize[0][1],
					this.dataInitialize[0][2], this.dataInitialize[0][3], this.dataInitialize[0][4],
					this.dataInitialize[0][5]);
			System.out.println(this.findText);
			String use = this.ur.UserInputRequest(">");
			int num = Arrays.asList(this.dataInitialize[0]).indexOf(use);
			if (num > -1) {
				this.ipf.Change(this.dataInitialize[1][num]);
				this.properties = this.ipf.getProperties().getProperty("Read");
			} else if ("exit".equals(use)) {
				this.properties = "exit";
			} else {
				System.out.println("コマンドを間違えています。[Enter]を押すと流れます");
				this.ur.UserInputRequest("");
			}

		}

		try {
			this.console.ConsoleCommand();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.setExecution(true);
		}
	}

	public void TextEditter(String... args) {
		this.findText = String.format("読み上げる対象はこのようになっています\n現在：%s\n詳細：\nexit=終了,\n"
				+ "none = なし,\n%s = any,\n%s = 全体チャット,\n%s = パーティーチャット,\n%s = プライベートチャット(wis),\n%s = チームチャット,\n%s = グループチャット"
				+ "\n変更する場合は、例の通りに入力してください >A[Enter] 複数入れる場合は[,]で区切ってください\n", args[0], args[1], args[2],
				args[3], args[4], args[5], args[6]);
	}

	// getter and setter
	public String getProperties() {
		return this.properties;
	}

	public boolean getExcution() {
		return this.isExecution;
	}

	public void setExecution(boolean flg) {
		this.isExecution = flg;
	}

	public InitPropertiesFile getIpf() {
		return ipf;
	}

	public void setIpf(InitPropertiesFile ipf) {
		this.ipf = ipf;
	}

	public UserRequest getUr() {
		return ur;
	}

	public void setUr(UserRequest ur) {
		this.ur = ur;
	}

	public String getFindText() {
		return findText;
	}

	public void setFindText(String findText) {
		this.findText = findText;
	}

	public CEExpress getConsole() {
		return console;
	}

	public void setConsole(CEExpress console) {
		this.console = console;
	}

	public String[][] getDataInitialize() {
		return dataInitialize;
	}

	public boolean isExecution() {
		return isExecution;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

}
