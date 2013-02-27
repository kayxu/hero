package com.joymeng.game.net.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class NationRequest extends JoyRequest{
	public NationRequest() {
		super(ProcotolType.NATION_REQ);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	static Logger logger = LoggerFactory.getLogger(NationRequest.class);
	byte type;//类型
	int stateId;//州id
	String name;//修改名字
	byte resType;//资源类型
	byte page;//页码
	int heroId;
	String soMsg;
	String tuiMsg;
	@Override
	protected void _deserialize(JoyBuffer in) {
		// todo
		type = in.get();
		switch (type) {
		case ProcotolType.NATION_ALL_STATE:// 所有州
			break;
		case ProcotolType.NATION_ALL_CITY:// 所有市
			stateId = in.getInt();
			break;
		case ProcotolType.NATION_MOTIFY_NAME:// 修改名字
			stateId = in.getInt();
			name = in.getPrefixedString((byte)2);
			break;
		case ProcotolType.NATION_MIND_RES:// 修改名字
			resType = in.get();
			page = in.get();
			stateId = in.getInt();
			break;
		case ProcotolType.NATION_OCC_GOLD:
			resType = in.get();
			stateId = in.getInt();
			heroId = in.getInt();
//			soMsg = in.getPrefixedString((byte)2);
			break;
		case ProcotolType.NATION_RBT_GOLD:
			break;
		case ProcotolType.ADD_SOLDIER:
			stateId = in.getInt();
			heroId = in.getInt();
			name = in.getPrefixedString((byte)2);
			break;
		case ProcotolType.NATION_FIGHT:
			stateId = in.getInt();
			break;
		case ProcotolType.TOP_WAR:
			stateId = in.getInt();
			break;
		case ProcotolType.NATION_ADD_SOLIDER:
			stateId = in.getInt();
			name = in.getPrefixedString((byte)2);
			break;
		case ProcotolType.NATION_POWER:
			stateId = in.getInt();
			break;
		case ProcotolType.WAR_SING:
			stateId = in.getInt();
			heroId = in.getInt();
			break;
		case ProcotolType.WAR_QUIT:
			stateId = in.getInt();
			heroId = in.getInt();
			break;
		case ProcotolType.RESOURCES_SING:
			stateId = in.getInt();
			heroId = in.getInt();
			break;
		case ProcotolType.RESOURCES_QUIT:
			stateId = in.getInt();
			heroId = in.getInt();
			break;
		case ProcotolType.KEEP_UNDER:
			heroId = in.getInt();
			name = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
			break;
		}
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		System.out.println("send "+NationRequest.class.getName());
		out.put(type);
		switch (type) {
		case ProcotolType.NATION_ALL_STATE:// 所有州
			break;
		case ProcotolType.NATION_ALL_CITY:// 所有市
			out.putInt(stateId);
			break;
		case ProcotolType.NATION_MOTIFY_NAME:// 修改名字
			out.putInt(stateId);
			out.putPrefixedString(name,JoyBuffer.STRING_TYPE_SHORT);
			break;
		case ProcotolType.NATION_MIND_RES:// 获取资源
			out.put(resType);
			out.put(page);
			out.putInt(stateId);
			break;
		case ProcotolType.NATION_OCC_GOLD:
			out.put(resType);
			out.putInt(stateId);
			out.putInt(heroId);
//			out.putPrefixedString(soMsg,(byte)2);
			break;
		case ProcotolType.NATION_RBT_GOLD:
			break;
		case ProcotolType.ADD_SOLDIER:
			out.putInt(stateId);
			out.putInt(heroId);
			out.putPrefixedString(name,JoyBuffer.STRING_TYPE_SHORT);
			break;
		case ProcotolType.NATION_FIGHT:
			out.putInt(stateId);
			break;
		case ProcotolType.TOP_WAR:
			out.putInt(stateId);
			break;
		case ProcotolType.NATION_ADD_SOLIDER:
			out.putInt(stateId);
			out.putPrefixedString(name,JoyBuffer.STRING_TYPE_SHORT);
			break;
		case ProcotolType.NATION_POWER:
			out.putInt(stateId);
			break;
		case ProcotolType.WAR_SING:
			out.putInt(stateId);
			out.putInt(heroId);
			break;
		case ProcotolType.WAR_QUIT:
			out.putInt(stateId);
			out.putInt(heroId);
			break;
		case ProcotolType.RESOURCES_SING:
			out.putInt(stateId);
			out.putInt(heroId);
			break;
		case ProcotolType.RESOURCES_QUIT:
			out.putInt(stateId);
			out.putInt(heroId);
			break;
		case ProcotolType.KEEP_UNDER:
			out.putInt(heroId);
			out.putPrefixedString(name,JoyBuffer.STRING_TYPE_SHORT);
			break;
		}
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
	 * @return GET the stateId
	 */
	public int getStateId() {
		return stateId;
	}

	/**
	 * @param SET stateId the stateId to set
	 */
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return GET the resType
	 */
	public byte getResType() {
		return resType;
	}

	/**
	 * @param SET resType the resType to set
	 */
	public void setResType(byte resType) {
		this.resType = resType;
	}
	
	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public String getSoMsg() {
		return soMsg;
	}

	public void setSoMsg(String soMsg) {
		this.soMsg = soMsg;
	}

	/**
	 * @return GET the page
	 */
	public byte getPage() {
		return page;
	}

	/**
	 * @param SET page the page to set
	 */
	public void setPage(byte page) {
		this.page = page;
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		com.joymeng.core.utils.StringUtil.append(sb, String.valueOf(this.getUserInfo().getUid()),String.valueOf(this.getUserInfo().getCid()),String.valueOf(this.getUserInfo().getEid()));
		return sb.toString();
	}
}
