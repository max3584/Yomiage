package org.DataBase;

import java.util.HashMap;

import org.w3c.dom.Element;

public interface NoSQLDocumentFormatXml {

	/**
	 * ノードを追加
	 * @param elem ドキュメント構成要素
	 * @param key プロパティ指定
	 * @param value プロパティの値
	 * @param msg 要素内のテキスト設定
	 * @return boolean 成功した場合は true, それ以外はfalse
	 */
	public boolean addNode(Element elem, String[] key, String[] value, String... msg);
	
	/**
	 *  ノードを取得する場合に使用
	 *  @return ノードの値が返ってきます
	 */
	public String[] getNodeNames();
	
	/**
	 * ノードと値を取得
	 * @return ノードと値が返ってきます
	 */
	public HashMap<String, String> getNodes();
	
	/**
	 * 探索法：線形探索法
	 * ノードの値を取得
	 * @param NodeName
	 * @return ノードの場所にある値を取得します
	 */
	public String getValue(String NodeName);

}
