package com.chowlb.gcusedgear;


public class RssItem {
	private String description;
	private String link;
	private String image;
	private int guid;
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
	
	public int getGuid() {
		return this.guid;
	}
	
	public String toString() {
		return description;
	}
	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.guid = Integer.parseInt(image);
		this.image = "http://used.guitarcenter.com/images/products/thmb/"+image+"_th.jpg";
	}
	
}
