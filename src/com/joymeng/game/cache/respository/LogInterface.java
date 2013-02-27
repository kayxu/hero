package com.joymeng.game.cache.respository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.joymeng.game.cache.domain.DiamondPlayer;
import com.joymeng.game.cache.domain.Log;
import com.joymeng.game.cache.domain.Login;
import com.joymeng.game.cache.domain.NewUser;
import com.joymeng.game.cache.domain.PropSale;
import com.joymeng.web.entity.CardOperationInfo;
import com.joymeng.web.entity.ExtremeBox;
import com.joymeng.web.entity.OnlineNum;
import com.joymeng.web.entity.OnlineTime;
import com.joymeng.web.entity.Sale;

public interface LogInterface {
	public List<Log> getAllLogs(String start);
	public String getLogByKey(String key,int limit);
	
	public String getLogByTime(String start,String end);
	public void save(Log log);
	public void getQuestAll();
	public List<DiamondPlayer> getDiamondPlayerNum(String day);
	public void getArenaLogByUid(String start,String end,int id);
	public void getArenaLogByAid(String start,String end,int id);
//	public void script();
	/**
	 * 大转盘某一段时间的使用人数
	 * @param start
	 * @param end
	 * @return
	 */
	public int getBoxUseNum(String start,String end);
	
	
	/**
	 * 大转盘某一段时间消耗的筹码数量
	 * @param start
	 * @param end
	 * @return
	 */
	public int getBoxCostChipNum(String start,String end);
	
	/**
	 * 大转盘某一段时间产出的奖品数量
	 * @param start
	 * @param end
	 * @return
	 */
	public int getBoxAwardNum(String start,String end);
	
	/**
	 * 商城每一道具在某一段时间使用的数量
	 * @param start
	 * @param end
	 * @return
	 */
	
	
	/**
	 * 商城每一道具在某一段时间的购买数量
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Sale> getEachPropsBuyNum(String start,String end);
	public List<PropSale> getPropsBuyType(String day,byte type);
	/**
	 * 某一段时间使用钻石的总数量
	 * @param start
	 * @param end
	 * @return
	 */
	
	
	/**
	 * 某一段时间登录过的玩家数
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Login> getLoginNum(String day);
	
	/**
	 * 某一段时间同时在线玩家数量最大值
	 * @param start
	 * @param end
	 * @return
	 */
	public List<OnlineNum>  getOnlineNum2(String day);
	
	/**
	 * 获得在线时间
	 * @param start
	 * @param end
	 * @return
	 */
	public List<OnlineTime> getOnlineTime(String day);
	
	public List<NewUser> getNewUser(String day);
	
	/**
	 * 查询翻牌相关操作记录信息
	 * @param start 起始时间
	 * @param end 截止时间
	 * @return
	 */
	public CardOperationInfo getCardOperationInfo(String start,String end);
	
	/**
	 * 统计某一段时间内宝箱，大转盘的相关数据
	 * @param start 开始时间
	 * @param end 截止时间
	 * @return 信息实体
	 */
	public ExtremeBox getExtremeBox(String start,String end);
}
