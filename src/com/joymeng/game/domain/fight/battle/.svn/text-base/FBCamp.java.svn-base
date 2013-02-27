package com.joymeng.game.domain.fight.battle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.mod.Campaign;
import com.joymeng.game.domain.fight.obj.FightMonster;
import com.joymeng.game.domain.force.Mob;
import com.joymeng.game.domain.monster.Monster;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.net.request.FightRequest;

public class FBCamp extends FightBattleBase {
	static Logger logger = LoggerFactory.getLogger(FBCamp.class);
	private Campaign camp = null;

	public FBCamp(FightRequest _request, PlayerCharacter _player) {
		super(_request, _player);
	}
	@Override
	public boolean init(){
		if(super.init()){
			QuestUtils.checkFinish(player, QuestUtils.TYPE4, true,type, camp.getId());
			return true;
		}
		return false;
	}
	byte charge=0;
	@Override
	public boolean initDefencer() {
		int id = request.getId();
		charge = request.getCharge();// 是否付费 0免费1付费
		logger.info("初始化战役场战斗，参数="+id+" charge="+charge);
		camp = GameDataManager.campaignManager.getCampaign(id);
		if (camp == null) {
			tip.setMessage("战役=null,id=" + id);
			return false;
		}
		// 判断是否可以进入
		if (camp.checkEnter(player, charge)) {
			Mob force = GameDataManager.forceManager
					.getForce(camp.getForceId());
			if (force == null) {
				tip.setMessage("部队=null,id=" + camp.getForceId());
				return false;
			}
			Monster monster = GameDataManager.monsterManager.getMonster(force
					.getGeneral());
			if (monster == null) {
				tip.setMessage("怪物=null,id=" + force.getGeneral());
				return false;
			}
			logger.info("部队号=" + camp.getForceId() + " 怪物号="
					+ force.getGeneral());
			if (!defencer.init( null, new FightMonster(monster), force.getSoldier(), type)) {
				tip.setMessage("防御方初始化错误:"+defencer.getTip().getMessage());
				return false;
			}
		} else {
			// 不能进入0. 
			tip.setMessage("无法进入该战役，当天进入次数已经用完");
			return false;
		}
//		camp.enter(player, charge);
		return true;
	}

	@Override
	public void saveResult() {
		// 回收士兵
		attacker.recoverSoldier();
		if(attacker.getWinType()==FightConst.FIGHT_WIN){
			// 获胜方更新战役进度
			camp.updateCampId(attacker.getPc());
			//获胜则减少次数
			camp.enter(player, charge);
		}
		//由于在接受任务后，战斗符合条件判断则不消耗次数，所以任务检测要放到这里
		QuestUtils.checkFinish(player, QuestUtils.TYPE5, true,type, camp.getId(),attacker.getWinType());
		// 保存战斗日志
		FightEvent fightEvent = createFightEvent(attacker);
		if (attacker.getWinType() != FightConst.FIGHT_WIN) {
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				fightEvent.setMemo("Your hero " + attacker.getHero().getName() + " attacked  "
						+ camp.getName() + " but failed.");
			}else{
				fightEvent.setMemo("你的武将" + attacker.getHero().getName() + "在进攻"
						+ camp.getName() + "时失败了");
			}
			
		} else {
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				fightEvent.setMemo("Your hero " + attacker.getHero().getName() + " attacked "
						+ camp.getName() + " and successfully");
			}else{
				fightEvent.setMemo("你的武将" + attacker.getHero().getName() + "在进攻"
						+ camp.getName() + "时取得了胜利");
			}
			
		}
		super.saveResult(fightEvent, null);
	}

	@Override
	public void getReward() {
		this.addLog(FightUtil.getFightResult(attacker, this.type, (byte)camp.getId(),defencer));
		this.addLog(FightUtil.getFightResult(defencer, this.type, (byte)camp.getId(),attacker));
	}
	@Override
	public boolean sendSoldier(){
		// 攻击方派兵
		if (!attacker.dispachSoldier()) {
			tip.setMessage("派兵失败");
			return false;
		}
		return true;
	}
}
