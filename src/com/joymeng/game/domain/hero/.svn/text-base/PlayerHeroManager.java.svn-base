package com.joymeng.game.domain.hero;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.Instances;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.item.Item;
import com.joymeng.game.domain.item.ItemConst;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipUtil;

public class PlayerHeroManager implements Instances{
	private static final Logger logger = LoggerFactory
			.getLogger(PlayerHeroManager.class);
	PlayerCharacter player;
	/**
	 * key =playerHeroId
	 */
	public HashMap<Integer,PlayerHero> playerHeroMap = new HashMap<Integer,PlayerHero>();
	
	public void init(){
		List<PlayerHero> list=	DBManager.getInstance().getWorldDAO().getAllPlayerHero((int)player.getData().getUserid());
		for(PlayerHero phero:list){
			phero.setPlayer(player);
			playerHeroMap.put(phero.getId(), phero);
//			phero.print();
		}
		logger.info("get player hero size="+list.size()+" uid="+player.getId());
	}
	/**
	 * 更新将领属性，该方法要确认在装备和技能都初始化后调用
	 */
	public void updateHero(){
		Iterator<Integer> it=playerHeroMap.keySet().iterator();
		while(it.hasNext()){
			PlayerHero playerhero=playerHeroMap.get(it.next());
			playerhero.updateHeroAttri();
		}
	}
	public PlayerHero getHero(int id){
		return playerHeroMap.get(id);
	}
	/**
	 * 加入一个英雄
	 */
	public PlayerHero addHero(Hero hero){
		PlayerHero playerHero=new PlayerHero();
		playerHero.setPlayer(player);
		playerHero.setUserId((int)player.getData().getUserid());
		playerHero.setName(hero.getName());
		playerHero.setIcon(hero.getIcon());
		playerHero.setLevel(1);
		playerHero.setExp(0);
		playerHero.setSex(hero.getSex());
		playerHero.setAttack(hero.getAttack());
		playerHero.setDefence(hero.getDefence());
		playerHero.setHp(hero.getMaxHp());
		playerHero.setInitAttack(hero.getAttack());
		playerHero.setInitHp(hero.getMaxHp());
		playerHero.setInitDefence(hero.getDefence());
		playerHero.setMaxHp(hero.getMaxHp());
		playerHero.setAttackAdd(hero.getAttackAdd());
		playerHero.setDefenceAdd(hero.getDefenceAdd());
		playerHero.setHpAdd(hero.getHpAdd());
		playerHero.setColor(hero.getColor());
		playerHero.setSoldierNum(hero.getSoldierNum());
		playerHero.setArmour(0);
		playerHero.setWeapon(0);
		playerHero.setHelmet(0);
		playerHero.setHorse(0);
		playerHero.setMemo("");
		playerHero.setSkillNum(hero.getSkillNum());
		playerHero.createSkill(hero);
		playerHero.setSoldier("");
		playerHero.updateHeroAttri();
		playerHero.setAttackTotal(hero.getAttack());
		playerHero.setDefenceTotal(hero.getDefence());
		playerHero.setHpTotal(hero.getMaxHp());
		int id=DBManager.getInstance().getWorldDAO().addPlayerHero(playerHero);
		playerHero.setId(id);
		playerHeroMap.put(id, playerHero);
		QuestUtils.checkFinish(player, QuestUtils.TYPE8, true,hero.getColor());
		return playerHero;
	}
	/**
	 * 移除一个英雄
	 * @param id
	 */
	public boolean removeHero(PlayerHero playerhero){
		if(!playerhero.remove()){
			return false;
		}
		//移除
		player.getPlayerStorageAgent().cutoverEquipment(playerhero.getWeapon(), 0, playerhero.getId());
		player.getPlayerStorageAgent().cutoverEquipment(playerhero.getArmour(), 0, playerhero.getId());
		player.getPlayerStorageAgent().cutoverEquipment(playerhero.getHelmet(), 0, playerhero.getId());
		player.getPlayerStorageAgent().cutoverEquipment(playerhero.getHorse(), 0, playerhero.getId());
		DBManager.getInstance().getWorldDAO().delPlayerHero(playerhero);
		playerHeroMap.remove(playerhero.getId());
		return true;
	}
	/**
	 * 刷新英雄
	 */
	public Hero[] randomHero(int num,int groupId,int level){
		Hero[] array=new Hero[num];
		for(int i=0;i<num;i++){
			array[i]=GameDataManager.heroManager.randomHero(groupId, level);
		}
		return array;
	}

	public HashMap<Integer, PlayerHero> getPlayerHeroMap() {
		return playerHeroMap;
	}

	public void setPlayerHeroMap(HashMap<Integer, PlayerHero> playerHeroMap) {
		this.playerHeroMap = playerHeroMap;
	}
	public PlayerCharacter getPlayer() {
		return player;
	}
	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}
	/**
	 * 获得玩家将领数组
	 * @return
	 */
	public PlayerHero[] getHeroArray(){
		PlayerHero[] arr=new PlayerHero[playerHeroMap.size()];
		Iterator<Integer> it=playerHeroMap.keySet().iterator();
		int i=0;
		while(it.hasNext()){
			arr[i]=playerHeroMap.get(it.next());
			i++;
		}
		return arr;
	}
	/**
	 * 保存玩家英雄数据
	 */
	public void save(int id){
		PlayerHero playerhero=playerHeroMap.get(id);
		if(playerhero != null){
			DBManager.getInstance().getWorldDAO().savePlayerHero(playerhero);
		}
	}
	public void saveAll(){
//		List<PlayerHero> list=new ArrayList<PlayerHero>();
		Iterator<Integer> it=playerHeroMap.keySet().iterator();
		while(it.hasNext()){
			PlayerHero playerhero=playerHeroMap.get(it.next());
			if(playerhero != null){
				DBManager.getInstance().getWorldDAO().savePlayerHero(playerhero);
			}
//			list.add(playerhero);
			
		}
//		DBManager.getInstance().getWorldDAO().savePlayerHero(list);
	}
	
	/**
	 * 修改英雄状态
	 * @param id 英雄id
	 * @param status 状态类型
	 * @param msg 带兵数
	 * @return 
	 */
	public boolean motifyStatus(int id,byte status,String memo,String msg,long time){
		logger.info("motifyStatus id:"+id+" status:"+status+" memo:"+memo+" msg:"+msg);
		PlayerHero playerhero=playerHeroMap.get(id);
		if(playerhero != null && playerhero.getStatus() != status){
			if(status == GameConst.HEROSTATUS_IDEL){//赋闲
//				playerhero.setStatus(status);
//				playerhero.setSoldier("");
//				playerhero.setMemo("");
				playerhero.setInfo(status, "", "");
				playerhero.setTrainEndTime(time);
//				saveAll();
				//rms
				RespModuleSet rms1=new RespModuleSet(ProcotolType.HERO_RESP);//模块消息
				rms1.addModule(playerhero);
				AndroidMessageSender.sendMessage(rms1, player);
				//rms
				return true;
			}else{//将领只有在赋闲站台才能驻防
				if(playerhero.getStatus() == GameConst.HEROSTATUS_IDEL){
//					playerhero.setStatus(status);
//					playerhero.setSoldier(msg);
//					playerhero.setMemo(memo);
					playerhero.setInfo(status,memo,msg);
					playerhero.setTrainEndTime(time);
//					saveAll();
					//rms
					RespModuleSet rms1=new RespModuleSet(ProcotolType.HERO_RESP);//模块消息
					rms1.addModule(playerhero);
					AndroidMessageSender.sendMessage(rms1, player);
					//rms
					return true;
				}
				return false;
			}
		}else if(playerhero != null && status ==GameConst.HEROSTATUS_IDEL && playerhero.getStatus() == status){
//			playerhero.setStatus(status);
//			playerhero.setSoldier("");
//			playerhero.setMemo("");
			playerhero.setInfo(status, "", "");
			playerhero.setTrainEndTime(time);
			saveAll();
			//rms
			RespModuleSet rms1=new RespModuleSet(ProcotolType.HERO_RESP);//模块消息
			rms1.addModule(playerhero);
			AndroidMessageSender.sendMessage(rms1, player);
			//rms
			return true;
		}
		return false;
	}
	
	public TipUtil userProps(int heroId,Props props){
		TipUtil tip = new TipUtil(ProcotolType.ITEMS_RESP);
		tip.setFailTip("使用失败!");
		PlayerHero hero = getHero(heroId);
		if(hero == null){
			return tip.setFailTip("hero is null");
		}else{
			if(hero.getStatus() !=  GameConst.HEROSTATUS_TRAIN && hero.getStatus() != GameConst.HEROSTATUS_IDEL){
				return tip.setFailTip("将领驻防无法执行本操作");
			}
			if(!player.getPlayerStorageAgent().isDelete(props.getId(), 1, Item.ITEM_PROPS).getStatus()){
				return tip.setFailTip("道具不足");
			}
			byte type = props.getPrototype().getPropsType();
			switch (type) {
			case ItemConst.HERO_EXP_TYPE:
				if(hero.getLevel()>= GameDataManager.heroManager.maxLevel()){
					return tip.setFailTip("已经升级到最高级别，无法继续升级");
				}
				hero.addExp(Integer.parseInt(props.getPrototype().getProperty2()));
				tip.setSuccTip("增加"+props.getPrototype().getProperty2()+"点经验");
				break;
			case ItemConst.TRAINING_HERO_TYPE:
				if(hero.speedUp()){
					tip.setSuccTip(hero.getTip().getMessage());
				}
				break;
			}
			tip = player.getPlayerStorageAgent().getPis().userProps(props.getId(), false);
			if(!tip.isResult()){
				return tip;
			}
			//*****rms
			RespModuleSet rms=new RespModuleSet(ProcotolType.HERO_RESP);
			rms.addModuleBase(tip.getLst());
			rms.addModule(hero);
			AndroidMessageSender.sendMessage(rms,player);
			//*****rms
		}
		return tip;
	}
	/**
	 * 修改状态
	 * @param id
	 * @param status
	 * @param memo
	 */
	public void changeStatu(int id,byte status,String memo){
		PlayerHero playerhero=playerHeroMap.get(id);
		String me = "";
		if(I18nGreeting.LANLANGUAGE_TIPS ==1){
			me = "Garrsion in ";
		}else{
			me = "驻守";
		}
		if(playerhero != null ){
			playerhero.setStatus(status);
			playerhero.setMemo(me+memo);
//			saveAll();
		}
	}
	/**
	 * 检测所有将领的状态，判断金矿和资源点
	 * @param type
	 * @return
	 */
	public boolean checkHeroStatus(byte type){
		PlayerHero[] heros=getHeroArray();
		for(PlayerHero h:heros){
			if(h.getStatus()==type){
				return true;
			}
		}
		return false;
	}
}
