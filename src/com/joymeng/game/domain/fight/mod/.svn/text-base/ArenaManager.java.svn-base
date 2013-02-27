package com.joymeng.game.domain.fight.mod;

import hirondelle.date4j.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.award.ArenaAward;
import com.joymeng.game.domain.fight.result.ArenaReward;
import com.joymeng.game.domain.rank.RankArena;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.time.SysTime;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.World;

/**
 * 竞技场管理器
 * 
 * @author admin
 * @date 2012-6-18 TODO
 */
public class ArenaManager {
	static Logger logger = LoggerFactory.getLogger(ArenaManager.class);
	
	private boolean isOpen;
	// 上一次领取的时间
//	private long time;
	// 奖励
	private int award;
	// 战报
	// 公告---1第一名变化 2连胜10+ 3排名上升300
//	List<Arena> list = new ArrayList<Arena>();
	private HashMap<Short, Arena> arenaMap = new HashMap<Short, Arena>();
	public static final int MAXNUM = 2000;// 最大排名
	public static final int OPEN_LEVEL = 10;// 开启级别
	public static final int CD = 10 * 60 * 1000;// cd毫秒
	private static ArenaManager instance;
	private int totalPages;
	private int currentPage;

	public static ArenaManager getInstance() {
		if (instance == null) {
			instance = new ArenaManager();
		}
		return instance;
	}

	public Arena getArena(short id) {
//		for(Arena arena:list){
//			System.out.println("id="+arena.getId());
//		}
		//list.get(index)
		return arenaMap.get(id);
	}
	
	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * 检测竞技场中是否已经有该玩家
	 * @param uid
	 * @return
	 */
	public boolean clear(){
		Iterator<Short> it=arenaMap.keySet().iterator();
		int num=0;
		//清除玩家的arenaId，清除玩家将领id，重置竞技场数据
		//

		while(it.hasNext()){
			Arena arena=arenaMap.get(it.next());
			arena.reset();
			ArenaManager.getInstance().save(arena);
		}
		return true;
	}
	/**
	 * 检测竞技场中是否已经有该玩家
	 * @param uid
	 * @return
	 */
	public String check(){
		logger.info("check arena!!!");
		Iterator<Short> it=arenaMap.keySet().iterator();
		List<Arena> list=new ArrayList<Arena>();
		while(it.hasNext()){
			Arena arena=arenaMap.get(it.next());
			int uid=arena.getUserId();
			if(arena.getUserId()==0){
				continue;
			}
			PlayerCharacter pc=World.getInstance().getPlayer(uid);
			if(pc.getData().getArenaId()!=arena.getId()){
				list.add(arena);
//				logger.info("arena id="+arena.getId());
			}
		}
		StringBuilder sb=new StringBuilder();
		for(Arena a:list){
			logger.error("arena check ,error id="+a.getId());
			sb.append("\n");
			sb.append(a.getId());
		}
		return sb.toString();
	}
	public void saveAll(){
		Iterator it=arenaMap.keySet().iterator();
		while(it.hasNext()){
			Arena arena=arenaMap.get(it.next());
//			int uid=arena.getUserId();
			if(arena.getUserId()==0){
				continue;
			}
			this.save(arena);
		}
	}
	/**
	 * 初始化竞技场
	 */
	public void init() {
		load();
//		if (arenaMap == null || arenaMap.size() == 0) {
//			for (short i = 0; i < MAXNUM; i++) {
//				Arena arena = Arena.create((short)(i+1));
//				DBManager.getInstance().getWorldDAO().getArenaDAO().add(arena);
//			}
//			load();//重新load一次
//		}
	}

	public void load() {
		List<Arena> list = DBManager.getInstance().getWorldDAO().getArenaDAO().getAll();
		for(Arena arena:list){
//			System.out.println("id="+arena.getId());
			arenaMap.put(arena.getId(), arena);
		}
	}

	public void save(Arena arena) {
		DBManager.getInstance().getWorldDAO().getArenaDAO().saveArena(arena);
	}

	/**
	 * @return
	 */
	public int hasNum() {
		int i = 0;
		Iterator it=arenaMap.keySet().iterator();
		while(it.hasNext()){
			Arena arena=arenaMap.get(it.next());
			if(arena.getUserId()!=0){
				i++;
			}
		}

		return i;
	}

	/**
	 * 从指定的范围内（start，end）查找指定数量的空白位置
	 * 
	 * @param start
	 * @param end
	 * @param num
	 * @return
	 */
	public int[] findBlank(short start, short end, int num, byte type) {
		int data[] = new int[num];
		int j = 0;
		if (type == 0) {// 正序
			for (short i = start; i < end; i++) {
				Arena a = arenaMap.get(i);
				if (a.getUserId() == 0) {
					data[j] = a.getId();
					j++;
					if (j >= num) {
						return data;
					}
				}
			}
		} else {// 倒序
			for (short i = end; i >= start; i--) {
				Arena a = arenaMap.get(i);
				if (a.getUserId() == 0) {
					data[j] = a.getId();
					j++;
					if (j >= num) {
						return data;
					}
				}
			}
		}

		return data;
	}

	/**
	 * 查找自己可以看到的玩家id，
	 * 
	 * @param rankId
	 *            自己当前的排名 =0 没有排名
	 */
	public List<Arena> search(int rankId) {
		// 1、第一次进入 当前名次显示为无或0
		int data[] = new int[5];
		if (rankId == 0) {// 从来没有进入过
			int num = hasNum();
			if (num == MAXNUM) {
				// 2000个擂台已满 挑战最后5个擂台
				data = new int[] { MAXNUM - 1, MAXNUM - 2, MAXNUM - 3,
						MAXNUM - 4, MAXNUM };
			} else {
				// 2000个擂台未满 ,最后一个有擂台的玩家不是第2000名，假设是第X名,显示第X+1、X、X-1、X-2、X-3名擂台
				if (num < 5) {
					data = new int[] { 1, 2, 3, 4, 5 };
				} else {
					int j=0;
					for (short i = MAXNUM; i >=0 ; i--) {
						Arena a = arenaMap.get(i);
						if (a.getUserId() != 0) {
							j=i;
							break;
						}
					}
					data = new int[] { j+1, j, j-1, j-2, j-3 };
				}
			}
		} else {
			// 2、获得名次后
			if (rankId <= 5) {
				data = new int[] { 1, 2, 3, 4, 5 };
			} else if (rankId <= 199) {
				data = new int[] { rankId, rankId - 1, rankId - 2, rankId - 3,
						rankId - 4 };
			} else if (rankId <= 499) {
				data = new int[] { rankId,
						MathUtils.randomNoInclude(rankId - 15,rankId,rankId),
						MathUtils.randomNoInclude(rankId - 30,rankId-16,rankId),
						MathUtils.randomNoInclude(rankId - 45,rankId-31,rankId),
						MathUtils.randomNoInclude( rankId - 60,rankId-46,rankId) };
			} else if (rankId <= 999) {
				data = new int[] { rankId,
						MathUtils.randomNoInclude(rankId - 30,rankId,rankId ),
						MathUtils.randomNoInclude(rankId - 60,rankId-31,rankId ),
						MathUtils.randomNoInclude(rankId - 90,rankId-61,rankId),
						MathUtils.randomNoInclude( rankId - 120,rankId-91,rankId) };
			} else if (rankId <= 2000) {
				data = new int[] { rankId,
						MathUtils.randomNoInclude(rankId - 50,rankId,rankId),
						MathUtils.randomNoInclude(rankId - 100,rankId-51 ,rankId),
						MathUtils.randomNoInclude(rankId - 150,rankId-101,rankId),
						MathUtils.randomNoInclude( rankId - 200,rankId-151,rankId) };
			}
		}
        List<Arena> _list = new ArrayList<Arena>();
		for (int i = 0; i < data.length; i++) {
			// 根据自己的排名来寻找相应的位置
			Arena arena=this.getArena((short)data[i]);
//			logger.info("刷新擂台 arena search=="+data[i]);
			_list.add(arena);
		}
		return _list;
	}
	
	/**
	 * 发送奖励信息
	 */
	public void sendRewardMessage(){
		logger.info("发送竞技场奖励");
//		DateTime now = TimeUtils.now();//获得当前时间
		//发送奖励的时间
		DateTime date=TimeUtils.getTime(GameConfig.arenaUpdate);
//		if(now.lt(date)){
//			logger.info("不发送奖励，当前时间="+now.format(TimeUtils.FORMAT)+" 发送奖励的时间="+date.format(TimeUtils.FORMAT));
//			return;
//		}
		DateTime nextDate=date.plusDays(2);
		GameConfig.arenaUpdate=TimeUtils.getTimes(nextDate.format(TimeUtils.FORMAT));
		logger.info("下一次奖励发送时间="+nextDate.format(TimeUtils.FORMAT));
		// 保存时间为2天后
		GameConfig.getPm().set("arenaUpdate",date.toString());
		GameConfig.getPm().save(GameConfig.FILENAME);
		World gameWorld = World.getInstance();
		Iterator<Short> it=arenaMap.keySet().iterator();
		while(it.hasNext()){
			Arena a=arenaMap.get(it.next());
			// 向每一个玩家发送奖励
			if(a.getUserId()==0){
				continue;
			}
			int rankId = a.getId();
			ArenaReward ar = GameDataManager.arenaRewardManager
					.getReward(rankId);
			int money = ar.getGold();
			int award = ar.getMedal();
			int pid = a.getUserId();
			PlayerCharacter pc = gameWorld.getPlayer(pid);
			if(pc==null){
				continue;
			}
			
			//保存玩家竞技场奖励到数据库
			String title = I18nGreeting.getInstance().getMessage("arena.title", null);
			String messgae = I18nGreeting.getInstance().getMessage("arena.message", new Object[]{rankId});//"目前竞技场排名" + rankId + "获得第" + rankId + "名排名奖励";
//			if(I18nGreeting.getInstance().LANLANGUAGE_TIPS == 1){
//				title = "Arena Rewards";
//				messgae = "The current Arena Ranking" + rankId+" you got" +rankId+ " rewards.";
//			}
			ArenaAward arenaAward = new ArenaAward();
			arenaAward.setUserId(pid);
			arenaAward.setGold(money);
			arenaAward.setMedal(award);
			arenaAward.setRankId(rankId);
			arenaAward.setTitle(title);
			arenaAward.setTipsForAward(messgae);
			
			//为了保持数据库中竞技场奖励不超过3条
			DBManager.getInstance().getWorldDAO().getArenaAwardDAO().delPlayerArenaAwardRemainTwo(a.getUserId());
			
			logger.info("竞技场奖励保存,"+TimeUtils.now());
			
			//发系统消息给获得奖励的玩家
			GameUtils.sendEventOpenFlag((byte)3, pc.getUserInfo());
			
			if (pc.getUserInfo() != null) {
				pc.getFightEventManager().addPersonalSystemAward(arenaAward);
				// 发送
				logger.info("当前排名="+rankId);
				RespModuleSet rms = new RespModuleSet(ProcotolType.ARENA_RESP);// 模块消息
				rms.addModule(pc.getData());
				List<Arena> list=GameDataManager.arenaManager.search(rankId);
				for(Arena arena:list){
//					System.out.println("id="+arena.getId());
					rms.addModule(arena);
				}
				rms.addModule(new SysTime());
				AndroidMessageSender.sendMessage(rms, pc);

			}
			else{
				DBManager.getInstance().getWorldDAO().getArenaAwardDAO().addArenaAward(arenaAward);
			}
		}
	}
//
//	/**
//	 * 发送奖励
//	 */
//	public void sendReward() {
//		logger.info("发送竞技场奖励");
//		DateTime now = TimeUtils.now();//获得当前时间
//		DateTime date=TimeUtils.getTime(GameConfig.arenaUpdate);
////		logger.info("当前时间="+now.toString()+" 发送奖励时间="+date.toString());
////		if(TimeUtils.nowLong()<GameConfig.arenaUpdate){
////			logger.info("时间未到，不发送奖励");
////			return;
////		}
////		long time=GameConfig.arenaUpdate+3600*24*3*1000;
//		long time=TimeUtils.nowLong()+300*1000;
//		date=TimeUtils.getTime(time);
//		GameConfig.arenaUpdate=time;
//		logger.info("下一次奖励发送时间="+date.format(TimeUtils.FORMAT1));
//		// 保存时间为三天后
//		GameConfig.getPm().set("arenaUpdate",date.toString());
//		GameConfig.getPm().save(GameConfig.FILENAME);
//		World gameWorld = World.getInstance();
//		Iterator<Short> it=arenaMap.keySet().iterator();
//		while(it.hasNext()){
//			Arena a=arenaMap.get(it.next());
//			// 向每一个玩家发送奖励
//			if(a.getUserId()==0){
//				continue;
//			}
//			int rankId = a.getId();
//			ArenaReward ar = GameDataManager.arenaRewardManager
//					.getReward(rankId);
//			int money = ar.getGold();
//			int award = ar.getMedal();
//			int pid = a.getUserId();
//			PlayerCharacter pc = gameWorld.getPlayer(pid);
//			if(pc==null){
//				continue;
//			}
//			pc.saveResources(GameConfig.GAME_MONEY, money);
//			pc.saveResources(GameConfig.AWARD, award);
//			if (pc.getUserInfo() != null) {
//				// 发送
//				logger.info("当前排名="+rankId);
//				RespModuleSet rms = new RespModuleSet(ProcotolType.ARENA_RESP);// 模块消息
//				rms.addModule(pc.getData());
//				List<Arena> list=GameDataManager.arenaManager.search(rankId);
//				for(Arena arena:list){
////					System.out.println("id="+arena.getId());
//					rms.addModule(arena);
//				}
//				rms.addModule(new SysTime());
//				AndroidMessageSender.sendMessage(rms, pc);
//				TipMessage tip = new TipMessage("发送竞技场奖励，当前排名" + rankId+" 获得金币"+money+" 获得功勋"+award,
//						ProcotolType.ARENA_RESP, GameConst.GAME_RESP_SUCCESS,
//						(byte) 0);
//				GameUtils.sendTip(tip, pc.getUserInfo(),GameUtils.FLUTTER);
////				ChatResp resp = new ChatResp();
////				Notice notice = new Notice(ChatChannel.CHANNEL_ARENA, "发送竞技场奖励，当前排名" + rankId+" 获得金币"+money+" 获得功勋"+award);
////				notice.setName("系统");
////				notice.setPlayerId(-1);
////				resp.setUserInfo(pc.getUserInfo());
////				resp.setNotice(notice);
////				JoyServiceApp.getInstance().sendMessage(resp);
////				**日**时，***挑战你，你战败了，排名降至***		
////				**日**时，***挑战你，你获胜了，排名不变		
////				**日**时，你挑战***，你战败了，排名不变		
////				**日**时，你挑战***，你获胜了，排名升至***		
//
//			}
//		}
//	}
	/**
	 * 检测数据异常
	 */
	public void SystemCheck(){
		for(Arena a:arenaMap.values()){
			String soldier=a.getSoldier();
			if(soldier.equals("")){
				continue;
			}
			String[] str=soldier.split(":");
			String s=str[1];
			int soldierNum=Integer.parseInt(s.split(",")[0]);
			if(soldierNum==0){
				logger.info("ERROR! arena id="+a.getId()+"soldier num=="+soldierNum);
			}
		}
	}
	
	/**
	 * 获得竞技场排名
	 * @param requestPage
	 * @param pageSize
	 */
	public void getArenaRank(int requestPage,int pageSize,PlayerCharacter player){
		List<RankArena> rankArenaList = new ArrayList<RankArena>();
		for(int i=1;i<=10;i++){
			Arena arena = arenaMap.get((short)i);
			
			RankArena ra = new RankArena();
			ra.setRank(arena.getId());
			String name = arena.getUserName();
			if(null == name ||"".equals(name)){
				name ="-";
				ra.setBlank((byte)0);
			}
			else{
				ra.setBlank((byte)1);
			}
			ra.setName(name);
			ra.setUserId(arena.getUserId());
			
			ArenaReward ar = GameDataManager.arenaRewardManager
					.getReward(i);
			ra.setGoldNum(ar.getGold());
			ra.setMedalNum(ar.getMedal());
			
			rankArenaList.add(ra);
		}	
		
		int totalSize = rankArenaList.size();

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
		
		//请求玩家当前所在页
		/*else if(requestPage == -1){
			getSelfLadderMaxRank(pageSize,player);
			return;
		}*/
		
		else{
			
			fromIndex = (requestPage - 1) * pageSize;
			if((fromIndex + pageSize) > totalSize){
				endIndex = totalSize;
			}
			else{
				endIndex = fromIndex + pageSize;
			}
			currentPage = requestPage;
		}
		
		List<RankArena> needRankArenaList = new ArrayList<RankArena>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			//截取所需要的排行榜信息
			needRankArenaList = rankArenaList.subList(fromIndex, endIndex);
		}

		RespModuleSet rms=new RespModuleSet(ProcotolType.RANK_RESP);
		for(RankArena rankArena : needRankArenaList){
			rms.addModule(rankArena);
		}
		AndroidMessageSender.sendMessage(rms,player);
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
	
	
	
	
}
