package com.assistant.process;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bdx.net.log.NetLog;
import bdx.net.netty.NetFile;

import com.assistant.common.MyRoleType;
import com.assistant.data.ImageViewData;
import com.assistant.data.NotificationData;
import com.assistant.table.ImageViewTable;
import com.assistant.table.MyNotificationTable;
import com.cooperate.common.ShowErrorException;
import com.cooperate.data.BaseData;
import com.cooperate.process.CooperateProcess;
import com.cooperate.template.RoleTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyProcess extends CooperateProcess {

	private Map<String, RoleTemplate> mMyRoleTemplate = null;

	@Override
	protected Map<String, RoleTemplate> onInitRoleTemplate()
	{
		super.onInitRoleTemplate();
		
		mMyRoleTemplate = new HashMap<String, RoleTemplate>();
		RoleTemplate temp = null;
		
		temp = new RoleTemplate();
		temp.setTemplate_type(MyRoleType.NOTIFICATION.name());
		temp.setTemplate_type_name(this.mCurLangMap.get(MyRoleType.NOTIFICATION.name()));
		temp.setShow_list_title("Notification List");
		temp.setShow_list_url("notification_list.html?act=menu_show_notification_list");
		temp.setAdd_item_title("Add New Notification");
		temp.setAdd_item_url("notification_add.html?act=menu_notification_add");
		temp.setAdd_item_command("act_notification_add");
		temp.setDelete_item_command("act_notification_delete");
		mMyRoleTemplate.put(MyRoleType.NOTIFICATION.name(), temp);
		
		temp = new RoleTemplate();
		temp.setTemplate_type(MyRoleType.IMAGE_VIEW.name());
		temp.setTemplate_type_name(this.mCurLangMap.get(MyRoleType.IMAGE_VIEW.name()));
		temp.setShow_list_title("Image List");
		temp.setShow_list_url("image_list.html?act=menu_show_image_list");
		temp.setAdd_item_title("Add New Image");
		temp.setAdd_item_url("image_add.html?act=menu_image_add");
		temp.setAdd_item_command("act_image_add");
		temp.setDelete_item_command("act_image_delete");
		mMyRoleTemplate.put(MyRoleType.IMAGE_VIEW.name(), temp);
		
		return mMyRoleTemplate;
	}
	
	@Override
	protected void onErrorNotFind(String client, String uri)
	{
		NetLog.debug(client, this.getUri());
		
		if (uri.equals("\\api\\Notification") || uri.equals("\\api\\Notification\\"))
		{
			MyNotificationTable tableNotification = new MyNotificationTable();
			List<Map<String, Object>> list = tableNotification.queryAllData();
			
			JsonParser parserJson = new JsonParser();
			JsonArray arrayJson = new JsonArray();
			
			for (Map<String, Object> map: list)
			{
				Gson gson = new Gson();
				String text = null;
				try {
					map = NotificationAdapter(map);
					text = gson.getAdapter(Map.class).toJson(map);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				arrayJson.add(parserJson.parse(text));
			}
			
			JsonObject objJson = new JsonObject();
			objJson.add("Notifications", arrayJson);
			//objJson.add("ArchivedIds", arcIds);
			// {"":["":[1573]}
			
			this.print(objJson.toString());
			
			//String temp = "{\"Notifications\":[{\"Id\":1678,\"Title\":\"AFDGAD\",\"Description\":\"GADFGFGD\",\"ShortDescription\":null,\"TargetAppName\":\"C7AFC9C6D424658344A30FD4C1772B55\",\"NotificationType\":null,\"Category\":null,\"Severity\":\"Optional\",\"Action1Text\":null,\"Action1Uri\":null,\"Action2Text\":null,\"Action2Uri\":null,\"Action1ActionType\":null,\"State\":null,\"Action2ActionType\":null,\"StartDate\":\"2016-06-13T10:32:30\",\"EndDate\":\"2016-06-13T10:32:31\",\"OOBECompleteDate\":\"1900-01-01T00:00:00\"},{\"Id\":1679,\"Title\":\"HAIMEI\",\"Description\":\"HAIMEI\",\"ShortDescription\":null,\"TargetAppName\":\"C7AFC9C6D424658344A30FD4C1772B55\",\"NotificationType\":null,\"Category\":null,\"Severity\":\"Optional\",\"Action1Text\":null,\"Action1Uri\":null,\"Action2Text\":null,\"Action2Uri\":null,\"Action1ActionType\":null,\"State\":null,\"Action2ActionType\":null,\"StartDate\":\"2016-06-13T10:34:32\",\"EndDate\":\"2016-06-23T10:34:33\",\"OOBECompleteDate\":\"1900-01-01T00:00:00\"},{\"Id\":1684,\"Title\":\"haimei123\",\"Description\":\"haimei123\",\"ShortDescription\":null,\"TargetAppName\":\"C7AFC9C6D424658344A30FD4C1772B55\",\"NotificationType\":null,\"Category\":null,\"Severity\":\"Optional\",\"Action1Text\":null,\"Action1Uri\":null,\"Action2Text\":null,\"Action2Uri\":null,\"Action1ActionType\":null,\"State\":null,\"Action2ActionType\":null,\"StartDate\":\"2016-06-13T16:03:42\",\"EndDate\":\"2016-06-18T16:03:43\",\"OOBECompleteDate\":\"1900-01-01T00:00:00\"},{\"Id\":1687,\"Title\":\"etyetyety\",\"Description\":\"rtyrtyr\",\"ShortDescription\":null,\"TargetAppName\":\"C7AFC9C6D424658344A30FD4C1772B55\",\"NotificationType\":null,\"Category\":null,\"Severity\":\"Optional\",\"Action1Text\":null,\"Action1Uri\":null,\"Action2Text\":null,\"Action2Uri\":null,\"Action1ActionType\":null,\"State\":null,\"Action2ActionType\":null,\"StartDate\":\"2016-06-13T16:30:30\",\"EndDate\":\"2016-06-17T16:30:31\",\"OOBECompleteDate\":\"1900-01-01T00:00:00\"}],\"ArchivedIds\":[]}";
			//this.print(temp);
		}
		else
		{
			super.onErrorNotFind(client, uri);
		}
		
		/*
		if (!uri.equals("\\api\\Notification"))
		{
			Date endDate = new Date();
			endDate.setDate(endDate.getDate()+1);
			
			Map<String, Object> map = new HashMap<String, Object>();
			//map.put("Id", (int)(Math.random()*1000));
			map.put("Id", 1000);
			map.put("Title", "new 1");
			map.put("Description", "new 1");
			map.put("ShortDescription", "null");
			
			map.put("TargetAppName", "null");
			map.put("NotificationType", "null");
			map.put("Category", "null");
			map.put("Severity", "Critical");
			map.put("Action1Text", "Check");
			map.put("Action1Uri", "new 1");
			map.put("Action2Text", "null");
			map.put("Action2Uri", "null");
			
			map.put("Action1ActionType", "null");
			map.put("State", "null");
			map.put("Action2ActionType", "null");
			map.put("StartDate", new Date());
			map.put("EndDate", endDate);
			
			map.put("OOBECompleteDate", new Date());
			
			Gson gson = new Gson();
			String text = null;
			try {
				text = gson.getAdapter(Map.class).toJson(map);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			JsonParser parserJson = new JsonParser();
	
			JsonArray arrayJson = new JsonArray();
			arrayJson.add(parserJson.parse(text));
			
			JsonObject objJson = new JsonObject();
			objJson.add("Notifications", arrayJson);
			//objJson.add("ArchivedIds", arcIds);
			// {"":["":[1573]}
			
			this.print(objJson.toString());
		}
		else
		{
			super.onErrorNotFind(client, uri);
		}
		*/
	}
	
	private Map<String, Object> NotificationAdapter(Map<String, Object> map) {
		
		Map<String, Object> retval = new HashMap<String, Object>();
		
		//private int nt_state = 0;		// 0-new, 1-burn, 2-delete
		
		for(Entry<String, Object> entry :  map.entrySet())
		{
			if (entry.getKey().equals("id"))
			{
				retval.put("Id", entry.getValue());
			}
			
			if (entry.getKey().equals("title"))
			{
				retval.put("Title", entry.getValue());
			}
			
			if (entry.getKey().equals("nt_description"))
			{
				retval.put("Description", entry.getValue());
			}

			if (entry.getKey().equals("targetappname"))
			{
				retval.put("TargetAppName", entry.getValue());
			}

			if (entry.getKey().equals("severity"))
			{
				retval.put("Severity", entry.getValue());
			}
			
			if (entry.getKey().equals("action1text"))
			{
				retval.put("Action1Text", entry.getValue());
			}
			
			if (entry.getKey().equals("action1uri"))
			{
				retval.put("Action1Uri", entry.getValue());
			}
			
			if (entry.getKey().equals("startdate"))
			{
				retval.put("StartDate", entry.getValue());
			}
			
			if (entry.getKey().equals("enddate"))
			{
				retval.put("EndDate", entry.getValue());
			}
			
			retval.put("ShortDescription", "null");
			retval.put("NotificationType", "null");
			retval.put("Category", "null");
			retval.put("Action2Text", "null");
			retval.put("Action2Uri", "null");
			
			retval.put("Action1ActionType", "null");
			retval.put("State", "null");
			retval.put("Action2ActionType", "null");

			retval.put("OOBECompleteDate", new Date());
		}

		return retval;
	}

	@Override
	protected boolean doProcess(String client, String path, File tempFile, String act, Map<String, String> request) throws ShowErrorException {

		NetLog.debug(client, this.getUri());
		// int a = tempFile.hashCode();

		if (act.equals("menu_show_notification_list"))
		{
			MyNotificationTable tableNotification = new MyNotificationTable();
			this.templateShowListPage(client, tempFile, request, tableNotification, MyRoleType.NOTIFICATION.name());
		}
		else if (act.equals("menu_notification_add"))
		{
			this.templateShowAddPage(client, tempFile, request, MyRoleType.NOTIFICATION.name());
		}
		else if (act.equals("act_notification_add"))
		{
			MyNotificationTable tableNotification = new MyNotificationTable();

			Map<String, Object> value = BaseData.filterMapData(NotificationData.class, request);
			this.templateAddData(client, request, tableNotification, value, MyRoleType.NOTIFICATION.name());
		}
		else if (act.equals("act_notification_delete"))
		{
			MyNotificationTable tableNotification = new MyNotificationTable();
			this.templateSetDeletedState(client, request, tableNotification, MyRoleType.NOTIFICATION.name());
		}
		else if (this.getUri().equals("/Notification/nt.html"))
		{
			
		}

		else if (act.equals("menu_show_image_list"))
		{
			ImageViewTable tableImageView = new ImageViewTable();
			this.templateShowListPage(client, tempFile, request, tableImageView, MyRoleType.IMAGE_VIEW.name());
		}
		else if (act.equals("menu_image_add"))
		{
			this.templateShowAddPage(client, tempFile, request, MyRoleType.IMAGE_VIEW.name());
		}
		else if (act.equals("act_image_add"))
		{
			NetFile netFile = this.getFile("image_file");

			if (netFile != null && !netFile.name.isEmpty())
			{
				ImageViewTable tableImageView = new ImageViewTable();
				Map<String, Object> value = ImageViewData.filterMapData(ImageViewData.class, request);
				
				NetLog.error("File", netFile.name);
				value.put("image_identity", netFile.tmp_name);
				value.put("image_name", netFile.name);
				//File f = new File(netFile.tmp_name);
				//f.renameTo(dest);
				
				this.templateAddData(client, request, tableImageView, value, MyRoleType.IMAGE_VIEW.name());
			}
			else
			{
				NetLog.error(client, "File is not found");
				String retval = this.MakeJsonReturn("failed", "File is not found");
				this.print(retval);
			}
		}
		else if (act.equals("act_image_delete"))
		{
			ImageViewTable tableImageView = new ImageViewTable();
			this.templateSetDeletedState(client, request, tableImageView, MyRoleType.IMAGE_VIEW.name());
		}
		else if (act.equals("menu_chat"))
		{
			if (request.containsKey("account") && request.containsKey("name"))
			{
				String account = request.get("account");
				String name = request.get("name");
				
				this.mWebUtil.assign("account", account);
				this.mWebUtil.assign("name", name);
				this.mWebUtil.display("websocket.html");
			}
			else
			{
				this.print("current account is not exist");
			}
		}
		
		else
		{
			super.doProcess(client, path, tempFile, act, request);
		}
		
		return true;
	}

}
