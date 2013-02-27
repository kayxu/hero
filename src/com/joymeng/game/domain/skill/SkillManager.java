package com.joymeng.game.domain.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.MathUtils;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.world.GameDataManager;
/**
 * 固化技能管理器
 * @author admin
 * @date 2012-5-18
 * TODO
 */
public class SkillManager {
	static final Logger logger = LoggerFactory.getLogger(SkillManager.class);
	//key=id ,value=skill
	private HashMap<Integer, Skill> SkillMap = new HashMap<Integer, Skill>();
	//key=level,value=list<skill>
	private HashMap<Integer,List<Skill>> skillMapByLevel=new HashMap<Integer,List<Skill>>();
	private static SkillManager instance;
	public static SkillManager getInstance() {
		if (instance == null) {
			instance = new SkillManager();
		}
		return instance;
	}
	/**
	 * 载入固化数据
	 * @param path
	 * @throws Exception
	 */
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, Skill.class);
		System.out.println("load skill,size="+list.size());
		for (Object obj : list) {
			Skill data = (Skill) obj;
			SkillMap.put(data.getId(), data);
//			if(data.getId()==5935){
//				System.out.println("====");
//			}
			if(skillMapByLevel.get(data.getLevel())==null){
				List<Skill> slist=new ArrayList<Skill>();
				slist.add(data);
				skillMapByLevel.put(data.getLevel(), slist);
			}else{
				skillMapByLevel.get(data.getLevel()).add(data);
			}
//			System.out.println("("+data.getId()+","+data.getName()+")");
		}
	}
	/**
	 * 获得技能固化数据
	 * @param id
	 * @return
	 */
	public Skill getSKill(int id){
		return SkillMap.get(id);
	}
	/**
	 * 获得非加制技能
	 * @return
	 */
	public List<Skill> getNormalSkillList(){
		List<Skill> list=new ArrayList<Skill>();
		Iterator it=SkillMap.keySet().iterator();
		while(it.hasNext()){
			Skill sk=SkillMap.get(it.next());
			if(sk.getSkillType()>=49&&sk.getSkillType()<=68){
					continue;
			}
			list.add(sk);
		}
		return list;
	}
	/**
	 * 随机获得某一个级别的技能
	 * @param level
	 * @return
	 */
	public int randomSkillByLevel(int level){
		int id=0;
		List<Skill> list=skillMapByLevel.get(level);
		if(list==null){
			logger.info("没有级别的技能，level="+level);
			return 0;
		}
		for (int i = 0; i < list.size(); i++) {//排除加制技能
			Skill sk = list.get(i);
			if(sk.getSkillType()>=49&&sk.getSkillType()<=68){
				list.remove(sk);
				i--;
			}
		}
		if(list!=null){
			int random=MathUtils.random(list.size());
			return list.get(random).getId();
		}
		return id;
	}
//	水 0；风1 ；火2 ；土3 ；雷4
//	相生关系{1,2,3,4,0}
//	相克关系{2,4,1,3,0}
	public static final int[] FIVE_ELEMENT1=new int[]{1,2,3,4,0,0};
	public static final int[] FIVE_ELEMENT2=new int[]{2,3,4,0,1,0};
	/**
	 * 技能合成
	 * @param id1 技能1id
	 * @param id2 技能2id
	 * @return 新技能id
	 */
	public int compose(int id1,int id2){
		
//		1、技能属性相生相克关系	
//		B=3，五行相生
//		B=2，五行无关
//		B=1，五行相克
		Skill sk1=SkillMap.get(id1);
		Skill sk2=SkillMap.get(id2);
		if(sk1==null||sk2==null){
			return 0;
		}
		//技能的☆属性
		int b1=sk1.getAttri();
		int b2=sk2.getAttri();
		logger.info("compose b1=="+b1+" b2=="+b2+" skillId1="+id1+" skillId2="+id2);
		int b=2;
		if(b1==5||b2==5){
			b=2;
		}else{
			if(FIVE_ELEMENT1[b1]==b2){
				b=3;
			}else if(FIVE_ELEMENT2[b1]==b2){
				b=1;
			}
		}
		logger.info("skill compose b="+b);
//		A=参与合成的两本技能的平均等级【向上取整】			
//		C=（A-参与合成的两本技能的平均等级）【向上取整】				
//		S=合成后生成的新技能	
		//判断等级，判断类型
		int level1=sk1.getLevel();//
		int level2=sk2.getLevel();
		double A=((double)(level1+level2))/2;
		int a=(int) Math.ceil(A);
		logger.info("compose a="+a);
		double C=a-A;
		int c=(int) Math.ceil(C);
		logger.info("compose c="+c);
//		等级	几率（%）		
//		S=A+2	X=2^B*0.6^C		
//		S=A+1	Y=（20+20*B）*0.6^C		
//		S=A	Z=100-X-Y	
		double rate1=Math.pow(2,b)*Math.pow(0.6,c);
		double rate2=(20+28*b)*Math.pow(0.6,c);
		double rate3=100-rate1-rate2;
		int rate[]={(int)rate1,(int)rate2,(int)rate3};
		int level[]={a+2,a+1,a};
		//得到最终生成的级别
		int lv=MathUtils.getRandomId2(level,rate , 100);
		if(lv>GameConst.SKILL_MAXLEVEL){
			lv=GameConst.SKILL_MAXLEVEL;
		}
		
		List<Skill> list=new ArrayList<Skill>();
		int skillType1=sk1.getSkillType();
		int skillType2=sk2.getSkillType();
		int type=skillType1;//默认是技能1的
		if(skillType1!=skillType2){
			if(Math.abs(level1-level2)>=4){
				 type=level1>level2?skillType2:skillType1;
				 list=search(type,lv);
				 logger.info("logic 1");
			}else{
//				if(b1==5||b2==5){//暗技能
//					//从所有按技能中选择一个暗技能
//					list=search2((byte)5,lv);
//					 logger.info("logic 2");
//				}else{
					list=search(-1,lv);
					logger.info("logic 3");
//				}
			}
		}else{
			//从技能书中找到所有level=lv的 type=type的
			logger.info("logic 4");
			list=search(type,lv);
		}
		logger.info("技能合成,id1="+id1+" id2="+id2+" level1="+level1+" level2="+level2+ " type1="+skillType1+" type2="+skillType2);

		if(list.size()==0){
			logger.info("技能合成,未成功,id1="+id1+" id2="+id2+" type="+type+" level="+lv);
			return 0;
		}
		//从所有技能中找到type和level相等的技能，随机一个
		int random=MathUtils.random(list.size());
		int newId=list.get(random).getId();
		logger.info("技能合成,成功,newId="+newId+" type="+type+" level="+lv);
		return newId;
	}
	/**
	 * 
	 * @param skillType
	 * @param level
	 * @return
	 */
	public List<Skill> search(int type,int level){
		List<Skill> list=getNormalSkillList();
		for (int i = 0; i < list.size(); i++) {//排除加制技能
			Skill sk = list.get(i);
			if(type==-1){//只判断级别
				if(sk.getLevel()==level){
//					list.add(sk);
				}else{
					list.remove(i);
					i--;
				}
			}else{//判断级别和类型
				if(sk.getSkillType()==type&&sk.getLevel()==level){
//					list.add(sk);
				}else{
					list.remove(i);
					i--;
				}
			}
			
		}
		return list;
	}
	public List<Skill> search2(byte attri,int level){
		List<Skill> list=getNormalSkillList();
		for (int i = 0; i < list.size(); i++) {//排除加制技能
			Skill sk = list.get(i);
			if(sk.getAttri()==attri&&sk.getLevel()==level){//只判断级别
				logger.info("skill search2,id="+sk.getId()+" attri="+sk.getAttri());
//					list.add(sk);
			}else{
				list.remove(i);
				i--;
			}
		}
		return list;
	}
}
