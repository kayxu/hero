package com.joymeng.game.domain.fight;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.MathUtils;
import com.joymeng.game.domain.fight.battle.FightBattleBase;
import com.joymeng.game.domain.fight.obj.FightCreature;
import com.joymeng.game.domain.fight.obj.FightGroup;
import com.joymeng.game.domain.fight.obj.FightPlayer;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.Item;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.skill.Skill;
import com.joymeng.game.domain.skill.SkillManager;
import com.joymeng.services.core.buffer.JoyBuffer;
/**
 * 战斗指令
 * @author admin
 * @date 2012-6-1
 * TODO
 */
public class FightCmd {
//	static final Logger logger = LoggerFactory.getLogger(FightCmd.class);
	private int cmdCount;// 指令数量
	// 序列化buffer
	private JoyBuffer out = JoyBuffer.allocate(1024);
	private FightBattleBase fb;
	public FightCmd(FightBattleBase _fb){
		this.fb=_fb;
//		int index=out.position();
//		System.out.println("index="+index);
		out.put(fb.getType());
//		index=out.position();
//		System.out.println("index="+index);
		out.skip(4);	//个字节长度 cmdCount
//		index=out.position();
//		System.out.println("index="+index);
		out.skip(1);	//4个字节长度 round（回合数）
		int backupPic=MathUtils.random(3);
//		logger.info("backupPic="+backupPic);
		out.put((byte)backupPic);
	}
	/**
	 * 初始化指令,byte 指令号, byte（0攻击方1防守方）,byte（攻守方人数）,攻守放基本属性
	 * 
	 * @param list
	 * @param type
	 */
	public void initCmd(List<FightCreature> list, byte type) {
//		logger.info("●●●send initCmd >>>init, cmdType="+FightConst.FIGHT_INIT);
		out.put(FightConst.FIGHT_INIT);
		out.put(type);
		out.put((byte) list.size());
		for (FightCreature f : list) {
			FightUtil.creatureOut(f, out);
		}
		cmdCount++;
	}

	/**
	 * 移动指令 byte 指令号,type(将领0，小兵1)
	 * 
	 * @param fc
	 */
	public void moveCmd(byte type) {
//		logger.info("●●●send moveCmd >>>move, cmdType="+FightConst.FIGHT_MOVE);
		out.put(FightConst.FIGHT_MOVE);
		// out.put(type);
		cmdCount++;
	}
	/**
	 * 回合指令
	 */
	public void roundCmd(){
//		logger.info("●●●send roundCmd >>>round="+fb.getRound()+" , cmdType="+FightConst.FIGHT_ROUND);
		out.put(FightConst.FIGHT_ROUND);// 指令类型
		out.put(fb.getRound());// 回合数
		cmdCount++;
	}
	/**
	 * 攻击指令
	 * 
	 * @param fc
	 */
	public void attackCmd(FightCreature fc, FightCreature target, int num,boolean isHeavyHurt,List<Byte> effect) {
//		logger.info("●●●send attackCmd >>>attack，目标方="+target.getName()+" 剩余血量="+num+" cmdType="+FightConst.FIGHT_ATTACK);
		out.put(FightConst.FIGHT_ATTACK);// 指令类型
//		out.put(fb.getRound());// 回合数
		out.put(fc.isAttack() ? FightConst.FTYPE_ATTACK
				: FightConst.FTYPE_DEFENCE);// 指令操作方
		if(fc.getPriority()==FightConst.CREATURE_HERO){
			out.putInt(0);
		}else{
			out.putInt(fc.getPriority());// 指令操作方id
		}
		if(target.getPriority()==FightConst.CREATURE_HERO){
			out.putInt(0);
		}else{
			out.putInt(target.getPriority());// 被攻击方id
		}
		out.putInt(num);// 被攻击方的血量或者数量变化
		//是否重伤
		out.put(isHeavyHurt?(byte)1:0);
		//是否暴击 1暴击 0不暴击
		out.put((byte)0);
//		out.put(fc.getCritical()?(byte)1:0);
		out.put((byte)effect.size());
		for(Byte i:effect){
			out.put(i);
//			logger.info("●●● attackCmd effect="+i);
		}
		cmdCount++;
	}

	/**
	 * 技能指令
	 * @param fc
	 * @param skillId
	 * @param isHeavyHurt
	 * @param type
	 * @param hurt 数组长度为10 分别表示 【0-4】敌方将领，弓，齐，步，特【5-9】我方将领，弓，齐，步，特
	 */
	public void skillCmd(FightCreature fc, int skillId,boolean isHeavyHurt ,byte type ,int[]hurt) {
		String str="";
		for(int i=0;i<hurt.length;i++){
			str+=hurt[i]+",";
		}
//		logger.info("●●●send skillCmd >>>skill,skillId="+skillId+" isHeavyHurt="+isHeavyHurt+" type="+type+" hurt="+str+" cmdType="+FightConst.FIGHT_SKILL);
		out.put(FightConst.FIGHT_SKILL);// 指令类型
//		out.put(fb.getRound());// 回合数
		out.put(fc.isAttack() ? FightConst.FTYPE_ATTACK
				: FightConst.FTYPE_DEFENCE);// 指令操作方
		out.putInt(fc.getPriority());// 指令操作方类型
		out.putInt(skillId);// 技能id
		out.put(type);//士兵类型 ，将战中不起作用
		//是否暴击 1暴击 0不暴击
//		out.put(fc.getCritical()?(byte)1:0);
		out.put((byte)0);
		//是否重伤 1重伤0 不重伤
		out.put(isHeavyHurt?(byte)1:0);
		//伤害次数 for（伤害数值）
		out.putInt(hurt.length);
		for(int i=0;i<hurt.length;i++){
			out.putInt(hurt[i]);
		}
		cmdCount++;
	}
	/**
	 * 将战结束后的加成
	 * @param group
	 * @param attackAdd 攻击加成
	 * @param defAdd 防守加成
	 */
	public void heroFightEnd(FightGroup group){
//		logger.info("●●●send heroFightEnd >>>end"+"cmdType="+FightConst.FIGHT_HEROEND);
		out.put(FightConst.FIGHT_HEROEND);
		// 攻守方类别
		out.put(group.getAttackType());
		//攻击加成
		out.put(group.getBaseAdd());
		//防守加成
		out.put(group.getBaseAdd());
		cmdCount++;
	}

	/**
	 * 结束指令
	 * 
	 * @param fc
	 */
	public void endCmd(FightGroup group) {
//		logger.info("●●●send endCmd >>>end"+"cmdType="+FightConst.FIGHT_SKILL);
			out.put(FightConst.FIGHT_END);
			// 攻守方类别
			out.put(group.getAttackType());
			//是否是获胜方 ，1获胜 0失败
			out.put(group.getWinType());
			//玩家类别
			out.put(group.getType());
			// 玩家经验，功勋 ,金钱
			// 将领的经验，级别
			PlayerCharacter pc=group.getPc();
			if(group.getType()==FightConst.CREATURE_HERO){
				out.putInt(pc.getData().getExp());
				out.putInt(group.getReward()[1]);
				out.putInt(group.getReward()[0]);
				
				FightPlayer fp=(FightPlayer)group.getHero();
				out.putInt(fp.getHero().getExp());
				out.putInt(fp.getHero().getLevel());
				//3道具 	//1装备  最多
//				out.put((byte)0);
				List<Cell> list=group.getGoods();
				out.put((byte)list.size());
//				logger.info("发送战斗奖励数量="+list.size());
				for(Cell cell: list){
//					cell.serialize(out);
					Item item=cell.getItem();
					if(item.isProp()){//道具
						out.put((byte)0);
					}else{//装备
						out.put((byte)1);
					}
					out.putInt(item.getId());
					out.putInt(cell.getItemCount());
				}
			}else{
				out.putInt(0);
				out.putInt(0);
				out.putInt(0);
				out.putInt(0);
				out.putInt(0);
			}
			// 每一个兵种的损失
			List<FightCreature> soldier=group.getSoldier();
			out.put((byte) (soldier.size()));
//			logger.info("fight end");
			for (FightCreature f : soldier) {
					out.putInt(f.getPriority());
					out.putInt(f.getLoseNum());
//					logger.info("name="+group.getHero().getName()+" type=" + f.getPriority() + "损失数量="
//							+ f.getLoseNum() + " 剩余数量=" + f.getNum());
					out.putInt(f.getRecover());// 救活
			}
			cmdCount++;
	}
	
	public void resultCmd(String str1,String str2){
//		logger.info("●●●send resultCmd >>>end"+"cmdType="+FightConst.FIGHT_RESULT);
		out.put(FightConst.FIGHT_RESULT);
		out.putPrefixedString(str1,JoyBuffer.STRING_TYPE_SHORT);
		out.putPrefixedString(str2,JoyBuffer.STRING_TYPE_SHORT);
		cmdCount++;
	}

	public int getCmdCount() {
		return cmdCount;
	}
	public void setCmdCount(int cmdCount) {
		this.cmdCount = cmdCount;
	}
	public JoyBuffer getOut() {
		return out;
	}

	public void setOut(JoyBuffer out) {
		this.out = out;
	}


}
