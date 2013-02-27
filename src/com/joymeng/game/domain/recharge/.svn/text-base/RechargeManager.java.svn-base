package com.joymeng.game.domain.recharge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.box.PropsBoxManager;
import com.joymeng.game.domain.card.CardManager;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.box.Package;

public class RechargeManager {
	private static final Logger logger = LoggerFactory.getLogger(CardManager.class);
	
	PlayerCharacter player;
	
	public RechargeManager(PlayerCharacter player){
		this.player = player;
	}
	
	public static Map<Integer,Recharge> rechargeAwardRuleMap = new HashMap<Integer,Recharge>();
	
	/**
	 * 用户充值后，获得的奖励包裹id,和该包裹所属的层级 String格式为   层级,包裹id
	 */
	List<String> stageAndPackageIds;
	
	/**
	 * 每一个字符串中包含4个物品id和所有奖品的层级
	 */
	List<String> awardIds = new ArrayList<String>();
	
	/**
	 * 每个字符串包含4个物品的数量
	 */
	List<String> awardNums = new ArrayList<String>();
	
	/**
	 * 充值后可领奖次数
	 */
	int getAwardChance = 0;
	
	/**
	 * 达到下一个阶段需要充值多少
	 */
	int toNextNeedRechargeVal = 0;
	
	int alreadyRechargeVal = 0;
	
	/**
	 * 下一个奖品规则列表
	 */
	Recharge nextRecharge;
	
	/**
	 * 加载充值奖励规则
	 * @param path
	 * @throws Exception
	 */
	public static void loadRechargeAwardRule(String path)throws Exception{
		//logger.info("-----------------------加载充值奖励规则-------------------");
		List<Object> list = GameDataManager.loadData(path, Recharge.class);
		for(Object o : list){
			Recharge re = (Recharge)o;
			//logger.info(re + "");
			rechargeAwardRuleMap.put(re.getRechargeVal(), re);
		}
		//logger.info("-----------------------加载充值奖励规则完毕-------------------");
	}
	
	/**
	 * 充值处理
	 * @return
	 */
	public boolean doRecharge(int rechargeVal){
		
		//----------------------------------------------------
		
		//判断用户是否充值成功
		//to add
		//----------------------------------------------------
		
		//用户重新登录，从数据库中查找用户是否有未抽完的奖,和玩家已充值钻数
		if(stageAndPackageIds == null){
			stageAndPackageIds = new ArrayList<String>();
			//数据库中读取玩家充值信息
			readRechargeInfoFromDB();
		}
		
		//用户充值成功的状态下执行
		int nextRechargeVal = getAwardPackageByRechargeVal(alreadyRechargeVal,rechargeVal);

		if(stageAndPackageIds.size() != 0){
			for(String info : stageAndPackageIds){
				String[] infos = info.split(",");
				Package p = PropsBoxManager.getInstance().packageMap.get(Integer.parseInt(infos[1]));
				String itemId = p.getItemId();
				String itmeIdAndStage = itemId + ":" + infos[0];
				//如果物品列表不为空
				if(!"".equals(itemId)){
					awardIds.add(itmeIdAndStage);
				}
				String itemNum = p.getItemNum();
				if(!"".equals(itemNum)){
					awardNums.add(itemNum);
				}
			}
		}
		
		//可领奖机会
		getAwardChance = awardIds.size();
		
		//下一个阶段奖品规则
		nextRecharge = rechargeAwardRuleMap.get(nextRechargeVal);
		
		//到下一个阶段奖需要的钻石数
		toNextNeedRechargeVal = nextRechargeVal - rechargeVal;
		return true;
	}
	
	public void getRechargeAward(){
		
		if(getAwardChance > 0){
			String itemId = awardIds.get(0);
			String itemNum = awardNums.get(0);
			//将奖品推送给客户端
			if(!"".equals(itemId) && !"".equals(itemNum)){
				String[] itemIds = itemId.split(":");
				String[] itemNums = itemNum.split(":");
				//主动推送客户端
				RespModuleSet rms=new RespModuleSet(ProcotolType.RECHARGE_RESP);
				for(int i=0;i<itemNums.length;i++){
					Cell cell = player.getPlayerStorageAgent().addPropsCell(Integer.parseInt(itemIds[i]),Integer.parseInt(itemNums[i]));
					rms.addModule(cell);
					rms.addModule(player.getData());
					AndroidMessageSender.sendMessage(rms,player);
				}
			}
			
			if(stageAndPackageIds.size() != 0 && awardIds.size() != 0 && awardNums.size() != 0){
				stageAndPackageIds.remove(0);
				awardIds.remove(0);
				awardNums.remove(0);
			}
		}
		getAwardChance --;
		
	}
	
	/**
	 * 根据充值奖励获取奖励包裹
	 * @param rechargeVal int 玩家充值数量
	 * @return int 下一个充值额度
	 */
	private int getAwardPackageByRechargeVal(int alreadyRechargeVal,int rechargeVal){
		int nextRange = 10;
		if(rechargeVal + alreadyRechargeVal >= 10 && alreadyRechargeVal < 10){
			stageAndPackageIds.add("10," + rechargeAwardRuleMap.get(10).getPackageId());
			nextRange = 200;
		}
		if(rechargeVal + alreadyRechargeVal >= 200 && alreadyRechargeVal < 200){
			stageAndPackageIds.add("200," + rechargeAwardRuleMap.get(200).getPackageId());
			nextRange = 500;
		}
		if(rechargeVal + alreadyRechargeVal >= 500 && alreadyRechargeVal < 500){
			stageAndPackageIds.add("500," + rechargeAwardRuleMap.get(500).getPackageId());
			nextRange = 1000;
		}
		if(rechargeVal + alreadyRechargeVal >= 1000 && alreadyRechargeVal < 1000){
			stageAndPackageIds.add("1000," + rechargeAwardRuleMap.get(1000).getPackageId());
			nextRange = 2000;
		}
		if(rechargeVal + alreadyRechargeVal >= 2000 && alreadyRechargeVal < 2000){
			stageAndPackageIds.add("2000," + rechargeAwardRuleMap.get(2000).getPackageId());
			nextRange = 5000;
		}
		if(rechargeVal + alreadyRechargeVal >= 5000 && alreadyRechargeVal < 5000){
			stageAndPackageIds.add("5000," + rechargeAwardRuleMap.get(5000).getPackageId());
			nextRange = 10000;
		}
		if(rechargeVal + alreadyRechargeVal >= 10000 && alreadyRechargeVal < 10000){
			stageAndPackageIds.add("10000," + rechargeAwardRuleMap.get(10000).getPackageId());
			nextRange = 20000;
		}
		if(rechargeVal + alreadyRechargeVal >= 20000 && alreadyRechargeVal < 20000){
			stageAndPackageIds.add("20000," + rechargeAwardRuleMap.get(20000).getPackageId());
			nextRange = 50000;
		}
		if(rechargeVal + alreadyRechargeVal >= 50000 && alreadyRechargeVal < 50000){
			stageAndPackageIds.add("50000," + rechargeAwardRuleMap.get(50000).getPackageId());
			nextRange = -1;//充值额度达到最大
		}
		
		//已经充值的钻数增加
		alreadyRechargeVal += rechargeVal;
		
		return nextRange;
	}
	
	/***
	 * 保存玩家充值信息
	 */
	public void saveRechargeInfo(){
		int userid = (int)player.getData().getUserid();
		PlayerRecharge playerRecharge = new PlayerRecharge();
		playerRecharge.setUserid(userid);
		playerRecharge.setAlreadyRechargeVal(alreadyRechargeVal);
		StringBuffer stageIds = new StringBuffer();
		if(null != stageAndPackageIds){
			for(String info : stageAndPackageIds){
				stageIds.append(info).append(";");
			}
		}
		playerRecharge.setStageAndPackageIds(stageIds.toString());
		PlayerRecharge pr = DBManager.getInstance().getWorldDAO().getPlayerRechargeDAO().findCertainPlayerRecharge(userid);
		if(null == pr){//保存玩家充值信息
			DBManager.getInstance().getWorldDAO().getPlayerRechargeDAO().addPlayerRecharge(playerRecharge);
		}
		else{//更新玩家充值信息
			DBManager.getInstance().getWorldDAO().getPlayerRechargeDAO().updatePlayerRecharge(playerRecharge);
		}
		
	}
	
	/**
	 * 从数据库中读取玩家充值信息
	 */
	public void readRechargeInfoFromDB(){
		//重启服务器后从数据库中读取保存的玩家充值信息
		PlayerRecharge playerRecharge = DBManager.getInstance().getWorldDAO().getPlayerRechargeDAO().findCertainPlayerRecharge((int)player.getData().getUserid());
		if(null != playerRecharge){
			alreadyRechargeVal = playerRecharge.getAlreadyRechargeVal();
			String stageIds = playerRecharge.getStageAndPackageIds();
			String[] stageIdsArr = stageIds.split(";");
			for(String info : stageIdsArr){
				stageAndPackageIds.add(info);
			}
		}
		
	}

	public PlayerCharacter getPlayer() {
		return player;
	}

	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}

	public List<String> getAwardIds() {
		return awardIds;
	}

	public void setAwardIds(List<String> awardIds) {
		this.awardIds = awardIds;
	}

	public int getGetAwardChance() {
		return getAwardChance;
	}

	public void setGetAwardChance(int getAwardChance) {
		this.getAwardChance = getAwardChance;
	}

	public int getToNextNeedRechargeVal() {
		return toNextNeedRechargeVal;
	}

	public void setToNextNeedRechargeVal(int toNextNeedRechargeVal) {
		this.toNextNeedRechargeVal = toNextNeedRechargeVal;
	}

	public Recharge getNextRecharge() {
		return nextRecharge;
	}

	public void setNextRecharge(Recharge nextRecharge) {
		this.nextRecharge = nextRecharge;
	} 
	
	
	
	

}
