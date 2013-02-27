package com.joymeng.game.domain.fight.mod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;

/**
 * 天梯
 * 
 * @author admin
 * @date 2012-6-11 
 */
public class Ladder {
	 static final Logger logger = LoggerFactory.getLogger(Ladder.class);
	private int id;
	private String name;
	private int level;
	private int money;
	private int exp;
	private int equip;
	private int rate;
	private int quality;
	private int num;
	private int propId;
	private int forceId;

	private int freeNum;
	private int chargeNum;

	private String text;
	private String pic;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getEquip() {
		return equip;
	}

	public void setEquip(int equip) {
		this.equip = equip;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}

	public int getForceId() {
		return forceId;
	}

	public void setForceId(int forceId) {
		this.forceId = forceId;
	}

	public int getFreeNum() {
		return freeNum;
	}

	public void setFreeNum(int freeNum) {
		this.freeNum = freeNum;
	}

	public int getChargeNum() {
		return chargeNum;
	}

	public void setChargeNum(int chargeNum) {
		this.chargeNum = chargeNum;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	/**
	 * 更新天梯进度
	 * 
	 * @param player
	 * @param result
	 *            0失败 1成功
	 */
	public void updateLadderId(PlayerCharacter player, byte result, byte charge) {
		String ladder = player.getData().getLadder();
		LadderInfo li=new LadderInfo(ladder);
		
		if (result == FightConst.FIGHT_WIN) {// 成功
			if (this.id > player.getData().getLadderId()) {
				// 更新进度
				if (this.id > player.getData().getLadderMax()) {
					player.getData().setLadderMax((byte)this.id);
				}
			}
			//根据战役进度进行系统广播
			if(id%10==0){
				String msg = I18nGreeting.getInstance().getMessage("ladder.lv"+id, new Object[]{player.getData().getName(),id});
				logger.info("ladder msg="+msg);
				if(!"".equals(msg)){
					NoticeManager.getInstance().sendSystemWorldMessage(msg);//系统消息
				}
//				GameUtils.sendWolrdMessage(new TipMessage(msg, ProcotolType.CHAT_RESP,
//						GameConst.GAME_RESP_SUCCESS),(byte) 1);
			}
			player.getData().setLadderId((byte) this.id);
		} else {
			// 失败，更新状态为停止状态
//			li.setStatus(STATUS_STOP);
			player.getData().setLadder(li.toStr());
		}
//		World.getInstance().savePlayer(player);
	}

//	 * 状态0，可刷新次数1,2，时间3
//	 * @param charge
//	 * @return
//	 */
//	public static String getInitStr() {
//		String str = STATUS_FREE  + ";" + "2;1" + ";"
//				+ TimeUtils.nowLong();
//		return str;
//	}

	/**
	 * 检测是否能进入
	 * 
	 * @param player
	 * @param charge
	 * @return
	 */
	public boolean checkEnter(PlayerCharacter player, byte charge) {
		String ladder = player.getData().getLadder();
		LadderInfo li=new LadderInfo(ladder);
		try {
			if (!TimeUtils.isSameDay(li.getTime())) {// 不是同一天，重置数据
				li=new LadderInfo("");
				player.getData().setLadder(li.toStr());
			} 
		} catch (Exception ex) {
			GameLog.error("Ladder chekEnter error", ex);
		}
		// 每次只能打下一个
		if (this.id != (player.getData().getLadderId() + 1)) {
			logger.info("天梯战失败，id="+this.id+" ladderId="+player.getData().getLadderId());
			player.setTip(new TipMessage("当前进度为"+player.getData().getLadderId(),ProcotolType.FIGHT_RESP,GameConst.GAME_RESP_FAIL,(byte)0));
			return false;
		}
		if(charge==1){//自动战斗,打一关扣一次钱 
			if(player.saveResources( GameConfig.JOY_MONEY, -GameConfig.ladderMoney)>0){
//				logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " uid=" + player.getId() + " uname=" + player.getData().getName() 
//						+ " diamondNum=" + GameConfig.ladderMoney + " description=通天塔自动战斗");
				GameLog.logSystemEvent(LogEvent.USE_DIAMOND, String.valueOf(GameConst.DIAMOND_LADDERFIGHT),"", String.valueOf(GameConfig.ladderMoney),String.valueOf(player.getId()));

				return true;
			}else{
				player.setTip(new TipMessage("金钱不足",ProcotolType.FIGHT_RESP,GameConst.GAME_RESP_FAIL,(byte)0));
				return false;
			}
		}
		return true;
	}

	
	public static final byte STATUS_FREE=0;//未开始
	public static final byte STATUS_START = 1;//开始
	public static final byte STATUS_STOP = 2;//结束
}
