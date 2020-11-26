package org.Datas;

/**
 * タブ文字を配列にするクラス
 * @author max
 *
 */
public class TabDatas {
	
	//引数なしコンストラクタ
	public TabDatas() {

	}
	
	/**
	 *  タブ文字を入れるためのもの
	 * @param line タブ文字
	 * @return　タブ文字を配列に直したもの
	 */
	public String[] TabInsert(String line) {
		return line.split("\t");
	}
	
}
