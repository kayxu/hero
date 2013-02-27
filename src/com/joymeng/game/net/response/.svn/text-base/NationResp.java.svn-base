package com.joymeng.game.net.response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.war.MilitaryCamp;
import com.joymeng.game.domain.nation.war.StrongHold;
import com.joymeng.game.domain.nation.war.UserWarData;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class NationResp extends JoyResponse{
	
	static Logger logger = LoggerFactory.getLogger(NationResp.class);
	byte type;
	byte result;
	int errorCode;
	String name;
	List<Nation> nations;
	List<ClientModuleBase> fight;
	List<UserWarData> topLst;
	public NationResp() {
		super(ProcotolType.NATION_RESP);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void _deserialize(JoyBuffer in) {
		result=in.get();
		errorCode=in.getInt();
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		type=in.get();
		switch (type) {
		case ProcotolType.NATION_ALL_STATE:// 所有州
			
			break;
		case ProcotolType.NATION_ALL_CITY:// 所有市
			break;
		}// TODO Auto-generated method stub
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		logger.info("out hashCode = "+out.hashCode());
		//每个resp必须先回个size
		logger.info("nation put type >>>>>>>>>>>>"+type);
		switch (type) {
		case ProcotolType.NATION_ALL_STATE:// 所有州
			if(fight != null){
				out.putInt(fight.size());
//				for(Nation n : nations){
//					n.serialize(out);
//				}
				for(ClientModuleBase cb : fight){
					if(cb instanceof Nation){
						Nation na = (Nation)cb;
						na.serialize(out);
					}
					if(cb instanceof SimplePlayerHero){
						SimplePlayerHero si = (SimplePlayerHero)cb;
						si.serialize(out);
					}
				}
			}
			break;
		case ProcotolType.NATION_ALL_CITY:// 所有市
			if(fight!=null && fight.size() >0){
				out.putInt(fight.size());
				for(ClientModuleBase cb : fight){
					if(cb instanceof Nation){
						Nation na = (Nation)cb;
						na.serialize(out);
					}
					if(cb instanceof SimplePlayerHero){
						SimplePlayerHero si = (SimplePlayerHero)cb;
						si.serialize(out);
					}
				}
			}else{
				out.putInt(0);
			}
			break;
		case ProcotolType.NATION_MOTIFY_NAME : //NATION_WAR_DATA
//			out.putPrefixedString(name, (byte) 2);
			break;
		case ProcotolType.NATION_WAR_DATA : //NATION_WAR_DATA
			if(topLst!=null && topLst.size() >0){
				out.putInt(topLst.size());
				for(UserWarData cb : topLst){
					cb.serialize(out);
				}
			}else{
				out.putInt(0);
			}
			break;
		case ProcotolType.NATION_FIGHT:
			StringBuffer sb= new StringBuffer();
			sb.append("发送 NATION_FIGHT....");
			if(fight!=null && fight.size() >0){
				out.putInt(fight.size());
				for(ClientModuleBase cb : fight){
					if(cb instanceof MilitaryCamp){
						MilitaryCamp mili = (MilitaryCamp)cb;
						mili.serialize(out);
					}
					if(cb instanceof StrongHold){
						StrongHold st = (StrongHold)cb;
						st.serialize(out);
					}
					if(cb instanceof SimplePlayerHero){
						SimplePlayerHero si = (SimplePlayerHero)cb;
						si.serialize(out);
					}
					
				}
				if(topLst!=null && topLst.size() >0){
					out.putInt(topLst.size());
					sb.append("发送 UserWarData....size:  "+topLst.size());
					for(UserWarData cb : topLst){
						cb.serialize(out);
					}
				}else{
					sb.append("发送 UserWarData....size:  "+0);
					out.putInt(0);
				}
				sb.append("发送 NATION_FIGHT....成功  ");
			}else{
				out.putInt(0);
				sb.append("发送 NATION_FIGHT....成功 0数据");
			}
			logger.info(sb.toString());
//			GameUtils.sendTip(new TipMessage("SSSSSSSS", ProcotolType.ARENA_RESP,
//					GameConst.GAME_RESP_FAIL), getUserInfo(),GameUtils.FLUTTER);
			break;
		case ProcotolType.TOP_WAR:
			if(topLst!=null && topLst.size() >0){
				out.putInt(topLst.size());
				for(UserWarData data : topLst){
					if(data.getWarCache() != null){
						out.putPrefixedString(data.getWarCache().getName(), (byte)2);
						out.putInt(data.getWarIntegral());
					}
				}
			}else{
				out.putInt(0);
			}
			break;
		case ProcotolType.NATION_POWER:
			out.putInt(0);
			break;
		case ProcotolType.NATION_MIND_RES:
			out.putInt(0);
			break;
		case ProcotolType.ADD_SOLDIER:
			out.putInt(0);
			break;
		case ProcotolType.NATION_ADD_SOLIDER:
			out.putInt(0);
			break;
		case ProcotolType.WAR_SING:
			out.putInt(0);
			logger.info("发送 WAR_SING....成功  ");
			break;
		case ProcotolType.WAR_QUIT:
			out.putInt(0);
			logger.info("发送WAR_QUIT....成功  ");
			break;
		case ProcotolType.RESOURCES_SING:
			out.putInt(0);
			logger.info("发送RESOURCES_SING....成功  ");
			break;
		case ProcotolType.RESOURCES_QUIT:
			out.putInt(0);
			logger.info("发送 RESOURCES_QUIT....成功  ");
			break;
		case ProcotolType.KEEP_UNDER:
			if(getFight() != null){
				out.putInt(1);
				ClientModuleBase cb = getFight().get(0);
				if(cb instanceof Nation){
					((Nation)cb).serialize(out);
				}
			}else{
				out.putInt(0);
			}
		}
		logger.info("发送类型："+type+"|数据包大小："+out.array().length);
		logger.info(">>>>>>>>>response UserInfo="+type+"|userinfo："+toString());
		
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
	 * @return GET the result
	 */
	public byte getResult() {
		return result;
	}

	/**
	 * @param SET result the result to set
	 */
	public void setResult(byte result) {
		this.result = result;
	}

	/**
	 * @return GET the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param SET errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return GET the nations
	 */
	public List<Nation> getNations() {
		return nations;
	}
	
	public List<ClientModuleBase> getFight() {
		return fight;
	}

	public void setFight(List<ClientModuleBase> fight) {
		this.fight = fight;
	}
	
	public List<UserWarData> getTopLst() {
		return topLst;
	}

	public void setTopLst(List<UserWarData> topLst) {
		this.topLst = topLst;
	}

	/**
	 * @param SET nations the nations to set
	 */
	public void setNations(List<Nation> nations) {
		this.nations = nations;
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		com.joymeng.core.utils.StringUtil.append(sb, String.valueOf(this.getUserInfo().getUid()),String.valueOf(this.getUserInfo().getCid()),String.valueOf(this.getUserInfo().getEid()));
		return sb.toString();
	}
	
}
