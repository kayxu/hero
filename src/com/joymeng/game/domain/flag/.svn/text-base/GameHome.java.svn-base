package com.joymeng.game.domain.flag;

import java.util.HashMap;
import java.util.Map;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 大本营
 * 
 * @author Administrator
 * 
 */
public class GameHome extends ClientModuleBase {
	private byte point;// 坐标 51，19
	private boolean flag;// 是否有军旗
	PlayerCharacter player;
	private int heroA;// 英雄A
	private int heroB;// B
	private int heroC;// C
	private int reTime;// 刷新时间
	private int mobility;// 指令
	private int createTime;// 创建时间
	private int isDis = 7;// 默认111 1:在营 0 ：派出

	private Map<Integer, Integer> dieMap = new HashMap<Integer, Integer>();
	private boolean isWin = false;

	public Map<Integer, Integer> getDieMap() {
		return dieMap;
	}

	public void setDieMap(Map<Integer, Integer> dieMap) {
		this.dieMap = dieMap;
	}

	public boolean isWin() {
		return isWin;
	}

	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	public byte getPoint() {
		return point;
	}

	public void setPoint(byte point) {
		this.point = point;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public PlayerCharacter getPlayer() {
		return player;
	}

	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}

	public int getHeroA() {
		return heroA;
	}

	public void setHeroA(int heroA) {
		this.heroA = heroA;
	}

	public int getHeroB() {
		return heroB;
	}

	public void setHeroB(int heroB) {
		this.heroB = heroB;
	}

	public int getHeroC() {
		return heroC;
	}

	public void setHeroC(int heroC) {
		this.heroC = heroC;
	}

	public int getReTime() {
		return reTime;
	}

	public void setReTime(int reTime) {
		this.reTime = reTime;
	}

	public GameHome() {
	}

	public int getMobility() {
		return mobility;
	}

	public void setMobility(int mobility) {
		this.mobility = mobility;
	}

	public int getIsDis() {
		return isDis;
	}

	public void setIsDis(int isDis) {
		this.isDis = isDis;
	}

	/**
	 * 创建大本营
	 */
	public void create(PlayerCharacter pp, int heroA, int heroB, int heroC,
			int mob) {
		setPlayer(pp);
		setHeroA(heroA);
		setHeroB(heroB);
		setHeroC(heroC);
		setMobility(mob);
		setReTime((int) (TimeUtils.nowLong() / 1000));
		setCreateTime((int) (TimeUtils.nowLong() / 1000));
		setIsDis(7);//
		setFlag(true);
	}

	/**
	 * 派将
	 * 
	 * @param heroId
	 */
	public void sendHero(int heroId, PlayerCharacter[] ps) {
		if (heroId == heroA) {
			setIsDis(getIsDis() & 0x3);
		} else if (heroId == heroB) {
			setIsDis(getIsDis() & 0x5);
		} else if (heroId == heroC) {
			setIsDis(getIsDis() & 0x6);
		}
		FlagManager.getInstance().sendOne(this, ps);
	}

	/**
	 * 丢旗
	 */
	public void changeFlag(PlayerCharacter[] ps, boolean isAtHome) {
		setFlag(isAtHome);
		FlagManager.getInstance().sendOne(this, ps);
	}

	/**
	 * 回营
	 * 
	 * @param heroId
	 */
	public void recHero(int heroId, Map<Integer, Integer> dieNum,
			PlayerCharacter[] ps) {
		if (heroId == heroA) {
			setIsDis(getIsDis() | 0x4);
		} else if (heroId == heroB) {
			setIsDis(getIsDis() | 0x2);
		} else if (heroId == heroC) {
			setIsDis(getIsDis() | 0x1);
		}
		FlagManager.getInstance().sendOne(this, ps);
	}

	/**
	 * 设置总死亡数
	 * 
	 * @param dieNum
	 */
	public void setAllDeath(Map<Integer, Integer> deahMap, PlayerCharacter[] ps) {
		if (deahMap != null) {
			for (Integer i : deahMap.keySet()) {
				if (dieMap.get(i) == null) {
					dieMap.put(i, deahMap.get(i));
				} else {
					dieMap.put(i, dieMap.get(i) + deahMap.get(i));
				}
			}
			FlagManager.getInstance().sendOne(this, ps);
		}
	}

	/**
	 * 总死亡数
	 * 
	 * @return
	 */
	public int getAllDeah() {
		int all = 0;
		for (Integer i : dieMap.values()) {
			all += i;
		}
		return all;
	}

	public boolean isAtHome(int heroid) {
		// 是否在大本营
		if (heroid == getHeroA()) {
			return (getIsDis() & 0x7) >> 2 == 1;
		} else if (heroid == getHeroB()) {
			return (getIsDis() & 0x3) >> 1 == 1;
		} else if (heroid == getHeroC()) {
			return (getIsDis() & 0x1) == 1;
		}
		return true;
	}

	/**
	 * 修改大本营
	 * 
	 * @param du
	 */
	public void motify(int du, PlayerCharacter[] allHome) {

		setMobility(du + getMobility());
		// 修改
		// RespModuleSet rms = new RespModuleSet(ProcotolType.FLAG_RESP);// 模块消息
		// rms.addModule(this);
		// AndroidMessageSender.sendMessage(rms,player);
		FlagManager.getInstance().sendOne(this, allHome);

	}

	/**
	 * 刷新
	 * 
	 * @param i
	 */
	public void refreshMobility(int i, PlayerCharacter[] allHome) {
		if (getReTime() + 25 <= (int) (TimeUtils.nowLong() / 1000)) {
			setReTime((int) (TimeUtils.nowLong() / 1000));
			motify(i, allHome);
		} else {
			motify(0, allHome);
		}
	}

	@Override
	public String print() {
		StringBuffer sb = new StringBuffer();
		sb.append("|================战斗结果数据 ================\n");
		sb.append("┌────────────────────────────────┐\n");
		sb.append("|用户：" + player.getId() + "\n");
		sb.append("|结果：" + isWin + "\n");
		sb.append("|士兵类型：" + dieMap.keySet().toString() + "\n");
		sb.append("|士兵数量：" + dieMap.values().toString() + "\n");
		sb.append("└────────────────────────────────┘\n");
		sb.append("|================战斗结果数据 ================\n");
		System.out.println(sb.toString());
		return sb.toString();
	}

	@Override
	public byte getModuleType() {
		return NTC_GAME_HOME;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.put(point);
		if (flag) {
			out.put((byte) 1);
		} else {
			out.put((byte) 0);
		}
		out.putInt(player.getId());
		out.putPrefixedString(player.getData().getName(), (byte) 2);
		out.putInt(heroA);
		if (heroA != 0) {
			if (isAtHome(heroA)) {
				out.put((byte) 1);
			} else {
				out.put((byte) 0);
			}
		} else {
			out.put((byte) 1);
		}

		PlayerHero pAA = getPlayer().getPlayerHeroManager().getHero(getHeroA());
		if (pAA != null) {
			out.putPrefixedString(pAA.getIcon(), (byte) 2);
		} else {
			out.putPrefixedString("", (byte) 2);
		}
		out.putInt(heroB);
		if (heroB != 0) {
			if (isAtHome(heroB)) {
				out.put((byte) 1);
			} else {
				out.put((byte) 0);
			}
		} else {
			out.put((byte) 1);
		}

		PlayerHero pAB = getPlayer().getPlayerHeroManager().getHero(getHeroB());
		if (pAB != null) {
			out.putPrefixedString(pAB.getIcon(), (byte) 2);
		} else {
			out.putPrefixedString("", (byte) 2);
		}
		out.putInt(heroC);
		if (heroC != 0) {
			if (isAtHome(heroC)) {
				out.put((byte) 1);
			} else {
				out.put((byte) 0);
			}
		} else {
			out.put((byte) 1);
		}

		PlayerHero pAC = getPlayer().getPlayerHeroManager().getHero(getHeroC());
		if (pAC != null) {
			out.putPrefixedString(pAC.getIcon(), (byte) 2);
		} else {
			out.putPrefixedString("", (byte) 2);
		}
		out.putInt(reTime + 30);
		out.putInt(mobility);

		out.put(player.getData().getFaction());
		out.putShort(player.getData().getLevel());
		if (isWin()) {
			out.put((byte) 1);
		} else {
			out.put((byte) 0);
		}
		out.putInt(dieMap.size());
		for (Integer i : dieMap.keySet()) {
			out.putInt(i);
			out.putInt(dieMap.get(i));
		}

		print();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int x = 7;
		// short my = Short.parseShort(Integer.toHexString(x),16);
		// System.out.println(my);
		// my = (short)(my | 0x7);
		x = x & 0x5;

		System.out.println((x & 0x3) >> 1);
	}

}
