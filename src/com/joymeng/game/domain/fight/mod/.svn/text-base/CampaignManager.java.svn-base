package com.joymeng.game.domain.fight.mod;

import java.util.HashMap;
import java.util.List;

import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;

/**
 * 战役管理器
 * @author admin
 * @date 2012-6-6
 * TODO
 */
public class CampaignManager {
	private static CampaignManager instance;
	private HashMap<Integer, Campaign> campaignMap = new HashMap<Integer, Campaign>();
	public static CampaignManager getInstance() {
		if (instance == null) {
			instance = new CampaignManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, Campaign.class);
		for (Object obj : list) {
			Campaign data = (Campaign) obj;
			campaignMap.put(data.getId(), data);
			
		}
	}
	public Campaign getCampaign(int id){
		return campaignMap.get(id);
	}
	/**
	 * 更新某一个玩家的战役情况
	 * @param player
	 */
	public void update(PlayerCharacter player){
		String str=player.getData().getCamp();
		if(str==null||"".equals(str.trim())){//第一次
			           
		}else{
			//判断时间是否过期
			String infos[]=str.split("/");
			for(int i=0;i<infos.length;i++){
				CampInfo ci=new CampInfo(infos[i]);
//				System.out.println("camp time=="+TimeUtils.getTime(ci.getTime()));
				if(!TimeUtils.isSameDay(ci.getTime())){//不是同一天，清空
					Campaign camp=this.getCampaign(ci.getId());
					ci.setChargeNum(camp.getChargeNum());
					ci.setFreeNum(camp.getFreeNum());
					ci.setTime(TimeUtils.nowLong());
					infos[i]=ci.toStr();
				}
			}
			String newStr=StringUtils.recoverNewStr(infos, "/");
			player.getData().setCamp(newStr);
		}
	}
}
