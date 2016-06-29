package com.assistant.application;

import com.assistant.process.MyProcess;
import com.assistant.process.MyWebSocket;
import com.cooperate.interface1.CooperateApplication;

import bdx.net.log.NetLog;
import bdx.net.netty.NetProcess;

public class WorkAssisant extends CooperateApplication {

	private static final String TAG = "WorkAssisant";
	
	protected WorkAssisant() {
		super(AssistantConfig.Instance());
		super.registerWebsocket(new MyWebSocket());
	}

	@Override
	public boolean onInitLanguage()
	{
		super.onInitLanguage();
		WorkAssisant.getLangData().loadLanguage("language");
		
		return true;
	}
	
	@Override
	public NetProcess onGetProcess() {
		return new MyProcess();
	}

	public static void main(String[] args)
	{
		new WorkAssisant().run(args, "E6B4A85F8D30BEF8AD0D1C5465B1BBC9");
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		
		NetLog.info(TAG, "onStart()");
	}
}
