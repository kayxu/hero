package com.joymeng.game.net.response;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.box.Award;
import com.joymeng.game.domain.box.BoxConst;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class BoxResp extends JoyResponse{

	static Logger logger = LoggerFactory.getLogger(BoxResp.class);
	byte type;
	//List<Award> selectedAwards;
	String selectedAwardIdString;
	byte result;
	int errorCode;
	//List<Award> beAwardedDatas;
	String beAwardedIdString;
	byte totalChance;
	byte leftChance;
	int boxIntegral;
	byte countReciveAward;
	int chip;
	int refresCostChip;
	int turnCostChip;

	/**
	 * 用于测试时可删除
	 */
	HashMap<Integer,Award> awardDatas = new HashMap<Integer,Award>();

	/**
	 * 用于测试时，可删除
	 * @param path
	 * @throws Exception
	 */
	public void loadAwardDatas(String path) throws Exception{
		//logger.info("------------------------------加载奖品数据-----------------------");
		List<Object> list = GameDataManager.loadData(path, Award.class);
		for (Object o : list){
			Award a = (Award) o;
			awardDatas.put(a.getId(), a);
		}
		//logger.info("记录条数：" + awardDatas.size());
		//logger.info("----------------------------奖品数据加载完毕---------------------");
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		result=in.get();
		errorCode=in.getInt();
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		type=in.get();
		//logger.info("==================================================================================");
		switch(type){
		 case BoxConst.OPEN_BOX:
			 in.get();
			 in.get();
			 int size = in.getInt();
			 //logger.info("下发奖品数量" + size);
			 for(int i=0;i< size;i++){
				 int id = in.getInt();
				 Award a = awardDatas.get(id);
				 //logger.info("物品id:" + a.getId() + " 奖品等级：" + a.getLevel() + " 奖品类型：" + a.getType());
			 }
			 //logger.info("==================================================================================");
			 break;
		 case BoxConst.REFRESH_BOX:
			 //logger.info("==================================================================================");
			 size = in.getInt();
			 //logger.info("奖品个数：" + size);
			 //logger.info("================左边抽奖品区=============");
			 for(int i=0;i < size;i++){
				 int id = in.getInt();
				 Award a = awardDatas.get(id);
				 if(a == null){
					 //logger.info("此位置奖品已被抽走");
				 }
				 else{
					 //logger.info("物品id:" + a.getId() + " 奖品等级：" + a.getLevel() + " 奖品类型：" + a.getType());
				 }
				 
			 }
		/*	 //logger.info("========END========");
			 //logger.info("================右边已抽奖品=============");
			 size = in.getInt();
			 for(int i=0;i<size;i++){
				 int id = in.getInt();
				 Award a = awardDatas.get(id);
				 //logger.info("物品id:" + a.getId() + " 奖品等级：" + a.getLevel() + " 奖品类型：" + a.getType());
			 }
			 //logger.info("=======END=========");
			 //logger.info("******说明：左边抽奖区域前几个奖品为右边已抽中奖品*******");
			 //logger.info("==================================================================================");*/
			 break;
		 case BoxConst.START:
			 in.getInt();
			 in.get();
			 in.get();
			 //logger.info("==================================================================================");
			 //logger.info("================右边已抽奖品=============");
			 size = in.getInt();
			 for(int i=0;i<size;i++){
				 int id = in.getInt();
				 Award a = awardDatas.get(id);
				 //logger.info("物品id:" + a.getId() + " 奖品等级：" + a.getLevel() + " 奖品类型：" + a.getType());
			 }
			 //logger.info("=======END=========");
			 //logger.info("==================================================================================");
			 
		}
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// todo
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		switch(type){
		 case BoxConst.OPEN_BOX:
			 out.putInt(refresCostChip);
			 out.putInt(turnCostChip);
			 out.putInt(chip);
			 out.putInt(boxIntegral);
			 out.put(totalChance);
			 out.put(leftChance);
			 if(!"".equals(selectedAwardIdString)){
				 
				 String[] selectedAwardId = selectedAwardIdString.split(",");
				 out.putInt(selectedAwardId.length);
				 for(String id : selectedAwardId){
					 out.putInt(Integer.parseInt(id));
				 }
			 }else{
				 out.putInt(0);
			 }
			 if(!"".equals(beAwardedIdString)){
				 String[] beAwardedId = beAwardedIdString.split(",");
				 out.putInt(beAwardedId.length);
				 for(String id : beAwardedId){
					 out.putInt(Integer.parseInt(id));
				 }
			 }
			 else{
				 out.putInt(0);
			 }
			 break;
		 case BoxConst.REFRESH_BOX:
			 out.putInt(refresCostChip);
			 out.putInt(turnCostChip);
			 out.putInt(chip);
			 //out.put(countReciveAward);
			 if(!"".equals(selectedAwardIdString)){
				 String[] selectedAwardId = selectedAwardIdString.split(",");
				 out.putInt(selectedAwardId.length);
				 for(String id : selectedAwardId){
					 out.putInt(Integer.parseInt(id));
				 }
			 }else{
				 out.putInt(0);
			 }
			/* if(beAwardedIdString != ""){
				 String[] beAwardedId = beAwardedIdString.split(",");
				 out.putInt(beAwardedId.length);
				 for(String id : beAwardedId){
					 out.putInt(Integer.parseInt(id));
				 }
			 }
			 else{
				 out.putInt(0);
			 }*/
			 break;
		 case BoxConst.START:
			 out.putInt(refresCostChip);
			 out.putInt(turnCostChip);
			 out.putInt(chip);
			 out.putInt(boxIntegral);//宝箱积分
			 out.put(totalChance);//抽奖总次数
			 out.put(leftChance);//剩下次数
			 if(!"".equals(beAwardedIdString)){
				 String[] beAwardedId = beAwardedIdString.split(",");
				 out.putInt(beAwardedId.length);
				 for(String id : beAwardedId){
					 out.putInt(Integer.parseInt(id));
				 }
			 }
			 else{
				 out.putInt(0);
			 }
			 break;
		 case BoxConst.RECEIVE_AWARD:
			 out.putInt(refresCostChip);
			 out.putInt(turnCostChip);
			 out.putInt(boxIntegral);//宝箱积分
			 out.put(totalChance);//抽奖总次数
			 out.put(leftChance);//剩下次数
			 if(!"".equals(beAwardedIdString)){
				 String[] beAwardedId = beAwardedIdString.split(",");
				 out.putInt(beAwardedId.length);
				 for(String id : beAwardedId){
					 out.putInt(Integer.parseInt(id));
				 }
			 }
			 else{
				 out.putInt(0);
			 }
			 break;
		 case BoxConst.RESTART:
			 out.putInt(refresCostChip);
			 out.putInt(turnCostChip);
			 out.putInt(boxIntegral);//宝箱积分
			 out.put(totalChance);//抽奖总次数
			 out.put(leftChance);//剩下次数
			 if(!"".equals(selectedAwardIdString)){
				 String[] selectedAwardId = selectedAwardIdString.split(",");
				 out.putInt(selectedAwardId.length);
				 for(String id : selectedAwardId){
					 out.putInt(Integer.parseInt(id));
				 }
			 }else{
				 out.putInt(0);
			 }
		 case BoxConst.BUY://购买筹码
			 break;
		}
	}


	public String getSelectedAwardIdString() {
		return selectedAwardIdString;
	}

	public void setSelectedAwardIdString(String selectedAwardIdString) {
		this.selectedAwardIdString = selectedAwardIdString;
	}
	

	public String getBeAwardedIdString() {
		return beAwardedIdString;
	}

	public void setBeAwardedIdString(String beAwardedIdString) {
		this.beAwardedIdString = beAwardedIdString;
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
	
	public byte getTotalChance() {
		return totalChance;
	}

	public void setTotalChance(byte totalChance) {
		this.totalChance = totalChance;
	}

	public byte getLeftChance() {
		return leftChance;
	}

	public void setLeftChance(byte leftChance) {
		this.leftChance = leftChance;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getCountReciveAward() {
		return countReciveAward;
	}

	public void setCountReciveAward(byte countReciveAward) {
		this.countReciveAward = countReciveAward;
	}

	public HashMap<Integer, Award> getAwardDatas() {
		return awardDatas;
	}

	public void setAwardDatas(HashMap<Integer, Award> awardDatas) {
		this.awardDatas = awardDatas;
	}

	public BoxResp() {
		super(ProcotolType.BOX_RESP);
	}

	public int getBoxIntegral() {
		return boxIntegral;
	}

	public void setBoxIntegral(int boxIntegral) {
		this.boxIntegral = boxIntegral;
	}

	public int getChip() {
		return chip;
	}

	public void setChip(int chip) {
		this.chip = chip;
	}

	public int getRefresCostChip() {
		return refresCostChip;
	}

	public void setRefresCostChip(int refresCostChip) {
		this.refresCostChip = refresCostChip;
	}

	public int getTurnCostChip() {
		return turnCostChip;
	}

	public void setTurnCostChip(int turnCostChip) {
		this.turnCostChip = turnCostChip;
	}
	
}
