package com.joymeng.game.domain.role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.world.World;

//英雄得分J=Sum（英雄等级*英雄品质）
//	金币数(G)	金币权重(Gr)	功勋数(H)	功勋权重(Hr)	钻石数(D)	钻石权重(Dr)	士兵总和M()	士兵权重（Mr)	英雄得分（J）	英雄权重（Jr）
//战斗力F=sum（G*Gr,H*Hr,D*Dr,M*Mr,J*Jr）
public class PowerCalculate {
	Map<Integer,Power> powerData = new HashMap<Integer,Power>();
	
	public Map<Integer, Power> getPowerData() {
		return powerData;
	}

	public void setPowerData(Map<Integer, Power> powerData) {
		this.powerData = powerData;
	}
	public static double GR = 0.001;// 金币权重
	public static double HR = 0.05;// 功勋权重
	public static int DR = 1;// 钻石权重
	public static double MR = 0.01;// 士兵权重
	public static int JR = 2;// 英雄权重

	static Logger logger = LoggerFactory.getLogger(PowerCalculate.class);

	public static PowerCalculate instance;

	public static PowerCalculate getInstance() {
		if (instance == null)
			instance = new PowerCalculate();
		return instance;
	}

	public int calculatePower(PlayerCharacter player) {
		double weights = 0;
		if (player != null) {
			//logger.info(player.toString());
			weights = player.getData().getJoyMoney() * GR
					+ player.getData().getJoyMoney() * DR
					+ player.getData().getAward() * HR + allSolider(player)
					* MR + allPlayerHero(player) * JR;
			logger.info("用户：" + player.getId() + "|战斗力：" + weights);
		}
		return (int) weights;
	}

	/**
	 * 所有士兵
	 * 
	 * @param player
	 * @return
	 */
	public int allSolider(PlayerCharacter player) {
		int total = 0;
		if (player != null && player.getPlayerBuilgingManager().getPlayerBarrack() != null) {
			total = player.getPlayerBuilgingManager().getPlayerBarrack()
					.allSoliderCount();// 兵营数据
			PlayerHero[] arr = player.getPlayerHeroManager().getHeroArray();
			if (arr != null && arr.length > 0) {
				for (int i = 0; i < arr.length; i++) {
					if (arr[i].getStatus() != GameConst.HEROSTATUS_IDEL)// 不是空闲状态
						total += FightUtil.getSoldierNum(arr[i].getSoldier());
				}
			}
			logger.info("用户：" + player.getId() + "|士兵积分：" + total);
		}
		return total;
	}

	/**
	 * 所有英雄积分
	 * 
	 * @param player
	 * @return
	 */
	public int allPlayerHero(PlayerCharacter player) {
		int total = 0;
		if (player != null) {
			PlayerHero[] arr = player.getPlayerHeroManager().getHeroArray();
			if (arr != null && arr.length > 0) {
				for (int i = 0; i < arr.length; i++) {
					total += arr[i].getLevel() * arr[i].getColor();
				}
			}
			logger.info("用户：" + player.getId() + "|英雄积分：" + total);
		}
		return total;
	}
	/**
	 * 计算每州州长数据
	 */
	public void calaNationState() {
		HashMap<Integer, Nation> state = NationManager.getInstance().stateMap;
		HashMap<Integer, Nation> county = NationManager.getInstance().countryMap;
		powerData.clear();//清空数据
		if(state != null && state.size() > 0){
			for(Nation n : state.values()){
				if(n.getOccupyUser() != 0){
					PlayerCharacter player = World.getInstance().getPlayer(n.getOccupyUser());
					Power power = new Power();
					power.setN(n);
					power.setPowerPoint(calculatePower(player));
					powerData.put(n.getId(),power);
				}else{
					Power power = new Power();
					power.setN(n);
					powerData.put(n.getId(),power);
				}
			}
		}
		if(county != null && county.size() > 0){
			for(Nation n : county.values()){
				if(n.getOccupyUser() != 0){
					PlayerCharacter player = World.getInstance().getPlayer(n.getOccupyUser());
					Power power = new Power();
					power.setN(n);
					power.setPowerPoint(calculatePower(player));
					powerData.put(n.getId(),power);
				}else{
					Power power = new Power();
					power.setN(n);
					power.setPowerPoint(0);
					powerData.put(n.getId(),power);
				}
			}
		}
//		Collections.sort(powerData);
	}
	public static void main(String[] args) {
		Power p1 = new Power();
		p1.setPowerPoint(2);
		Power p2 = new Power();
		p2.setPowerPoint(8);
		Power p3 = new Power();
		p3.setPowerPoint(6);
		ArrayList<Power> p = new ArrayList<>();
//		p.add(p1);
		p.add(p2);
		p.add(p2);
		System.out.println(p);
		Collections.sort(p);
		System.out.println(p);
	}
}
