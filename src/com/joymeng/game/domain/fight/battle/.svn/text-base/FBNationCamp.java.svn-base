package com.joymeng.game.domain.fight.battle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.fight.FightLog;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.obj.FightPlayer;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.nation.war.MilitaryCamp;
import com.joymeng.game.domain.nation.war.WarManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.FightRequest;

/**
 * 军营争夺战
 * @author Administrator
 *
 */
public class FBNationCamp extends FightBattleBase {
	private final byte[] lock = new byte[0]; // 锁
	//static Logger logger = LoggerFactory.getLogger(FBNationCamp.class);
	private MilitaryCamp camp= null;
	public FBNationCamp(FightRequest _request, PlayerCharacter _player) {
		super(_request, _player);
	}
	
	@Override
	public  boolean initDefencer(){//初始化防守方
			try {
				String sendTips = "";
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					sendTips = "Battle Error! Please try again.";
				}else{
					sendTips = "战斗错误,请重试";
				}
				FightLog.info("发生战斗 " + attacker.getPc().getId()
						+ "FBNationCamp：" + this.uuid);
				//军营id
				int id = request.getId();
				FightLog.info("初始化camp战斗，参数=" + id);
				//得到军营
				camp = WarManager.getInstance().getMyCamp(id);
				if (camp == null) {
					GameUtils.sendTip(new TipMessage("战斗错误,请重试",
							ProcotolType.FIGHT_RESP, GameConst.GAME_RESP_FAIL,
							type), player.getUserInfo(), GameUtils.FLUTTER);
					tip.setMessage(sendTips);
					FightLog.info("camp 不存在：id=" + id + "|" + this.uuid);
					return false;
				} else if (!camp.checkFight(player, attacker.getHero().getId(),
						FightUtil.changeSoldierToStr(attacker.getSoldier()),
						"", attacker.getHero().getId())) {
					GameUtils.sendTip(new TipMessage("",
							ProcotolType.FIGHT_RESP, GameConst.GAME_RESP_FAIL,
							type), player.getUserInfo(), GameUtils.FLUTTER);
					tip.setMessage("");
					FightLog.info("不能发生战斗发生战斗 FBNationCamp：" + this.uuid);
					return false;
				} else {
					if (!camp.occNationCamp(attacker.getPc())) {
						tip.setMessage("怪物未消灭完不能攻打主城");
						FightLog.info("怪物未消灭完不能攻打主城   FBNationCamp："
								+ this.uuid);
						return false;
					}
					World gameWorld = World.getInstance();
					PlayerCharacter def = World.getInstance().getPlayer(camp.getOccCache().getUserid());// 被攻击的玩家
					if (def == null) {
						tip.setMessage(sendTips);
						FightLog.info("战斗nationCamp错误,驻防用户不存在   FBNationCamp："
								+ this.uuid);
						return false;
					}
					PlayerHero ph = def.getPlayerHeroManager().getHero(
							camp.getHeroId());
					if (ph == null) {
						tip.setMessage(sendTips);
						FightLog.info("战斗将领错误,驻防用户不存在   FBNationCamp："
								+ this.uuid);
						return false;
					}
					if (!defencer.init(def, new FightPlayer(def, ph),
							camp.getSoliderInfo(), type)) {
						tip.setMessage(sendTips);
						FightLog.info("防御方初始化错误   FBNationCamp：" + this.uuid);
						return false;
					}
					FightLog.info("开始战斗   " + attacker.getPc().getId()
							+ "FBNationCamp：" + this.uuid);
					return true;
				}
			} catch (Exception e) {
				FightLog.info(e.getMessage());
				return false;
			}
			
		
	}

	@Override
	public  void saveResult() {
		// 回收士兵
		attacker.recoverSoldier();
		// 保存战斗日志
		FightEvent fightEvent1 = createFightEvent(attacker);
		// 保存战斗日志
		FightEvent fightEvent2 = createFightEvent(defencer);
		String str1 = FightUtil.changeSoldierToStr(attacker.getSoldier());
		String str2 = FightUtil.changeSoldierToStr(defencer.getSoldier());
		if (attacker.getWinType() == FightConst.FIGHT_WIN) {// 战斗胜利
			camp.occupied(attacker.getPc(), attacker.getHero().getId(), str1, str2, attacker.getHero().getId(), fightEvent1, fightEvent2);
		} else {
			camp.occupied(attacker.getPc(), 0, str1, str2, attacker.getHero().getId(), fightEvent1, fightEvent2);
		}
		super.saveResult(fightEvent1, fightEvent2);
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
