package com.joymeng.game.domain.world;

import java.util.ArrayList;
import java.util.List;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.common.GameConst;

public class TipUtil implements TipModule {
	
	private TipMessage tip;
	private boolean result = false;//结果
	private int type;
	private boolean fightresult = false;//战斗结果
	private List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
 	private byte procotolType;//操作类型
 	private int[] bufint;//攻击方，防御方加成
 	private String str;//消息
 	
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public TipUtil(int type){
		this.type = type;
	}
	@Override
	public TipMessage getTip() {
		return this.tip;
	}

	@Override
	public void setTip(TipMessage tip) {
		this.tip = tip;
	}
	
	public int[] getBufint() {
		return bufint;
	}
	public void setBufint(int[] bufint) {
		this.bufint = bufint;
	}
	public byte getProcotolType() {
		return procotolType;
	}
	public void setProcotolType(byte procotolType) {
		this.procotolType = procotolType;
	}
	public boolean isFightresult() {
		return fightresult;
	}
	public void setFightresult(boolean fightresult) {
		this.fightresult = fightresult;
	}
	public List<ClientModuleBase> getLst() {
		return lst;
	}
	public void setLst(List<ClientModuleBase> lst) {
		this.lst = lst;
	}
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	public String getResultMsg(){
		if(tip == null){
			return "";
		}else{
			return tip.getMessage();
		}
	}
	/**
	 * 设置成功消息
	 * @param msg
	 * @param type
	 */
	public TipUtil setSuccTip(String msg){
		setTip(new TipMessage(msg, getType(), GameConst.GAME_RESP_SUCCESS));
		setResult(true);
		return this;
	}
	/**
	 * 设置失败消息
	 * @param msg
	 * @param type
	 */
	public TipUtil setFailTip(String msg){
		setTip(new TipMessage(msg, getType(), GameConst.GAME_RESP_FAIL));
		setResult(false);
		return this;
	}
	
	public static void main(String[] args) {//1343038214511
		System.out.println(TimeUtils.addSecond(TimeUtils.nowLong(), 0));
	}
}
