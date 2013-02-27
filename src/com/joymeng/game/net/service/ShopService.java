package com.joymeng.game.net.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.game.ErrorMessage;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.mod.LadderManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.shop.Goods;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.ShopRequest;
import com.joymeng.game.net.response.ShopResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 商店
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
@JoyMessageService
public class ShopService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(ShopService.class);

	private ShopService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleShop(ShopRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			return null;
		}
		byte type = request.getType();
		ShopResp resp = new ShopResp();//将领消息
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
		logger.info("shop from " + request.getJoyID()+" type="+type);
		RespModuleSet rms=new RespModuleSet(ProcotolType.SHOP_RESP);//模块消息
		switch (type) {
		case ProcotolType.SHOP_ALL_GOODS:
			List<Goods> lst = player.getPlayerShopManager().getAllGoods();
			if(lst != null){
				System.out.println("****************"+lst.size());
				for (Goods g : lst){
					rms.addModule(g);
				}
			}
			AndroidMessageSender.sendMessage(rms,player);
			return null;
		case ProcotolType.SHOP_GOOD_BUY:
			int id = request.getGoodId();
			int num = request.getNum();
			TipUtil tip = player.getPlayerShopManager().buyGoods(id,num);
			GameUtils.sendTip(tip.getTip(), request.getUserInfo(),GameUtils.FLUTTER);
			return null;
		case ProcotolType.SHOP_LADDER://购买天梯付费
			 num=request.getNum();
			if(LadderManager.buyChargeNum(player)){
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				rms.addModule(player.getData());//更新玩家属性
				AndroidMessageSender.sendMessage(rms,player);
			}else{
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.ERROR_CMD);
			}
			return resp;
		}
		return null;
	}
}
