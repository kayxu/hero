package com.joymeng.game.domain.fight.battle;

import com.joymeng.core.fight.FightLog;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.mod.Arena;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.fight.obj.FightPlayer;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.FightRequest;

/**
 * 战斗开始的基本逻辑
 * A id<0 撤防
 * B id>0
 * 	 C arena=要占领的竞技场
 * 	   if(arena.userid==0){
 * 		//空台子---自己先从原来的位置站起来，再直接坐到新台子上
 * 	   }{
 * 		//台子上有人
 * 		if(是自己人){
 *  		//换防，先站起来，再坐下
 * 		}else{
 * 			//进入战斗
 * 			}		
 * 	   }	 
 * 
 * 战斗结束后的基本逻辑
 * 
 * 先记录下初始的双方位置，a1,a2
 * if(a1.win){
 * 	a1占领a2的位置。
 * 		如果a1有位置，a2坐到a1位置
 * 		如果a1没有位置，a2找一个空位置坐下
 * }else{
 * 	 a1没有位置，则回收士兵
 *   a1有位置，则重新设置自己的士兵
 * }
 * 
 *
 * 
 * @author admin
 *
 */
public class FBArena extends FightBattleBase {
//	static FightLog FightLog = FightLogFactory.getFightLog(FBArena.class);

	public FBArena(FightRequest _request, PlayerCharacter _player) {
		super(_request, _player);
	}

	private Arena arena = null;

	@Override
	public boolean initDefencer() {
		// 根据id去查找相应的擂台数据
		int id = request.getId();
		// 攻击方的将领
		PlayerHero attackHero = ((FightPlayer) attacker.getHero()).getHero();
		if (attackHero == null) {
			return false;
		}
		if (id == -1) {// 撤防
			int rankId = player.getData().getArenaId();
			arena = GameDataManager.arenaManager.getArena((short) rankId);
			if (arena == null) {
				tip.setMessage("竞技场撤防错误1，id=" + id + " 当前排名=" + rankId);
				FightLog.info("竞技场错误，id=" + id + " 当前排名=" + rankId);
				return false;
			}
			if(arena.getUserId()!=player.getId()||arena.getHeroId()!=attackHero.getId()){
				tip.setMessage("竞技场撤防错误2，id=" + id + " 当前排名=" + rankId);
				FightLog.info("竞技场错误，id=" + id + " 当前排名=" + rankId);
				return false;
			}
			TipUtil tips = player.getPlayerBuilgingManager().disarm(attackHero, "");
			FightLog.info(tips.getTip().getMessage());
			FightLog.info("[arena] 撤防,playerid="+player.getId()+" heroid="+attackHero.getId()+" arenaid="+rankId);
			return false;
		}
		// 要占领的擂台
		arena = GameDataManager.arenaManager.getArena((short) id);
		if (arena == null) {
			tip.setMessage("竞技场错误，id=" + id);
			return false;
		}
		// 当前竞技场中的玩家id
		int userId = arena.getUserId();
		int rankId = player.getData().getArenaId();
		if (userId == 0) {// 没有人则直接占领
			if (rankId != 0) {
				// 先从自己的位置站起来，无论有没有占领过
				Arena a = GameDataManager.arenaManager.getArena((short) rankId);
//				if (a != null) {
					a.standUp();
//				}
			}
			// 再直接占领
			String str = FightUtil.changeSoldierToStr(attacker.getSoldier());
			arena.sitdown(attackHero, str, player, FightConst.FTYPE_ATTACK);
			String msg = I18nGreeting.getInstance().getMessage(
					"arena.challenge.success", new Object[] { arena.getId() });
			tip.setMessage("");
			QuestUtils.checkFinish(player, QuestUtils.TYPE21, true);
			FightLog.info("[arena] 直接占领,playerid="+player.getId()+" heroid="+attackHero.getId()+" arenaid="+rankId);
			return false;
		} else {
			// 有人，判断是不是自己
			if (userId == player.getData().getUserid()) {// 如果是自己的将领，则替换
				arena.standUp();// 然后从竞技场先站起来
				arena.sitdown(attackHero,
						FightUtil.changeSoldierToStr(attacker.getSoldier()),
						player, FightConst.FTYPE_ATTACK);// 再坐下
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setMessage("successful");
				}else{
					tip.setMessage("成功");
				}
				FightLog.info("[arena] 换防,playerid="+player.getId()+" heroid="+attackHero.getId()+" arenaid="+rankId);
				return false;
			} else {
				// 5名以后的能打5名之前的，5名以内的不能打5名以后的。
				if (id > 5 && player.getData().getArenaId() <= 5
						&& player.getData().getArenaId() > 0) {
					tip.setMessage("攻击方当前排名=" + player.getData().getArenaId()
							+ " 攻击的擂台=" + id);
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setMessage("error");
					}
					return false;
				}
				long time = player.getData().getArenaTime();
				if (System.currentTimeMillis() < time) {
					tip.setMessage("竞技场错误，战斗cd中.下次战斗开始时间="
							+ TimeUtils.getTime(time).format(TimeUtils.FORMAT1));
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setMessage("error");
					}
					return false;
				}
				World gameWorld = World.getInstance();
				// 获得玩家角色
				PlayerCharacter p = gameWorld.getPlayer(userId);
				if (p == null) {
					tip.setMessage("防守方不存在,id=" + userId);
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setMessage("error");
					}
					return false;
				}
				int heroId = arena.getHeroId();
				PlayerHero ph = p.getPlayerHeroManager().getHero(heroId);
				if (ph == null) {
					tip.setMessage("防守方将领不存在:" + heroId);
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setMessage("error");
					}
					return false;
				}
				if (!defencer.init(p, new FightPlayer(p, ph),
						arena.getSoldier(), type)) {
					tip.setMessage("防御方初始化错误:" + defencer.getTip().getMessage());
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setMessage("error");
					}
					return false;
				}
				FightLog.info("[arena] 攻击,playerid="+player.getId()+" heroid="+attackHero.getId()+" arenaid="+rankId+" 被攻击playerid="+p.getId()+" heroid="+heroId);
				// 只有真正进入战斗才设置cd时间
				player.getData().setArenaTime(
						System.currentTimeMillis() + 10 * 60 * 1000);
			}
		}
		QuestUtils.checkFinish(player, QuestUtils.TYPE21, true);
		return true;
	}

	@Override
	public void saveResult() {
		long time1 = System.currentTimeMillis();
		FightEvent fightEvent1 = createFightEvent(attacker);
		FightEvent fightEvent2 = createFightEvent(defencer);
		// 记录攻守双方的擂台位置
		int rankId1 = attacker.getPc().getData().getArenaId();
		int rankId2 = defencer.getPc().getData().getArenaId();
		// 取得攻守双方的将领
		PlayerHero ph1 = ((FightPlayer) attacker.getHero()).getHero();
		PlayerHero ph2 = ((FightPlayer) defencer.getHero()).getHero();
		long time2 = System.currentTimeMillis();
		// FightLog.info("check1 耗时="+(time2-time1));
		if (attacker.getWinType() == FightConst.FIGHT_WIN) {// 战斗胜利
			// 如果有排名,则不能打位置高的
			if (rankId1 > 0 && rankId1 < rankId2) {
				FightLog.info("不互换位    ,rankId1=" + rankId1 + " rankId2="
						+ rankId2);
			} else {
				// 如果当前排名比昨天上升300名
				long time = attacker.getPc().getData().getArenaLastTime();
				if (!TimeUtils.isSameDay(time)) {// 不是同一天，保存当前的排名和时间
					attacker.getPc().getData().setArenaLastId(rankId1);
					attacker.getPc().getData()
							.setArenaLastTime(System.currentTimeMillis());
				}
				// int ri = rankId2 -
				// attacker.getPc().getData().getArenaLastId();
				// if (ri > 300) {
				// attacker.getPc().getData().setArenaLastId(rankId2);
				// attacker.getPc().getData()
				// .setArenaLastTime(System.currentTimeMillis());
				// GameUtils.sendWolrdMessage(new TipMessage(player.getData()
				// .getName() + "排名上升" + ri, ProcotolType.FIGHT_RESP,
				// GameConst.GAME_RESP_SUCCESS, this.type),(byte)1);
				//
				// }

				// 获得防守方当前的擂台
				Arena defArena = GameDataManager.arenaManager
						.getArena((short) rankId2);
				// 防御方站起，攻击方坐到防守方的位置
				if (defArena == null) {
					FightLog.info("当前攻击的防守方的擂台=null,rankId2=" + rankId2);
					return;
				}
				// 站起前,重新设置被攻击方的士兵
				defArena.setSoldier(FightUtil.changeSoldierToStr(defencer
						.getSoldier()));
				// 防守方站起
				defArena.standUp();
				// 攻击方先从原来的站起，再坐到防守方擂台下
				Arena attackArena = GameDataManager.arenaManager
						.getArena((short) rankId1);
				if (attackArena != null) {
					attackArena.standUp();
				}
				defArena.sitdown(ph1,
						FightUtil.changeSoldierToStr(attacker.getSoldier()),
						attacker.getPc(), FightConst.FTYPE_ATTACK);
				// 如果攻击方没有排名，防守方找到一个空闲的位置坐下
				if (rankId1 == 0 & rankId2 != 0) {
					// 防守方找到一个空闲位置，坐下
					int newId[] = GameDataManager.arenaManager.findBlank(
							(short) rankId2, (short) ArenaManager.MAXNUM, 1,
							(byte) 0);
					if (newId.length != 0) {
						Arena a = GameDataManager.arenaManager
								.getArena((short) newId[0]);
						if (a != null) {
							a.sitdown(ph2, FightUtil
									.changeSoldierToStr(defencer.getSoldier()),
									defencer.getPc(), FightConst.FTYPE_DEFENCE);
						}

					}
				} else {
					// if(rankId1!=0){//攻击方也是有排名的
					// //防守方坐到攻击方的位置
					// //获得攻击方的之前的位置，坐下
					attackArena
							.sitdown(ph2, FightUtil.changeSoldierToStr(defencer
									.getSoldier()), defencer.getPc(),
									FightConst.FTYPE_DEFENCE);
				}
				if (rankId2 == 1) {
					String str = "天下无敌，勇夺竞技场第一名！";
					if(I18nGreeting.LANLANGUAGE_TIPS ==1){
						str = " is Invincible,won the NO.1 in Arena";
					}
					World.getInstance().addShout(
							new TipMessage(player.getData().getName()
									+ str,
									ProcotolType.FIGHT_RESP,// tip alter by madi
									GameConst.GAME_RESP_SUCCESS, this.type));
					// GameUtils.sendWolrdMessage(new
					// TipMessage(player.getData()
					// .getName() + "天下无敌，勇夺竞技场第一名！",
					// ProcotolType.FIGHT_RESP,//tip alter by madi
					// GameConst.GAME_RESP_SUCCESS, this.type),(byte)1);
				}
			}
			// 处理连胜次数
			int killNum = player.getData().getArenaKill();
			killNum += 1;
//			logger.info("连胜=" + killNum);
			player.getData().setArenaKill(killNum);
			if (killNum != 0 && (killNum % 10 == 0)) {
				String strs = "无人能挡，豪取10连胜";
				if(I18nGreeting.LANLANGUAGE_TIPS ==1){
					strs = " is unstoppable，wins 10 fights in a row";
				}
				// GameUtils.sendWolrdMessage(new TipMessage(player.getData()
				// .getName() + "连胜到达" + killNum, ProcotolType.FIGHT_RESP,
				// GameConst.GAME_RESP_SUCCESS, this.type),(byte)1);
				World.getInstance().addShout(
						new TipMessage(player.getData().getName()
								+ strs,
								ProcotolType.FIGHT_RESP,
								GameConst.GAME_RESP_SUCCESS, this.type));

			}
			/*
			 * String win = TimeUtils.now().format(TimeUtils.FORMAT1) + ";你挑战了"
			 * + defencer.getHero().getName() + ",你获胜了，排名为" + rankId2; String
			 * lose = TimeUtils.now().format(TimeUtils.FORMAT1) + ";" +
			 * attacker.getHero().getName() + "挑战了你，你战败了，排名为" + rankId1;
			 */
			String win = I18nGreeting.getInstance().getMessage(
					"arena.challenge.success",
					new Object[] { player.getData().getArenaId() });
			String lose = I18nGreeting.getInstance().getMessage(
					"arena.challenge.fail",
					new Object[] { defencer.getPc().getData().getArenaId() });
			GameUtils.sendTip(new TipMessage("", GameConst.GAME_RESP_SUCCESS,
					this.type), attacker.getPc().getUserInfo(),
					GameUtils.FLUTTER);
			GameUtils.sendTip(new TipMessage(lose, GameConst.GAME_RESP_SUCCESS,
					this.type), defencer.getPc().getUserInfo(),
					GameUtils.FLUTTER);
			fightEvent1.setMemo(win);
			fightEvent2.setMemo(lose);
		} else {
			// 攻击方失败
			// 清空连胜次数
			player.getData().setArenaKill(0);
			if (rankId1 == 0) {// 攻击方没有排名,把损失的兵派出去
				attacker.recoverSoldier();
			} else {// 攻击方重新坐下到原来的位置
				Arena a = GameDataManager.arenaManager
						.getArena((short) rankId1);
				if (a == null) {
					FightLog.info("当前攻击的防守方的擂台=null,rankId1=" + rankId1);
					return;
				}
				a.setSoldier(FightUtil.changeSoldierToStr(attacker.getSoldier()));
				// 保存
				// GameDataManager.arenaManager.save(a);
			}
			// 防守方获胜，伤病治愈100%，暂时不处理
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				fightEvent1.setMemo("You challenged " + defencer.getHero().getName() + " but failed.");
				fightEvent2.setMemo(attacker.getHero().getName() + " challenged you, and you won.");
			}else{
				fightEvent1.setMemo("你挑战了" + defencer.getHero().getName() + "，你战败了");
				fightEvent2.setMemo(attacker.getHero().getName() + "挑战了你，你战胜了");
			}
		}
		long time3 = System.currentTimeMillis();
		// FightLog.info("check2 耗时="+(time3-time2));
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
		// 攻击方派兵
		int rankId1 = attacker.getPc().getData().getArenaId();
		if (rankId1 == 0) {
			if (!attacker.dispachSoldier()) {
				tip.setMessage("派兵失败");
				return false;
			}
		}
		return true;
	}
}
