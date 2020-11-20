package Test.Debug;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlRequest {

	/**
	 * コメジェネデバッグ
	 * 
	 * @param args コメジェネのsetting.xmlの場所
	 */
	public static void main(String[] args) {

		try {

			DocumentBuilder docBuild = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Element element = docBuild.parse(new File(args[0])).getDocumentElement();
			NodeList nodeList = element.getChildNodes();

			String filePath = "";

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element name = (Element) node;
					if (name.getNodeName().equals("HcgPath")) {
						System.out.println(name.getTextContent());
						filePath = name.getTextContent();
					}
				}
			}
			
			File CG = new File(String.format("%s\\comment.xml", filePath));
			
			Document document = docBuild.parse(CG);
			element = document.getDocumentElement();

			// 読み取り
			nodeList = element.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element name = (Element) node;
					System.out.print(String.format("<%s", name.getNodeName()));
					NamedNodeMap nnm = name.getAttributes();
					for (int j = 0; j < nnm.getLength(); j++) {
						String nodeName = nnm.item(j).getNodeName();
						String value = nnm.item(j).getNodeValue();
						System.out.print(String.format(" %s=%s ", nodeName, value));
					}
					System.out.println(String.format("> : %s ", name.getTextContent()));
				}
			}
			// 試し書き
			Element debug = document.createElement("comment");
			debug.setAttribute("handle", "none");
			debug.setAttribute("no", "0");
			debug.setAttribute("owner", "0");
			debug.setAttribute("service", "debug");
			debug.setAttribute("time",
					new String(String.valueOf(new Date(System.currentTimeMillis()).getTime())));
			debug.appendChild(document.createTextNode("comment"));
			element.appendChild(debug);
			/*
			 * DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); try {
			 * DocumentBuilder builder = factory.newDocumentBuilder(); Document doc =
			 * builder.parse(new File(args[0]));
			 * 
			 * Element element = doc.getDocumentElement();
			 * 
			 * NodeList nodeList = element.getChildNodes();
			 * 
			 * System.out.println(((Element)nodeList.item(1)).getTextContent());
			 * 
			 * /* for (int i = 0; i < nodeList.getLength(); i++) { Node node =
			 * nodeList.item(i); if (node.getNodeType() == Node.ELEMENT_NODE) { Element name
			 * = (Element) node; System.out.println(name.getNodeName() + ": " +
			 * name.getTextContent());
			 * 
			 * } }
			 */
			createxml(CG, document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean createxml(File file, Document document) {
		Transformer transformer = null;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		transformer.setOutputProperty("indent", "yes");
		transformer.setOutputProperty("encoding", "UTF-8");

		try {
			transformer.transform(new DOMSource(document), new StreamResult(file));
		} catch (TransformerException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
