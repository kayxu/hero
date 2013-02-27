package com.joymeng.game.domain.fight.battle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.obj.FightPlayer;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.nation.GoldMine;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.nation.RefreshResources;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.FightRequest;

public class FBGold extends FightBattleBase {
	private GoldMine goldMine = null;
	static Logger logger = LoggerFactory.getLogger(FBGold.class);

	public FBGold(FightRequest _request, PlayerCharacter _player) {
		super(_request, _player);
	}

	@Override
	public boolean initDefencer() {
		int id = request.getId();
		goldMine = NationManager.getInstance().allGoldMap.get(id);
		QuestUtils.checkFinish(player, QuestUtils.TYPE38, true);
		logger.info("初始化goldMine战斗，参数=" + id);
		if (goldMine == null) {
			logger.info("goldMine错误，id=" + id);
			tip.setMessage("");
			return false;
		}
		if (!goldMine.check(player, attacker.getHero().getId(), FightUtil
				.changeSoldierToStr(attacker.getSoldier()), attacker.getHero()
				.getId())) {
			GameUtils.sendTip(new TipMessage("",
					ProcotolType.FIGHT_RESP, GameConst.GAME_RESP_FAIL, type),
					player.getUserInfo(),GameUtils.FLUTTER);
			tip.setMessage("");
			return false;
		}
		World gameWorld = World.getInstance();
		PlayerCharacter pc = gameWorld.getPlayer(goldMine.getUserId());// 被攻击的玩家
		if (pc == null) {
			logger.info("战斗goldMine错误,uid=" + goldMine.getUserId());
			tip.setMessage("");
			return false;
		}
		PlayerHero ph = pc.getPlayerHeroManager().getHero(goldMine.getHeroId());
		if (ph == null) {
			logger.info("战斗goldMine错误,uid=" + goldMine.getUserId()
					+ " heroId=" + goldMine.getHeroId());
			tip.setMessage("");
			return false;
		}
		if (!defencer.init(pc, new FightPlayer(pc, ph), goldMine.getSoMsg(),
				type)) {
			tip.setMessage("防御方初始化错误:" + defencer.getTip().getMessage());
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
		String str1 = FightUtil.changeSoldierToStr(attacker.getSoldier());
		String str2 = FightUtil.changeSoldierToStr(defencer.getSoldier());
		if (attacker.getWinType() == FightConst.FIGHT_WIN) {// 战斗胜利
			goldMine.occBattle(attacker.getPc(), attacker.getHero().getId(),
					str1, str2, attacker.getHero().getId(), fightEvent1,
					fightEvent2);
		} else {
			goldMine.occBattle(attacker.getPc(), 0, str1, str2, attacker
					.getHero().getId(), fightEvent1, fightEvent2);
		}
		super.saveResult(fightEvent1, fightEvent2);
	}

	@Override
	public void getReward() {
		this.addLog(FightUtil.getFightResult(attacker, this.type, (byte) 0,defencer));
		this.addLog(FightUtil.getFightResult(defencer, this.type, (byte) 0,attacker));

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
