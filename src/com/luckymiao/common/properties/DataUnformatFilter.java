package com.luckymiao.common.properties;

import java.util.Stack;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.luckymiao.common.utils.LogUtils;

public class DataUnformatFilter extends XMLFilterBase {
	private static Logger logger = LogUtils.getLogger(DataUnformatFilter.class.getName());
	
	private static final Object SEEN_NOTHING = new Object();
	private static final Object SEEN_ELEMENT = new Object();
	private static final Object SEEN_DATA = new Object();

	private Object state = SEEN_NOTHING;
	private Stack stateStack = new Stack();

	private StringBuffer whitespace = new StringBuffer();
	

	public DataUnformatFilter() {
	}

	public DataUnformatFilter(XMLReader xmlreader) {
		super(xmlreader);
	}

	public void reset() {
		this.state = SEEN_NOTHING;
		this.stateStack = new Stack();
		this.whitespace = new StringBuffer();
	}

	public void startDocument() throws SAXException {
		reset();
		super.startDocument();
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		clearWhitespace();
		this.stateStack.push(SEEN_ELEMENT);
		this.state = SEEN_NOTHING;
		super.startElement(uri, localName, qName, atts);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (this.state == SEEN_ELEMENT)
			clearWhitespace();
		else {
			emitWhitespace();
		}
		this.state = this.stateStack.pop();
		super.endElement(uri, localName, qName);
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (this.state != SEEN_DATA) {
			int end = start + length;
			while (end-- > start) {
				if (!isXMLWhitespace(ch[end])) {
					break;
				}

			}

			if (end < start) {
				saveWhitespace(ch, start, length);
			} else {
				this.state = SEEN_DATA;
				emitWhitespace();
			}

		}

		if (this.state == SEEN_DATA)
			super.characters(ch, start, length);
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		emitWhitespace();
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		emitWhitespace();
		super.processingInstruction(target, data);
	}

	protected void saveWhitespace(char[] ch, int start, int length) {
		this.whitespace.append(ch, start, length);
	}

	protected void emitWhitespace() throws SAXException {
		char[] data = new char[this.whitespace.length()];
		if (this.whitespace.length() > 0) {
			this.whitespace.getChars(0, data.length, data, 0);
			this.whitespace.setLength(0);
			super.characters(data, 0, data.length);
		}
	}

	protected void clearWhitespace() {
		this.whitespace.setLength(0);
	}

	private boolean isXMLWhitespace(char c) {
		return (c == ' ') || (c == '\t') || (c == '\r') || (c == '\n');
	}

	private String toBlankString(int count) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < count; i++)
			buffer.append("    ");
		return buffer.toString();
	}
}
