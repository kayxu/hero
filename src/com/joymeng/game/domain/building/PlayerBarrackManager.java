package com.joymeng.game.domain.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.LoggerUtils;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.item.props.PropsPrototype;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.soldier.Soldier;
import com.joymeng.game.domain.soldier.SoldierEqu;
import com.joymeng.game.domain.soldier.SoldierManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.net.client.ClientModule;

/**
 * 兵营管理类
 * 
 * @author xufangliang
 * 
 */
public class PlayerBarrackManager {
	private static final Logger logger = LoggerFactory
			.getLogger(PlayerBarrackManager.class);
	
	private static final LoggerUtils logUtil = LoggerUtils.getInstance(PlayerBarrackManager.class.getName());
	
	private static final int MAX_LEVEL = 50;
	private static final int LOCK = 1;//锁定
	private static final int UNLOCK = 0;//已解锁
	static EquipmentManager equipMgr = EquipmentManager.getInstance();
	static BuildingManager bldMgr = BuildingManager.getInstance();
	static SoldierManager soMgr = SoldierManager.getInstance();
	PlayerCharacter owner;
	PlayerBuildingManager pm;

	Map<Byte,Integer> soliderLevel = new HashMap<>();
	public void activation(PlayerCharacter p, PlayerBuildingManager pm) {// 激活主城操作类
		if (p != null) {
			owner = p;
			this.pm = pm;
			getBarrack();
			trainingOver();
			getSoliderLevels();
			if (getBarrack() != null){
				logger.info("用户：" + owner.getData().getUserid() + " 兵营："
						+ getBarrack().getId() + " 控制类加载成功");
			}
		}
	}

	/**
	 * 获取兵营
	 * 
	 * @return
	 */
	public PlayerBuilding getBarrack() {
		return pm.getPlayersBuild(GameConst.BARRACK_ID, (int) owner.getData()
				.getUserid());
	}
	
	/**
	 * 设置默认存储类型
	 * 类型,级别;
	 */
	public void setDefSoliderLevel(){
		String msg = "1:0:0;2:0:0;3:0:0;4:0:0";
		getBarrack().setRemark3(msg);
	}
	
	/**
	 * 解析兵种等级字符串
	 * @return
	 */
	public void getSoliderLevels(){
		String msg = getBarrack().getRemark3();
		if(null == msg || StringUtils.isNull(getBarrack().getRemark3(), "")){
			setDefSoliderLevel();
		}
		String typeSolider = getBarrack().getRemark3();
		String[] soliderTypes = typeSolider.split(";");
		for(String s : soliderTypes){
			if(null != s && !StringUtils.isNull(s, "")){
				String[] soliderType = s.split(":");
				soliderLevel.put(Byte.parseByte(soliderType[0]), Integer.parseInt(soliderType[1])*10+Integer.parseInt(soliderType[2]));
			}
		}
		logger.info("用户="+owner.getId()+"|士兵级别数据="+soliderLevel.toString());
		return ;
	}
	
	/**
	 * 保存士兵等级数据
	 * @param soliderLevel
	 */
	public void saveSiliderLevels(){
		StringBuffer sb= new StringBuffer();
		for(Byte type : soliderLevel.keySet()){
			sb.append(type).append(":").append(getSoliderLevel(type)).append(":").append(getSoliderLock(type)).append(";");
		}
		getBarrack().setRemark3(sb.toString());
		logger.info("用户="+owner.getId()+"|数据="+sb.toString());
		//rms
		RespModuleSet rms=new RespModuleSet(ProcotolType.BUILDING_RESP);
		rms.addModule(getBarrack());
		AndroidMessageSender.sendMessage(rms,owner);
		//rms
	}
	
	/**
	 * 返回用户兵种等级
	 * @param type
	 * @return
	 */
	public int getSoliderLevel(byte type){
		if(null==soliderLevel.get(type)){
			logger.info("用户="+owner.getId() +"|士兵类型="+type+"|数据为空："+getBarrack().getRemark3());
			return 0;
		}else{
			return soliderLevel.get(type)/10;
		}
	}
	
	/**
	 * 返回用户兵种解锁状态
	 * @param type
	 * @return
	 */
	public int getSoliderLock(byte type){
		if(null==soliderLevel.get(type)){
			logger.info("用户="+owner.getId() +"|士兵类型="+type+"|数据为空："+getBarrack().getRemark3());
			return 0;//解锁
		}else{
			return soliderLevel.get(type)%10;
		}
	}
	
	/**
	 * 修改用户兵种锁定状态
	 * @param type
	 * @return
	 */
	public void motifySoliderLock(byte type,int lockType){
		logger.info("用户="+owner.getId() +"|士兵类型="+type+"|锁定状态："+lockType);
		if(null==soliderLevel.get(type)){
			logger.info("用户="+owner.getId() +"|士兵类型="+type+"|数据为空："+getBarrack().getRemark3());
		}else{
			soliderLevel.put(type, getSoliderLevel(type)*10+lockType);
			saveSiliderLevels();
		}
	}
	
	
	/**
	 * 士兵升级
	 */
	public boolean soliderUpgrade(byte type){
		int level = getSoliderLevel(type);
		String message = I18nGreeting.getInstance().getMessage("solider.upGrade.false", null);
		StringBuffer sb = new StringBuffer("\n");
		sb.append("----------------soliderUpgrade --------------\n");
		boolean ret = false;
		if(level >= MAX_LEVEL){
			message = I18nGreeting.getInstance().getMessage("solider.upGrade.fail", null);
			sb.append(message+"\n");
			ret= false;
		}else if(getSoliderLock(type) == LOCK){
			//需要解锁
			message = I18nGreeting.getInstance().getMessage("solider.upGrade.lock", null);
			sb.append(message+"\n");
			ret= false;
		}else{
			SoldierUpGrade su = getUpGradeCondition(type*100+(level+1));
			TipUtil tip = checkUpGradeCondition(su, false);
			if(!tip.isResult()){
				message = tip.getResultMsg();
				ret= false;
				sb.append(message+"\n");
			}else{
				checkUpGradeCondition(su, true);
				int lock = 0;
				
				switch (su.getLockType()) {
				case 0:
					lock = 0;
					break;
				default:
					lock = 1;
				}
				
				soliderLevel.put(type, (level+1)*10+lock);
				saveSiliderLevels();
				ret= true;
				message = I18nGreeting.getInstance().getMessage("solider.upGrade.true", null);
				sb.append("锁定状态"+lock+"\n");
				sb.append(message+"\n");
			}
		}
		sb.append("----------------soliderUpgrade --------------\n");
		GameUtils.sendTip(new TipMessage(message,
				ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
				owner.getUserInfo(), GameUtils.FLUTTER);
		logger.info(sb.toString());
		return ret;
	}
	
	/**
	 * 获取升级条件
	 * @param id 条件id
	 */
	public SoldierUpGrade getUpGradeCondition(int id){
		logger.info("用户="+owner.getId() +"|升级id="+id);
		SoldierUpGrade su = BuildingManager.getInstance().soldierUpGradeDatas.get(id);
		return su;
	}
	
	/**
	 * 校验升级条件
	 * 
	 * @param su 升级条件
	 * @param isConsume  是否扣除道具
	 * @return
	 */
	public TipUtil checkUpGradeCondition(SoldierUpGrade su,boolean isConsume){
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		tip.setSuccTip(I18nGreeting.getInstance().getMessage("solider.upGrade.true", null));
		StringBuffer sb = new StringBuffer("\n");
		List<ClientModuleBase> sendList = new ArrayList<ClientModuleBase>();
		sb.append("----------------checkUpGradeCondition --------------\n");
		sb.append("用户="+owner.getId() +"|升级 \n");
		if(su == null){
			sb.append("用户="+owner.getId() +"|升级|升级条件为空 \n");
			tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.false", null));
		}else{
			sb.append("用户="+owner.getId() +"|升级|升级条件="+su.toString()+" \n");
			//校验木材
			if(su.getWood() >0 && tip.isResult()){
				if(isConsume){
					//消耗扣除
					Cell wood = owner.getPlayerStorageAgent().dellCellAll(GameConfig.GAME_TIMBER_ID, su.getWood());
					sendList.add(wood);
					if(null == wood){
						sb.append("用户="+owner.getId() +"|升级|升级消耗 木材|消耗数量="+su.getWood()+"|失败  \n");
					}else{
						sb.append("用户="+owner.getId() +"|升级|升级消耗 木材|消耗数量="+su.getWood()+"|成功 \n");
					}
				}else{
					//校验
					Cell woods = owner.getPlayerStorageAgent().getCell(GameConfig.GAME_TIMBER_ID);
					//用户道具存在且数量充足
					if(null == woods || !owner.getPlayerStorageAgent().isDelete(woods.getItem(), su.getWood()).getStatus()){
						sb.append("用户="+owner.getId() +"|升级|升级条件wood不足 |需要="+su.getWood()+"\n");
						tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.wood.fail", null));
					}
				}
			}
			//校验矿石
			if(su.getOre() >0 && tip.isResult()){
				if(isConsume){
					//消耗扣除
					Cell ore = owner.getPlayerStorageAgent().dellCellAll(GameConfig.GAME_IRONORE_ID, su.getOre());
					sendList.add(ore);
					if(null == ore){
						sb.append("用户="+owner.getId() +"|升级|升级消耗 矿石|消耗数量="+su.getOre()+"|失败  \n");
					}else{
						sb.append("用户="+owner.getId() +"|升级|升级消耗 矿石|消耗数量="+su.getOre()+"|成功 \n");
					}
				}else{
					Cell ores = owner.getPlayerStorageAgent().getCell(GameConfig.GAME_IRONORE_ID);
					if(null == ores || !owner.getPlayerStorageAgent().isDelete(ores.getItem(), su.getOre()).getStatus()){
						sb.append("用户="+owner.getId() +"|升级|升级条件ore不足 |需要="+su.getOre()+"\n");
						tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.ore.fail", null));
					}
				}
			}
			//校验马匹
			if(su.getHorse() >0 && tip.isResult()){
				if(isConsume){
					//消耗扣除
					Cell horse = owner.getPlayerStorageAgent().dellCellAll(GameConfig.GAME_HORSES_ID, su.getHorse());
					sendList.add(horse);
					if(null == horse){
						sb.append("用户="+owner.getId() +"|升级|升级消耗 马匹|消耗数量="+su.getHorse()+"|失败  \n");
					}else{
						sb.append("用户="+owner.getId() +"|升级|升级消耗 马匹|消耗数量="+su.getHorse()+"|成功 \n");
					}
				}else{
					Cell horses = owner.getPlayerStorageAgent().getCell(GameConfig.GAME_HORSES_ID);
					if(null == horses || !owner.getPlayerStorageAgent().isDelete(horses.getItem(), su.getHorse()).getStatus()){
						sb.append("用户="+owner.getId() +"|升级|升级条件horse不足 |需要="+su.getHorse()+"\n");
						tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.horse.fail", null));
					}
				}
			}
			if(su.getSpecial() != 0 && tip.isResult()){//特殊消耗
				switch (su.getSpecial()) {
				case SoldierUpGrade.GOLD://金币
					if(isConsume){
						int igz=owner.saveResources(GameConfig.GAME_MONEY, -1*su.getNum());
						if(igz == -1){
							sb.append("用户="+owner.getId() +"|升级|升级消耗 金币|消耗数量="+su.getNum()+"|失败  \n");
						}else{
							sb.append("用户="+owner.getId() +"|升级|升级消耗 金币|消耗数量="+su.getNum()+"|成功  \n");
						}
					}else{
						if(owner.getResourcesData(GameConfig.GAME_MONEY) < su.getNum()){
							sb.append("用户="+owner.getId() +"|升级|升级条件="+su.getSpecial()+"不足 |需要="+su.getNum()+"\n");
							tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.gold.fail", null));
						}
					}
					
					break;
				case SoldierUpGrade.DIAMOND://钻石
					if(isConsume){
						int igz= owner.saveResources(GameConfig.JOY_MONEY, -1*su.getNum());
						if(igz == -1){
							sb.append("用户="+owner.getId() +"|升级|升级消耗 钻石|消耗数量="+su.getNum()+"|失败  \n");
						}else{
							sb.append("用户="+owner.getId() +"|升级|升级消耗 钻石|消耗数量="+su.getNum()+"|成功  \n");
						}
					}else{
						if(owner.getResourcesData(GameConfig.JOY_MONEY) < su.getNum()){
							sb.append("用户="+owner.getId() +"|升级|升级条件="+su.getSpecial()+"不足 |需要="+su.getNum()+" \n");
							tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.diamond.fail", null));
						}
					}
					
					break;
				case SoldierUpGrade.MERITORIOUS://功勋
					if(isConsume){
						int igz=owner.saveResources(GameConfig.AWARD, -1*su.getNum());
						if(igz == -1){
							sb.append("用户="+owner.getId() +"|升级|升级消耗 功勋|消耗数量="+su.getNum()+"|失败  \n");
						}else{
							sb.append("用户="+owner.getId() +"|升级|升级消耗 功勋|消耗数量="+su.getNum()+"|成功  \n");
						}
					}else{
						if(owner.getResourcesData(GameConfig.AWARD) < su.getNum()){
							sb.append("用户="+owner.getId() +"|升级|升级条件="+su.getSpecial()+"不足 |需要="+su.getNum()+"\n");
							tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.meritor.fail", null));
						}
					}
					break;
				case SoldierUpGrade.CHIP://筹码
					if(isConsume){
						int igz=owner.saveResources(GameConfig.CHIP, -1*su.getNum());
						if(igz == -1){
							sb.append("用户="+owner.getId() +"|升级|升级消耗 筹码|消耗数量="+su.getNum()+"|失败  \n");
						}else{
							sb.append("用户="+owner.getId() +"|升级|升级消耗 筹码|消耗数量="+su.getNum()+"|成功  \n");
						}
					}else{
						if(owner.getResourcesData(GameConfig.CHIP) < su.getNum()){
							sb.append("用户="+owner.getId() +"|升级|升级条件="+su.getSpecial()+"不足 |需要="+su.getNum()+"\n");
							tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.chip.fail", null));
						}
					}
					break;
				default :
					if(su.getSpecial() > 0){
						if(isConsume){
							//消耗扣除物品
							Cell cell = owner.getPlayerStorageAgent().dellCellAll(su.getSpecial(), su.getNum());
							sendList.add(cell);
							if(null == cell){
								sb.append("用户="+owner.getId() +"|升级|升级消耗 特殊道具="+su.getSpecial()+"|消耗数量="+su.getNum()+"|失败  \n");
							}else{
								sb.append("用户="+owner.getId() +"|升级|升级消耗 特殊道具="+su.getSpecial()+"|消耗数量="+su.getNum()+"|成功 \n");
							}
						}else{
							Cell cell = owner.getPlayerStorageAgent().getCell(su.getSpecial());
							if(null == cell || !owner.getPlayerStorageAgent().isDelete(cell.getItem(), su.getNum()).getStatus()){
								sb.append("用户="+owner.getId() +"|升级|升级条件="+su.getSpecial()+"不足 |需要="+su.getNum()+"\n");
								tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.false", null));
							}
						}
					}else{
						sb.append("用户="+owner.getId() +"|升级|升级条件  没有对应特殊道具类型 \n");
						tip.setFailTip("");
					}
					break;
				}
				sendList.add(owner.getData());
			}
			if(su.getRestrictions() != 0 && tip.isResult()){//限制条件
//				switch (su.getRestrictions()) {
//				case 1://等级
//					
//					break;
//				case 2://职位
//					
//					break;
//				case 3://
//	
//					break;
//				case 4:
//	
//					break;
//				default:
//					break;
//				}
			}
		}
		sb.append("----------------checkUpGradeCondition --------------\n");
		
		//rms
		RespModuleSet rms=new RespModuleSet(ProcotolType.BUILDING_RESP);
		rms.addModuleBase(sendList);
		AndroidMessageSender.sendMessage(rms,owner);
		//rms
		logger.info(sb.toString());
		return tip;
	}
	
	/**
	 * 解锁
	 * @param su
	 */
	public void unLock(byte soliderType){
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		tip.setSuccTip(I18nGreeting.getInstance().getMessage("solider.lock.succ", null));
		StringBuffer sb = new StringBuffer("\n");
		List<ClientModuleBase> sendList = new ArrayList<ClientModuleBase>();
		sb.append("----------------unLock --------------\n");
		sb.append("用户="+owner.getId() +"|解锁\n");
		SoldierUpGrade su = getUpGradeCondition(soliderType*100+getSoliderLevel(soliderType));
		if(su == null){
			sb.append("用户="+owner.getId() +"|解锁|解锁条件为空\n");
			tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.lock.fail", null));
		}else{
			sb.append("用户="+owner.getId() +"|解锁|解锁条件="+su.lockType+"\n");
			if(getSoliderLock(soliderType) != LOCK){//不在解锁状态
				sb.append("用户="+owner.getId() +"|解锁|不在解锁状态\n");
				tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.lock.un.fail", null));
			}else{
				switch (su.lockType) {
				case 0:
					sb.append("用户="+owner.getId() +"|解锁|无需解锁\n");
					tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.lock.none", null));
					break;
				case SoldierUpGrade.GOLD://金币
					if(owner.getResourcesData(GameConfig.GAME_MONEY) < su.getLockCost()){
						sb.append("用户="+owner.getId() +"|解锁|解锁条件  金币不足\n");
						tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.gold.fail", null));
					}else{
						int i = owner.saveResources(GameConfig.GAME_MONEY, -1*su.getLockCost());
						sendList.add(owner.getData());
						if(i == -1){
							sb.append("用户="+owner.getId() +"|解锁|解锁消耗 金币|消耗数量="+su.getLockCost()+"|失败  \n");
						}else{
							sb.append("用户="+owner.getId() +"|解锁|解锁消耗 金币|消耗数量="+su.getLockCost()+"|成功  \n");
						}
					}
					break;
				case SoldierUpGrade.DIAMOND://钻石
					if(owner.getResourcesData(GameConfig.JOY_MONEY) < su.getLockCost()){
						sb.append("用户="+owner.getId() +"|解锁|解锁条件 钻石 不足 |需要="+su.getLockCost()+"\n");
						tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.diamond.fail", null));
					}else{
						int i = owner.saveResources(GameConfig.GAME_MONEY, -1*su.getLockCost());
						sendList.add(owner.getData());
						if(i == -1){
							sb.append("用户="+owner.getId() +"|解锁|解锁消耗 钻石|消耗数量="+su.getLockCost()+"|失败  \n");
						}else{
							sb.append("用户="+owner.getId() +"|解锁|解锁消耗 钻石|消耗数量="+su.getLockCost()+"|成功  \n");
						}
					}
					break;
				case SoldierUpGrade.MERITORIOUS://功勋
					if(owner.getResourcesData(GameConfig.AWARD) < su.getLockCost()){
						sb.append("用户="+owner.getId() +"|解锁|解锁条件 功勋 不足 |需要="+su.getLockCost()+"\n");
						tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.meritor.fail", null));
					}else{
						int i = owner.saveResources(GameConfig.GAME_MONEY, -1*su.getLockCost());
						sendList.add(owner.getData());
						if(i == -1){
							sb.append("用户="+owner.getId() +"|解锁|解锁消耗 功勋|消耗数量="+su.getLockCost()+"|失败  \n");
						}else{
							sb.append("用户="+owner.getId() +"|解锁|解锁消耗 功勋|消耗数量="+su.getLockCost()+"|成功  \n");
						}
					}
					break;
				case SoldierUpGrade.CHIP://筹码
					if(owner.getResourcesData(GameConfig.CHIP) < su.getLockCost()){
						sb.append("用户="+owner.getId() +"|解锁|解锁条件 筹码不足 |需要="+su.getLockCost()+"\n");
						tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.upGrade.chip.fail", null));
					}else{
						int i = owner.saveResources(GameConfig.GAME_MONEY, -1*su.getLockCost());
						sendList.add(owner.getData());
						if(i == -1){
							sb.append("用户="+owner.getId() +"|解锁|解锁消耗  筹码|消耗数量="+su.getLockCost()+"|失败  \n");
						}else{
							sb.append("用户="+owner.getId() +"|解锁|解锁消耗  筹码|消耗数量="+su.getLockCost()+"|成功  \n");
						}
					}
					break;
				default:
					if(su.lockType > 0){
						Cell cell = owner.getPlayerStorageAgent().getCell(su.lockType);
						if(null == cell || !owner.getPlayerStorageAgent().isDelete(cell.getItem(), su.getLockCost()).getStatus()){
							sb.append("用户="+owner.getId() +"|解锁|解锁条件="+su.lockType+"不足 |需要="+su.getLockCost()+"\n");
							tip.setFailTip(I18nGreeting.getInstance().getMessage("solider.lock.fail", null));
						}else{
							//消耗扣除物品
							Cell del = owner.getPlayerStorageAgent().dellCellAll(su.getSpecial(), su.getNum());
							sendList.add(del);
							if(null == del){
								sb.append("用户="+owner.getId() +"|解锁|解锁消耗 特殊道具="+su.lockType+"|消耗数量="+su.getLockCost()+"|失败  \n");
							}else{
								sb.append("用户="+owner.getId() +"|解锁|解锁消耗 特殊道具="+su.lockType+"|消耗数量="+su.getLockCost()+"|成功 \n");
							}
						}
					}else{
						sb.append("用户="+owner.getId() +"|解锁|解锁条件  没有对应特殊道具类型 \n");
						tip.setFailTip("");
					}
					break;
				}
			}
		}
		sb.append("----------------checkUpGradeCondition --------------\n");
		//修改解锁状态
		if(tip.isResult()){
			motifySoliderLock(soliderType, UNLOCK);
			//rms
			RespModuleSet rms=new RespModuleSet(ProcotolType.BUILDING_RESP);
			rms.addModuleBase(sendList);
			AndroidMessageSender.sendMessage(rms,owner);
			//rms
			
		}
		logger.info(sb.toString());
		GameUtils.sendTip(new TipMessage(tip.getResultMsg(), ProcotolType.BUILDING_CHARGEOUT, GameConst.GAME_RESP_SUCCESS), owner.getUserInfo(), GameUtils.FLUTTER);
	}

	/**
	 * 得到士兵训练数据
	 * @param soldieId
	 * @return
	 */
	public TrainingSoldier getSoldierTrain(int soldieId) {
		Soldier s = SoldierManager.getInstance().getSoldier(soldieId);
		if (s == null)
			return null;
		else {
			if (s.isSpecialSoldier()) {
				// 特种兵
				return bldMgr.getSoldierTrain(getBarrack().getBuildLevel(),
						(short) 2);
			} else {
				return bldMgr.getSoldierTrain(getBarrack().getBuildLevel(),
						(short) 1);
			}
		}
	}

	/**
	 * 修改训练时间
	 * 
	 * @param soldierId
	 * @param lon
	 * @return
	 */
	public PlayerSoldier changeTrainingTime(int soldierId, int lon) {
		StringBuffer sb = new StringBuffer();
		sb.append("加速士兵：" + soldierId + " 加速时间:" + lon + "秒 \n");
		Map<Integer, PlayerSoldier> map = getSoldier();
		if (map == null || map.size() == 0) {
			return null;
		} else {
			PlayerSoldier ps = map.get(soldierId);
			// 完成时间大于现在 才有修改的必要
			if (ps != null
					&& lon > 0
					&& ps.getTrainingTime() > TimeUtils.nowLong()) {
				ps.setTrainingTime(ps.getTrainingTime() - lon * 1000);
				if (TimeUtils.nowLong() > ps
						.getTrainingTime()) {
					ps.setSoldierCount(ps.getSoldierCount()
							+ ps.trainingSoldierCount);
					ps.setTrainingSoldierCount(0);
				}
				// logger.info("加速训练后：" + ps.toString());
			} else {
				return null;
			}
			logger.info(sb.toString());
			saveMapToString(map);// 保存
			return ps;
		}
	}

	/**
	 * 训练结束
	 */
	public Map<Integer, PlayerSoldier> trainingOver() {
		StringBuffer sb = new StringBuffer();
		sb.append("结束训练！\n");
		Map<Integer, PlayerSoldier> so = getSoldier();
		if (so != null) {
			for (PlayerSoldier p : so.values()) {
				if (TimeUtils.nowLong()
						- p.getTrainingTime() > 0
						&& p.trainingSoldierCount > 0) {
					sb.append("训练结束士兵id:" + p.getPlayerId());
					p.setSoldierCount(p.getTrainingSoldierCount()
							+ p.getSoldierCount());
					p.setPlayerId((int) owner.getData().getUserid());
					p.setTrainingSoldierCount(0);
				}
			}
//			logger.info(sb.toString());
			saveMapToString(so);
			return so;
		}
		return null;
	}

	/**
	 * 查看士兵状态
	 * @param type
	 * @return
	 */
	public List<PlayerSoldier> viewSoldier(byte type) {// 是否特种兵 byte 1 = 特种兵
		trainingOver();
		Map<Integer, PlayerSoldier> so = getSoldier();
		if (so == null)
			return null;
		else {
			List<PlayerSoldier> lst = new ArrayList<PlayerSoldier>();
			for (PlayerSoldier p : so.values()) {
				Soldier s = SoldierManager.getInstance().getSoldier(p.getSoldierId());
				if (s == null){
					logger.info("用户="+owner.getId()+"|士兵id="+p.getSoldierId()+"|固化数据错误");
					return null;
				}
				if (type == 1) {
					if (p.isSpecialSoldier()) {
						lst.add(p);
					}
				} else {
					if (!p.isSpecialSoldier()) {
						lst.add(p);
					}
				}

			}
			return lst;
		}
	}

	/**
	 * 训练士兵
	 * 
	 * @param soldierId
	 * @param count
	 * @param type
	 *            1：金币 2：功勋
	 */
	public Map<Integer, PlayerSoldier> trainingSoldier(TrainingSoldier tso,
			int soldierId, int count, byte type) {
		Soldier s = SoldierManager.getInstance().getSoldier(soldierId);
		if(null == s){
			logger.info("用户="+owner.getId()+"|士兵id="+soldierId+"|固化数据错误");
			return null;
		}
		if (s.getType() == 4) {//特种兵
			if (getSpecialSolder() != soldierId) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(new TipMessage("Your empire is unable to recruit such special troops.",
							ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
							owner.getUserInfo(), GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(new TipMessage("本国无法生产此类特种兵",
							ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
							owner.getUserInfo(), GameUtils.FLUTTER);
				}
				return null;
			}
			if (owner.getData().getTitle() < 2) {
				String msg = I18nGreeting.getInstance().getMessage(
						"soldier.train", null);
				GameUtils.sendTip(new TipMessage(msg,
						ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
						owner.getUserInfo(), GameUtils.FLUTTER);
				return null;
			}
		}
		// 获取建造时间
//		sb.append("训练士兵：" + soldierId + "  数量：" + count + " 类型：" + type +"\n");
		if (soldierId == 0)
			return null;
		Map<Integer, PlayerSoldier> map = trainingOver();
		if (type == 1 && count > 0) {// 金币训练
			// 消耗金币
			int gold = count * tso.getGold();
			if (owner.getData().getGameMoney() < gold) {
				return map;
			}
			int ll = tso.getTrainingTime();
			long ts = TimeUtils.nowLong() + ll * count
					* 1000;
			if (map != null && map.size() > 0) {
				PlayerSoldier ps = map.get(soldierId);
				if (ps != null) {
					if (ps.getTrainingSoldierCount() == 0) {
						if (ll == 0) {
							ps.setSoldierCount(ps.getSoldierCount() + count);
						} else {
							ps.setTrainingSoldierCount(count);
							ps.setTrainingTime(ts);
						}

					} else {
//						sb.append("士兵id:" + ps.getSoldierId() + " 士兵没有训练完成："
//								+ ps.getTrainingTime());
						return null;
					}
				} else {
					if (ll == 0) {
						PlayerSoldier ps1 = new PlayerSoldier();
						ps1.setPlayerId((int) owner.getData().getUserid());
						ps1.setTrainingSoldierCount(0);
						ps1.setSoldierCount(count);
						ps1.setSoldierId(soldierId);
						ps1.setTrainingTime(ts);
						map.put(ps1.getSoldierId(), ps1);
					} else {
						PlayerSoldier ps1 = new PlayerSoldier();
						ps1.setPlayerId((int) owner.getData().getUserid());
						ps1.setTrainingSoldierCount(count);
						ps1.setSoldierCount(0);
						ps1.setSoldierId(soldierId);
						ps1.setTrainingTime(ts);
						map.put(ps1.getSoldierId(), ps1);
					}

				}
				// map.put(soldierId, ps);
			} else {
				if (ll == 0) {
					map = new HashMap<Integer, PlayerSoldier>();
					PlayerSoldier ps = new PlayerSoldier();
					ps.setPlayerId((int) owner.getData().getUserid());
					ps.setTrainingSoldierCount(0);
					ps.setSoldierCount(count);
					ps.setSoldierId(soldierId);
					ps.setTrainingTime(ts);
					map.put(ps.getSoldierId(), ps);
				} else {
					map = new HashMap<Integer, PlayerSoldier>();
					PlayerSoldier ps = new PlayerSoldier();
					ps.setPlayerId((int) owner.getData().getUserid());
					ps.setTrainingSoldierCount(count);
					ps.setSoldierCount(0);
					ps.setSoldierId(soldierId);
					ps.setTrainingTime(ts);
					map.put(ps.getSoldierId(), ps);
				}

			}
			if (saveMapToString(map)) {
				// 消耗金币
				// int gold = count * tso.getGold();
				owner.saveResources(GameConfig.GAME_MONEY, -1 * gold);
//				owner.saveData();
				// Rms
				RespModuleSet rms = new RespModuleSet(
						ProcotolType.BUILDING_RESP);
				rms.addModule(owner.getData());
				AndroidMessageSender.sendMessage(rms, owner);
				// rms
			}
			return map;

		} else if (type == 2 && count > 0) {// 功勋训练
			if (owner.getData().getCityLevel() < 1) {
				if(I18nGreeting.LANLANGUAGE_TIPS==1){
					GameUtils.sendTip(new TipMessage("You need upgrade your title to Knight or higher.",
							ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
							owner.getUserInfo(), GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(new TipMessage("爵位需要骑士以上",
							ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
							owner.getUserInfo(), GameUtils.FLUTTER);
				}
				
				return null;
			}
			int honor = count / tso.getHonorCount() == 0 ? 1 : count
					/ tso.getHonorCount();// 需要功勋
			if (owner.getData().getAward() < honor) {
				return map;
			}
			if (map != null && map.size() > 0) {
				PlayerSoldier ps = map.get(soldierId);
				if (ps != null)
					// if (ps.getTrainingSoldierCount() == 0) {
					ps.setSoldierCount(ps.getSoldierCount() + count);
				// } else {
				// logger.info("士兵id:" + ps.getSoldierId() + " 士兵没有训练完成："
				// + ps.getTrainingTime());
				// return null;
				// }
				else {
					PlayerSoldier ps1 = new PlayerSoldier();
					ps1.setPlayerId((int) owner.getData().getUserid());
					ps1.setTrainingSoldierCount(0);
					ps1.setSoldierCount(count);
					ps1.setSoldierId(soldierId);
					ps1.setTrainingTime(TimeUtils.nowLong());
					map.put(ps1.getSoldierId(), ps1);
				}
			} else {
				map = new HashMap<Integer, PlayerSoldier>();
				PlayerSoldier ps = new PlayerSoldier();
				ps.setPlayerId((int) owner.getData().getUserid());
				ps.setSoldierCount(count);
				ps.setSoldierId(soldierId);
				ps.setTrainingSoldierCount(0);
				ps.setTrainingTime(TimeUtils.nowLong());
				map.put(ps.getSoldierId(), ps);
			}
			if (saveMapToString(map)) {
				// int honor = count / tso.getHonorCount();// 需要功勋
				owner.saveResources(GameConfig.AWARD, -1 * honor);// 消耗功勋
//				owner.saveData();
				// Rms
				RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
				rms.addModule(owner.getData());
				AndroidMessageSender.sendMessage(rms, owner);
				// rms
			}
			QuestUtils.checkFinish(owner, QuestUtils.TYPE33, true);
			return map;

		}
		return null;
	}

	/**
	 * 字符串装载成 PlayerSoldier 对象
	 * 
	 * @param ps
	 * @return
	 */
	public PlayerSoldier toPlayerSoldier(String ps) {
		try {
			String[] so = ps.split(",");
			PlayerSoldier pso = new PlayerSoldier();
			// pso.setPlayerId(player.getData().getUserid());
			if (StringUtils.isNull(so[0], "")) {
				return null;
			}
			Integer pId = Integer.parseInt(so[0]);
			pso.setPlayerId(pId);
			Integer sId = Integer.parseInt(so[1]);
			pso.setSoldierId(sId);
			Integer sCount = Integer.parseInt(so[2]);
			pso.setSoldierCount(sCount);
			Integer sTrainingCount = Integer.parseInt(so[3]);
			pso.setTrainingSoldierCount(sTrainingCount);
			long time = StringUtils.isNull(so[4], "") ? TimeUtils.nowLong() : Long.parseLong(so[4]);
			pso.setTrainingTime(time);

			return pso;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取用户士兵
	 * 
	 * @return
	 */
	public Map<Integer, PlayerSoldier> getSoldier() {
		// 兵营的
		Map<Integer, PlayerSoldier> map = new HashMap<Integer, PlayerSoldier>();
		if (getBarrack() == null
				|| StringUtils.isNull(getBarrack().getSoldierMsg(), ""))
			return map;
//		logger.info("**士兵数据**：" + getBarrack().getSoldierMsg());

		String[] pSoldiers = getBarrack().getSoldierMsg().split(";");
		for (int i = 0; i < pSoldiers.length; i++) {
			if (pSoldiers[i] != null && !"".equals(pSoldiers[i])) {
				PlayerSoldier pso = toPlayerSoldier(pSoldiers[i]);
				if (pso != null)
					map.put(pso.getSoldierId(), pso);
			}
		}
		return map;
	}

	public void addSoliderNum(int id, int count) {
		trainingOver();
		boolean isOn = false;
		Map<Integer, PlayerSoldier> map =  getSoldier();
		if(map != null){
			for(PlayerSoldier ps : map.values()){
				if(ps.getSoldierId() == id){
					if(count < 0){
						count = 0;
					}
					ps.setSoldierCount(count);
					isOn = true;
				}
			}
			if(!isOn){//不存在添加
				PlayerSoldier ps = new PlayerSoldier();
				ps.setPlayerId((int) owner.getData().getUserid());
				ps.setSoldierCount(count);
				ps.setSoldierId(id);
				ps.setTrainingSoldierCount(0);
				ps.setTrainingTime(TimeUtils.nowLong());
				map.put(ps.getSoldierId(), ps);
			}
		}
		saveMapToString(map);
	}

	/**
	 * 解析字符串
	 * 
	 * @param soMsg
	 * @return
	 */
	public static HashMap<Integer, String> resolveSoMsg(String soMsg) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		if (soMsg != null && soMsg.length() > 0) {
			String[] so = soMsg.split(";");
			if (so != null && so.length > 0) {
				for (String s : so) {
					String[] scount = s.split(":");
					map.put(Integer.parseInt(scount[0]), scount[1]);
				}
			}
		}
		return map;
	}

	/**
	 * 生成字符串 1:4,2 1:士兵id, 4:士兵数量 ,2:兵装数量
	 * 
	 * @param map
	 * @return
	 */
	public static String generateSoMsg(HashMap<Integer, String> map) {
		StringBuffer sb = new StringBuffer();
		if (map != null && map.size() > 0) {
			for (Integer i : map.keySet()) {
				if (map.get(i).indexOf("-") == -1) {// 没有负的数据,没有0数据
					sb.append(i).append(":").append(map.get(i)).append(";");
				}
			}
		}
		return sb == null || sb.length() == 0 ? null : sb.toString().substring(
				0, sb.toString().length() - 1);
	}

	/**
	 * 派兵
	 * 
	 * @param soMsg
	 *            兵种id:数量；
	 * @return
	 */
	public boolean dispatchSoldier(String soMsg) {
		Map<Integer, PlayerSoldier> so = getSoldier();
		HashMap<Integer, String> sMsg = resolveSoMsg(check(soMsg));
		HashMap<Integer, Integer> newMsg = new HashMap<Integer, Integer>();
		boolean falg = false;
		StringBuffer sb = new StringBuffer();
		if (so == null || so.size() == 0 || sMsg == null || sMsg.size() == 0) {
			sb.append("**********兵营无兵");
			return false;
		} else {
			int count = 0;
			for (Integer i : sMsg.keySet()) {
				if (i > 3) {
					count++;
				}
				String[] se = sMsg.get(i).split(",");
				int sCount = so.get(i).getSoldierCount()
						- Integer.parseInt(se[0]);
				if (sCount >= 0) {

					if (Integer.parseInt(se[1]) > 0) {
						if (Integer.parseInt(se[0]) > getsoliderEquNum(i)) {
							sb.append("************兵装不足");
							newMsg.put(i, getsoliderEquNum(i));
						} else {
							newMsg.put(i, Integer.parseInt(se[0]));
						}
					} else {
						newMsg.put(i, 0);
					}
					so.get(i).setSoldierCount(sCount);
					falg = true;
				} else {
					sb.append("**********" + i + ":种士兵不足");
					falg = false;
				}
			}
			if (falg && count >= 2) {
				sb.append("********特种兵超过两种");
				falg = false;
			}

			if (so != null && falg) {
				saveMapToString(so);
				for (Integer i : newMsg.keySet()) {
					//String[] se = sMsg.get(i).split(",");
//					so.get(i).setSoldierCount(
//							so.get(i).getSoldierCount()
//									- Integer.parseInt(se[0]));
//					logger.info("******扣除兵装:" + i + "|原兵装："+getsoliderEquNum(i)+"|" + newMsg.get(i));
					savesoliderEquNum(i, -1 * newMsg.get(i));
//					logger.info("******扣除兵装后:" + i + "|原兵装："+getsoliderEquNum(i));
					// owner.saveData();
				}
				// return falg;
			}
		}
		logger.info(sb.toString());
		
		logger.info("**士兵数据**：" + getBarrack().getSoldierMsg());
		return falg;
	}

	public String check(String soMsg) {
		Map<Integer, PlayerSoldier> so = getSoldier();
		HashMap<Integer, String> sMsg = resolveSoMsg(soMsg);
		if (so == null || so.size() == 0 || sMsg == null || sMsg.size() == 0)
			return "";
		else {
			for (Integer i : sMsg.keySet()) {
				String[] se = sMsg.get(i).split(",");
				if (so.get(i) == null) {
//					logger.info("士兵不足!");
					return "";
				}
				int sCount = so.get(i).getSoldierCount()
						- Integer.parseInt(se[0]);
				if (sCount >= 0) {
					if (Integer.parseInt(se[1]) > 0) {
						if (Integer.parseInt(se[0]) >= getsoliderEquNum(i)) {
							sMsg.put(i, se[0] + "," + getsoliderEquNum(i));
						} else {
							sMsg.put(i, se[0] + "," + se[0]);
						}
					}
				} else {
//					logger.info("士兵不足!");
					return "";
				}
			}
			return generateSoMsg(sMsg);
		}
	}

	public String checkSolider(String soMsg) {
//		logger.info("派兵数据:" + soMsg);
		// Map<Integer, PlayerSoldier> so = getSoldier();
		HashMap<Integer, String> sMsg = resolveSoMsg(soMsg);
		// if (so == null || so.size() == 0 || sMsg == null || sMsg.size() == 0)
		// return "";
		// else {
		for (Integer i : sMsg.keySet()) {
			String[] se = sMsg.get(i).split(",");
			int sCount = Integer.parseInt(se[0]);
			if (sCount >= 0) {
				if ("-1".equals(se[1])) {//需要兵装
					if (Integer.parseInt(se[0]) >= getsoliderEquNum(i)) {
						sMsg.put(i, se[0] + "," + getsoliderEquNum(i));
					} else {
						sMsg.put(i, se[0] + "," + se[0]);
					}
				}
			} else {
				// sMsg.remove(i);
				return "";
				// sMsg.put(i, "0,0");
			}
		}
		return generateSoMsg(sMsg);
		// }
	}

	/**
	 * 回收兵
	 * 
	 * @param soMsg
	 *            兵种id:数量；
	 * @return
	 */
	public boolean recoverSoldier(String soMsg) {
		Map<Integer, PlayerSoldier> so = getSoldier();
		HashMap<Integer, String> sMsg = resolveSoMsg(soMsg);
		if (sMsg == null || sMsg.size() == 0)
			return true;
		else {
			if (so != null) {
				for (Integer i : sMsg.keySet()) {
					PlayerSoldier ps = so.get(i);
					String[] se = sMsg.get(i).split(",");
					if (ps != null) {
						ps.setSoldierCount(so.get(i).getSoldierCount()
								+ Integer.parseInt(se[0]));
//						logger.info("******回收兵装后:" + i + "|原兵装："+getsoliderEquNum(i)+"|数量："+Integer.parseInt(se[1]));
						savesoliderEquNum(i, Integer.parseInt(se[1]));// 保存入用户数据
//						logger.info("******回收兵装后:" + i + "|原兵装："+getsoliderEquNum(i));
					} else {
						ps = new PlayerSoldier();
						ps.setPlayerId((int) owner.getData().getUserid());
						ps.setSoldierCount(Integer.parseInt(se[0]));
						ps.setSoldierId(i);
						ps.setTrainingSoldierCount(0);
						ps.setTrainingTime(TimeUtils.nowLong());
						so.put(i, ps);
//						logger.info("******回收兵装后:" + i + "|原兵装："+getsoliderEquNum(i)+"|数量："+Integer.parseInt(se[1]));
						savesoliderEquNum(i, Integer.parseInt(se[1]));// 保存入用户数据
//						logger.info("******回收兵装后:" + i + "|原兵装："+getsoliderEquNum(i));
					}

				}
				saveMapToString(so);
				return true;
			} else {
				so = new HashMap<Integer, PlayerSoldier>();
				for (Integer i : sMsg.keySet()) {
					String[] se = sMsg.get(i).split(",");
					PlayerSoldier ps = new PlayerSoldier();
					ps.setPlayerId((int) owner.getData().getUserid());
					ps.setSoldierCount(Integer.parseInt(se[0]));
					ps.setSoldierId(i);
					ps.setTrainingSoldierCount(0);
					ps.setTrainingTime(TimeUtils.nowLong());
					so.put(i, ps);
//					logger.info("******回收兵装后:" + i + "|原兵装："+getsoliderEquNum(i)+"|数量："+Integer.parseInt(se[1]));
					savesoliderEquNum(i, Integer.parseInt(se[1]));// 保存入用户数据
//					logger.info("******回收兵装后:" + i + "|原兵装："+getsoliderEquNum(i));
					// owner.saveData();
				}
				saveMapToString(so);
				return true;
			}
		}
	}

	/**
	 * 更新进数据库
	 */
	public synchronized boolean saveMapToString(Map<Integer, PlayerSoldier> ps) {
		StringBuffer sb = new StringBuffer();
		if (pm == null)
			return false;
		if (ps != null && ps.size() > 0) {
			for (PlayerSoldier p : ps.values()) {
				if (p.getSoldierCount() > 0 || p.getTrainingSoldierCount() > 0) {
					sb.append(p.toString()).append(";");
				}
			}
		}
		if (getBarrack() != null) {
			String soldierMsg = sb.toString();

			getBarrack().setSoldierMsg(soldierMsg);
//			pm.savePlayerBuilding(getBarrack());
			pm.playerSolDatas = ps;
//			logger.info("保存士兵数据：" + soldierMsg);
			return true;
		}
		return false;
	}

	/**
	 * 保存某类的兵装
	 * 
	 * @param soliderId
	 * @return
	 */
	public void savesoliderEquNum(int soliderId, int num) {
		switch (soliderId) {
		case 1:
			owner.recordRoleData(GameConfig.ARCHEREQU, num);
			break;
		case 2:
			owner.recordRoleData(GameConfig.CAVALRYEQU, num);
			break;
		case 3:
			owner.recordRoleData(GameConfig.INFANTRYEQU, num);
			break;
		case 4:
			owner.recordRoleData(GameConfig.SPECIALARMS, num);
			break;
		case 5:
			owner.recordRoleData(GameConfig.SPECIALARMS, num);
			break;
		case 6:
			owner.recordRoleData(GameConfig.SPECIALARMS, num);
			break;
		}
//		owner.saveData();
		// //rms
		// RespModuleSet rms=new RespModuleSet(ProcotolType.USER_INFO_RESP);
		// rms.addModule(owner.getData());
		// AndroidMessageSender.sendMessage(rms, owner);
		// //rms
	}

	/**
	 * 得到某类的兵装
	 * 
	 * @param soliderId
	 * @return
	 */
	public int getsoliderEquNum(int soliderId) {
		switch (soliderId) {
		case 1:
			return owner.getData().getArcherEqu();
		case 2:
			return owner.getData().getCavalryEqu();
		case 3:
			return owner.getData().getInfantryEqu();
		case 4:
			return owner.getData().getSpecialArms();
		case 5:
			return owner.getData().getSpecialArms();
		case 6:
			return owner.getData().getSpecialArms();
		}
		return 0;
	}

	/**
	 * 两个字符串 派兵 baseMsg 基本 soMsg 回收兵
	 */
	public static String disSomsg(String baseMsg, String soMsg) {
		HashMap<Integer, String> map = PlayerBarrackManager.resolveSoMsg(soMsg);// 收兵数据
		HashMap<Integer, String> maps = PlayerBarrackManager
				.resolveSoMsg(baseMsg);
		if (map == null || map.size() == 0)
			return baseMsg;
		for (Integer i : map.keySet()) {
			if (maps.get(i) == null) {
				maps.put(i, map.get(i));
			} else {
				String[] soe = map.get(i).split(",");
				String[] base = maps.get(i).split(",");
				maps.put(
						i,
						Integer.parseInt(base[0]) + Integer.parseInt(soe[0])
								+ "," + Integer.parseInt(base[1])
								+ Integer.parseInt(soe[1]));
			}
		}
		return generateSoMsg(maps);
	}

	/**
	 * 两个字符串合成数据 baseMsg 基本 soMsg 回收兵
	 */
	public static String addSomsg(String baseMsg, String soMsg) {
		HashMap<Integer, String> map = PlayerBarrackManager.resolveSoMsg(soMsg);// 收兵数据
		HashMap<Integer, String> maps = PlayerBarrackManager
				.resolveSoMsg(baseMsg);
		if (map == null || map.size() == 0)
			return baseMsg;
		for (Integer i : map.keySet()) {
			if (maps.get(i) == null) {
				maps.put(i, map.get(i));
			} else {
				String[] soe = map.get(i).split(",");
				String[] base = maps.get(i).split(",");
				maps.put(
						i,
						Integer.parseInt(base[0]) + Integer.parseInt(soe[0])
								+ "," + Integer.parseInt(base[1])
								+ Integer.parseInt(soe[1]));
			}
		}
		return generateSoMsg(maps);
	}

	/**
	 * 制作兵装
	 * 
	 * @param id
	 * @param num
	 */
	public TipUtil soliderEqu(int id, int num) {
//		logger.info("兵装类型:" + id + "|数量:" + num);
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		// tip.setFailTip("");
		SoldierEqu soe = soMgr.getEqu(id);
		if (soe == null) {
			// tip.setFailTip("fail");
			return tip;
		} else {
			tip = equNum(soe, num);
			if (tip.isResult()) {
				List<ClientModule> lst = new ArrayList<ClientModule>();
				savesoliderEquNum(id, num);
				lst.add(owner.getData());
				if (soe.getWood() > 0)// 木材
				{
//					logger.info("扣除材料:木材:" + soe.getWood() * num);
					Cell cell = owner.getPlayerStorageAgent().dellCell(
							GameConfig.GAME_TIMBER_ID, soe.getWood() * num);

					if (null != cell) {
						Props props = (Props) cell.getItem();
						logger.info("useTime="
								+ TimeUtils.now().format(TimeUtils.FORMAT1)
								+ " propsId=" + props.getPrototype().getId()
								+ " propsName="
								+ props.getPrototype().getName()
								+ " useNumber=" + num + " uid=" + owner.getId()
								+ " uname=" + owner.getData().getName());
						GameLog.logSystemEvent(LogEvent.USE_PROPS,
								String.valueOf(props.getPrototype().getId()),
								props.getPrototype().getName(),
								String.valueOf(num),
								String.valueOf(owner.getId()));
					}

					lst.add(cell);
				}
				if (soe.getIron() > 0)// 铁块
				{
//					logger.info("扣除材料:铁块:" + soe.getIron() * num);
					Cell cell = owner.getPlayerStorageAgent().dellCell(
							GameConfig.GAME_IRONORE_ID, soe.getIron() * num);

					if (null != cell) {
						Props props = (Props) cell.getItem();
						logger.info("useTime="
								+ TimeUtils.now().format(TimeUtils.FORMAT1)
								+ " propsId=" + props.getPrototype().getId()
								+ " propsName="
								+ props.getPrototype().getName()
								+ " useNumber=" + num + " uid=" + owner.getId()
								+ " uname=" + owner.getData().getName());

						GameLog.logSystemEvent(LogEvent.USE_PROPS,
								String.valueOf(props.getPrototype().getId()),
								props.getPrototype().getName(),
								String.valueOf(num),
								String.valueOf(owner.getId()));
					}

					lst.add(cell);
				}
				if (soe.getFur() > 0)// 毛皮
				{
//					logger.info("扣除材料:毛皮:" + soe.getFur() * num);
					Cell cell = owner.getPlayerStorageAgent().dellCell(
							GameConfig.GAME_HORSES_ID, soe.getFur() * num);

					if (null != cell) {
						Props props = (Props) cell.getItem();
						logger.info("useTime="
								+ TimeUtils.now().format(TimeUtils.FORMAT1)
								+ " propsId=" + props.getPrototype().getId()
								+ " propsName="
								+ props.getPrototype().getName()
								+ " useNumber=" + num + " uid=" + owner.getId()
								+ " uname=" + owner.getData().getName());

						GameLog.logSystemEvent(LogEvent.USE_PROPS,
								String.valueOf(props.getPrototype().getId()),
								props.getPrototype().getName(),
								String.valueOf(num),
								String.valueOf(owner.getId()));
					}

					lst.add(cell);
				}
				// rms
				RespModuleSet rms = new RespModuleSet(
						ProcotolType.BUILDING_RESP);
				rms.addModules(lst);
				AndroidMessageSender.sendMessage(rms, owner);
				// rms
				tip.setSuccTip("");
				QuestUtils.checkFinish(owner, QuestUtils.TYPE35, true);
				return tip;
			} else {
				// tip.setFailTip("fail");
				return tip;
			}
		}
	}

	public TipUtil equNum(SoldierEqu soe, int num) {
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		tip.setFailTip("");
		if (soe.getWood() > 0)// 木材
		{
			PropsPrototype wood = PropsManager.getInstance().getProps(
					GameConfig.GAME_TIMBER_ID);
			if (wood == null) {
//				logger.info("木材不存在!");
				tip.setFailTip("木材不存在");
			} else {
				if (owner.getPlayerStorageAgent().allCount(new Props(wood)) >= soe
						.getWood() * num) {
//					logger.info("用户拥有木材数量: >=" + soe.getWood() * num);
					tip.setSuccTip("");
				} else {
//					logger.info("用户拥有木材数量: <" + soe.getWood() * num);
					tip.setFailTip("木材数量不足");
					return tip;
				}
			}
		}
		if (soe.getIron() > 0)// 铁块
		{
			PropsPrototype iron = PropsManager.getInstance().getProps(
					GameConfig.GAME_IRONORE_ID);
			if (iron == null) {
//				logger.info("铁块不存在");
				tip.setFailTip("铁块不存在");
			} else {
				if (owner.getPlayerStorageAgent().allCount(new Props(iron)) >= soe
						.getIron() * num) {
//					logger.info("用户拥有铁块数量: >=" + soe.getIron() * num);
					tip.setSuccTip("");
				} else {
//					logger.info("用户拥有铁块数量: <" + soe.getIron() * num);
					tip.setFailTip("铁块数量不足");
					return tip;
				}
			}
		}
		if (soe.getFur() > 0)// 毛皮
		{
			PropsPrototype fur = PropsManager.getInstance().getProps(
					GameConfig.GAME_HORSES_ID);
			if (fur == null) {
//				logger.info("毛皮不存在");
				tip.setFailTip("毛皮不存在");
			} else {
				if (owner.getPlayerStorageAgent().allCount(new Props(fur)) >= soe
						.getFur() * num) {
//					logger.info("用户拥有毛皮数量: >=" + soe.getFur() * num);
					tip.setSuccTip("");
				} else {
//					logger.info("用户拥有毛皮数量: >=" + soe.getFur() * num);
					tip.setFailTip("毛皮数量不足");
					return tip;
				}
			}
		}
		return tip;
	}

	/**
	 * 获取本州特种兵类型
	 * 
	 * @return
	 */
	public int getSpecialSolder() {
		int country = owner.getData().getNativeId() / 1000 * 1000;
		switch (country) {
		case 1000:
			return 6;
		case 2000:
			return 5;
		case 3000:
			return 4;
		default:
			return 0;
		}
	}

	/**
	 * 士兵总数
	 * 
	 * @return
	 */
	public int allSoliderCount() {
		int num = 0;
		Map<Integer, PlayerSoldier> allMap = getSoldier();
		for (PlayerSoldier ps : allMap.values()) {
			num += ps.getSoldierCount();
		}
		return num;
	}

	public static void main(String[] args) {
		System.out.println("111,0".indexOf("-") == -1);
	}
}
