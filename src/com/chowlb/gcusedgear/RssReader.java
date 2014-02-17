package com.chowlb.gcusedgear;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssReader {
	private String rssUrl;
	
	public RssReader(String rssUrl) {
		this.rssUrl = rssUrl;
	}
	
	public String getUrl() {
		return rssUrl;
	}
	
	public List<RssItem> getItems() throws Exception{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		RssParseHandler handler = new RssParseHandler();
		saxParser.parse(rssUrl,  handler);
		return handler.getItems();
	}
	
}
