package com.luckymiao.common.properties;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.springframework.core.io.Resource;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DOMEngine {
	private Resource configLocation = null;
	private Resource[] configLocations = null;
	private List configLocationList = null;

	public static Document createDocument() {
		Document commonRoot = null;
		try {
			commonRoot = (Document) Class.forName(
					"org.apache.xerces.dom.DocumentImpl").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commonRoot;
	}

	public static Element createElement(Node node, String elemName,
			String elemValue) {
		Document doc = node.getOwnerDocument();
		Element elem = doc.createElement(elemName);
		Node textnode = doc.createTextNode(elemName);
		textnode.setNodeValue(elemValue);
		elem.appendChild(textnode);
		node.appendChild(elem);
		return elem;
	}

	public static Element createElement(Node node, String elemName) {
		Document doc = node.getOwnerDocument();
		Element elem = doc.createElement(elemName);
		node.appendChild(elem);
		return elem;
	}

	public static Element createRootElement(Document doc, String rootName) {
		Element elem = doc.createElement(rootName);
		doc.appendChild(elem);
		return elem;
	}

	public static String getTextByTagName(Element parent, String tagname) {
		try {
			NodeList list = parent.getElementsByTagName(tagname);
			if (list == null)
				return null;
			Element element = (Element) list.item(0);
			Node textnode = element.getFirstChild();
			return textnode.getNodeValue();
		} catch (Exception e) {
		}
		return "";
	}

	public static Element createRootElement(Document doc, String rootName,
			String rootValue) {
		Element elem = doc.createElement(rootName);
		Node textnode = doc.createTextNode(rootName);
		textnode.setNodeValue(rootValue);
		elem.appendChild(textnode);
		doc.appendChild(elem);
		return elem;
	}

	public static void createAttribute(Element elem, String attrName,
			String attrValue) {
		Document doc = elem.getOwnerDocument();
		Attr attr = doc.createAttribute(attrName);
		attr.setNodeValue(attrValue);
		elem.setAttribute(attr.getNodeName(), attr.getNodeValue());
	}

	public static Document parseDoc(String sDocName) {
		Document theDocument = null;
		DOMParser parser = new DOMParser();
		try {
			parser.setFeature("http://xml.org/sax/features/validation", false);
			parser.parse(sDocName.trim());
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		theDocument = parser.getDocument();
		return theDocument;
	}

	public static void dispDoc(Document doc, String fileName) {
		try {
			PrintWriter xmlout = new PrintWriter(new FileWriter(fileName));
			XMLSerializer xl = new XMLSerializer(xmlout, new OutputFormat(doc,
					"UTF-8", true));

			xl.serialize(doc);
			xmlout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void dispDoc(Document doc) {
		try {
			XMLSerializer xl1 = new XMLSerializer(System.out, new OutputFormat(
					doc, "GBK", true));

			xl1.serialize(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getTextFromNodeChild(Node node, String name) {
		String retStr = null;
		if (node != null) {
			NodeList nodeList = node.getChildNodes();
			if (nodeList != null) {
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node childNode = nodeList.item(i);
					if (childNode.getNodeName().equals(name)) {
						retStr = getTextFromNode(childNode);
						return retStr;
					}

					retStr = getTextFromNodeChild(childNode, name);
				}
			}
		}

		return retStr;
	}

	public static String getTextFromNode(Node node) {
		StringBuffer returnStr = new StringBuffer();
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node childNode = nodeList.item(i);
				switch (childNode.getNodeType()) {
				case 3:
					returnStr.append(childNode.getNodeValue());
					break;
				case 6:
					returnStr.append(getTextFromEntity((Entity) childNode));
				}
			}
		} else {
			returnStr.setLength(0);
		}
		return returnStr.toString().trim();
	}

	public static String getTextFromEntity(Entity entity) {
		StringBuffer returnStr = new StringBuffer();
		if (entity.getNodeType() == 6) {
			NodeList entityList = entity.getChildNodes();
			if (entityList != null) {
				for (int i = 0; i < entityList.getLength(); i++) {
					Node childNode = entityList.item(i);
					switch (childNode.getNodeType()) {
					case 3:
						returnStr.append(childNode.getNodeValue());
						break;
					case 6:
						returnStr.append(getTextFromEntity((Entity) childNode));
					}
				}

			} else {
				returnStr.setLength(0);
			}
		}
		return returnStr.toString().trim();
	}

	public static Node getNodeByValue(Node root, String name, String value) {
		Node rtNode = null;

		String tmp = getTextFromNodeChild(root, name);
		if ((tmp != null) && (tmp.equals(value)))
			return root;
		if (root.hasChildNodes()) {
			rtNode = getNodeByValue(root.getFirstChild(), name, value);
			if (rtNode != null)
				return rtNode;
		}
		if (root.getNextSibling() != null) {
			rtNode = getNodeByValue(root.getNextSibling(), name, value);
			if (rtNode != null)
				return rtNode;
		}
		return null;
	}

	public static NodeList getNodesByTagname(Document doc, String tagname) {
		return doc.getElementsByTagName(tagname);
	}

	public static int getAmountByTagname(Document doc, String tagname) {
		return doc.getElementsByTagName(tagname).getLength();
	}

	public static Node getNodeByTagname(Document doc, String tagname, int number) {
		NodeList nl = doc.getElementsByTagName(tagname);
		if ((nl.getLength() >= number) && (number > 0)) {
			number--;
			return nl.item(number);
		}
		return null;
	}

	public static Node getFirstNodeByTagname(Document doc, String tagname) {
		NodeList nl = doc.getElementsByTagName(tagname);
		if (nl.getLength() > 0) {
			return nl.item(0);
		}
		return null;
	}

	public static Node getLastNodeByTagname(Document doc, String tagname) {
		NodeList nl = doc.getElementsByTagName(tagname);
		int iIndex = 0;
		if ((iIndex = nl.getLength()) > 0) {
			iIndex--;
			return nl.item(iIndex);
		}
		return null;
	}

	public static Node getFirstNodeByTagname(Element elem, String tagname) {
		NodeList nl = elem.getElementsByTagName(tagname);
		int iIndex = 0;
		if ((iIndex = nl.getLength()) > 0) {
			iIndex--;
			return nl.item(iIndex);
		}
		return null;
	}

	public static String getAttributeValue(Node node, String attributeName) {
		if ((node != null) && (node.getNodeType() == 1)) {
			Element elem = (Element) node;
			return elem.getAttribute(attributeName);
		}
		return null;
	}

	public static String XML2String(Document doc) {
		StringWriter sw = new StringWriter();
		try {
			XMLSerializer xl = new XMLSerializer(sw, new OutputFormat(doc,
					"UTF-8", true));

			xl.serialize(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sw.toString();
	}

	public static Document strToXML(String s) throws Exception {
		String str_tmp = s.trim();

		Document theDocument = null;
		StringReader sr = new StringReader(s);
		DOMParser parser = new DOMParser();
		try {
			InputSource is = new InputSource(sr);
			parser.parse(is);
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		theDocument = parser.getDocument();
		return theDocument;
	}

	public Document StreamToXML() throws Exception {
		InputStream inputStream = this.configLocation.getInputStream();
		InputSource inputSource = new InputSource();

		inputSource.setByteStream(inputStream);

		Document theDocument = null;
		DOMParser parser = new DOMParser();
		try {
			parser.parse(inputSource);
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		theDocument = parser.getDocument();
		return theDocument;
	}

	public Resource getConfigLocation() {
		return this.configLocation;
	}

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public List getConfigLocationList() {
		return this.configLocationList;
	}

	public void setConfigLocationList(List configLocationList) {
		this.configLocationList = configLocationList;
	}

	public Resource[] getConfigLocations() {
		return this.configLocations;
	}

	public void setConfigLocations(Resource[] configLocations) {
		this.configLocations = configLocations;
	}
}
