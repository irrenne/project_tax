package com.epam.tax.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXController {

    private final String xmlFileName;

    public SAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public ReportXml parseSAX() throws Exception {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler();
        parser.parse(xmlFileName, handler);

        return handler.emp;
    }
}

class SAXHandler extends DefaultHandler {

    ReportXml emp = null;
    String content = null;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if ("report".equals(qName)) {
            emp = new ReportXml();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "company" -> emp.setCompany(content);
            case "salary" -> emp.setSalary(Integer.parseInt(content));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }
}