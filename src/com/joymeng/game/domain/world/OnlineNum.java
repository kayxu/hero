package com.joymeng.game.domain.world;

public class OnlineNum {
	//new hot red green gray 
//	public static final byte SERVER_STATE_FULL =0;//人满
//	public static final byte SERVER_STATE_CROW =1;//拥挤
//	public static final byte SERVER_STATE_COMMON =2;//普通
//	public static final byte SERVER_STATE_FEW =3;//较少
//	status
	public static final byte SERVER_STATE_RED =0;//拥挤
	public static final byte SERVER_STATE_GREEN =1;//普通
	public static final byte SERVER_STATE_GRAY =2;//关闭
	
//	type
	public static final byte SERVER_NEW=0;
	public static final byte SERVER_HOT=1;
	public static final byte SERVER_NORMAL=2;
	
	long heartTime;//心跳
	int gameId;// 服务器instanceId
	int onlinePlayerNum;// 在线玩家数量
	int gameType;// 服务器类型（0-德州扑克,1-21点 ,2-老虎机,3-梭哈）
	String gameName;//服务器名称
	byte gameState;//服务器状态
	String dbUrl;//数据库url

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getOnlinePlayerNum() {
		return onlinePlayerNum;
	}

	public void setOnlinePlayerNum(int onlinePlayerNum) {
		this.onlinePlayerNum = onlinePlayerNum;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public byte getGameState() {
		return gameState;
	}

	public void setGameState(byte gameState) {
		this.gameState = gameState;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public long getHeartTime() {
		return heartTime;
	}

	public void setHeartTime(long heartTime) {
		this.heartTime = heartTime;
	}

}
