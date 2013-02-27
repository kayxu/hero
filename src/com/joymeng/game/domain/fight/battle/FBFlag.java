package com.joymeng.game.domain.fight.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.obj.FightCreature;
import com.joymeng.game.domain.fight.obj.FightGroup;
import com.joymeng.game.domain.fight.obj.FightPlayer;
import com.joymeng.game.domain.fight.obj.FightSoldier;
import com.joymeng.game.domain.flag.FlagLattice;
import com.joymeng.game.domain.flag.FlagManager;
import com.joymeng.game.domain.flag.GameStart;
import com.joymeng.game.domain.flag.Room;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.FightRequest;

public class FBFlag extends FightBattleBase {
	static Logger logger = LoggerFactory.getLogger(FBFlag.class);

	FlagManager fgr = FlagManager.getInstance();

	public FBFlag(FightRequest _request, PlayerCharacter _player) {
		super(_request, _player);
	}

	private FlagLattice defFlag = null;
	private FlagLattice attFlag = null;
	private Room room = null;
	
	@Override
	public boolean initDefencer() {
		String tipps = "";
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			tipps = "Error! Please try again.";
		}else{
			tipps = "战斗错误,请重试";
		}
		boolean isFight = false;
		String roomid = request.getRoomid();
		Byte defId = Byte.valueOf((byte) request.getId());
		Byte attId = Byte.valueOf((byte) request.getFromId());
		room = fgr.roomMap.get(roomid);
		// 根据id去查找相应的房间数据
		if (room == null) {
			tip.setMessage("home is null" + roomid);
		} else {
			StringBuffer sb = new StringBuffer();
			defFlag = room.getFlagLst().get(defId);
			attFlag = room.getFlagLst().get(attId);
			if (attFlag == null) {
				if (!attacker.dispachSoldier()) {
					tip.setMessage("派兵失败");
					if(I18nGreeting.LANLANGUAGE_TIPS ==1){
						tip.setMessage("fail");
					}
					return false;
				}
			}
			if (defFlag != null) {
				TipUtil tips = room.isFight(attacker.getPc().getId(), attacker
						.getHero().getHero().getId(), FightUtil.changeSoldierToStr(attacker.getSoldier()),
						attId, defId);
				if (tips.isResult()) {// 可以战斗
					PlayerCharacter pc = World.getInstance().getOnlineRole(
							defFlag.getUserid());// 被攻击的玩家
					PlayerHero ph = pc.getPlayerHeroManager().getHero(
							defFlag.getHeroid());// 武将
					if (pc != null && ph != null) {
						int[] buff = tips.getBufint();
						if (!defencer.init(pc, new FightPlayer(pc, ph),
								defFlag.getSoinfo(), type)) {
							logger.info(tipps);
							tip.setMessage("防御方初始化错误:"
									+ defencer.getTip().getMessage());
						} else {
							sb.append("|攻击方加成：" + buff[0]);
							sb.append("|防御方加成：" + buff[1]);
							logger.info("加成数据：" + sb.toString());
							setBuff(attacker, buff[0]);
							setBuff(defencer, buff[1]);
//							GameUtils.sendTip(new TipMessage(sb.toString(),
//									GameConst.GAME_RESP_SUCCESS,
//									GameConst.GAME_RESP_SUCCESS), attacker
//									.getPc().getUserInfo());
							isFight = true;
						}
					} else {
						logger.info("防御玩家错误" + defId);
						tip.setMessage(tipps);
						// TODO 游戏结束
						room.quitRoom(0, defFlag.getUserid(),false);
					}
				} else {
					tip.setMessage(tips.getResultMsg());
				}
			} else {
				logger.info("移动到 格子 is null" + defId);
				tip.setMessage(tipps);
			}
		}
		GameUtils.sendTip(tip, attacker.getPc().getUserInfo(),GameUtils.FLUTTER);
		return isFight;
	}

	@Override
	public void sendFightMessage() {
		// sendNotify(attacker);
		// sendNotify(defencer);
		int attDealNum = 0;
		int defDealNum =0;
		for (FightCreature fc : attacker.getSoldier()) {// 计算攻击方死亡总数
			attDealNum +=fc.getLoseNum();
		}
		for (FightCreature fc : defencer.getSoldier()) {// 计算防守方 死亡总数
			defDealNum +=fc.getLoseNum();
		}
		if (attacker.getWinType() == FightConst.FIGHT_WIN) {// 攻击方获胜
			// String winMsg = "";
			String winMsg = I18nGreeting.getInstance().getMessage(
					"flag.fight.att.win",
					new Object[] { attacker.getHero().getHero().getName(),
							defencer.getPc().getData().getName(),
							defencer.getHero().getHero().getName() });
			String failMsg = I18nGreeting.getInstance().getMessage(
					"flag.fight.def.fail",
					new Object[] { defencer.getHero().getHero().getName(),
							attacker.getPc().getData().getName(),
							attacker.getHero().getHero().getName() });

			FlagManager.getInstance().sendOne(new GameStart((byte) 2, winMsg,FightConst.FIGHT_WIN,defFlag.getPoint(),attDealNum),
					new PlayerCharacter[] { attacker.getPc() });
			FlagManager.getInstance().sendOne(new GameStart((byte) 2, failMsg,FightConst.FIGHT_LOSE,defFlag.getPoint(),defDealNum),
					new PlayerCharacter[] { defencer.getPc() });
			// GameUtils.sendTip(new TipMessage("你胜利了", type,
			// GameConst.GAME_RESP_SUCCESS), attacker.getPc()
			// .getUserInfo());
			// GameUtils.sendTip(new TipMessage("你失败了", type,
			// GameConst.GAME_RESP_SUCCESS), defencer.getPc()
			// .getUserInfo());
			logger.info("动画点："+defFlag.getPoint());
		} else {
			String failMsg = I18nGreeting.getInstance().getMessage(
					"flag.fight.att.fail",
					new Object[] { attacker.getHero().getHero().getName(),
							defencer.getPc().getData().getName(),
							defencer.getHero().getHero().getName() });
			String winMsg = I18nGreeting.getInstance().getMessage(
					"flag.fight.def.win",
					new Object[] { defencer.getHero().getHero().getName(),
							attacker.getPc().getData().getName(),
							attacker.getHero().getHero().getName() });
			
			FlagManager.getInstance().sendOne(new GameStart((byte) 2, failMsg,FightConst.FIGHT_LOSE,defFlag.getPoint(),attDealNum),
					new PlayerCharacter[] { attacker.getPc() });
			FlagManager.getInstance().sendOne(new GameStart((byte) 2, winMsg,FightConst.FIGHT_WIN,defFlag.getPoint(),defDealNum),
					new PlayerCharacter[] { defencer.getPc() });
			// GameUtils.sendTip(new TipMessage("你胜利了", type,
			// GameConst.GAME_RESP_SUCCESS), defencer.getPc()
			// .getUserInfo());
			// GameUtils.sendTip(new TipMessage("你失败了", type,
			// GameConst.GAME_RESP_SUCCESS), attacker.getPc()
			// .getUserInfo());
			logger.info("动画点："+defFlag.getPoint());
		}
	}

	@Override
	public void saveResult() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("|保存战斗结果数据 \n");
		sb.append("┌────────────────────────────────┐\n");
		// 保存战斗日志
		FightEvent fightEvent1 = createFightEvent(attacker);
		// 保存战斗日志
		FightEvent fightEvent2 = createFightEvent(defencer);
		String str1 = FightUtil.changeSoldierToStr(attacker.getSoldier());
		String str2 = FightUtil.changeSoldierToStr(defencer.getSoldier());
		Map<Integer,Integer> allAttDead = new HashMap<Integer, Integer>();
		Map<Integer,Integer> allDefDead = new HashMap<Integer, Integer>();
		if (attacker.getWinType() == FightConst.FIGHT_WIN) {// 攻击方获胜
			for (FightCreature fc : attacker.getSoldier()) {// 计算攻击方死亡总数
				FightSoldier soldier = (FightSoldier) fc;
				allAttDead.put(soldier.getId(), fc.getLoseNum());
			}
			for (FightCreature fc : defencer.getSoldier()) {// 计算防守方 死亡总数
				FightSoldier soldier = (FightSoldier) fc;
				allDefDead.put(soldier.getId(), fc.getLoseNum());
			}
			room.move(true, attacker.getPc().getId(), attacker.getHero()
					.getHero().getId(), str1, attFlag, defFlag, str2,
					allAttDead, allDefDead);
			defencer.recoverSoldier();// 回收士兵
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				fightEvent1.setMemo("You dispatched hero "
					     + attacker.getHero().getHero().getName()
					     + ", won PVP battle and occupied the stronghold.");// 攻击方 成功
				fightEvent2.setMemo("Your hero " + defencer.getHero().getHero().getName()
					     + " was defeated by enemy in PVP battle.");// 防御方 失败
			}else{
				fightEvent1.setMemo("你派遣武将"
						+ attacker.getHero().getHero().getName()
						+ "在兵棋战中击败了对方，占领了据点");// 攻击方 成功
				fightEvent2.setMemo("你的武将" + defencer.getHero().getHero().getName()
						+ "在兵棋战中被对方击败了");// 防御方 失败
			}
			
			sb.append("|用户："+attacker.getPc().getId()+"|士兵类型："+allAttDead.keySet().toString()+"\n");
			sb.append("|用户："+attacker.getPc().getId()+"|士兵数量："+allAttDead.values().toString()+"\n");
			sb.append("|用户："+defencer.getPc().getId()+"|士兵类型："+allDefDead.keySet().toString()+"\n");
			sb.append("|用户："+defencer.getPc().getId()+"|士兵数量："+allDefDead.values().toString()+"\n");
		} else {
			for (FightCreature fc : attacker.getSoldier()) {// 计算攻击方死亡总数
				FightSoldier soldier = (FightSoldier) fc;
				allAttDead.put(soldier.getId(), fc.getLoseNum());
//				allAttDead += fc.getLoseNum();
			}
			for (FightCreature fc : defencer.getSoldier()) {// 计算防守方 死亡总数
				FightSoldier soldier = (FightSoldier) fc;
				allDefDead.put(soldier.getId(), fc.getLoseNum());
			}
			room.move(false, attacker.getPc().getId(), attacker.getHero()
					.getHero().getId(), str1, attFlag, defFlag, str2,
					allAttDead, allDefDead);
			attacker.recoverSoldier();// 回收士兵
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				fightEvent1.setMemo("Your hero " + attacker.getHero().getHero().getName()
						+ " was defeated by enemy in PVP battle.");// 攻击方 失败
				fightEvent2.setMemo("Your hero " + defencer.getHero().getHero().getName()
						+" defended the attack from enemy in PVP battle");// 防御方 成功
			}else{
				fightEvent1.setMemo("你的武将" + attacker.getHero().getHero().getName()
						+ "在兵棋战中攻击对方据点时失败了");// 攻击方 失败
				fightEvent2.setMemo("你的武将" + defencer.getHero().getHero().getName()
						+ "在兵棋战中抵御了对方的进攻");// 防御方 成功
			}
			
			sb.append("|用户："+attacker.getPc().getId()+"|士兵类型："+allAttDead.keySet().toString()+"\n");
			sb.append("|用户："+attacker.getPc().getId()+"|士兵数量："+allAttDead.values().toString()+"\n");
			sb.append("|用户："+defencer.getPc().getId()+"|士兵类型："+allDefDead.keySet().toString()+"\n");
			sb.append("|用户："+defencer.getPc().getId()+"|士兵数量："+allDefDead.values().toString()+"\n");
		}
		sb.append("└────────────────────────────────┘\n");
		logger.info("攻击方剩余士兵：" + str1 + "|防御方剩余士兵：" + str2);
		if(room != null){
			room.setDealNum(attacker.getPc().getId(), allAttDead, allDefDead);
		}
		logger.info(sb.toString()+"\n");
		super.saveResult(fightEvent1, fightEvent2);
	}

	@Override
	public void getReward() {
		this.addLog(FightUtil.getFightResult(attacker, this.type, (byte) 0,
				defencer));
		this.addLog(FightUtil.getFightResult(defencer, this.type, (byte) 0,
				attacker));
	}

	@Override
	public boolean sendSoldier() {

		return true;
	}

	/**
	 * 设置攻防buff
	 * 
	 * @param fb
	 * @param per
	 */
	public void setBuff(FightGroup fb, int per) {
		if (per > 0) {
			logger.info("类型：" + fb.getAttackType() + "|加成：" + per);
			List<FightCreature> list = fb.getSoldier();
			for (FightCreature fc : list) {
				fc.setAttack(fc.getAttack() * (1 + per / (float) 100.0));
				fc.setDefence(fc.getDefence() * (1 + per / (float) 100.0));
			}
		}
	}
}
