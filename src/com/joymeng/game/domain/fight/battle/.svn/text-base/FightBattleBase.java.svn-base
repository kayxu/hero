package com.joymeng.game.domain.fight.battle;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.fight.FightCmd;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightSpecial;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.mod.FightInfo;
import com.joymeng.game.domain.fight.obj.FightCreature;
import com.joymeng.game.domain.fight.obj.FightGroup;
import com.joymeng.game.domain.fight.obj.FightPlayer;
import com.joymeng.game.domain.fight.obj.FightSkill;
import com.joymeng.game.domain.fight.obj.FightSoldier;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.skill.Skill;
import com.joymeng.game.domain.skill.SkillEffect;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.net.request.FightRequest;
import com.joymeng.game.net.response.FightResp;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.buffer.JoyBuffer;

public class FightBattleBase {
	private static Logger logger = Logger.getLogger(FightBattleBase.class);
	
	boolean isHeavyHurt;// 是否重伤
	private byte state;// 战斗阶段
	byte type;// 战斗类型
	private byte round;// 回合数
	private FightCmd cmd;// 战斗指令
	private byte heroWiner;// 将战胜利者
	private byte soldierWiner;// 兵战斗胜利者
	private boolean isAttackAction;// 是否是攻击方行动
	private boolean isMoveAction;// 每一个回合都包含一次移动，一次攻击
	private int winType;//
	private byte[] MOVECOUNT = new byte[] { -1, -1, -1, -1 };// 总共移动3次
	FightGroup attacker ;// 攻击方
	FightGroup defencer ;// 防守方
	TipMessage tip ;// 提示信息
	FightRequest request;
	PlayerCharacter player;
	UUID uuid ;
	StringBuilder sb=new StringBuilder();
	public FightBattleBase(FightRequest _request, PlayerCharacter _player) {
		this.request = _request;
		this.player = _player;
		this.type = request.getType();
		tip = new TipMessage("", ProcotolType.FIGHT_RESP,
				GameConst.GAME_RESP_FAIL, request.getType());
		attacker = new FightGroup(FightConst.FTYPE_ATTACK,tip);
		defencer = new FightGroup(FightConst.FTYPE_DEFENCE,tip);
	}

	public boolean init() {
//		logger.info("fight Init start>>>>>>\n");
		this.addLog("战斗初始化开始>>>\n",request.toString(),"\n");
		if (!initAttacker()) {//初始化攻击
			this.tip.setMessage(attacker.getTip().getMessage());
			return false;
		}
		if (!initDefencer()) {//初始化防御
			this.tip.setMessage(attacker.getTip().getMessage());
			return false;
		}
		if (!sendSoldier()) {//是否派兵
			return false;
		}
		this.addLog("┌────────────────────────────────┐\n",attacker.toString(),"│\n",defencer.toString(),"└────────────────────────────────┘\n");
		this.addLog("战斗初始化结束>>>\n");
		return true;

	}

	public boolean initAttacker() {
		int heroId = request.getAttackHero();// 攻击方将领
		String soldier = request.getSoldier1();// 攻击方士兵
		String newStr = player.getPlayerBuilgingManager().checkSolider(soldier);
		if ("".equals(newStr)) {
			tip.setMessage("攻击方士兵字符串解析错误");
			return false;
		}
		PlayerHero hero = player.getPlayerHeroManager().getHero(heroId);
		if (hero == null) {
			tip.setMessage("hero is null");
			return false;
		}
		if (!attacker.init( player, new FightPlayer(player,hero), newStr, type)) {
			tip.setMessage("攻击方初始化错误:"+attacker.getTip().getMessage());
			return false;
		}

		return true;
	}

	public void start() {
		long time1=TimeUtils.nowLong();
		if (!this.init()) {
			return;
		}
		long time2=TimeUtils.nowLong();
		logger.info("初始化耗时="+(time2-time1));
		//统计战斗次数
		FightInfo fi=new FightInfo(player.getData().getFightInfo());
		fi.addFight(this.getType());
		player.getData().setFightInfo(fi.toStr());
		QuestUtils.checkFinish(player, QuestUtils.TYPE9, true);
		//tip.setMessage("战斗成功");
		tip.setResult(GameConst.GAME_RESP_SUCCESS);
		// 状态参数赋值
		state = FightConst.STATUS_HERO;
		heroWiner = -1;
		soldierWiner = -1;
		FightUtil.resetRoundData(attacker, defencer);
		isAttackAction = false;
		// 战斗指令
		cmd = new FightCmd(this);
		cmd.initCmd(attacker.getList(), FightConst.FTYPE_ATTACK);
		cmd.initCmd(defencer.getList(), FightConst.FTYPE_DEFENCE);
		specialSkillInit(attacker,defencer);
		specialSkillInit(defencer,attacker);
		logger.info("fight  start>>>>>>\n");
		for(round=0;round<FightConst.MAXROUND;){
			switch (state) {
			case FightConst.STATUS_HERO:
				this.addLog("将战开始>>>\n");
				startHeroFight();
				this.addLog("将战结束>>>\n");
				break;
			case FightConst.STATUS_SOLDIER:
				this.addLog("兵战开始>>>\n");
				startSoldierFight();
				this.addLog("兵战结束>>>\n");
				break;
			}
		}
		long time3=TimeUtils.nowLong();
		logger.info("战斗耗时="+(time3-time2));
		fightEnd();
		long time4=TimeUtils.nowLong();
		logger.info("保存耗时="+(time4-time3));
		sendFightMessage();
		long time5=TimeUtils.nowLong();
		logger.info("发送消息耗时="+(time5-time4));
	
	}
	//处理特殊的技能
	public void specialSkillInit(FightGroup a ,FightGroup b){
		//找到攻守双方技能类型=7的技能
		List<FightSkill> list1=a.getSkillList();
		List<FightSkill> list2=b.getSkillList();
//		logger.info("受影响的将领 hero name="+b.getHero().getName());
		for(FightSkill fs:list2){
//			logger.info(" 拥有的技能 skilltype=="+fs.getSkill().getSkillType()+" id=="+fs.getId());
		}
		for(FightSkill fs:list1){
			Skill sk=fs.getSkill();
			if(sk.getBackup2()==FightConst.SKILL_TYPE7){
//				logger.info("hero name="+a.getHero().getName()+" 拥有技能类型="+FightConst.SKILL_TYPE7+"，id="+sk.getId()+" skillType=="+sk.getSkillType());
				switch(sk.getSkillType()){
				case 57://5936 影响技能 8.10.11.12.14
					for(FightSkill ffs:list2){
						if(FightUtil.checkByType(ffs.getSkill(), new int[]{sk.getBackup4(),sk.getBackup5(),sk.getBackup6(),sk.getBackup7(),sk.getBackup8()})){
							ffs.addRatePlus(sk.getSkillProbability());
						}
					}
					break;
				case FightConst.SID_62://5954,影响技能 8.10.11.12.14
					for(FightSkill ffs:list2){
						if(FightUtil.checkByType(ffs.getSkill(), new int[]{sk.getBackup4(),sk.getBackup5(),sk.getBackup6(),sk.getBackup7(),sk.getBackup8()})){
							ffs.addRealHurtRate(sk.getSkillProbability());
//							logger.info("SID_62 影响技能id="+ffs.getId()+" 影响数值="+sk.getSkillProbability());
						}
					}
					break;
				case 58://5937 影响技能 35.36.37.38.39
					for(FightSkill ffs:list2){
						if(FightUtil.checkByType(ffs.getSkill(), new int[]{sk.getBackup4(),sk.getBackup5(),sk.getBackup6(),sk.getBackup7(),sk.getBackup8()})){
							ffs.addRatePlus(sk.getSkillProbability());
						}
					}
					break;
				case FightConst.SID_63://5955
					for(FightSkill ffs:list2){
						if(FightUtil.checkByType(ffs.getSkill(), new int[]{sk.getBackup4(),sk.getBackup5(),sk.getBackup6(),sk.getBackup7(),sk.getBackup8()})){
							ffs.addHurt(sk.getSkillProbability());
						}
					}
					break;
				case FightConst.SID_31://4101
					for(FightSkill ffs:list2){
						if(FightUtil.checkByType(ffs.getSkill(), new int[]{sk.getBackup4(),sk.getBackup5(),sk.getBackup6(),sk.getBackup7(),sk.getBackup8()})){
							ffs.addHurt(sk.getSkillProbability());
						}
					}
					break;
				}
			}
		}
		
	}

	/**
	 * 开始将战
	 */
	public void startHeroFight() {
		// 移动，第一回合双方将领移动
		doMove(FightConst.CREATURE_HERO);
		// 进入战斗，将战模块即将开始的时候,双方将领都可以触发技能
		touchSkill((byte) 0);
		
		while (!checkHeroEnd()) {// 将战没有结束，选择一个将领进行攻击
			this.addLog("********************[round]===",String.valueOf(round),"********************\n");
			// 将战每个回合开始时，判断将领出手顺序的时候
			touchSkill((byte) 1);
			cmd.roundCmd();
			// touchSkill((byte) 2, attacker.getHero());//生命buff计算，附加状态
			while (!checkRoundEnd()) {// 当本回合没有结束
//				logger.info("=============================round=" + round);
				doAttack(FightConst.STATUS_HERO);
				this.addLog("┌────────────────────────────────┐\n",String.valueOf(round),attacker.heroString(),"│\n",defencer.heroString(),"└────────────────────────────────┘\n");
			}
			round++;
			FightUtil.resetRoundData(attacker, defencer);
		
		}
		// 将战结束
		touchSkill((byte) 7);
		// 重置回合数据
		FightUtil.resetRoundData(attacker, defencer);
		// 确定获胜方,获胜方攻防加成20%+政绩加成
		logger.info("hp1="+attacker.getHero().getHp());
		logger.info("hp2="+defencer.getHero().getHp());
		if (attacker.getHero().getHp() > 0) {
			heroWiner = FightConst.FTYPE_ATTACK;
			isAttackAction = true;
			attacker.endHeroFight(FightConst.FIGHT_WIN,type);
			defencer.endHeroFight(FightConst.FIGHT_LOSE,type);
		} else {
			heroWiner = FightConst.FTYPE_DEFENCE;
			isAttackAction = false;
			attacker.endHeroFight(FightConst.FIGHT_LOSE,type);
			defencer.endHeroFight(FightConst.FIGHT_WIN,type);
		}
		//发送攻守双方的加成
		cmd.heroFightEnd(defencer);
		cmd.heroFightEnd(attacker);
		isMoveAction = false;// 是否是移动回合,兵战先执行一次攻击
		// 移动， 返回
		doMove(FightConst.STATUS_HERO);
		// 切换到兵战状态
		state = FightConst.STATUS_SOLDIER;
	}

	/**
	 * 开始兵战
	 */
	public void startSoldierFight() {
		if (!isHeavyHurt) {// 没有产生重击,设置血量=1，以便可以使用技能
			if (attacker.getHero().getHp() <= 0) {
				attacker.getHero().setHp(1);
			} else {
				defencer.getHero().setHp(1);
			}
		}
		
		touchSkill((byte) 8);// 将战结束，将领归位，兵战模块开始的时候
		// isAttackAction=!isAttackAction;
		// touchSkill((byte) 8);// 将战结束，将领归位，兵战模块开始的时候
		// isAttackAction=!isAttackAction;

	
		boolean firstAction=isAttackAction;
		boolean isMove = false;// 是否可以移动
		while (!checkSoldierEnd()) {// 当战斗没有结束
			cmd.roundCmd();
			this.addLog("********************[round] ",String.valueOf(round),"********************\n");
			
			isMove=checkMove();
//			logger.info("1 isAttackAction="+isAttackAction);
			if (!isMove||!isMoveAction) {//非移动状态下处理技能
				touchSkill((byte) 9);// 兵战每个回合即将开始的时候
				isAttackAction = !isAttackAction;
				touchSkill((byte) 9);// 兵战每个回合即将开始的时候
				isAttackAction = !isAttackAction;
			}
//			logger.info("2 isAttackAction="+isAttackAction);
			// 先攻击，再移动
			while (!checkRoundEnd()) {// 当本回合没有结束
				if(isMove&&isMoveAction){
					doMove(FightConst.STATUS_SOLDIER);
					break;
				}else{
					doAttack(FightConst.STATUS_SOLDIER);
				}
			}
//			logger.info("3 isAttackAction="+isAttackAction);
			if (!isMove||!isMoveAction) {//非移动状态下处理技能
				touchSkill((byte) 10);// 兵战每个回合结束的时候
				isAttackAction = !isAttackAction;
				touchSkill((byte) 10);// 兵战每个回合结束的时候
				isAttackAction = !isAttackAction;
				this.addLog("┌────────────────────────────────┐\n",String.valueOf(round),attacker.soldierString(),"│\n",defencer.soldierString(),"└────────────────────────────────┘\n");
			}
//			logger.info("4 isAttackAction="+isAttackAction);
			if(isMove){//可以移动，则切换攻击与移动动作
				isMoveAction = !isMoveAction;
			}
			round++;
			FightUtil.resetRoundData(attacker, defencer);
//			logger.info("5 isAttackAction="+isAttackAction);
			isAttackAction=firstAction;//每一个回合结束后重置先手
		}
		touchSkill((byte) 12);// 兵战模块结束
		round = FightConst.MAXROUND;// 跳出循环
	}

	/**
	 * 结束战场
	 */
	public void fightEnd() {
		// 确定获胜方
		logger.info("战斗结束----round=" + round);
		long time=TimeUtils.nowLong();
		winType = heroWiner;// 如果没有兵战，则胜利方=将战胜利方
		if (soldierWiner != -1) {
			// System.out.println("soldierWiner=" + soldierWiner);
			winType = soldierWiner;// 否则胜利方=兵战胜利方
		}else{
			if(	attacker.getSoldierNum()>defencer.getSoldierNum()){
				winType=FightConst.FTYPE_ATTACK;
			}else{
				winType=FightConst.FTYPE_DEFENCE;
			}
		}
		// 攻击方获胜
		if (winType == FightConst.FTYPE_ATTACK) {
			attacker.setWinType(FightConst.FIGHT_WIN);
			defencer.setWinType(FightConst.FIGHT_LOSE);
		} else {
			attacker.setWinType(FightConst.FIGHT_LOSE);
			defencer.setWinType(FightConst.FIGHT_WIN);
		}
		//gongshou shuangfang 
		attacker.resetSoldierNum();
		defencer.resetSoldierNum();
		this.addLog("┌────────────────────────────────┐\n");
		// 获得战斗奖励
	
		getReward();
		long time2=TimeUtils.nowLong();
		logger.info("getReward 耗时="+(time2-time));
		this.addLog("\n└────────────────────────────────┘\n");
//		List<Cell> goods = group.getGoods();
//		if (goods.size() == 0) {
//			logger.info("不存在战斗奖励");
//		}
//		for (Cell cell : goods) {
//			rms.addModule(cell);
//			logger.info("通知奖励物品，cell=" + cell.toString());
//		}
		this.addLog("┌────────────────────────────────┐\n","fightEnd\n",attacker.toString(),"│\n",defencer.toString(),"└────────────────────────────────┘\n");
		cmd.endCmd(attacker);
		cmd.endCmd(defencer);
		long time3=TimeUtils.nowLong();
		logger.info("endCmd 耗时="+(time3-time2));
		saveResult();
		long time4=TimeUtils.nowLong();
		logger.info("saveResult 耗时="+(time4-time3));
//		this.fightlog(sb.toString());
	}

	/**
	 * 攻击判定
	 * 
	 * @param fc
	 */
	public void doAttack(byte type) {
		FightCreature fc = null;
		FightCreature target = null;
		switch (type) {
		case FightConst.STATUS_HERO:
			if (isAttackAction) {// 攻击方攻击
				fc = attacker.getHero();
				target = defencer.getHero();
			} else {// 防守方攻击
				fc = defencer.getHero();
				target = attacker.getHero();
			}
			doFight(fc, target);
			fc.setActionNum(fc.getActionNum() + 1);// 一定要放在doFight后面
			break;
		case FightConst.STATUS_SOLDIER:
			if (isAttackAction) {// 攻击方攻击
				soldierAttack(attacker, defencer);
			} else {// 防守方攻击
				soldierAttack(defencer, attacker);
			}
			break;
		}
		isAttackAction = !isAttackAction;
	}

	/**
	 * 移动判定
	 * 
	 * @param fc
	 */
	public void doMove(byte type) {
		if (checkSoldierEnd()) {
			return ;
		}
		boolean canMove = false;
		for (int i = 0; i < MOVECOUNT.length; i++) {
			if (MOVECOUNT[i] == -1) {
				MOVECOUNT[i] = 0;
				canMove = true;
				break;
			}
		}
		if (!canMove) {
			return ;
		}
		this.addLog("do move \n");
		FightUtil.move(attacker, state);
		FightUtil.move(defencer, state);
		cmd.moveCmd(type);
//		for (int i = 0; i < MOVECOUNT.length; i++) {
//			if (MOVECOUNT[i] == -1) {
//				return true;
//			}
//		}
//		return false;
	}
	/**
	 * 检测是否能移动
	 * @return
	 */
	public boolean checkMove(){
		for (int i = 0; i < MOVECOUNT.length; i++) {
			if (MOVECOUNT[i] == -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断本回合结束 死亡或者已经出过手了
	 */
	public boolean checkRoundEnd() {
		// 双方活着的士兵或者将领都出手过了
		if (!FightUtil.checkRoundEnd(attacker, state)) {
			return false;
		}
		if (!FightUtil.checkRoundEnd(defencer, state)) {
			return false;
		}
		return true;
	}

	/**
	 * 检测将战结束
	 * 
	 * @return
	 */
	public boolean checkHeroEnd() {
		// 获得攻击方和防御方的将领
		if (round >= FightConst.MAXROUND) {
			return true;
		}
		FightCreature fc1 = attacker.getHero();
		FightCreature fc2 = defencer.getHero();
		// 攻守双方的将领有一方血量《=0则结束
		while (fc1.getHp() > 0 && fc2.getHp() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 检测兵战是否结束
	 */
	public boolean checkSoldierEnd() {
		// 双方都没有士兵
		if (round >= FightConst.MAXROUND) {
			return true;
		}
		if ((attacker.getList().size() <= 1)
				&& (defencer.getList().size() <= 1)) {
			return true;
		}
		// 一方所有单位都死亡
		if (attacker.checkAllSoldierDead()) {
			soldierWiner = FightConst.FTYPE_DEFENCE;
			return true;
		}
		if (defencer.checkAllSoldierDead()) {
			soldierWiner = FightConst.FTYPE_ATTACK;
			return true;
		}
		return false;
	}

	/**
	 * 发送战斗通知
	 */
	public void sendFightMessage() {
		sendNotify(attacker);
		sendNotify(defencer);

		JoyBuffer out = cmd.getOut();
		int index = 0;
		out.put(index, this.type);
		// int index=1;//跳过type
		// out.putInt(index, cmd.getCmdCount());
		// index+=4;
		// out.put(index,this.round);
		FightResp resp = new FightResp();
		byte data[] = out.array();
		resp.setData(data);
		resp.setUserInfo(request.getUserInfo());
//		logger.info("cmd count=" + cmd.getCmdCount() + " round=" + round);
		// send
		JoyServiceApp.getInstance().sendMessage(resp);
	}

	/**
	 * 发送模块消息
	 * 
	 * @param group
	 */
	public void sendNotify(FightGroup group) {
		if (group.getType() != FightConst.CREATURE_HERO) {// 只有真实的玩家才会获得奖励
			return;
		}
		// // 通知兵营数据,不需要，客户端每次都请求
		RespModuleSet rms = new RespModuleSet(ProcotolType.FIGHT_RESP);
		// 通知奖励物品
		List<Cell> goods = group.getGoods();
		if (goods.size() == 0) {
//			logger.info("不存在战斗奖励");
		}
		for (Cell cell : goods) {
			rms.addModule(cell);
//			logger.info("通知奖励物品，cell=" + cell.toString());
		}
		// 通知玩家
		rms.addModule(group.getPc().getData());
//		logger.info("fight end role ="+group.getPc().getData().print());
		// 通知将领
		rms.addModule(((FightPlayer) group.getHero()).getHero());
		AndroidMessageSender.sendMessage(rms, group.getPc());

	}

	public FightEvent createFightEvent(FightGroup group) {
	
		// FightUtil.deserializeCmd(data1);
		// 保存战报数据
		FightEvent fightEvent = group.getFightEvent();
		fightEvent.setUserId(group.getPc().getId());
		fightEvent.setHeroId(group.getHero().getId());
		fightEvent.setType(this.type);
		fightEvent.setResult(group.getWinType());
		fightEvent.setMemo("");
		fightEvent.setDate(new Date());
		fightEvent.setIsAttack(group.getAttackType());
		return fightEvent;
	}

	/**
	 * 触发技能
	 * 
	 * @param type
	 *            触发点类型
	 * @param fc
	 *            战斗对象
	 * @param isAttack
	 *            是否是当前攻击方
	 */
	public List<FightSkill> touchSkill(byte triggerType) {
		List<FightSkill> list = new ArrayList<FightSkill>();
		//判断攻守双方是否有一方已经死亡，如果有，则不处理任何技能
		if (state == FightConst.STATUS_HERO) {// 如果是将战
			if(attacker.getHero().isDead()||defencer.getHero().isDead()){
//				logger.info("touchSkill 将战一方已经死亡,不处理技能触发");
				return null;
			}
		}else{
			if(attacker.checkAllSoldierDead()||defencer.checkAllSoldierDead()){
//				logger.info("touchSkill 兵战一方已经死亡,不处理技能触发");
				return null;
			}
		}
			this.addLog("■ sk=",String.valueOf(triggerType),"\n");
		if (triggerType == 1) {// 将战每个回合开始
			FightUtil.doSpecial(attacker.getHero(), defencer.getHero(),
					FightSpecial.FIGHT_EFFECT_BURN, 0, this);
			FightUtil.doSpecial(defencer.getHero(), attacker.getHero(),
					FightSpecial.FIGHT_EFFECT_BURN, 0, this);
		}
		// if (triggerType == 4) {
		// System.out.println("----------------touch skill 4");
		// }
		if (isAttackAction) {
			list = SkillEffect.use(attacker, defencer, this, triggerType);
		} else {
			list = SkillEffect.use(defencer, attacker, this, triggerType);
		}
		this.addLog("skill size=",String.valueOf(list.size()),"\n");
		for(FightSkill fs:list){
			this.addLog("skill id=",String.valueOf(fs.getId()));
		}
		if (triggerType == 1) {
			// 获得将领的克敌先机技能
			FightUtil.doSpecial(attacker.getHero(), defencer.getHero(),
					FightSpecial.FIGHT_EFFECT_FIRST, 0, this);
			FightUtil.doSpecial(defencer.getHero(), attacker.getHero(),
					FightSpecial.FIGHT_EFFECT_FIRST, 0, this);
		}

		return list;

	}

	/**
	 * 攻击
	 * 
	 * @param attack
	 * @param target
	 */
	public void doFight(FightCreature attack, FightCreature target) {
		this.addLog("[",this.isAttackAction?"A=":"B=",attack.getName(),"]","===>","[",this.isAttackAction?"B=":"A=",target.getName(),"] ", "\n");
		// logger.info("round=" + round + " A id=" + attack.getId() + "("
		// + attack.getName() + ") ==>B id=" + target.getId() + "("
		// + target.getName() + ") ******\n");

		// 是否攻击到对方
		int random = MathUtils.random(100);

		if (state == FightConst.STATUS_HERO) {// 如果是将战
			if (!FightUtil.canFight(attack)) {// 如果可以攻击
				return;
			}
			List<FightSpecial> list = target.getSpecial();
			List<FightSpecial> list2 = attack.getSpecial();
			// 攻击方开始攻击
			touchSkill((byte) 3);
			// 被攻击方技能触发
			touchSkill((byte) 5);
			// 闪避的判断
			boolean dodge = false;
			for (FightSpecial fs : list) {
				if (fs.getType() == FightSpecial.FIHGT_EFFECT_DODGE) {
					dodge = true;
					fs.effect();
					logger.info("触发闪避技能");
					break;
				}
			}
			List<FightSkill> fsList = new ArrayList<FightSkill>();
//			logger.info("random=="+random+"  target.getDodge()="+ target.getDodge()+" dodge="+dodge);
			if ((random >= target.getDodge()) && !dodge) {// 攻击到对方
				// 攻击方攻击到对方
				touchSkill((byte) 4);
				// 被攻击方被攻击到后触发
				touchSkill((byte) 6);
			} else {
				// 闪避
				cmd.attackCmd(attack, target, (int) target.getHp(), false,
						attack.getEffect());
				return;
			}
			if (target.getHp() <= 0) {
//				logger.info("target hp <=0, 不触发普通攻击");
				return;
			}
			int hp = FightUtil.mathHp(attack, target);// 计算被攻击方的剩余血量
			int hurt = (int) target.getHp() - hp;// 受到的伤害
			// 对克敌先机的判断
			for (FightSpecial fs : list2) {
				if (fs.getType() == FightSpecial.FIGHT_EFFECT_FIRST) {
					int effect = fs.getP1();
//					logger.info("触发克敌先机技能,effect=" + effect + " 当前伤害=" + hurt
//							+ " 剩余血量=" + hp);
					hurt = hurt * effect / 100;
					hp = (int) target.getHp() - hurt;
					fs.effect();
					break;
				}
			}
			// 格挡的判断
			for (FightSpecial fs : list) {
				if (fs.getType() == FightSpecial.FIHGT_EFFECT_BLOCK) {
					hurt = 1;
					hp = (int) target.getHp() - hurt;
					fs.effect();
//					logger.info("触发格挡技能 , 当前伤害=" + hurt + " 剩余血量=" + hp);
					break;
				}
			}
			isHeavyHurt = FightUtil.checkDead(attack, target, hp);
			cmd.attackCmd(attack, target, hp, isHeavyHurt, attack.getEffect());// 发送战斗指令
			// 对某些技能的特殊处理，这些一定要放在普通攻击之后
			// if(target.getHp()>0){
			// for(FightSkill fs:fsList){
			// logger.info("xxxxxxxxxxxxxx="+fs.getId());
			// cmd.skillCmd(attack, fs.getId(), false, (byte) 0, new int[0]);
			// }
			// }

			// 反弹的判断
			FightUtil.doSpecial(attack, target,
					FightSpecial.FIGHT_EFFECT_BOUNCE, hurt, this);
			// 是否恢复生命
			FightUtil.doSpecial(target, attack,
					FightSpecial.FIGHT_EFFECT_RESUMEHP, 0, this);
			// touchSkill((byte) 11);
//			logger.info("round=" + round + " setHp" + " id=" + target.getId()
//					+ " hp=" + target.getHp() + "\n");
		} else {// 如果是兵战
			if (target instanceof FightSoldier) {
				if (random < target.getDodge()) {// 未攻击到对方
					// 发送攻击不到的指令
					// 闪避
					cmd.attackCmd(attack, target, 0, false, attack.getEffect());
					return;
				}
				int num = FightUtil.mathNum(attack, target);

				int result = target.getNum() - num;
				if (result <= 0) {
					result = 0;
				}
				// 加入蛊惑技能的处理，如果触发了蛊惑，则对方兵种减少enchant%数量，自己增加enchant%
//				logger.info("attack=" + attack.getName() + "==>target="
//						+ target.getName() + " 当前数量=" + target.getNum()
//						+ " 损失数量=" + num + " 剩余数量=" + result);
				// target.addLose(target.getNum() - result);
				target.setNum(result<0?0:result);
				cmd.attackCmd(attack, target, target.getNum(), false,
						attack.getEffect());
				// logger.info("round=" + round + " SetNum" + " id="
				// + target.getId() + " isAttack=" + target.isAttack()
				// + " num=" + target.getNum() + "\n");
			} else {
				return;
			}

		}

	}

	/**
	 * 士兵攻击
	 * 
	 * @param list
	 * @param isAttack
	 * @param out
	 */
	public void soldierAttack(FightGroup a,FightGroup b) {
//		logger.info("=================soldierAttack==============round="+round+ (a.getAttackType()==FightConst.FTYPE_ATTACK ? " action A" : " action B"));
		List<FightCreature> list=a.getSoldier();
		List<FightCreature> defList=b.getSoldier();
		FightCreature target = null;
		StringBuilder sb=new StringBuilder();
		for (int i = 1; i < FightConst.MAXTYPE; i++) {// 将领优先级为1，跳过将领操作
			FightCreature f = FightUtil.getFightObj(list, (byte) i);
			if (f == null) {
				continue;
			}
			sb.append("出手的兵种=").append(f.getName());
//			logger.info("出手的兵种=="+f.getName());
		
			if (FightUtil.canFight(f)) {// 如果可以攻击
				f.setActionNum(f.getActionNum() + 1); // 无论本次是否能找到攻击对象，
				target = FightUtil.selectTarget(f, defList);// 选择目标
				sb.append(" 可以攻击 ");
				sb.append("选择目标=");
				if (target != null) {// 选择目标不为空
//					logger.info("debug fight "
//							+ (f.isAttack() ? "左边行动A" : "右边行动B") + f.getName()
//							+ "=>" + target.getName());
					sb.append( target.getName());
					doFight(f, target);
					break;
				} else {
					sb.append(" NULL");
//					logger.info("debug fight "
//							+ (f.isAttack() ? "左边行动A" : "右边行动B") + f.getName()
//							+ "=>  NULL");
				}
				sb.append("\n");
			}else{
				f.setActionNum(f.getActionNum() + 1); // 无论本次是否能找到攻击对象，
				sb.append(" 不能攻击").append("\n");
			}
		}
//		logger.info("=================soldierAttack==============round="+round+" \n"+sb.toString());
	}

	/**
	 * 派兵,需要覆写
	 * 
	 * @return
	 */
	public boolean sendSoldier() {
		logger.info("error,sendSoldier default");
		return false;
	}

	/**
	 * 初始化防守方,需要覆写
	 * 
	 * @return
	 */
	public boolean initDefencer() {
		logger.info("error,initDefencer default");
		return false;
	}

	/**
	 * 获得奖励,需要覆写
	 */
	public void getReward() {
		logger.info("error,getReward default");
	}
	public void saveResult(){
		logger.info("error,saveResult default");
	}
	/**
	 * 保存战斗结果,需要覆写
	 */
	public void saveResult(FightEvent fe1,FightEvent fe2) {
		long time=TimeUtils.nowLong();
		String str1="",str2="";
		if(fe1!=null){
			str1=fe1.getMemo();
		}
		if(fe2!=null){
			str2=fe2.getMemo();
		}
		cmd.resultCmd(str1, str2);
		
		JoyBuffer out = cmd.getOut();
		int index = 0;// 跳过type
		out.put(index, FightConst.FIGHTBATTLE_EVENT);
		index = 1;
		out.putInt(index, cmd.getCmdCount());
		index += 4;
		out.put(index, this.round);
		logger.info("cmd round=" + cmd.getCmdCount() + " round=" + this.round);
		
		byte data[] = cmd.getOut().array();
		// byte testData[]=cmd.getOut().arrayToPosition();
		 System.out.println("length="+data.length);
		// System.out.println("testData="+testData.length);
		byte data1[] = null;
		String str = null;
		try {
			str = new String(data,"ISO-8859-1");
			data1 = str.getBytes("ISO-8859-1");
//			System.out.println("length1=" + data1.length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(attacker.getPc()!=null){
			fe1.setData(str);
			attacker.getPc().getFightEventManager()
			.addFightEvent(fe1);
		}
		if(defencer.getPc()!=null){
			fe2.setData(str);
			defencer.getPc().getFightEventManager()
			.addFightEvent(fe2);
		}
//		logger.info("saveResult2 耗时="+(TimeUtils.nowLong()-time));
	
	}

	public TipMessage getTip() {
		return tip;
	}

	public byte getType() {
		return type;
	}

	public byte getRound() {
		return round;
	}

	public FightCmd getCmd() {
		return cmd;
	}

	public boolean isAttackAction() {
		return isAttackAction;
	}

	public void setAttackAction(boolean isAttackAction) {
		this.isAttackAction = isAttackAction;
	}
	
	public FightGroup getAttacker() {
		return attacker;
	}

	public void setAttacker(FightGroup attacker) {
		this.attacker = attacker;
	}

	public FightGroup getDefencer() {
		return defencer;
	}

	public void setDefencer(FightGroup defencer) {
		this.defencer = defencer;
	}

	/**
	 * 加入log
	 * @param strings
	 */
	public void addLog(String...strings){
		com.joymeng.core.utils.StringUtil.append(this.sb, strings);
	}
	public void fightlog(String str){
		NDC.push(this.uuid.toString());
		logger.info(str);
		NDC.pop();
		NDC.remove();
	}
	public void setTip(TipMessage tip) {
		this.tip = tip;
	}
}
