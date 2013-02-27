package com.joymeng.game.domain.building;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.role.PlayerCharacter;

public class PlayerTavernManager{//用户酒馆建筑
	private static final Logger logger = LoggerFactory
	.getLogger(PlayerTavernManager.class);
	static EquipmentManager equipMgr = EquipmentManager.getInstance();
	static BuildingManager bldMgr = BuildingManager.getInstance();
//	PlayerBuilding tavern;
	PlayerCharacter owner;
	PlayerBuildingManager pm;
	public void activation(PlayerCharacter p,PlayerBuildingManager pm){//激活主城操作类
		if(p != null){
			owner = p;
			this.pm = pm;
			getTavern();
//			if(getTavern()!= null)
//				logger.info("用户："+owner.getData().getUserid() +" 酒馆："+getTavern().getId() + " 控制类加载成功");
		}
	}
	
	/**
	 * 获取
	 * @return
	 */
	public PlayerBuilding getTavern(){
//		if(tavern == null)
//			tavern = pm.getPlayersBuild(GameConst.JIUGUAN_ID,(int)owner.getData().getUserid());
		return pm.getPlayersBuild(GameConst.JIUGUAN_ID,(int)owner.getData().getUserid());
	}
	//获取酒馆刷将时间
	public long getHeroTime(){
		if(getTavern() == null)
			return 0;
		//获取酒馆建筑
		return getTavern().getUpdateTime().getTime();
	}
	
	//获取酒馆刷将时间
	public Timestamp getUpdateTime(){
		if(getTavern() == null)
			return null;
		//获取酒馆建筑
		return getTavern().getUpdateTime();
	}
	//更新用户刷将时间
	public boolean setHeroTime(){
		if(getTavern() != null){
//			logger.info(getTavern().toString());
			BuildingUpGrade bu = bldMgr.getBuindingLevel(getTavern().getBuildingID(),getTavern().getBuildLevel());
			getTavern().setUpdateTime(TimeUtils.addSecond(TimeUtils.nowLong(),bu.getPeriod()));
//			pm.savePlayerBuilding(getTavern());
			return true;
		}
		return false;
	}
	
	//增加一次
	public boolean addItemTime(){
		boolean isOk = false;
		if(getTavern() != null){
			getTavern().setRefreshTimes(getTavern().getRefreshTimes()+1);
			isOk =  true;
		}
		//
		 RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
		 rms.addModule(getTavern());
		 AndroidMessageSender.sendMessage(rms, owner);
		//
		return isOk;
	}
	//获取次数
	public int getItemTime(){
		if(getTavern() == null)
			return 1000;
		else
			return getTavern().getRefreshTimes();
	}
}
