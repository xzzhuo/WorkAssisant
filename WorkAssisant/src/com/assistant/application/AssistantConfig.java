package com.assistant.application;

import com.cooperate.interface1.CooperateConfig;

import bdx.net.database.DatabaseParam;
import bdx.net.interface1.LogLevel;
import bdx.net.interface1.NetCharset;
import bdx.net.interface1.ServerType;

public class AssistantConfig extends CooperateConfig {

	private final int VERSION = 3;
	
	private DatabaseParam mDatabaseParam = new DatabaseParam();
	private final int mApplicationVersion = 1;
	private final int mDatabaseOldVersion = VERSION - 1;
	private final int mDatabaseVersion = VERSION;
	private final boolean mAllowAnonymous = true;

	private static AssistantConfig mInstance = new AssistantConfig();
	
	private AssistantConfig()
	{
		mDatabaseParam.setDatabaseName("assistant_web");
		mDatabaseParam.setUser("root");
		mDatabaseParam.setPassword("root");
		mDatabaseParam.setPort(3306);
		mDatabaseParam.setUrl("localhost");
	}
	
	public static AssistantConfig Instance()
	{
		return mInstance;
	}
	
	@Override
	public NetCharset getCharset() {
		return NetCharset.UTF_8;
	}

	@Override
	public LogLevel getLogLevel() {
		return LogLevel.Debug;
	}

	@Override
	public String getRootPath() {
		return "webpages";
	}

	@Override
	public int getServerPort() {
		return 8081;
	}

	@Override
	public String getTempPath() {
		return "temp";
	}

	public DatabaseParam getDatabaseParam()
	{
		return this.mDatabaseParam;
	}

	public int getApplicationVersion() {
		return this.mApplicationVersion;
	}
	
	public int getDatabaseOldVersion() {
		return this.mDatabaseOldVersion;
	}
	
	public int getDatabaseVersion() {
		return this.mDatabaseVersion;
	}
	
	public boolean getAllowAnonymous() {
		return this.mAllowAnonymous;
	}

	@Override
	public ServerType getServerType() {
		return ServerType.WEB_SERVER;
	}

	@Override
	public String getTemplatePath() {
		return "default";
	}
}
