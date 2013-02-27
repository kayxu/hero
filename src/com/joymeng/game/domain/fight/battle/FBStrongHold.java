package com.joymeng.game.domain.fight.battle;

import com.joymeng.core.fight.FightLog;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.obj.FightMonster;
import com.joymeng.game.domain.force.Mob;
import com.joymeng.game.domain.monster.Monster;
import com.joymeng.game.domain.nation.war.MilitaryCamp;
import com.joymeng.game.domain.nation.war.StrongHold;
import com.joymeng.game.domain.nation.war.WarManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.net.request.FightRequest;

/**
 * 军营争夺战
 * @author Administrator
 *
 */
public class FBStrongHold extends FightBattleBase {
	private final byte[] lock = new byte[0]; // 锁
	//static Logger logger = LoggerFactory.getLogger(FBStrongHold.class);
	private StrongHold hold= null;
	private MilitaryCamp camp= null;
	public FBStrongHold(FightRequest _request, PlayerCharacter _player) {
		super(_request, _player);
	}
	
	@Override
	public boolean initDefencer() {//初始化防守方
			try {
				FightLog.info("发生战斗 :" + attacker.getPc().getId()
						+ "FBStrongHold：" + this.uuid);
				//军营id
				int campId = request.getId();
				//得到据点
				hold = WarManager.getInstance().getMyStrong(campId);
				//得到军营
				camp = WarManager.getInstance().getMyCamp(campId);
				TipUtil tips = hold.checkFight(attacker.getPc());
				if (hold == null || camp == null || attacker.getPc() == null
						|| attacker.getHero() == null || tips.isResult()) {
					GameUtils.sendTip(new TipMessage(tips.getResultMsg(),
							ProcotolType.FIGHT_RESP, GameConst.GAME_RESP_FAIL,
							type), player.getUserInfo(), GameUtils.FLUTTER);
					tip.setMessage("");
					FightLog.info("战斗 FBStrongHold：不能发生" + this.uuid);
					return false;
				}
				//			hold.initMob(type, attacker.getHero().getLevel(),defencer);
				int heroLevel = 10;
				int level = attacker.getHero().getLevel();
				if (level > 10) {
					heroLevel = level;
				}
				int id = type * 1000 + heroLevel;
				Mob force = GameDataManager.forceManager.getForce(id);
				if (force == null) {
					FightLog.info("mob 不存在,id=" + id + "|" + this.uuid);
					return false;
				}
				Monster monster = GameDataManager.monsterManager
						.getMonster(force.getGeneral());
				if (monster == null) {
					FightLog.info("monster 不存在,id=" + force.getGeneral() + "|"
							+ this.uuid);
					return false;
				}
				if (!defencer.init(null, new FightMonster(monster),
						force.getSoldier(), type)) {
					tip.setMessage("战斗错误,请重试");
					FightLog.info("防御方初始化错误:" + defencer.getTip().getMessage()
							+ "|" + this.uuid);
					return false;
				}
				FightLog.info("开始战斗 :" + attacker.getPc().getId()
						+ "FBStrongHold：" + this.uuid);
				return true;
			} catch (Exception e) {
				
				FightLog.info(e.getMessage());
				return false;
			}
	}

	@Override
	public void saveResult() {
		// 保存战斗日志
		FightEvent fightEvent1 = createFightEvent(attacker);
		String str1 = FightUtil.changeSoldierToStr(attacker.getSoldier());
		if (attacker.getWinType() == FightConst.FIGHT_WIN) {// 战斗胜利
			hold.fightMob((byte)0, attacker.getPc(), str1);
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				fightEvent1.setMemo("You successfully defeated "+MilitaryCamp.getNationName(camp.getNativeId())+"defense troops.");
			}else{
				fightEvent1.setMemo("你成功击败了"+MilitaryCamp.getNationName(camp.getNativeId())+"据点防御部队");
			}
			
		} else {
			hold.fightMob((byte)1, attacker.getPc(), str1);
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				fightEvent1.setMemo("You attacked "+MilitaryCamp.getNationName(camp.getNativeId())+" but failed.");
			}else{
				fightEvent1.setMemo("你攻击了"+MilitaryCamp.getNationName(camp.getNativeId())+"据点失败了");
			}
			
			
		}
		super.saveResult(fightEvent1, null);
	}

	@Override
	public void getReward() {
		this.addLog(FightUtil.getFightResult(attacker, this.type, (byte)0,defencer));
		this.addLog(FightUtil.getFightResult(defencer, this.type, (byte)0,attacker));
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
