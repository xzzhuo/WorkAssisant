package com.assistant.table;

import com.assistant.application.AssistantConfig;
import com.assistant.database.TableName1;
import com.cooperate.template.StockTemplateTable;

public class MyNotificationTable extends StockTemplateTable {

	public MyNotificationTable() {
		super(AssistantConfig.Instance().getDatabaseParam(),
				TableName1.getNotification(), 
				AssistantConfig.Instance().getDatabaseOldVersion(), 
				AssistantConfig.Instance().getDatabaseVersion());
	}

	@Override
	public String onAddFields() {
		String sql = "";
		
		sql += "title VARCHAR(100) NOT NULL,";
		sql += "nt_description VARCHAR(40) DEFAULT '',";
		sql += "severity VARCHAR(40) DEFAULT '',";
		sql += "action1text VARCHAR(40) DEFAULT '',";
		sql += "action1uri VARCHAR(40) DEFAULT '',";
		sql += "startdate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,";
		sql += "enddate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,";
		sql += "nt_state INT DEFAULT '0',";
		sql += "targetappname VARCHAR(60) DEFAULT '',";
		
		return sql;
	}

}
