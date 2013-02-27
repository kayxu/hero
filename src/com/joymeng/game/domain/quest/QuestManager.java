package com.joymeng.game.domain.quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.world.GameDataManager;
/**
 * 任务表
 * @author admin
 *
 */
public class QuestManager {
	private static QuestManager instance;
	private HashMap<Integer, Quest> questMap = new HashMap<Integer, Quest>();
	public static QuestManager getInstance() {
		if (instance == null) {
			instance = new QuestManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, Quest.class);
		for (Object obj : list) {
			Quest data = (Quest) obj;
			questMap.put(data.getId(), data);
//			System.out.println("quest id="+data.getId());
		}
	}
	public Quest getQuest(int id){
		return questMap.get(id);
	}
	/**
	 * 获得该任务的后续任务
	 * @param quest
	 * @return
	 */
	public List<Quest> getNextQuest(Quest quest){
		List<Quest> list=new ArrayList<Quest>();
		Quest q=getQuest(quest.getNextId1());
		if(q!=null){
			list.add(q);
		}
		q=this.getQuest(quest.getNextId2());
		if(q!=null){
			list.add(q);
		}
		q=this.getQuest(quest.getNextId3());
		if(q!=null){
			list.add(q);
		}
		return list;
	}
	/**
	 * 查找可以接受的任务
	 * @param finished
	 * @return
	 */
	public List<Quest> search(List<Quest> finished,Quest quest){
		//遍历整个任务表，不包含每日任务
	
//		List<Quest> list=getQuestListButThisType(PlayerQuestAgent.TYPE_DAILY);
		List<Quest> list=getNextQuest(quest);
		List<Quest> newList=new ArrayList<Quest>();
		for(Quest q:list){
			//已经完成的次数
			int finishedNum=GameUtils.getCompletedNum(q.getId(), finished);
			//最大可以完成的次数
			int maxNum=q.getMaxCount();
			if(finishedNum<maxNum){
				newList.add(q);
			}
		}
		return newList;
	}
	/**
	 * 查找某一个类型的任务
	 * @param type
	 * @return
	 */
	public List<Quest> getQuestList(byte type){
		List<Quest> list=new ArrayList<Quest>();
		Iterator<Integer> it=questMap.keySet().iterator();
		while(it.hasNext()){
			Quest q=questMap.get(it.next());
			if(q.getType()==type){
				list.add(q);
			}
		}
		return list;
	}
	/**
	 * 获得不符合该类型的任务
	 * @param type
	 * @return
	 */
	public List<Quest> getQuestListButThisType(byte type){
		List<Quest> list=new ArrayList<Quest>();
		Iterator<Integer> it=questMap.keySet().iterator();
		while(it.hasNext()){
			Quest q=questMap.get(it.next());
			if(q.getType()!=type){
				list.add(q);
			}
		
		}
		return list;
	}
	public List<T> init(String initStr){
		List<T> list = new ArrayList<T>();	
		if (initStr.length() > 0) {
			String str[] = initStr.split(";");
			for (int i = 0; i < str.length; i++) {
				T t=new T();
				list.add(t);
			}
		}
		return list;
	}
	public List<AcceptedQuest> initAcceptedQuest(String initStr){
		List<AcceptedQuest> list = new ArrayList< AcceptedQuest>();		
		if (initStr.length() > 0) {
			String str[] = initStr.split(";");
			for (int i = 0; i < str.length; i++) {
				String[] s = str[i].split(",");
				Quest q=this.getQuest(Integer.parseInt(s[0]));
				if(q==null){
					continue;
				}
				AcceptedQuest aq = new AcceptedQuest(q,
						Byte.parseByte(s[1]));
				list.add(aq);
			}
		}
		return list;
	}
	public List<Quest>  initQuestList(String initStr){
		List<Quest> list=new ArrayList<Quest>();
		if (initStr.length() > 0) {
			String str[] = initStr.split(";");
			for (int i = 0; i < str.length; i++) {
				Quest q = QuestManager.getInstance().getQuest(
						Integer.parseInt(str[i]));
				if (q == null) {
					continue;
				}
				list.add(q);
			}
		}
		return list;
	}
	public String[] saveAcceptedQuest(List<AcceptedQuest> list){
		String str[] = new String[list.size()];
		int i = 0;
		for(AcceptedQuest aq:list){
			str[i] = aq.getQ().getId()+ "," + aq.getStatus();
			i++;
		}
		return str;
	}
	public String[] saveList(List<Quest> list){
		String[]	str = new String[list.size()];
		for (int j = 0; j < list.size(); j++) {
			str[j] = String.valueOf(list.get(j).getId());
		}
		return str;
	}
}
