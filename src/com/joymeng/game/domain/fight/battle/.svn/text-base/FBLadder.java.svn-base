package com.joymeng.game.domain.fight.battle;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.mod.Ladder;
import com.joymeng.game.domain.fight.obj.FightMonster;
import com.joymeng.game.domain.force.Mob;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.monster.Monster;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.net.request.FightRequest;

public class FBLadder extends FightBattleBase {
	static Logger logger = LoggerFactory.getLogger(FBLadder.class);
	private Ladder ladder = null;

	public FBLadder(FightRequest request, PlayerCharacter player) {
		super(request, player);
	}

	@Override
	public boolean init() {
		if (super.init()) {
			QuestUtils.checkFinish(player, QuestUtils.TYPE4, true, type, ladder.getId());
			return true;
		}
		return false;
	}

	@Override
	public boolean initDefencer() {
		int id = request.getId();
		byte charge = request.getCharge();// 是否付费 0免费1付费
		logger.info("初始化战役场战斗，参数=" + id + " charge=" + charge);
		ladder = GameDataManager.ladderManager.getLadder(id);
		if (ladder == null) {
			tip.setMessage("ladder=null,id=" + id);
			return false;
		}
		// 判断是否可以进入
		if (ladder.checkEnter(player, charge)) {
			Mob force = GameDataManager.forceManager.getForce(ladder
					.getForceId());
			if (force == null) {
				tip.setMessage("部队=null,id=" + ladder.getForceId());
				return false;
			}
			Monster monster = GameDataManager.monsterManager.getMonster(force
					.getGeneral());
			if (monster == null) {
				tip.setMessage("怪物=null,id=" + force.getGeneral());
				return false;
			}
			if (!defencer.init(null, new FightMonster(monster),
					force.getSoldier(), type)) {
				tip.setMessage("防御方初始化错误:" + defencer.getTip().getMessage());
				return false;
			}
		} else {
			tip.setMessage(player.getTip().getMessage() + ",请求战斗id=" + id);
			return false;
		}
		return true;

	}

	@Override
	public void saveResult() {
		// 回收士兵
		attacker.recoverSoldier();
		// 更新天梯进度
		ladder.updateLadderId(attacker.getPc(), attacker.getWinType(), (byte) 0);
		//更新提示文本
		if(attacker.getWinType()==FightConst.FIGHT_WIN){
			//tip.setMessage("挑战成功");
		}else{
			//tip.setMessage("挑战失败");
		}
		// 保存战斗日志
		FightEvent fightEvent = createFightEvent(attacker);
		if (attacker.getWinType() == FightConst.FIGHT_WIN) {
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				fightEvent.setMemo("Congratulations! You passed the" + ladder.getId() + "Stage");
			}else{
				fightEvent.setMemo("经过不懈的努力，你通关了第" + ladder.getId() + "层");
			}
			
		} else {
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				fightEvent.setMemo("Unfortunately! You failed to pass the" + ladder.getId() + "stage");
			}else{
				fightEvent.setMemo("你未通过第" + ladder.getId() + "层");
			}
			
		}
		super.saveResult(fightEvent, null);
	}

	@Override
	public void getReward() {
		this.addLog(FightUtil.getFightResult(attacker, this.type,
				(byte) ladder.getId(),defencer));
		this.addLog(FightUtil.getFightResult(defencer, this.type,
				(byte) ladder.getId(),attacker));
		QuestUtils.checkFinish(player, QuestUtils.TYPE5, true, type, ladder.getId(),
				attacker.getWinType());
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
