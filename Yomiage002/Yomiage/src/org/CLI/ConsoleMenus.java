package org.CLI;

public interface ConsoleMenus {

	/**
	 * メニューバーの表示を行うためのもの
	 */
	public abstract  void PrintMenu();
	/**
	 * 
	 *  メニューバーを選択した場合に、使用されるメソッド
	 * 
	 * @param use 主に、メニューバーにあるものを入力する
	 * @return 実行官僚文字を出力
	 */
	public abstract void CallMenu(String use);
}
