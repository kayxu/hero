package com.joymeng.game.domain.nation.war;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.PushSign;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 据点
 * @author Administrator
 *
 */
public class StrongHold extends ClientModuleBase{
	private final byte[] lock = new byte[0]; // 锁
	public static Logger logger = Logger.getLogger(StrongHold.class);
	int campId;//军营id
	int nationId;//区域id
	int refreshTime;//刷新时间
	byte fightingProcess;//战斗进程 0,1,2,3,4战斗进程
	int mob1;//怪物部队1
	int mob2;//怪物部队2
	int mob3;//怪物部队3
	int mob4;//怪物部队4
	public int getCampId() {
		return campId;
	}
	public void setCampId(int campId) {
		this.campId = campId;
	}
	public byte getFightingProcess() {
		return fightingProcess;
	}
	public void setFightingProcess(byte fightingProcess) {
		this.fightingProcess = fightingProcess;
	}
	public int getMob1() {
		return mob1;
	}
	public void setMob1(int mob1) {
		this.mob1 = mob1;
	}
	public int getMob2() {
		return mob2;
	}
	public void setMob2(int mob2) {
		this.mob2 = mob2;
	}
	public int getMob3() {
		return mob3;
	}
	public void setMob3(int mob3) {
		this.mob3 = mob3;
	}
	public int getMob4() {
		return mob4;
	}
	public void setMob4(int mob4) {
		this.mob4 = mob4;
	}
	
	public int getNationId() {
		return nationId;
	}
	public void setNationId(int nationId) {
		this.nationId = nationId;
	}
	
	public int getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}
	/**
	 * 刷新据点,战胜次数为0
	 */
	public void refreshHold(int now){
		setFightingProcess((byte)0);
		setRefreshTime(now);
		//全员推送消息
//		logger.info("=============refreshHold");
//		logger.info("============="+getNationId());
		WarManager.getInstance().sendRmsOne(this, getNationId());
	}
	
	/**
	 * 是否需要战斗
	 * @return
	 */
	public TipUtil checkFight(PlayerCharacter att){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		tip.setFailTip("");
//		synchronized (lock) {
			if(att.getData().getLevel() > GameConst.STRONGHOLD_END || att.getData().getLevel() < GameConst.STRONGHOLD_BEGIN){
//				logger.info("打据点需要玩家等级5~30");
				tip.setSuccTip("打据点需要玩家等级5~30");
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setSuccTip("Your Lord should be at Lv.5 ~ Lv.30");
				}
				
			}else{
				MilitaryCamp camp = WarManager.getInstance().getMyCamp(getCampId());
				if(camp != null){
//					UserWarData myData = WarManager.getInstance().getMyWarData(att.getId(), camp.getNativeId());
//					if((TimeUtils.nowLong()/1000 - myData.getCdTime()) < 2){
//						logger.info("未到CD时间");
//						tip.setSuccTip("未到CD时间,不可以战斗");
//					}
					if(isThrough(getStrongNum(getNationId())).isResult()){
//						logger.info("怪物已消灭完");
						tip.setSuccTip("怪物已消灭完");
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							tip.setSuccTip("Monsters have been wiped out");
						}
					}
				}else{
					tip.setSuccTip("camp fail");
				}
			}
			return tip;
//		}
	}
	/**
	 * 战斗mob 
	 * @param att 用户
	 * @param backSolider 返回士兵
	 */
	public  void fightMob(byte type,PlayerCharacter att,String backSolider){
//		synchronized (lock) {
			MilitaryCamp camp = WarManager.getInstance().getMyCamp(getCampId());
			if(camp != null){
				TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
				tip.setFailTip("fail");
				//取得攻击用户数据
				UserWarData myData = WarManager.getInstance().getMyWarData(att.getId(), camp.getNativeId());
				if(type == 0)//胜利
				{	
					//修改士兵数据
//					if(camp.getUserId() == att.getId()){
//						camp.motifyCampSolider(backSolider);//修改士兵数据
//					}else{
					att.getPlayerBuilgingManager().recoverSoldier(backSolider);
//					}
					//修改 据点战斗时间
					setFightingProcess((byte)(getFightingProcess()+1));
					//积分添加
//					myData.setWarIntegral(myData.getWarIntegral()+1);
					myData.saveWarData(UserWarData.WAR_INTEGRAL, getWarData(),true);
//					logger.info("消灭怪物胜利");
					//rms
					List<ClientModuleBase> push = new ArrayList<ClientModuleBase>();
					push.add(this);
					push.add(myData);
					PushSign.sendAll(push, new PlayerCharacter[]{att}, ProcotolType.USER_INFO_RESP);
					WarManager.getInstance().sendRmsList(push, getNationId());
					
					//rms
				}else{//失败
					//修改士兵数据
//					if(camp.getUserId() == att.getId()){
//						camp.motifyCampSolider(backSolider);//修改士兵数据
//					}else{
					att.getPlayerBuilgingManager().recoverSoldier(backSolider);
//					}
//					logger.info("消灭怪物失败,撤回");
				}
				//修改cd时间
				//myData.saveWarData(UserWarData.CD_TIME, 0,true);
				
				tip.setSuccTip("success");
			}
//		}
		
	}
	
	/**
	 * 得到对应积分
	 * @return
	 */
	public int getWarData(){
		if(nationId%1000 == 0){
			return WarManager.COUNTY_MONSTER;
		}else if(nationId%100 == 0){
			return WarManager.STATE_MONSTER;
		}else if(nationId%10 == 0){
			return WarManager.CITY_MONSTER;
		}
		return 0;
	}
	
	/**
	 * 是否贯通
	 * @return
	 */
	public TipUtil isThrough(int fightNum){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		tip.setFailTip("fail");
		if(!WarManager.IS_FIGHT){
			tip.setFailTip("未开放战斗");
			if(I18nGreeting.LANLANGUAGE_TIPS==1){
				tip.setFailTip("Not open battles");
			}
			return tip;
		}else{
			tip.setSuccTip("");
		}
		if(fightingProcess < fightNum){
			tip.setFailTip("未消灭怪物");
			if(I18nGreeting.LANLANGUAGE_TIPS==1){
				tip.setFailTip("Monsters have not been wiped out");
			}
			return tip;
		}else{
			tip.setSuccTip("");
			return tip;
		}
	}
	
	@Override
	public byte getModuleType() {
		return NTC_SHRONG_BATTLE;
	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(campId);
		out.putInt(refreshTime+120);
		out.put((byte)(getStrongNum(nationId) -fightingProcess));
		logger.info(">>>StrongHold 区域:"+campId+"|发送成功");
	}
	
	/**
	 * 需要攻击多少次
	 * 
	 * @param type
	 * @return
	 */
	public  int getStrongNum(int nationId) {
		if (nationId % 1000 == 0) {
			return 8;
		} else if (nationId % 100 == 0) {
			return 8;
		} else if (nationId % 10 == 0) {
			return 4;
		}
		return 0;
	}

	
}
