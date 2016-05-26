package com.assistant.table;

import com.assistant.application.AssistantConfig;
import com.assistant.database.TableName1;
import com.cooperate.template.StockTemplateTable;

public class ImageViewTable extends StockTemplateTable {

	public ImageViewTable() {
		super(AssistantConfig.Instance().getDatabaseParam(),
				TableName1.getImageView(), 
				AssistantConfig.Instance().getDatabaseOldVersion(), 
				AssistantConfig.Instance().getDatabaseVersion());
	}

	@Override
	public String onAddFields() {
		String sql = "";
		
		sql += "image_identity VARCHAR(256) NOT NULL,";
		sql += "image_name VARCHAR(256) NOT NULL,";
		sql += "image_content VARCHAR(2048) DEFAULT '',";
		
		return sql;	
	}

}
