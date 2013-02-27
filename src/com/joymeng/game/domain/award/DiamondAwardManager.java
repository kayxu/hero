package com.joymeng.game.domain.award;

import hirondelle.date4j.DateTime;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;

/**
 * 赠送钻石
 * @author 马缔
 *
 */
public class DiamondAwardManager {
	
	private static DiamondAwardManager diamondAwardManager;
	
	public static DiamondAwardManager getInstance(){
		if(null == diamondAwardManager){
			diamondAwardManager = new DiamondAwardManager();
		}
		return diamondAwardManager;
	}
	
	/**
	 * 升级赠送
	 * @param player 玩家
	 * @return
	 */
	public boolean levelUpAward(PlayerCharacter player){
		int value = 0;
		if(player.getData().getLevel() == 5){//升级到5级
			player.saveResources(GameConfig.JOY_MONEY, 500);//给玩家500钻石
			value = 500;
		}
		else if(player.getData().getLevel() == 10){//升级到10级
			player.saveResources(GameConfig.JOY_MONEY, 1000);//给玩家1000钻石
			value = 1000;
		}
		else if(player.getData().getLevel() == 15){//升级到15级
			player.saveResources(GameConfig.JOY_MONEY, 1500);//给玩家1500钻石
			value = 1500;
		}
		else if(player.getData().getLevel() == 20){//升级到20级
			player.saveResources(GameConfig.JOY_MONEY, 2000);//给玩家2000钻石
			value = 2000;
		}
		else if(player.getData().getLevel() == 25){//升级到25级
			player.saveResources(GameConfig.JOY_MONEY, 2500);//给玩家2500钻石
			value = 2500;
		}
		else if(player.getData().getLevel() == 30){//升级到30级
			player.saveResources(GameConfig.JOY_MONEY, 3000);//给玩家3000钻石
			value = 3000;
		}
		if(value != 0){
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(new TipMessage("Congratulations! You've got Rewards:" + value +" Gems."
						, ProcotolType.ENTER_GAME_RESP, GameConst.GAME_RESP_SUCCESS), player.getUserInfo(),GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("恭喜你获得内测奖励：钻石" + value
						, ProcotolType.ENTER_GAME_RESP, GameConst.GAME_RESP_SUCCESS), player.getUserInfo(),GameUtils.FLUTTER);
			}
			
			
		}
		return true;
	}
	
	/**
	 * 每天赠送钻石
	 * @param player 玩家
	 * @return 
	 */
	public int everydayAward(PlayerCharacter player){
		DateTime startDate = TimeUtils.getTime(player.getData().getRecordLastLoginTime());
		DateTime nowDate = TimeUtils.getTime(TimeUtils.nowLong());
		int intervalDays = startDate.numDaysFrom(nowDate);
		player.saveResources(GameConfig.JOY_MONEY, intervalDays * 1000);//按天发给玩家相应数量的钻石，每天1000
		return intervalDays * 1000;
	}
	
	/**
	 * 官位赠送
	 * @param player PlayerChacter
	 * @return
	 */
	public int titleAward(PlayerCharacter player){
		int value = 0;
		if(!TimeUtils.isSameDay(player.getData().getRecordLastLoginTime())){//如果不是同一天登录
			if(player.getData().getTitle() == GameConst.TITLE_MAYOR_TOWN){//如果是县长
				player.saveResources(GameConfig.JOY_MONEY, 100);//获得钻石100
				value = 100;
			}
			else if(player.getData().getTitle() == GameConst.TITLE_MAYOR_CITY){//如果是市长
				player.saveResources(GameConfig.JOY_MONEY, 200);
				value = 200;
			}
			else if(player.getData().getTitle() == GameConst.TITLE_GOVERNOR){//如果是州长
				player.saveResources(GameConfig.JOY_MONEY, 500);
				value = 500;
			}
			else if(player.getData().getTitle() == GameConst.TITLE_KING){//如果是国王
				player.saveResources(GameConfig.JOY_MONEY, 1000);
				value = 1000;
			}
		}
		return value;
		
	}
	
	/**
	 * 赠送奖品
	 * @param player 玩家
	 */
	public boolean sendAward(PlayerCharacter player){
		int gainDiamondNum = everydayAward(player);
		gainDiamondNum += titleAward(player);
		if(gainDiamondNum > 0){
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(new TipMessage("Congratulations! You've got Rewards:" + gainDiamondNum +" Gems."
						, ProcotolType.ENTER_GAME_RESP, GameConst.GAME_RESP_SUCCESS), player.getUserInfo(),GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("恭喜你获得内测奖励：钻石" + gainDiamondNum
						, ProcotolType.ENTER_GAME_RESP, GameConst.GAME_RESP_SUCCESS), player.getUserInfo(),GameUtils.FLUTTER);
			}
			
		}
		return true;
		
	}

}
