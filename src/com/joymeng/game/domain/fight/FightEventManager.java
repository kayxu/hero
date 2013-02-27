package com.joymeng.game.domain.fight;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.award.ArenaAward;
import com.joymeng.game.domain.card.SimpleAward;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.PlayerStorageAgent;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.World;

/**
 * 玩家战报管理
 * @author madi
 *
 */
public class FightEventManager {

	private static final Logger logger = LoggerFactory.getLogger(FightEventManager.class);
	AtomicInteger index=new AtomicInteger();
	/**
	 * 
	 */
	private static final int EVENT_NUM = 25;
	
	PlayerCharacter player;
	
	private int totalPages = 0;
	
	private int currentPage = 0;
	
	private int totalSize = 0;
	private boolean isLoaded;
	public List<FightEvent> fightEventList = new ArrayList<FightEvent>();
//	PriorityQueue<FightEvent> fightEventList = new PriorityQueue<FightEvent>(EVENT_NUM,
//			new MyComparator());
	
	/**
	 * 系统战斗开放信息
	 */
	public static List<Battlefield> battlefields = new ArrayList<Battlefield>();
	
	
	/**
	 * 玩家个然系统奖励信息
	 */
	public List<ArenaAward> personalSystemAwards = new ArrayList<ArenaAward>();
	
	public FightEventManager(PlayerCharacter player){
		this.player=player;
		//this.init();
	}
	
	public static FightEvent stageFightEvent;
	
	public static void loadStageFightEvent(String path) throws Exception{
		//logger.info("------------------------------系统阶段战报----------------------暂时不需要了");
//		stageFightEvent = DBManager.getInstance().getWorldDAO().getFightEventDAO().getStageFightEvent();
		//logger.info(stageFightEvent.getMemo());
		//logger.info("----------------------------系统阶段战报---------------------");
		
		//logger.info("-----------------------------加载系统战斗开放信息-------------");
		List<Object> list = GameDataManager.loadData(path, Battlefield.class);
		for(Object o : list){
			battlefields.add((Battlefield) o);
		}
		//logger.info("记录条数:" + battlefields.size());
		//logger.info("-----------------------------加载系统战斗开放信息完毕-------------");
		
		
		/*//logger.info("-----------------------------加载系统奖励信息-------------");
		List<Object> list2 = GameDataManager.loadData(path, SystemAward.class);
		for(Object o : list2){
			systemAwards.add((SystemAward)o);
		}
		//logger.info("-----------------------------加载系统奖励信息完毕-------------");*/
	}
	
	
	public void init(){
		isLoaded=true;
//		List<FightEvent> list = DBManager.getInstance().getWorldDAO().getFightEventDAO().getCertainFightEventByUserId((int)player.getData().getUserid());
		List<?> list=MongoServer.getInstance().getLogServer().getFightEvent(player.getId(), FightEvent.class);
		fightEventList.clear();
		for(int i=0;i<list.size();i++){
			FightEvent fe=(FightEvent)list.get(i);
			fe.setIndex(index.incrementAndGet());
			fightEventList.add(fe);
		}
		Collections.sort(fightEventList);
		fightEventList=fightEventList.subList(0, fightEventList.size()>EVENT_NUM?EVENT_NUM:fightEventList.size());
		//logger.info("加载玩家战报消息成功，战报记录条数" + fightEventList.size());
		
		//---------------just for test method addFightEvent()-------------
		/*FightEvent fightEvent = new FightEvent();
		fightEvent.setUserId((int)player.getData().getUserid());
		fightEvent.setHeroId(2037);
		fightEvent.setType((byte)1);
		fightEvent.setResult((byte)1);
		fightEvent.setMemo("test memeo 1");
		fightEvent.setData("test data 1");
		fightEvent.setFightTime(TimeUtils.nowLong());
		addFightEvent(fightEvent);*/
	}
	/**
	 * 增加一条站报消息
	 * @param fightEvent
	 */
	public FightEvent addFightEvent(FightEvent fightEvent){
		if(fightEvent==null){
			return null;
		}
//		int id = DBManager.getInstance().getWorldDAO().getFightEventDAO().saveFightEvent(fightEvent);
		fightEvent.setNew(true);
		fightEvent.setDate(new Date());
		fightEvent.setIndex(index.incrementAndGet());
		fightEventList.add(fightEvent);
		//保存战报
		MongoServer.getInstance().getBgServer().save("fightEvent", fightEvent);
		Collections.sort(fightEventList);
		//获得当前战报列表的长度
		int size=fightEventList.size();
		if(size>EVENT_NUM){
			//移除第一条,并从数据库中删除
//			FightEvent fe=fightEventList.poll();
//			int num=0;
			for(int i=EVENT_NUM-1;i<fightEventList.size();i++){
				//MongoServer.getInstance().removeFightEvent(fightEventList.get(i));
				logger.info(fightEventList.get(i).getId());
//				num++;
			}
//			logger.info("需要删除战报="+num);
			fightEventList = fightEventList.subList(0, EVENT_NUM);
		}
		//战报的删除，交给mongo的ttl时间过期来处理
		
		return fightEvent;
	}
//	public int getNum(){
//		return fightEventList.size();
//	}
	/**
	 * 按类型查找战报消息
	 * @param type byte
	 * @return
	 */
	public List<FightEvent> search(byte type){
		List<FightEvent> list=new ArrayList<FightEvent>();
		for(FightEvent event:fightEventList){
			if(event.getType()==type){
				list.add(event);
			}
		}
		return list;
	}
	
	/**
	 * 个人所有战报消息
	 */
	public void personalFightEvent(int requestPage,int pageSize){
		if(!isLoaded){
			init();
		}
		totalSize = fightEventList.size();
		
		if(totalSize % pageSize == 0){
			totalPages = totalSize/pageSize;
		}
		else{
			totalPages = (totalSize/pageSize) + 1;
		}
		
		int fromIndex = 0;
		int endIndex = 0;
		
		//请求最后一页
		if(requestPage == 0){
			if(totalSize % pageSize == 0){
				fromIndex = totalSize - pageSize;
				endIndex = totalSize;
				currentPage = totalSize / pageSize;
			}
			else{
				fromIndex = totalSize - (totalSize % pageSize);
				endIndex = totalSize;
				currentPage = (totalSize / pageSize) + 1;
			}
			
		}
		else{
			//如果允许翻上一页
			if(requestPage >= 1){
				fromIndex = (requestPage - 1) * pageSize;
				if((fromIndex + pageSize) > totalSize){
					endIndex = totalSize;
				}
				else{
					endIndex = fromIndex + pageSize;
				}
				currentPage = requestPage;
			}
		}
		
		List<FightEvent> needFightEventList = new ArrayList<FightEvent>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			Collections.sort(fightEventList);
			needFightEventList = fightEventList.subList(fromIndex, endIndex);
		}
		
		RespModuleSet rms=new RespModuleSet(ProcotolType.BATTLE_FIELD_RESP);
		SimplePage sp = new SimplePage();
		sp.setCurrentPage(currentPage);
		sp.setTotalPages(totalPages);
		rms.addModule(sp);
		for(FightEvent fe : needFightEventList){
//			int id = fe.getId();
			rms.addModule(fe);
		}
		AndroidMessageSender.sendMessage(rms,player);
	}
	
	/**
	 * 按战斗类型分页查询战报消息
	 * @param id
	 * @return
	 */
	public void queryByTypeForPage(int type,int requestPage,int pageSize){
		List<FightEvent> fightEventListForType = search((byte)type);
		totalSize = fightEventListForType.size();
		
		if(totalSize % pageSize == 0){
			totalPages = totalSize/pageSize;
		}
		else{
			totalPages = (totalSize/pageSize) + 1;
		}
		
		int fromIndex = 0;
		int endIndex = 0;
		
		//请求最后一页
		if(requestPage == 0){
			if(totalSize % pageSize == 0){
				fromIndex = totalSize - pageSize;
				endIndex = totalSize;
				currentPage = totalSize / pageSize;
			}
			else{
				fromIndex = totalSize - (totalSize % pageSize);
				endIndex = totalSize;
				currentPage = (totalSize / pageSize) + 1;
			}
			
		}
		else{
			//如果允许翻上一页
			if(requestPage >= 1){
				fromIndex = (requestPage - 1) * pageSize;
				if((fromIndex + pageSize) > totalSize){
					endIndex = totalSize;
				}
				else{
					endIndex = fromIndex + pageSize;
				}
				currentPage = requestPage;
			}
		}
		
		List<FightEvent> needFightEventList = new ArrayList<FightEvent>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			//截取所需要的战报消息信息
			needFightEventList = fightEventListForType.subList(fromIndex, endIndex);
		}
		
		RespModuleSet rms=new RespModuleSet(ProcotolType.BATTLE_FIELD_RESP);
		SimplePage sp = new SimplePage();
		sp.setCurrentPage(currentPage);
		sp.setTotalPages(totalPages);
		rms.addModule(sp);
		for(FightEvent fe : needFightEventList){
//			int id = fe.getId();
			rms.addModule(fe);
		}
		AndroidMessageSender.sendMessage(rms,player);
	}
	
	//按战报id获取玩家战斗详细即观战
	public FightEvent seeFightEvent(int idx){
		//logger.info(">>>see fight event");
//		FightEvent fightEvent = DBManager.getInstance().getWorldDAO().getFightEventDAO().getFightEventById(id);
		Object[] array=fightEventList.toArray();
		//logger.info(fightEvent.getId() + " " + fightEvent.getMemo() + " " + fightEvent.getData());
		for(int i=0;i<array.length;i++){
			FightEvent fe=(FightEvent)array[i];
			if(fe.getIndex()==idx){
				return fe; 
			}
		}
		return null;
	}
	
	
	public void getSystemBattleFieldInfo(int requestPage ,int pageSize){
		List<Battlefield> battlefieldList = new ArrayList<Battlefield>();
		for(Battlefield bf : battlefields){
			String startTime = bf.getStartTime();
			String endTime = bf.getEndTime();
			if("00:00:00".equals(startTime) && "00:00:00".equals(endTime)){//如果是全天开启战斗
				battlefieldList.add(bf);
			}
			else{
				Date nowDate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String nowTime = sdf.format(nowDate);
				if(endTime.compareTo(startTime) > 0){//如果结束时间晚于开始时间，说明是当天
					if(nowTime.compareTo(startTime) >= 0 && nowTime.compareTo(endTime) < 0){
						battlefieldList.add(bf);
					}
				}
				else{//如果时间段跨天
					if((nowTime.compareTo(startTime) >= 0 && nowTime.compareTo("23:59:59") <= 0)
							||(nowTime.compareTo("00:00:00") >= 0 && nowTime.compareTo(endTime) < 0)){
						battlefieldList.add(bf);
					}
				}
			}
			
		}
		
		totalSize = battlefieldList.size();
		
		if(totalSize % pageSize == 0){
			totalPages = totalSize/pageSize;
		}
		else{
			totalPages = (totalSize/pageSize) + 1;
		}
		
		int fromIndex = 0;
		int endIndex = 0;
		
		/*//请求最后一页
		if(requestPage == 0){
			if(totalSize % pageSize == 0){
				fromIndex = totalSize - pageSize;
				endIndex = totalSize;
				currentPage = totalSize / pageSize;
			}
			else{
				fromIndex = totalSize - (totalSize % pageSize);
				endIndex = totalSize;
				currentPage = (totalSize / pageSize) + 1;
			}
			
		}
		else{*/
			//如果允许翻上一页
			if(requestPage >= 1){
				fromIndex = (requestPage - 1) * pageSize;
				if((fromIndex + pageSize) > totalSize){
					endIndex = totalSize;
				}
				else{
					endIndex = fromIndex + pageSize;
				}
				currentPage = requestPage;
			}
		//}
		
		List<Battlefield> needBattlefieldList = new ArrayList<Battlefield>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			//截取所需要的战报消息信息
			needBattlefieldList = battlefieldList.subList(fromIndex, endIndex);
		}

		RespModuleSet rms=new RespModuleSet(ProcotolType.BATTLE_FIELD_RESP);
		SimplePage sp = new SimplePage();
		sp.setCurrentPage(currentPage);
		sp.setTotalPages(totalPages);
		rms.addModule(sp);
		//AndroidMessageSender.sendMessage(rms,player);
		//RespModuleSet rms2=new RespModuleSet(ProcotolType.BATTLE_FIELD_RESP);
		for(Battlefield bf : needBattlefieldList){

			rms.addModule(bf);
		}
		AndroidMessageSender.sendMessage(rms,player);
		
	}
	
	
	/**
	 * 获取个人系统奖励消息
	 * @param requestPage 请求页
	 * @param pageSize 页面记录条数
	 */
	public void getSystemAwardInfo(int requestPage,int pageSize){

		//if(personalSystemAwards == null || personalSystemAwards.size() == 0 ){
			personalSystemAwards = DBManager.getInstance().getWorldDAO().getArenaAwardDAO().findOfCertainPlayer((int)player.getData().getUserid());
		//}
		
		totalSize = personalSystemAwards.size();
		
		if(totalSize % pageSize == 0){
			totalPages = totalSize/pageSize;
		}
		else{
			totalPages = (totalSize/pageSize) + 1;
		}
		
		int fromIndex = 0;
		int endIndex = 0;
		
		//如果允许翻上一页
		if(requestPage >= 1){
			fromIndex = (requestPage - 1) * pageSize;
			if((fromIndex + pageSize) > totalSize){
				endIndex = totalSize;
			}
			else{
				endIndex = fromIndex + pageSize;
			}
			currentPage = requestPage;
		}
		
		List<ArenaAward> needSystemAwardList = new ArrayList<ArenaAward>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			//截取所需要的战报消息信息
			needSystemAwardList = personalSystemAwards.subList(fromIndex, endIndex);
		}

		RespModuleSet rms=new RespModuleSet(ProcotolType.BATTLE_FIELD_RESP);
		SimplePage sp = new SimplePage();
		sp.setCurrentPage(currentPage);
		sp.setTotalPages(totalPages);
		rms.addModule(sp);
		for(ArenaAward aa : needSystemAwardList){
			//aa.setTitle("竞技场奖励");
			//aa.setTipsForAward("目前竞技场排名" + aa.getRankId() + "获得第" + aa.getRankId() + "名排名奖励");
			rms.addModule(aa);
		}
		AndroidMessageSender.sendMessage(rms,player);
		
	}
	
	/**
	 * 领取系统奖励
	 * @param id SystemAward id
	 * @return
	 */
	public List<SimpleAward> getAward(int id){
		List<SimpleAward> simpleAwardList = new ArrayList<SimpleAward>();
		for(ArenaAward aa : personalSystemAwards){
			if(id == aa.getId()){
				PlayerCharacter pc = World.getInstance().getPlayer(aa.getUserId());
				if(aa.getGold() != 0 || aa.getMedal() != 0){
					int gold = aa.getGold();
					int medal = aa.getMedal();
					if(pc!=null){
						pc.saveResources(GameConfig.GAME_MONEY, gold);
						pc.saveResources(GameConfig.AWARD, medal);
						SimpleAward simpleAward = new SimpleAward();
						simpleAward.setWhat(SimpleAward.GOLD);
						simpleAward.setValue(gold);
						simpleAwardList.add(simpleAward);
						
						SimpleAward simpleAward2 = new SimpleAward();
						simpleAward2.setWhat(SimpleAward.HONOR);
						simpleAward2.setValue(medal);
						simpleAwardList.add(simpleAward2);
						
						/*TipMessage tip = new TipMessage("获得金币"+gold+" 获得功勋"+medal,
									ProcotolType.ARENA_RESP, GameConst.GAME_RESP_SUCCESS,
									(byte) 0);
						GameUtils.sendTip(tip, pc.getUserInfo());*/
					}
				}else{//领取道具
					String areStr = aa.getGoodsOrEqu();
					//logger.info("领取奖励："+areStr);
					List<Cell> arenCell = PlayerStorageAgent.createFromStrings(areStr);
					StringBuffer sb = new StringBuffer();
					if(arenCell != null){
						//rms
						RespModuleSet rms = new RespModuleSet(ProcotolType.ARENA_RESP);// 模块消息
						//rms
						for(Cell cell : arenCell){
							//保存数据库
							pc.getPlayerStorageAgent().addPropsCell(cell.getItem().getId(), cell.getItemCount());
							sb.append("获得:"+cell.getItem().getName()+"数量："+cell.getItemCount());
							SimpleAward sa = new SimpleAward();
							sa.setWhat(cell.getItem().getId());
							sa.setValue(cell.getItemCount());
							simpleAwardList.add(sa);
							rms.addModule(cell);
						}
						AndroidMessageSender.sendMessage(rms, pc);
						/*TipMessage tip = new TipMessage(sb.toString(),
								ProcotolType.ARENA_RESP, GameConst.GAME_RESP_SUCCESS,
								(byte) 0);
						GameUtils.sendTip(tip, pc.getUserInfo());*/
					}
					logger.info("奖励界面："+sb.toString());
				}
				break;
			}
		}
		//删除此条个人系统奖励消息
		removePersonalSystemAward(id);	
		return simpleAwardList;
	
	}
	
	/**
	 * 更新内存中系统奖励数据
	 * @param simpleAward
	 * @return
	 */
	public ArenaAward addPersonalSystemAward(ArenaAward arenaAward){
		if(arenaAward == null){
			return null;
		}
		int id = DBManager.getInstance().getWorldDAO().getArenaAwardDAO().addArenaAward(arenaAward);
		arenaAward.setId(id);
		List<ArenaAward> tempList = new ArrayList<ArenaAward>();
		
		//确保新增加的战报消息在第一条显示
		tempList.add(arenaAward);
		for(ArenaAward a : personalSystemAwards){
			tempList.add(a);
		}
		personalSystemAwards = tempList;
		return arenaAward;
		
	}
	
	/**
	 * 删除玩家特定id的系统奖励信息
	 * @param id 系统奖励信息id SystemAward id
	 * @return
	 */
	public boolean removePersonalSystemAward(int id){
		DBManager.getInstance().getWorldDAO().getArenaAwardDAO().deleteArenaAward(id);
		for(int i=0;i<personalSystemAwards.size();i++){
			if(id == personalSystemAwards.get(i).getId()){
				personalSystemAwards.remove(i);
				break;
			}
		}
		return true;
		
	}
	
	/**
	 * 阶段战报
	 *//*
	public boolean stageFightEvent(){
		stageFightEvent = DBManager.getInstance().getWorldDAO().getFightEventDAO().getStageFightEvent();
		if(null == stageFightEvent){
			return false;
		}
		RespModuleSet rms=new RespModuleSet(ProcotolType.BATTLE_FIELD_RESP);
		rms.addModule(stageFightEvent);
		AndroidMessageSender.sendMessage(rms,player);
		return true;
	}*/
	
	/**
	 * 县长争夺战是否开启
	 * @return
	 */
	public static boolean isCountyWarGoing(){
		boolean going = false;
		String startTime = "";
		String endTime = "";
		for(Battlefield bf : battlefields){
			if(bf.getId() == 3){//如果是县长争夺战
				startTime = bf.getStartTime();
				endTime = bf.getEndTime();
				break;
			}
		}
		if("00:00:00".equals(startTime) && "00:00:00".equals(endTime)){//如果是全天开启战斗
			going = true;
		}
		else{
			Date nowDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String nowTime = sdf.format(nowDate);
			if(endTime.compareTo(startTime) > 0){//如果结束时间晚于开始时间，说明是当天
				if(nowTime.compareTo(startTime) >= 0 && nowTime.compareTo(endTime) < 0){
					going = true;
				}
			}
			else{//如果时间段跨天
				if((nowTime.compareTo(startTime) >= 0 && nowTime.compareTo("23:59:59") <= 0)
						||(nowTime.compareTo("00:00:00") >= 0 && nowTime.compareTo(endTime) < 0)){
					going = true;
				}
			}
		}
		return going;
		
	}
	
	
	public PlayerCharacter getPlayer() {
		return player;
	}

	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}
	
	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	
	public List<FightEvent> getFightEventList() {
		return fightEventList;
	}


	public void setFightEventList(List<FightEvent> fightEventList) {
		this.fightEventList = fightEventList;
	}


	/**
	 * 玩家退出后保存
	 */
	public void save(){
		long time=TimeUtils.nowLong();
		Object[] array=fightEventList.toArray();
		int num=0;
		for(int i=0;i<array.length;i++){
			FightEvent fe=(FightEvent)array[i];
			if(fe.isNew){
				MongoServer.getInstance().getBgServer().save("fightEvent", fe);
				num++;
			}
		}
//		logger.info("保存战报耗时="+(TimeUtils.nowLong()-time)+" 保存条数="+num);
	}

	//按照时间排序
	class MyComparator implements Comparator<FightEvent> {

		@Override
		public int compare(FightEvent o1, FightEvent o2) {
			return (int)(o2.getDate().getTime() - o1.getDate().getTime());
		}

	}
	
}
