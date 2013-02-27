package com.joymeng.game.domain.sign;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.card.CardManager;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;

/**
 * 签到管理
 *
 */
public class SignManager {
	
	/**
	 * 已签到
	 */
	private static final int SIGNED = 1;
	
	/**
	 * 签到
	 */
	private static final int SIGNING = 2;
	
	/**
	 * 未签到
	 */
	private static final int NOT_SIGN = 3;
	
	/**
	 * 标志签到7次完成后的奖
	 */
	private static final int AWARD_FLAG = 4;
	
	/**
	 * 筹码
	 */
	private static final int P_CHIP = 0;
	
	/**
	 * 金币
	 */
	private static final int P_GOLD = -1;
	
	/**
	 * 功勋
	 */
	private static final int P_HONOR = -2;

	private static final Logger logger = LoggerFactory.getLogger(CardManager.class);
	
	PlayerCharacter player;
	
	public static HashMap<Integer,Day> daysMap = new HashMap<Integer,Day>();
	
	/**
	 * 玩家7天签到数据
	 */
	private SignModule[] signData = new SignModule[10];
	
	/**
	 * 当天是否已签过到
	 */
	private boolean isSigned = false;
	
	public SignManager(PlayerCharacter player){
		this.player = player;
	}
	
	/**
	 * 加载签到奖品数据
	 * @param path
	 * @throws Exception 
	 */
	public static void loadData(String path) throws Exception{
		logger.info("===================加载签到配置表");
		List<Object> list = GameDataManager.loadData(path, Day.class);
		for(Object o : list){
			Day days = (Day)o;
			daysMap.put(days.getId(), days);
		}
		logger.info("===================加载签到配置表完成" + daysMap.size());
	}
	
	/**
	 * 根据玩家登录初始化isSigned变量
	 */
	public void handleIsSigned(){
		logger.info("handle 本次打开UI时间：" + new Timestamp(System.currentTimeMillis()));
		logger.info("上次签到时间：" + new Timestamp(player.getData().getLastSignTime()));
		//如果玩家上次签到时间和本次打开UI时间不是同一天则设置是否签到过为false
		if(!TimeUtils.isSameDay(player.getData().getLastSignTime()) ){
			logger.info("signNum" + player.getData().getSignNum());
			logger.info("handle 本次登录时间：" + new Timestamp(TimeUtils.nowLong()));
			logger.info("上次登录时间：" + new Timestamp(player.getData().getRecordLastLoginTime()));

				isSigned = false;
				player.getData().setIsSigned((byte)0);
				if(player.getData().getSignNum() == 7){
					player.getData().setSignNum(0);
				}	
		}
		else{//如果是同一天
			if(player.getData().getIsSigned() == 0){
				isSigned = false;
			}
			else{
				isSigned = true;
			}
		}
		
	}
	
	/**
	 * 玩家进入签到界面
	 */
	public void openUI(){
		logger.info("======打开签到界面");
		
		handleIsSigned();
		
		//获得玩家已签到的次数
		int signNum = player.getData().getSignNum();
		
		if(signNum <= 7){
			for(int i=0;i<signNum;i++){//标志已签到的模块
				SignModule signModule = new SignModule();
				signModule.setIndex((byte) i);
				signModule.setStatus(SIGNED);
				signModule.setIcon(daysMap.get(i + 1).getIcon1());
				signModule.setValue(daysMap.get(i + 1).getValue1());
				signModule.setName("");
				signData[i] = signModule;
			}
			
			if(!isSigned){//如果没有签到过
				if(signNum < 7){//signNum <7 有需要签到的
					//需签到
					SignModule signModule = new SignModule();
					signModule.setStatus(SIGNING);
					signModule.setIndex((byte)signNum);
					signModule.setIcon(daysMap.get(signNum + 1).getIcon1());
					signModule.setValue(daysMap.get(signNum + 1).getValue1());
					signModule.setName("");
					signData[signNum] = signModule;
				}
			}
			else{
				if(signNum < 7){//signNum <7 有需要签到的
					//需签到
					SignModule signModule = new SignModule();
					signModule.setStatus(NOT_SIGN);
					signModule.setIndex((byte)signNum);
					signModule.setIcon(daysMap.get(signNum + 1).getIcon1());
					signModule.setValue(daysMap.get(signNum + 1).getValue1());
					signModule.setName("");
					signData[signNum] = signModule;
				}
			}
				
			if(signNum < 6){//有未签到
				//未签到
				for(int i = signNum + 1;i < 7;i++){
					SignModule sm = new SignModule();
					sm.setIndex((byte)i);
					sm.setStatus(NOT_SIGN);
					sm.setIcon(daysMap.get(i + 1).getIcon1());
					sm.setValue(daysMap.get(i + 1).getValue1());
					sm.setName("");
					signData[i] = sm;
				}
			}
				
		}	
		
		SignModule lastSm1 = new SignModule();
		lastSm1.setIndex((byte)7);
		lastSm1.setStatus(AWARD_FLAG);
		lastSm1.setIcon(daysMap.get(8).getIcon1());
		lastSm1.setValue(daysMap.get(8).getValue1());
		lastSm1.setName(daysMap.get(8).getName1());
		SignModule lastSm2 = new SignModule();
		lastSm2.setIndex((byte)8);
		lastSm2.setStatus(AWARD_FLAG);
		lastSm2.setIcon(daysMap.get(8).getIcon2());
		lastSm2.setValue(daysMap.get(8).getValue2());
		lastSm2.setName(daysMap.get(8).getName2());
		SignModule lastSm3 = new SignModule();
		lastSm3.setIndex((byte)9);
		lastSm3.setStatus(AWARD_FLAG);
		lastSm3.setIcon(daysMap.get(8).getIcon3());
		lastSm3.setValue(daysMap.get(8).getValue3());
		lastSm3.setName(daysMap.get(8).getName3());
		signData[7] = lastSm1;
		signData[8] = lastSm2;
		signData[9] = lastSm3;
		
		//发送给客户端
		RespModuleSet rms=new RespModuleSet(ProcotolType.SIGN_RESP);
		for(SignModule sm : signData){
			rms.addModule(sm);
		}
		AndroidMessageSender.sendMessage(rms,player);
		
		
	}
	
	/**
	 * 玩家签到
	 */
	public TipUtil signIn(){
		logger.info("===================玩家开始签到");
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		//判断玩家签到是否合法
		//如果玩家上次签到时间和本次签到时间不是同一天则可以签到
		if(!TimeUtils.isSameDay(player.getData().getLastSignTime())){	
			
			isSigned = true;
			player.getData().setIsSigned((byte)1);//标志玩家已签过到
			player.getData().setLastSignTime(System.currentTimeMillis());
			//发奖
			sendAward();
			tip.setSuccTip("");
		}
		else{
			tip.setFailTip("您今天已签过到");
		}
				
		return tip;
		
	}
	
	/**
	 * 签到发奖
	 */
	private void sendAward(){
		//玩家之前已签过的总次数
		int signNum = player.getData().getSignNum();
		int nowNum = signNum + 1;
		player.getData().setSignNum(nowNum); 
		int value = daysMap.get(nowNum).getValue1();
		player.saveResources(GameConfig.CHIP, value);
		logger.info("签到获奖：恭喜您获得" + value + "筹码");
		String str = I18nGreeting.getInstance().getMessage("sign.award",
				new Object[]{value,"筹码"});
		if(I18nGreeting.LANLANGUAGE_TIPS ==1 ){
			str = I18nGreeting.getInstance().getMessage("sign.award",
					new Object[]{value,"Chip"});
		}
		GameUtils.sendTip(new TipMessage(str, ProcotolType.SIGN_RESP, GameConst.GAME_RESP_SUCCESS), player.getUserInfo(),GameUtils.FLUTTER);
		if(nowNum == 7){//发签满7天奖励
			int[] propsIds = new int[3];
			int[] values = new int[3];
			propsIds[0] = daysMap.get(8).getPropsId1();
			propsIds[1] = daysMap.get(8).getPropsId2();
			propsIds[2] = daysMap.get(8).getPropsId3();
			values[0] = daysMap.get(8).getValue1();
			values[1] = daysMap.get(8).getValue2();
			values[2] = daysMap.get(8).getValue3();
			
			for(int i =0;i<3;i++){
				if(propsIds[i] == P_CHIP){//如果是筹码
					
					player.saveResources(GameConfig.CHIP, values[i]);
					logger.info("签到获奖：恭喜您获得" + values[i] + "筹码");
					String msg = I18nGreeting.getInstance().getMessage("sign.final",
							new Object[]{values[i],"筹码"});
					if(I18nGreeting.LANLANGUAGE_TIPS ==1 ){
						msg = I18nGreeting.getInstance().getMessage("sign.final",
								new Object[]{values[i]," Chip"});
					}
					
					GameUtils.sendTip(new TipMessage(msg, ProcotolType.SIGN_RESP, GameConst.GAME_RESP_SUCCESS), player.getUserInfo(),GameUtils.FLUTTER);
				}
				else if(propsIds[i] == P_GOLD){//金币
					player.saveResources(GameConfig.GAME_MONEY, values[i]);
					logger.info("签到获奖：恭喜您获得" + values[i] + "金币");
					String msg = I18nGreeting.getInstance().getMessage("sign.final",
							new Object[]{values[i],"金币"});
					if(I18nGreeting.LANLANGUAGE_TIPS ==1 ){
						msg = I18nGreeting.getInstance().getMessage("sign.final",
								new Object[]{values[i]," Gold "});
					}
					
					GameUtils.sendTip(new TipMessage(msg, ProcotolType.SIGN_RESP, GameConst.GAME_RESP_SUCCESS), player.getUserInfo(),GameUtils.FLUTTER);
				}
				else if(propsIds[i] == P_HONOR){//功勋
					player.saveResources(GameConfig.AWARD, values[i]);
					logger.info("签到获奖：恭喜您获得" + values[i] + "功勋");
					String msg = I18nGreeting.getInstance().getMessage("sign.final",
							new Object[]{values[i],"功勋"});
					if(I18nGreeting.LANLANGUAGE_TIPS ==1 ){
						msg = I18nGreeting.getInstance().getMessage("sign.final",
								new Object[]{values[i]," Honor"});
					}
					
					GameUtils.sendTip(new TipMessage(msg, ProcotolType.SIGN_RESP, GameConst.GAME_RESP_SUCCESS), player.getUserInfo(),GameUtils.FLUTTER);
				}
				else{//物品
					//主动推送客户端
					RespModuleSet rms=new RespModuleSet(ProcotolType.SIGN_RESP);
					Cell cell = player.getPlayerStorageAgent().addPropsCell(propsIds[i], values[i]);
					String name = PropsManager.getInstance().propsDatas.get(propsIds[i]).getName();
					String msg = I18nGreeting.getInstance().getMessage("sign.final",
							new Object[]{values[i],name});
					GameUtils.sendTip(new TipMessage(msg, ProcotolType.SIGN_RESP, GameConst.GAME_RESP_SUCCESS), player.getUserInfo(),GameUtils.FLUTTER);
					rms.addModule(cell);
					rms.addModule(player.getData());
					AndroidMessageSender.sendMessage(rms,player);
				}
			}
		}
		
		//更新签到状态为已签到状态
		signData[signNum].setStatus(SIGNED);
		//发送给客户端
		RespModuleSet rms=new RespModuleSet(ProcotolType.SIGN_RESP);
		for(SignModule sm : signData){
				rms.addModule(sm);
		}
		AndroidMessageSender.sendMessage(rms,player);
	}
	
	
}
