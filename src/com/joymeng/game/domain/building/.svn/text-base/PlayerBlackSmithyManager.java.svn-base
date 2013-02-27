package com.joymeng.game.domain.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.item.equipment.EquipPrototype;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;

/**
 * 铁匠铺管理类
 * @author xufangliang
 *
 */
public class PlayerBlackSmithyManager{
	private static final Logger logger = LoggerFactory
	.getLogger(PlayerBarrackManager.class);
	static EquipmentManager equipMgr = EquipmentManager.getInstance();
	static BuildingManager bldMgr = BuildingManager.getInstance();
//	PlayerBuilding blackSmithy;
	PlayerCharacter owner;
	PlayerBuildingManager pm;
	ArrayList<Equipment> equArl = new ArrayList<Equipment>();
	public void activation(PlayerCharacter p,PlayerBuildingManager pm){//激活主城操作类
		if(p != null){
			owner = p;
			this.pm = pm;
			getBlackSmithy();
			if(getBlackSmithy() != null){
//				logger.info("用户："+owner.getData().getUserid() +" 训练台："+getBlackSmithy().getId() + " 控制类加载成功");
				loadBlackSmithy();
			}
		}
	}
	//保存信息
	public boolean saveBlackSmithy(){
		String msg = toStringMsg();
		if(!"".equals(msg) && getBlackSmithy() != null){
			getBlackSmithy().setRemark2(msg);
//			pm.savePlayerBuilding(getBlackSmithy());
			return true;
		}
		return false;
	}
	//加载装备集合
	public void loadBlackSmithy(){
		if(getBlackSmithy() != null && !"".equals(getBlackSmithy().getRemark2())){
			String[] keys = getBlackSmithy().getRemark2().split(";");
//			logger.info("用户："+owner.getData().getUserid() + " smithy : "+keys.toString());
			for(String s : keys){
				if(!"".equals(s)){
					int i = Integer.parseInt(s);
					EquipPrototype e = equipMgr.getEquipment(i);//owner.getEquipment(i);
					if(e != null){
						Equipment ee = new Equipment(e);
						equArl.add(ee);
					}
				}
			}
		}
	}
	/**
	 * 获取铁匠铺
	 * @return
	 */
	public PlayerBuilding getBlackSmithy(){
//		if(blackSmithy == null)
//			blackSmithy = pm.getPlayersBuild(GameConst.BLACKSMITHY_ID,(int)owner.getData().getUserid());
		return pm.getPlayersBuild(GameConst.BLACKSMITHY_ID,(int)owner.getData().getUserid());
	}
	/**
	 * 刷新铁匠铺
	 * @param smithy
	 * @return
	 */
	public ArrayList<Equipment> refreshEqument(Smithy smithy){
		if(smithy == null){
			return null;
		}
		else{
			ArrayList<Equipment> equLst = new ArrayList<Equipment>();
			 Map<Byte, List<EquipPrototype>> map = equipMgr.equipmentLevelDatas;
			 Map<Integer, EquipPrototype> equiDatas = equipMgr.equipmentDatas;
			 if(map == null || map.size() ==0){}
			 else{//装备共13级
				for(int i = 0 ;i < smithy.getEqumentNum1();i++){
					List<EquipPrototype> lst = map.get((byte)1);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum5();i++){
					List<EquipPrototype> lst = map.get((byte)5);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum10();i++){
					List<EquipPrototype> lst = map.get((byte)10);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum15();i++){
					List<EquipPrototype> lst = map.get((byte)15);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum20();i++){
					List<EquipPrototype> lst = map.get((byte)20);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum25();i++){
					List<EquipPrototype> lst = map.get((byte)25);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum30();i++){
					List<EquipPrototype> lst = map.get((byte)30);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum35();i++){
					List<EquipPrototype> lst = map.get((byte)35);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum40();i++){
					List<EquipPrototype> lst = map.get((byte)40);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum45();i++){
					List<EquipPrototype> lst = map.get((byte)45);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum50();i++){
					List<EquipPrototype> lst = map.get((byte)50);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum55();i++){
					List<EquipPrototype> lst = map.get((byte)55);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
				for(int i = 0 ;i < smithy.getEqumentNum60();i++){
					List<EquipPrototype> lst = map.get((byte)60);
					int t = random(lst.size());
					int id = lst.get(t-1).getId() + randomColor(smithy);
					Equipment e = new Equipment(equiDatas.get(id));
					equLst.add(e);
				}
			 }
//			 BlackSmithyRefresh refresh = RefreshsBuildManager.getInstance().getBlackSmithyRefresh(blackSmithy.get)
//			 equArl = equLst;
//			 saveBlackSmithy();//保存
			 return equLst;
		}
	}
	/**
	 * 特定刷新
	 * @param smithy
	 * @param refresh
	 * @return
	 */
	public ArrayList<Equipment> refreshBlackSmithy(Smithy smithy,BlackSmithyRefresh refresh){
		ArrayList<Equipment> equLst = refreshEqument(smithy);
		HashMap<Integer, Equipment> maps = new HashMap<Integer, Equipment>();
		for(int i = 0 ; i < equLst.size() ;i ++){
			maps.put(i, equLst.get(i));
		}
		if(refresh == null){
			 equArl = equLst;
			 saveBlackSmithy();//保存
			return equLst;
		}else{
			if (refresh.getNo1() != 0) {
				Equipment e = new Equipment(equipMgr.equipmentDatas.get(refresh.getNo1()));
				if(e != null){
					maps.put(0, e);
				}
			}
			if (refresh.getNo2() != 0) {
				Equipment e = new Equipment(equipMgr.equipmentDatas.get(refresh.getNo2()));
				if(e != null){
					maps.put(1, e);
				}
			}
			if (refresh.getNo3() != 0) {
				Equipment e = new Equipment(equipMgr.equipmentDatas.get(refresh.getNo3()));
				if(e != null){
					maps.put(2, e);
				}
			}
			if (refresh.getNo4() != 0) {
				Equipment e = new Equipment(equipMgr.equipmentDatas.get(refresh.getNo4()));
				if(e != null){
					maps.put(3, e);
				}
			}
			if (refresh.getNo5() != 0) {
				Equipment e = new Equipment(equipMgr.equipmentDatas.get(refresh.getNo5()));
				if(e != null){
					maps.put(4, e);
				}
			}
			if (refresh.getNo6() != 0) {
				Equipment e = new Equipment(equipMgr.equipmentDatas.get(refresh.getNo6()));
				if(e != null){
					maps.put(5, e);
				}
			}
			if (refresh.getNo7() != 0) {
				Equipment e = new Equipment(equipMgr.equipmentDatas.get(refresh.getNo7()));
				if(e != null){
					maps.put(6, e);
				}
			}
			if (refresh.getNo8() != 0) {
				Equipment e = new Equipment(equipMgr.equipmentDatas.get(refresh.getNo8()));
				if(e != null){
					maps.put(7, e);
				}
			}
			if (refresh.getNo9() != 0) {
				Equipment e = new Equipment(equipMgr.equipmentDatas.get(refresh.getNo9()));
				if(e != null){
					maps.put(8, e);
				}
			}
			if (refresh.getNo10() != 0) {
				Equipment e = new Equipment(equipMgr.equipmentDatas.get(refresh.getNo10()));
				if(e != null){
					maps.put(9, e);
				}
			}
			
			 equArl = new ArrayList<Equipment>(maps.values());
			 saveBlackSmithy();//保存
			return new ArrayList<Equipment>(maps.values());
		}
		
	}
	/**
	 * 随机成色
	 * @param smithy
	 * @return
	 */
	public  int randomColor(Smithy smithy){
		double f = Math.random();
		if(f > 0 && f <= smithy.getWhite()){
			return 1*000;
		}else if(f > smithy.getWhite() && f <= smithy.getGreen() && smithy.getGreen()!= 0){
			return 1*100;
		}else if(f > smithy.getGreen() && f <= smithy.getBlue() && smithy.getBlue()!= 0){
			return 2*100;
		}else if( f > smithy.getBlue() && f <= smithy.getPurple()  && smithy.getPurple()!= 0){
			return 3*100;
		}else if( f > smithy.getPurple() && f <= smithy.getOrange() && smithy.getOrange()!= 0){
			return 4*100;
		}else{
			return 0*100;
		}
	}
	/**
	 * 求随机数
	 * @param num 范围
	 * @return
	 */
	public static int random(int num){
		return (int)(Math.random()*num+1);
	}
	
	/**
	 * 刷新铁匠铺
	 * 
	 * @param type
	 * @return
	 */
	public ArrayList<Equipment> refreshEqument(byte type) {// byte= 2 付费       1  刷新
		if (getBlackSmithy() == null || owner == null)
			return null;
		else {
			boolean flag = false;
			if(type == 0){
				if(equArl != null && equArl.size() > 0){
					if(TimeUtils.nowLong() - getBlackSmithy().getUpdateTime().getTime()<0){
						return equArl;
					}else{
						flag = true;
					}
				}else{
					if(getBlackSmithy().getUpdateTime().getTime() == 0){
						flag = true;
					}else if(TimeUtils.nowLong() - getBlackSmithy().getUpdateTime().getTime()<0){
						return new ArrayList<Equipment>();
					}else{
						flag = true;
					}
				}
			}
			Smithy smithy = bldMgr.smithyDatas.get((int)getBlackSmithy().getBuildLevel());
			if (type == 0 && flag) {
//				if(true){
					getBlackSmithy().setUpdateTime(TimeUtils.addSecond(TimeUtils.nowLong(), 6 * 60 *60));
					BlackSmithyRefresh refresh = RefreshsBuildManager.getInstance().getBlackSmithyRefresh(getBlackSmithy().getInitialTimes(),(byte)0);
					getBlackSmithy().setInitialTimes(getBlackSmithy().getInitialTimes()+1);
//					pm.savePlayerBuilding(getBlackSmithy());// 更新时间
					
					return refreshBlackSmithy(smithy,refresh);
//				} else {
//					if(equArl != null && equArl.size() > 0){
//						return equArl;
//					}
//					return equArl;
//				}
			} else if(type == 2){
				int cost = GameConfig.refreshBlackSmithy;
				if (owner.getResourcesData(GameConfig.JOY_MONEY) >= cost ) {// 是否有足够的道具
					getBlackSmithy().setUpdateTime(TimeUtils.addSecond(TimeUtils.nowLong(), 6 * 60 *60));
					BlackSmithyRefresh refresh = RefreshsBuildManager.getInstance().getBlackSmithyRefresh(getBlackSmithy().getRefreshTimes()+1,(byte)1);
					getBlackSmithy().setRefreshTimes(getBlackSmithy().getRefreshTimes()+1);
//					pm.savePlayerBuilding(getBlackSmithy());
					// 消耗符 符 ID确定
					owner.recordRoleData(GameConfig.JOY_MONEY, -1*cost);// 消耗符
					
//					logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " uid=" + owner.getId() + " uname=" + owner.getData().getName() 
//							+ " diamondNum=" + cost + " description=铁匠铺刷新");
//					
					GameLog.logSystemEvent(LogEvent.USE_DIAMOND, String.valueOf(GameConst.DIAMOND_SMITHY),"", String.valueOf(cost),String.valueOf(owner.getId()));

					
					//rms
					RespModuleSet rms=new RespModuleSet(ProcotolType.BUILDING_RESP);//模块消息
					rms.addModule(getBlackSmithy());
					AndroidMessageSender.sendMessage(rms, owner);
					//rms
					return refreshBlackSmithy(smithy,refresh);
				} else {
					String msg = I18nGreeting.getInstance().getMessage("dimond.not.enough", null);
					GameUtils.sendTip(new TipMessage(msg,
							ProcotolType.NATION_RESP, GameConst.GAME_RESP_SUCCESS),
							owner.getUserInfo(),GameUtils.FLUTTER);
					if(equArl != null && equArl.size() > 0){
						return equArl;
					}
					return equArl;
				}
			}
		}
		return null;
	}
	
	//查询list是否有本装备
	public Equipment fromArr(int eid){
		if(equArl == null || equArl.size() == 0)
			return null;
		for(Equipment e : equArl){
			if(e.getId() == eid){
				return e;
			}
		}
		return null;
	}
	
	//购买
	public Equipment buyEquipment(int eid){
		if(equArl == null || equArl.size() == 0){
//			logger.info("铁匠铺数据错误！");
			return null;
		}
		Equipment e = fromArr(eid);
		if(e == null){
//			logger.info("获取装备对象错误！");
			return null;
		}else if(owner.getPlayerStorageAgent().isFull()){
//			logger.info("背包已满");
			e.setTip(new TipMessage("背包已满!",ProcotolType.ITEMS_RESP,GameConst.GAME_RESP_FAIL));
			return e;
		}else{
			//本地删除
			if( owner.getData().getGameMoney() >= e.getPrototype().getBuyPrice()){
				equArl.remove(e);//本地删除
				//加入装备
				Equipment ee=owner.getPlayerStorageAgent().addEquipment(eid, 1, 0);
				owner.saveResources(GameConfig.GAME_MONEY, -1 * e.getPrototype().getBuyPrice());//扣钱
				saveBlackSmithy();//保存数据
				QuestUtils.checkFinish(owner, QuestUtils.TYPE26, true,ee.getPrototype().getQualityColor());
				String msg = I18nGreeting.getInstance().getMessage("buy.success", null);
				e.setTip(new TipMessage(msg,ProcotolType.ITEMS_RESP,GameConst.GAME_RESP_SUCCESS));
				return e;
			}
			e.setTip(new TipMessage("钱币不够！",ProcotolType.ITEMS_RESP,GameConst.GAME_RESP_FAIL));
//			logger.info("钱币不够！");
			return e;
		}
	}
	//保存数据
	public String toStringMsg(){
		if(equArl == null || equArl.size() == 0)
			return "";
		else{
			StringBuffer play =new StringBuffer();
			for(Equipment e : equArl){
				 play.append(StringUtils.join("",String.valueOf(e.getId()),";"));
			}
			return play.toString();
		}
	}
	
	public static void main(String[] args) {
		List<String> lst = new ArrayList<String>();
		lst.add("a");
		lst.add("b");
		
		for(int i= 0;i < 2 ;i++){
			String a = lst.get(i);
			a = "xx"+i;
		}
		
		System.out.println(lst);
	}
}
