package com.assistant.data;

import java.util.Date;

import com.cooperate.template.StockTemplateData;

public class NotificationData extends StockTemplateData {

	private String title = "";
	private String nt_description = "";
	private String action1text = "";
	private String action1uri = "";
	private String severity = "";
	private Date startdate = new Date();
	private Date enddate = new Date();
	private String targetappname = "";
	private int nt_state = 0;		// 0-new, 1-burn, 2-delete
	
	public NotificationData()
	{
		startdate = new Date();
		enddate = new Date();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNt_description() {
		return nt_description;
	}

	public void setNt_description(String description) {
		this.nt_description = description;
	}

	public String getAction1text() {
		return action1text;
	}

	public void setAction1text(String action1_text) {
		this.action1text = action1_text;
	}

	public String getAction1uri() {
		return action1uri;
	}

	public void setAction1uri(String action1_uri) {
		this.action1uri = action1_uri;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date start_date) {
		this.startdate = start_date;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date end_date) {
		this.enddate = end_date;
	}

	public String getTargetappname() {
		return targetappname;
	}

	public void setTargetappname(String app_id) {
		this.targetappname = app_id;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public int getNt_state() {
		return nt_state;
	}

	public void setNt_state(int nt_state) {
		this.nt_state = nt_state;
	}
}
