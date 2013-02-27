package com.joymeng.game.domain.rank;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;

/**
 * 排行管理器
 * @author admin
 * @date 2012-6-12
 * TODO
 */
public class RankManager {
	
	static final Logger logger = LoggerFactory.getLogger(RankManager.class);
	
	private static RankManager rankManager;
	
	public static RankManager getInstance(){
		if(null == rankManager){
			rankManager = new RankManager();
		}
		return rankManager;
	}
	
	/**
	 * 金币排行榜
	 */
	public List<RankAUnit> rankGameMoneyList = new ArrayList<RankAUnit>();
	
	/**
	 * 钻石排行榜
	 */
	public List<RankAUnit> rankJoyMoneyList = new ArrayList<RankAUnit>();
	
	/**
	 * 通天塔进度排行榜
	 */
	public List<RankAUnit> rankLadderMaxList = new ArrayList<RankAUnit>();
	
	/**
	 * 武将攻击力排行榜
	 */
	public List<RankHero> rankHeroAttackList = new ArrayList<RankHero>();
	
	/**
	 * 武将防御力排行榜
	 */
	public List<RankHero> rankHeroDefenceList = new ArrayList<RankHero>();
	
	/**
	 * 武将生命值排行榜
	 */
	public List<RankHero> rankHeroHPList = new ArrayList<RankHero>();
	
	/**
	 * gameMoney数据记录总页数，此数据对每个玩家都是一样的因此可以共享
	 */
	public int gameMoneyTotalPages = 0;
	
	/**
	 * joyMoney数据记录总页数
	 */
	public int joyMoneyTotalPages = 0;
	
	/**
	 * 通天塔记录总页数
	 */
	public int ladderMaxTotalPages = 0;
	
	/**
	 * 武将攻击力数据记录总页数
	 */
	public int heroAttackTotalPages = 0;
	
	/**
	 * 武将防御力数据记录总页数
	 */
	public int heroDefenceTotalPages = 0;
	
	/**
	 * 武将生命力数据记录总页数
	 */
	public int heroHPTotalPages = 0;
	
	
	//----------------just for test 
	public void load() throws Exception {
		rankHeroAttackList = DBManager.getInstance().getWorldDAO().getPlayerHeroDAO().getRankByHeroAttack(1000);
		int ranking = 1;
		for(RankHero rh : rankHeroAttackList){
			rh.setRanking(ranking);
			ranking ++;
			//logger.info(rh.getRanking() + " " + rh.getHeroName() + " " + rh.getLevel() + " " + rh.getValue() + " " + rh.getPlayerName());
		}
		
	}
	
	/**
	 * 读取数据库中金币前100名的玩家
	 */
	public void getNeedGameMoneyRank(){
		//logger.info(">>>>计划任务读取读取数据库中金币前100名的玩家");
		rankGameMoneyList.clear();
		List<RoleData> roleDataList = DBManager.getInstance().getWorldDAO().getRankRolesByGameMoney(100);
		int ranking = 1;
		for(RoleData roleData : roleDataList){
			RankAUnit r = new RankAUnit();
			r.setRanking(ranking);
			r.setName(roleData.getName());
			r.setValue(roleData.getGameMoney());
			r.setPlayerId(roleData.getUserid());
			rankGameMoneyList.add(r);
			ranking ++;
		}
	}
	
	/**
	 * 读取数据库中钻石前100名的玩家
	 */
	public void getNeedJoyMoneyRank(){
		//logger.info(">>>>计划任务读取读取数据库中钻石前100名的玩家");
		rankJoyMoneyList.clear();
		List<RoleData> roleDataList = DBManager.getInstance().getWorldDAO().getRankRolesByJoyMoney(100);
		int ranking = 1;
		for(RoleData roleData : roleDataList){
			RankAUnit r = new RankAUnit();
			r.setRanking(ranking);
			r.setName(roleData.getName());
			r.setValue(roleData.getJoyMoney());
			r.setPlayerId(roleData.getUserid());
			rankJoyMoneyList.add(r);
			ranking ++;
		}
	}
	
	/**
	 * 读取数据库中通天塔进度前100名的玩家
	 */
	public void getNeedLadderMaxRank(){
		//logger.info(">>>>计划任务读取读取数据库中通天塔前100名的玩家");
		rankLadderMaxList.clear();
		List<RoleData> roleDataList = DBManager.getInstance().getWorldDAO().getRankRolesByLadderMax(100);
		int ranking = 1;
		for(RoleData roleData : roleDataList){
			RankAUnit r = new RankAUnit();
			r.setRanking(ranking);
			r.setName(roleData.getName());
			r.setValue(roleData.getLadderMax());
			r.setPlayerId(roleData.getUserid());
			rankLadderMaxList.add(r);
			ranking ++;
		}
	}
	
	/**
	 * 读取数据库中武将攻击力前1000名的排行榜
	 */
	public void getNeedAttackRank(){
		//logger.info(">>>>计划任务读取读取数据库中武将攻击力前1000名的玩家");
		rankHeroAttackList.clear();
		rankHeroAttackList = DBManager.getInstance().getWorldDAO().getPlayerHeroDAO().getRankByHeroAttack(1000);
		int ranking = 1;
		for(RankHero rh : rankHeroAttackList){
			rh.setRanking(ranking);
			ranking ++;
			////logger.info(rh.getRanking() + " " + rh.getHeroName() + " " + rh.getLevel() + " " + rh.getValue() + " " + rh.getPlayerName());
		}
		
	}
	
	/**
	 * 读取数据库中武将防御力前1000名的排行榜
	 */
	public void getNeedDefenceRank(){
		//logger.info(">>>>计划任务读取读取数据库中武将防御力前1000名的玩家");
		rankHeroDefenceList.clear();
		rankHeroDefenceList = DBManager.getInstance().getWorldDAO().getPlayerHeroDAO().getRankByHeroDefence(1000);
		int ranking = 1;
		for(RankHero rh : rankHeroDefenceList){
			rh.setRanking(ranking);
			ranking ++;
		}
	}
	
	/**
	 * 读取数据库中武将生命值前1000名的排行榜
	 */
	public void getNeedHPRank(){
		//logger.info(">>>>计划任务读取读取数据库中武将生命值前1000名的玩家");
		rankHeroHPList.clear();
		rankHeroHPList = DBManager.getInstance().getWorldDAO().getPlayerHeroDAO().getRankByHeroHP(1000);
		int ranking = 1;
		for(RankHero rh : rankHeroHPList){
			rh.setRanking(ranking);
			ranking ++;
		}
	}
	
	/**
	 * 查询符合条件的金币排行榜,requestPage为0请求最后一页,requestPage为-1请求当前玩家所在页
	 * @param requestPage 请求页
	 * @param pageSize 每页显示记录条数
	 * @param player 玩家
	 */
	public void rankGameMoney(int requestPage,int pageSize,PlayerCharacter player){
		int totalSize = rankGameMoneyList.size();
		
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedGameMoneyRank();
		}
		totalSize = rankGameMoneyList.size();
		if(totalSize % pageSize == 0){
			gameMoneyTotalPages = totalSize/pageSize;
		}
		else{
			gameMoneyTotalPages = (totalSize/pageSize) + 1;
		}
		
		int fromIndex = 0;
		int endIndex = 0;
		int currentPage = 0;
		
		//请求最后一页
		if(requestPage == 0){
			if(totalSize % pageSize == 0){
				fromIndex = totalSize - pageSize;
				endIndex = totalSize;
				currentPage = totalSize / pageSize;
			}
			else{
				fromIndex = totalSize - (totalSize % pageSize);
				endIndex = totalSize;
				currentPage = (totalSize / pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
			
		}
		
		//请求玩家当前所在页
		else if(requestPage == -1){
			getSelfGameMoneyRank(pageSize,player);
			return;
		}
		
		else{
			
			fromIndex = (requestPage - 1) * pageSize;
			if((fromIndex + pageSize) > totalSize){
				endIndex = totalSize;
			}
			else{
				//if(totalSize > pageSize){//如果数据库中记录条数不止单页显示
					endIndex = fromIndex + pageSize;
				//}
				/*else{
					endIndex = fromIndex + totalSize;
				}
				*/
			}
			player.getData().setTempCurrentPage(requestPage);
		}
		
		List<RankAUnit> needRankGameMoneyList = new ArrayList<RankAUnit>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			//截取所需要的排行榜信息
			needRankGameMoneyList = rankGameMoneyList.subList(fromIndex, endIndex);
		}

		RespModuleSet rms = new RespModuleSet(ProcotolType.RANK_RESP);
		for(RankAUnit r : needRankGameMoneyList){
			rms.addModule(r);
		}
		AndroidMessageSender.sendMessage(rms,player);
	}
	
	/**
	 * 获得玩家自己的金币排行
	 */
	public void getSelfGameMoneyRank(int pageSize,PlayerCharacter player){
		int totalSize = rankGameMoneyList.size();
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedGameMoneyRank();
		}
		int rank = 0;
		for(RankAUnit r : rankGameMoneyList){
			if(r.getPlayerId() == player.getData().getUserid()){
				rank = r.getRanking();
				break;
			}
		}
		
		List<RankAUnit> needRankGameMoneyList = new ArrayList<RankAUnit>();
		//玩家当前所在页
		int currentPage = 0;
		
		//如果当前玩家排名在排行榜内
		if(rank != 0){
			if(rank % pageSize == 0){
				needRankGameMoneyList = rankGameMoneyList.subList(rank - pageSize, rank);
				currentPage = rank /pageSize;
			}
			else{
				
				if(totalSize > (rank/pageSize)*pageSize + pageSize){//当前玩家排名不是在最后一页
					needRankGameMoneyList = rankGameMoneyList.subList((rank/pageSize) * pageSize, (rank/pageSize)*pageSize + pageSize);
				}
				else{
					needRankGameMoneyList = rankGameMoneyList.subList((rank/pageSize) * pageSize,totalSize);
				}
				currentPage = (rank/pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
		}
		
		
		RespModuleSet rms=new RespModuleSet(ProcotolType.RANK_RESP);
		if(needRankGameMoneyList.size() != 0){
			for(RankAUnit r : needRankGameMoneyList){
				rms.addModule(r);
			}
			AndroidMessageSender.sendMessage(rms,player);
		}
		
		
		
	}
	
	/**
	 * 查询符合条件的钻石排行榜,requestPage为0请求最后一页,requestPage为-1请求当前玩家所在页
	 * @param requestPage 请求页
	 * @param pageSize 每页显示的记录条数
	 * @param player 玩家
	 */
	public void rankJoyMoney(int requestPage,int pageSize,PlayerCharacter player){
		int totalSize = rankJoyMoneyList.size();
		
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedJoyMoneyRank();
		}
		totalSize = rankJoyMoneyList.size();
		if(totalSize % pageSize == 0){
			joyMoneyTotalPages = totalSize/pageSize;
		}
		else{
			joyMoneyTotalPages = (totalSize/pageSize) + 1;
		}
		
		int fromIndex = 0;
		int endIndex = 0;
		int currentPage = 0;
		
		//请求最后一页
		if(requestPage == 0){
			if(totalSize % pageSize == 0){
				fromIndex = totalSize - pageSize;
				endIndex = totalSize;
				currentPage = totalSize / pageSize;
			}
			else{
				fromIndex = totalSize - (totalSize % pageSize);
				endIndex = totalSize;
				currentPage = (totalSize / pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
			
		}
		
		//请求玩家当前所在页
		else if(requestPage == -1){
			getSelfJoyMoneyRank(pageSize,player);
			return;
		}
		
		else{
			
			fromIndex = (requestPage - 1) * pageSize;
			if((fromIndex + pageSize) > totalSize){
				endIndex = totalSize;
			}
			else{
				endIndex = fromIndex + pageSize;
			}
			player.getData().setTempCurrentPage(requestPage);
		}
		
		List<RankAUnit> needRankJoyMoneyList = new ArrayList<RankAUnit>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			//截取所需要的排行榜信息
			needRankJoyMoneyList = rankJoyMoneyList.subList(fromIndex, endIndex);
		}

		RespModuleSet rms=new RespModuleSet(ProcotolType.RANK_RESP);
		for(RankAUnit r : needRankJoyMoneyList){
			rms.addModule(r);
		}
		AndroidMessageSender.sendMessage(rms,player);
	}
	
	/**
	 * 获取玩家自己的钻石排行
	 */
	public void getSelfJoyMoneyRank(int pageSize,PlayerCharacter player){
		int totalSize = rankJoyMoneyList.size();
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedJoyMoneyRank();
		}
		int rank = 0;
		for(RankAUnit r : rankJoyMoneyList){
			if(r.getPlayerId() == player.getData().getUserid()){
				rank = r.getRanking();
				break;
			}
		}
		
		List<RankAUnit> needRankJoyMoneyList = new ArrayList<RankAUnit>();
		//玩家当前所在页
		int currentPage = 0;
		
		//如果当前玩家排名在排行榜内
		if(rank != 0){
			if(rank % pageSize == 0){
				needRankJoyMoneyList = rankJoyMoneyList.subList(rank - pageSize, rank);
				currentPage = rank /pageSize;
			}
			else{
				if(totalSize > (rank/pageSize)*pageSize + pageSize){//当前玩家排名不是在最后一页
					needRankJoyMoneyList = rankJoyMoneyList.subList((rank/pageSize) * pageSize, (rank/pageSize)*pageSize + pageSize);
				}
				else{
					needRankJoyMoneyList = rankJoyMoneyList.subList((rank/pageSize) * pageSize,totalSize);
				}
				currentPage = (rank/pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
		}
		
		
		RespModuleSet rms=new RespModuleSet(ProcotolType.RANK_RESP);
		if(needRankJoyMoneyList.size() != 0){
			for(RankAUnit r : needRankJoyMoneyList){
				rms.addModule(r);
			}
			AndroidMessageSender.sendMessage(rms,player);
		}
		
	}
	
	/**
	 * 查询符合条件的钻石排行榜,requestPage为0请求最后一页,requestPage为-1请求当前玩家所在页
	 * @param requestPage
	 * @param pageSize
	 * @param player
	 */
	public void rankLadderMax(int requestPage,int pageSize,PlayerCharacter player){
		int totalSize = rankLadderMaxList.size();
		
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedLadderMaxRank();
		}
		totalSize = rankLadderMaxList.size();
		if(totalSize % pageSize == 0){
			ladderMaxTotalPages = totalSize/pageSize;
		}
		else{
			ladderMaxTotalPages = (totalSize/pageSize) + 1;
		}
		
		int fromIndex = 0;
		int endIndex = 0;
		int currentPage = 0;
		
		//请求最后一页
		if(requestPage == 0){
			if(totalSize % pageSize == 0){
				fromIndex = totalSize - pageSize;
				endIndex = totalSize;
				currentPage = totalSize / pageSize;
			}
			else{
				fromIndex = totalSize - (totalSize % pageSize);
				endIndex = totalSize;
				currentPage = (totalSize / pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
			
		}
		
		//请求玩家当前所在页
		else if(requestPage == -1){
			getSelfLadderMaxRank(pageSize,player);
			return;
		}
		
		else{
			
			fromIndex = (requestPage - 1) * pageSize;
			if((fromIndex + pageSize) > totalSize){
				endIndex = totalSize;
			}
			else{
				endIndex = fromIndex + pageSize;
			}
			player.getData().setTempCurrentPage(requestPage);
		}
		
		List<RankAUnit> needRankLadderMaxList = new ArrayList<RankAUnit>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			//截取所需要的排行榜信息
			needRankLadderMaxList = rankLadderMaxList.subList(fromIndex, endIndex);
		}

		RespModuleSet rms=new RespModuleSet(ProcotolType.RANK_RESP);
		for(RankAUnit r : needRankLadderMaxList){
			rms.addModule(r);
		}
		AndroidMessageSender.sendMessage(rms,player);
	}
	
	/**
	 * 获取玩家自己通天塔进度排行
	 * @param pageSize
	 * @param player
	 */
	public void getSelfLadderMaxRank(int pageSize,PlayerCharacter player){
		int totalSize = rankLadderMaxList.size();
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedLadderMaxRank();
		}
		int rank = 0;
		for(RankAUnit r : rankLadderMaxList){
			if(r.getPlayerId() == player.getData().getUserid()){
				rank = r.getRanking();
				break;
			}
		}
		
		List<RankAUnit> needRankLadderMaxList = new ArrayList<RankAUnit>();
		//玩家当前所在页
		int currentPage = 0;
		
		//如果当前玩家排名在排行榜内
		if(rank != 0){
			if(rank % pageSize == 0){
				needRankLadderMaxList = rankLadderMaxList.subList(rank - pageSize, rank);
				currentPage = rank /pageSize;
			}
			else{
				if(totalSize > (rank/pageSize)*pageSize + pageSize){//当前玩家排名不是在最后一页
					needRankLadderMaxList = rankLadderMaxList.subList((rank/pageSize) * pageSize, (rank/pageSize)*pageSize + pageSize);
				}
				else{
					needRankLadderMaxList = rankLadderMaxList.subList((rank/pageSize) * pageSize,totalSize);
				}
				currentPage = (rank/pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
		}
		
		
		RespModuleSet rms=new RespModuleSet(ProcotolType.RANK_RESP);
		if(needRankLadderMaxList.size() != 0){
			for(RankAUnit r : needRankLadderMaxList){
				rms.addModule(r);
			}
			AndroidMessageSender.sendMessage(rms,player);
		}
	}
	
	/**
	 * 查询符合条件的武将攻击力排行榜
	 * @param requestPage int
	 * @param pageSize int
	 * @param player PlayerCharacter
	 */
	public void rankHeroAttack(int requestPage,int pageSize,PlayerCharacter player){
		int totalSize = rankHeroAttackList.size();
		
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedAttackRank();
		}
		totalSize = rankHeroAttackList.size();
		if(totalSize % pageSize == 0){
			heroAttackTotalPages = totalSize/pageSize;
		}
		else{
			heroAttackTotalPages = (totalSize/pageSize) + 1;
		}
		
		int fromIndex = 0;
		int endIndex = 0;
		int currentPage = 0;
		
		//请求最后一页
		if(requestPage == 0){
			if(totalSize % pageSize == 0){
				fromIndex = totalSize - pageSize;
				endIndex = totalSize;
				currentPage = totalSize / pageSize;
			}
			else{
				fromIndex = totalSize - (totalSize % pageSize);
				endIndex = totalSize;
				currentPage = (totalSize / pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
			
		}
		
		//请求玩家当前所在页
		else if(requestPage == -1){
			getSelfHeroAttackRank(pageSize,player);
			return;
		}
		
		else{
			
			fromIndex = (requestPage - 1) * pageSize;
			if((fromIndex + pageSize) > totalSize){
				endIndex = totalSize;
			}
			else{
				endIndex = fromIndex + pageSize;
			}
			player.getData().setTempCurrentPage(requestPage);
		}
		
		List<RankHero> needRankHeroAttackList = new ArrayList<RankHero>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			//截取所需要的排行榜信息
			needRankHeroAttackList = rankHeroAttackList.subList(fromIndex, endIndex);
		}

		RespModuleSet rms=new RespModuleSet(ProcotolType.RANK_RESP);
		for(RankHero rh : needRankHeroAttackList){
			rms.addModule(rh);
		}
		AndroidMessageSender.sendMessage(rms,player);
	}
	
	/**
	 * 获取玩家自己的武将攻击力排行
	 * @param pageSize
	 * @param player
	 */
	public void getSelfHeroAttackRank(int pageSize,PlayerCharacter player){
		int totalSize = rankHeroAttackList.size();
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedAttackRank();
		}
		int rank = 0;
		
		//按玩家名称找到当前玩家
		for(RankHero rh : rankHeroAttackList){
			if(rh.getPlayerId() == player.getData().getUserid()){
			//if(rh.getPlayerName().equals(player.getData().getName())){
				rank = rh.getRanking();
				break;
			}
		}
		
		List<RankHero> needRankHeroAttackList = new ArrayList<RankHero>();
		//玩家当前所在页
		int currentPage = 0;
		
		//如果当前玩家排名在排行榜内
		if(rank != 0){
			if(rank % pageSize == 0){
				needRankHeroAttackList = rankHeroAttackList.subList(rank - pageSize, rank);
				currentPage = rank /pageSize;
			}
			else{
				if(totalSize > (rank/pageSize)*pageSize + pageSize){//当前玩家排名不是在最后一页
					needRankHeroAttackList = rankHeroAttackList.subList((rank/pageSize) * pageSize, (rank/pageSize)*pageSize + pageSize);
				}
				else{
					needRankHeroAttackList = rankHeroAttackList.subList((rank/pageSize) * pageSize,totalSize);
				}
				currentPage = (rank/pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
		}
		
		
		RespModuleSet rms=new RespModuleSet(ProcotolType.RANK_RESP);
		if(needRankHeroAttackList.size() != 0){
			for(RankHero rh : needRankHeroAttackList){
				rms.addModule(rh);
			}
			AndroidMessageSender.sendMessage(rms,player);
		}
		
	}
	
	/**
	 * 武将防御力排行榜
	 * @param requestPage
	 * @param pageSize
	 * @param player
	 */
	public void rankHeroDefence(int requestPage,int pageSize,PlayerCharacter player){
		
		int totalSize = rankHeroDefenceList.size();
		
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedDefenceRank();
		}
		totalSize = rankHeroDefenceList.size();
		if(totalSize % pageSize == 0){
			heroDefenceTotalPages = totalSize/pageSize;
		}
		else{
			heroDefenceTotalPages = (totalSize/pageSize) + 1;
		}
		
		int fromIndex = 0;
		int endIndex = 0;
		int currentPage = 0;
		
		//请求最后一页
		if(requestPage == 0){
			if(totalSize % pageSize == 0){
				fromIndex = totalSize - pageSize;
				endIndex = totalSize;
				currentPage = totalSize / pageSize;
			}
			else{
				fromIndex = totalSize - (totalSize % pageSize);
				endIndex = totalSize;
				currentPage = (totalSize / pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
			
		}
		
		//请求玩家当前所在页
		else if(requestPage == -1){
			getSelfHeroDefenceRank(pageSize,player);
			return;
		}
		
		else{
			
			fromIndex = (requestPage - 1) * pageSize;
			if((fromIndex + pageSize) > totalSize){
				endIndex = totalSize;
			}
			else{
				endIndex = fromIndex + pageSize;
			}
			player.getData().setTempCurrentPage(requestPage);
		}
		
		List<RankHero> needRankHeroDefenceList = new ArrayList<RankHero>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			//截取所需要的排行榜信息
			needRankHeroDefenceList = rankHeroDefenceList.subList(fromIndex, endIndex);
		}

		RespModuleSet rms = new RespModuleSet(ProcotolType.RANK_RESP);
		for(RankHero rh : needRankHeroDefenceList){
			rms.addModule(rh);
		}
		AndroidMessageSender.sendMessage(rms,player);
	}
	
	/**
	 * 获取玩家自己的防御力排行榜
	 */
	public void getSelfHeroDefenceRank(int pageSize,PlayerCharacter player){
		int totalSize = rankHeroDefenceList.size();
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedDefenceRank();
		}
		int rank = 0;
		
		//按玩家名称找到当前玩家
		for(RankHero rh : rankHeroDefenceList){
			if(rh.getPlayerId() == player.getData().getUserid()){
			//if(rh.getPlayerName().equals(player.getData().getName())){
				rank = rh.getRanking();
				break;
			}
		}
		
		List<RankHero> needRankHeroDefenceList = new ArrayList<RankHero>();
		//玩家当前所在页
		int currentPage = 0;
		
		//如果当前玩家排名在排行榜内
		if(rank != 0){
			if(rank % pageSize == 0){
				needRankHeroDefenceList = rankHeroDefenceList.subList(rank - pageSize, rank);
				currentPage = rank /pageSize;
			}
			else{
				if(totalSize > (rank/pageSize)*pageSize + pageSize){//当前玩家排名不是在最后一页
					needRankHeroDefenceList = rankHeroDefenceList.subList((rank/pageSize) * pageSize, (rank/pageSize)*pageSize + pageSize);
				}
				else{
					needRankHeroDefenceList = rankHeroDefenceList.subList((rank/pageSize) * pageSize,totalSize);
				}
				currentPage = (rank/pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
		}
		
		
		RespModuleSet rms = new RespModuleSet(ProcotolType.RANK_RESP);
		if(needRankHeroDefenceList.size() != 0){
			for(RankHero rh : needRankHeroDefenceList){
				rms.addModule(rh);
			}
			AndroidMessageSender.sendMessage(rms,player);
		}
		
	}
	
	/**
	 * 武将生命值排行榜
	 * @param requestPage
	 * @param pageSize
	 * @param player
	 */
	public void rankHeroHP(int requestPage,int pageSize,PlayerCharacter player){
		int totalSize = rankHeroHPList.size();
		
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedHPRank();
		}
		totalSize = rankHeroHPList.size();
		if(totalSize % pageSize == 0){
			heroHPTotalPages = totalSize/pageSize;
		}
		else{
			heroHPTotalPages = (totalSize/pageSize) + 1;
		}
		
		int fromIndex = 0;
		int endIndex = 0;
		int currentPage = 0;
		
		//请求最后一页
		if(requestPage == 0){
			if(totalSize % pageSize == 0){
				fromIndex = totalSize - pageSize;
				endIndex = totalSize;
				currentPage = totalSize / pageSize;
			}
			else{
				fromIndex = totalSize - (totalSize % pageSize);
				endIndex = totalSize;
				currentPage = (totalSize / pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
			
		}
		
		//请求玩家当前所在页
		else if(requestPage == -1){
			getSelfHeroHPRank(pageSize,player);
			return;
		}
		
		else{
			
			fromIndex = (requestPage - 1) * pageSize;
			if((fromIndex + pageSize) > totalSize){
				endIndex = totalSize;
			}
			else{
				endIndex = fromIndex + pageSize;
			}
			player.getData().setTempCurrentPage(requestPage);
		}
		
		List<RankHero> needRankHeroHPList = new ArrayList<RankHero>();
		
		//如果允许翻下一页,即未到达最后一页
		if(fromIndex < totalSize ){
			//截取所需要的排行榜信息
			needRankHeroHPList = rankHeroHPList.subList(fromIndex, endIndex);
		}

		RespModuleSet rms = new RespModuleSet(ProcotolType.RANK_RESP);
		for(RankHero rh : needRankHeroHPList){
			rms.addModule(rh);
		}
		AndroidMessageSender.sendMessage(rms,player);
	}
	
	/**
	 * 获取玩家自己的生命值排行
	 * @param pageSize
	 * @param player
	 */
	public void getSelfHeroHPRank(int pageSize,PlayerCharacter player){
		int totalSize = rankHeroHPList.size();
		//计划任务未执行，第一次加载需要数据
		if(totalSize == 0){
			getNeedHPRank();
		}
		int rank = 0;
		
		//按玩家名称找到当前玩家
		for(RankHero rh : rankHeroHPList){
			if(rh.getPlayerId() == player.getData().getUserid()){
			//if(rh.getPlayerName().equals(player.getData().getName())){
				rank = rh.getRanking();
				break;
			}
		}
		
		List<RankHero> needRankHeroHPList = new ArrayList<RankHero>();
		//玩家当前所在页
		int currentPage = 0;
		
		//如果当前玩家排名在排行榜内
		if(rank != 0){
			if(rank % pageSize == 0){
				needRankHeroHPList = rankHeroHPList.subList(rank - pageSize, rank);
				currentPage = rank /pageSize;
			}
			else{
				if(totalSize > (rank/pageSize)*pageSize + pageSize){//当前玩家排名不是在最后一页
					needRankHeroHPList = rankHeroHPList.subList((rank/pageSize) * pageSize, (rank/pageSize)*pageSize + pageSize);
				}
				else{
					needRankHeroHPList = rankHeroHPList.subList((rank/pageSize) * pageSize,totalSize);
				}
				currentPage = (rank/pageSize) + 1;
			}
			player.getData().setTempCurrentPage(currentPage);
		}
		
		
		RespModuleSet rms = new RespModuleSet(ProcotolType.RANK_RESP);
		if(needRankHeroHPList.size() != 0){
			for(RankHero rh : needRankHeroHPList){
				rms.addModule(rh);
			}
			AndroidMessageSender.sendMessage(rms,player);
		}
		
	}
	
	
	
}
