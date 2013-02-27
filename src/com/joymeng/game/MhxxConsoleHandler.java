package com.joymeng.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.test.jetty.JettyFactory;

import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.ServerInfo;
import com.joymeng.core.utils.StringUtil;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.MongoUtil;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.nation.war.WarManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.role.UsernameManager;
import com.joymeng.game.domain.shop.GoodsManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.GameListRequest;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.console.ConsoleService;
import com.joymeng.web.entity.CardOperationInfo;
import com.joymeng.web.entity.ExtremeBox;
import com.joymeng.web.entity.OnlineTime;

public class MhxxConsoleHandler implements ConsoleService {
	private static MhxxConsoleHandler instance = new MhxxConsoleHandler();
	private static final Logger logger = LoggerFactory
			.getLogger(MhxxConsoleHandler.class);

	@Override
	public String handle(String msg) {
		try {
			StringBuffer buf = new StringBuffer(msg.length());
			// StringBuffer returnBuffer=new StringBuffer();
			char[] chars = msg.toCharArray();
			int arrLen = chars.length;
			char c;
			for (int i = 0; i < arrLen; i++) {
				c = chars[i];
				if (c == '') // 去除光标的按键
				{
					i++;
					i++;
					continue;
				}
				if (c != 8) {
					buf.append(c);
				} else // Backspace键，则删除前一个字符
				{
					int idx = buf.length() - 1;
					if (idx >= 0) {
						buf.deleteCharAt(idx);
					}
				}
			}
			msg = buf.toString();

			switch (msg) {
			case "exit":
				break;
			case "test":
				//处理服务器间通讯
				try{
					GameListRequest req = new GameListRequest();
					//设置具体要通讯的服务器的instanceId,发送给登录服务器
					req.setDestInstanceID(0xFFFE);
					JoyServiceApp.getInstance().sendMessage(req);
				}catch(Exception ex){
					logger.info("error ,str="+ex.toString());
				}
				return "test";
			case "help":
				return "help";
			case "info":// 输出服务器信息
				List<String> list = new ArrayList<String>();
				String serverinfo = new ServerInfo().toString();
				String worldinfo = World.getInstance().info();
				String[] splits = serverinfo.split("\n");
				for (String str : splits) {
					list.add(str + "\n");
				}
				splits = worldinfo.split("\n");
				for (String str : splits) {
					list.add(str + "\n");
				}
				logger.info("info=" + list.toString());
				return list.toString();
			case "gameinit":// 重新载入数据
				World.getInstance().init();
				break;
			case "clear arena":
				//遍历竞技场 
				ArenaManager.getInstance().clear();
				return "success";
			case "check arena":
				//遍历竞技场 
				return "success:"+ArenaManager.getInstance().check();
			case "all player":
				return "mapsize="+World.getInstance().getAllPlayerNum()+"" +World.getInstance().getAllPlayerNum2();
			case "online":
				return "onlinenum="+World.getInstance().getOnlinePlayerNum();
			case "city_start":
				WarManager.getInstance().FIGHT_START = TimeUtils
						.getWarTime(" 8:00:00");
				WarManager.getInstance().FIGHT_END = TimeUtils
						.getWarTime(" 21:00:00");
				WarManager.getInstance().FIGHT_TYPE = 0;
				WarManager.getInstance().FIGHT_NUM = 4;
				WarManager.getInstance().CAMP_NUM = 8;// 军营数据
				WarManager.getInstance().IS_REFRESH = true;
				System.out.println("city start ok !"
						+ TimeUtils.chDate(TimeUtils.nowLong()));
				WarManager.getInstance().startWar(true,null);
				return "city start end ok !"
						+ TimeUtils.chDate(TimeUtils.nowLong());
			case "city_stop":
				WarManager.getInstance().calcResults();
				WarManager.getInstance().stopWar(true);
				return "city  stop !"
						+ TimeUtils.chDate(TimeUtils.nowLong());
			case "state_start":
				WarManager.getInstance().FIGHT_START = TimeUtils
						.getWarTime(" 6:11:00");
				WarManager.getInstance().FIGHT_END = TimeUtils
						.getWarTime(" 20:00:00");
				WarManager.getInstance().FIGHT_TYPE = 1;
				WarManager.getInstance().FIGHT_NUM = 4;
				WarManager.getInstance().CAMP_NUM = 4;// 军营数据
				WarManager.getInstance().IS_REFRESH = false;
				System.out.println("state start ok !"
						+ TimeUtils.chDate(TimeUtils.nowLong()));
				WarManager.getInstance().startWar(true,null);
				return "state start end ok !"
						+ TimeUtils.chDate(TimeUtils.nowLong());
			case "state_stop":
				WarManager.getInstance().calcResults();
				WarManager.getInstance().stopWar(true);
				return "state  stop !"
						+ TimeUtils.chDate(TimeUtils.nowLong());
			case "quest":
//				PlayerCharacter pc=World.getInstance().getRole(1418);
//				pc.getPlayerQuestAgent().addDailyQuestOnLevelup();
				break;
			case "country_start":
				WarManager.getInstance().FIGHT_START = TimeUtils
						.getWarTime(" 6:11:00");
				WarManager.getInstance().FIGHT_END = TimeUtils
						.getWarTime(" 20:00:00");
				WarManager.getInstance().FIGHT_TYPE = 2;
				WarManager.getInstance().FIGHT_NUM = 1;
				WarManager.getInstance().CAMP_NUM = 4;// 军营数据
				WarManager.getInstance().IS_REFRESH = false;
				System.out.println("state start ok !"
						+ TimeUtils.chDate(TimeUtils.nowLong()));
				WarManager.getInstance().startWar(true,null);
				return "country start end ok !"
						+ TimeUtils.chDate(TimeUtils.nowLong());
			case "country_stop":
				WarManager.getInstance().calcResults();
				WarManager.getInstance().stopWar(true);
				return "country stop!"
						+ TimeUtils.chDate(TimeUtils.nowLong());
			case "arena clear"://清除竞技场数据
				ArenaManager.getInstance().clear();
				return "success";
			case "city_quit":
				HashMap<Integer, Nation> allCity = NationManager.getInstance().allCityMap;
				for(Nation n:allCity.values()){
					n.retreatNation(0, true);
				}
				return "city_quit"
				+ TimeUtils.chDate(TimeUtils.nowLong());
			case "state_quit":
				HashMap<Integer, Nation> allstate = NationManager.getInstance().stateMap;
				for(Nation n:allstate.values()){
					n.retreatNation(0, true);
				}
				return "state_quit"
				+ TimeUtils.chDate(TimeUtils.nowLong());
			case "county_quit":
				HashMap<Integer, Nation> allcounty = NationManager.getInstance().countryMap;
				for(Nation n:allcounty.values()){
					n.retreatNation(0, true);
				}
				return "county_quit"
				+ TimeUtils.chDate(TimeUtils.nowLong());
			case "init_username"://初始化可供玩家选择的用户名

				UsernameManager.getInstance().consleInitUsername();//插入数据库

//				UsernameManager.getInstance().insertUsernameStatusToDB();//插入数据库
//				UsernameManager.getInstance().loadAvaliableUsernameStatus();//加载可用玩家姓名

				return "success";
			case "clear_reload_player_cache"://清除mongoDB中的playerCache,然后重新加载
				
				//先清空MongoDB中PlayerCache数据
				MongoServer.getInstance().getLogServer().getPlayerCacheDAO().dropCollection();
				
				//重新加载PlayerCache数据
				int PAGE_SIZE = 100;//一次查询100条
				int totalSize = DBManager.getInstance().getWorldDAO().countRoles();//总记录条数
				int totalPages;//总页数
				if(totalSize % PAGE_SIZE == 0){
					totalPages = totalSize / PAGE_SIZE;
				}
				else{
					totalPages = (totalSize / PAGE_SIZE) + 1;
				}
				
				long startTime = TimeUtils.nowLong();
				//分页查询出所有玩家数据,并将玩家数据缓存到MongoDB
				for(int i=0;i<totalPages;i++){
					List<RoleData> roleDataList = DBManager.getInstance().getWorldDAO().queryRoleByPage(i * PAGE_SIZE,i * PAGE_SIZE + PAGE_SIZE);
					for(RoleData rd : roleDataList){
						PlayerCharacter playerCharacter = World.getInstance().getPlayer((int)rd.getUserid());
						GameUtils.putToCache(playerCharacter);
					}
				}
				long endTime = TimeUtils.nowLong();
				logger.info("重新初始化玩家缓存数据耗时：" + (endTime - startTime));
				return "success";
			case "memory":
				Runtime rt = Runtime.getRuntime();
				return "total memory size=" + rt.totalMemory() + "|free memory size ="+ rt.freeMemory()+" 堆内存"+rt.maxMemory();
			case "reload":
				if(JettyServerApp.server != null){
					JettyFactory.reloadContext(JettyServerApp.server);
					return "jetty reload ok!";
				}
				return "jetty reload fail!";
			case "jetty_start":
				JettyServerApp.close();
				JettyServerApp.start();
				return "jetty start ok!";
			case "jetty_stop":
				JettyServerApp.close();
				//JettyServerApp.start();
				return "jetty stop ok!";
			case "mili_load":
				NationManager.getInstance().satateMilitary();
				return "mili_load";
			case "achiev_load":
				NationManager.getInstance().setAchiev();
				return "achiev_load";
			case "heart":
				// 取得指定日期内的在线时间数据
//				String time=TimeUtils.now().format(TimeUtils.FORMAT);
//				List<OnlineTime> timelist = MongoServer.getInstance().getLogChche()
//						.getOnlineTime(time + " 0:0:0",time + " 24:0:0");
				return "success";
			}
			//1秒后关机
			//shutdown 1   
			if(msg.startsWith("shutdown")){
				String cmd[] = msg.split(" ");
				long time =Long.parseLong(cmd[1]);
				System.out.println("服务器将在"+time+" 秒后关闭");
				 Timer timer = new Timer();
			        timer.schedule(new TimerTask() {
			            public void run() {
			            	//System.out.println("-------设定要指定任务--------");
			            	World.getInstance().stop();
			            }
			        }, time*1000);// 设定指定的时间time,此处为2000毫秒
				//10秒后关闭服务器
				long now=TimeUtils.nowLong();
				long end=now+time*1000;
				while(TimeUtils.nowLong()<end){
//					System.out.println("send shutdown ");
					long left=(end-TimeUtils.nowLong())/1000;//剩余时间
					//每2分钟通知一次
					if(left%10==0){
						System.out.println("send shutdown left="+left);
						GameUtils.sendWolrdMessage(new TipMessage("服务器"+left+"秒后关闭",
								ProcotolType.SYSTEM_RESP,
								GameConst.GAME_RESP_SUCCESS), GameUtils.FLUTTER);
						Thread.sleep(1*1000);
					}
			
				}
		
				return "关闭成功";
			}
			if(msg.startsWith("deluser_")){
				String cmd[] = msg.split("_");
				if(StringUtils.isInteger(cmd[1])){
					int userid = Integer.parseInt(cmd[1]);
					DBManager.getInstance().getWorldDAO().removeRole(userid);
				}
			}
			if (msg.startsWith("show")) {// 输出继承 clientModuleBase的对象属性
				String cmd[] = msg.split(" ");
				switch (cmd[1]) {
				case "player":// show player 1001
					int uid = Integer.parseInt(cmd[2]);
					PlayerCharacter player = World.getInstance().getOnlineRole(
							uid);
					if (player != null) {
						logger.info("roledata=" + player.getData().print());
						return player.getData().print();
					}
					break;
					
					//查询大转盘操作记录信息
				case "extreme_box_info":// eg： show extreme_box_info 2012-12-18 2012-12-19
//					String start = cmd[2];
//					String end = cmd[3];
//					ExtremeBox extremeBox = MongoServer.getInstance().getExtremeBox(start, end);
//					List<Integer> awardIdList = extremeBox.getAwardIDList();
//					StringBuffer s = new StringBuffer();
//					s.append("award list:");
//					for(int i : awardIdList){
//						s.append(i + ",");
//					}
//					return "rool player num:" + extremeBox.getRollNum() 
//							+ ",roll times:" + extremeBox.getRollTimes()
//							+ ",refresh player num:" + extremeBox.getRefreshNum()
//							+ ",refresh times:" + extremeBox.getRefreshTimes()
//							+ ",total score:" + extremeBox.getTotalScore() + s.toString();
					
					//查询翻牌操作相关记录
				case "card_operation_info"://eg : show card_operation_info 2012-12-18 2012-12-19
//					String startDate = cmd[2];
//					String endDate = cmd[3];
//					CardOperationInfo cardInfo = MongoServer.getInstance().getCardOperationInfo(startDate, endDate);
//					List <Integer> idList = cardInfo.getAwardIdList();
//					List<Integer> numList = cardInfo.getAwardNumList();
//					StringBuffer str = new StringBuffer();
//					str.append("award list:");
//					for(int i = 0;i < idList.size();i++){
//						str.append("[award id:" + idList.get(i) + ",award num:" + numList.get(i) + "]" );
//					}
//					return "flip player num:" + cardInfo.getFlipPlayerNum()
//							+ ",flip times:" + cardInfo.getFlipTimes()
//							+ ",rotate player num:" + cardInfo.getRotatePlayerNum()
//							+ ",rotate times:" + cardInfo.getRotateTimes()
//							+ "total opened card num:" + cardInfo.getTotalFlipNums() + str.toString();
					return "";
				// 查询玩家英雄相关
				case "playerHero":// show playerHero 715(player id)
									// 111(playerHero id) 如果是显示当前玩家所有英雄 则为all
					// eg show playerHero 715 1234 or show playerHero 715 allsef
					uid = Integer.parseInt(cmd[2]);
					player = World.getInstance().getOnlineRole(uid);
					if (player != null) {
						StringBuffer sb = new StringBuffer();
						if ("all".equals(cmd[3])) {// 显示当前玩家所有英雄
							PlayerHero[] playerHeroArray = player
									.getPlayerHeroManager().getHeroArray();
							for (PlayerHero ph : playerHeroArray) {
								sb.append(ph.print() + "\n");
							}
							return sb.toString();
						} else {// 显示当前玩家特定英雄
							return player.getPlayerHeroManager()
									.getHero(Integer.parseInt(cmd[3])).print();
						}
					}

					// 查询玩家资源相关
				case "playerBuilding":// show playerBuilding 715(player id)
										// 1234(playerBuilding id) 如果是显示当前玩家所有资源
										// 则为all
					// eg show playerBuilding 715 1234 or show playerBuilding
					// 715 all
					uid = Integer.parseInt(cmd[2]);
					player = World.getInstance().getOnlineRole(uid);
					if (player != null) {
						StringBuffer sb = new StringBuffer();
						if ("all".equals(cmd[3])) {// 显示当前玩家所有资源
							List<PlayerBuilding> playerBuildingList = player
									.getPlayerBuilgingManager()
									.getAllResource();
							for (PlayerBuilding pb : playerBuildingList) {
								sb.append(pb.print() + "\n");
							}
							return sb.toString();
						} else {// 显示当前玩家特定资源
							return player.getPlayerBuilgingManager()
									.getPlayerBuild(Integer.parseInt(cmd[3]))
									.print();
						}
					}

					// 查询战报相关
				case "playerFightEvent":// show playerFightEvent 715(player id)
										// 1234(plyerHero id) 如果是显示当前玩家所有战报
										// 则为all
					// eg show playerBuilding 715 1234 or show playerBuilding
					// 715 all
//					uid = Integer.parseInt(cmd[2]);
//					player = World.getInstance().getOnlineRole(uid);
//					if (player != null) {
//						StringBuffer sb = new StringBuffer();
//						List<FightEvent> feList = player.getFightEventManager().fightEventList;
//						if ("all".equals(cmd[3])) {// 显示当前玩家所有战报消息
//							for (FightEvent fe : feList) {
//								sb.append(fe.print() + "\n");
//							}
//							return sb.toString();
//						} else {// 显示当前玩家某一个将领的相关战报消息
//							for (FightEvent fe : feList) {
//								if (fe.getHeroId() == Integer.parseInt(cmd[3])) {
//									sb.append(fe.print() + "\n");
//								}
//							}
//							return sb.toString();
//						}
//
//					}

					// 查询某玩家某道具数量
				case "playerPropsNum":// show playerPropsNum 715(playerId)
										// 555(props id)
					uid = Integer.parseInt(cmd[2]);
					player = World.getInstance().getOnlineRole(uid);
					int num = 0;
					if (player != null) {
						num = player.getPlayerStorageAgent().countCertainProps(
								Integer.parseInt(cmd[3]));
					}
					return String.valueOf(num);

				default:
					logger.info("error cmd,cmd=" + msg);
					return "error cmd,cmd=" + msg;
				}
			}
			if (msg.startsWith("goods")) {//统计道具商店
				String cmd[] = msg.split(" ");
				switch (cmd[1]) {
				case "uid":// show player 1001
//					MongoServer.getInstance().getGoodsCache().getBuyRecordByUid(Integer.parseInt(cmd[2]));
					break;
				case "gid":
//					MongoServer.getInstance().getGoodsCache().getBuyRecordByGid(Integer.parseInt(cmd[2]));
					break;
				case "top":
					String time=TimeUtils.now().format(TimeUtils.FORMAT);
					GoodsManager.getInstance().mathGoodProps(time,time);
					break;
				}
				return "success";
			}
			
			//禁止玩家发言，登录
			//示例：disallow speak 1234                  说明：1234为玩家userid
			//    disallow login 1234
			if(msg.startsWith("disallow")){
				String cmd[] = msg.split(" ");
				int uid = Integer.parseInt(cmd[2]);
				PlayerCharacter playerCharacter = World.getInstance().getPlayer(uid);
				switch (cmd[1]) {
				case "speak":
					playerCharacter.getData().setCanSpeak((byte)1);//禁止玩家言发
					break;
				case "login":
					playerCharacter.getData().setCanLogin((byte)1);//禁止玩家登录
					World.getInstance().kickRole(playerCharacter);//踢出玩家
					break;
				}
				return "success";
			}
			
			//解禁玩家发言，登录
			//示例：disallow speak 1234                  说明：1234为玩家userid
			//    disallow login 1234
			if(msg.startsWith("allow")){
				String cmd[] = msg.split(" ");
				int uid = Integer.parseInt(cmd[2]);
				PlayerCharacter playerCharacter = World.getInstance().getPlayer(uid);
				switch (cmd[1]) {
				case "speak":
					playerCharacter.getData().setCanSpeak((byte)0);//解禁玩家发言
					break;
				case "login":
					playerCharacter.getData().setCanLogin((byte)0);//解禁玩家登录
					break;
				}
				return "success";
			}
			
			
			
			if (msg.startsWith("mongo")) {// 输出日志
//				MongoTest test = MongoTest.getInstance();
				String cmd[] = msg.split(" ");
				switch (cmd[1]) {
				case "test":
//					return MongoServer.getInstance().getLogChche().getLogByTime("2013-01-21","2013-01-26");
				case "job":
					MongoUtil.doJob("2013-01-14","2013-02-18");
					break;
				case "a1":
					//查找某个竞技场下的所有战报
//					MongoServer.getInstance().getLogChche().getArenaLogByUid("2013-01-18"+" 0:0:0","2013-02-01"+" 24:0:0",Integer.parseInt(cmd[2]));
					return "success";
				case "a2":
					//查找某个竞技场下的所有战报
//					MongoServer.getInstance().getLogChche().getArenaLogByAid("2013-01-18"+" 0:0:0","2013-02-01"+" 24:0:0",Integer.parseInt(cmd[2]));
					return "success";

//					// return test.get_log().getLogByTime("2012-9-3 1:1:1",
//					// "2012-9-5 1:1:1");
//					return test.get_log().getLogByTime(cmd[2] + " " + cmd[3],
//							cmd[4] + " " + cmd[5]);
				}
//				case "all":
//					test.get_log().getLogs(cmd[2], Integer.parseInt(cmd[3]),
//							cmd[4] + " " + cmd[5], cmd[6] + " " + cmd[7]);
//					break;
//				case "key":
//					if (cmd.length == 3) {// 输出全部
//						return test.get_log().getLogByKey(cmd[2], 0);
//					} else if (cmd.length == 4) {
//						return test.get_log().getLogByKey(cmd[2],
//								Integer.parseInt(cmd[3]));
//					} else if (cmd.length == 8) {
////						return test.get_log().getLogs(cmd[2],
////								Integer.parseInt(cmd[3]),
////								cmd[4] + " " + cmd[5], cmd[6] + " " + cmd[7]);
//					}
//					break;
//				case "time":
//					// return test.get_log().getLogByTime("2012-9-3 1:1:1",
//					// "2012-9-5 1:1:1");
//					return test.get_log().getLogByTime(cmd[2] + " " + cmd[3],
//							cmd[4] + " " + cmd[5]);
//				case "certain_props_use_buy":
////					return test.get_log().getCertainPropsUseOrBuyNum(cmd[2],
////							cmd[3], cmd[4] + " " + cmd[5],
////							cmd[6] + " " + cmd[7]);
//				case "login_num":
////					 String.valueOf(test.get_log().getLoginNum(
////							cmd[2] + " " + cmd[3], cmd[4] + " " + cmd[5]));
//					break;
//				case "max_online_num":
////					 test.get_log().getMaxOnlineNum(
////							cmd[2] + " " + cmd[3], cmd[4] + " " + cmd[5]);
//					break;
//				case "average_online_num":
////					return test.get_log().getAverageOnlineNum(
////							cmd[2] + " " + cmd[3], cmd[4] + " " + cmd[5]);
//				case "use_diamond":
////					return String.valueOf(test.get_log().getUseDiamond(
////							cmd[2] + " " + cmd[3], cmd[4] + " " + cmd[5]));
//				case "certain_player_use_diamond":
////					return String.valueOf(test.get_log()
////							.getCertainPlayerUseDiamond(cmd[2],
////									cmd[3] + " " + cmd[4],
////									cmd[5] + " " + cmd[6]));
//				case "buy_props_num":
////					return test.get_log().getEachPropsBuyNum(
////							cmd[2] + " " + cmd[3], cmd[4] + " " + cmd[5]);
//					break;
//				case "certain_props_buy_use_num":
////					return String.valueOf(test.get_log()
////							.getCertainPropsBuyOrUseNum(cmd[2], cmd[3],
////									cmd[4] + " " + cmd[5],
////									cmd[6] + " " + cmd[7]));
//				case "props_use_num":
////					return test.get_log().getEachPropsUseNum(
////							cmd[2] + " " + cmd[3], cmd[4] + " " + cmd[5]);
//					break;
//				case "box_use_num":
//					return String.valueOf(test.get_log().getBoxUseNum(
//							cmd[2] + " " + cmd[3], cmd[4] + " " + cmd[5]));
//				case "box_cost_chip_num":
//					return String.valueOf(test.get_log().getBoxCostChipNum(
//							cmd[2] + " " + cmd[3], cmd[4] + " " + cmd[5]));
//				case "box_award_num":
//					return String.valueOf(test.get_log().getBoxAwardNum(
//							cmd[2] + " " + cmd[3], cmd[4] + " " + cmd[5]));
//				}
			}
		} catch (Exception e) {
			// GameLog.error("", e);
			e.printStackTrace();
			return e.getMessage();
		}
		return null;
	}

	public static MhxxConsoleHandler getInstance() {
		return instance;
	}

}