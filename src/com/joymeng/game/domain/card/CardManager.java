package com.joymeng.game.domain.card;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogBuffer;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.box.Package;
import com.joymeng.game.domain.box.PropsBoxManager;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;

public class CardManager {

	private static final Logger logger = LoggerFactory.getLogger(CardManager.class);
	
	PlayerCharacter player;
	
	//int packageid = 0;
	List<SimpleAward> forTipAwardList = new ArrayList<SimpleAward>();
	
	Card[] cards = new Card[9];//为玩家生成9张牌
	
	//Card[] tempCardsForRotate = new Card[9];//旋转时为控制牌局产生的临时牌局
	
	public static List<CardAwardRate> cardAwardRates = new ArrayList<CardAwardRate>();
	
	/**
	 * 打开选中的牌
	 */
	List<Card> showCardsList = new ArrayList<Card>();
	
	public static HashMap<Integer,OnlineTime> onlineTimeMap = new HashMap<Integer,OnlineTime>();
	
	/**
	 * 用于电脑模拟打开牌
	 */
	List<Card> showCardsListForCom = new ArrayList<Card>();
	
	/**
	 * 找寻下一张牌结束
	 */
	boolean isFindEnd = false;
	
	/**
	 * 用于计算机模拟点牌
	 */
	boolean isComputerFindEnd = false;
	
	/**
	 * 玩家默认翻牌机会
	 */
	private int flipChance = 0;
	
	/**
	 * 玩家默认可旋转次数
	 */
	private int rotateChance = 0;
	
	/**
	 * 第几次获得翻牌机会增加
	 */
	private int turnsForChance = 1;
	
	/**
	 * 下一次获得翻牌机会所需时间
	 */
	private int nextTime;
	
	/**
	 * 玩家当前局使用旋转的次数
	 */
	private int useRotateChance;
	
	/**
	 * 用于控制随机不循环重复
	 */
	//private int randomTimes;
	
	//上次增加翻牌机会时的系统时间
	private long lastAddChanceTime = 0;
	
	public CardManager(PlayerCharacter player){
		this.player = player;
	}
	
	public static void loadCardAwardRates(String path)throws Exception{
		//logger.info("-----------------------加载牌获奖几率------------------------------");
		List<Object> list = GameDataManager.loadData(path, CardAwardRate.class);
		for(Object o : list){
			cardAwardRates.add((CardAwardRate) o);
		}
		
		//logger.info("记录条数：" + cardAwardRates.size());
		//logger.info("-----------------------加载牌获奖几率完毕------------------------------");
		//logger.info("-----------------------加载在线时长和获取翻牌机会次数-------------------");
		List<Object> onlineTimeList = GameDataManager.loadData(path, OnlineTime.class);
		for(Object o : onlineTimeList){
			OnlineTime ot = (OnlineTime)o;
			onlineTimeMap.put(ot.getTurns(), ot);
		}
		//logger.info("记录条数: " + onlineTimeMap.size());
		//logger.info("-----------------------加载在线时长和获取翻牌机会次数完毕-------------------");
	}
	
	public void enter(){//进入
		
		//如果不是同一天进入
		if (!TimeUtils.isSameDay(player.getData().getLeaveTime())) {
			//logger.info("与上次***不是***同一天进入========" + player.getData().getName());
			//logger.info("本次登录时间：" + new Timestamp(player.getData().getLastLoginTime()));
			//logger.info("上次离开时间：" + new Timestamp(player.getData().getLeaveTime()));
			//如果需要开始新的牌局
			if(cards[0] == null){
				initPlayerCards();
			}
			//logger.info("开局最大连牌数" + maxContinuous());
		}
		else{//如果是同一天进入，且玩家之前离线过，从数据库中读取
			//如果需要开始新的牌局
			//logger.info("与上次***是***同一天进入========" + player.getData().getName());
			//logger.info("本次登录时间：" + new Timestamp(player.getData().getLastLoginTime()));
			//logger.info("上次离开时间：" + new Timestamp(player.getData().getLeaveTime()));
			if(cards[0] == null){
				initPlayerCards();
			}
		}
			
		//发送倒计时时间和翻牌机会数
		showChanceAndNextTime();			
	}
	
	/**
	 * 生成新牌局
	 */
	private void newCardSurface(){
			isComputerFindEnd = false;
			for(int i=0;i<cards.length;i++){
				int face = MathUtils.random(4);
				cards[i] = new Card();
				cards[i].setFace((byte)face);
				cards[i].setIndex((byte)i);
			}
	}
	
	/**
	 * 开局是否有超过4张连续的牌面
	 * @return
	 */
	private boolean isOverMaxWhenOpen(){
		
		if(maxContinuous() > 4){//如果有超过4张连续牌
				return true;
		}
		return false;
		
	}
	
	/**
	 * 当前牌局最大连牌数
	 * @return 最大连牌数
	 */
	private int maxContinuous(){
		showCardsListForCom.clear();
	
		int max = 1;
		for(int i=0;i<cards.length;i++){
			
			showCardsListForCom.add(cards[i]);
			byte nextIndex = (byte)findNextCardForComputer((byte)i,cards[i].getFace());
			while(nextIndex != -1){
				if(isCircleForCom(nextIndex)){//如果下一张牌形成回环则跳出循环
					break;
				}
				showCardsListForCom.add(cards[nextIndex]);
				
				nextIndex = (byte)findNextCardForComputer(nextIndex,cards[nextIndex].getFace());
			}
			//logger.info("===============count "+ showCardsListForCom.size());
			if(showCardsListForCom.size() > max){//记录最大值
				max = showCardsListForCom.size();
				//logger.info("===============max "+ max);
			}
			isComputerFindEnd = false;
			showCardsListForCom.clear();
		}
		return max;
		
	}
	
	/**
	 * 显示玩家点击牌的结果是单独显示一张还是连锁显示多张
	 * @param index 玩家点击的是哪一张牌对应数组下标
	 */
	public boolean showClickResult(byte index){
		//logger.info("翻牌，玩家点击的牌的索引" + index);
		flipChance --;
		if(flipChance < 0){
			flipChance = 0;
			GameUtils.sendTip(
					new TipMessage("You have no chance to flip cards", ProcotolType.CARD_RESP,
							GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			return false;
		}
		if(showCardsList.size() != 0){
			String msg = I18nGreeting.getInstance().getMessage("card.reward", new Object[]{});
			GameUtils.sendTip(
					new TipMessage(msg, ProcotolType.CARD_RESP,
							GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			return false;
		}
		
		showCardsList.add(cards[index]);
		byte nextIndex = (byte)findNextCard(index,cards[index].getFace());
		while(nextIndex != -1){
			if(isCircle(nextIndex)){//如果下一张牌形成回环则跳出循环
				break;
			}
			showCardsList.add(cards[nextIndex]);
			nextIndex = (byte)findNextCard(nextIndex,cards[nextIndex].getFace());
		}
		
		//统计翻牌记录
		LogBuffer lb = new LogBuffer();
		//lb.add(TimeUtils.nowLong());//某时刻
		lb.add(showCardsList.size());//翻开了几张牌
		
		//为翻开的牌按几率生成奖品
		for(Card card : showCardsList){
			SimpleAward sa = generateAward(showCardsList.size());
			if(sa != null){
				card.setWhat(sa.getWhat());
				card.setValue(sa.getValue());
				
				lb.add(sa.getWhat());//获得的是什么
				lb.add(sa.getValue());//获得的数量是多少
			}
		}
		
		//统计某玩家某次翻开几张牌，获得哪些奖品
		GameLog.logPlayerEvent(player, LogEvent.FLIP_CARD, lb);//eg：62478|FLIP_CARD|1355820946464|2|5|1|-3|1|
		/*GameUtils.sendTip(new TipMessage("翻开了" + showCardsList.size()+ "张牌", ProcotolType.CARD_RESP,
						GameConst.GAME_RESP_FAIL), player.getUserInfo());*/
		//logger.info("翻开了" + showCardsList.size()+ "张牌");
		return true;
	}
	
	/**
	 * 是否形成回环
	 * @param index
	 * @return
	 */
	private boolean isCircle(int index){
		for(Card card : showCardsList){
			if(card.getIndex() == index){//如果形成回环
				return true;
			}
		}
		return false;
	}
	
	private boolean isCircleForCom(int index){

		for(Card card : showCardsListForCom){
			if(card.getIndex() == index){//如果形成回环
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 计算机搜索下一张牌
	 * @param index
	 * @param face
	 * @return
	 */
	public int findNextCardForComputer(byte index,byte face){
		if(!isComputerFindEnd){
			//向上找寻
			if(face == CardFace.UP){
				if((index - 3) >= 0){//如果向上找寻没有越界
					if(cards[index - 3].getFace() == CardFace.DOWN){
						isComputerFindEnd = true;
					}
					return index -3;
				}
			}
			
			//向下找寻
			if(face == CardFace.DOWN){
				if((index + 3) <= 8){//如果向下找寻没有越界
					if(cards[index + 3].getFace() == CardFace.UP){
						isComputerFindEnd = true;
					}
					return index + 3;
				}
			}
			
			//向左找寻
			if(face == CardFace.LEFT){
				if((index - 1) >= 0 && index % 3 != 0){//如果可以向左找寻
					if(cards[index - 1].getFace() == CardFace.RIGHT){
						isComputerFindEnd = true;
					}
					return index - 1;
				} 
			}
			
			//向右找寻
			if(face == CardFace.RIGHT){
				if((index + 1) <= 8 && (index + 1) % 3 != 0){//如果可以向右找寻
					if(cards[index + 1].getFace() == CardFace.LEFT){
						isComputerFindEnd = true;
					}
					return index + 1;
				}
			}
		}
		return -1;
	}
	
	/**
	 * 找当前点击牌的连锁牌们
	 * @param index 点击牌的位置对应数组下标
	 * @param face点击 牌面值上下左右
	 */
	public int findNextCard(byte index,byte face){
		if(!isFindEnd){
			//向上找寻
			if(face == CardFace.UP){
				if((index - 3) >= 0){//如果向上找寻没有越界
					if(cards[index - 3].getFace() == CardFace.DOWN){
						isFindEnd = true;
					}
					return index -3;
				}
			}
			
			//向下找寻
			if(face == CardFace.DOWN){
				if((index + 3) <= 8){//如果向下找寻没有越界
					if(cards[index + 3].getFace() == CardFace.UP){
						isFindEnd = true;
					}
					return index + 3;
				}
			}
			
			//向左找寻
			if(face == CardFace.LEFT){
				if((index - 1) >= 0 && index % 3 != 0){//如果可以向左找寻
					if(cards[index - 1].getFace() == CardFace.RIGHT){
						isFindEnd = true;
					}
					return index - 1;
				} 
			}
			
			//向右找寻
			if(face == CardFace.RIGHT){
				if((index + 1) <= 8 && (index + 1) % 3 != 0){//如果可以向右找寻
					if(cards[index + 1].getFace() == CardFace.LEFT){
						isFindEnd = true;
					}
					return index + 1;
				}
			}
		}
		return -1;
	}
	
	/**
	 * 生成奖品
	 * @param showCardsNum 打开牌数
	 */
	private SimpleAward generateAward(int showCardsNum){
		int level = player.getData().getLevel();
		for(CardAwardRate cardAwardRate :cardAwardRates){
			if(cardAwardRate.getShowCardsNum() == showCardsNum //选择符合条件的中奖几率数据
					&& (level >= cardAwardRate.getLowerLimit() && level <= cardAwardRate.getHighLimit())){
				int[] rates = {cardAwardRate.getRate1(),cardAwardRate.getRate2(),cardAwardRate.getRate3()
						,cardAwardRate.getRate4(),cardAwardRate.getRate5(),cardAwardRate.getRate6()};
				
				int[] what = {0,1,2,3,4,5};//或得的是什么0--金币，1--功勋，2--转动次数，3--c类物品，4--b类物品，5--a类物品
				int which = MathUtils.getRandomId1(what,rates,100);
				SimpleAward simpleAward = new SimpleAward();
				if(which == 0){//如果是金币
					simpleAward.setWhat(SimpleAward.GOLD);
					simpleAward.setValue(cardAwardRate.getGoldNum());
				}
				else if(which == 1){//如果是功勋
					simpleAward.setWhat(SimpleAward.HONOR);
					simpleAward.setValue(cardAwardRate.getHonor());
				}
				else if(which == 2){//如果是转动次数
					simpleAward.setWhat(SimpleAward.TURN_TIMES);
					simpleAward.setValue(cardAwardRate.getTurnTimes());
				}
				else if(which == 3 || which == 4 || which == 5){//如果是物品
					int packageId = 0;
					if(which == 3){
						packageId = cardAwardRate.getGoodsC();
					}else if(which == 4){
						packageId = cardAwardRate.getGoodsB();
					}else{
						packageId = cardAwardRate.getGoodsA();
					}
					Package pe = PropsBoxManager.getInstance().packageMap.get(packageId);
					String itemId = pe.getItemId();
					String itemNum = pe.getItemNum();
					String[] itemIds = itemId.split(":");
					String[] itemNums = itemNum.split(":");
					int index = MathUtils.random(itemIds.length);
					simpleAward.setWhat(Integer.parseInt(itemIds[index]));
					simpleAward.setValue(Integer.parseInt(itemNums[index]));
					if(pe.getId() == 47 || pe.getId() == 48){
						forTipAwardList.add(simpleAward);
					}
					//packageid = pe.getId();
				}
				return simpleAward;
			}
		}
		//logger.info("玩家生成奖品失败");
		return null;
	}
	
	/**
	 * 显示玩家翻牌机会和剩余时间
	 */
	public void showChanceAndNextTime(){
		
		if(player.getData().getLastLoginTime() > lastAddChanceTime){//如果玩家重新登录
			long intervalTime = TimeUtils.nowLong()
					- player.getData().getLastLoginTime();
			nextTime = onlineTimeMap.get(turnsForChance).getMin() * 60 - (int)(intervalTime/1000);
			
			//new alter 2012-10-12
			lastAddChanceTime = TimeUtils.nowLong();
		}
		else{
			long intervalTime = TimeUtils.nowLong() - lastAddChanceTime;
			nextTime = onlineTimeMap.get(turnsForChance).getMin() * 60 - (int)(intervalTime/1000);
			if(nextTime <= 0){
				getChance();
			}
		}
		
	}
	
	/**
	 * 玩家登录后翻牌外界面显示剩余时间
	 */
	public void outShowNextTime(){
		
		//如果不是同一天
		if (!TimeUtils.isSameDay(player.getData().getLeaveTime())) {
			/*flipChance = 0;
			rotateChance = 0;
			turnsForChance = 1;*/
			//以上修改，使用默认值即可，避免重置了玩家在内存中的数据
			//logger.info("与上次***不是***同一天进入========" + player.getData().getName());
			//logger.info("本次登录时间：" + new Timestamp(player.getData().getLastLoginTime()));
			//logger.info("上次离开时间：" + new Timestamp(player.getData().getLeaveTime()));
			//logger.info("需要      清除各种chance");
		}
		else{
			//logger.info("与上次***是***同一天进入========" + player.getData().getName());
			//logger.info("本次登录时间：" + new Timestamp(player.getData().getLastLoginTime()));
			//logger.info("上次离开时间：" + new Timestamp(player.getData().getLeaveTime()));
			//logger.info("不需要      清除各种chance");
			if(cards[0] == null){//如果是同一天，且玩家数据不在内存中
				PlayerCards playerCards = DBManager.getInstance().getWorldDAO().getPlayerCardsDAO().findCertainPlayerCards((int)player.getData().getUserid());
				if(playerCards != null){
					turnsForChance = playerCards.getTurnsForChance();
					flipChance = playerCards.getFlipChance();
					rotateChance = playerCards.getRotateChance();
				}
			}	
		}
		long intervalTime = TimeUtils.nowLong()
				- player.getData().getLastLoginTime();
		nextTime = onlineTimeMap.get(turnsForChance).getMin() * 60 - (int)(intervalTime/1000);
		lastAddChanceTime = TimeUtils.nowLong();
		//logger.info("当前时间：" + TimeUtils.nowLong());
		//logger.info("登录时间：" + player.getData().getLastLoginTime());
		
	}
	
	/**
	 * 倒计时到，获取翻牌机会
	 * @return
	 */
	public boolean getChance(){
		
		//判断客户端获取下一次机会请求是否合法
		if(!isGetChanceLegal()){//如果不合法
			//logger.info("倒计时获取翻牌机会时超出允许的合法基准时间范围");
			return false;
		}
		
		//第几次获得翻牌机会次数增加
		turnsForChance ++;
		
		//记录获得翻牌机会时刻
		lastAddChanceTime = TimeUtils.nowLong();
		
		if(turnsForChance <= onlineTimeMap.size()){
			OnlineTime ot = onlineTimeMap.get(turnsForChance);
			nextTime = ot.getMin()* 60;
			//机会增加
			flipChance += ot.getChance();
		}
		return true;
	}
	
	/***
	 * 判断客户端获取下一次机会请求是否合法
	 * @return
	 */
	private boolean isGetChanceLegal(){//判断客户端请求或得翻牌机会是否合法
		
		//两次倒计时的时间差
		int useTime = onlineTimeMap.get(turnsForChance).getMin() * 60;
		
		//合法请求的基准时间，单位秒
		int allowTimeLine = (int)(lastAddChanceTime/1000) + useTime;
		
		//现在时间，单位秒
		int nowTime = (int)(TimeUtils.nowLong()/1000);
		
		if(nowTime >= (allowTimeLine - 20)){//如果则基准时间偏差内，算作合法请求
			return true;
		}
		//logger.info("合法的请求时间范围为：>=" + (allowTimeLine - 20) +  "而现在时间是" + nowTime);
		
		return false;
		
	}
	
	/**
	 * 旋转特定牌
	 * @param index
	 * @return
	 */
	public boolean rotateCertainCard(int index){
		//logger.info("====================玩家：" + player.getData().getName());
		//logger.info("旋转牌，玩家点击的牌的索引" + index);
		if(rotateChance == 0){//玩家没有旋转牌机会
			//logger.info("没有旋转次数");
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(
						new TipMessage("No Rorate Times!", ProcotolType.CARD_RESP,
								GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(
						new TipMessage("没有旋转次数", ProcotolType.CARD_RESP,
								GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}
			
			return false;
		}
		if(cards[index].getWhat() != 0){//如果当前牌处于翻开状态,则不可以旋转
			//logger.info("玩家所点击的牌处于翻开状态，若客户端阻止则不会出现这种情况");
			return false;
		}
		int face = cards[index].getFace();
		boolean canRand = false;
		for(int i=0;i<=3;i++){
			if(i != face){
				if(isNextCanGo(index,i)){
					canRand = true;
					break;
				}
			}
		}
		if(canRand){//如果可以随机，即随机不会产生死循环
			int lastFace = face;//记录上次牌面面值,避免下次随机产生重复
			int tempFace = RandomUtils.nextInt(4);
			boolean canGo = isNextCanGo(index,tempFace);
			while(!canGo || tempFace == lastFace){//如果不可旋转获指向原牌面
				tempFace = RandomUtils.nextInt(4);
				canGo = isNextCanGo(index,tempFace);
				//logger.info("旋转牌产生随机方向");
			}
			cards[index].setFace((byte)tempFace);
			//logger.info("0-up,1-down,2-left,3-right -------" + tempFace);
		}
		
		if(!canRand){//如果未找到满足要求的可旋转方位则随机产生一个方位
			//产生9牌局的牌面，没有产生则还是原来的face一致
			int when9Face = cards[index].getFace();
			
			int tempFace = MathUtils.random(4);
			while(face == tempFace || when9Face == tempFace){
				tempFace = MathUtils.random(4);
			}
			
			cards[index].setFace((byte)tempFace);
			//logger.info("0-up,1-down,2-left,3-right ==========" + tempFace);
		}
		rotateChance --;
		useRotateChance ++;
		
		//记录某玩家某时刻旋转牌
		LogBuffer lb = new LogBuffer();
		//lb.add(TimeUtils.nowLong());
		GameLog.logPlayerEvent(player, LogEvent.ROTATE_CARD, lb);//记录旋转牌 eg:62478|ROTATE_CARD|
		
		return true;
		
	}

	/**
	 * 是否可以指向下一个位置
	 * @param index int
	 * @param face int
	 * @return 
	 */
	public boolean isNextCanGo(int index,int face){
		
		//假设可以向这个方向旋转看是否出现9连环，并作相应处理
		//不允许产生9连环方向
		if(!assumeSoWhat(index,face)){
			return false;
		}
		
		//旋转为向上
		if(face == CardFace.UP){
			if(index - 3 < 0){//如果指向空牌
				return false;//表示不可以旋转为向上
			}
			if(index - 3 >= 0){
				if(cards[index - 3].getFace() == CardFace.DOWN){//如果指向指向自己的牌
					return false;//表示不可以旋转为向上
				}
			}
			
		}
		
		//旋转为向下
		if(face == CardFace.DOWN){
			if((index + 3) > 8){//如果指向空牌
				return false;//表示不可以旋转为向下	
			}
			if((index + 3) <= 8){//如果指向指向自己的牌
				if(cards[index + 3].getFace() == CardFace.UP){
					return false;//表示不可以旋转为向下	
				}
			}
		}
		
		//旋转为向左
		if(face == CardFace.LEFT){
			if(index % 3 == 0){//如果指向空牌
				return false;
			}
			if((index - 1) >= 0 && index % 3 != 0){//如果指向指向自己的牌
				if(cards[index - 1].getFace() == CardFace.RIGHT){
					return false;
				}
			}
		}
		
		//向右找寻
		if(face == CardFace.RIGHT){
			if((index + 1) % 3 == 0){//如果指向空牌
				return false;
			}
			if((index + 1) <= 8 && (index + 1) % 3 != 0){//如果可以向右找寻
				if(cards[index + 1].getFace() == CardFace.LEFT){
					return false;
				}
			}
		}
		
		return true;
		
	}
	
	/**
	 * 假设可以像某一个方向旋转，是否出现9连环，出现9连环是否允许这种局面发生
	 * @param index
	 * @param face
	 * @return true 允许
	 */
	private boolean assumeSoWhat(int index,int face){
		byte oldFace = cards[index].getFace();//暂存以前的牌面，以便假设不成立时还原
		cards[index].setFace((byte)face);//假设可以像这个方向旋转
		if(useRotateChance < 3){//如果出现9连环并且本局旋转次数小于3次，则不允许
			if(9 == maxContinuous()){
				//不还原，以便记录出现9连牌牌面，防止下次随机时产生
				//cards[index].setFace(oldFace);
				return false;
			}	
		}
		//还原牌面
		cards[index].setFace(oldFace);
		return true;

	}
	
	
	/**
	 * 玩家领取奖励
	 * @return
	 */
	public boolean getAward(){
		//logger.info("=================玩家：" + player.getData().getName());
		//如果不可以领奖
		if(!canGetAward()){
			//logger.info("已经领过奖，无奖品可领，客户端若阻止了则不会发生这种情况");
			return false;
		}
		for(Card card : showCardsList){
			String award = "";
			if(card.getWhat() == SimpleAward.GOLD){//如果奖品是金币
				player.saveResources(GameConfig.GAME_MONEY, card.getValue());
				//logger.info("领取奖品或得金币：" + card.getValue());
				//award = "金币*"+card.getValue();
			}
			else if(card.getWhat() == SimpleAward.HONOR){//如果奖品是功勋
				player.saveResources(GameConfig.AWARD, card.getValue());
				//logger.info("领取奖品获得功勋：" + card.getValue());
				//award = "功勋*"+card.getValue();
			}
			else if(card.getWhat() == SimpleAward.TURN_TIMES){//如果奖品是旋转次数
				rotateChance += card.getValue();
				//award = "旋转次数*"+card.getValue();
				//logger.info("领取奖品获得旋转次数：" + card.getValue());
			}
			else{//奖品是物品
				Cell cell = player.getPlayerStorageAgent().addPropsCell(card.getWhat(), card.getValue());
				//主动推送客户端
				RespModuleSet rms=new RespModuleSet(ProcotolType.CARD_RESP);
				rms.addModule(cell);
				rms.addModule(player.getData());
				AndroidMessageSender.sendMessage(rms,player);
				//logger.info("领取奖品获得物品");
				//award = cell.getItem().getName()+"*"+card.getValue();
			}
			
			//清空翻开牌中的奖品
			card.setWhat(0);
			card.setValue(0);
			
			//
		}
		
		//如果是47，48包裹就发消息
		if(forTipAwardList.size() != 0){  //--上面注释部分不可用，若需发系统消息用此部分注释代码 alter -2013-1-17
			int index = RandomUtils.nextInt(forTipAwardList.size());
			SimpleAward simpleAward = forTipAwardList.get(index);
			String name = PropsManager.getInstance().propsDatas.get(simpleAward.getWhat()).getName();
			int num = simpleAward.getValue();
			String msg = I18nGreeting.getInstance().getMessage("card.award",
					new Object[]{player.getData().getName(),name,num});
			NoticeManager.getInstance().sendSystemWorldMessage(msg);
			forTipAwardList.clear();
		}
		
	
		// 重新产生牌局
		//logger.info("重新产生牌局");
		newCardSurface();
		while(isOverMaxWhenOpen()){//如果开局有超过4连的局面则重新开局
			//logger.info("控制产生随机牌不超过4连中。。。");
			newCardSurface();
		}
		//logger.info("开局最大连牌数" + maxContinuous());
		String msg = I18nGreeting.getInstance().getMessage("reward.success", null);
		GameUtils.sendTip(
				new TipMessage(msg, ProcotolType.CARD_RESP,
						GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
		//牌局可找寻
		isFindEnd = false;
		//翻开的牌列表清空
		showCardsList.clear();
		useRotateChance = 0;
		return true;
		
	}
	
	/**
	 * 保存玩家牌局
	 */
	public void saveCards(){

		if(cards[0] != null){//如果玩家在登录游戏后，进入过翻牌游戏
			doSave();
		}
		else{//如果玩家在登录后没有进入过翻牌游戏
			
			//为玩家生成翻牌数据，以便下次进入游戏时解析一致
			newCardSurface();
			while(isOverMaxWhenOpen()){//如果开局有超过4连的局面则重新开局
				newCardSurface();
				//logger.info("为玩家生成保存翻牌数据。。。");
			}
			
			doSave();
		}
		
	}
	
	/**
	 * 执行保存翻牌数据
	 */
	public void doSave(){
		PlayerCards needSaveCards = new PlayerCards();
		int userid = (int)player.getData().getUserid();
		StringBuffer faces = new StringBuffer();
		StringBuffer indexes = new StringBuffer();
		StringBuffer what = new StringBuffer();
		StringBuffer values = new StringBuffer();
		
		for(Card card : cards){
			faces.append(card.getFace()).append(",");
			indexes.append(card.getIndex()).append(",");
			what.append(card.getWhat()).append(",");
			values.append(card.getValue()).append(",");
		}
		needSaveCards.setUserid(userid);
		needSaveCards.setFaces(faces.toString());
		needSaveCards.setIndexes(indexes.toString());
		needSaveCards.setWhat(what.toString());
		needSaveCards.setValues(values.toString());
		needSaveCards.setFlipChance(flipChance);
		needSaveCards.setRotateChance(rotateChance);
		needSaveCards.setTurnsForChance(turnsForChance);
		PlayerCards playerCards = DBManager.getInstance().getWorldDAO().getPlayerCardsDAO().findCertainPlayerCards(userid);
		if(null == playerCards){
			DBManager.getInstance().getWorldDAO().getPlayerCardsDAO().addPlayerCards(needSaveCards);
		}
		else{
			DBManager.getInstance().getWorldDAO().getPlayerCardsDAO().updatePlayerCards(needSaveCards);
		}
	}
	
	/**
	 * 加载玩家牌局数据
	 * 修改后，此方法只控制加载牌面数据，不管理各种chance
	 * @param clearChance true 需要清除各种chance false 不需要清除各种chance   ！！！此参数已删除
	 */
	public void initPlayerCards(){
		//logger.info("====================玩家：" + player.getData().getName());
		//logger.info("玩家登录后首次进入加载牌局信息");
		int userid = (int)player.getData().getUserid();
		//重启服务器后从数据库中读取保存的玩家牌局数据
		PlayerCards playerCards = DBManager.getInstance().getWorldDAO().getPlayerCardsDAO().findCertainPlayerCards(userid);
		if(null != playerCards){//数据库中存在玩家牌局数据
			newCardSurface();
			String faces = playerCards.getFaces();
			String indexes = playerCards.getIndexes();
			String what = playerCards.getWhat();
			String values = playerCards.getValues();
			String[] faceArr = faces.split(",");
			String[] indexArr = indexes.split(",");
			String[] whatArr = what.split(",");
			String[] valueArr = values.split(",");
			for(int i=0;i<cards.length;i++){
				cards[i].setFace(Byte.parseByte(faceArr[i]));
				cards[i].setIndex(Byte.parseByte(indexArr[i]));
				cards[i].setWhat(Integer.parseInt(whatArr[i]));
				cards[i].setValue(Integer.parseInt(valueArr[i]));
				if(cards[i].getWhat() != 0){//如果有未领取的奖品
					showCardsList.add(cards[i]);
				}
			}
			
			/*if(!clearChance){//如果不需要清空各种chance
				flipChance = playerCards.getFlipChance();
				rotateChance = playerCards.getRotateChance();
				turnsForChance = playerCards.getTurnsForChance();
				//logger.info("***不需要***对玩家数据进行清空");
				//logger.info("flipChance: " + flipChance);
				//logger.info("rotateChance: " + rotateChance);
				//logger.info("turnsForChance: " + turnsForChance);
			}
			if(clearChance){
				//logger.info("***需要***对玩家数据进行清空");
				//logger.info("flipChance: " + flipChance);
				//logger.info("rotateChance: " + rotateChance);
				//logger.info("turnsForChance: " + turnsForChance);
			}*/
		}
		else{//数据库中不存在玩家牌局数据
			//logger.info("玩家第一次玩翻牌游戏,生成牌局");
			newCardSurface();
			while(isOverMaxWhenOpen()){//如果开局有超过4连的局面则重新开局
				//logger.info("控制产生随机牌不超过4连中。。。");
				newCardSurface();
			}
			//logger.info("开局最大连牌数" + maxContinuous());
		}
	}
	
	/**
	 * 判断玩家是否可领奖
	 * @return true 可领奖
	 */
	private boolean canGetAward(){
		for(Card card : cards){
			if(card.getWhat() != 0){
				return true;
			}
		}
		return false;
	}
	
	public Card[] getCards() {
		return cards;
	}

	public void setCards(Card[] cards) {
		this.cards = cards;
	}

	public int getNextTime() {
		return nextTime;
	}

	public void setNextTime(int nextTime) {
		this.nextTime = nextTime;
	}

	public int getFlipChance() {
		return flipChance;
	}

	public void setFlipChance(int flipChance) {
		this.flipChance = flipChance;
	}

	public int getRotateChance() {
		return rotateChance;
	}

	public void setRotateChance(int rotateChance) {
		this.rotateChance = rotateChance;
	}

	public List<Card> getShowCardsList() {
		return showCardsList;
	}

	public void setShowCardsList(List<Card> showCardsList) {
		this.showCardsList = showCardsList;
	}

	public int getTurnsForChance() {
		return turnsForChance;
	}

	public void setTurnsForChance(int turnsForChance) {
		this.turnsForChance = turnsForChance;
	}

	public int getUseRotateChance() {
		return useRotateChance;
	}

	public void setUseRotateChance(int useRotateChance) {
		this.useRotateChance = useRotateChance;
	}

	public long getLastAddChanceTime() {
		return lastAddChanceTime;
	}

	public void setLastAddChanceTime(long lastAddChanceTime) {
		this.lastAddChanceTime = lastAddChanceTime;
	}
	
	
	
	

	
	
	
	
	
	
	
}
