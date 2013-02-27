/**
 * Copyright com.joymeng.game.domain.Props-PropsManager.java
 * @author xufangliang
 * @time 2012-5-3
 */
package com.joymeng.game.domain.item.props;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.common.Instances;
import com.joymeng.game.domain.world.GameDataManager;


/**
 * @author xufangliang
 *  1.1
 */
public class PropsManager implements Instances{
	
	private Logger log = LoggerFactory.getLogger(PropsManager.class);
	
	private static PropsManager instance;
	public PropsManager(){}
	
	public static PropsManager getInstance(){
		if(instance == null)
			instance = new PropsManager();
		return instance;
	}
	//>>>>>>>>>>>>>>>>>>>道具固化数据<<<<<<<<<<<<<<<<
	public Map<Integer,PropsPrototype> propsDatas = new HashMap<Integer,PropsPrototype>();
//	public Map<Byte,PropsDelay> delayDatas = new HashMap<Byte,PropsDelay>();
	
	public void loadProps(String path) throws ClassNotFoundException{
//		log.info(">>>>>>>>>>>>>道具固化数据...开始加载>>>>>>>>>>>>>");
		List<Object> list = GameDataManager.loadData(path,PropsPrototype.class);
		for(Object o : list){
			PropsPrototype p = (PropsPrototype) o ;
			propsDatas.put(p.getId(), p);
		}
//		log.info(">>>>>>>>>>>>>道具固化数据...加载成功>>>>>>>>>>>>>");
	}

	
//	/**
//	 * 查询
//	 * @param path
//	 * @throws ClassNotFoundException
//	 */
//	public void loadDelay(String path) throws ClassNotFoundException{
//		log.info(">>>>>>>>>>>>>延时道具数据...开始加载>>>>>>>>>>>>>");
//		List<Map<String, Object>> lst = gameDao.getSimpleJdbcTemplate().queryForList(GameWorldDAO.SQL_SELECT_NATION);
//		for(Object o : lst){
//			PropsDelay p = (PropsDelay) o ;
//			delayDatas.put(p.getPropsType(), p);
//		}
//		log.info(">>>>>>>>>>>>>延时道具数据...加载成功>>>>>>>>>>>>>");
//	}
	
	public boolean checkDatas()
	{
		if(propsDatas == null || propsDatas.size() == 0)
			return false;
		return true;
	}
	/**
	 * 获取物品原形
	 * @param id
	 * @return
	 */
	public PropsPrototype getProps(int id){
		return propsDatas.get(id);
	}
	
	/**
	 * 通过ID获取 物品名
	 * @param id
	 * @return
	 */
	public String getNameById(int id){
		PropsPrototype props = getProps(id);
		if(props == null)
			return "物品不存在";
		else 
			return props.getName(); 
	}
	
	public List<PropsPrototype> getAllPrototype()
	{
		return new ArrayList<PropsPrototype>(propsDatas.values());
	}
	
	public PropsPrototype getProps(byte type, int level){
		for (PropsPrototype prototype : propsDatas.values()) {
			if (prototype.getPropsType() == type && prototype.getNeedLevelMin() == level) {
				return prototype;
			}
		}
		return null;
	}
	/**
	 * 根据物品TYPE类型获得道具原型
	 * @param type
	 * @return
	 */
	public PropsPrototype getPropsByType(byte type){
		for (PropsPrototype prototype : propsDatas.values()) {
			if (prototype.getPropsType() == type ) {
				return prototype;
			}
		}
		return null;
	}
}
