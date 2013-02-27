package com.joymeng.game.net.response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.role.PlayerAche;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.message.JoyResponse;

public class UserResp extends JoyResponse {
	static Logger logger = LoggerFactory.getLogger(UserResp.class);
	byte result;
	int errorCode;
	byte type;
	String name ;
	PlayerCharacter player;
	List<RoleData> roleLst;
	PlayerAche playerAche;
	boolean success;

	/**
	 * 玩家全名
	 */
	String fullName;

	List<ClientModule> allLst ;
	public UserResp() {
		super(ProcotolType.USER_INFO_RESP);
		super.setDestInstanceID(JoyProtocol.MODULE_SYS);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		System.out.println("user put type >>>>>>>>>>>>"+type);
		switch (type) {
		case ProcotolType.USER_MOTIFY_NAME:// 
			out.putPrefixedString(name, (byte) 2);
			break;
		case ProcotolType.USER_ENTER:
			if(allLst != null){
				out.putInt(allLst.size());
				for(ClientModule c : allLst)
					c.serialize(out);
			}
			break;
		case ProcotolType.LADDER_RESET:
			break;
		case ProcotolType.USER_TYPE:
			//玩家名称字符串
			out.putPrefixedString(fullName,JoyBuffer.STRING_TYPE_SHORT);
			break;
		case ProcotolType.USER_COMMIT:
			
			//成功与否
			if(success){
				out.put((byte)1);
			}
			else{
				out.put((byte)0);
			}
			break;
		case ProcotolType.PLAYER_ACHER:
			if(playerAche != null){
				out.putPrefixedString(String.valueOf(playerAche.getMyOfficial()),(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getMyFeat()),(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getMyAdd()),(byte)2);
//				out.putPrefixedString("1%",(byte)2);
				
				out.putPrefixedString(playerAche.getKingName(),(byte)2);
//				out.putPrefixedString("aaa",(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getKingFeat()),(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getKingAdd()),(byte)2);
//				out.putPrefixedString("5%",(byte)2);
				
				out.putPrefixedString(playerAche.getStateName(),(byte)2);
//				out.putPrefixedString("bbb",(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getStateFeat()),(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getStateAdd()),(byte)2);
//				out.putPrefixedString("4%",(byte)2);
				
				out.putPrefixedString(playerAche.getCityName(),(byte)2);
//				out.putPrefixedString("ccc",(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getCityFeat()),(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getCityAdd()),(byte)2);
//				out.putPrefixedString("3%",(byte)2);
				
				out.putPrefixedString(playerAche.getTownName(),(byte)2);
//				out.putPrefixedString("ddd",(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getTownFeat()),(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getTownAdd()),(byte)2);
//				out.putPrefixedString("2%",(byte)2);
				out.putPrefixedString(String.valueOf(playerAche.getProsperity()),(byte)2);
			}
			break;
		case ProcotolType.PLAYER_HALT:
			break;
		}
//		player.getData().serialize(out);
		// if(result==Constants.GAME_RESP_SUCCESS){
		// out.putInt(player.getUserId());
		// out.putPrefixedString(player.getNickname());
		// out.put(player.getImgId());
		// out.putInt(player.getMoney());
		// out.putInt(player.getLevel());
		// out.putInt(player.getVictoryNum());
		// out.putInt(player.getLoseNum());
		// out.putInt(player.getJoyMoney());
		// out.putInt(player.getLoginReward());
		// logger.info("get login reward="+player.getLoginReward());
		// }
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
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

	public PlayerCharacter getPlayer() {
		return player;
	}

	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}

	/**
	 * @return GET the type
	 */
	public byte getType() {
		return type;
	}

	/**
	 * @param SET type the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}
	
	/**
	 * @return GET the roleLst
	 */
	public List<RoleData> getRoleLst() {
		return roleLst;
	}

	/**
	 * @param SET roleLst the roleLst to set
	 */
	public void setRoleLst(List<RoleData> roleLst) {
		this.roleLst = roleLst;
	}

	/**
	 * @return GET the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param SET name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return GET the allLst
	 */
	public List<ClientModule> getAllLst() {
		return allLst;
	}

	/**
	 * @param SET allLst the allLst to set
	 */
	public void setAllLst(List<ClientModule> allLst) {
		this.allLst = allLst;
	}
	
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public PlayerAche getPlayerAche() {
		return playerAche;
	}

	public void setPlayerAche(PlayerAche playerAche) {
		this.playerAche = playerAche;
	}
	
}
