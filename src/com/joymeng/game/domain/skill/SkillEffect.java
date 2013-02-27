package com.joymeng.game.domain.skill;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.MathUtils;
import com.joymeng.game.domain.fight.FightCmd;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightSpecial;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.battle.FightBattleBase;
import com.joymeng.game.domain.fight.obj.FightCreature;
import com.joymeng.game.domain.fight.obj.FightGroup;
import com.joymeng.game.domain.fight.obj.FightSkill;

/**
 * 
 * @author admin
 * @date 2012-5-10 技能效果
 */
public class SkillEffect {
	static final Logger logger = LoggerFactory.getLogger(SkillEffect.class);

	// Skill 名称 动画 类型 效果
	// id name ani type
	// 1510 重击 39 5 1
	// 1810 刺刃 40 8 2
	// 2110 以力破巧 40 11 3
	// 2210 割裂 40 12 4
	// 2310 熔化 40 13 5
	// 5010 雷之心 27 40 6
	// 5110 风之心 28 41 7
	// 5210 水之心 24 42 8
	// 5310 火之心 41 43 9
	// 5410 土之心 28 44 10
	// 5510 五系之心 29 45 11

	public static List<FightSkill> use(FightGroup a, FightGroup b,
			FightBattleBase fb, byte trigger) {
		List<FightSkill> returnList = new ArrayList<FightSkill>();
		List<FightSkill> list1 = a.getSkillList(trigger);
//		logger.info("\n =========================触发点=" + trigger
//				+ "===================== round=" + fb.getRound());
		if (list1.size() != 0) {
//			logger.info("\n  攻击方触发技能 ，攻守双方为=[" + a.getHero().getName() + "==>"
//					+ b.getHero().getName() + "] attackType="+a.getAttackType());
		}
		returnList = effect(a, b, fb, FightConst.FTYPE_ATTACK, list1);
		List<FightSkill> list2 = b.getSkillList(trigger);
		if (list2.size() != 0) {
//			logger.info("\n  防御方触发技能，攻守双方为=[" + a.getHero().getName() + "==>"
//					+ b.getHero().getName() + "] attackType="+b.getAttackType());
		}
		effect(b, a, fb, FightConst.FTYPE_DEFENCE, list2);
		return returnList;
	}

	/**
	 * 效果作用
	 * 
	 * @param a
	 * @param b
	 * @param fb
	 * @param type
	 *            FightConst.FTYPE_ATTACK攻击方，FightConst.FTYPE_DEFENCE 防守方
	 */
	public static List<FightSkill> effect(FightGroup a, FightGroup b,
			FightBattleBase fb, byte ftype, List<FightSkill> list) {
		List<FightSkill> returnList = new ArrayList<FightSkill>();
		if (list.size() == 0) {
//			logger.info("fightskill size =0 ,不处理技能");
			return returnList;
		}
		FightCreature hero = a.getHero();
		if(hero.isDead()){
//			logger.info("攻击方已经死亡，不处理技能");
			return returnList;
		}
		for (FightSkill skill : list) {
			Skill sk = skill.getSkill();
			if (sk == null) {
				continue;
			}
//			logger.info("\n " + hero.getName() + "======处理技能,id=" + sk.getId()
//					+ ",name=" + sk.getName() + "------------");
			switch (sk.getBackup2()) {
			case FightConst.SKILL_TYPE1:// 兵战
				returnList = soldierSkill(a, b, ftype, fb, skill);
				break;
			case FightConst.SKILL_TYPE3:// 重击
				int num = sk.getSeriousInjury();
//				logger.info("重击处理，num=" + num + " sk.getType()="
//						+ (sk.getType() == 0 ? "自己" : "对方"));
				// 取得作用对象
				if (sk.getType() == 0) {// 自己
					if (num > 0) {
						hero.setHardHurt(hero.getHardHurt() + num);

					} else {
						hero.setBeHardHurt(hero.getBeHardHurt() + num);
					}

				} else if (sk.getType() == 1) {// 敌人
					if (num > 0) {
						b.getHero().setBeHardHurt(
								b.getHero().getBeHardHurt() + num);
					} else {
						b.getHero()
								.setHardHurt(b.getHero().getHardHurt() + num);
					}
				}
//				logger.info("attack=" + hero.getName() + " 重伤几率="
//						+ hero.getHardHurt() + " 被重伤几率=" + hero.getBeHardHurt());
//				logger.info("defence=" + b.getHero().getName() + " 重伤几率="
//						+ b.getHero().getHardHurt() + " 被重伤几率="
//						+ b.getHero().getBeHardHurt());

				break;
			case FightConst.SKILL_TYPE4:// buf状态
				returnList = buffSkill(a, b, fb, ftype, skill);
				break;
			case FightConst.SKILL_TYPE5:// 特殊技能
				returnList = specialSkill(a, b, fb, ftype, skill);
				break;
			case FightConst.SKILL_TYPE6:// 普通技能
				returnList = normalSkill(a, b, fb, ftype, skill);
				break;
			default:
			case FightConst.SKILL_TYPE7:
			case FightConst.SKILL_TYPE0:
			case FightConst.SKILL_TYPE2:  
				// logger.info("未知战斗类型,id=" + skill.getId() + " getBackup2="
				// + sk.getBackup2());
				break;
			}
		}
		return returnList;
	}

	/**
	 * 普通技能
	 * 
	 * @param a
	 *            对象a
	 * @param b
	 *            对象b
	 * @param fb
	 *            战场对象
	 * @param ftype
	 *            a是攻击方还是防守方
	 * @param skill
	 *            a 触发的技能
	 */
	public static List<FightSkill> normalSkill(FightGroup a, FightGroup b,
			FightBattleBase fb, byte ftype, FightSkill skill) {
		Skill sk = skill.getSkill();
//		logger.info("触发普通技能 skillId=" + skill.getId() + " name="
//				+ sk.getName() );
		List<FightSkill> returnList = new ArrayList<FightSkill>();
		if (ftype != FightConst.FTYPE_ATTACK) {// 攻击方触发●
//			logger.info("name=" + a.getHero().getName()
//					+ " 非攻击方不触发 normalSkill");
			return returnList;
		}
		byte type = sk.getType();// 作用对象
		int sid = sk.getSkillType();// 技能类型
		int num = 0;
		FightCreature hero = a.getHero();
		FightCmd cmd = fb.getCmd();
		switch (sid) {
		case FightConst.SID_5:// 攻击方触发●影响 RealHurtRate
			num = sk.getRealHurtRate();
			if (type == 0) {
				a.getHero()
						.setRealHurtRatePlus(num);
				a.getHero().addEffect(FightConst.EFFECT_1);
//				logger.info("攻击方,产生暴击，realHurtRate="
//						+ a.getHero().getRealHurtRate());
				cmd.skillCmd(hero, sk.getId(), false, (byte) 0, new int[0]);
			} else {
//				logger.info("skill effect error! id=" + sid);
			}
			break;

		case FightConst.SID_12:// 割裂 武将击中对方时，有几率忽视对方减伤比30%
			num = sk.getReduceHurt();
//			logger.info("防御方,初始ReduceHurt=" + b.getHero().getReduceHurt(),
//					" RealHrtRate=" + b.getHero().getRealHurtRate());
			b.getHero().setReduceHurt(b.getHero().getReduceHurt() + num);
			a.getHero().setRealHurtRatePlus(
					a.getHero().getRealHurtRate() + skill.getRealHurtRate());
//			logger.info("防御方,被割裂后ReduceHurt=" + b.getHero().getReduceHurt()
//					+ " RealHurtRate=" + b.getHero().getRealHurtRate());
			cmd.skillCmd(hero, sk.getId(), false, (byte) 0, new int[0]);
			a.getHero().addEffect(FightConst.EFFECT_4);
			break;
		case FightConst.SID_10:
		case FightConst.SID_14:// 攻击方触发●影响 getAttackRate
		case FightConst.SID_8:
			num = sk.getAttackRate();
			if (type == 0) {
				hero.setAttackRate(hero.getAttackRate() + num);
				a.getHero()
						.setRealHurtRatePlus(
								a.getHero().getRealHurtRate()
										+ skill.getRealHurtRate());
//				logger.info("攻击方,attackRate=" + hero.getAttackRate());
				cmd.skillCmd(hero, sk.getId(), false, (byte) 0, new int[0]);
			} else {
//				logger.info("skill effect error! id=" + sid);
			}
			break;
//		case FightConst.SID_8:
//			b.getHero().setReduceHurt(b.getHero().getReduceHurt()+sk.getReduceHurt());
//			a.getHero().addEffect(FightConst.EFFECT_2);
//			cmd.skillCmd(hero, sk.getId(), false, (byte) 0, new int[0]);
//			break;
		}
		return returnList;
	}

	/**
	 * buf技能
	 * 
	 * @param a
	 *            对象a
	 * @param b
	 *            对象b
	 * @param fb
	 *            战场对象
	 * @param ftype
	 *            a是攻击方还是防守方
	 * @param skill
	 *            a 触发的技能
	 */
	public static List<FightSkill> buffSkill(FightGroup a, FightGroup b,
			FightBattleBase fb, byte ftype, FightSkill skill) {
		List<FightSkill> returnList = new ArrayList<FightSkill>();
		Skill sk = skill.getSkill();
//		logger.info("触发buffer效果  skillId=" + skill.getId() + " name="
//				+ sk.getName());
		if (ftype != FightConst.FTYPE_ATTACK) {// 攻击方触发●
//			logger.info(" 非攻击方不触发 ");
			return returnList;
		}
		FightSpecial fs = null;
		int sid = sk.getSkillType();// 技能类型
		FightCmd cmd = fb.getCmd();
		switch (sid) {
		case FightConst.SID_40:// 击中对方后，有2%的几率使对方麻痹，麻痹后下回合不能行动 ，本技能几率=原触发几率*雷系技能数量*0.2
			fs = new FightSpecial(FightSpecial.FIGHT_EFFECT_SLEEP,
					sk.getRound());
			b.getHero().addSpecial(fs);
			cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0, new int[0]);
			a.getHero().addEffect(FightConst.EFFECT_6);
//			logger.info("增加麻痹效果");
			break;
//		case FightConst.SID_43:// 击中对方后，有3%的几率使对方灼烧，每回合减少4%生命上限气血，持续3回合，每个火系技能可以使火之心发挥出20%的效果
////			fs = new FightSpecial(FightSpecial.FIGHT_EFFECT_BURN, sk.getRound());
////			fs.setP1(sk.getHpRate());
////			b.getHero().addSpecial(fs);
//			int hurt[] = new int[sk.getRound()];
//			int n = (int) (b.getHero().getMaxHp() * sk.getHpRate() / 100);
//			for (int i = 0; i < hurt.length; i++) {
//				hurt[i] = n;
//			}
//			logger.info("增加灼烧效果，玩家最大血量=" + b.getHero().getMaxHp() + " 减少比例="
//					+ sk.getHpRate() + " 减少量=" + n);
//			cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0, hurt);
//			a.getHero().addEffect(FightConst.EFFECT_9);
//			break;
		}
		return returnList;
	}

	/**
	 * 特殊技能
	 * 
	 * @param a
	 *            对象a
	 * @param b
	 *            对象b
	 * @param fb
	 *            战场对象
	 * @param ftype
	 *            a是攻击方还是防守方
	 * @param skill
	 *            a 触发的技能
	 */
	public static List<FightSkill> specialSkill(FightGroup a, FightGroup b,
			FightBattleBase fb, byte ftype, FightSkill skill) {
		List<FightSkill> returnList = new ArrayList<FightSkill>();
		Skill sk = skill.getSkill();
//		logger.info("触发特殊技能   skillId=" + skill.getId() + " name="
//				+ sk.getName());
		int sid = sk.getSkillType();// 技能类型
		FightCmd cmd = fb.getCmd();
		FightCreature hero = a.getHero();
		FightSpecial fs = null;
		int effect[] = new int[10];
		int num = 0;
		switch (sid) {
		case FightConst.SID_11:// 武将击中对方时，有几率产生对方10%生命上限的额外伤害
		case FightConst.SID_43:
			if (ftype != FightConst.FTYPE_ATTACK) {// 攻击方触发
//				logger.info("非攻击方不触发");
				break;
			}
			b.getHero().setMaxHpHurt(sk.getHpRate());
			a.getHero().setRealHurtRatePlus(
					a.getHero().getRealHurtRate() + skill.getRealHurtRate());
			cmd.skillCmd(hero, sk.getId(), false, (byte) 0, new int[0]);
			a.getHero().addEffect(FightConst.EFFECT_3);
			break;
		case 1:// 击中敌人时，如果敌人护甲值高于自己，有5%的几率交换双方护甲值
			if (ftype != FightConst.FTYPE_ATTACK) {// 攻击方触发●
//				logger.info("非攻击方不触发");
				break;
			}
			float def1 = hero.getDefence();
			float def2 = b.getHero().getDefence();
			if (def2 > def1) {
//				logger.info("敌方护甲高于自己,def1=" + def1 + " def2=" + def2);
//				logger.info("双方护甲交换");
				hero.setDefence((int) def2);
				b.getHero().setDefence((int) def1);
				cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0,
						new int[0]);
			} else {
//				logger.info("敌方护甲不高于自己,不触发技能,def1=" + def1 + " def2=" + def2);
			}
			break;
		case 45:// 被击中后，将受到伤害的4%反弹给对方。学全了五系技能后才能生效
			if (ftype != FightConst.FTYPE_DEFENCE) {// 被攻击方触发●
//				logger.info("非防御方不触发");
				break;
			}
			// 检测是否学过5系技能
			if (FightUtil.allLearn(a.getSkillList())) {
				// if (MathUtils.random(100) <= rate) {
//				logger.info("触发反弹技能>>>");
				fs = new FightSpecial(FightSpecial.FIGHT_EFFECT_BOUNCE,
						(byte) 1);
				fs.setP1(sk.getHurtRebound());
				fs.setP2(sk.getId());
				b.getHero().addSpecial(fs);
				b.getHero().addEffect(FightConst.EFFECT_11);
				// }
			} else {
//				logger.info("未学习五系技能不触发该技能");
			}
			break;
		case 6:// 将战必然先手攻击，第一击造成的伤害为正常伤害的50%
//			logger.info("触发克敌先机技能>>>");
			fs = new FightSpecial(FightSpecial.FIGHT_EFFECT_FIRST, (byte) 1);
			fs.setP1(sk.getSkillProbability());
			hero.addSpecial(fs);
			cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0, new int[0]);
			break;
		case 36:// 蛊惑,20%减少敌方所有部队1%的数量，我方随机一支部队增加1%的士兵
			num = a.getTotalSoldierNum();
			if (ftype != FightConst.FTYPE_ATTACK) {// 攻击方触发●
//				logger.info("非攻击方不触发");
				break;
			}
//			logger.info("我方士兵=" + FightUtil.changeSoldierToStr(a.getSoldier())
//					+ " 敌方士兵=" + FightUtil.changeSoldierToStr(b.getSoldier()));
			List<FightCreature> sl = b.getSoldier();
			int sNum = 0;// 敌方部队剩余类型数
			for (FightCreature fc : sl) {
				if (fc.getNum() > 0) {
					sNum++;
				}
			}

			if (sNum != 0) {
				for (FightCreature fc : sl) {
					int reduce = num * (sk.getSkillProbability()) / 100;
					// reduce+=reduce*(p3+p4)/100;
					int nowNum = fc.getNum();
					reduce += reduce * (skill.getHurt() + fc.getReduceHurt())
							/ 100;
					reduce = reduce / sNum;
					int result = nowNum - reduce;
					if (result < 0) {
						result = 0;
						reduce = nowNum;
					}
					fc.setNum(result);
					effect[fc.getPriority()] = -reduce;
				}
			}

			sl = a.getLiveSoldier();
			int rr = MathUtils.random(sl.size());
			FightCreature fc = sl.get(rr);
			if (fc != null) {
				int nowNum = fc.getNum();
				int add = num * (sk.getSkillProbability()) / 100;
				nowNum += add;
				fc.setNum(nowNum);
				effect[5 + fc.getPriority()] = add;
			}
			cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0, effect);
			// }
			break;
		case 44:// 被击中后，有3%的几率格挡，只受到1点伤害，每个土系技能可以使土之心发挥出20%的效果
			if (ftype != FightConst.FTYPE_DEFENCE) {// 被攻击方触发●
				break;
			}
//			logger.info("触发格挡技能>>>");
			hero.addSpecial(new FightSpecial(FightSpecial.FIHGT_EFFECT_BLOCK,
					(byte) 1));
			cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0, new int[0]);
			b.getHero().addEffect(FightConst.EFFECT_10);
			break;
		case 41:// 有2%的几率闪避对方的普通攻击和技能攻击，每个风系技能可以使风之心发挥出20%的效果
			if (ftype != FightConst.FTYPE_DEFENCE) {// 被攻击方触发●
				break;
			}
//			logger.info("触发闪避技能>>>");
			hero.addSpecial(new FightSpecial(FightSpecial.FIHGT_EFFECT_DODGE,
					(byte) 1));
			cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0, new int[0]);
			b.getHero().addEffect(FightConst.EFFECT_7);
			break;
		case 42:// 被击中后，有2%的几率恢复5%生命上限的生命，每个水系技能可以使水之心发挥出20%的效果
			if (ftype != FightConst.FTYPE_DEFENCE) {// 被攻击方触发●
				break;
			}
//			logger.info("触发恢复生命技能>>>");
			fs = new FightSpecial(FightSpecial.FIGHT_EFFECT_RESUMEHP, (byte) 1);
			fs.setP1(sk.getHpRate());
			fs.setP2(sk.getId());
			hero.addSpecial(fs);
			b.getHero().addEffect(FightConst.EFFECT_8);
			break;
		case 13:// 融化,无视减伤
			if (ftype != FightConst.FTYPE_ATTACK) {// 攻击方触发●
				break;
			}
			a.getHero().addEffect(FightConst.EFFECT_5);
//			logger.info("触发融化技能,无视减伤效果增加>>>");
			fs = new FightSpecial(FightSpecial.FIGHT_EFFECT_NOHURT, (byte) 1);
			hero.addSpecial(fs);
			cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0, new int[0]);
			break;
		}
		return returnList;
	}

	/**
	 * 兵战技能
	 * 
	 * @param a
	 *            对象a
	 * @param b
	 *            对象b
	 * @param fb
	 *            战场对象
	 * @param ftype
	 *            a是攻击方还是防守方
	 * @param skill
	 *            a 触发的技能
	 */
	public static List<FightSkill> soldierSkill(FightGroup a, FightGroup b,
			byte ftype, FightBattleBase fb, FightSkill skill) {
		List<FightSkill> returnList = new ArrayList<FightSkill>();
		Skill sk = skill.getSkill();
		byte type = sk.getType();// 作用对象
		int soldierType = sk.getBackup3();// 影响的兵种类型=0表示全部兵种
		FightCmd cmd = fb.getCmd();
		StringBuilder sb=new StringBuilder();
		sb.append("触发兵战技能,skillId=").append(sk.getId()).append(" name=").append(sk.getName());
		switch (sk.getBackup4()) {
		case FightConst.SOLDIER_TYPE1:// 被动
		case FightConst.SOLDIER_TYPE6:// 被动
			sb.append(" 被动技能,不下发客户端");
			for (FightCreature soldier : a.getSoldier()) {
				if (soldierType == 0
						|| (soldier.getPriority() == soldierType && soldierType != 0)) {
					effectSoldier(soldier, sk);
				}
			}
			break;
		case FightConst.SOLDIER_TYPE2:
			if ((sk.getBackup1() == 3 && a.getAttackType() == FightConst.FTYPE_ATTACK)
					|| // 攻击方触发
					(sk.getBackup1() == 4 && a.getAttackType() == FightConst.FTYPE_DEFENCE)) {// 防御方触发
				if (sk.getBackup1() == 3
						&& a.getAttackType() == FightConst.FTYPE_ATTACK) {
					sb.append(" 攻击方触发");
				} else {
					sb.append(" 防御方触发");
				}
				cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0,
						new int[0]);
				for (FightCreature soldier : a.getSoldier()) {
					effectSoldier(soldier, sk);
				}
			}
			break;
		case FightConst.SOLDIER_TYPE3:
			if (sk.getBackup1() == 1) {// 获胜方触发●
				if (a.getWinType() == FightConst.FIGHT_WIN) {
					if (type == 2) {// 自己小兵
						sb.append(" 获胜方触发，自己小兵");
						cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0,
								new int[0]);
						for (FightCreature soldier : a.getSoldier()) {
							effectSoldier(soldier, sk);
						}
					} else if (type == 3) {
						sb.append(" 获胜方触发,对方小兵");
						cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0,
								new int[0]);
						for (FightCreature soldier : b.getSoldier()) {
							effectSoldier(soldier, sk);
						}
					}

				}
			}
			break;
		case FightConst.SOLDIER_TYPE4:
		case FightConst.SOLDIER_TYPE5:
			sb.append(" 直接对士兵造成伤害");
			int effect[] = new int[10];
			if (ftype != FightConst.FTYPE_ATTACK) {// 攻击方触发●
				sb.append(" 不是攻击方 不触发");
				break;
			}
			sb.append("\n");
			if (sk.getBackup4() == 4) {
				// 找到士兵最多的
				FightCreature max = null;
				for (FightCreature soldier : b.getSoldier()) {
					if (max == null) {
						max = soldier;
					} else {
						if (soldier.getNum() > max.getNum()) {
							max = soldier;
						}
					}
				}
				if (max != null) {
					sb.append("处理地方士兵最多的，敌方士兵=").append(FightUtil.changeSoldierToStr(b.getSoldier())).append(" 最多的部队=").append(max.getName()).append(" 类型=").append( max.getPriority());
					int num = a.getTotalSoldierNum();
					int reduce = num * (sk.getSoldierDeadRate()) / 100;
					reduce += reduce * (skill.getHurt() + max.getReduceHurt())
							/ 100;
					if(reduce<=0){//对死亡数量计算后为0，处理成1
						reduce=1;
					}
					int now = max.getNum() - reduce;
//					sb.append("\n").append("处理前士兵信息=").append(max.print());
					max.setNum(now == 0 ? 0 : now);
//					sb.append("\n").append("处理后士兵信息=").append(max.print());
					effect[max.getPriority()] = -reduce;
					cmd.skillCmd(a.getHero(), sk.getId(), false,
							max.getPriority(), effect);
				}
			} else {
				// 对所有
				int num = a.getTotalSoldierNum();
				int sNum = 0;
				for (FightCreature soldier : b.getSoldier()) {
					if (soldier.getNum() > 0) {
						sNum++;
					}
				}
				if (sNum != 0) {
					int reduce = num * (sk.getSoldierDeadRate()) / 100;
					reduce=reduce*(100+skill.getHurt())/100;
					reduce = reduce / sNum;
					sb.append("处理所有士兵,攻击方士兵初始总数=" + num + " 减少的初始总数=" + reduce);
					for (FightCreature soldier : b.getSoldier()) {
						int n = reduce;
						n += reduce * (soldier.getReduceHurt()) / 100;
						if(n<=0){//对死亡数量计算后为0，处理成1
							n=1;
						}
						int now = soldier.getNum() - n;
//						sb.append("\n").append("处理前士兵信息=").append(soldier.print());
						soldier.setNum(now == 0 ? 0 : now);
//						sb.append("\n").append("处理后士兵信息=").append(soldier.print());
						effect[soldier.getPriority()] = -n;
					}
					cmd.skillCmd(a.getHero(), sk.getId(), false, (byte) 0,
							effect);
				}
			}
			break;
		}
//		logger.info("soldier skill, "+sb.toString());
		return returnList;
	}

	/**
	 * 对士兵的攻击和防御进行影像
	 * 
	 * @param soldier
	 * @param sk
	 */
	public static void effectSoldier(FightCreature soldier, Skill sk) {
		soldier.setAttack(soldier.getAttack() + soldier.getAttack()
				* sk.getSoldierAttack() / 100);
		soldier.setDefence(soldier.getDefence() + soldier.getDefence()
				* sk.getSoldierDefence() / 100);
		soldier.setReduceHurt(soldier.getReduceHurt()
				+ sk.getSkillProbability());
	}

}
