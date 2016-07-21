package com.assistant.process;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.assistant.application.AssistantConfig;
import com.cooperate.data.AccountData;
import com.cooperate.table.AccountTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bdx.net.log.NetLog;
import bdx.net.netty.WebSocket;
import net.sf.json.JSONObject;

public class MyWebSocket extends WebSocket {

	class Result {
		public SocketAddress address;
		public String jsonValue;
	}
	
	private static final String TAG = "MyWebSocket";
	
	private Map<String, SocketAddress> friendMap = new HashMap<String, SocketAddress>();
	
	public MyWebSocket() {
		super("websocket");
		
		NetLog.info(TAG, String.format("Address : ws://0.0.0.0:%d/%s",
				AssistantConfig.Instance().getServerPort(),
				this.getAddress()));
	}

	@Override
	public void onConnect(SocketAddress address) {
		NetLog.debug(TAG, "onConnect, Address : " + address);
	}

	@Override
	public void onDisconnect(SocketAddress address) {
		NetLog.debug(TAG, "onDisconnect, Address : " + address);

	}

	@Override
	public void onMessage(SocketAddress address, String message) {
		NetLog.debug(TAG, "onMessage, Message : " + message);
		
		if (message != null && !message.isEmpty())
		{
			JSONObject jsonObject = null;
			JSONObject jsonHeader = null;
			JSONObject jsonData = null;

			try
			{
				jsonObject = JSONObject.fromObject(message);
				jsonHeader = jsonObject.getJSONObject("header");
				jsonData = jsonObject.getJSONObject("data");
				NetLog.debug(TAG, jsonHeader.toString());
			} catch (Exception e) {
		        NetLog.error(TAG, e.getMessage());
		    }

			Header header = null;
			Message msg = null;
			Result respond = null;
			if (jsonHeader != null)
			{
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					header = objectMapper.readValue(jsonHeader.toString(), Header.class);
					msg = objectMapper.readValue(jsonData.toString(), Message.class);
					
					respond = this.handleCommand(address, header, msg);
					
					NetLog.debug(TAG, header.getMsgid());
			    } catch (Exception e) {
			        NetLog.error(TAG, e.getMessage());
			    }
			}
			
			// JSONObject jsonObject = new JSONObject();
			// {"receive":{"name":"%s", "state":"%s"}", "data":"%s"}
			String jsonValue = null;
			if (respond.jsonValue != null)
			{
				jsonValue = String.format("{\"command\":{\"name\":\"%s\", \"state\":\"%s\"}, \"data\":%s}",
						header.getMsgid(),
						"success",respond.jsonValue);
			}
			else
			{
				jsonValue = String.format("{\"command\":{\"name\":\"%s\", \"state\":\"%s\"}}",
						header.getMsgid(),
						"failed");
			}
			
			if (respond.address != null)
			{
				this.sendMessage(respond.address, jsonValue);
			}
			else
			{
				NetLog.warning("WebSocket", "Target is not exist");
			}
		}

	}

	private Result handleCommand(SocketAddress address, Header header, Message message) {
		
		Result result = new Result();
		
		if (header.getMsgid().equals("GET_FRIND_LIST"))
		{
			friendMap.put(header.getAccount(), address);
			result.jsonValue = this.getFrindList(header.getAccount());
			result.address = address;
		}
		else if (header.getMsgid().equals("CHAT_CONTENT"))
		{
			SocketAddress target = null;
			if (this.friendMap.containsKey(message.getTo()))
			{
				target = this.friendMap.get(message.getTo());
			}
			
			result.address = target;
			if (target != null)
			{
				result.jsonValue = String.format("{\"message\":\"%s\"}", message.getMessage());
			}
			else
			{
				result.jsonValue = null;
			}
		}
		
		else
		{
			result.jsonValue = "{\"nop\":\"nop\"}";
		}
		
		return result;
	}

	@Override
	public void onMessage(SocketAddress address, byte[] data) {
		NetLog.debug("onMessage", "Address : " + address);

	}

	private String getFrindList(String current_account) {

		AccountTable accountTable = new AccountTable();
		List<AccountData> list = accountTable.queryAllAccount();

		List<SampleAccount> listSample = new ArrayList<SampleAccount>();
		for (AccountData account : list)
		{
			SampleAccount newAccount = new SampleAccount();
			
			newAccount.setAccount(account.getAccount());
			newAccount.setAddress(account.getAddress());
			newAccount.setBirthday(account.getBirthday());
			newAccount.setName(account.getName());
			newAccount.setPhone(account.getPhone());
			newAccount.setPhoto(account.getPhoto());
			newAccount.setSex(account.getSex());
			newAccount.setUuid(account.getUuid());
			
			listSample.add(newAccount);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonValue = null;
		try {
			jsonValue = objectMapper.writeValueAsString(listSample);
		} catch (JsonProcessingException e) {
			NetLog.error(TAG, e.getMessage());
		}
		
		return jsonValue;
	}
}
