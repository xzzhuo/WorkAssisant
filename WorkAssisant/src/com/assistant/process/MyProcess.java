package com.assistant.process;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
		if (uri.equals("\\api\\Notification"))
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
	}
	
	@Override
	protected boolean doProcess(String client, String path, File tempFile, String act, Map<String, String> request) throws ShowErrorException {

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
		
		else
		{
			super.doProcess(client, path, tempFile, act, request);
		}
		
		return true;
	}

}
