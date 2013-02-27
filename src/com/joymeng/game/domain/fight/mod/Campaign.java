package com.joymeng.game.domain.fight.mod;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.battle.FBCamp;
import com.joymeng.game.domain.quest.AcceptedQuest;
import com.joymeng.game.domain.quest.PlayerQuestAgent;
import com.joymeng.game.domain.quest.Quest;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;

/**
 * 战役
 * @author admin
 * @date 2012-6-6
 * TODO
 */
public class Campaign {
	static Logger logger = LoggerFactory.getLogger(Campaign.class);
	private int id;
	private String name;
	private String battleName;//章名
	private int level;
	private int  money;
	private int  exp	;
	private int  equip	;
	private int rate	;
	private int  quality	;
	private int  num;
	private int propId	;
	private int forceId;

	private int freeNum;
	private int chargeNum;

	private String text;
	private String pic;
	public int getEquip() {
		return equip;
	}
	public void setEquip(int equip) {
		this.equip = equip;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getQuality() {
		return quality;
	}
	
	public String getBattleName() {
		return battleName;
	}
	public void setBattleName(String battleName) {
		this.battleName = battleName;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public int getForceId() {
		return forceId;
	}
	public void setForceId(int forceId) {
		this.forceId = forceId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getFreeNum() {
		return freeNum;
	}
	public void setFreeNum(int freeNum) {
		this.freeNum = freeNum;
	}
	public int getChargeNum() {
		return chargeNum;
	}
	public void setChargeNum(int chargeNum) {
		this.chargeNum = chargeNum;
	}
	/**
	 * 更新战役进度 
	 * @param player
	 */
	public void updateCampId(PlayerCharacter player){
		if(this.id>player.getData().getCampId()){
			player.getData().setCampId(this.id);
//			World.getInstance().savePlayer(player);
			logger.info("更新战役进度，当前战役 campId="+this.id);
			if(this.id>0 && this.id%9==0){
				 String msg = I18nGreeting.getInstance().getMessage("campaign.message", new Object[]{player.getData().getName(),this.getBattleName()});
				 NoticeManager.getInstance().sendSystemWorldMessage(msg);
			}
		}
	}
	/**
	 * 检测是否能进入
	 * @param player
	 * @param charge
	 * @return
	 */
	public boolean checkEnter(PlayerCharacter player,byte charge){
		//只能是关卡1打过了才能打2
		if(player.getData().getCampId()+1<this.id){
			logger.info("当前战役进度="+player.getData().getCampId()+" 所要进入的战役id="+this.id);
			return false;
		}
		CampInfo ci=new CampInfo("");
		String str=player.getData().getCamp();
		if(str==null||str.equals("")){//第一次
			ci.setId(this.id);
			ci.setFreeNum(this.freeNum);
			ci.setChargeNum(this.chargeNum);
			player.getData().setCamp(ci.toStr());
		}else{
			//判断时间是否过期
			String infos[]=str.split("/");
			for(int i=0;i<infos.length;i++){
				ci=new CampInfo(infos[i]);
				if(ci.getId()==this.id){//找到当前关卡的信息
					if(!checkQuest(player)){
					
					if(charge==0){
						if(ci.getFreeNum()<=0){
							return false;
						}
					}else{
						if(ci.getChargeNum()<=0){
							return false;
						}
						if(player.saveResources( GameConfig.JOY_MONEY, -1)<0){
							return false;
						}
					}
					}
					return true;
				}
			}
			//新加入的
			ci.setId(this.id);
			ci.setFreeNum(this.freeNum);
			ci.setChargeNum(this.chargeNum);
			str+="/"+ci.toStr();
			player.getData().setCamp(str);
		}
//		World.getInstance().savePlayer(player);
		return true;
	}
	public boolean checkQuest(PlayerCharacter player){
		//如果当前有相关的任务类型未完成，则不消耗
				List<AcceptedQuest> quests=player.getPlayerQuestAgent().getAcceptedQuests();
				for(AcceptedQuest aq:quests){
					Quest quest= aq.getQ();
					if(aq.getStatus()==PlayerQuestAgent.COMPLETE){//跳过已经完成的任务
						continue;
					}
					if(quest.getTarget()==QuestUtils.TYPE4||quest.getTarget()==QuestUtils.TYPE5){//如果有战斗任务
						//并且打的正好是这个任务所要求的
						if(quest.getTargetArgs1()==FightConst.FIGHTBATTLE_CAMP&&quest.getTargetArgs2()==this.id){
							return true;
						}
					}
				}
				return false;
	}
	public void enter(PlayerCharacter player,byte charge){
		String str=player.getData().getCamp();
		String infos[]=str.split("/");

		for(int i=0;i<infos.length;i++){
			CampInfo ci=new CampInfo(infos[i]);
			System.out.println("111");
			if(ci.getId()==this.id){//找到当前关卡的信息
				if(!checkQuest(player)){
					if(charge==0){
						ci.setFreeNum(ci.getFreeNum()-1);
					}else{
						ci.setChargeNum(ci.getChargeNum()-1);
					}
				}
			
				infos[i]=ci.toStr();
				break;
			}
		}
		str=StringUtils.recoverNewStr(infos, "/");
		player.getData().setCamp(str);
//		World.getInstance().savePlayer(player);
	}
}
