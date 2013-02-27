package com.joymeng.game.domain.fight.mod;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.fight.FightLog;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogBuffer;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 竞技场
 * 
 * @author admin
 * @date 2012-6-18 TODO
 */
public class Arena extends ClientModuleBase {
//	static Logger logger = LoggerFactory.getLogger(Arena.class);
	private short id; // 排名
	private int userId; // 玩家id
	private int heroId; // 将领id
	private String userName; // 玩家姓名
	private String heroInfo; // 将领信息
	private String soldier;// 士兵
	private Timestamp time;// 更新时间
	private int serverId;

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeroInfo() {
		return heroInfo;
	}

	public void setHeroInfo(String heroInfo) {
		this.heroInfo = heroInfo;
	}

	public String getSoldier() {
		return soldier;
	}

	public void setSoldier(String soldier) {
		this.soldier = soldier;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public byte getModuleType() {
		return NTC_ARENA;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putShort(id);
		out.putInt(userId);
		out.putInt(heroId);
		out.putPrefixedString(userName, JoyBuffer.STRING_TYPE_SHORT);
		out.putPrefixedString(soldier, JoyBuffer.STRING_TYPE_SHORT);
		if (this.heroInfo.equals("")) {
			out.put((byte) 0);
			out.putInt(0);
			// 武器
			out.putInt(0);
			// 铠甲
			out.putInt(0);
			// 头盔
			out.putInt(0);
			// 马
			out.putInt(0);
		} else {
			String data[] = heroInfo.split(";");
			out.put(Byte.parseByte(data[0]));
			out.putInt(Integer.parseInt(data[1]));
			// 武器
			out.putInt(Integer.parseInt(data[2]));
			// 铠甲
			out.putInt(Integer.parseInt(data[3]));
			// 头盔
			out.putInt(Integer.parseInt(data[4]));
			// 马
			out.putInt(Integer.parseInt(data[5]));
		}

	}

	@Override
	public void deserialize(JoyBuffer in) {
		// byte modelType = in.get();
		// this.id = in.getShort();
		// this.userId = in.getInt();
		// this.heroId = in.getInt();
		// this.userName = in.getPrefixedString();
		// this.heroInfo = in.getPrefixedString();
	}

	// public void print() {
	// System.out.println("id==" + getId());
	// System.out.println("userId==" + getUserId());
	// System.out.println("heroId==" + getHeroId());
	// System.out.println("userName==" + getUserName());
	// System.out.println("heroInfo==" + getHeroInfo());
	// }

	// /**
	// * 创建初始竞技场
	// *
	// * @return
	// */
	// public static Arena create(short id) {
	// Arena arena = new Arena();
	// arena.setId(id);
	// arena.setHeroId(0);
	// arena.setHeroInfo("");// name;sex;level;weapon;armour;helmet;horse
	// arena.setUserId(0);
	// arena.setUserName("");
	// arena.setSoldier("");
	// return arena;
	// }
	/**
	 * 根据将领信息获得heroInfo
	 * 
	 * @param ph
	 * @return
	 */
	public static String createHeroInfo(PlayerHero ph) {
		String str = "";
		// String soldier=ph.getSoldier();
		String data[] = new String[6];
		data[0] = String.valueOf(ph.getSex());
		data[1] = String.valueOf(ph.getLevel());
		data[2] = String.valueOf(ph.getWeapon());
		data[3] = String.valueOf(ph.getArmour());
		data[4] = String.valueOf(ph.getHelmet());
		data[5] = String.valueOf(ph.getHorse());
		str = StringUtils.recoverNewStr(data, ";");
		return str;
	}

	/**
	 * 坐下位置
	 * 
	 * @param heroId
	 * @param soldier
	 * @param player
	 */
	public synchronized void sitdown(PlayerHero hero, String soldier,
			PlayerCharacter player, byte attackType) {
		//判断数据是否合法
		if (hero==null ||player ==null) {
			FightLog.error("竞技场 sitdown-1 error,player id=" + this.getUserId()
					+ " arenaId=" + this.id + " hero id=" + hero.getId());
			return;
		}
		if (hero.getUserId() != player.getId()) {
			FightLog.error("竞技场 sitdown-2 error,player id=" + this.getUserId()
					+ " arenaId=" + this.id + " hero id=" + hero.getId());
			return;
		}
		// 先站起
		if (!this.standUp()) {
			FightLog.error("竞技场 sitdown-3 error,player id=" + this.getUserId()
					+ " arenaId=" + this.id + " hero id=" + hero.getId());
			return;
		}
		long time1 = System.currentTimeMillis();
		synchronized (this) {
			try{
				String meme = "驻守竞技场中";
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					meme = "Garrsion in Arena";
				}
				//更改将领和玩家状态
				player.getPlayerHeroManager().motifyStatus(hero.getId(),
						GameConst.HEROSTATUS_ARENA, meme, soldier,
						System.currentTimeMillis());
				player.getData().setArenaId(this.id);
				//更改竞技场状态
				this.setHeroId(hero.getId());
				this.setUserId((int) player.getData().getUserid());
				this.setUserName(player.getData().getName());
				this.setSoldier(soldier);
				this.setHeroInfo(createHeroInfo(hero));
			}catch(Exception ex){
				FightLog.error("竞技场 sitdown4 error,player id=" + this.getUserId()
						+ " arenaId=" + this.id + " hero id=" + hero.getId());
				return;
			}
		
			long time2 = System.currentTimeMillis();
			// logger.info("sitdown 耗时1="+(time2-time1));
			try{
				// 需要加入派兵方法
				player.getPlayerBuilgingManager().dispatchSoldier(soldier);
			}catch (Exception ex){
				FightLog.error("竞技场 sitdown5 error,player id=" + this.getUserId()
						+ " arenaId=" + this.id + " hero id=" + hero.getId());
				return;
			}
			GameLog.logPlayerEvent(player, LogEvent.ARENA,
					new LogBuffer().add("竞技场id="+this.id).add("sitdown").add("soldier"+soldier).add("heroId="+heroId).add("attackType="+attackType));
			long time3 = System.currentTimeMillis();
			// logger.info("sitdown 耗时2="+(time3-time2));
			// 保存
			 GameDataManager.arenaManager.save(this);
			// World.getInstance().savePlayer(player);
			// 推送客户端
			List<Arena> list = GameDataManager.arenaManager.search(this.id);
			RespModuleSet rms = new RespModuleSet(ProcotolType.ARENA_RESP);// 模块消息
			for (Arena a : list) {
//				System.out.println("id=" + a.getId());
				rms.addModule(a);
			}
			rms.addModule(hero);
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms, player);
			long time4 = System.currentTimeMillis();
			// logger.info("sitdown 耗时3="+(time4-time3));
			QuestUtils.checkFinish(player, QuestUtils.TYPE42, true);
			long time5 = System.currentTimeMillis();
			// logger.info("sitdown 耗时4="+(time5-time4));
		}

	}

	/**
	 * 站起
	 */
	public synchronized boolean standUp() {
//		long time1 = System.currentTimeMillis();
		if(this.userId==0){
			return true;
		}
	
		synchronized (this) {
			// 将领回城
			PlayerCharacter player = World.getInstance().getPlayer(
					this.getUserId());
			if (player == null) {
				FightLog.error("竞技场 standUp1 error,player id="
						+ this.getUserId() + " arenaId=" + this.id);
			}
			PlayerHero hero = player.getPlayerHeroManager().getHero(
					this.getHeroId());
			if (hero == null) {
				FightLog.error("竞技场 standUp2 error,hero id=" + this.getHeroId()
						+ " arenaId=" + this.id);
				return false;
			}
			if (player.getData().getArenaId() != this.id) {
				FightLog.error("竞技场 standUp3 error, arena id=" + id + " userId="
						+ userId);
				return false;
			}
			GameLog.logPlayerEvent(player, LogEvent.ARENA,
					new LogBuffer().add("竞技场id="+this.id).add("standup").add("userId="+userId).add("heroId="+heroId));
			long time2 = System.currentTimeMillis();
			// logger.info("standUp1 耗时="+(time2-time1));
			// logger.info("arena standup,uid="+player.getId()+" index="+this.id);

			String soldier = this.soldier;
			long time3 = System.currentTimeMillis();
			// logger.info("standUp2 耗时="+(time3-time2));

			this.reset();
			try {
				// 更改状态
				player.getData().setArenaId(0);
				player.getPlayerHeroManager().motifyStatus(hero.getId(),
						GameConst.HEROSTATUS_IDEL, "", "", 0);
			} catch (Exception ex) {
				FightLog.error("竞技场 standUp4 error, arena id=" + id + " userId="
						+ userId, ex);
				return false;
			}
			try {
				// 回收士兵
				player.getPlayerBuilgingManager().recoverSoldier(soldier);
			} catch (Exception ex) {
				FightLog.error("竞技场 standUp5 error, arena id=" + id + " userId="
						+ userId + " soldier=" + soldier, ex);
				return false;
			}

			// 保存竞技场数据
			 GameDataManager.arenaManager.save(this);
			// World.getInstance().savePlayer(player);
			// 发送消息
			RespModuleSet rms = new RespModuleSet(ProcotolType.ARENA_RESP);// 模块消息
			List<Arena> list = GameDataManager.arenaManager.search(0);
			for (Arena a : list) {
				rms.addModule(a);
			}
			rms.addModule(hero);
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms, player);
			long time4 = System.currentTimeMillis();
			// logger.info("standUp3 耗时="+(time4-time3));
			return true;
		}

	}

	public void reset() {
		this.setHeroId(0);
		this.setHeroInfo("");// name;sex;level;weapon;armour;helmet;horse
		this.setUserId(0);
		this.setUserName("");
		this.setSoldier("");
	}

}
