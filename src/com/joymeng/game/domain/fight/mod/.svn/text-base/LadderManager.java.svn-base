package com.joymeng.game.domain.fight.mod;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.net.client.ClientModule;
import com.sun.imageio.plugins.common.I18N;

/**
 * 天梯管理
 * @author admin
 * @date 2012-6-12
 * TODO
 */
public class LadderManager {
	static final Logger logger = LoggerFactory.getLogger(LadderManager.class);
	private static LadderManager instance;
	private HashMap<Integer, Ladder> ladderMap = new HashMap<Integer, Ladder>();
	public static LadderManager getInstance() {
		if (instance == null) {
			instance = new LadderManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, Ladder.class);
		for (Object obj : list) {
			Ladder data = (Ladder) obj;
			ladderMap.put(data.getId(), data);
		}
	}
	public Ladder getLadder(int id){
		return ladderMap.get(id);
	}
	/**
	 * 重置天梯，2次免费，1次付费
	 * 
	 * @param player
	 * @param level
	 *            10 倍数
	 * @return
	 */
	public static boolean reset(PlayerCharacter player, byte level,byte charge) {
		int id=level-level%20;
		int curId = player.getData().getLadderId();
		String ladder = player.getData().getLadder();
		LadderInfo li=new LadderInfo(ladder);
//		if((charge==0&&li.getFreeNum()<=0)||(charge==1&&li.getChargeNum()<=0)){ 
//			System.out.println(" ladder reset error ! charge=" + charge+" num<=0");
//			return false;
//		}
		// 重置
		try {
			//如果有免费，先扣除免费次数
			if(li.getFreeNum()>0){
				li.setFreeNum(li.getFreeNum()-1);
				li.setTime(TimeUtils.nowLong());
			}else{
				if(li.getChargeNum()>0){
					li.setChargeNum(li.getChargeNum()-1);
					li.setTime(TimeUtils.nowLong());
				}else{
					logger.info("充值天梯失败，uid="+player.getId()+" ladder="+ladder);
					return false;
				}
			}
//			if(charge==0){
//				li.setFreeNum(li.getFreeNum()-1);
//			}else{
//				int m=GameConfig.ladderFresh;
//				
//				//下面提示语句是否应该放到与此if对应的else中
//				if(player.saveResources( GameConfig.JOY_MONEY, -m)<0){
//					player.setTip(new TipMessage("金钱不足",ProcotolType.FIGHT_RESP,GameConst.GAME_RESP_FAIL,(byte)0));
//					return false;
//				}
//				logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " uid=" + player.getId() + " uname=" + player.getData().getName() 
//						+ " diamondNum=" + m + " description=通天塔购买重置天梯");
//				
//				li.setChargeNum(li.getChargeNum()-1);
//			}
//			li.setTime(TimeUtils.nowLong());
			
		} catch (Exception ex) {
			return false;
		}
		player.getData().setLadder(li.toStr());
		player.getData().setLadderId((byte)id);
		return true;
	}
	
	/**
	 * 购买可以付费刷新的次数,消耗2个钻石，每天只能买一次
	 * @param player
	 * @param n
	 * @return
	 */
	public static boolean buyChargeNum(PlayerCharacter player){
		int curId = player.getData().getLadderId();
		String ladder = player.getData().getLadder();
		LadderInfo li=new LadderInfo(ladder);
		if(li.getBuyNum()>0){
			logger.info("购买天梯次数失败，uid="+player.getId()+" ladder="+ladder);
//			player.setTip(new TipMessage("购买天梯次数失败",ProcotolType.FIGHT_RESP,GameConst.GAME_RESP_FAIL,(byte)0));
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(new TipMessage("Your today's Purchase Times have been used up.",ProcotolType.SHOP_RESP,GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("今日购买次数已满",ProcotolType.SHOP_RESP,GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}
			
			return false;
		}
		int m=GameConfig.ladderFresh;
		if(player.saveResources( GameConfig.JOY_MONEY, -m)<0){
			player.setTip(new TipMessage("金钱不足",ProcotolType.FIGHT_RESP,GameConst.GAME_RESP_FAIL,(byte)0));
			return false;
		}
//		logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " uid=" + player.getId() + " uname=" + player.getData().getName() 
//				+ " diamondNum=" + m + " description=通天塔购买重置天梯");
		GameLog.logSystemEvent(LogEvent.USE_DIAMOND, String.valueOf(GameConst.DIAMOND_LADDERRESET),"", String.valueOf(m),String.valueOf(player.getId()));

		li.setBuyNum(1);
		li.setChargeNum(1);
		player.getData().setLadder(li.toStr());
		return true;
	}
	/**
	 * 更新某一个玩家的战役情况
	 * @param player
	 */
	public void update(PlayerCharacter player){
		String ladder = player.getData().getLadder();
		LadderInfo li=new LadderInfo(ladder);
		if(ladder==null||ladder.equals("")){//第一次
		}else{
//			//判断时间是否过期
				if(!TimeUtils.isSameDay(li.getTime())){//不是同一天，清空
					player.getData().setLadder(new LadderInfo("").toStr());
				}
//			}
		}
	}
}
