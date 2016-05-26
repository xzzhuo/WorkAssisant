package com.assistant.data;

import com.cooperate.template.StockTemplateData;

public class ImageViewData extends StockTemplateData {

	private String image_identity = "";
	private String image_name = "";
	private String image_content = "";
	
	public String getImage_identity() {
		return image_identity;
	}
	public void setImage_identity(String image_identity) {
		this.image_identity = image_identity;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	public String getImage_content() {
		return image_content;
	}
	public void setImage_content(String image_content) {
		this.image_content = image_content;
	}
}
