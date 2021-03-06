package org.DataBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import old.SocketConnect.HttpConnection;

/**
 * ネットワーク接続でドキュメント型のデータベースを取得書き込みをするためのクラス
 * @author max
 *
 */
public class NetworkDocumentDatabase implements NoSQLDocumentFormatXml {

	/**
	 * HTTP接続用
	 */
	HttpConnection connection;
	/**
	 * ドキュメントを組み立てるためのもの
	 */
	DocumentBuilder docBuild;
	/**
	 * ドキュメント管理用
	 */
	Document document;
	/**
	 * DOC編集管理
	 */
	Element element;
	/**
	 * 要素内のパラメータデータ
	 */
	NodeList nodeList;
	
	/**
	 * コンストラクタ
	 * 初期設定
	 * @param url　ネットワークのデータベース位置のＦＱＤＮ
	 * @param option HTTPメソッド
	 * @exception ParserConfigurationException (このソフトウェアで発生しないのでわかりません)
	 * @exception SAXException (このソフトウェアで発生しないのでわかりません)
	 * @exception IOException 取得書き込みが不可能な場合発生
	 */
	public NetworkDocumentDatabase(String url, String option) throws ParserConfigurationException, SAXException, IOException {
		
		this.connection = new HttpConnection(url, option);
		this.docBuild = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		this.document = this.docBuild.parse(this.connection.getIs());
		this.element = this.document.getDocumentElement();
		this.nodeList = element.getChildNodes();
		
	}
	
	@Override
	public boolean addNode(Element elem, String[] key, String[] value, String... msg) {
		for(int i = 0; i < key.length & i < value.length; i++) {
			elem.setAttribute(key[i], value[i]);
		}
		elem.appendChild(document.createTextNode(msg[0]));
		// 現在のXMLに大して最後に追加する
		element.appendChild(elem);
		
		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			//e.printStackTrace();
			return false;
		}
		transformer.setOutputProperty("indent", "yes");
		transformer.setOutputProperty("encoding", "UTF-8");

		try {
			transformer.transform(new DOMSource(this.document), new StreamResult(this.connection.getOs()));
		} catch (TransformerException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public String[] getNodeNames() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < this.nodeList.getLength(); i++) {
			Node node = this.nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				list.add(element.getNodeName());
			}
		}
		return (String[])list.toArray();
	}

	@Override
	public HashMap<String, String> getNodes() {
		HashMap<String, String> nodes = new HashMap<String, String>();
		for(int i = 0; i < this.nodeList.getLength(); i++) {
			Node node = this.nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				nodes.put(element.getNodeName(), element.getTextContent());
			}
		}
		return nodes;
	}

	@Override
	public String getValue(String NodeName) {
		String value = "";
		for(int i = 0; i < this.nodeList.getLength(); i++) {
			Node node = this.nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (element.getNodeName().equals(NodeName)) {
					value = element.getTextContent();
					break;
				}
			}
		}
		return value;
	}

	// getter and setter
	public HttpConnection getConnection() {
		return connection;
	}

	public void setConnection(HttpConnection connection) {
		this.connection = connection;
	}

	public DocumentBuilder getDocBuild() {
		return docBuild;
	}

	public void setDocBuild(DocumentBuilder docBuild) {
		this.docBuild = docBuild;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public NodeList getNodeList() {
		return nodeList;
	}

	public void setNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}

}
