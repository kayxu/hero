package com.joymeng.game.domain.fight.battle;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.obj.FightPlayer;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.FightRequest;

public class FBRegion extends FightBattleBase {

	private Nation nation = null;

	static Logger logger = LoggerFactory.getLogger(FBRegion.class);

	public FBRegion(FightRequest _request, PlayerCharacter _player) {
		super(_request, _player);
	}

	@Override
	public boolean initDefencer() {
		int id = request.getId();
		logger.info("初始化县长争夺战");
		nation = NationManager.getInstance().nationMap.get(id);
		if (null == nation) {
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				tip.setMessage("Fighting Error");
			}else{
				tip.setMessage("请求县错误");
			}
			
			logger.info("请求县错误");
			return false;
		}
		QuestUtils.checkFinish(player, QuestUtils.TYPE43, true);
		if (!nation.checkEnterBattle(player)) {// 判断攻击方是否可进入游戏
			// tip.setMessage("当前称谓不允许进入争夺战");
			// logger.info("当前称谓不允许进入争夺战");
			return false;
		}
		World gameWorld = World.getInstance();
		int userId = nation.getOccupyUser();
		if (userId == 0) {// 如果当前县没有被人占领

			// 当前玩家丢弃以前县的地理位置和称谓并回收士兵
			discardNationAndTitle();
			
			// 攻击方派兵驻防
			attacker.dispachSoldier();

			// 改变攻击将领为驻防状态
			PlayerHero ph = ((FightPlayer) attacker.getHero()).getHero();
			ph.setStatus(GameConst.HEROSTATUS_ZHUFANG_COUNTY);
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				ph.setMemo("garrison in " + Nation.getNationName(nation.getId()));
			}else{
				ph.setMemo("驻防" + Nation.getNationName(nation.getId()));
			}
			ph.setSoldier(FightUtil.changeSoldierToStr(attacker.getSoldier()));
			
			// 直接进入县城
			nation.occupyNation(player, attacker.getHero().getId(),
					FightUtil.changeSoldierToStr(attacker.getSoldier()));// 占领

			// 换玩家地理位置
			NationManager.getInstance().MigrationCounty(player, nation.getId());
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				tip.setMessage("You become the Townleader of " + nation.getName() );
			}else{
				tip.setMessage("已成功进入" + nation.getName() + "，成为县长");
			}
			
			logger.info("已成功进入" + nation.getName() + "，成为县长");
			return false;

		} else {// 如果当前县已有玩家占领

			// 但玩家已撤回将领和士兵
			if ((0 == nation.getHeroId() && null == nation.getSoldierInfo())
					|| (0 == nation.getHeroId() && "".equals(nation
							.getSoldierInfo()))) {
				
				// 回收驻防以前县城里的士兵和将领
				recoverHeroAndSoldier();

				// 攻击方派兵驻防
				attacker.dispachSoldier();

				// 改变攻击将领为驻防状态
				PlayerHero ph = ((FightPlayer) attacker.getHero()).getHero();
				ph.setStatus(GameConst.HEROSTATUS_ZHUFANG_COUNTY);
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					ph.setMemo("garrison in " + Nation.getNationName(nation.getId()));
				}else{
					ph.setMemo("驻防" + Nation.getNationName(nation.getId()));
				}
				ph.setSoldier(FightUtil.changeSoldierToStr(attacker.getSoldier()));
				// 攻击方是否是县长
				boolean isCountyHead = false;

				// 防御方去攻击方以前县城
				HashMap<Integer, Nation> nationMap = NationManager
						.getInstance().nationMap;
				for (Nation tempNation : nationMap.values()) {
					if (tempNation.getOccupyUser() == player.getData()
							.getUserid()) {// 如果当前玩家占领过其他县,即是县长
						tempNation.occupyNation(World.getInstance().getPlayer(nation.getOccupyUser()), 0, null);
						isCountyHead = true;
						break;
					}
				}
				
				if (!isCountyHead) {// 如果攻击方不是县长，防御方回攻击方县城做平民

					// 攻击方所在县城
					Nation toNation = NationManager.getInstance().nationMap
							.get(player.getData().getNativeId());

					PlayerCharacter pc = gameWorld.getPlayer(userId);// 被攻击的玩家

					// 丢弃防御方县长称谓
					RoleData roleData = pc.getData();
					logger.info("县长争夺战FBRegion:"+pc.getId()+"|变成："+GameConst.TITLE_CIVILIAN);
					roleData.setTitle(GameConst.TITLE_CIVILIAN);
					//roleData.setNativeId(toNation.getId());
					pc.checkTraining();
//					DBManager.getInstance().getWorldDAO().saveRole(roleData);
				}
				
				// 直接进入县城
				nation.occupyNation(player, attacker.getHero().getId(),
						FightUtil.changeSoldierToStr(attacker.getSoldier()));// 占领
				
				//-----------new alter
				int playerNativeId = player.getData().getNativeId();
				// 转移地理位置
				NationManager.getInstance().MigrationCounty(player, nation.getId());
				NationManager.getInstance().MigrationCounty(gameWorld.getPlayer(userId),playerNativeId);
				
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setMessage("garrisoned Success");
				}else{
					tip.setMessage("驻防成功");
				}
				
				logger.info("占领无人防守县城成功");
				return false;
			}

			PlayerCharacter user = gameWorld.getPlayer(userId);// 被攻击的玩家
			if (user == null) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setMessage("Fighting Error");
				}else{
					tip.setMessage("县长战斗错误,uid=" + userId);
				}
				
				return false;
			}
			PlayerHero ph = user.getPlayerHeroManager().getHero(
					nation.getHeroId());
			if (ph == null) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setMessage("Fighting Error");
				}else{
					tip.setMessage("战斗错误,uid=" + userId + " heroId="
							+ nation.getHeroId());
				}
				
				return false;
			}
			if (!defencer.init(user, new FightPlayer(user, ph),
					nation.getSoldierInfo(), type)) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setMessage("Fighting Error");
				}else{
					tip.setMessage("防御方初始化错误:" + defencer.getTip().getMessage());
				}
				
				return false;
			}
			if (userId == player.getData().getUserid()) {// 如果被攻击的玩家是当前玩家自己则阻止
				// to add 驻防
				if (defencer.getHero().getId() == 0) {// 且当前县城处于撤防状态，则当前玩家驻防
					// 攻击方派兵驻防
					attacker.dispachSoldier();

					// 改变攻击将领为驻防状态
					ph = ((FightPlayer) attacker.getHero()).getHero();
					ph.setStatus(GameConst.HEROSTATUS_ZHUFANG_COUNTY);
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						ph.setMemo("garrison in " + Nation.getNationName(nation.getId()));
					}else{
						ph.setMemo("驻防" + Nation.getNationName(nation.getId()));
					}
					
					ph.setSoldier(FightUtil.changeSoldierToStr(attacker.getSoldier()));
					// 直接进入县城
					nation.occupyNation(player, attacker.getHero().getId(),
							FightUtil.changeSoldierToStr(attacker.getSoldier()));// 占领
					
					logger.info("驻防" + nation.getName() + "成功");
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setMessage("garrison in " + nation.getName() + ",Successful ");
					}else{
						tip.setMessage("驻防" + nation.getName() + "成功");
					}
				} else {
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setMessage("Attack failed. Can't attack your own Town.");
					}else{
						tip.setMessage("不能对自己的县城发动进攻");
					}
					
					logger.info("不能对自己的县城发动进攻");
				}
				return false;
			}
		}

		return true;
	}

	@Override
	public void saveResult() {
		// 保存战斗日志
		FightEvent fightEvent1 = createFightEvent(attacker);
		// 保存战斗日志
		FightEvent fightEvent2 = createFightEvent(defencer);
		String attackSoldierInfo = FightUtil.changeSoldierToStr(attacker
				.getSoldier());
		String defenceSoldierInfo = FightUtil.changeSoldierToStr(defencer
				.getSoldier());
		PlayerCharacter defencePlayer = World.getInstance().getPlayer(
				nation.getOccupyUser());
		if (attacker.getWinType() == FightConst.FIGHT_WIN) {// 战斗胜利
			//tip.setMessage("进入战斗");
			deal(fightEvent1, fightEvent2, attackSoldierInfo,
					defenceSoldierInfo, defencePlayer);

		} else {// 战斗失败
			//tip.setMessage("进入战斗");
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				fightEvent1.setMemo("You attacked " + nation.getName() + " but failed.");
				fightEvent2.setMemo("You defended" + player.getData().getName() + "'s attack");
			}else{
				fightEvent1.setMemo("你进攻" + nation.getName() + "时失败了");
				fightEvent2.setMemo("你抵御了" + player.getData().getName() + "的进攻");
			}
			attacker.recoverSoldier();

			// 防御方任然驻守原来县城，攻击方不做改变
			nation.occupyNation(defencer.getPc(), defencer.getHero().getId(),
					defenceSoldierInfo);// 占领新县城

			// attacker.getPc().getFightEventManager().addFightEvent(fightEvent1);
			// defencer.getPc().getFightEventManager().addFightEvent(fightEvent2);
			logger.info("攻击" + nation.getName() + "失败");
		}
		super.saveResult(fightEvent1, fightEvent2);
	}

	@Override
	public boolean sendSoldier() {
		// 攻击方派兵
		if (!attacker.dispachSoldier()) {
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				tip.setMessage("Fighting Error");
			}else{
				tip.setMessage("派兵失败");
			}
			return false;
		}
		return true;
	}

	// 当前玩家丢弃以前县的地理位置和称谓(不丢弃称谓)
	public void discardNationAndTitle() {
		HashMap<Integer, Nation> nationMap = NationManager.getInstance().nationMap;
		for (Nation tempNation : nationMap.values()) {
			if (tempNation.getOccupyUser() == player.getData().getUserid()) {// 如果当前玩家占领过其他县

				// 丢弃原来县城
				tempNation.discardNation(player);
				break;
			}
		}
	}

	/**
	 * 恢复将领和士兵信息
	 */
	public void recoverHeroAndSoldier() {
		HashMap<Integer, Nation> nationMap = NationManager.getInstance().nationMap;
		for (Nation tempNation : nationMap.values()) {
			if (tempNation.getOccupyUser() == player.getData().getUserid()) {// 如果当前玩家占领过其他县
				// 回收已占领县城的士兵
				player.getPlayerBuilgingManager().recoverSoldier(
						tempNation.getSoldierInfo());

				// 改变以前县城的将领状态为空闲
				if(null !=player.getPlayerHeroManager().getHero(tempNation.getHeroId())){
					player.getPlayerHeroManager().getHero(tempNation.getHeroId())
					.setStatus((byte) 0);
					player.getPlayerHeroManager().getHero(tempNation.getHeroId()).setMemo("");
				}
				tempNation.setHeroId(0);
				tempNation.setSoldierInfo(null);
				DBManager.getInstance().getWorldDAO().getNationDAO()
						.saveNation(tempNation);
				break;
			}
		}
	}

	@Override
	public void getReward() {
		this.addLog(FightUtil.getFightResult(attacker, this.type, (byte) 0,defencer));
		this.addLog(FightUtil.getFightResult(defencer, this.type, (byte) 0,attacker));
	}

	/**
	 * 战斗成功相关处理
	 */
	private void deal(FightEvent fightEvent1, FightEvent fightEvent2,
			String attackSoldierInfo, String defenceSoldierInfo,
			PlayerCharacter defencePlayer) {
		// 回收驻防以前县城里的士兵和将领
		recoverHeroAndSoldier();

		// 攻击方回收战争之后剩余的士兵
		attacker.recoverSoldier();
		// 攻击方派兵驻防
		attacker.dispachSoldier();

		// 改变将领状态为驻防
		PlayerHero ph = ((FightPlayer) attacker.getHero()).getHero();
		ph.setStatus(GameConst.HEROSTATUS_ZHUFANG_COUNTY);
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			ph.setMemo("garrison in " + Nation.getNationName(nation.getId()));
		}else{
			ph.setMemo("驻防" + Nation.getNationName(nation.getId()));
		}
		
		ph.setSoldier(attackSoldierInfo);

		// 防御方回收战争剩余的士兵
		defencer.recoverSoldier();
		// 防御方派兵驻防
		defencer.dispachSoldier();

		// 攻击方是否是县长
		boolean isCountyHead = false;

		// 防御方去攻击方以前县城
		HashMap<Integer, Nation> nationMap = NationManager.getInstance().nationMap;
		for (Nation tempNation : nationMap.values()) {
			if (tempNation.getOccupyUser() == player.getData().getUserid()) {// 如果当前玩家占领过其他县,即是县长
				tempNation.occupyNation(defencer.getPc(), defencer.getHero()
						.getId(), defenceSoldierInfo);
				isCountyHead = true;
				break;
			}
		}

		if (!isCountyHead) {// 如果攻击方不是县长，防御方回攻击方县城做平民

			// 攻击方所在县城
			 Nation toNation = NationManager.getInstance().nationMap.get(player
					.getData().getNativeId());
			 
			// 丢弃防御方县长称谓
			RoleData roleData = defencer.getPc().getData();
			roleData.setTitle((byte) 0);
			logger.info("县长争夺战FBRegion deal:"+roleData.getUserid()+"|变成："+GameConst.TITLE_CIVILIAN);
			defencer.getPc().checkTraining();
			//roleData.setNativeId(toNation.getId());
//			DBManager.getInstance().getWorldDAO().saveRole(roleData);
		}

		nation.occupyNation(player, attacker.getHero().getId(),
				attackSoldierInfo);// 攻击方占领新县城
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			fightEvent1.setMemo("You defeated " + defencePlayer.getData().getName() + " and became "
					+ nation.getName() + "'s Townleader.");
			fightEvent2.setMemo("You are defeated by " + player.getData().getName() + " and lose the official position of "
					+ nation.getName() + "'s Townleader.");
		}else{
			fightEvent1.setMemo("你击败" + defencePlayer.getData().getName() + "，成为"
					+ nation.getName() + "县的县长");
			fightEvent2.setMemo("你被" + player.getData().getName() + "击败" + "，失去了"
					+ nation.getName() + "县县长的官位");
		}
		

		// attacker.getPc().getFightEventManager().addFightEvent(fightEvent1);
		// defencer.getPc().getFightEventManager().addFightEvent(fightEvent2);

		// 改变将领状态为驻防
		/*PlayerHero attackerPh = ((FightPlayer) attacker.getHero()).getHero();
		attackerPh.setStatus(GameConst.HEROSTATUS_ZHUFANG_COUNTY);
		attackerPh.setMemo(Nation.getNationName(nation.getId()));
		attackerPh.setSoldier(attackSoldierInfo);*/ //alter 2012-10-17

		
		if (isCountyHead) {// 如果攻击方是县长，则防御方将领由于战败驻防攻击方以前的县城
			PlayerHero defencerPh = ((FightPlayer) defencer.getHero())
					.getHero();
			defencerPh.setStatus(GameConst.HEROSTATUS_ZHUFANG_COUNTY);
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				defencerPh.setMemo("garrison in " +Nation.getNationName(nation.getId()));
			}else{
				defencerPh.setMemo("驻防" +Nation.getNationName(nation.getId()));
			}
			
			defencerPh.setSoldier(defenceSoldierInfo);
		} else {// 如果攻击方不是县长，则防御方将领由于战败回收将领和士兵
			PlayerHero defencerPh = ((FightPlayer) defencer.getHero())
					.getHero();
			defencerPh.setStatus(GameConst.HEROSTATUS_IDEL);
			defencerPh.setMemo("");
			defencerPh.setSoldier("");
			defencer.recoverSoldier();

		}

		int playerNativeId = player.getData().getNativeId();
		// 转移地理位置
		NationManager.getInstance().MigrationCounty(player, nation.getId());
		NationManager.getInstance().MigrationCounty(defencer.getPc(),playerNativeId);
		logger.info("已成功进入县" + nation.getName() + "，成为县长");
	}

}
