package com.joymeng.game.domain.fight;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.domain.fight.battle.FightBattleBase;
import com.joymeng.game.domain.fight.obj.FightCreature;
import com.joymeng.game.domain.fight.obj.FightGroup;
import com.joymeng.game.domain.fight.obj.FightMonster;
import com.joymeng.game.domain.fight.obj.FightPlayer;
import com.joymeng.game.domain.fight.obj.FightSkill;
import com.joymeng.game.domain.fight.obj.FightSoldier;
import com.joymeng.game.domain.fight.result.FightReward;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.skill.Skill;
import com.joymeng.game.domain.soldier.Soldier;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.services.core.buffer.JoyBuffer;

public class FightUtil {
//	private static Logger logger = Logger.getLogger(FightUtil.class.getName());


	/**
	 * 重置回合数据
	 * 
	 * @param list
	 */
	public static void resetRoundData(List<FightCreature> list) {
		for (FightCreature f : list) {
			// 重置可以行动次数
			f.setActionNum(0);
			f.setMaxActionNum(1);
			f.doSpecial();
			f.resetSkill();
//			f.setCritical(false);
//			logger.info("name=" + f.getName() + " num=" + f.getNum() + " hp="
//					+ f.getHp());
			f.clearEffect();
		}

	}

	/**
	 * 执行特殊状态逻辑
	 * 
	 * @param fc
	 */
	public static void doSpecial(FightCreature fc) {
		List<FightSpecial> list = fc.getSpecial();

		if (fc.getHp() <= 0) {
//			logger.info("name=" + fc.getName() + " 特殊效果总数=" + list.size()
//					+ " 该对象hp=" + fc.getHp());
			list.clear();
		}
		for (int i = 0; i < list.size(); i++) {
			FightSpecial fs = list.get(i);
			if (fs.getCounter() <= 0) {
//				logger.info("移除特殊效果 ---------------  type=" + fs.getType());
				list.remove(fs);
				i--;
			}
		}
	}

	public static void addSpecial(FightCreature fc, FightSpecial f) {
		fc.getSpecial().add(f);
//		logger.info(fc.getName() + "--------------- 增加特殊效果,type=" + f.getType()
//				+ " counter=" + f.getCounter() + " p1=" + f.getP1());
	}

	/**
	 * 
	 * @param fc1
	 *            受影响的
	 * @param fc2
	 *            产生影响的
	 * @param type
	 */
	public static void doSpecial(FightCreature fc1, FightCreature fc2,
			byte type, int hurt, FightBattleBase fb) {
		if(fc1.getHp()<=0){
//			logger.info("doSpecial ,fc1 hp <=0");
			return;
		}
		List<FightSpecial> list = fc1.getSpecial();
		FightCmd cmd = fb.getCmd();
		int effect[] = new int[10];
		for (FightSpecial fs : list) {
			if (fs.getType() != type) {
				continue;
			}
//			logger.info("--------------处理特效影响--------受影响的=" + fc1.getName()
//					+ " 产生影响的=" + fc2.getName());
			switch (type) {
			case FightSpecial.FIGHT_EFFECT_BURN: // 灼烧的判断
				int hpRate = fs.getP1();
//				logger.info("触发灼烧技能效果，name=" + fc1.getName());
//				logger.info("当前血量=" + fc1.getHp() + "减少生命比例=" + hpRate);
				int hp = (int) (fc1.getHp() + fc1.getMaxHp() * hpRate / 100);
//				logger.info("剩余血量=" + hp);
				checkDead(fc2, fc1, hp);
				fs.effect();
				break;
			case FightSpecial.FIGHT_EFFECT_RESUMEHP:
				int r = fs.getP1();
				float _hp = fc1.getMaxHp() * r / 100;
				float num = (int) fc1.getHp() + _hp;
				if (num > fc1.getMaxHp()) {
					num = fc1.getMaxHp();
				}
				effect[5] = (int) (num - fc1.getHp());
				fc1.setHp(num);
//				logger.info("触发回血技能，恢复血量=" + _hp);
				fs.effect();

				cmd.skillCmd(fc1, fs.getP2(), false, (byte) 0, effect);
				break;
			case FightSpecial.FIGHT_EFFECT_BOUNCE:
				num = fs.getP1() * hurt / 100;
//				logger.info("触发反弹技能, 受到伤害=" + hurt + "反弹比例=" + fs.getP1()
//						+ " 反弹伤害=" + num);
//				logger.info("攻击方当前血量=" + fc1.getHp());
				boolean isHeavyHurt = FightUtil.checkDead(fc2, fc1,
						(int) fc1.getHp() + (int)num);
//				logger.info("攻击方被反弹后剩余血量=" + fc1.getHp());
				if(fc2.getHp()<=0){
//					logger.info("被攻击方死亡不触发反弹" );
					break;
				}
				fs.effect();
				effect[5] = (int)num;
				cmd.skillCmd(fc2, fs.getP2(), isHeavyHurt, (byte) 0, effect);
				break;
			case FightSpecial.FIGHT_EFFECT_FIRST:
				if (!fb.isAttackAction() && fc1.isAttack()) {// 如果是攻击方，但是是防守方先手
					fb.setAttackAction(true);
//					logger.info("自己是攻击方，但是是防守方先手，fb.isAttackAction()="
//							+ fb.isAttackAction());
				}
				if (fb.isAttackAction() && !fc1.isAttack()) {// 如果是防守，但是是攻击方方先手
					fb.setAttackAction(false);
//					logger.info("自己是防守，但是是攻击方方先手，fb.isAttackAction()="
//							+ fb.isAttackAction());
				}
			
//				logger.info("name=" + fc1.getName() + " 触发克敌先机技能");
				break;
			}

		}
	}

	/**
	 * 根据优先级别 获得行动对象
	 * 
	 * @param list
	 * @param type
	 *            ，优先级
	 * @return
	 */
	public static FightCreature getFightObj(List<FightCreature> list, byte type) {
		for (FightCreature f : list) {
			if (f.getPriority() == type) {
				return f;
			}
		}
		return null;
	}

//	/**
//	 * 检测兵战的胜利者
//	 * 
//	 * @param list
//	 * @return =true 表示对方获胜
//	 */
//	public static boolean checkSoldierWinner(List<FightCreature> list) {
//		int allHp = 0;
//		for (FightCreature f : list) {
//			allHp += f.getHp();
//		}
//		// System.out.println("allHp="+allHp);
//		if (allHp <= 0) {
//			return true;
//		}
//		return false;
//	}

	/**
	 * 回合是否结束
	 * 
	 * @param group
	 * @param type
	 *            0将战1兵战
	 * @return
	 */
	public static boolean checkRoundEnd(FightGroup group, byte type) {
//		logger.info("checkRoundEnd type="+type);
		//没死的人都行动过了==ture
		//还有活着的人没有行动过==false
		if (type == FightConst.STATUS_HERO) {
			FightCreature f = group.getHero();
			if (!f.isDead() && (f.getActionNum() < f.getMaxActionNum())) {
				return false;
			}
		} else {
			for (FightCreature f : group.getSoldier()) {
				if (!f.isDead() && (f.getActionNum() < f.getMaxActionNum())) {
					return false;
				}
			}
		}
		return true;
	}

//	/**
//	 * 获得将领
//	 * 
//	 * @param isAttack
//	 * @return
//	 */
//	public FightCreature getHero(List<FightCreature> list) {
//		for (FightCreature f : list) {
//			if (f.getPriority() == FightConst.CREATURE_HERO) {// 将领
//				return f;
//			}
//		}
//		return null;
//	}

	/**
	 * 移动
	 * 
	 * @param group
	 * @param type
	 *            0将战1兵战
	 */
	public static void move(FightGroup group, byte type) {

		if (type == FightConst.STATUS_HERO) {
			FightCreature f = group.getHero();
//			if (f.getPriority() == FightConst.CREATURE_HERO) {// 只检测将领
				f.setPos(f.pos()
						+ f.getMoveDistance()
						* (group.getAttackType() == FightConst.FTYPE_ATTACK ? 1
								: -1));
//			}
		} else {
			for (FightCreature f : group.getSoldier()) {
//				if (f.getPriority() == FightConst.CREATURE_HERO) {// 排除 将领
//					continue;
//				}
				f.setPos(f.pos()
						+ f.getMoveDistance()
						* (group.getAttackType() == FightConst.FTYPE_ATTACK ? 1
								: -1));
			}

		}
	}

	/**
	 * 获得两个对象间的距离
	 * 
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static int getDistance(FightCreature f1, FightCreature f2) {
		return Math.abs(f1.pos() - f2.pos());
	}

	/**
	 * 是否可以攻击
	 * 
	 * @param fc
	 * @return
	 */
	public static boolean canFight(FightCreature fc) {
		if (fc == null) {// fc=null
//			logger.info(" canFight is null,name="+fc.getName());
			return false;
		}
		if (fc.getHp() <= 0) {// 已经死亡
//			logger.info(" canFight hp<=0 ,name="+fc.getName());
			return false;
		}
		if (fc.getNum() <= 0) {
//			logger.info(" canFight num<=0,name="+fc.getName());
			return false;
		}
		if (fc.getActionNum() >= fc.getMaxActionNum()) {// 已经出过手了
//			logger.info(" canFight getActionNum<=0,name="+fc.getName());
			return false;
		}
		List<FightSpecial> list = fc.getSpecial();
		for (FightSpecial fs : list) {
			if (fs.getType() == FightSpecial.FIGHT_EFFECT_SLEEP) {
//				logger.info("当前处于麻痹状态，无法攻击,type=" + fs.getType());
				fs.effect();
				return false;
			}
		}
		return true;
	}

	/**
	 * 选择目标
	 * 
	 * @param fc
	 * @return
	 */
	public static FightCreature selectTarget(FightCreature fc,
			List<FightCreature> list) {
		FightCreature target = null;
		byte[] priority = fc.getTargetPriority();// 获得攻击方的优先攻击级别

		for (int i = 0; i < priority.length; i++) {
			target = FightUtil.getFightObj(list, priority[i]);// 获得被攻击对象
			// 被攻击对象的血量》0且在攻击距离内
			if (target == null) {
				continue;
			}
//			logger.info("选择被攻击对象="+target.getName());
			if ((target.getHp() > 0)
					&& (FightUtil.getDistance(fc, target) <= fc
							.getAttackDistance())) {
//				 logger.info("满足条件");
				return target;
			} else {
//				logger.info("不满足条件,双方距离="
//						+ FightUtil.getDistance(fc, target) + " 攻击距离="
//						+ fc.getAttackDistance() + " 被攻击方数量=" + target.getNum());
				target = null;
			}
		}
		return target;
	}

	/**
	 * 打印信息
	 * 
	 * @param list
	 */
	public static void print(List<FightCreature> list) {
		for (FightCreature fc : list) {
//			fc.log();
		}
	}

	/**
	 * 兵战伤害计算
	 * 
	 * @param attacker
	 * @param target
	 * @return
	 */
	public static int mathNum(FightCreature attacker, FightCreature target) {
		// A攻击方 B防守方
		// C(克制加成)=0.06(有克制关系则生效。无克制关系则不生效)
		// NUM(B死亡数量)=A数量*（A攻击力/(B防御力*5）*(2-B血量/28)+C）
		float c = 0;
		// 如果A克制B
		if (FightConst.RELATION[attacker.getPriority()] == target.getPriority()) {
			c = (float) 0.06;
		}
		int num = 1;
		try {
			float a = attacker.getAttack();
			float b = target.getDefence() * 5;
			float d = 2 - (float) (target.getHp() / 28.0);
			float e = a / b * d + c;
			num = (int) (attacker.getNum() * e);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		float f = num * (1 + attacker.getSoldierDeadRate() / 100);
		// 再加上技能产生的伤害比例
		num = (int) f;
		if (num <= 0) {
			num = 1;
		}
		return num;
	}

	/**
	 * 将战伤害计算
	 * 
	 * @param attacker
	 * @param target
	 * @return
	 */
	public static int mathHp(FightCreature attacker, FightCreature target) {
		// A攻击方 B防守方
		// C(基础攻击力)=（A基础攻击力+A临时基础攻击力）
		// D(攻击比例)=（A基础攻击比例加成+A临时攻击比例加成）
		// E(基础护甲)=(B基础护甲+B临时护甲)
		// F(护甲比例)=（B基础护甲加成+B临时护甲加成）
		// G（计算护甲）=E*(1+F)
		// p(临时减伤)=（B临时减伤）
		// H（减伤）=G/(G+（B等级+4）*20)+P
		// I(真实伤害)=（A真实伤害）
		// J(真实伤害比例)=(A真实伤害比例)
		// K(生命)=（B基础生命+B临时生命）
		// L(生命比例)=（B基础生命比例+B临时生命比例）
		// M(B生命上限)=k*(1+L)
		// N(基础最终伤害)=(C*(1+D)*(1-H)+I()*1+J)
		// O(临时最终伤害)=M*(对B生命上限造成的伤害)
		// 判断格挡是否产生，产生则受到1点伤害
		// 最终防守方的生命HP=M-N-O
		// 技能伤害(N+O)*(1+rate) ,只对对方产生效果，对自己没效果
		double c = attacker.getAttack();
		double d = attacker.getAttackRate();
//		logger.info("mathHp >attackRate " + attacker.getName() + "="
//				+ attacker.getAttackRate());
		double e = target.getDefence();
		double f = 0;
		double g = e * (1 + f / 100);
		double p =(double)(target.getReduceHurt())/100;
		double h = g / (g + (target.getLevel() + 4) * 20) + p;
		if(h<0){
			h=0;
		}
		// 特殊处理
		List<FightSpecial> list = attacker.getSpecial();
		for (FightSpecial fs : list) {
			if (fs.getType() == FightSpecial.FIGHT_EFFECT_NOHURT) {
				h = 0;
				fs.effect();
//				logger.info("触发无视减伤效果技能 ");
				break;
			}
		}
//		double i = attacker.getRealHurt();
		double i=0;
		double j = attacker.getRealHurtRate() +attacker.getRealHurtRatePlus();
//		logger.info("getRealHurtRate="+attacker.getRealHurtRate()+" getRealHurtRatePlus="+attacker.getRealHurtRatePlus());
//		logger.info("mathHp >realhurt " + attacker.getName() + "="
//				+ attacker.getRealHurtRate() + " b=" + target.getName() + "="
//				+ target.getRealHurtRate());
//		double k = target.getHp();
//		double l = target.getHpRate();
//		double m = k * (1 + l / 100);
		double n = (c * (1 + d / 100) * (1 - h) + i) * (1 + j / 100);
		int o = (int) (target.getMaxHp()* target.getMaxHpHurt()/100);
		// 格挡的处理暂时去掉，没有相关技能
		// if (attacker.getSpecial(FightConst.FIHGT_EFFECT_NOBLOCK) == 1) {//
		// 无视格挡
		// return m - (n + o);
		// } else {
		// // 判断是否产生格挡
		// if (target.getSpecial(FightConst.FIGHT_EFFECT_BLOCK) == 1) {
		// return m - 1;
		// }
		double m=target.getHp();
//		 logger.info("c="+c+",d="+d+",e="+e+",f="+f+",g="+g+",p="+p+",h="+h+",i="+i+",j="+j+" n="+n+",o="+o+",(m-n-o)="+(m-n-o));
		return (int) (m - (n + o));
		// }
	}

	// /**
	// * 根据士兵还是将领 获得相应list
	// *
	// * @param old
	// * @param type
	// * 0将领 1小兵
	// * @return
	// */
	// public static List<FightCreature> getCreatureList(List<FightCreature>
	// old,
	// byte type) {
	// List<FightCreature> list = new ArrayList<FightCreature>();
	// if (type == FightConst.CREATURE_HERO) {
	// list.add(getFightObj(old, FightConst.CREATURE_HERO));
	// } else {
	// for (FightCreature f : list) {
	// if (f.getPriority() != FightConst.CREATURE_HERO) {
	// list.add(f);
	// }
	// }
	// }
	// return list;
	// }
	//
	// /**
	// * 根据id获得战报事件
	// *
	// * @param id
	// */
	// public static void getFightEvent(int id) {
	// FightEvent fightEvent = DBManager.getInstance().getWorldDAO()
	// .getEvent(id);
	// String str = fightEvent.getData();
	// byte[] data = str.getBytes();
	// deserializeCmd(data);
	// }

	// /**
	// * 服务器解析战斗协议,还原战斗数据
	// *
	// * @param data1
	// */
	public static void deserializeCmd(byte[] data1) {
//		JoyBuffer out = JoyBuffer.wrap(data1);
//
//		byte type = out.get();
//		int cmdNum = out.getInt();
//		byte round = out.get();
//		logger.info("deserializeCmd , type=" + type + " cmdNum=" + cmdNum
//				+ " round=" + round);
//		for (int i = 0; i < cmdNum; i++) {
//			byte cmdType = out.get();
//			logger.info("cmd=" + cmdType);
//			switch (cmdType) {
//			case FightConst.FIGHT_INIT:
//				logger.info("init..........");
//				byte t = out.get();
//				byte num = out.get();
//				for (int j = 0; j < num; j++) {
//					byte htype = out.get();
//					switch (htype) {
//					case FightConst.CREATURE_HERO:
//						FightPlayer player = new FightPlayer(null, null);
//						player.in(out);
//						break;
//					case FightConst.CREATURE_MONSTER:
//						FightMonster monster = new FightMonster(null);
//						monster.in(out);
//						break;
//					default:
//						FightSoldier soldier = new FightSoldier(null);
//						soldier.in(out);
//						break;
//					}
//				}
//				break;
//			case FightConst.FIGHT_MOVE:
//				logger.info("move..........");
//				break;
//			case FightConst.FIGHT_ATTACK:
//				logger.info("attack..........");
//				byte attackType = out.get();
//				int id1 = out.getInt();
//				int id2 = out.getInt();
//				int hp = out.getInt();
//				out.get();
//				out.get();
//				break;
//			case FightConst.FIGHT_END:
//				logger.info("end..........");
//				// // 攻守方类别
//				byte _t = out.get();
//				// 是否是获胜方 ，1获胜 0失败
//				out.get();
//				// 玩家类别
//				byte at = out.get();
//				if (at == FightConst.CREATURE_HERO) {
//					// 玩家经验，功勋 ,金钱
//					int a = out.getInt();
//					int b = out.getInt();
//					int c = out.getInt();
//					// 将领的经验，级别
//					out.getInt();
//					out.getInt();
//					byte goodsNum = out.get();
//					for (int j = 0; j < goodsNum; j++) {
//
//					}
//				} else {
//					out.getInt();
//					out.getInt();
//					out.getInt();
//					out.getInt();
//					out.getInt();
//				}
//
//				// // //每一个兵种的损失
//				byte size = out.get();
//				for (int j = 0; j < size; j++) {
//					int id = out.getInt();
//					int loseNum = out.getInt();
//					int recoverNum = out.getInt();
//				}
//				break;
//			case FightConst.FIGHT_SKILL:
//				logger.info("skill..........");
//				attackType = out.get();
//				out.getInt();
//				out.getInt();
//				out.get();
//				out.get();
//				out.get();
//				int n = out.getInt();
//				for (int j = 0; j < n; j++) {
//					int hurt = out.getInt();
//				}
//				break;
//			case FightConst.FIGHT_ROUND:
//				logger.info("round..........");
//				byte r = out.get();
//				logger.info("round=" + r);
//				break;
//			}
//		}
	}

	/**
	 * 根据战斗类型获得战斗奖励
	 * 
	 * @param fc
	 * @param type
	 */
	public static String getFightResult(FightGroup group, byte type,byte point,FightGroup group2) {
		StringBuilder sb=new StringBuilder();
		sb.append("getFightResult\n");
		FightReward fr = GameDataManager.fightRewardManager.getFightReward(
				type,point);
		sb.append("战斗类型="+group.getAttackType()+"战斗结果="+group.getWinType()+" 获得战斗奖励\n");
		if (fr == null) {
			sb.append("战斗奖励错误，type=" + type + " point=" + point);
//			logger.info("不存在战斗奖励，type=" + type + " point=" + point);
			return sb.toString();
		}
		FightCreature fc = group.getHero();
		if (fc == null) {
			sb.append("战斗奖励错误，hero=null");
//			logger.info("不存在战斗奖励，hero=null");
			return sb.toString();
		}
		int recover=0;
		int  deadNum=0;
		if(group.getWinType()==FightConst.FIGHT_LOSE){
			recover=fr.getWinrecover();
		}else{
			recover=fr.getRecover();
		}
		sb.append("伤兵治愈=").append(String.valueOf(recover)).append("\n");
//
//战斗结束
//
//1、伤兵数量=初始携带数量-战斗后剩余数量（如果为负值则置为0）
//
//2、治愈数量=伤兵数量*治愈比例（最小为1）
//
//3、治愈后数量=战斗后剩余数量+治愈数量（若大于携带数量，置为携带数量）

		// 无论胜负,都会获得相应的获得恢复
//		int totalLose=0;//总的损失士兵的数量
		for (FightCreature f : group.getSoldier()) {
			FightSoldier fs=(FightSoldier)f;
			int initNum=fs.getInitNum();//士兵初始数量
			int nowNum=f.getNum();//士兵当前剩余数量
			if(nowNum<=0){
				nowNum=0;
			}
			int loseNum=initNum-nowNum;//士兵损失数量
			if(loseNum<0){
				loseNum=0;
			}
			int recoverNum=Math.abs( loseNum * recover/ 100);
			int finalNum=nowNum+recoverNum;//最终数量
			if(finalNum>initNum){
				finalNum=initNum;
			}
			if(type == FightConst.FIGHTBATTLE_ARENA||type==FightConst.FIGHTBATTLE_CITY||
					type == FightConst.FIGHTBATTLE_STATE_CAMP||type==FightConst.FIGHTBATTLE_COUNTRY_CAMP||
					type == FightConst.FIGHTBATTLE_CITY_CAMP
					){
				if(finalNum<=0){
					finalNum=1;
				}
			}
			int eNum=fs.getEquipNum();//兵装总数
			int rate=finalNum*100/initNum;//百分比
			int left=eNum*rate/100;//剩余的兵装数
			fs.setEquipNum(left);
			f.setNum(finalNum);
			f.setRecover(recoverNum);
			f.setLoseNum(loseNum);
			sb.append("士兵处理,name="+f.getName()+" initNum="+initNum+" nowNum="+nowNum+" loseNum="+loseNum+" 治愈比例="+recover+" finalNum="+finalNum).append("\n");
			sb.append("兵装处理，士兵损失比例="+rate+" 兵装总数="+eNum+" 剩余兵装数="+left).append("\n");
//			totalLose+=f.getLoseNum();
		}
		sb.append("总的死亡数量="+deadNum).append("\n");
		if(group.getWinType()!=FightConst.FIGHT_WIN||group.getAttackType()!=FightConst.FTYPE_ATTACK){
			sb.append("非攻击方或者战斗未胜利，不获得奖励").append("\n");
			return sb.toString();
		}
		PlayerCharacter pc = group.getPc();
		if(pc==null){
			sb.append("非玩家不获得经验和物品");
			return sb.toString();
		}
		// 获得经验
		int exp = fr.getExp();
		if(exp<=0){
			if(exp==-2){
//				获得经验=杀兵数*0.2*（将领等级/10）向上取整
				int totalLose=0;//总的损失士兵的数量
				for (FightCreature f : group2.getSoldier()) {
					FightSoldier fs=(FightSoldier)f;
					int initNum=fs.getInitNum();//士兵初始数量
					int nowNum=f.getNum();//士兵当前剩余数量
					if(nowNum<=0){
						nowNum=0;
					}
					int loseNum=initNum-nowNum;//士兵损失数量
					if(loseNum<0){
						loseNum=0;
					}
					totalLose+=loseNum;
				}
				float n=totalLose*2*group.getHero().getLevel()/100;
				exp=(int)Math.floor(n);
			}else{
				exp=0;
			}
		}
		int addExp=((FightPlayer) fc).getHero().getExpAdd();
		sb.append(" 经验加成比例:"+addExp+" 初始经验:"+exp);
		exp+=exp*addExp/100;
		((FightPlayer) fc).getHero().addExp(exp);
		sb.append(" 获得经验:"+exp);
		// 获得金钱
		int money = fr.getMoney();
		if(money<=0){
//-1：= 2*(武将等级+5)^2
//-2：= 3*(武将等级+5)^2
//-3：= 4*(武将等级+5)^2
			if(money==-1){
				money=(int) (2*Math.pow((group.getHero().getLevel()+5), 2));
			}else if(money==-2){
				money=(int) (3*Math.pow((group.getHero().getLevel()+5), 2));
			}else if(money==-3){
				money=(int) (4*Math.pow((group.getHero().getLevel()+5), 2));
			}else{
				money=0;
			}
		}
		pc.saveResources(GameConfig.GAME_MONEY, money);
		sb.append(" 获得金钱:"+money);
		// 功勋
		int award = fr.getAward();
		pc.saveResources(GameConfig.AWARD, award);
		sb.append(" 获得功勋:"+award).append("\n");
		// 成就
		int[] data=new int[]{money,award};
		group.setReward(data);
		// 获得装备
		List<Cell> _l = new ArrayList<Cell>();
		int random = MathUtils.random(10000);
		sb.append("产生随机数:").append(String.valueOf(random)).append(" 触发几率:").append(fr.getRate());
		if (random < fr.getRate()) {
			if(fr.getEquip()!=0){
				Equipment eq = EquipmentManager.getInstance().getEqu(fr.getEquip(),
						fr.getQuality(), 0);
				_l = group.getPc().getPlayerStorageAgent()
						.addEquis(eq, 1);
			}
		}
		for (Cell cell : _l) {
			group.addGoods(cell);
			sb.append(" 获得装备:").append(cell.toString());
		}

		// 获得物品
		int[] rate = getResultRate(fr)[0];
		int[] prop = getResultRate(fr)[1];

		for (int j = 0; j < rate.length; j++) {// 随机5次
			random = MathUtils.random(10000);
			if (random <= rate[j]) {
				int propId = prop[j];
				if(propId!=0){
					Cell cell = pc.getPlayerStorageAgent().addPropsCell(propId, 1);
					sb.append(" 获得物品:").append(cell.toString());
					group.addGoods(cell);

				}
			}
		}
		return sb.toString();

	}

	/**
	 * 获得战斗结果
	 * 
	 * @param fr
	 * @return
	 */
	public static int[][] getResultRate(FightReward fr) {
		int data[][] = new int[2][];// 【0】rate，【1】prop
		data[0] = new int[5];
		data[0][0] = fr.getRate1();
		data[0][1] = fr.getRate2();
		data[0][2] = fr.getRate3();
		data[1] = new int[5];
		data[1][0] = fr.getPropId1();
		data[1][1] = fr.getPropId2();
		data[1][2] = fr.getPropId3();
		return data;
	}

	/**
	 * 序列化战斗属性 //类型 byte //id int //name string //num int//hp int
	 * 
	 * @param f
	 */
	public static void creatureOut(FightCreature f, JoyBuffer out) {
		if (f instanceof FightPlayer) {
			FightPlayer player = (FightPlayer) f;
			player.out(out);
		} else if (f instanceof FightMonster) {
			FightMonster monster = (FightMonster) f;
			monster.out(out);
		} else if (f instanceof FightSoldier) {
			FightSoldier soldier = (FightSoldier) f;
			soldier.out(out);
		} else {
//			logger.info("creatureOut error");
		}
	}

//	/**
//	 * 获得触发点的技能
//	 * 
//	 * @param ids
//	 * @param id
//	 *            触发点类型
//	 * @return
//	 */
//	public static List<Skill> getSkill(int[] ids, byte id) {
//		List<Skill> list = FightUtil.getSkillList(ids, id);
//		if (id == FightConst.FIGHT_TRIGGER0) {// 被动技能返回全部
//			return list;
//		}
//		int newId[] = new int[list.size()];
//		int newRate[] = new int[list.size()];
//		int i = 0;
//		for (Skill s : list) {
//			newId[i] = s.getId();
//			newRate[i] = s.getRate();
//			i++;
//		}
//		// 获得随机技能
//		// list.clear();
//		// int sid = MathUtils.getRandomId(newId, newRate, 100);
//		// Skill skill=GameDataManager.skillManager.getSKill(sid);
//		// if(skill!=null){
//		// list.add(skill);
//		// }
//		return list;
//	}

	/**
	 * 获得该触发点下的将领技能
	 * 
	 * @param ids
	 * @param id
	 *            -1代表全部
	 * @return
	 */
	public static List<Skill> getSkillList(int[] ids, byte id) {
		Skill sk = null;
		List<Skill> list = new ArrayList<Skill>();
		for (int i = 0; i < ids.length; i++) {// 找到该出发点的技能
			sk = GameDataManager.skillManager.getSKill(ids[i]);
			if (sk == null) {
				continue;
			}
			if (id != -1) {
				if (sk.getTrigger() == id) {// 加入符合规则的技能id
					list.add(sk);
				}
			} else {
				list.add(sk);
			}

		}
		return list;
	}

	/**
	 * 解析士兵字符串 1:2,0;2:3,1
	 * 
	 * @param str
	 * @return
	 */
	public static List<Soldier> getSoldierFormStr(String str) {
		List<Soldier> list = new ArrayList<Soldier>();
//		logger.info("=====战斗开始 ，addSoldier，str="+str);
		// 解析字符串，构造相应的战斗对象，
		if (str == null || str.equals("")) {
//			logger.info("战斗士兵解析错误,未带士兵");
			return null;
		}
		String s[] = null;
		try {
			s = str.split(";");
		} catch (Exception ex) {
//			logger.info("战斗士兵解析错误，请检查格式");
			return null;
		}
		for (int i = 0; i < s.length; i++) {
			String[] ss = s[i].split(":");
			int id = Integer.parseInt(ss[0]);// id
			String _temp = ss[1];// num,isEquip
			String _temp2[] = _temp.split(",");
			int num = Integer.parseInt(_temp2[0]);// num
			int equipNum = Integer.parseInt(_temp2[1]);
			Soldier soldier = GameDataManager.soldierManager.getSoldier(id);
			if (soldier == null) {
//				logger.info("加入的士兵=null,id=" + id);
				break;
			}
			if(num<=0){
//				logger.info("加入的士兵num<=0,id=" + id);
				continue;
			}
			soldier.setEquipNum(equipNum);
			soldier.setNum(num);
//			logger.info("name=" + soldier.getName() + " 数量="
//					+ soldier.getNum() + " 兵装数=" + soldier.getEquipNum());
			
			list.add(soldier);
		}
		return list;
	}

	/**
	 * 将士兵转化为字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String changeSoldierToStr(List<FightCreature> list) {
		String str = "";
		String array[] = new String[list.size()];
		int i = 0;
		for (FightCreature fc : list) {
			FightSoldier soldier = (FightSoldier) fc;
			array[i] = soldier.getId() + ":" + soldier.getNum() + ","
					+ soldier.getEquipNum();
			i++;
		}
		str = StringUtils.recoverNewStr(array, ";");
		return str;
	}

	/**
	 * 获得士兵总数
	 * 
	 * @param str
	 * @return
	 */
	public static int getSoldierNum(String str) {
//		logger.info("获得士兵总数："+str);
		List<Soldier> list = getSoldierFormStr(str);
		if(list==null){
			return 0;
		}
		int num = 0;
		for (Soldier s : list) {
			num += s.getNum();
		}
		return num;
	}

	/**
	 * 获得所拥有的技能中指定属性的数量
	 * 
	 * @param type
	 * @param list
	 * @return
	 */
	public static int getAttri(byte type, List<FightSkill> list) {
		int num = 0;
		for (FightSkill skill : list) {
			Skill sk = skill.getSkill();
			if (sk.getAttri() == type) {
				num++;
			}
		}
		return num;
	}

	/**
	 * 
	 * @param attack
	 * @param target
	 * @param hp
	 *            剩余血量
	 * @return
	 */
	public static boolean checkDead(FightCreature attack, FightCreature target,
			int hp) {
		boolean isHeavyHurt = false;// 是否重伤
		int preHp = (int) target.getHp();
		int hurt = preHp - hp;
		if(hp<0){
			hp=0;
		}
//		target.addLose(hp);
		target.setHp(hp);
		if (target.getHp() == 0) {// 被攻击方死亡
			// 重伤几率=3*最后一击伤血/被攻击将领生命上限"+攻守方双的技能重伤几率之和
			// System.out.println("id="+target.getId()+"hp=="+hp);
			int r =(int)( 3 * hurt * 100 / target.getMaxHp() + attack.getHardHurt()+ target.getBeHardHurt());
			int random = MathUtils.random(100);
//			logger.info("目标死亡,重击检测 r=" + r + " random=" + random + " hurt=" + hurt
//					+ " maxHp=" + target.getMaxHp() + " ahurt="
//					+ attack.getHardHurt() + " bhurt="
//					+ target.getBeHardHurt());
			if (random < r) {
				isHeavyHurt = true;
//				logger.info("产生重击");
			}else{
//				logger.info("未产生重击");
			}
		}
		return isHeavyHurt;
	}

	// 是否学习了五系技能
	public static boolean allLearn(List<FightSkill> list) {
		boolean b[] = new boolean[] { false, false, false, false, false };
		for (FightSkill skill : list) {
			Skill sk = skill.getSkill();
			if (sk == null) {
				continue;
			}
			int index = sk.getAttri();
			if(index>b.length-1){
				continue;
			}
			b[index] = true;
		}
		for (int i = 0; i < b.length; i++) {
//			logger.info("学习的技能的五系=" + b[i]);
		}
		for (int i = 0; i < b.length; i++) {
			if (!b[i]) {
				return false;
			}
		}
		return true;
	}

	

//	/**
//	 * 玩家战斗的初始化
//	 * 
//	 * @param group
//	 * @param userId
//	 * @param heroId
//	 * @param soldierStr
//	 */
//	public static boolean init(FightGroup group, PlayerCharacter player,
//			FightCreature hero, String soldierStr, byte type) {
//		try {
//			group.setPc(player);
//			group.addHero(hero);
//			if (!group.addSoldier(soldierStr)) {
//				group.getTip().setMessage("加入士兵错误");
//				return false;
//			}
////			if (!group.init()) {
////				group.getTip().setMessage("group 初始化失败,type="+group.getType());
////				return false;
////			}
//		} catch (Exception ex) {
//			group.getTip().setMessage(ex.toString());
//			logger.error("FightGroup init error");
//		}
//		return true;
//	}

	/**
	 * 重置本回合数据
	 */
	public static void resetRoundData(FightGroup attacker, FightGroup defencer) {
		attacker.resetRoundData();
		defencer.resetRoundData();
	}

//	/**
//	 * 增加奖励物品
//	 * 
//	 * @param group
//	 * @param list
//	 */
//	public static void addGoods(FightGroup group, List<Cell> list) {
//		if(list==null){
//			return;
//		}
//		for (Cell cell : list) {
//			group.addGoods(cell);
//		}
//	}
//	/**
//	 * 获得奖励
//	 */
//	public static void getReward(FightGroup group,byte type,byte point) {
//		if (group.getType() != FightConst.CREATURE_HERO) {// 只有真实的玩家才会获得奖励
//			return;
//		}
//		// 获得通用的战斗奖励
//		FightUtil.getFightResult(group, type, point);
//	}
	/**
	 * 判断技能类型
	 * @param sk
	 * @param type
	 * @return
	 */
	public static boolean  checkByType(Skill sk,int type[]){
		if(type==null){
			return false;
		}
		for(int i=0;i<type.length;i++){
			if(sk.getSkillType()==type[i]){
				return true;
			}
		}
		return false;
	}
}
