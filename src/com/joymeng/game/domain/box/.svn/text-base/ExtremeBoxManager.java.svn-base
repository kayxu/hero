package com.joymeng.game.domain.box;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.org.mozilla.javascript.internal.ObjArray;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogBuffer;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;

public class ExtremeBoxManager {
	
	static final Logger logger = LoggerFactory.getLogger(ExtremeBoxManager.class);
	
	private static ExtremeBoxManager boxManager;
	
	public static ExtremeBoxManager getInstance(){
		if(null == boxManager){
			boxManager = new ExtremeBoxManager();
		}
		return boxManager;
		
	}
	
	public static long costChipPerDay = 0;
	
	/**
	 * 奖品数据
	 */
	public HashMap<Integer,Award> awardDatas = new HashMap<Integer,Award>();
	
	/**
	 * 刷新几率数据
	 */
	public HashMap<Integer,RefreshRate> refreshRates = new HashMap<Integer,RefreshRate>();
	
	/**
	 * 中奖几率数据
	 */
	public HashMap<Integer,PrizeRate> prizeRates = new HashMap<Integer,PrizeRate>();
	

	/**
	 * 每次开局，和免费刷新时，各等级奖品出现的个数分配
	 */
	private static int[][] openBoxAwardRate = {
		{1,1,2,2,2,3,3,3,4,4,5,5},//分配方案一
		{1,1,1,2,2,3,3,3,4,5,5,5},//分配方案二
		{1,1,1,2,2,3,3,3,4,4,4,5},//分配方案三
		{1,1,1,1,2,3,3,4,4,5,5,5},//分配方案四
		{1,1,1,2,2,3,3,4,4,4,5,5}//分配方案五	
	};
	
	/**
	 * 转动花费,下标为转动次数减一
	 */
	private static int[] costChipWhenTurn = {1,2,3,4,5,6,7,8,9,10,11,12};
	
	/**
	 * 刷新所需要的筹码，下标为已转动次数
	 */
	private static int[] costChipWhenRefresh = {0,1,1,1,2,2,2,3,3,3,4,5};/*{0,1,1,1,1,1,1,1,2,3,4,5}*/
	
	/**
	 * 转动次数与获得积分对应关系，下标为转动次数减一
	 */
	private static int[] gainIntegral = {1,2,3,4,5,6,7,8,9,10,11,12};
	
	
	/**
	 * 奖品等级
	 */
	private int[] levels = {1,2,3,4,5,6,7,8,9,10};

	
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
	
	public void loadAdditionalDatas(String path) throws Exception{
		//logger.info("------------------------------加载宝箱附加数据-----------------------");
		List<Object> list = GameDataManager.loadData(path, RefreshRate.class);
		for(Object o : list){
			RefreshRate refreshRate = (RefreshRate) o;
			refreshRates.put((int)refreshRate.getRound(), refreshRate);
		}
		//logger.info("刷新几率记录条数：" + refreshRates.size());
		List<Object> listPrizeRate = GameDataManager.loadData(path, PrizeRate.class);
		for(Object o : listPrizeRate){
			PrizeRate prizeRate = (PrizeRate) o;
			prizeRates.put((int)prizeRate.getRound(), prizeRate);
		}
		//logger.info("中奖几率记录条数：" + prizeRates.size());
		//logger.info("---------------------------附加数据加载完毕---------------------");
	}

	
	/**
	 * 按奖品等级随机选取一个符合要求的奖品
	 * @param lelvel int
	 * @return Award 奖品
	 */
	private Award getRandomAwardByLevel(int level,PlayerCharacter player){
		
		List<Award> certainLevelAwards = new ArrayList<Award>();
		for(int id : awardDatas.keySet()){
			Award award = awardDatas.get(id);
			if(award.getLevel() == level ){
				certainLevelAwards.add(award);
			}
		}
		
		List<Award> certainLevelWithoutBeSelected = new ArrayList<Award>();
		//去掉已经选中的奖品,控制选取的奖品不重复
		for(int i=0;i<certainLevelAwards.size();i++){
			Award a = certainLevelAwards.get(i);
			if(!isSelected(a,player)){
				certainLevelWithoutBeSelected.add(a);
			}
		}
		
		int size = certainLevelWithoutBeSelected.size();
		
		if(size == 0){//当前等级奖品已没有或被抽完
			return null;
		}
		
		int which = MathUtils.random(certainLevelWithoutBeSelected.size());
		Award a = certainLevelWithoutBeSelected.get(which);
		
		//发送消息
		if(level > 6 && level < 9){
			String msg = I18nGreeting.getInstance().getMessage("box.refresh",
					new Object[]{player.getData().getName(),a.getDesc()});
			NoticeManager.getInstance().sendSystemWorldMessage(msg);
		}
		return a;
		
	}
	
	/**
	 * 某个奖品是否被选中
	 * @param award Award
	 * @return boolean 
	 */
	private boolean isSelected(Award award,PlayerCharacter player){

		String selectedAwardIdString = player.getData().getSelectedAwardIdString();
		if("".equals(selectedAwardIdString)){
			return false;
		}
		String[] selectedAwardId = selectedAwardIdString.split(",");
		
		for(String id : selectedAwardId){
			if(Integer.parseInt(id) == award.getId()){
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * 打开宝箱
	 * @param player PlayerCharacter
	 */
	public void openBox(PlayerCharacter player){
		
		GameLog.logPlayerEvent(player, LogEvent.BOX_USE_NUM, new LogBuffer().add(TimeUtils.now().format(TimeUtils.FORMAT1)));
		
		////logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + "uid=" + player.getId() + "uname=" + player.getData().getName());
		
		int boxIntegral = player.getData().getScore();

		int totalChance = getPlayerRoundByIntegral(boxIntegral);
		
		//获取玩家转动次数
		int round = player.getData().getBoxNum();
		
		int refreshCostChip;
		int turnCostChip;
		
		if(round == 12){
			refreshCostChip = 0;
			turnCostChip = 0;
		}
		else{
			refreshCostChip = costChipWhenRefreshByRound(round);
			turnCostChip = costChipWhenTurnByRound(round + 1);
		}

		player.getData().setTempRefreshCostChip(refreshCostChip);
		player.getData().setTempTurnCostChip(turnCostChip);
		
		//玩家剩余次数
		int leftChance = totalChance - round;
		if(leftChance < 0){
			leftChance = 0;
		}
		player.getData().setTotalChance(totalChance);
		player.getData().setLeftChance(leftChance);
		
		/**
		 * 开局刷新显示奖品
		 */
		if(round == 0){
			refreshWith0Round(player);
		}
	}
	
	/**
	 * 开局和0次转动刷新处理
	 */
	private void refreshWith0Round(PlayerCharacter player){
		
		//记录某玩家某时刻刷新宝箱
		LogBuffer lb = new LogBuffer();
		//lb.add(TimeUtils.nowLong());//某时
		GameLog.logPlayerEvent(player, LogEvent.REFRESH_BOX, lb);// eg : 62478|REFRESH_BOX|
		
		//selectedAwards.clear();
		player.getData().setSelectedAwardIdString("");
		//开局时随机选择一种分配方案
		int methodIndex = MathUtils.random(5);
		int[] needLevels = openBoxAwardRate[methodIndex];
		
		//打乱分配方案数组，以便生成符合奖品等级的奖品
		for(int i=0;i<needLevels.length;i++){
			int index = MathUtils.random(needLevels.length);
			int temp = needLevels[i];
			needLevels[i] = needLevels[index];
			needLevels[index] = temp;
		}
		
		//根据等级随机选取奖品
		StringBuffer sb = new StringBuffer();
		for(int level : needLevels){
			Award a = getRandomAwardByLevel(level,player);
			//selectedAwards.add(a);
			sb.append(a.getId()).append(",");
			player.getData().setSelectedAwardIdString(sb.toString());
		}
		
		
	}
	
	
	
	/**
	 * 转动之后刷新
	 * @param round int 玩家已转动次数
	 */
	private void refreshAfterStart(int round,PlayerCharacter player){
		
		//记录某玩家某时刻刷新宝箱
		LogBuffer lb = new LogBuffer();
		//lb.add(TimeUtils.nowLong());//某时
		GameLog.logPlayerEvent(player, LogEvent.REFRESH_BOX, lb);// eg : 62478|REFRESH_BOX|1355816172263|
		
		String selectedAwardIdString = player.getData().getSelectedAwardIdString();
		String[] selectedAwardIds = selectedAwardIdString.split(",");
		
		int refreshCostChip;
		int turnCostChip;
		if(round >= 12){//new alter 2012-1-16
			turnCostChip = 0;
		}
		else{
			turnCostChip= costChipWhenTurnByRound(round + 1);
		}
		
		if(round >= 12 ){
			refreshCostChip = 0;
			return;
		}
		else{
			refreshCostChip = costChipWhenRefreshByRound(round);
		}

		player.getData().setTempRefreshCostChip(refreshCostChip);
		player.getData().setTempTurnCostChip(turnCostChip);
		
		//玩家刷新所需筹码
		int costChip = costChipWhenRefreshByRound(round);
		
		//玩家剩余筹码
		int chip = player.getData().getChip();
		if(chip - costChip >= 0){
			chip -= costChip;
			GameLog.logSystemEvent(LogEvent.COST_CHIP, "" + costChip,TimeUtils.now().format(TimeUtils.FORMAT1));
			costChipPerDay += costChip;
		}
		else{
			return;
		}
		player.getData().setChip(chip);
		RefreshRate rr = refreshRates.get(round);
		
		int[] rate = {rr.getLv1Rate(),rr.getLv2Rate()
				,rr.getLv3Rate(),rr.getLv4Rate()
				,rr.getLv5Rate(),rr.getLv6Rate()
				,rr.getLv7Rate(),rr.getLv8Rate()
				,rr.getLv9Rate(),rr.getLv10Rate()
			};
		int[] tempLevels = new int[12 - round];
		//logger.info("XXX111");
		for(int i=0;i<12-round;i++){
			int lv = MathUtils.getRandomId1(levels, rate, 10000);
			tempLevels[i] = lv;
		}
		//logger.info("XXX222");
		Award[] awards = new Award[12 - round];
		for(int i=0;i<tempLevels.length;i++){
			Award a = getRandomAwardByLevel(tempLevels[i],player);
			
			//为了效率在30次随机都未找到满足条件时，提前跳出循环 alter 2012-10-15
			int circleTimes = 0;
			while(null == a ){
				//logger.info("=============refreshAfterStart============");
				circleTimes ++;
				if(circleTimes >= 30){
					break;
				}
				int level = MathUtils.getRandomId1(levels, rate, 10000);
				a = getRandomAwardByLevel(level,player);
			}
			
			//因以上提前跳出循环后，按顺序再找 alter 2012-10-15
			if(circleTimes >= 30){
				for(int arrIndex=0;arrIndex < rate.length;arrIndex ++){
					if(rate[arrIndex] != 0){
						a = getRandomAwardByLevel(arrIndex + 1,player);
						if(a != null){
							break;
						}
					}
				}
			}	
			
			awards[i] = a;
			//控制和已选的不重复
			int j=0;
			for(int index=0;index<selectedAwardIds.length;index++){
				if(!"-1".equals(selectedAwardIds[index])){
					if(j > i) break;
					selectedAwardIds[index] = String.valueOf(awards[j].getId());
					j ++;
				}
			}
			StringBuffer sb = new StringBuffer();
			for(String id : selectedAwardIds){
				sb.append(id).append(",");
			}
			player.getData().setSelectedAwardIdString(sb.toString());
			
		}
		//logger.info("XXX333");
	}
	
	/**
	 * 玩家刷新宝箱需要的筹码，筹码数量根据转动次数确定
	 * @param round int
	 * @return int
	 */
	private int costChipWhenRefreshByRound(int round){
		return costChipWhenRefresh[round];
	}
	
	/**
	 * 玩家转动花费所需筹码
	 * @param round int
	 * @return int
	 */
	private int costChipWhenTurnByRound(int round){
		return costChipWhenTurn[round - 1];
	}
	
	
	/**
	 * 刷新奖品
	 * @param player
	 */
	public void refresh(PlayerCharacter player){
		
		//获取玩家转动次数
		int round = player.getData().getBoxNum();
		
		//玩家没有转动进行刷新
		if(round == 0){
			
			//刷新几率同开局奖品出现几率
			refreshWith0Round(player);
		}
		
		//玩家转动过后进行刷新
		else{
			refreshAfterStart(round,player);
		}
	}
	
	/**
	 * 转动获取奖品
	 */
	public void getPrize(PlayerCharacter player){
		
		
		//获取玩家转动次数
		int round = player.getData().getBoxNum();
		
		int refreshCostChip;
		int turnCostChip;
		if(round >= 11){
			refreshCostChip = 0;
			turnCostChip = 0;
		}
		else{
			refreshCostChip = costChipWhenRefreshByRound(round + 1);
			turnCostChip = costChipWhenTurnByRound(round + 1 + 1 );
		}
		
		
		player.getData().setTempRefreshCostChip(refreshCostChip);
		player.getData().setTempTurnCostChip(turnCostChip);
		
		//玩家因转动获取宝箱积分
		int gainIntegral = gainIntegralByRound(round);
	
		int boxIntegral = player.getData().getScore();
		int oldScore = boxIntegral;
		boxIntegral += gainIntegral;//玩家转动后当前积分
		player.getData().setScore(boxIntegral);
		//sendMessage(oldScore,player.getData().getScore(), player);
		QuestUtils.checkFinish(player, QuestUtils.TYPE45, true);
		//获取玩家可转动总数
		int totalChance = getPlayerRoundByIntegral(boxIntegral);
		int leftChance = totalChance - (round + 1);
		
		player.getData().setTotalChance(totalChance);
		player.getData().setLeftChance(leftChance);
		
		//判断玩家是否可抽奖
		if(leftChance < 0){
			leftChance = 0;
			return;
		}
		
		//增加玩家转动花费
		int costChip = costChipWhenTurnByRound(round + 1);
		int chip = player.getData().getChip();
		if(chip - costChip >= 0){
			chip -= costChip;
			GameLog.logSystemEvent(LogEvent.COST_CHIP, "" + costChip,TimeUtils.now().format(TimeUtils.FORMAT1));
			costChipPerDay += costChip;
		}
		else{
			return;
		}
		player.getData().setChip(chip);
		
		PrizeRate pr = prizeRates.get(round);
		int[] rate = {pr.getLv1Rate(),pr.getLv2Rate()
				,pr.getLv3Rate(),pr.getLv4Rate()
				,pr.getLv5Rate(),pr.getLv6Rate()
				,pr.getLv7Rate(),pr.getLv8Rate()
				,pr.getLv9Rate(),pr.getLv10Rate()	
		};
		
		int totalRate = 100;//随机奖品时初始总几率
		
		//logger.info("XXX444");
		int lv = MathUtils.getRandomId1(levels, rate, totalRate);
		List<Award> tempAwards = new ArrayList<Award>();
		String selectedAwardIdString = player.getData().getSelectedAwardIdString();
		String[] selectedAwardId = selectedAwardIdString.split(",");
		for(String id : selectedAwardId){
			if(Integer.parseInt(id) != -1){
				Award a = awardDatas.get(Integer.parseInt(id));
				//初步筛选，奖品等级符合条件，转动次数大于底限次数
				if(a.getLevel() == lv && round >= (a.getRound() - 1) ){
					tempAwards.add(a);
				}
			}
		}
		//logger.info("XXX555");
		//如果赌盘中没有同时符合底限次数和奖品等级的物品，重行随机
		while(tempAwards.size() == 0){
			//logger.info("=============getPrize============");
			int removeLvRate = rate[lv - 1];//需要删除的修正几率
			rate[lv - 1] = 0;
			int total = 0;
			for(int r : rate){
				total += r;
			}
			//按百分比转换rate数组
			for(int i=0;i<rate.length;i++){
				rate[i]=rate[i]*totalRate/total;
			}
			
			int nTotal = 0;
			for(int nr : rate){
				nTotal += nr;
			}
			//logger.info("修正几率补差" + (totalRate - nTotal));
			if(totalRate - nTotal > 0){
				for(int i=0;i<rate.length;i++){
					if(rate[i] != 0){
						rate[i] += (totalRate - nTotal);
						break;
					}
				}
			}
			
			//totalRate -= removeLvRate;
			//if(totalRate != 0){//alter 2012-11-13
			lv = MathUtils.getRandomId1(levels, rate, totalRate);
			//}
			
			for(int i=0;i<selectedAwardId.length;i++){
				String id = selectedAwardId[i];
				if(Integer.parseInt(id) != -1){
					Award a = awardDatas.get(Integer.parseInt(id));
					//初步筛选，奖品等级符合条件，转动次数大于底限次数
					if(a.getLevel() == lv && (a.getRound()-1) <= round){
						tempAwards.add(a);
					}
				}
			}
			
			//解决没有满足底限次数的奖品情况下导致的死循环
			if(tempAwards.size() == 0){//alter 2012-11-13
				int minRound = 100;
				for(int i=0;i<selectedAwardId.length;i++){
					String id = selectedAwardId[i];
					if(Integer.parseInt(id) != -1){
						Award a = awardDatas.get(Integer.parseInt(id));
						if(a.getRound() != 12 ){
							if(a.getRound() < minRound){//找最小的底限次数奖品
								minRound = a.getRound();
								tempAwards.add(a);
							}
						}
					}
				}
			}
			
			//解决可能出现几个需要12次才能抽取的奖品而导致死循环
			boolean needBreak = true;
			for(int i=0;i<selectedAwardId.length;i++){
				String id = selectedAwardId[i];
				if(Integer.parseInt(id) != -1){
					Award a = awardDatas.get(Integer.parseInt(id));
					if(a.getRound() != 12 ){
						needBreak = false;
						break;
					}
				}
			}
			if(needBreak){
				for(int i=0;i<selectedAwardId.length;i++){
					String id = selectedAwardId[i];
					if(Integer.parseInt(id) != -1){
						Award a = awardDatas.get(Integer.parseInt(id));
						tempAwards.add(a);
					}
				}
				break;
			}
			
			//所有奖品全抽完
			boolean allGone = true;
			for(int i=0;i<selectedAwardId.length;i++){
				String id = selectedAwardId[i];
				if(Integer.parseInt(id) != -1){
					allGone = false;
				}
			}
			if(allGone){//防止客户端的不合法请求
				break;
			}
			
		}
		//logger.info("XXX666");
		
		//从符合要求的奖品中随机选一个
		int index = MathUtils.random(tempAwards.size());
		Award a = tempAwards.get(index);
		
		//-------------------
		LogBuffer lb = new LogBuffer();
		//lb.add(TimeUtils.nowLong());//某时
		lb.add(a.getId());//获得奖品Id
		lb.add(a.getName());//获得奖品名称
		lb.add(gainIntegral);//转动宝箱所获得的宝箱积分
		GameLog.logPlayerEvent(player, LogEvent.ROLL_BOX, lb);// eg:62478|ROLL_BOX|17|技能书盒lv2|5
		
		GameLog.logSystemEvent(LogEvent.AWARD_LIST, "" + a.getId(),a.getName(),TimeUtils.now().format(TimeUtils.FORMAT1));
		
		//logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1) + "awardId=" + a.getId() + "awardName=" + a.getName());
		String beAwardedIdString = player.getData().getBeAwardedIdString();
		player.getData().setBeAwardedIdString( beAwardedIdString + a.getId() + ",");
		selectedAwardIdString = player.getData().getSelectedAwardIdString();
		String[] selectedAwardIds = selectedAwardIdString.split(",");
		for(int i=0;i<selectedAwardIds.length;i++){
			if(Integer.parseInt(selectedAwardIds[i]) == a.getId()){
				selectedAwardIds[i] = "-1";
			}
		}
		StringBuffer sb = new StringBuffer();
		for(String id : selectedAwardIds){
			sb.append(id).append(",");
		}
		player.getData().setSelectedAwardIdString(sb.toString());
		//玩家转动次数加一
		round ++;

		player.getData().setBoxNum((byte)round);
		
	}
	
	/**
	 * 领取奖品
	 * @param player PlayerCharcater
	 */
	public void receiveAward(PlayerCharacter player){
		
		//获取背包剩余空间
		int leftSpace = GameConst.MAX_STORAGE  - player.getPlayerStorageAgent().getSize();
		
		String beAwardedIdString = player.getData().getBeAwardedIdString();
		String[] beAwardedId = beAwardedIdString.split(",");
		
		if("".equals(beAwardedIdString)){
			String msg = I18nGreeting.getInstance().getMessage("box.reward.nothave", null);
			GameUtils.sendTip(
					new TipMessage(msg, ProcotolType.BOX_RESP,
							GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			//logger.info("奖品列表为空");
		}
		
		
		//如果背包空间充足，（背包剩余空间不足，则提示整理背包，返回的则将还是原来的奖品列表）
		if(leftSpace > beAwardedId.length){
			
			//领取奖品
			Award receive= null;
			for(String id : beAwardedId){
				if(!"".equals(id)){
					Award a = awardDatas.get(Integer.parseInt(id));
					//按类型将奖品放入背包
					addAwardToPackageByType(a,player);
					if(a.getLevel() > 4){
						receive = a;
					}
				}
			}
			//发送消息
			if(receive != null && receive.getLevel() > 5){
				String msg = I18nGreeting.getInstance().getMessage("box.get", new Object[]{player.getData().getName(),receive.getDesc()});
				NoticeManager.getInstance().sendSystemWorldMessage(msg);
			}
			
			
			//获取玩家已领取的奖品
			StringBuffer sb = new StringBuffer();
			String reciveAwardIdString = player.getData().getRecivedAwardIdString();
			sb.append(reciveAwardIdString).append(beAwardedIdString);
			
			//设置玩家已领取的奖品
			player.getData().setRecivedAwardIdString(sb.toString());
			
			//放入背包后清空抽中奖品
			player.getData().setBeAwardedIdString("");
		}
		else{//背包空间不足
			String msg = I18nGreeting.getInstance().getMessage("box.space.notenough", null);
			GameUtils.sendTip(
					new TipMessage(msg, ProcotolType.BOX_RESP,
							GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			//logger.info("背包空间不足");
		}
	}
	
	/**
	 * 重新开始
	 * @param player PlayerCharcater
	 */
	public void restart(PlayerCharacter player){
		player.getData().setTempTurnCostChip(1);
		//设置玩家转动次数为0
		player.getData().setBoxNum((byte)0);
		
		//获取玩家可转动总数，和剩余转动次数
		int totalChance = getPlayerRoundByIntegral(player.getData().getScore());
		int leftChance = totalChance ;
		player.getData().setTotalChance(totalChance);
		player.getData().setLeftChance(leftChance);
		player.getData().setTempRefreshCostChip(0);
		
		//重新生成奖品
		refreshWith0Round(player);
		
		
	}
	
	/**
	 * 按类型将奖品加入背包
	 * @param award
	 */
	private void addAwardToPackageByType(Award award,PlayerCharacter player){
		int type = award.getType();
		Cell cell =  null;
		switch(type){
			case 1://金币
				player.saveResources(GameConfig.GAME_MONEY, award.getValue());
				break;
			case 2://功勋
				player.saveResources(GameConfig.AWARD, award.getValue());
				break;
			case 3://物品
				cell = player.getPlayerStorageAgent().addPropsCell(award.getValue(), award.getQuantity());
				break;
			case 4://积分
				int score = player.getData().getScore();
				player.getData().setScore(score + award.getValue());
				//sendMessage(score,player.getData().getScore(), player);
				break;
			case 5://筹码
				int chip = player.getData().getChip();
				player.getData().setChip(chip + award.getValue());
				break;
			case 6://钻石
				player.saveResources(GameConfig.JOY_MONEY, award.getValue());
			
		}
		//主动推送客户端
		RespModuleSet rms=new RespModuleSet(ProcotolType.BOX_RESP);
		rms.addModule(cell);
		rms.addModule(player.getData());
		AndroidMessageSender.sendMessage(rms,player);
	}
	
	/**
	 * 奖品是否已被抽中
	 * @param award Award
	 * @return
	 */
	private boolean isAlreadyAwarded(Award award,PlayerCharacter player){

		String recivedAwardIdString = player.getData().getRecivedAwardIdString();
		String beAwardedIdString = player.getData().getBeAwardedIdString();
		if(!"".equals(recivedAwardIdString)){
			String[] beAwardedId = recivedAwardIdString.split(",");
			for(String id : beAwardedId){
				if(Integer.parseInt(id) == award.getId()){
					return true;
				}
			}
		}
		if(!"".equals(beAwardedIdString)){
			String[] beAwardedId = beAwardedIdString.split(",");
			for(String id : beAwardedId){
				if(Integer.parseInt(id) == award.getId()){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 根据玩家宝箱积分得到玩家可转动次数
	 * @param integral int
	 */
	private int getPlayerRoundByIntegral(int integral){
		if(integral >= 0 && integral < 100){
			return 6;
		}
		if(integral >= 100 && integral < 200){
			return 7;
		}
		if(integral >= 200 && integral < 500){
			return 8;
		}
		if(integral >= 500 && integral < 1000){
			return 9;
		}
		if(integral >= 1000 && integral < 2000){
			return 10;
		}
		if(integral >= 2000 && integral < 5000){
			return 11;
		}
		if(integral >= 5000){
			return 12;
		}
		return 0;
		
	}
	
	/**
	 * 按转动次数获得积分
	 * @param round int
	 * @return
	 */
	private int gainIntegralByRound(int round){
		
		return gainIntegral[round];
		
	}
	
	/**
	 * 购买筹码
	 * @param number int
	 * @return
	 */
	public boolean buyChip(int number,PlayerCharacter player){
		if(player.saveResources(GameConfig.JOY_MONEY, -1 * number) >= 0){
			//logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " uid=" + player.getId() 
					//+ " uname=" + player.getData().getName() + " diamondNum=" + number + " description=购买筹码");
			GameLog.logSystemEvent(LogEvent.USE_DIAMOND, String.valueOf(GameConst.DIAMOND_CHIP),"", String.valueOf(number),String.valueOf(player.getId()));

			player.saveResources(GameConfig.CHIP, number);
			return true;
		}
		return false;
	}
	
	/**
	 * 发送系统消息
	 * @param oldScore 玩家获得积分前的积分
	 * @param score 玩家获得积分后的积分
	 * @param pc
	 */
	public void sendMessage(int oldScore,int score,PlayerCharacter pc){
		String msg ="";
		if(oldScore < 2000){
			if(score >= 2000){
				msg = I18nGreeting.getInstance().getMessage("duwang", new Object[]{pc.getData().getName()});
			}	 
		}
		else if(oldScore < 5000){
			if(score >=5000){
				 msg = I18nGreeting.getInstance().getMessage("dusehng", new Object[]{pc.getData().getName()});
			}
		}
		
		NoticeManager.getInstance().sendSystemWorldMessage(msg);
	}
	
	
	
}
