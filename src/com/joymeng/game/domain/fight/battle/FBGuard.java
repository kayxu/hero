package com.joymeng.game.domain.fight.battle;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.obj.FightMonster;
import com.joymeng.game.domain.fight.obj.FightPlayer;
import com.joymeng.game.domain.force.Mob;
import com.joymeng.game.domain.force.MobManager;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.monster.Monster;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.FightRequest;

public class FBGuard extends FightBattleBase {
	static Logger logger = LoggerFactory.getLogger(FBGuard.class);

	public FBGuard(FightRequest _request, PlayerCharacter _player) {
		super(_request, _player);
	}

	@Override
	public boolean init() {
		if (super.init()) {
			QuestUtils.checkFinish(player, QuestUtils.TYPE4, true, type, 0);
			return true;
		}
		return false;
	}

	@Override
	public boolean initDefencer() {
		// 驻防为三方战斗，分别为攻击方A / 被攻击方B/ B上的驻防玩家C
		World gameWorld = World.getInstance();
		int BId = request.getDefenceUser();// 被攻击的玩家id
		logger.info("初始化驻防场战斗，参数=" + BId);
		PlayerCharacter BUser = gameWorld.getPlayer(BId);// 被攻击的玩家
		if (BUser == null) {
			tip.setMessage("被攻击的玩家不存在,BId=" + BId);
			logger.info("被攻击的玩家不存在,BId=" + BId);
			return false;
		}
		long CId = BUser.getPlayerBuilgingManager().getPmc().getMainCity()
				.getOccupyUserId();// 占领该玩家的userId
		int hid = BUser.getPlayerBuilgingManager().getPmc().getMainCity()
				.getOfficerId();
		if (hid == 0) {// 还没有被占领过
			if (BId == player.getId()) {
				player.getPlayerBuilgingManager().guard(true,
						attacker.getHero().getId(),
						FightUtil.changeSoldierToStr(attacker.getSoldier()),
						BId, "",null, null,
						attacker.getWinType(),0);
//				tip.setMessage("fight  驻防成功 ,BId=" + BId + " CId=" + CId);
				tip.setMessage("驻防成功");
				return false;
			} else {
				if(!player.getPlayerBuilgingManager().isFuCityFull()){
					tip.setMessage("无空余附属城");
					return false;
				}
				Mob force = GameDataManager.forceManager.getForce(121);
				if (force == null) {
					tip.setMessage("mob 不存在,id=" + 121);
					return false;
				}
				Monster monster = GameDataManager.monsterManager
						.getMonster(force.getGeneral());
				if (monster == null) {
					tip.setMessage("monster 不存在,id=" + force.getGeneral());
					return false;
				}
				if (!defencer.init(null, new FightMonster(monster),
						force.getSoldier(), type)) {
					tip.setMessage("防御方初始化错误:" + defencer.getTip().getMessage());
					return false;
				}
				return true;
			}
			// 直接占领
			// player.getPlayerBuilgingManager().guard(
			// attacker.getHero().getId(),
			// FightUtil.changeSoldierToStr(attacker.getSoldier()),
			// BId, "", attacker.getHero().getId(), null, null);
			// tip.setMessage("fight  直接占领成功 ,BId=" + BId + " CId=" + CId);
			// QuestUtils.checkFinish(player, 4, true,type, 0);
			// QuestUtils.checkFinish(player, 5, true,type,
			// 0,attacker.getWinType());
			// QuestUtils.checkFinish(player, 30, true);
			// 怪物

		} else {
			if (CId == 0) {
				CId = BUser.getData().getUserid();
			}
			
			// 驻防的是自己的将领
			if (CId == player.getData().getUserid()) {
				tip.setMessage(" 驻防的是自己的将领");
				return false;
			}
			if(BId != player.getId()){//不是自己的附属城
				if(!player.getPlayerBuilgingManager().isFuCityFull()){
					tip.setMessage("无空余附属城");
					return false;
				}
			}
			
			PlayerCharacter CUser = gameWorld.getPlayer((int) CId);// 驻防玩家C
			if (CUser == null) {
				tip.setMessage("fight CUser=null,id=" + CId);
				return false;
			}

			PlayerHero ph = CUser.getPlayerHeroManager().getHero(hid);
			if (ph == null) {
				tip.setMessage("战斗错误,uid=" + CId + " heroId=" + hid);
				return false;
			}
			
			if (!defencer.init(CUser, new FightPlayer(CUser, ph),
					BUser.getPlayerBuilgingManager().getPmc().getMainCity().getSoldierMsg(), type)) {
				tip.setMessage("防御方初始化错误:" + defencer.getTip().getMessage());
				return false;
			}
			return true;
		}

	}

	@Override
	public void saveResult() {
		attacker.recoverSoldier();
		// 攻守双方的士兵
		String soldier = FightUtil.changeSoldierToStr(attacker.getSoldier());
		String defSoldier = FightUtil.changeSoldierToStr(defencer.getSoldier());
		// 保存战斗日志
		FightEvent fightEvent1 = createFightEvent(attacker);
		// 保存战斗日志
		FightEvent fightEvent2 = null;
		int defHero = 0;
		if (defencer.getPc() != null) {
			fightEvent2 = createFightEvent(defencer);
			defHero = ((FightPlayer) defencer.getHero()).getHero().getId();
		}
		if (attacker.getWinType() == FightConst.FIGHT_WIN) {// 攻击方战斗胜利，占领
			int heroId = ((FightPlayer) attacker.getHero()).getHero().getId();
			attacker.getPc()
					.getPlayerBuilgingManager()
					.guard(true,heroId, soldier, request.getDefenceUser(),
							defSoldier, fightEvent1, fightEvent2,
							attacker.getWinType(),defHero);
		} else {
			// 防守方胜利
			attacker.getPc()
					.getPlayerBuilgingManager()
					.guard(false,attacker.getHero().getId(), soldier, request.getDefenceUser(), defSoldier,
							 fightEvent1,
							fightEvent2, attacker.getWinType(),defHero);
		}
		super.saveResult(fightEvent1, fightEvent2);
	}
	

	@Override
	public void getReward() {
		this.addLog(FightUtil.getFightResult(attacker, this.type, (byte) 0,defencer));
		this.addLog(FightUtil.getFightResult(defencer, this.type, (byte) 0,attacker));
		QuestUtils.checkFinish(player, QuestUtils.TYPE5, true, type, 0, attacker.getWinType());
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
