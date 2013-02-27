package com.joymeng.game.net.response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.card.SimpleAward;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class BattleFieldResp extends JoyResponse {

	static Logger logger = LoggerFactory.getLogger(BattleFieldResp.class);
	
	byte result;
	int errorCode;
	byte type;
	int currentPage;
	int totalPages;
	int totalSize;
	List<SimpleAward> saList;
			
	public BattleFieldResp() {
		super(ProcotolType.BATTLE_FIELD_RESP);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 此处注释掉的代码是因为全部改为走通用模块
	 */
	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		switch(type){
		case ProcotolType.GET_SYS_AWARD:
			if(saList != null){
				out.put((byte)saList.size());
				for(SimpleAward sa : saList){
					out.putInt(sa.getWhat());
					out.putInt(sa.getValue());
				}
			}
			break;
		case ProcotolType.PERSONAL_FIGHT_EVENT:
		case ProcotolType.SEE_FIGHT_EVENT:
		case ProcotolType.STAGE_FIGHT_EVENT:
		case ProcotolType.SYS_BATTLE_INFO:
		case ProcotolType.SYS_AWARD:
		case ProcotolType.IS_NEED_GET_SYS_AWARD:
			break;
			//case ProcotolType.PERSONAL_FIGHT_EVENT:
			//out.putInt(currentPage);//当前页数
			//out.putInt(totalPages);//总页数
			//out.putInt(totalSize);//总记录条数
			//break;
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

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<SimpleAward> getSaList() {
		return saList;
	}

	public void setSaList(List<SimpleAward> saList) {
		this.saList = saList;
	}
	
	
	
	

}
