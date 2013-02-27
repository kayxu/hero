package com.joymeng.game.domain.building;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.domain.world.GameDataManager;

public class RefreshsBuildManager {
	private static final Logger logger = LoggerFactory
			.getLogger(RefreshsBuildManager.class);
	public static RefreshsBuildManager instance;
	public static RefreshsBuildManager getInstance(){
		if(instance ==null ){
			instance = new RefreshsBuildManager();
		}
		return instance;
	}
	//武将刷新
	HashMap<Integer, HeroRefresh> herorefMap = new HashMap<Integer, HeroRefresh>();
	//铁匠铺刷新
	HashMap<Integer, BlackSmithyRefresh> BlackSmithyMap = new HashMap<Integer, BlackSmithyRefresh>();
	
	public void load(String path) throws Exception {
		loadHero(path);
		loadBlackSmithy(path);
	}
	public void loadHero(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, HeroRefresh.class);
		for (Object obj : list) {
			HeroRefresh data = (HeroRefresh) obj;
			herorefMap.put(data.getRefreshTimes(), data);
		}
//		logger.info(">>>herorefMap:"+herorefMap.size());
	}
	
	public void loadBlackSmithy(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, BlackSmithyRefresh.class);
		for (Object obj : list) {
			BlackSmithyRefresh data = (BlackSmithyRefresh) obj;
			BlackSmithyMap.put(data.getRefreshTimes(), data);
		}
//		logger.info(">>>BlackSmithyMap:"+BlackSmithyMap.size());
	}
	/**
	 * 获取特定数据
	 * @param times
	 * @return
	 */
	public HeroRefresh getHeroRefresh(int times,byte type){
		if(herorefMap != null && herorefMap.size() >0){
			for(HeroRefresh re : herorefMap.values()){
				if(re.getType() == type && times == re.getRefreshTimes()){
					return re;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取特定数据
	 * @param times
	 * @return
	 */
	public BlackSmithyRefresh getBlackSmithyRefresh(int times,byte type){
		if(BlackSmithyMap != null && BlackSmithyMap.size() >0){
			for(BlackSmithyRefresh re : BlackSmithyMap.values()){
				if(re.getType() == type && times == re.getRefreshTimes()){
					return re;
				}
			}
		}
		return null;
	}
}
