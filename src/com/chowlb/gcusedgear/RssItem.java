package com.chowlb.gcusedgear;

public class RssItem {
	private String description;
	private String link;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = "http://used.guitarcenter.com/usedGear/" + link;
	}
	
	public String toString() {
		return description;
	}
	
}
