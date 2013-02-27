package com.joymeng.game.domain.quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.joymeng.core.Tickable;
import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.event.GameEvent;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogBuffer;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.mod.FightInfo;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.role.PlayerAgent;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.soldier.Soldier;
import com.joymeng.game.domain.soldier.SoldierManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipModule;
import com.joymeng.game.net.client.ClientModule;
//完成次数		该任务的已经完成次数小于最大完成次数				
//
//刷新类型	0	不刷新				
//	1	每天0：00将最大完成次数置为1				
//						
//每日任务处理		每天从所有的满足领取条件的每日任务中				
//		1、随机出5个任务				
//		2、将其他的未随机到的任务最大完成次数置为0				
//		3、随机出的任务随机排序，1，2，3，4，5。				
//		4、将第2个任务的前提任务置为第1个任务的ID，3置为2的。类推，1不处理				

public class PlayerQuestAgent implements PlayerAgent, Tickable, TipModule {
	// 任务状态
	public static final byte ACCEPTED = 1;// 已经接了
	public static final byte UNCOMPLETE = 2;// 未完成的
	public static final byte COMPLETE = 3;// 完成的
	// 任务类型
	public static final byte TYPE_NORMAL = 1;// 普通
	public static final byte TYPE_DAILY = 2;// 日常
	public static final byte TYPE_ACTIVITY = 3;// 活动
	//
	QuestManager questManager=QuestManager.getInstance();
	// 已经完成的任务
	List<Quest> completedQuests = new ArrayList<Quest>();
	// 已接任务列表
	List< AcceptedQuest> acceptedQuests = new ArrayList<AcceptedQuest>();
	// 日常任务
	private List<Quest> dailyQuest = new ArrayList<Quest>();
	// 提示消息
	public TipMessage tip = new TipMessage("任务失败", ProcotolType.MISSION_RESP,
			GameConst.GAME_RESP_FAIL);

	public String getAccepted() {
		return "";
	}

	public String getCompleted() {
		return "";
	}

	public PlayerQuestAgent(PlayerCharacter role) {
		this.pc = role;
		// 人物升级事件
		// role.registEventListener(this, GameEvent.EVT_PLAYER_LEVEL_UP);
		String acceptedStr = role.getData().getAcceptedQuests();
		//初始化已经接受的任务
		acceptedQuests=questManager.initAcceptedQuest(acceptedStr);
		for(AcceptedQuest aq:acceptedQuests){
			//更新任务状态
			updateQuestRate(aq);
		}
		String completedStr = role.getData().getCompletedQuests();
		completedQuests=questManager.initQuestList(completedStr);
		String dailyStr = role.getData().getDailyQuests();
		dailyQuest=questManager.initQuestList(dailyStr);
		clearOtherTask();
		clearDailyTask();
	}
	/**
	 * 取得已经完成的任务类型的最大id
	 * @param type
	 * @return
	 */
	public int getCompleteMaxId(byte type){
		int maxId=0;
		for(Quest q:completedQuests){
			if(q.getType()==type){
				if(maxId<q.getId()){
					maxId=q.getId();
				}
			}
		}
		return maxId;
	}

	/**
	 * 还原为roledata的字符串
	 */
	public void save() {
		logger.info("==========================保存任务==============================");
		// 还原已经接受的任务
		String str[]=null;
		str=questManager.saveAcceptedQuest(acceptedQuests);
//		logger.info(GameUtils.getStringArrayLog(str, "---------已经接受任务，"));
		pc.getData().setAcceptedQuests(StringUtils.recoverNewStr(str, ";"));
		// 还原已经完成的任务
		str=questManager.saveList(completedQuests);
//		logger.info(GameUtils.getStringArrayLog(str, "---------已经完成任务，"));
		pc.getData().setCompletedQuests(StringUtils.recoverNewStr(str, ";"));
		// 还原每日任务
		str=questManager.saveList(dailyQuest);
//		logger.info(GameUtils.getStringArrayLog(str, "---------每日任务剩余，"));
		pc.getData().setDailyQuests(StringUtils.recoverNewStr(str, ";"));
	}

	public void clearOtherTask() {
		// 如果是同一天，则不清除
		if (TimeUtils.isSameDay(pc.getData().getLastLoginTime())) {
			return;
		}
		// 遍历所有任务找到类型不是每日任务的
		List<Quest> list = QuestManager.getInstance().getQuestListButThisType(
				TYPE_DAILY);
		for (Quest q : list) {
			if (q.getRefreshType() == 1) {// 处理那些需要每日刷新的
				// 从已经完成的任务列表中清理
				for (int i = 0; i < completedQuests.size(); i++) {
					if (q.getId() ==completedQuests.get(i).getId()) {
						completedQuests.remove(i);
						i--;
					}
				}
			}
		}
	}

	/**
	 * 清除每日任务
	 */
	public void clearDailyTask() {
		// 如果是同一天，则不清除
		if (TimeUtils.isSameDay(pc.getData().getLastLoginTime())) {
			return;
		}
		// 清除已经接受的日常任务
		for(int i=0;i<acceptedQuests.size();i++){
			AcceptedQuest aq = acceptedQuests.get(i);
			Quest quest =aq.getQ();
			if (quest != null) {
				if (quest.getType() == TYPE_DAILY) {// 如果是日常任务
					acceptedQuests.remove(i);
					i--;
				}
			}
		}
		// 生成新的日常任务链,从所有日常任务中找到5个任务
		dailyQuest.clear();
		createDailyQuestLinked();
		getDailyQuset();
	}
	/**
	 * 生成每日任务链
	 */
	public void createDailyQuestLinked(){
		//获得所有的每日任务
		List<Quest> questList = QuestManager.getInstance().getQuestList(
				TYPE_DAILY);
		// 随机最多5个
		int i = 0;
		while (i <5&&questList.size()>0) {
			int random = MathUtils.random(questList.size());
			Quest q = questList.get(random);
			System.out.println("add1 quest,id="+q.getId()+" LV1="+q.getNeedLevel()+" LV2="+q.getMaxLevel());
			if (q != null) {
				int lv=pc.getData().getLevel()/5;
				if((lv)*5<=q.getNeedLevel()&&q.getMaxLevel()<(lv+1)*5){//每日任务加入，当前级别所在 【1-5】区间内的任务
					System.out.println("add quest,id="+q.getId());
					dailyQuest.add(q);
					i++;
				}
			}
			questList.remove(random);
		}
//		for (Quest q : dailyQuest) {
//			logger.info("new dailyTask==" + q.getName() + " id=" + q.getId());
//		}
		
	}
	/**
	 * 接取一个每日任务
	 */
	public void getDailyQuset(){
		if (dailyQuest.size() > 0) {
			// 自动接取第一个
			addQuest(dailyQuest.get(0));
			// 从任务链中移除第一个
			dailyQuest.remove(0);
		}
	}
	/**
	 * 在升级的时候加入日常任务
	 */
	public void addDailyQuestOnLevelup() {
		// 先判断是否已经接了日常任务
		if(this.pc.getData().getLevel()==5){//5级的时候生成一次日常任务链并自动接取
			createDailyQuestLinked();
			getDailyQuset();
			sendTaskToClient();
		}
	}

	/**
	 * 加入一个任务
	 * 
	 * @param id
	 * @return
	 */
	public boolean addQuest(Quest q) {
		StringBuilder sb=new StringBuilder();
		sb.append(" 尝试加入任务，id="+q.getId());
		if (q == null) {
			sb.append("任务不存在，id=" + q.getId());
			logger.info(sb.toString());
			return false;
		}
		// logger.info("加入任务，id=" + q.getId());
//		if (acceptedQuests.get(q.getId()) == null) {
			// 判断条件
			int needLevel = q.getNeedLevel();// 领取条件君主等级
			int needNobility = q.getNeedNobility();// 领取条件君主爵位
			int maxLevel = q.getMaxLevel();
//			int preId = q.getPreId();// 领取条件前提任务
			if (pc.getData().getLevel() < needLevel
					|| pc.getData().getLevel() > maxLevel) {
				sb.append(" 级别不满足条件，玩家级别=" + pc.getData().getLevel()
						+ " 需求级别=" + needLevel + "-" + maxLevel + " questid="
						+ q.getId());
				logger.info(sb.toString());
				return false;
			}
			if (pc.getData().getCityLevel() < needNobility) {
				sb.append(" 爵位不满足条件，玩家爵位=" + pc.getData().getCityLevel()
						+ " 需求爵位=" + needNobility + " questid=" + q.getId());
				logger.info(sb.toString());
				return false;
			}
//			boolean b = false;
//			if (preId == -1) {//不检测前置任务
//				b = true;
//			} else {
//				//检测前置任务，该任务完成过
//				for (String str : completedQuests) {
//					int id = Integer.parseInt(str);
//					if (id == preId) {
//						b = true;
//						break;
//					}
//				}
//			}
//			if (!b) {
//				sb.append(" 前置任务未完成，前置任务id=" + preId );
//				logger.info(sb.toString());
//				return false;
//			} else {
				AcceptedQuest aq = new AcceptedQuest(q, ACCEPTED);
				acceptedQuests.add(aq);
				sb.append(" 加入任务成功，id=" + q.getId());
				if(q.getTarget()==9){
					//清除该类型的次数
					FightInfo fi=new FightInfo(pc.getData().getFightInfo());
					fi.clearFightNum((byte)q.getTargetArgs1());
					pc.getData().setFightInfo(fi.toStr());
				}
				this.updateQuestRate(aq);
				// 需要接取的时候进行判断是否完成
				acceptCheck();
				logger.info(sb.toString());
				tip.setMessage("领取奖励成功");
				return true;
//			}
			
//		} else {
//			sb.append("未接受过该任务，id=" + q.getId());
//			logger.info(sb.toString());
//			return false;
//		}
	}

	/**
	 * 判断接取后的任务是否可以完成
	 */
	public void acceptCheck() {
		logger.info("acceptCheck start....................");
		QuestUtils.checkFinish(pc, QuestUtils.TYPE9, false);
		QuestUtils.checkFinish(pc, QuestUtils.TYPE27, false);
		QuestUtils.checkFinish(pc, QuestUtils.TYPE28, false);
		List<PlayerBuilding> buldLst = pc.getPlayerBuilgingManager()
				.getPlayerAll();// 遍历全部建筑
		for (PlayerBuilding pb : buldLst) {
			QuestUtils.checkFinish(pc, QuestUtils.TYPE3, false, pb.getBuildingID(),
					pb.getBuildLevel());
		}
		QuestUtils.checkFinish(pc, QuestUtils.TYPE33, false);
		QuestUtils.checkFinish(pc, QuestUtils.TYPE30, false);
		QuestUtils.checkFinish(pc, QuestUtils.TYPE31, false);
		QuestUtils.checkFinish(pc, QuestUtils.TYPE32, false);
		QuestUtils.checkFinish(pc, QuestUtils.TYPE35, false);
		QuestUtils.checkFinish(pc, QuestUtils.TYPE42, false);
		QuestUtils.checkFinish(pc, QuestUtils.TYPE45, false);
		QuestUtils.checkFinish(pc, QuestUtils.TYPE36, false);
		QuestUtils.checkFinish(pc, QuestUtils.TYPE39, false);
		//如果级别大于县长立刻完成任务43
		byte b=pc.getData().getTitle();
		if(b==GameConst.TITLE_MAYOR_CITY||b==GameConst.TITLE_GOVERNOR||b==GameConst.TITLE_KING){
			QuestUtils.checkFinish(pc, QuestUtils.TYPE43, false);
		}
		logger.info("acceptCheck end....................");
		//调用该方法的模块都会推送任务信息，这里不需要再发送
//		RespModuleSet rms = new RespModuleSet(ProcotolType.MISSION_RESP);// 模块消息
//		Iterator<Integer> it = acceptedQuests.keySet().iterator();
//		while (it.hasNext()) {
//			AcceptedQuest q = acceptedQuests.get(it.next());
//			rms.addModule(q);
//		}
//		AndroidMessageSender.sendMessage(rms, pc);
	}

	/**
	 * 查找是否能完成任务
	 * 
	 * @param type
	 * @return
	 */
	public List<AcceptedQuest> getList(byte type) {
		List<AcceptedQuest> list = new ArrayList<AcceptedQuest>();
		for(AcceptedQuest aq:acceptedQuests){
			Quest quest =aq.getQ();
			if (quest.getTarget() == type&&aq.getStatus()!=COMPLETE) {
				list.add(aq);
			}
		}
		return list;
	}

	/**
	 * 完成任务
	 * 
	 * @param q
	 * @return
	 */
	public boolean finishQuest(AcceptedQuest aq) {
		if (aq == null) {
			tip.setMessage("未接受过该任务，id=" + aq.getQ().getId());
			return false;
		}
		if (aq.getStatus() != COMPLETE) {
			tip.setMessage("任务状态不对，id=" + aq.getQ().getId() + " status="
					+ aq.getStatus());
			return false;
		}
		//写入日志  玩家 在日期xx完成任务xxid,type,当前级别
		GameLog.logPlayerEvent(pc, LogEvent.QUESTINFO, new LogBuffer().add(aq.getQ().getId()).add(aq.getQ().getType()).add(pc.getData().getLevel()));
		// 从已经接受的列表中删除任务
		delAcceptQuest(aq);
		// 加入到完成任务列表中
		completedQuests.add(aq.getQ());
		GameUtils.putToCache(pc);
		// 获得奖励
		List<Cell> list = getReward(aq.getQ());
		// 查找是否有下一个可接任务
		searchQuest(aq.getQ());
		//保存任务信息
		pc.getPlayerQuestAgent().save();
		// 发送玩家信息，任务列表
		RespModuleSet rms = new RespModuleSet(ProcotolType.MISSION_RESP);// 模块消息
		// 玩家信息
		rms.addModule(pc.getData());
		// 物品信息
		for (Cell cell : list) {
			rms.addModule(cell);
		}
		// 任务信息
		for(AcceptedQuest _q:acceptedQuests){
			rms.addModule(_q);
			logger.info("finishQuest uid="+pc.getId()+" questId="+_q.getQ().getId()+" status="+_q.getStatus());
		}
		AndroidMessageSender.sendMessage(rms, pc);
		return true;
	}

	/**
	 * 查找是否有可以接受的任务
	 * 
	 * @param preQuest
	 *            之前完成的任务
	 */
	public void searchQuest(Quest preQuest) {
		if (preQuest.getType() == TYPE_DAILY) {// 如果是日常任务
			getDailyQuset();
		}else{
			List<Quest> list = QuestManager.getInstance().search(completedQuests,preQuest);
			for (Quest q : list) {
				addQuest(q);
				// break;//测试阶段一次只接受一个
			}
		}
	}

	public List<Cell> getReward(Quest q) {
		List<Cell> list = new ArrayList<Cell>();
		if (q == null) {
			tip.setMessage("任务不存在，id= 对象为空");
			return list;
		}
		logger.info("获得任务奖励，id=" + q.getId());
		// 处理经验
		int exp = q.getRewardExp();
		pc.saveExp(exp);
		// 处理金钱
		int money = q.getRewardGold();
		pc.saveResources(GameConfig.GAME_MONEY, money);
		// 处理道具
		String iStr = q.getRewardItems();
		if (!iStr.equals("") && !iStr.equals("-1")) {
			String str[] = iStr.split(",");
			for (int i = 0; i < str.length; i++) {
				String[] s = str[i].split(":");
				Cell cell = pc.getPlayerStorageAgent().addPropsCell(
						Integer.parseInt(s[0]), Integer.parseInt(s[1]));
				logger.info("获得道具id=" + cell.getItem().getId());
				list.add(cell);
			}
		}
		// 处理装备
		String eStr = q.getRewardEquips();
		if (!eStr.equals("") && !eStr.equals("-1")) {
			String str[] = eStr.split(",");
			for (int i = 0; i < str.length; i++) {
				String[] s = str[i].split(":");
				Equipment eq = EquipmentManager.getInstance().getEqu(
						Integer.parseInt(s[0]), Integer.parseInt(s[1]), 0);
				List<Cell> cellList = pc.getPlayerStorageAgent()
						.addEquis(eq, 1);
				for (Cell cell : cellList) {
					logger.info("获得装备id=" + cell.getItem().getId());
					list.add(cell);
				}
			}
		}
		return list;
	}

	/**
	 * 更新指定类型的任务 初始化之后更新，checkfinish后更新
	 * 
	 * @return
	 */
	public boolean updateQuestRate(AcceptedQuest aq) {
		boolean b = false;
		Quest q =aq.getQ();
		switch (q.getTarget()) {
		case 9:// 更新战斗次数
			b = true;
			FightInfo fi = new FightInfo(pc.getData().getFightInfo());
			aq.setInfo(q.getTargetArgs2() + ":"
					+ fi.getFightNum((byte) q.getTargetArgs1()));
			break;
		case 30:
			b = true;
			aq.setInfo(q.getTargetArgs1() + ":"
					+ pc.getPlayerBuilgingManager().getFucityNum());
			break;
		case 32:
			b = true;
			aq.setInfo(q.getTargetArgs1() + ":"
					+ pc.getPlayerHeroManager().getHeroArray().length);
			break;
		case 33:
			b = true;
			aq.setInfo(q.getTargetArgs1() + ":"
					+ pc.getPlayerBuilgingManager().allSoliderCount());
			break;
		case 35:
			b = true;
			aq.setInfo(q.getTargetArgs1() + ":"
					+ pc.getPlayerBuilgingManager().allSoliderEqu());
			break;
		}
//		logger.info("更新任务进度，"+aq.print());
		return b;
	}

	/**
	 * 当前玩家引用
	 */
	PlayerCharacter pc;

	@Override
	public List<ClientModule> handleEvent(GameEvent event) {
		switch (event.getCode()) {
		case GameEvent.EVT_PLAYER_LEVEL_UP:
			System.out.println("test player levelUp");
			// RespModuleSet moduleSet = new
			// RespModuleSet(ProcotolType.USER_INFO_REQ);
			// AndroidMessageSender.sendMessage(moduleSet, role);
			break;
		}
		return null;
	}

	@Override
	public void tick() {
		// 清除每日任务
	}

	@Override
	public void writeToEntity(RoleData data) {

	}

	@Override
	public void readFromEntity(RoleData data) {

	}

	public List<AcceptedQuest> getAcceptedQuests() {
		return acceptedQuests;
	}

	public void setAcceptedQuests(List<AcceptedQuest> acceptedQuests) {
		this.acceptedQuests = acceptedQuests;
	}

	@Override
	public TipMessage getTip() {
		return tip;
	}

	@Override
	public void setTip(TipMessage tip) {
		this.tip = tip;
	}
	/**
	 * 向客户端推送任务信息
	 */
	public void sendTaskToClient(){
		RespModuleSet rms = new RespModuleSet(ProcotolType.MISSION_RESP);// 模块消息
		for(AcceptedQuest aq:acceptedQuests){
			rms.addModule(aq);
		}
		AndroidMessageSender.sendMessage(rms, pc);
	}
	public AcceptedQuest getAcceptQuest(int id){
		for(AcceptedQuest aq:acceptedQuests){
			if(aq.getQ().getId()==id){
				return aq;
			}
		}
		return null;
	}
	public void delAcceptQuest(AcceptedQuest _aq){
		for(AcceptedQuest aq:acceptedQuests){
			if(aq.getQ().getId()==_aq.getQ().getId()){
				acceptedQuests.remove(aq);
				break;
			}
		}
	}
}