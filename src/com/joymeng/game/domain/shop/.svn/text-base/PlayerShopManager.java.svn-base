package com.joymeng.game.domain.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.common.Instances;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.PlayerStorageAgent;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.item.props.PropsPrototype;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipUtil;

public class PlayerShopManager implements Instances{
	PlayerCharacter player;
	PlayerStorageAgent psa;
	GoodsManager gdMgr = GoodsManager.getInstance();
	private Logger logger = LoggerFactory.getLogger(PlayerShopManager.class);
	
	public  PlayerShopManager( PlayerCharacter player){
		this.player=player;
		this.init(player, player.getPlayerStorageAgent());
	}
	public void init(PlayerCharacter user ,PlayerStorageAgent p){
//		logger.info("加载用户商店");
		player = user;
		psa = p;
//		logger.info("用户："+player.getId()+"商店"+" 背包大小："+psa.getSize());
	}
	
	/**
	 * 获取全部商品
	 * @return
	 */
	public List<Goods> getAllGoods(){
		Map<Integer,Goods> map = gdMgr.getGoodsMap();
		if(map != null){
			for(Goods g : map.values()){
				g.setIsHotSell((byte)0);
			}
			List<Goods> hot = hotGoods();
			List<Goods> notHot = new ArrayList<>();
			//RespModuleSet rms=new RespModuleSet(ProcotolType.SHOP_RESP);//模块消息
			for(Goods g : map.values()){
				if(!hot.contains(g)){
					notHot.add(g);
				}
			}
			hot.addAll(notHot);
			System.out.println(hot);
			//AndroidMessageSender.sendMessage(rms,player);
			return hot;
		}
		return null;
	}
	
	/**
	 * 热卖商品
	 * @return
	 */
	public ArrayList<Goods> hotGoods(){
		boolean isHot = true;
		ArrayList<Goods> hotGoodLst = new ArrayList<Goods>();
		int[] hotGoods = gdMgr.getHotGoodsId();
		logger.info("商城：HOT:"+ hotGoods);
		if(hotGoods == null || hotGoods.length < 8){
			isHot = false;
		}else{
			for (int i = 0; i < hotGoods.length;i++){
				if(hotGoods[i] == 0){
					isHot = false;
					break;
				}
			}
		}
		if(!isHot){
			//默认物品 23,24,47,607,14,576,575,2
			hotGoods = new int[]{23,24,47,607,14,576,575,2};//134
		}
		
		logger.info("商城：HOT:"+ hotGoods);
		for(int i = 7 ; i >= 0;i--){
			Goods g = gdMgr.getGoodProps(hotGoods[i]);
			if(g != null){
				g.setIsHotSell((byte)1);
				hotGoodLst.add(g);
			}
		}
		return hotGoodLst;
	}
	
	public TipUtil buyGoods(int key,int num){
//		logger.info("物品："+key +" 数量："+num);
		TipUtil tip = new TipUtil(ProcotolType.SHOP_RESP);
		tip.setFailTip("购买失败!");
		Goods good = gdMgr.getGood(key);
		if(good != null){
			if(player.getPlayerStorageAgent().isFull()){
				tip.setFailTip("背包已满");
			}else if(num <=0){
				tip.setFailTip("选择数量！");
			}else if(player.getData().getJoyMoney() < num*good.getBuyPrice()){
				String msg = I18nGreeting.getInstance().getMessage("dimond.not.enough", null);
				tip.setFailTip(msg);
			}else{
				//扣钱
				player.saveResources(GameConfig.JOY_MONEY, -1*num*good.getBuyPrice());
//				logger.info("消耗："+-1*num*good.getBuyPrice());
//				logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " uid=" + player.getId() + " uname=" + player.getData().getName() 
//						+ " diamondNum=" + num * good.getBuyPrice() + " description=购买商品");
				GameLog.logSystemEvent(LogEvent.USE_DIAMOND, String.valueOf(GameConst.DIAMOND_BUYPROP),"", String.valueOf(num*good.getBuyPrice()),String.valueOf(player.getId()),"物品："+key +" 数量："+num);
				
				PropsPrototype p = PropsManager.getInstance().getProps(good.getGoodId());
				//放入背包
				if(p != null){
					Cell cell =  psa.addPropsCell(good.getGoodId(), num);
					if(null != cell){
						Props props = (Props)cell.getItem();
//						logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
//								+ " propsName=" + props.getPrototype().getName() + " buyNumber=" + num + " uid=" + player.getId() 
//								+ " uname=" + player.getData().getName());
						
						GameLog.logSystemEvent(LogEvent.BUY_PROPS, String.valueOf(props.getPrototype().getId()),props.getPrototype().getName()
								,String.valueOf(num),String.valueOf(player.getId()));
					}
				
					RespModuleSet rms=new RespModuleSet(ProcotolType.SHOP_RESP);
					rms.addModule(cell);
					AndroidMessageSender.sendMessage(rms,player);
//					World.getInstance().savePlayer(player);
					//tip.setFailTip("购买成功!");
					String msg = I18nGreeting.getInstance().getMessage("buy.success", null);
					tip.setFailTip(msg);
				}else{
					tip.setFailTip("购买道具不存在!");
				}
			}
		}
		return tip;
	}
}
