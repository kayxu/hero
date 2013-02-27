package com.joymeng.core.event;

/**
 * 游戏事件类 GameObject之间、GameObject的各个功能模块之间以及地图事件都通过游戏事件的方式交互
 * 
 * @author Shaolong Wang
 * 
 */
public class GameEvent {

	public static final short EVT_ENTER_MAIN_GAME = 0x0000;
	public static final short EVT_EXIT_MAIN_GAME = 0x0001;

	public static final short EVT_FIGHT_SUCC = 0x0002;// 战斗胜利
	public static final short EVT_FIGHT_FAIL = 0x0003;

	public static final short EVT_NEW_PLAYER_ENTER_GAME = 0x0004;// 新玩家进入游戏

	public static final short EVT_PLAYER_LEVEL_UP = 0x0005;
	public static final short EVT_EXIT_TEAM = 0x0006; // 玩家离开队伍
	public static final short EVT_OFF_LINE = 0x0007; // 玩家掉线

	public static final short EVT_ADD_ITEM = 0x0008;// 背包中添加物品，或装备

	public static final short EVT_REMOVE_ITEM = 0x0009; // 从背包中移除物品

	public static final short EVT_EQUIPMENT_ENHANCE = 0x000A; // 强化装备
	public static final short EVT_STONE_ENHANCE = 0x000B; // 玄石、宝石合成
	public static final short EVT_EQUIPMENT_EMBED = 0x000C; // 镶嵌宝石
	public static final short EVT_EMBED_STONE_ENHANCE = 0x000D; // 宝石合成
	public static final short EVT_EXIT_SCENE = 0x000F; // 离开场景

	protected final short code;// 事件code
	protected short delayTick;// 延迟tick

	public GameEvent(short code) {
		this.code = code;
		delayTick = 0;
	}

	public short getCode() {
		return code;
	}

	/**
	 * 指定事件类型
	 * 
	 * @param code
	 * @param type
	 */
	public GameEvent(short code, short delayTick) {
		this.code = code;
		this.delayTick = delayTick;
	}

	public short getDelayTick() {
		return delayTick;
	}

	public void tick() {
		delayTick--;
	}

//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		return sb.toString();
//	}
}
