package com.joymeng.game.net.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class RankResp extends JoyResponse{

	static Logger logger = LoggerFactory.getLogger(RankResp.class);
	byte result;
	int errorCode;
	byte type;
	int slefRanking;
	int currentPage;
	int totalPages;
	public RankResp() {
		super(ProcotolType.RANK_RESP);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		switch(type){
		case ProcotolType.GAME_MONEY_RANK://金币
			out.putInt(currentPage);//当前页数
			out.putInt(totalPages);//总页数
			break;
		case ProcotolType.GAME_JOY_MONEY_RANK://钻石
			out.putInt(currentPage);//当前页数
			out.putInt(totalPages);//总页数
			break;
		case ProcotolType.LADDER_RANK://通天塔
			out.putInt(currentPage);
			out.putInt(totalPages);
			break;
		case ProcotolType.GENERAL_ATTACK_RANK://武将攻击力
			out.putInt(currentPage);
			out.putInt(totalPages);
			break;
		case ProcotolType.GENERAL_DEFENSE_RANK://武将防御力
			out.putInt(currentPage);
			out.putInt(totalPages);
			break;
		case ProcotolType.GENERAL_HP_RANK://武将生命值
			out.putInt(currentPage);
			out.putInt(totalPages);
			break;
		case ProcotolType.ARENA_RANK:
			out.putInt(currentPage);
			out.putInt(totalPages);
			break;
			
		}
		
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

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
	
	public int getSlefRanking() {
		return slefRanking;
	}

	public void setSlefRanking(int slefRanking) {
		this.slefRanking = slefRanking;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}


}
