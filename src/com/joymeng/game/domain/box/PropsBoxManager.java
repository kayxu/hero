package com.joymeng.game.domain.box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.Notice;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.hero.Hero;
import com.joymeng.game.domain.hero.HeroManager;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.equipment.Equip;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.item.props.PropsPrototype;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.response.ChatResp;
import com.joymeng.services.core.JoyServiceApp;

/**
 * 道具宝箱管理
 * @author madi
 *
 */
public class PropsBoxManager {
	
	public static int GAME_MONEY = 1;
	
	public static int FEAT = 2;
	
	public static int GOODS = 3;
	
	public static int EQUIP = 4;

	/**
	 * 需要提示等级的物品的propsType
	 */
	public static int[] NEED_LV = {1,4,7,17,22,30,28,29,33,34,11,12};
	
	static final Logger logger = LoggerFactory.getLogger(PropsBoxManager.class);
	
	private static PropsBoxManager propsBoxManager;
	
	public static PropsBoxManager getInstance(){
		if(null == propsBoxManager){
			propsBoxManager = new PropsBoxManager();
		}
		return propsBoxManager;
	}
	
	/**
	 * 道具宝箱数据
	 * @param path
	 * @throws Exception
	 */
	public HashMap<Integer,PropsBox> propsBoxesMap = new HashMap<Integer,PropsBox>();
	
	/**
	 * 包裹数据
	 * @param path
	 * @throws Exception
	 */
	public HashMap<Integer,Package> packageMap = new HashMap<Integer,Package>();
	
	/**
	 * 装备数据
	 */
	public HashMap<Integer,Equip> equipMap = new HashMap<Integer,Equip>();
	
	public Map<Integer,PropsPrototype> propsPrototypes = PropsManager.getInstance().propsDatas;
	
	public void loadPropsBoxes(String path) throws Exception{
		logger.info("------------------------------加载道具宝箱数据-----------------------");
		List<Object> list = GameDataManager.loadData(path, PropsBox.class);
		for (Object o : list){
			PropsBox pb = (PropsBox) o;
			/*logger.info(pb.getId() + " " + pb.getMoney1() + " " + pb.getMoneyRates1() +" " + pb.getFeats1() + " " +  pb.getFeatRates1() + " " 
					+ pb.getGoods1() + " " + pb.getGoodRates1() + " " + pb.getPackages1() + " " + pb.getPackageRates1());*/
			propsBoxesMap.put(pb.getId(), pb);
		}
		logger.info("记录条数：" + propsBoxesMap.size());
		logger.info("----------------------------道具宝箱数据加载完毕---------------------");
		
		logger.info("----------------------------加载包裹数据-----------------------------");
		List<Object> packageList = GameDataManager.loadData(path, Package.class);
		for(Object o : packageList){
			Package p = (Package) o;
			packageMap.put(p.getId(), p);
			logger.info(p.getId() + " name: " + p.getName() + " equipId: " + p.getEquipId() + " equipNum: "
					+  p.getEquipNum() + " itemId: " + p.getItemId() + " itemNum: " + p.getItemNum());
		}
		logger.info("记录条数：" + packageList.size());
		logger.info("----------------------------包裹数据加载完毕---------------------");
		
		logger.info("---------------------------加载装备数据--------------------------");
		List<Object> equipList = GameDataManager.loadData(path, Equip.class);
		for(Object o : equipList){
			Equip e = (Equip)o;
			equipMap.put(e.getId(), e);
		}
		logger.info("记录条数：" + equipMap.size());
		logger.info("---------------------------加载装备数据完毕--------------------------");
	}
	
	public boolean openNewBag(PlayerCharacter player){//打开新手打礼包
		if(player.getData().getLevel() < 4){
			String msg = I18nGreeting.getInstance().getMessage("propsbox.newbag.level",null);
			GameUtils.sendTip(
					new TipMessage(msg, ProcotolType.PROPS_BOX_RESP,
							GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			return false;
		}
		return true;
	}
	
	
	/**
	 * 获得一个道具
	 * _index int PropsBox id
	 * 
	 */
	public boolean getAProp(int _index,int propsId,PlayerCharacter player){
		
		
		PropsBox propsBox = propsBoxesMap.get(_index);
		if(propsBox == null){
			logger.info("!!!!!!!!!所发的道具宝箱下标不存在" + "index=" + _index);
			return false;
		}
		String boxName = propsBox.getName();
		
		if(propsId == 602){//如果是新手大礼包
			if(player.getData().getLevel() < 4){
				logger.info("4级可领取价值1888钻新手至尊礼包！");
				String msg = I18nGreeting.getInstance().getMessage("propsbox.newbag.level",null);
				GameUtils.sendTip(
						new TipMessage(msg, ProcotolType.PROPS_BOX_RESP,
								GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				return false;
			}
			else{
				if(player.getData().getNewBag() == 1){
					GameUtils.sendTip(
							new TipMessage("您无法再次领取新手大礼包", ProcotolType.PROPS_BOX_RESP,
								GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
					return false;
				}
			
				player.getData().setNewBag((byte)1);//将新手大礼包置为不可用
			}
					
		}
		else{//如果不是新手大礼包
			//主动推送客户端
			Cell cell = player.getPlayerStorageAgent().dellCell(propsId, 1);
			if(cell == null){
				GameUtils.sendTip(
						new TipMessage("不合法的请求", ProcotolType.PROPS_BOX_RESP,
								GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				return false;
			}
			else{
				Props props = (Props)cell.getItem();
				logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
						+ " propsName=" + props.getPrototype().getName() + " useNumber=1"+ " uid=" + player.getId() 
						+ " uname=" + player.getData().getName());
				
				GameLog.logSystemEvent(LogEvent.USE_PROPS, String.valueOf(props.getPrototype().getId()),String.valueOf(props.getPrototype().getName()),String.valueOf(1 )
						,player.getId() + "",TimeUtils.now().format(TimeUtils.FORMAT1));
			}
			RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
			rms.addModule(cell);
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms,player);
		}
		
		
//		logger.info(">>>>>>>>>>>获取道具宝箱平凡掉落" + propsBox.getId());
		String money1 = propsBox.getMoney1();
		String[] twoMoney1 = money1.split(":");
		String moneyRates1 = propsBox.getMoneyRates1();
		String[] twoMoneyRates1 = moneyRates1.split(":");
		String feats1 = propsBox.getFeats1();
		String[] twoFeats1 = feats1.split(":");
		String featRates1 = propsBox.getFeatRates1();
		String[] twoFeatRates1 = featRates1.split(":");
		String goods1 = propsBox.getGoods1();
		String[] fiveGoods1 = goods1.split(":");
		String goodRates1 = propsBox.getGoodRates1();
		String[] fiveGoodRates1 = goodRates1.split(":");
		String packages1 = propsBox.getPackages1();
		String[] threePackages1 = packages1.split(":");
		String packageRates1 = propsBox.getPackageRates1();
		String[] threePackageRates1 = packageRates1.split(":");
		int[] colorRates1 = {propsBox.getWhiteRate1(),propsBox.getGreenRate1()
				,propsBox.getBlueRate1(),propsBox.getPurpleRate1()
				,propsBox.getOrangeRate1()};
		
//		logger.info(">>>>>>>>>>>获取道具宝箱爆发掉落" + propsBox.getId());
		String money2 = propsBox.getMoney2();
		String[] twoMoney2 = money2.split(":");
		String moneyRates2 = propsBox.getMoneyRates2();
		String[] twoMoneyRates2 = moneyRates2.split(":");
		String feats2 = propsBox.getFeats2();
		String[] twoFeats2 = feats2.split(":");
		String featRates2 = propsBox.getFeatRates2();
		String[] twoFeatRates2 = featRates2.split(":");
		String goods2 = propsBox.getGoods2();
		String[] fiveGoods2 = goods2.split(":");
		String goodRates2 = propsBox.getGoodRates2();
		String[] fiveGoodRates2 = goodRates2.split(":");
		String packages2 = propsBox.getPackages2();
		String[] threePackages2 = packages2.split(":");
		String packageRates2 = propsBox.getPackageRates2();
		String[] threePackageRates2 = packageRates2.split(":");
		int[] colorRates2 = {propsBox.getWhiteRate2(),propsBox.getGreenRate2()
				,propsBox.getBlueRate2(),propsBox.getPurpleRate2()
				,propsBox.getOrangeRate2()};
		
		//道具出现的几率
		int[] propRates = {Integer.parseInt(twoMoneyRates1[0]),Integer.parseInt(twoMoneyRates1[1])
				,Integer.parseInt(twoFeatRates1[0]),Integer.parseInt(twoFeatRates1[1])
				,Integer.parseInt(fiveGoodRates1[0]),Integer.parseInt(fiveGoodRates1[1])
				,Integer.parseInt(fiveGoodRates1[2]),Integer.parseInt(fiveGoodRates1[3])
				,Integer.parseInt(fiveGoodRates1[4]),Integer.parseInt(threePackageRates1[0])
				,Integer.parseInt(threePackageRates1[1]),Integer.parseInt(threePackageRates1[2])
				,Integer.parseInt(twoMoneyRates2[0]),Integer.parseInt(twoMoneyRates2[1])
				,Integer.parseInt(twoFeatRates2[0]),Integer.parseInt(twoFeatRates2[1])
				,Integer.parseInt(fiveGoodRates2[0]),Integer.parseInt(fiveGoodRates2[1])
				,Integer.parseInt(fiveGoodRates2[2]),Integer.parseInt(fiveGoodRates2[3])
				,Integer.parseInt(fiveGoodRates2[4]),Integer.parseInt(threePackageRates2[0])
				,Integer.parseInt(threePackageRates2[1]),Integer.parseInt(threePackageRates2[2])
				};
		
		//所在索引位置的道具是哪一种，0,1,12,13-金币，2,3,14,15-功勋，4,5,6,7,8,16,17,18,19,20-物品，9,10,11,21,22,23-包裹
		int[] what = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
		
		//道具内容，如果是金币或功勋则为数量，如果为物品或包裹则为id
		int[] content = {Integer.parseInt(twoMoney1[0]),Integer.parseInt(twoMoney1[1])
				,Integer.parseInt(twoFeats1[0]),Integer.parseInt(twoFeats1[1])
				,Integer.parseInt(fiveGoods1[0]),Integer.parseInt(fiveGoods1[1])
				,Integer.parseInt(fiveGoods1[2]),Integer.parseInt(fiveGoods1[3])
				,Integer.parseInt(fiveGoods1[4]),Integer.parseInt(threePackages1[0])
				,Integer.parseInt(threePackages1[1]),Integer.parseInt(threePackages1[2])
				,Integer.parseInt(twoMoney2[0]),Integer.parseInt(twoMoney2[1])
				,Integer.parseInt(twoFeats2[0]),Integer.parseInt(twoFeats2[1])
				,Integer.parseInt(fiveGoods2[0]),Integer.parseInt(fiveGoods2[1])
				,Integer.parseInt(fiveGoods2[2]),Integer.parseInt(fiveGoods2[3])
				,Integer.parseInt(fiveGoods2[4]),Integer.parseInt(threePackages2[0])
				,Integer.parseInt(threePackages2[1]),Integer.parseInt(threePackages2[2])};
		boolean needTip = false;
		
		//30,31,32为木材包、矿石包、马匹包,48,50,51为大木材包，大马匹包，大铁矿包，52为金钱大礼包
		if((_index >= 38 &&  _index <= 44) || _index == 48 ||  (_index >= 30 && _index <= 32) || (_index >= 49 && _index <= 52)){
			needTip = true;
		}
		
		boolean isGetAll = false;//是否取出包裹里的所用物品或装备
		
		if(_index == 45 || _index == 48 || _index == 46
				|| (_index >= 53 && _index <= 55) || _index == 57 || _index == 66){//45推荐大礼包中，包裹打开,48新手大礼包,46发展礼包大礼包
			//53火神技能包, 54 神秘加制包, 55 雷神技能包 ,57 混沌技能包 ,66史诗神技
			isGetAll = true;
		}
		
		int which = MathUtils.getRandomId1(what, propRates, 10000);
		dealWithGetWhich(content,colorRates1,colorRates2,which,player,needTip,boxName,isGetAll);
		return true;
		
	}
	
	
	/**
	 * 处理取包裹里的装备或物品
	 * @param content 道具内容，如果是金币或功勋则为数量，如果为物品或包裹则为id
	 * @param colorRates1 品质几率，即白绿蓝紫橙装出现的几率
	 * @param colorRates2品质几率
	 * @param which 是哪一个，是什么
	 * @param needTip 是否需要提示
	 * @param isGetAll 是否需要拿出包裹中的所有
	 * @param boxName 打开宝箱的名称
	 * 
	 */
	private void dealWithGetWhich(int[] content,int[]colorRates1,int[]colorRates2,int which,PlayerCharacter player,boolean needTip,String boxName,boolean isGetAll){
		
		if(which == 0 || which == 1 || which == 12 || which == 13){//按概率计算得到的是金币
			int moneyValue = content[which];
			player.saveResources(GameConfig.GAME_MONEY, moneyValue);
			player.getData().setTempEquipIds("");
			player.getData().setTempItemIds("");
			player.getData().setTempWhatForPropsBox(PropsBoxManager.GAME_MONEY);
			player.getData().setTempGoodOrEquipIdOrNeedValue(moneyValue);
			if(needTip){
				String msg = I18nGreeting.getInstance().getMessage("propsbox.money", new Object[]{moneyValue});
				GameUtils.sendTip(new TipMessage(msg
						, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}
			
			if(which == 12 || which == 13){//如果是爆发掉落，全服滚动玩家获取
				String msg = I18nGreeting.getInstance().getMessage("prob.box.get",
						new Object[]{player.getData().getName(),boxName,moneyValue," gold"});
				NoticeManager.getInstance().sendSystemWorldMessage(msg);// (byte)1
			}
			
		
		}
		if(which == 2 || which == 3 || which == 14 || which == 15){//功勋
			int featValue = content[which];
			player.saveResources(GameConfig.AWARD, featValue);
			player.getData().setTempEquipIds("");
			player.getData().setTempItemIds("");
			player.getData().setTempWhatForPropsBox(PropsBoxManager.FEAT);
			player.getData().setTempGoodOrEquipIdOrNeedValue(featValue);
			if(needTip){
				String msg = I18nGreeting.getInstance().getMessage("propsbox.feat", new Object[]{featValue});
				GameUtils.sendTip(new TipMessage(msg
					, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}
			
			if(which == 14 || which == 15){//如果是爆发掉落，全服滚动玩家获取
				/*sendMessageInWorldChannel(new TipMessage("恭喜玩家" + player.getData().getName() 
						+ "开启" + boxName 
						+ "获得了" + featValue + "功勋",0,GameConst.GAME_RESP_SUCCESS), (byte)1);*/
				String msg = I18nGreeting.getInstance().getMessage("prob.box.get",
						new Object[]{player.getData().getName(),boxName,featValue," honor."});
				NoticeManager.getInstance().sendSystemWorldMessage(msg);
				
			}
			
		}
		if(which == 4 || which == 5 || which == 6 || which == 7 || which == 8
				|| which == 16 || which == 17 || which == 18 || which == 19 || which == 20){//物品
			int goodsId = content[which];
			//player.getData().setTempGoodOrEquipId(goodsId);
			player.getData().setTempEquipIds("");
			player.getData().setTempItemIds("");
			player.getData().setTempWhatForPropsBox(GOODS);
			player.getData().setTempGoodOrEquipIdOrNeedValue(goodsId);
			Cell cell = player.getPlayerStorageAgent().addPropsCell(goodsId, 1);
			//主动推送客户端
			RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
			rms.addModule(cell);
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms,player);
			if(needTip){
				String msg = I18nGreeting.getInstance().getMessage("propsbox.goodequip", new Object[]{propsPrototypes.get(goodsId).getName(),1});
				GameUtils.sendTip(new TipMessage(msg
						, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}
			if(which == 16 || which == 17 || which == 18 || which == 19 || which == 20){//如果是爆发掉落，全服滚动玩家获取
				String msg = I18nGreeting.getInstance().getMessage("prob.box.get.prop",
						new Object[]{player.getData().getName(),boxName,propsPrototypes.get(goodsId).getName()});
				NoticeManager.getInstance().sendSystemWorldMessage(msg);
			}
			
			logger.info("已获取1个" + propsPrototypes.get(goodsId).getName());
		}
		if(which == 9 || which == 10 || which == 11
				|| which == 21 || which == 22|| which == 23){
			int packageId = content[which];
			
			if(isGetAll){//推荐大礼包中，包裹打开,特殊处理
				if(which == 9 || which == 10 || which == 11){//按平凡掉落中品质几率计算
					openPackage(packageId,colorRates1,player);
				}
				if(which == 21 || which == 22 || which == 23){//按爆发掉落中品质几率计算
					openPackage(packageId,colorRates2,player);
				}
				return;
			}
			
			//进入包裹选择装备，物品
			Package p = packageMap.get(packageId);
			String equipId = p.getEquipId();
			String equipNum = p.getEquipNum();
			String itemId = p.getItemId();
			String itemNum = p.getItemNum();
			
			player.getData().setTempEquipIds(equipId);
			player.getData().setTempItemIds(itemId);
			
			//如果装备列表不为空
			if(!"".equals(equipId) && !"".equals(equipNum)){
				String[] equipIds = equipId.split(":");
				String[] equipNums = equipNum.split(":");
				int index = MathUtils.random(equipIds.length);
				
				//按几率获取装备品质
				int[] colorArray = {1,2,3,4,5};//1-白装，2-绿装，3-蓝装，4-紫装，5-橙装
				int qId = 0;
				if(which == 9 || which == 10 || which == 11){//按平凡掉落中品质几率计算
					qId = MathUtils.getRandomId1(colorArray, colorRates1, 100);
				}
				if(which == 21 || which == 22 || which == 23){//按爆发掉落中品质几率计算
					qId = MathUtils.getRandomId1(colorArray, colorRates2, 100);
				}
					
				int sId = 0;//强化
				Equipment equ = EquipmentManager.getInstance().getEqu(Integer.parseInt(equipIds[index]), qId, sId);
				
				//返回装备id和装备数量
				if(null != equ){
					int id = equ.getId();
					
					player.getData().setTempWhatForPropsBox(EQUIP);
					player.getData().setTempGoodOrEquipIdOrNeedValue(id);
					player.getData().setTempGoodsOrEquipNumForPropsBox(Integer.parseInt(equipNums[index]));
					List<Cell> cellList = player.getPlayerStorageAgent().addEqui(id, Integer.parseInt(equipNums[index]), 0);
					//主动推送客户端
					RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
					rms.addModule(cellList.get(0));
					rms.addModule(player.getData());
					AndroidMessageSender.sendMessage(rms,player);
					if(needTip){
						String msg = I18nGreeting.getInstance().getMessage("propsbox.goodequip", new Object[]{equipMap.get(Integer.parseInt(equipIds[index])).getName(),equipNums[index]});
						GameUtils.sendTip(new TipMessage(msg
								, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
					}
					if(which == 21 || which == 22 || which == 23){//如果是爆发掉落，全服滚动玩家获取
						String msg = I18nGreeting.getInstance().getMessage("prob.box.get.prop",
								new Object[]{player.getData().getName(),boxName,equipMap.get(Integer.parseInt(equipIds[index])).getName()});
						NoticeManager.getInstance().sendSystemWorldMessage(msg);
					}
					logger.info("已获取" + equipNums[index] + "个" + propsPrototypes.get(Integer.parseInt(equipIds[index])).getName());
					
					
				}
				
			}
			
			//如果物品列表不为空
			if(!"".equals(itemId) && !"".equals(itemNum)){
				String[] itemIds = itemId.split(":");
				String[] itemNums = itemNum.split(":");
				int index = MathUtils.random(itemIds.length);
				
				//返回物品id和物品数量
				player.getData().setTempWhatForPropsBox(GOODS);
				player.getData().setTempGoodOrEquipIdOrNeedValue(Integer.parseInt(itemIds[index]));
				player.getData().setTempGoodsOrEquipNumForPropsBox(Integer.parseInt(itemNums[index]));
				//player.getData().setTempGoodOrEquipId(Integer.parseInt(itemIds[index]));
				Cell cell = player.getPlayerStorageAgent().addPropsCell(Integer.parseInt(itemIds[index]), Integer.parseInt(itemNums[index]));
				//主动推送客户端
				RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
				rms.addModule(cell);
				rms.addModule(player.getData());
				AndroidMessageSender.sendMessage(rms,player);
				logger.info("" + propsPrototypes.size());
				if(needTip){
					String msg = I18nGreeting.getInstance().getMessage("propsbox.goodequip", new Object[]{propsPrototypes.get(Integer.parseInt(itemIds[index])).getName(),itemNums[index]});
					GameUtils.sendTip(new TipMessage(msg 
							, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				}
				if(which == 21 || which == 22 || which == 23){//如果是爆发掉落，全服滚动玩家获取
					String msg = I18nGreeting.getInstance().getMessage("prob.box.get.prop",
							new Object[]{player.getData().getName(),boxName,propsPrototypes.get(Integer.parseInt(itemIds[index])).getName()});
					NoticeManager.getInstance().sendSystemWorldMessage(msg);
				}
				logger.info("已获取" + itemNums[index] + "个" + propsPrototypes.get(Integer.parseInt(itemIds[index])).getName());
				
			}
		
		}
	}
	
	/**
	 * 打开包裹每次发给客户端一个装备
	 * whichPackage 关联id
	 * packageId 包裹id 对应PropsPrototype.xml中id
	 */
	public boolean openPackageOnceAItem(int whichPackage,int packageId,PlayerCharacter player){
		
		int[] needTotalPackageIds = {16,17,18,19,20,21,22,23,60,68};//可打开的包裹们,60至尊套装,68 终极神装
		
		//找请求包裹不是指定的可打开的包裹
		boolean find = false;
		for(int i=0;i< needTotalPackageIds.length;i++){
			if(whichPackage == needTotalPackageIds[i]){
				find = true;
				break;
			}
		}
		if(!find){//未找到可打开的请求包裹
			logger.info("!!!!!!!!!所发的包裹id不存在" + "包裹关联id=" + whichPackage);
			return false;
		}
		StringBuffer sbEquip = new StringBuffer();
		StringBuffer sbItem = new StringBuffer();
		
		//如果请求的是后面的三个4个全选的装备盒
		//-------------------------------与带有此类型的注释else对应----------------------
		if(whichPackage == 21 || whichPackage == 22 || whichPackage == 23 || whichPackage == 60 || whichPackage == 68){
			for(int id : needTotalPackageIds){
				Package pe = packageMap.get(id);
				if(pe == null){//防止错误
					logger.info("XXXXXXXXXXXX:"+id);
					continue;
				}
				if(!"".equals(pe.getEquipId())){
					sbEquip.append(pe.getEquipId());
					sbEquip.append(":");
				}
				if(!"".equals(pe.getItemId())){
					sbItem.append(pe.getItemId());
					sbItem.append(":");
				}
			}
			
			//供客户端从此列表中选取
			player.getData().setTempTotalEquipIds(sbEquip.toString());
			player.getData().setTempTotalItemIds(sbItem.toString());
			Package p = packageMap.get(whichPackage);
			if(p == null){
				return false;
			}
			String equipId = p.getEquipId();
			String equipNum = p.getEquipNum();
			String itemId = p.getItemId();
			String itemNum = p.getItemNum();
			StringBuffer sbequ = new StringBuffer();//存放装备id含品质和强化等级
			if(!"".equals(equipId) && !"".equals(equipNum)){
				String[] equipIds = equipId.split(":");
				String[] equipNums = equipNum.split(":");		
				List<Cell> cellList = new ArrayList<Cell>();
				
				if(whichPackage == 60){//如果是至尊套装
					for(int i=0;i<equipIds.length;i++){
						Equipment equ = EquipmentManager.getInstance().getEqu(Integer.parseInt(equipIds[i]), 5, 7);//开7级橙色装备
						sbequ.append(equ.getId()).append(":");
						if(equ != null){
							List<Cell> tempCellList = player.getPlayerStorageAgent().addEqui(equ.getId(), Integer.parseInt(equipNums[i]), 0);
							for(Cell c : tempCellList){
								cellList.add(c);
							}
						}
					}
				}
				else{
					for(int i=0;i<equipIds.length;i++){
						Equipment equ = EquipmentManager.getInstance().getEqu(Integer.parseInt(equipIds[i]), 5, 0);//开橙色装备
						sbequ.append(equ.getId()).append(":");
						if(equ != null){
							List<Cell> tempCellList = player.getPlayerStorageAgent().addEqui(equ.getId(), Integer.parseInt(equipNums[i]), 0);
							for(Cell c : tempCellList){
								cellList.add(c);
							}
						}
					}
				}
				
				
				//主动推送客户端
				RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
				for(Cell c : cellList){
					rms.addModule(c);
				}
				rms.addModule(player.getData());
				AndroidMessageSender.sendMessage(rms,player);
				
				
			}
			if(!"".equals(itemId) && !"".equals(itemNum)){
				String[] itemIds = itemId.split(":");
				String[] itemNums = itemNum.split(":");
				
				List<Cell> cellList2 = new ArrayList<Cell>();
				//主动推送客户端
				RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
				for(int i=0;i<itemIds.length;i++){
					Cell cell = player.getPlayerStorageAgent().addPropsCell(Integer.parseInt(itemIds[i]), Integer.parseInt(itemNums[i]));
					cellList2.add(cell);
				}
				for(Cell c : cellList2){
					rms.addModule(c);
				}
				rms.addModule(player.getData());
				AndroidMessageSender.sendMessage(rms,player);
			
			}
			
			player.getData().setTempEquipIds(sbequ.toString());
			player.getData().setTempItemIds(itemId);
			//主动推送客户端,销毁包裹
			Cell cell = player.getPlayerStorageAgent().dellCell(packageId, 1);
			if(cell == null){
				return false;
			}
			else{
				Props props = (Props)cell.getItem();
				logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
						+ " propsName=" + props.getPrototype().getName() + " useNumber=1"+ " uid=" + player.getId() 
						+ " uname=" + player.getData().getName());
				
				GameLog.logSystemEvent(LogEvent.USE_PROPS, String.valueOf(props.getPrototype().getId()),props.getPrototype().getName(),String.valueOf(1)
						,String.valueOf(player.getId()));
			}
			RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
			rms.addModule(cell);
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms,player);
		}
		
		//选取一个装备的情况
		//-------------------------------与带有此类型注释的if对应----------------------
		else{
			Package pe = packageMap.get(whichPackage);
			PropsBox propsBox = propsBoxesMap.get(whichPackage);
			int[] colorRate = {propsBox.getWhiteRate1(),propsBox.getGreenRate1(),propsBox.getBlueRate1()
					,propsBox.getPurpleRate1(),propsBox.getOrangeRate1()};
			int[] colorArray = {1,2,3,4,5};//1-白装，2-绿装，3-蓝装，4-紫装，5-橙装
			int color = MathUtils.getRandomId1(colorArray, colorRate, 100);
			
			if(pe == null){
				return false;
			}
			if(!"".equals(pe.getEquipId())){
				sbEquip.append(pe.getEquipId());
			}
			if(!"".equals(pe.getItemId())){
				sbItem.append(pe.getItemId());
			}
			//供客户端从此列表中选取
			player.getData().setTempTotalEquipIds(sbEquip.toString());
			player.getData().setTempTotalItemIds(sbItem.toString());
			String equipId = pe.getEquipId();
			String equipNum = pe.getEquipNum();
			String itemId = pe.getItemId();
			String itemNum = pe.getItemNum();
			
			StringBuffer sbequ = new StringBuffer();//存放装备id含品质和强化等级
			if(!"".equals(equipId) && !"".equals(equipNum)){
				String[] equipIds = equipId.split(":");
				String[] equipNums = equipNum.split(":");		
				List<Cell> cellList = new ArrayList<Cell>();
				
				int index = MathUtils.random(equipIds.length);
				Equipment equ = EquipmentManager.getInstance().getEqu(Integer.parseInt(equipIds[index]), color, 0);//开**色装备
				sbequ.append(equ.getId());
				if(equ != null){
					List<Cell> tempCellList = player.getPlayerStorageAgent().addEqui(equ.getId(), Integer.parseInt(equipNums[index]), 0);
					for(Cell c : tempCellList){
						cellList.add(c);
					}
				}
				
				//主动推送客户端
				RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
				for(Cell c : cellList){
					rms.addModule(c);
				}
				rms.addModule(player.getData());
				AndroidMessageSender.sendMessage(rms,player);
				
				
			}
			if(!"".equals(itemId) && !"".equals(itemNum)){
				String[] itemIds = itemId.split(":");
				String[] itemNums = itemNum.split(":");
				
				//主动推送客户端
				RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
				int index = MathUtils.random(itemIds.length);
				Cell cell = player.getPlayerStorageAgent().addPropsCell(Integer.parseInt(itemIds[index]), Integer.parseInt(itemNums[index]));
				rms.addModule(cell);
				rms.addModule(player.getData());
				AndroidMessageSender.sendMessage(rms,player);
			
			}
			
			player.getData().setTempEquipIds(sbequ.toString());
			player.getData().setTempItemIds(itemId);
			//主动推送客户端,销毁包裹
			Cell cell = player.getPlayerStorageAgent().dellCell(packageId, 1);
			if(cell == null){
				return false;
			}
			else{
				Props props = (Props)cell.getItem();
				logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
						+ " propsName=" + props.getPrototype().getName() + " useNumber=1"+ " uid=" + player.getId() 
						+ " uname=" + player.getData().getName());
				GameLog.logSystemEvent(LogEvent.USE_PROPS, String.valueOf(props.getPrototype().getId()),props.getPrototype().getName(),String.valueOf(1)
						,String.valueOf(player.getId()));
			}
			RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
			rms.addModule(cell);
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms,player);
		}
		
		return true;
		
	}
	
	/**
	 * 推荐大礼包中，包裹打开，61新手大礼包packageId
	 * @param packageId
	 * @return
	 */
	public boolean openPackage(int packageId,int[]colorRates,PlayerCharacter player){
		if(packageId == 61){//如果是新手大礼包，额外发送金币20w
			player.saveResources(GameConfig.GAME_MONEY, 200000);
		}
		Package p = packageMap.get(packageId);
		if(p == null){
			return false;
		}
		String equipId = p.getEquipId();
		String equipNum = p.getEquipNum();
		String itemId = p.getItemId();
		String itemNum = p.getItemNum();
		StringBuffer sbequ = new StringBuffer();//存放装备id含品质和强化等级
		if(!"".equals(equipId) && !"".equals(equipNum)){
			String[] equipIds = equipId.split(":");
			String[] equipNums = equipNum.split(":");
			int[] colorArray = {1,2,3,4,5};//1-白装，2-绿装，3-蓝装，4-紫装，5-橙装
			int qId = MathUtils.getRandomId1(colorArray, colorRates, 100);
			int sId = 0;//强化
			List<Cell> cellList = new ArrayList<Cell>();
			
			for(int i=0;i<equipIds.length;i++){
				Equipment equ = EquipmentManager.getInstance().getEqu(Integer.parseInt(equipIds[i]), qId, sId);//开橙色装备
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(new TipMessage("Get " + equ.getName() + ",please check your bag"
							, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(new TipMessage("获得了装备" + equ.getName() + "，请查收"
							, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				}
				
				if(equ != null){
					List<Cell> tempCellList = player.getPlayerStorageAgent().addEqui(equ.getId(), Integer.parseInt(equipNums[i]), 0);
					for(Cell c : tempCellList){
						cellList.add(c);
					}
				}
			}
			//主动推送客户端
			RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
			for(Cell c : cellList){
				rms.addModule(c);
			}
			
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms,player);
	
		}
		if(!"".equals(itemId) && !"".equals(itemNum)){
			String[] itemIds = itemId.split(":");
			String[] itemNums = itemNum.split(":");
			
			List<Cell> cellList = new ArrayList<Cell>();
			//主动推送客户端
			RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
			for(int i=0;i<itemIds.length;i++){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(new TipMessage("get " +itemNums[i] +" " + propsPrototypes.get(Integer.parseInt(itemIds[i])).getName()+ ",please check your bag"
							, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(new TipMessage("获得了" +itemNums[i] +"个" + propsPrototypes.get(Integer.parseInt(itemIds[i])).getName()+ "，请查收"
							, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				}
				
				Cell cell = player.getPlayerStorageAgent().addPropsCell(Integer.parseInt(itemIds[i]), Integer.parseInt(itemNums[i]));
				cellList.add(cell);
			}
			for(Cell c : cellList){
				rms.addModule(c);
			}
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms,player);
		
		}
		
		player.getData().setTempEquipIds(sbequ.toString());
		player.getData().setTempItemIds(itemId);
		/*//主动推送客户端,销毁包裹
		Cell cell = player.getPlayerStorageAgent().dellCell(packageId, 1);
		if(cell == null){
			return false;
		}
		else{
			Props props = (Props)cell.getItem();
			logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
					+ " propsName=" + props.getPrototype().getName() + " useNumber=1"+ " uid=" + player.getId() 
					+ " uname=" + player.getData().getName());
			
			GameLog.logSystemEvent(LogEvent.USE_PROPS, String.valueOf(props.getPrototype().getId()),props.getPrototype().getName(),String.valueOf(1)
					,String.valueOf(player.getId()));
		}
		RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
		rms.addModule(cell);
		rms.addModule(player.getData());
		AndroidMessageSender.sendMessage(rms,player);*/
		return true;
		
	}
	
	
	/**
	 * 根据武将卡id获取一个武将
	 * @param heroCardId
	 */
	public boolean getHero(int heroCardId,PlayerCharacter player){
		// 主动推送客户端,销毁武将卡 
		//by xufangliang
		Cell cell = player.getPlayerStorageAgent().dellCell(heroCardId, 1);
		if (cell == null) {
			return false;
		}
		Map<Integer,PropsPrototype> propsPrototypes = PropsManager.getInstance().propsDatas;
		PropsPrototype propsPrototype = propsPrototypes.get(heroCardId);
		int from = Integer.parseInt(propsPrototype.getProperty2());
		int to = Integer.parseInt(propsPrototype.getProperty3());
		Hero hero = HeroManager.getInstance().randomPropHero(from, to);
		
		if(hero == null){
			return false;
		}
		PlayerHero playerHero = player.getPlayerHeroManager().addHero(hero);
		RespModuleSet rms = new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
		rms.addModule(playerHero);
		AndroidMessageSender.sendMessage(rms,player);
		player.getData().setTempGetPlayerHeroId(playerHero.getId());
		player.getData().setTempPlayerHero(playerHero);
		player.getData().setTempHeroId(hero.getBaseId());
		
		//主动推送客户端,销毁武将卡
		//Cell cell = player.getPlayerStorageAgent().dellCell(heroCardId, 1);
		
		if(null != cell){
			Props props = (Props)cell.getItem();
			logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
					+ " propsName=" + props.getPrototype().getName() + " useNumber=1"+ " uid=" + player.getId() 
					+ " uname=" + player.getData().getName());
			
			GameLog.logSystemEvent(LogEvent.USE_PROPS, String.valueOf(props.getPrototype().getId()),props.getPrototype().getName(),String.valueOf(1)
					,String.valueOf(player.getId()));
		}
		
		RespModuleSet rms2=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
		rms2.addModule(cell);
		rms2.addModule(player.getData());
		AndroidMessageSender.sendMessage(rms2,player);
		player.getData().setTempGetPlayerHeroId(playerHero.getId());
		return true;
	}
	
	/**
	 * 确认获取武将
	 */
	public void confirmGetHero(PlayerCharacter player){
		if(I18nGreeting.LANLANGUAGE_TIPS ==1){
			GameUtils.sendTip(new TipMessage("Hero have issued", ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
		}else{
			GameUtils.sendTip(new TipMessage("武将已下发", ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
		}
		
	}
	
	/**
	 * 取消获取武将
	 * @param player
	 */
	public void cancelGetHero(PlayerCharacter player){
		int heroId = player.getData().getTempHeroId();
		int value = 0;
		if(heroId >= 0 && heroId<=9){
			value = 2000;
		}
		else if(heroId >= 10 && heroId<=18){
			value = 4000;
		}
		else if(heroId >= 19 && heroId<=27){
			value = 8000;
		}
		else if(heroId >= 28 && heroId<=36){
			value = 16000;
		}
		else if(heroId >= 37 && heroId<=57){
			value = 32000;
		}
		else if(heroId >= 58 && heroId<=107){
			value = 64000;
		}
		
		player.saveResources(GameConfig.GAME_MONEY, value);
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			GameUtils.sendTip(new TipMessage("You get " + value + " gold back for cancling hero card", ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
		}else{
			GameUtils.sendTip(new TipMessage("恭喜您获得武将卡返还" + value + "金币", ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
		}
		//player.getPlayerHeroManager().removeHero(player.getData().getTempPlayerHero());
	}
	
	
	//根据道具id获取钻石和筹码
	public boolean getDiamondAndChip(int propsId,PlayerCharacter player){
		
		PropsPrototype propsPrototype = propsPrototypes.get(propsId);
		int value = Integer.parseInt(propsPrototype.getProperty3());
		if(propsId == 41 || propsId == 594 || propsId == 550){//钻石
			player.saveResources(GameConfig.JOY_MONEY, value);
			String msg = I18nGreeting.getInstance().getMessage(
					"propsbox.diamond", new Object[] {value});
			GameUtils.sendTip(new TipMessage(msg, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			logger.info("获取" + value + "个钻石");
		}
		if(propsId == 45 || propsId == 607){//筹码
			player.getData().setChip(player.getData().getChip() + value);
			String msg = I18nGreeting.getInstance().getMessage("propsbox.chip", new Object[]{value});
			GameUtils.sendTip(new TipMessage(msg, ProcotolType.PROPS_BOX_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			logger.info("获取" + value + "个筹码");
		}
		//主动推送客户端,销毁道具
		Cell cell = player.getPlayerStorageAgent().dellCell(propsId, 1);
		
		//---日志
		Props props = (Props) cell.getItem();
		if(null != props){
			logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
					+ " propsName=" + props.getPrototype().getName() + " useNumber=1"+ " uid=" + player.getId() 
					+ " uname=" + player.getData().getName());
			
			GameLog.logSystemEvent(LogEvent.USE_PROPS, String.valueOf(props.getPrototype().getId()),props.getPrototype().getName(),String.valueOf(1)
					,String.valueOf(player.getId()));
		}
		
		RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
		rms.addModule(cell);
		rms.addModule(player.getData());
		AndroidMessageSender.sendMessage(rms,player);
		return true;
	}
	
	/**
	 * 提示信息中物品是否需要加lv
	 * @param goodsId
	 * @return
	 */
	private boolean needLv(int goodsId){
		for(int i : NEED_LV){
			if(propsPrototypes.get(goodsId).getPropsType() == i){
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * @deprecated
	 * 打开新的包裹
	 * @param propsPrototypeId PropsPrototype.xml中id
	 * @param player
	 */
	public void openNewBag(int propsPrototypeId,PlayerCharacter player){
		int MONEY_VALUE = 200000;
		
		//获得金币
		player.saveResources(GameConfig.GAME_MONEY, MONEY_VALUE);
		
		//获得**
		Cell cell = player.getPlayerStorageAgent().addPropsCell(propsPrototypeId, 1);
		
		RespModuleSet rms=new RespModuleSet(ProcotolType.PROPS_BOX_RESP);
		rms.addModule(cell);
		rms.addModule(player.getData());
		AndroidMessageSender.sendMessage(rms,player);
	}
	
}
