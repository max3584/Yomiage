package org.DataBase;

import java.io.File;
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

/**
 * 
 * ドキュメント型データベース用
 * 
 * @author max
 *
 * @param filePath FileClass
 * @param docBuild DocumentBuilderClass
 * @param element ElementClass
 * @param NodeList NodeListClass
 *
 */

public class DocumentDatabase {

	File filePath;
	DocumentBuilder docBuild;
	Document document;
	Element element;
	NodeList nodeList;
	
	public DocumentDatabase(String url) throws ParserConfigurationException, SAXException, IOException {
		this.filePath = new File(url);
		this.docBuild = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		this.document = this.docBuild.parse(new File(url));
		this.element = this.document.getDocumentElement();
		this.nodeList = element.getChildNodes();
	}
	
	/**
	 * ノードを追加
	 * @param doc ノードが入ったブロック
	 * @return boolean 成功した場合は true, それ以外はfalse
	 */
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
			transformer.transform(new DOMSource(this.document), new StreamResult(this.filePath.getPath()));
		} catch (TransformerException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*
	 *  ノードを取得する場合に使用
	 */
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
	
	/*
	 * ノードと値を取得
	 */
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
	
	/**
	 * 探索法：線形探索法
	 * ノードの値を取得
	 * @param NodeName
	 * @return ノードの場所にある値を取得します
	 */
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

	/* getter and setter */
	public DocumentBuilder getDocBuild() {
		return docBuild;
	}

	public void setDocBuild(DocumentBuilder docBuild) {
		this.docBuild = docBuild;
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

	public File getFilePath() {
		return filePath;
	}

	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}
