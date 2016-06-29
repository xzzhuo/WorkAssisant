package com.assistant.process;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.assistant.application.AssistantConfig;
import com.cooperate.data.AccountData;
import com.cooperate.table.AccountTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bdx.net.log.NetLog;
import bdx.net.netty.WebSocket;
import net.sf.json.JSONObject;

public class MyWebSocket extends WebSocket {

	private static final String TAG = "MyWebSocket";
	
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
			JSONObject jsonCommand = null;
			try
			{
				jsonObject = JSONObject.fromObject(message);
				jsonCommand = jsonObject.getJSONObject("command");
				NetLog.debug(TAG, jsonCommand.toString());
			} catch (Exception e) {
		        NetLog.error(TAG, e.getMessage());
		    }
			
			Command command = null;
			String strRespond = null;
			if (jsonCommand != null)
			{
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					command = objectMapper.readValue(jsonCommand.toString(), Command.class);

					strRespond = this.handleCommand(command);
					
					NetLog.debug(TAG, command.getName());
			    } catch (Exception e) {
			        NetLog.error(TAG, e.getMessage());
			    }
			}
			
			// JSONObject jsonObject = new JSONObject();
			// {"receive":{"name":"%s", "state":"%s"}", "data":"%s"}
			String jsonValue = null;
			if (strRespond != null)
			{
				jsonValue = String.format("{\"command\":{\"name\":\"%s\", \"state\":\"%s\"}, \"data\":%s}",
						command.getName(),
						"success",strRespond);
			}
			else
			{
				jsonValue = String.format("{\"command\":{\"name\":\"%s\", \"state\":\"%s\"}}",
						command.getName(),
						"failed");
			}
			
			this.sendMessage(address, jsonValue);
		}

	}

	private String handleCommand(Command command) {
		
		String jsonValue = null;
		
		if (command.getName().equals("GET_FRIND_LIST"))
		{
			jsonValue = this.getFrindList(command.getAccount());
		}
		else if (command.getName().equals("CHAT_CONTENT"))
		{
			jsonValue = "{\"nop\":\"nop\"}";
		}
		
		else
		{
			jsonValue = "{\"nop\":\"nop\"}";
		}
		
		return jsonValue;
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
