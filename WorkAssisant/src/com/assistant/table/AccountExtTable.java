package com.assistant.table;

import java.util.List;
import java.util.Map;

import com.cooperate.table.AccountTable;

public class AccountExtTable extends AccountTable {

	public List<Map<String, Object>> searchAccountName(String name) {
		// TODO Auto-generated method stub
		String sql = String.format("SELECT * FROM %s WHERE name LIKE '%%%s%%' OR account LIKE '%%%s%%'",
				this.getTableName(), name, name);
		return this.query(sql);
	}

}
