package org.seasar.framework.xml;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;

import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.SAXParserFactoryUtil;
import org.seasar.framework.util.SAXParserUtil;
import org.xml.sax.InputSource;

public final class SaxHandlerParser {

	private SaxHandler saxHandler_;
	private SAXParser saxParser_;

	public SaxHandlerParser(SaxHandler saxHandler) {
		this(saxHandler, SAXParserFactoryUtil.newSAXParser());
	}

	public SaxHandlerParser(SaxHandler saxHandler, SAXParser saxParser) {
		saxHandler_ = saxHandler;
		saxParser_ = saxParser;
	}
	
	public SaxHandler getSaxHandler() {
		return saxHandler_;
	}
	
	public SAXParser getSAXParser() {
		return saxParser_;
	}

	public Object parse(String path) {
		return parse(ResourceUtil.getResourceAsStream(path));
	}

	public Object parse(InputStream inputStream) {
		return parse(new InputSource(inputStream));
	}

	public Object parse(InputSource inputSource) {
		SAXParserUtil.parse(saxParser_, inputSource, saxHandler_);
		return saxHandler_.getResult();
	}
}
