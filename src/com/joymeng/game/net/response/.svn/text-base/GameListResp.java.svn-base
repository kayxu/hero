package com.joymeng.game.net.response;

import java.util.ArrayList;
import java.util.List;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.world.OnlineNum;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyModuleMessage;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.message.JoyResponse;


public class GameListResp extends JoyModuleMessage
{
	List<OnlineNum> gameOnlineList;
	byte result;
	int errorCode;
	public GameListResp()
	{
		super(ProcotolType.GAME_LIST_RESP);
	}
	


	@Override
	protected void _serialize(JoyBuffer out)
	{
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_SUCCESS){
			out.putInt(gameOnlineList.size());
			for(OnlineNum online:gameOnlineList){
				out.putInt(online.getGameId());// 服务器instanceId
				out.putInt(online.getGameType());// 服务器类型
				out.put(online.getGameState());//服务器状态
				out.putInt(online.getOnlinePlayerNum());// 在线玩家数量
				out.putPrefixedString(online.getGameName(),JoyBuffer.STRING_TYPE_SHORT);
//				out.put(online.getGameState());
			}
		}
	}
	
	
	@Override
	protected void _deserialize(JoyBuffer in) 
	{
		result=in.get();
		errorCode=in.getInt();
		if(result==GameConst.GAME_RESP_SUCCESS){
			gameOnlineList=new ArrayList<OnlineNum>();
			int num=in.getInt();
			for(int i=0;i<num;i++){
				OnlineNum on=new OnlineNum();
				on.setGameId(in.getInt());
				on.setGameType(in.getInt());
				on.setGameState(in.get());
				on.setOnlinePlayerNum(in.getInt());
				on.setGameName(in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT));
				gameOnlineList.add(on);
			}
		}
	}



	public List<OnlineNum> getGameOnlineList() {
		return gameOnlineList;
	}



	public void setGameOnlineList(List<OnlineNum> gameOnlineList) {
		this.gameOnlineList = gameOnlineList;
	}



	public byte getResult() {
		return result;
	}



	public void setResult(byte result) {
		this.result = result;
	}



	public int getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}


	
}
