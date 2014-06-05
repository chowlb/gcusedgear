package com.chowlb.gcusedgear;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


public class RssParseHandler extends DefaultHandler{
	private List<RssItem> rssItems;
	private RssItem currentItem;
	private boolean parsingDescription;
	private boolean parsingLink;
	private boolean parsingImage;
	private StringBuffer descriptionSB;
	private StringBuffer linkSB;
	private StringBuffer imageSB;
	
	public RssParseHandler() {
		rssItems = new ArrayList<RssItem>();
	}
	
	public List<RssItem> getItems(){
		return rssItems;
	}
	
	//The startelement method creates an empty RssItem object when an item start tag is being processed.
		//When a description or link tag are being processed appropriate indicators are set to true.
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			
			if("item".equals(qName)) {
				currentItem = new RssItem();
			}else if("description".equals(qName)) {
				descriptionSB = new StringBuffer();
				parsingDescription = true;
			}else if("link".equals(qName)) {
				parsingLink = true;
				linkSB = new StringBuffer();
			}else if("guid".equals(qName)) {
				parsingImage = true;
				imageSB = new StringBuffer();
			}
		}
	

	// Characters method fills current RssItem object with data when title and link tag content is being processed
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(parsingDescription) {
			if(currentItem != null) {
				//Log.e("chowlb", "Parsing description");
				descriptionSB.append(new String(ch, start, length));
				
			}
		}else if(parsingLink) {
			if(currentItem != null) {
				//Log.e("chowlb", "Parsing link");
				linkSB.append(new String(ch, start, length));
			}
		}else if(parsingImage) {
			if(currentItem != null) {
				//Log.e("chowlb", "Parsing link");
				imageSB.append(new String(ch, start, length));
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if("item".equals(qName)) {
			rssItems.add(currentItem);
			//Log.e("chowlb", "RSS Item Added: List size:" +rssItems.size());
			currentItem = null;
		}else if("description".equals(qName)) {
			parsingDescription = false;
			if(currentItem != null) {
				currentItem.setDescription(descriptionSB.toString());
			}
		}else if("link".equals(qName)) {
			parsingLink = false;
			if(currentItem!=null) {
				currentItem.setLink(linkSB.toString());
				//Log.e("chowlb", "CALLING SET ITEM PRICE");
				//setItemPrice(currentItem);
			}
		}else if("guid".equals(qName)) {
			parsingImage = false;
			if(currentItem!=null) {
				currentItem.setImage(imageSB.toString());
			}
		}
	}

	
	
	
}
