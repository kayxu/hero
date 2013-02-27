package com.joymeng.game.domain.building;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.role.PlayerCharacter;


public class PlayerTrainingManager{//训练台
	private static final Logger logger = LoggerFactory
	.getLogger(PlayerBarrackManager.class);
	static EquipmentManager equipMgr = EquipmentManager.getInstance();
	static BuildingManager bldMgr = BuildingManager.getInstance();
//	PlayerBuilding training;
	PlayerCharacter owner;
	PlayerBuildingManager pm;
	public void activation(PlayerCharacter p,PlayerBuildingManager pm){//激活主城操作类
		if(p != null){
			owner = p;
			this.pm = pm;
			getTraining();
//			if(getTraining() != null)
//				logger.info("用户："+owner.getId() +" 训练台："+getTraining().getId() + " 控制类加载成功");
		}
	}
	
	/**
	 * 获取训练台
	 * @return
	 */
	public PlayerBuilding getTraining(){
//		if(training == null)
//			training = pm.getPlayersBuild(GameConst.TRAINING_ID,(int)owner.getData().getUserid());
		return pm.getPlayersBuild(GameConst.TRAINING_ID,(int)owner.getData().getUserid());
	}
	/**
	 * 训练位开放
	 * @param num 训练位no
	 * @return
	 */
	public PlayerBuilding openTraining(int no){
		if(getTraining() == null)
			return null;
		short my = Short.parseShort(Integer.toHexString(getTraining().getOperatcount()),16);
//		short my = 0xe00;
		int order = no /4;
		int order4 = no%4;
		switch (order) {
		case 0:
			switch (order4) {
			case 0:
				my = (short) (my | 0x800);
				break;
			case 1:
				my = (short) (my | 0x400);
				break;
			case 2:
				my = (short) (my | 0x200);
				break;
			case 3:
				my = (short) (my | 0x100);
				break;
			}
			break;
		case 1:
			switch (order4) {
			case 0:
				my = (short) (my | 0x080);
				break;
			case 1:
				my = (short) (my | 0x040);
				break;
			case 2:
				my = (short) (my | 0x020);
				break;
			case 3:
				my = (short) (my | 0x010);
				break;
			}
			break;
		case 2:
			switch (order4) {
			case 0:
				my = (short) (my | 0x008);
				break;
			case 1:
				my = (short) (my | 0x004);
				break;
			case 2:
				my = (short) (my | 0x002);
				break;
			case 3:
				my = (short) (my | 0x001);
				break;
			}
			break;
		}
		getTraining().setOperatcount(my);
		if(pm != null){
//			pm.savePlayerBuilding(getTraining());
		}
		return getTraining();
	}
	
	/**
	 * 训练位关闭
	 * @param num 训练位no
	 * @return
	 */
	public PlayerBuilding closeTraining(int no){
		if(getTraining() == null)
			return null;
		short my = Short.parseShort(Integer.toHexString(getTraining().getOperatcount()),16);
		int order = no /4;
		int order4 = no%4;
		switch (order) {
		case 0:
			switch (order4) {
			case 0:
				my = (short) (my & 0x7FF);
				break;
			case 1:
				my = (short) (my & 0xBFF);
				break;
			case 2:
				my = (short) (my & 0xDFF);
				break;
			case 3:
				my = (short) (my & 0xEFF);
				break;
			}
			break;
		case 1:
			switch (order4) {
			case 0:
				my = (short) (my & 0xF7F);
				break;
			case 1:
				my = (short) (my & 0xFBF);
				break;
			case 2:
				my = (short) (my & 0xFDF);
				break;
			case 3:
				my = (short) (my & 0xFEF);
				break;
			}
			break;
		case 2:
			switch (order4) {
			case 0:
				my = (short) (my & 0xFF7);
				break;
			case 1:
				my = (short) (my & 0xFFB);
				break;
			case 2:
				my = (short) (my & 0xFFD);
				break;
			case 3:
				my = (short) (my & 0xFFE);
				break;
			}
			break;
		}
		getTraining().setOperatcount(my);
		if(pm != null){
//			pm.savePlayerBuilding(getTraining());
		}
		return getTraining();
	}
	
	/**
	 * 判断是否开放
	 * @param no
	 * @return
	 */
	public boolean isTrainingOpen(int no){
//		PlayerBuilding pm = getPlayerFromBuild(getBuilding(GameConst.TRAINING_ID));
		if(getTraining() == null)
			return false;
		short my = Short.parseShort(Integer.toHexString(getTraining().getOperatcount()),16);
		
		int order = no /4;
		int order4 = no%4;
		switch (order) {
		case 0:
			switch (order4) {
			case 0:
				my = (short) (my & 0x800);
				break;
			case 1:
				my = (short) (my & 0x400);
				break;
			case 2:
				my = (short) (my & 0x200);
				break;
			case 3:
				my = (short) (my & 0x100);
				break;
			}
			break;
		case 1:
			switch (order4) {
			case 0:
				my = (short) (my & 0x80);
				break;
			case 1:
				my = (short) (my & 0x40);
				break;
			case 2:
				my = (short) (my & 0x20);
				break;
			case 3:
				my = (short) (my & 0x10);
				break;
			}
			break;
		case 2:
			switch (order4) {
			case 0:
				my = (short) (my & 0x8);
				break;
			case 1:
				my = (short) (my & 0x4);
				break;
			case 2:
				my = (short) (my & 0x2);
				break;
			case 3:
				my = (short) (my & 0x1);
				break;
			}
			break;
		}
		
		return (my >> 11-no) == 1;
	}
	
	/**
	 * 得到训练台对象
	 * @param no 训练位
	 * @return
	 */
	public TrainingBits getTrainingBits(int no){
		if(isTrainingOpen(no)){
			return bldMgr.getTrain(no);
		}
		return null;
	}
	
	/**
	 * 获取经验列表
	 * @param no
	 * @return
	 */
	public int[] getTrainExp(int no){
		TrainingBits tb = getTrainingBits(no);
		if(tb == null || getTraining() == null)
			return null;
		return tb.getTrainExp().get((int)getTraining().getBuildLevel());
	}
	/**
	 * 获取技能列表
	 * @param no
	 * @return
	 */
	public int[] getTrainSkill(int no){
		TrainingBits tb = getTrainingBits(no);
		return tb.getTrainSkill().get((int)getTraining().getBuildLevel());
	}
	
	
}
