package com.joymeng.game.domain.fight.battle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.obj.FightCreature;
import com.joymeng.game.domain.fight.obj.FightPlayer;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.nation.Veins;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.FightRequest;

public class FBVeins extends FightBattleBase {
	private Veins veins = null;
	static Logger logger = LoggerFactory.getLogger(FBVeins.class);

	public FBVeins(FightRequest _request, PlayerCharacter _player) {
		super(_request, _player);
	}

	@Override
	public boolean initDefencer() {
		int id = request.getId();
		QuestUtils.checkFinish(player, QuestUtils.TYPE41, true);
		logger.info("初始化veins战斗，参数=" + id);
		veins = NationManager.getInstance().allVeinsMap.get(id);
		if (veins == null) {
			tip.setMessage("veins错误，id=" + id);
			return false;
		}
		if (!veins.checkFight(player,
				((FightPlayer) attacker.getHero()).getHero(),
				FightUtil.changeSoldierToStr(attacker.getSoldier()), "")) {
			//tip.setMessage("战斗veins错误");
			return false;
		}
		World gameWorld = World.getInstance();
		PlayerCharacter p2 = gameWorld.getPlayer(veins.getUserId());
		if (p2 == null) {
			tip.setMessage("不存在该玩家,id=" + veins.getUserId());
			return false;
		}
		PlayerHero hero2 = p2.getPlayerHeroManager().getHero(veins.getHeroId());
		if (hero2 == null) {
			tip.setMessage("不存在该将领,id=" + veins.getHeroId());
			return false;
		}
		logger.info("|神祠剩余士兵："+veins.getBaseSoMsg());
		String str = veins.dispatchV(
				FightUtil.changeSoldierToStr(attacker.getSoldier()),
				hero2.getSoldierNum());
		logger.info("XXXXXXXXXXX防御方派出士兵："+str +"|神祠剩余士兵："+veins.getBaseSoMsg());
		
		if (!defencer.init(p2, new FightPlayer(p2,hero2), str, type)) {
			tip.setMessage("防御方初始化错误:"+defencer.getTip().getMessage());
			return false;
		}
		return true;
	}

	@Override
	public void saveResult() {
		// 回收士兵
		attacker.recoverSoldier();
		// 保存战斗日志
		FightEvent fightEvent1 = createFightEvent(attacker);
		// 保存战斗日志
		FightEvent fightEvent2 = createFightEvent(defencer);
		
		logger.info("战报att:"+fightEvent1 +"|战报def:"+fightEvent2);
		String str1 = FightUtil.changeSoldierToStr(attacker.getSoldier());
		String str2 = FightUtil.changeSoldierToStr(defencer.getSoldier());
		int allDead=0;
		for(FightCreature fc:defencer.getSoldier()){
			allDead+=fc.getLoseNum();
		}
		if (attacker.getWinType() == FightConst.FIGHT_WIN) {// 战斗胜利
			
			logger.info("对方死亡总数="+allDead);
			logger.info("防守失败，回收士兵："+str2);
			TipUtil tips = veins.occupyVeins(attacker.getPc(), attacker.getHero().getId(),
					str1, str2, attacker.getHero().getId(), fightEvent1,
					fightEvent2,allDead);
			//GameUtils.sendTip(tips.getTip(),attacker.getPc().getUserInfo());
		} else {
			logger.info("防守成功，回收士兵："+str2);
			TipUtil tips = veins.occupyVeins(attacker.getPc(), 0, str1, str2, attacker
					.getHero().getId(), fightEvent1, fightEvent2,allDead);
			//GameUtils.sendTip(tips.getTip(),attacker.getPc().getUserInfo());
		}
		super.saveResult(fightEvent1, fightEvent2);
	
	}

	@Override
	public void getReward() {
		this.addLog(FightUtil.getFightResult(attacker, this.type,
				(byte) 0,defencer));
		this.addLog(FightUtil.getFightResult(defencer, this.type,
				(byte) 0,attacker));
	}

	@Override
	public boolean sendSoldier() {
		// 攻击方派兵
		if (!attacker.dispachSoldier()) {
			tip.setMessage("派兵失败");
			return false;
		}
		return true;
	}
}
