package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.event.GameEvent;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.award.ArenaAward;
import com.joymeng.game.domain.flag.Room;
import com.joymeng.game.domain.hero.Hero;
import com.joymeng.game.domain.hero.HeroManager;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.skill.Skill;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.HeroRequest;
import com.joymeng.game.net.response.HeroResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyNormalMessage.UserInfo;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 将领
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
@JoyMessageService
public class HeroService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(HeroService.class);

	private HeroService() {
		System.out.println("init"+HeroService.class.getName());
	}

	@JoyMessageHandler
	public JoyProtocol handleHero(HeroRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
//			GameUtils.sendTip(new TipMessage("玩家不存在,id="
//					+ request.getUserInfo().getUid(), ProcotolType.HERO_RESP,
//					GameConst.GAME_RESP_FAIL, (byte) 0), player.getUserInfo());
			logger.info("玩家不存在，id="+request.getUserInfo().getUid());
			return null;
		}
		player.notifyEvent(new GameEvent(GameEvent.EVT_PLAYER_LEVEL_UP));
		UserInfo info = request.getUserInfo();
		byte type = request.getType();
		int id = 0;
		int skillId = 0;
		PlayerHero playerHero = null;
		HeroResp resp = new HeroResp();// 将领消息
		resp.setUserInfo(info);
		resp.setType(type);
		logger.info("handleHero from " + request.getUserInfo().getUid()
				+ " type=" + type);
		RespModuleSet rms = new RespModuleSet(ProcotolType.HERO_RESP);// 模块消息
		switch (type) {
		case ProcotolType.HERO_REFRESH:// 刷新将领
			id = request.getId();// 酒馆刷新类别byte（0自动刷新3个；1手动刷新3个；2自动刷新1个；3手动刷新1个）
			GameDataManager.heroManager.refresh(id, player);
			break;
		case ProcotolType.HERO_LIST:// 将领列表
			PlayerHero arr[] = player.getPlayerHeroManager().getHeroArray();
			for (int i = 0; i < arr.length; i++) {
				arr[i].stopTrain();//判断训练是否结束
				rms.addModule(arr[i]);
			}
			AndroidMessageSender.sendMessage(rms, player);
			break;
		case ProcotolType.HERO_GET:// 获得将领
			if(!player.checkHeroNumlimit()){
				return null;
			}
			id = request.getId();
			// 获得固化将领
			Hero hero = GameDataManager.heroManager.getById(id);
			if (hero == null) {// 不存在该固化将领数据
				break;
			}
			String str = player.getTavernHero();
			logger.info("召唤将领，str=" + str + " 召唤id=" + id + " playerId="+player.getId());
			int result = player.saveResources(GameConfig.GAME_MONEY,
					-hero.getMoney());
			if (result < 0) {
				// logger.info("招募失败，当前金钱为=" + player.getData().getGameMoney());
				if(I18nGreeting.LANLANGUAGE_TIPS ==1 ){
					GameUtils.sendTip(
							new TipMessage("Recruit hero failed. Gold:"
									+ player.getData().getGameMoney() + ", Need:"
									+ hero.getMoney(), ProcotolType.HERO_RESP,
									GameConst.GAME_RESP_FAIL, type), player
									.getUserInfo(),GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(
							new TipMessage("召唤将领失败，当前金钱为"
									+ player.getData().getGameMoney() + ",需要金钱为"
									+ hero.getMoney(), ProcotolType.HERO_RESP,
									GameConst.GAME_RESP_FAIL, type), player
									.getUserInfo(),GameUtils.FLUTTER);
				}
				
				break;
			}
			String ids[] = str.split(";");
			if (ids == null) {// 没有刷新就召唤将领
				if(I18nGreeting.LANLANGUAGE_TIPS ==1 ){
					GameUtils.sendTip(
							new TipMessage("Recruit hero failed. No refresh!",
									ProcotolType.HERO_RESP,
									GameConst.GAME_RESP_FAIL, type), player
									.getUserInfo(),GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(
							new TipMessage("召唤将领失败，没有刷新就召唤将",
									ProcotolType.HERO_RESP,
									GameConst.GAME_RESP_FAIL, type), player
									.getUserInfo(),GameUtils.FLUTTER);
				}
				
				break;
			} else {
				boolean b = false;
				for (int i = 0; i < ids.length; i++) {
					if (Integer.parseInt(ids[i]) == id) {
						b = true;
						break;
					}
				}
				if (!b) {// 招募的不是刷新出来的id
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						GameUtils.sendTip(new TipMessage("Recruit hero failed. Error hero refresh!",
								ProcotolType.HERO_RESP, GameConst.GAME_RESP_FAIL,
								type), player.getUserInfo(),GameUtils.FLUTTER);
					}else{
						GameUtils.sendTip(new TipMessage("召唤将领失败，招募的不是刷新出来的",
								ProcotolType.HERO_RESP, GameConst.GAME_RESP_FAIL,
								type), player.getUserInfo(),GameUtils.FLUTTER);
					}
					
					break;
				}
			}
			if(hero.isSpecial()){
				//发送消息
				String msg = I18nGreeting.getInstance().getMessage("recruit.hero",
						new Object[]{player.getData().getName(),hero.getName()});
				logger.info("recruit.hero===用户："+player.getId()+"|msg"+msg+"|length:"+msg.length());
				NoticeManager.getInstance().sendSystemWorldMessage(msg);
			}
			playerHero = player.getPlayerHeroManager().addHero(hero);
			QuestUtils.checkFinish(player, QuestUtils.TYPE31, true);
			QuestUtils.checkFinish(player, QuestUtils.TYPE32, true);
			// 移除掉已经刷新过的将领
			player.setTavernHero(StringUtils.removeFromStr(
					player.getTavernHero(), id, ";", "0"));
			logger.info("herolist2=" + player.getTavernHero());
			rms.addModule(playerHero);
			rms.addModule(player.getData());
			// playerHero.print();
//			World.getInstance().savePlayer(player);
			AndroidMessageSender.sendMessage(rms, player);
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(new TipMessage("Recruit hero successfully.",
						ProcotolType.HERO_RESP, GameConst.GAME_RESP_FAIL,
						type), player.getUserInfo(),GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("召唤将领成功", ProcotolType.HERO_RESP,
						GameConst.GAME_RESP_SUCCESS, type), player.getUserInfo(),GameUtils.FLUTTER);
			}
			
			break;
		case ProcotolType.HERO_DEL:// 删除将领
			id = request.getId();
			playerHero = this.getHero(player, id, type);
			if (playerHero == null) {
				break;
			}
			int cal=(HeroManager.getInstance().getTotalExp(playerHero.getLevel())+playerHero.getExp())*70/100;
			if (player.getPlayerHeroManager().removeHero(playerHero)) {
				//武将获得的总的经验数值
				String fireherotitle = I18nGreeting.getInstance().getMessage("fire.hero.title",new Object[] {});
				String firehero = I18nGreeting.getInstance().getMessage("fire.hero.Content",new Object[] {});
				ArenaAward ae = Room.arenaAward(player, cal,0,0,fireherotitle,firehero);
				if(ae != null){
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						playerHero.setTip(new TipMessage("Fired successful",
								ProcotolType.HERO_RESP, GameConst.GAME_RESP_SUCCESS,
								type));
					}else{
						playerHero.setTip(new TipMessage(" 删除将领成功,id=" + skillId,
								ProcotolType.HERO_RESP, GameConst.GAME_RESP_SUCCESS,
								type));
					}
					
					resp.setIsEject((byte)1);
				}else{
					resp.setIsEject((byte)0);
				}
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				return resp;
			}
			break;
		case ProcotolType.HERO_EQUIP:// 装备
			break;
		case ProcotolType.HERO_UNEQUIP:// 卸下装备
			break;
		case ProcotolType.HERO_ADDSKILL:// 学习技能
			id = request.getId();
			playerHero = this.getHero(player, id, type);
			if (playerHero == null) {
				break;
			}
			// 使用的道具书
			int itemId = request.getItemId();
			// 根据技能书获得技能id
			skillId = player.getSkillIdFromBook(itemId);
			if (skillId <= 0) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(
							new TipMessage("Skill doesn't exist",
									ProcotolType.HERO_RESP,
									GameConst.GAME_RESP_FAIL, type), player
									.getUserInfo(),GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(
							new TipMessage("技能不存在,id=" + skillId,
									ProcotolType.HERO_RESP,
									GameConst.GAME_RESP_FAIL, type), player
									.getUserInfo(),GameUtils.FLUTTER);
				}
				
				break;
			}
			if(I18nGreeting.LANLANGUAGE_TIPS ==1 ){
				playerHero.setTip(new TipMessage("Learn Successfully",//"学习技能成功,id=" + skillId,
						ProcotolType.HERO_RESP, GameConst.GAME_RESP_SUCCESS, type));
			}else{
				playerHero.setTip(new TipMessage("学习技能成功",//"学习技能成功,id=" + skillId,
						ProcotolType.HERO_RESP, GameConst.GAME_RESP_SUCCESS, type));
			}
			
			if (playerHero.addSkill(skillId)) {// 加入技能失败
				// 从背包中移除该书籍
				Cell cell = player.getPlayerStorageAgent().dellCell(itemId, 1);
				
				if(null != cell){
					Props props = (Props) cell.getItem();
					logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
							+ " propsName=" + props.getPrototype().getName() + " useNumber=1" + " uid=" + player.getId() 
							+ " uname=" + player.getData().getName());
					
					GameLog.logSystemEvent(LogEvent.USE_PROPS, String.valueOf(props.getPrototype().getId()),props.getPrototype().getName(),String.valueOf(1)
							,String.valueOf(player.getId()));
				}
				
				
				
				rms.addModule(playerHero);
				rms.addModule(cell);
				AndroidMessageSender.sendMessage(rms, player);
			}
			logger.info("hero\n"+playerHero.print());
			QuestUtils.checkFinish(player, QuestUtils.TYPE15, true);
//			World.getInstance().savePlayer(player);
			break;
		case ProcotolType.HERO_DELSKILL:// 删除技能
			/*GameUtils.sendTip(new TipMessage("xxxxxxxxxxxxxx",
					ProcotolType.HERO_RESP, GameConst.GAME_RESP_FAIL,
					type), player.getUserInfo(),GameUtils.FLUTTER);*/
			id = request.getId();
			skillId = request.getSkillId();
			byte moneyType=request.getMoneyType();
			playerHero = this.getHero(player, id, type);
			if (playerHero == null) {
				break;
			}
			Skill skill = GameDataManager.skillManager.getSKill(skillId);
			if (skill == null) {
				GameUtils.sendTip(
						new TipMessage("技能不存在,id=" + skillId,
								ProcotolType.HERO_RESP,
								GameConst.GAME_RESP_FAIL, type), player
								.getUserInfo(),GameUtils.FLUTTER);
				break;
			}
			
			String message = I18nGreeting.getInstance().getMessage("skill.remove.success", null);
			playerHero.setTip(new TipMessage(message,
					ProcotolType.HERO_RESP, GameConst.GAME_RESP_SUCCESS, type));
			if(moneyType>0){
				 result = player.saveResources(GameConfig.JOY_MONEY,
						-skill.getLevel()*2);
				if (result < 0) {
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						GameUtils.sendTip(
								new TipMessage("Delete skill failed. You have Golds."
										+ player.getData().getGameMoney() + ",Need Golds:"
										+ 1, ProcotolType.HERO_RESP,
										GameConst.GAME_RESP_FAIL, type), player
										.getUserInfo(),GameUtils.FLUTTER);
					}else{
						GameUtils.sendTip(
								new TipMessage("删除技能失败，当前金钱为"
										+ player.getData().getGameMoney() + ",需要金钱为"
										+ 1, ProcotolType.HERO_RESP,
										GameConst.GAME_RESP_FAIL, type), player
										.getUserInfo(),GameUtils.FLUTTER);
					}
					
					break;
				}
			}
			if (playerHero.removeSkill(skillId)) {
				player.getPlayerHeroManager().save(id);
				// 50%移除成功,将该技能还原为技能书
				int random = MathUtils.random(100);
				if (random < 50||moneyType>0) {
				
					Props prop = player.getPlayerStorageAgent()
							.getBookFromAllSkill(skillId);
					if (prop == null) {
						/*playerHero.setTip(new TipMessage("遗忘技能=" + skillId
								+ ",该技能不存在技能书", ProcotolType.HERO_RESP,
								GameConst.GAME_RESP_FAIL));*/
						String msg = I18nGreeting.getInstance().getMessage("skill.get.fail", null);
						playerHero.setTip(new TipMessage(msg, ProcotolType.HERO_RESP,
								GameConst.GAME_RESP_FAIL));
					}else{
						/*playerHero.setTip(new TipMessage("遗忘技能=" + skillId
								+ ",获得新技能道具="+prop.getName(), ProcotolType.HERO_RESP,
								GameConst.GAME_RESP_SUCCESS));*/
						String msg = I18nGreeting.getInstance().getMessage("skill.get", new Object[]{prop.getName()});
						playerHero.setTip(new TipMessage(msg, ProcotolType.HERO_RESP,
								GameConst.GAME_RESP_SUCCESS));
					}
				
					// 重新加入到背包中
					Cell cell2 = player.getPlayerStorageAgent()
							.addPropsCell(prop.getId(), 1);
					rms.addModule(cell2);
				}
				rms.addModule(playerHero);
				rms.addModule(player.getData());
				AndroidMessageSender.sendMessage(rms, player);
				QuestUtils.checkFinish(player, QuestUtils.TYPE16, true);
			}
			break;
		case ProcotolType.HERO_SOLDIER:// 带兵数，test
			break;
		case ProcotolType.HERO_LEVELUP:// 升级，test
			break;
		case ProcotolType.HERO_TRAINSTART:// 开始训练
			id = request.getId();
			byte t = request.getTrainType();
			byte index = request.getTrainIndex();
			playerHero = this.getHero(player, id, type);
			if (playerHero == null) {
				break;
			}
			playerHero.setTip(new TipMessage("", ProcotolType.HERO_RESP,
					GameConst.GAME_RESP_SUCCESS, type));
			if (playerHero.startTrain(t, index)) {
				sendModuleMessage(player, playerHero);
			}
			break;
		case ProcotolType.HERO_TRAINEND:// 结束训练
			id = request.getId();
			playerHero = this.getHero(player, id, type);
			if (playerHero == null) {
				break;
			}
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				playerHero.setTip(new TipMessage("training over", ProcotolType.HERO_RESP,
						GameConst.GAME_RESP_SUCCESS, type));
			}else{
				playerHero.setTip(new TipMessage("训练结束", ProcotolType.HERO_RESP,
						GameConst.GAME_RESP_SUCCESS, type));
			}
			if (playerHero.stopTrain()) {
				sendModuleMessage(player, playerHero);
			}
			break;
		case ProcotolType.HERO_EXPANDSKILL:// 扩扎技能格
			id = request.getId();
			moneyType = request.getMoneyType();
			playerHero = this.getHero(player, id, type);
			if (playerHero == null) {
				break;
			}
			String msg = I18nGreeting.getInstance().getMessage("skill.expand.success", null);
			playerHero.setTip(new TipMessage(msg, ProcotolType.HERO_RESP,
					GameConst.GAME_RESP_SUCCESS, type));
			if (playerHero.expandSkill(moneyType)) {
				sendModuleMessage(player, playerHero);
			}
			break;
		case ProcotolType.HERO_SPEEDUP:// 加速训练
			id = request.getId();
			playerHero = this.getHero(player, id, type);
			if (playerHero == null) {
				break;
			}
			playerHero.setTip(new TipMessage("加速训练，加速成功",
					ProcotolType.HERO_RESP, GameConst.GAME_RESP_SUCCESS, type));
			if (playerHero.speedUp()) {
				sendModuleMessage(player, playerHero);
			}
			break;
		}
		if(playerHero!=null){
			if(type!=ProcotolType.HERO_REFRESH){
				//GameUtils.sendTip(playerHero.getTip(), info,GameUtils.FLUTTER);
				GameUtils.sendTip(playerHero.getTip(), player.getUserInfo(),GameUtils.FLUTTER);
//				logger.info(playerHero.getTip().getMessage());
			}
			
		}
		
		return null;
	}

	

	/**
	 * 获得将领
	 * 
	 * @param player
	 * @param id
	 * @return
	 */
	public PlayerHero getHero(PlayerCharacter player, int id, byte type) {
		PlayerHero playerHero = player.getPlayerHeroManager().getHero(id);
		if (playerHero == null) {
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(new TipMessage("Hero doesn't exist.",
						ProcotolType.HERO_RESP, GameConst.GAME_RESP_FAIL, type),
						player.getUserInfo(),GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("将领不存在,id=" + id,
						ProcotolType.HERO_RESP, GameConst.GAME_RESP_FAIL, type),
						player.getUserInfo(),GameUtils.FLUTTER);
			}
			
		}
		return playerHero;
	}

	/**
	 * 发送模块消息
	 * 
	 * @param player
	 * @param ph
	 */
	public void sendModuleMessage(PlayerCharacter player, PlayerHero ph) {
		RespModuleSet rms = new RespModuleSet(ProcotolType.HERO_RESP);// 模块消息
		rms.addModule(ph);
		rms.addModule(player.getData());
		AndroidMessageSender.sendMessage(rms, player);
	}
}
