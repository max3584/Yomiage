package org.Request;

import java.util.Scanner;

/**
 * ユーザ入力要求用クラス
 * @author max
 *
 */
public class UserRequest {

	/**
	 * 入力を行うためのスキャナークラス
	 */
	private Scanner sc ;
	
	/**
	 * 初期化
	 */
	public UserRequest() {
		this.sc = new Scanner(System.in);
	}
	
	/**
	 * ユーザ入力用関数
	 * @param msg ユーザにどんな情報を入力したほしいのかといったメッセージ情報
	 * @return 入力されたデータ
	 */
	public String UserInputRequest(String msg) {
		System.out.print(msg);
		return sc.nextLine();
	}
}
