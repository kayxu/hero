package com.joymeng.game.domain.role;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.XmlUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.world.TipUtil;
import com.sun.imageio.plugins.common.I18N;
import com.sun.jmx.snmp.Timestamp;

public class UsernameManager {

	static final Logger logger = LoggerFactory.getLogger(UsernameManager.class);
	
	/**
	 * 内存中加载玩家名称的数量限定
	 */
	public static final int LOAD_SIZE = 10000;
	
	/**
	 * 当内存中数据记录条数为100时触发重新加载
	 */
	public static final int RELOAD_SIZE_TRIGGER = 2000;
	
	private HashMap<Integer,Username> usernameMap = new HashMap<Integer,Username>();
	
	private List<UsernameStatus> avaliableMaleUsernameStatusList;
	
	private List<UsernameStatus> avaliableFemaleUsernameStatusList;
	
	/**
	 * 屏蔽的字符串
	 */
	private List<String> disallowStrList = new ArrayList<String>();
	
	private static UsernameManager usernameManager;
	
	public static UsernameManager getInstance(){
		if(null == usernameManager){
			usernameManager = new UsernameManager();
		}
		return usernameManager;
	}
	
	/**
	 * 加载玩家姓名生成表
	 * @param path String
	 * @throws Exception
	 */
	public void load(String path) throws Exception {
		int nameNum = 0;
		int nickNameNum = 0;
		logger.info("--------------加载玩家姓名生成表---------------");
		List<Username> list = loadUsernameXmlFile(path, Username.class);
		for(Username username : list){
			if(null != username.getNickName() && !"".equals(username.getNickName())){//统计nickName 数量
				nickNameNum ++;
			}
			usernameMap.put(username.getId(), username);
		}
		nameNum = list.size();
		logger.info("name数量:" + nameNum);
		logger.info("nickName数量:" + nickNameNum);
		logger.info("------------加载玩家姓名生成表完毕-------------");
		
		//insertUsernameStatusToDB(nickNameNum);//此方法在更新数据库中玩家姓名时使用，在数据库中已有玩家姓名信息，且数量充足时不要调用
		
		loadAvaliableUsernameStatus();
		
		readFile(path);
		
	}
	
	public void consleInitUsername() throws Exception{
		if(I18nGreeting.LANLANGUAGE_TIPS ==1){
			load(GameConst.RES_PATH_EN);
		}else{
			load(GameConst.RES_PATH_CH);
		}
		
	}
	
	private List<Username> loadUsernameXmlFile(String path,Class<?> T){
		String fileName = T.getSimpleName();
		File file = new File(path + "/" + fileName + ".xml");
		Document d;
		List<Username> list = new ArrayList<Username>();
		try {
			d = XmlUtils.load(file);
			Element[] elements = XmlUtils.getChildrenByName(
					d.getDocumentElement(), fileName);
			for (Element element : elements) {
				Object data = T.newInstance();// 创建对象
				Field[] fs = T.getDeclaredFields();
				for (int i = 0; i < fs.length; i++) {
					Field f = fs[i];
					f.setAccessible(true); // 设置些属性是可以访问的
					String str = XmlUtils.getAttribute(element, f.getName());
					try {
						if (f.getType() == int.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Integer.parseInt(str));
							}

						} else if (f.getType() == long.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Long.parseLong(str));
							}

						} else if (f.getType() == boolean.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Boolean.parseBoolean(str));
							}

						} else if (f.getType() == byte.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Byte.parseByte(str));
							}
						} else if (f.getType() == String.class) {
							f.set(data, str);
						} else if (f.getType() == Short.class
								|| f.getType() == short.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Short.parseShort(str));
							}

						} else if (f.getType() == float.class
								|| f.getType() == Float.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Float.parseFloat(str));
							}

						} else if (f.getType() == Timestamp.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
							}
						} else {
							// System.out.println("error type=" +
							// f.getType()+" name="+f.getName());
						}
					} catch (Exception ex) {
						logger.info("load data error,str=" + f.getName()
								+ " fileName=" + fileName);
						ex.printStackTrace();
					}

				}
				Username username = (Username) data;
				for(Username u : list){
					if(username.equals(u)){
						logger.info("!重复名Id：" + username.getId());
						logger.info("与" + u.getId() + "有重复name或nickName");
					}
				}
				list.add(username);
				
			}

		} catch (Exception e) {

			GameLog.error("error in load " + fileName, e);
			e.printStackTrace();
			System.exit(0);
		}
		return list;
	}
	
	
	
	/**
	 * 
	 * @param path
	 */
	public void readFile(String path){
		disallowStrList.clear();
		logger.info("加载屏蔽字符");
		try{  
            FileReader reader = new FileReader(path + "/disallow.txt");  
            BufferedReader br = new BufferedReader(reader);  
            String s = null;  
            while((s = br.readLine()) != null) {  
            	disallowStrList.add(s);
            }  
            br.close();  
            reader.close();  
       }catch(Exception e){  
            e.printStackTrace();  
       }  
	}
	
	/**
	 * 此方法在更新数据库中玩家姓名时使用，在数据库中已有玩家姓名信息，且数量充足时不要调用
	 * 调用此方法前请先清空数据库，否则方法执行无效果
	 */
	public void insertUsernameStatusToDB(int nickNameNum){
		List<UsernameStatus> usernameStatusList = new ArrayList<UsernameStatus>();
		Set<UsernameStatus> usernameStatusSet = new HashSet<UsernameStatus>();
		for(int i=1;i<=usernameMap.size();i++){
			Username u1 = usernameMap.get(i);
			for(int j=1;j<=nickNameNum;j++){//此处50为nickName的数量
				Username u2 = usernameMap.get(j);
				String fullName = u1.getName().trim() + "-" + u2.getNickName().trim();
				UsernameStatus usernameStatus = new UsernameStatus();
				if(u1.getType() == 1){//性别为女
					usernameStatus.setFullName(fullName);
					usernameStatus.setSex(1);
					usernameStatus.setStatus(1);
				}
				else{
					usernameStatus.setFullName(fullName);
					usernameStatus.setSex(0);
					usernameStatus.setStatus(1);
				}
				usernameStatusSet.add(usernameStatus);	//去掉重名
			}
		}
		
		logger.info("姓名生成记录总条数：" + usernameStatusSet.size());
		
		//清空玩家可用名表
		DBManager.getInstance().getWorldDAO().getUsernameStatusDAO().clearUsernameStatus();
		
		//如果第一次加载玩家姓名生成表，表中没有数据，则初始化数据
		usernameStatusList.addAll(usernameStatusSet);
		usernameStatusSet = null;
		
		//初始化玩家姓名表
		int i=0;
		logger.info("正在初始化玩家可用名表，请耐心等待...");
			Collections.shuffle(usernameStatusList);
			for(UsernameStatus u : usernameStatusList){
				u.setId(i);
				DBManager.getInstance().getWorldDAO().getUsernameStatusDAO().addUsernameStatus(u);
				i++;
			}
		logger.info("初始化玩家可用名表，完成");
	}
	
	public void loadAvaliableUsernameStatus(){
		//加载可用的男性玩家名称 LOAD_SIZE 个
		avaliableMaleUsernameStatusList = Collections.synchronizedList(DBManager.getInstance()
			.getWorldDAO().getUsernameStatusDAO().getCertainNumAvaliableMaleUsernameStatus(LOAD_SIZE));
		
		logger.info("可用男性玩家名称数量：" + avaliableMaleUsernameStatusList.size());
		
		//加载可用的女性玩家名称 LOAD_SIZE 个
		avaliableFemaleUsernameStatusList = Collections.synchronizedList(DBManager.getInstance()
			.getWorldDAO().getUsernameStatusDAO().getCertainNumAvaliableFemaleUsernameStatus(LOAD_SIZE));
		
		logger.info("可用女性玩家名称数量：" + avaliableFemaleUsernameStatusList.size());

	}
	
	/**
	 * 加载特定数量的可用男性玩家名称
	 * @param num
	 */
	public List<UsernameStatus> loadCertainNumAvaliableMaleUsernameStatus(int num){
		List<UsernameStatus> list = DBManager.getInstance()
				.getWorldDAO().getUsernameStatusDAO().getCertainNumAvaliableMaleUsernameStatus(num);
		return list;
	}
	
	/**
	 * 加载特定数量的可用女性玩家名称
	 * @param num
	 * @return
	 */
	public List<UsernameStatus> loadCertainNumAvaliableFemaleUsernameStatus(int num){
		List<UsernameStatus> list = DBManager.getInstance()
				.getWorldDAO().getUsernameStatusDAO().getCertainNumAvaliableFemaleUsernameStatus(num);
		return list;
	}
	
	/**
	 * 获取一个随机不重复的玩家名
	 * @return String 用户名
	 */
	public String getRandomAndUniqueName(byte userType,PlayerCharacter player){
		
		forLog();
		
		//获取玩家上一次随机的名称，如果是第一次随机名称则取得的值为空字符串
		//String lastTimeFullName = player.getData().getLastTimeTempUseFullName();
		String fullName = "";
		if(userType == 1 || userType == 2 || userType == 3){//男性
			//获取男玩家姓名
			fullName = randomCanUseMaleName(player);
		}
		
		if(userType == 4 || userType == 5 || userType == 6){//女性
			
			//获取女玩家姓名
			fullName = randomCanUseFemaleName(player);
		}
		
		//记录玩家最近生成的姓名，防止再次产生
		if(player.getData().getGenerateNames().size() >= 10){//确保只记录10条数据
			player.getData().getGenerateNames().remove(0);
		}
		player.getData().getGenerateNames().add(fullName);
		
		return fullName;
		
	}
	
	/**
	 * 获取不重复的男玩家名
	 * @deprecated
	 * @return String 
	 */
	private String getMaleName(PlayerCharacter player){
		UsernameStatus usernameStatus;
		synchronized(this){
			releaseName(player);
			
			//查找当前姓名组合在数据库中是否存在
			int index = MathUtils.random(avaliableMaleUsernameStatusList.size());
			usernameStatus = avaliableMaleUsernameStatusList.get(index);
			logger.info("------------------------------数据库中可用和正准备使用的男名称数量:" + avaliableMaleUsernameStatusList.size());
			
			if(avaliableMaleUsernameStatusList.size() <= RELOAD_SIZE_TRIGGER){//当数据库中可用和正准备使用的名称数量小于RELOAD_SIZE_TRIGGER时，追加可用名称
				List<UsernameStatus> list = loadCertainNumAvaliableMaleUsernameStatus(LOAD_SIZE - RELOAD_SIZE_TRIGGER);
				avaliableMaleUsernameStatusList.addAll(list);
			}
			
			//如果玩家名称已被另一个用户正在准备使用      或者出现最近10次以内玩家已看过的角色名
			while(usernameStatus.getStatus() == 0 || alreadySee(usernameStatus.getFullName(),player)){
				
				logger.info("------------------------------数据库中可用和正准备使用的男名称数量:" + avaliableMaleUsernameStatusList.size());
				
				//！！！！！！！！！！！！！！！！！！！！！！！此处可设置提前提醒
				if(avaliableMaleUsernameStatusList.size() <= 10){
					logger.info("！！！！！！！！！！！！！！！！！！！数据库中可用角色名，满足条件的已用完数据库中可用角色名，满足条件的已用完，需要向数据库中添加可用记录！！！！！！！！！！！！");
					break;
				}
				index = MathUtils.random(avaliableMaleUsernameStatusList.size());
				usernameStatus = avaliableMaleUsernameStatusList.get(index);
			}
			//设置次玩家名称被占用
			usernameStatus.setStatus(0);
			
			//记录当前玩家获取的名称，以便玩家对此名称不满意时，进行释放
			player.getData().setLastTimeTempUseFullName(usernameStatus.getFullName());
			logger.info("==================设置玩家名称被占用" + usernameStatus.getFullName());
		}
		
		
		//如果之前玩家已获取过随机名称，释放前一次获取的名称
		/*String lastTimeFullName = player.getData().getLastTimeTempUseFullName();
		if(!"".equals(lastTimeFullName)){
			for(int i=0;i<avaliableMaleUsernameStatusList.size();i++){
				//在List中找到上次随机的名称
				if(lastTimeFullName.equals(avaliableMaleUsernameStatusList.get(i).getFullName())){
					avaliableMaleUsernameStatusList.get(i).setStatus(1);
					break;
				}	
			}
		}*/
		return usernameStatus.getFullName();
		
	}
	
	/**
	 *获取 随机可用的男玩家名
	 */
	public String randomCanUseMaleName(PlayerCharacter player){
		UsernameStatus usernameStatus;
		synchronized(this){
			releaseName(player);
			logger.info("------------------------------数据库中可用和正准备使用的男名称数量:" + avaliableMaleUsernameStatusList.size());
			
			if(avaliableMaleUsernameStatusList.size() <= RELOAD_SIZE_TRIGGER){//当数据库中可用和正准备使用的名称数量小于RELOAD_SIZE_TRIGGER时，追加可用名称
				List<UsernameStatus> list = loadCertainNumAvaliableMaleUsernameStatus(LOAD_SIZE - RELOAD_SIZE_TRIGGER);
				avaliableMaleUsernameStatusList.addAll(list);
			}
			
			List<UsernameStatus> canUseList = new ArrayList<UsernameStatus>();
			for(UsernameStatus u : avaliableMaleUsernameStatusList){
				if(u.getStatus() == 1 && !alreadySee(u.getFullName(),player)){//名字可用
					canUseList.add(u);
				}
			}
			int canUseSize = canUseList.size();
			if(canUseSize <= 10){//如果小于等于10便不再随机,让玩家手动填写名称
				return "";
			}
			int index = MathUtils.random(canUseList.size());
			usernameStatus = canUseList.get(index);
			
			//设置次玩家名称被占用
			usernameStatus.setStatus(0);
			//记录当前玩家获取的名称，以便玩家对此名称不满意时，进行释放
			player.getData().setLastTimeTempUseFullName(usernameStatus.getFullName());
		}
		return usernameStatus.getFullName();
		
	}
	
	
	public String randomCanUseFemaleName(PlayerCharacter player){
		UsernameStatus usernameStatus;
		synchronized(this){
			releaseName(player);
			logger.info("------------------------------数据库中可用和正准备使用的女名称数量:" + avaliableFemaleUsernameStatusList.size());
			
			if(avaliableFemaleUsernameStatusList.size() <= RELOAD_SIZE_TRIGGER){//当数据库中可用和正准备使用的名称数量小于RELOAD_SIZE_TRIGGER时，追加可用名称
				List<UsernameStatus> list = loadCertainNumAvaliableFemaleUsernameStatus(LOAD_SIZE - RELOAD_SIZE_TRIGGER);
				avaliableFemaleUsernameStatusList.addAll(list);
			}
			
			List<UsernameStatus> canUseList = new ArrayList<UsernameStatus>();
			for(UsernameStatus u : avaliableFemaleUsernameStatusList){
				if(u.getStatus() == 1 && !alreadySee(u.getFullName(),player)){//名字可用
					canUseList.add(u);
				}
			}
			int canUseSize = canUseList.size();
			if(canUseSize <= 10){//如果小于等于10便不再随机,让玩家手动填写名称
				return "";
			}
			int index = MathUtils.random(canUseList.size());
			usernameStatus = canUseList.get(index);
			
			//设置次玩家名称被占用
			usernameStatus.setStatus(0);
			//记录当前玩家获取的名称，以便玩家对此名称不满意时，进行释放
			player.getData().setLastTimeTempUseFullName(usernameStatus.getFullName());
		}
		return usernameStatus.getFullName();
	}
	
	
	/**
	 * 当前玩家在最近至少10次随机选择角色名称中是否已经出现过此名称
	 * @param fullName
	 * @return
	 */
	private boolean alreadySee(String fullName,PlayerCharacter player){
		for(String name : player.getData().getGenerateNames()){
			if(name.equals(fullName)){//如果此角色名最近出现过
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取不重复的女玩家名
	 * 
	 * @deprecated
	 * @return String
	 */
	private String getFemaleName(PlayerCharacter player){
		UsernameStatus usernameStatus;
		synchronized(this){
			releaseName(player);
			//查找当前姓名组合在数据库中是否存在
			int index = MathUtils.random(avaliableFemaleUsernameStatusList.size());
			usernameStatus = avaliableFemaleUsernameStatusList.get(index);
			logger.info("------------------------------数据库中可用和正准备使用的女名称数量:" + avaliableFemaleUsernameStatusList.size());
		
			if(avaliableFemaleUsernameStatusList.size() <= RELOAD_SIZE_TRIGGER){//当数据库中可用和正准备使用的名称数量小于RELOAD_SIZE_TRIGGER时，追加可用名称
				List<UsernameStatus> list = loadCertainNumAvaliableFemaleUsernameStatus(LOAD_SIZE - RELOAD_SIZE_TRIGGER);
				avaliableFemaleUsernameStatusList.addAll(list);
			}

			//如果玩家名称已被另一个用户正在准备使用
			while(usernameStatus.getStatus() == 0 || alreadySee(usernameStatus.getFullName(),player)){
				
				logger.info("------------------------------数据库中可用和正准备使用的女名称数量:" + avaliableFemaleUsernameStatusList.size());
				
				//！！！！！！！！！！！！！！！！！！！！！！！此处可设置提前提醒
				if(avaliableFemaleUsernameStatusList.size() <= 10){
					logger.info("！！！！！！！！！！！！！！！！！！！数据库中可用角色名，满足条件的已用完，需要向数据库中添加可用记录！！！！！！！！！！！！");
					break;
				}
				index = MathUtils.random(avaliableFemaleUsernameStatusList.size());
				usernameStatus = avaliableFemaleUsernameStatusList.get(index);
			}
			
			//设置玩家名称被占用
			usernameStatus.setStatus(0);
			//记录当前玩家获取的名称，以便玩家对此名称不满意时，进行释放
			player.getData().setLastTimeTempUseFullName(usernameStatus.getFullName());
			logger.info("==================设置玩家名称被占用" + usernameStatus.getFullName());
		}
		
		
		//如果之前玩家已获取过随机名称，释放前一次获取的名称
		/*String lastTimeFullName = player.getData().getLastTimeTempUseFullName();
		if(!"".equals(lastTimeFullName)){
			for(int i=0;i<avaliableMaleUsernameStatusList.size();i++){
				//在List中找到上次随机的名称
				if(lastTimeFullName.equals(avaliableMaleUsernameStatusList.get(i).getFullName())){
					avaliableMaleUsernameStatusList.get(i).setStatus(1);
					break;
				}	
			}
		}*/
		
		return usernameStatus.getFullName();
	}
	
	
	
	
	public void forLog(){
		int maleCanUse = 0;
		int maleUsing = 0;
		int femaleCanUse = 0;
		int femaleUsing = 0;
		for(UsernameStatus u : avaliableMaleUsernameStatusList){
			if(u.getStatus() == 1){//可用男玩家名数量
				maleCanUse ++;
			}
			else{
				maleUsing ++;
			}
		}
		for(UsernameStatus u : avaliableFemaleUsernameStatusList){
			if(u.getStatus() == 1){//可用女玩家名数量
				femaleCanUse ++;
			}
			else{
				femaleUsing ++;
			}
		}
		logger.info("内存总男玩家名总数量" + avaliableMaleUsernameStatusList.size());
		logger.info("内存中可以使用的男玩家名数量" + maleCanUse);
		logger.info("内存中正在被占用的男玩家名数量" + maleUsing);
		logger.info("内存总女玩家名总数量" + avaliableFemaleUsernameStatusList.size());
		logger.info("内存中可以使用的女玩家名数量" + femaleCanUse);
		logger.info("内存中正在被占用的女玩家名数量" + femaleUsing);
		
	}
	
	/**
	 * 用户提交修改信息
	 * 内存中清除掉已经用掉的名字，数据库中如果是随机出来的名字则做上已使用的标记
	 * 如果用户名是自己写的，则先检测用户名是否在数据库中存在，如果存在则重新填写
	 * @param player
	 */
	public TipUtil commit(byte userType,String username,PlayerCharacter player){
		
		TipUtil returnTip = new TipUtil(ProcotolType.USER_INFO_RESP);
		String lastTimeFullName = player.getData().getLastTimeTempUseFullName();
		boolean success = false;
		//用户没有使用随机姓名
		if(!username.equals(lastTimeFullName)){
			
			//检查用户名是否合法,如果不合法
			TipUtil tip = isNameLegal(username,1);
			if(!tip.isResult()){
				/*GameUtils.sendTip(
						new TipMessage(tip.getTip().getMessage(), ProcotolType.USER_INFO_RESP,
								GameConst.GAME_RESP_FAIL), player.getUserInfo());*/
				return tip;
				
			}
			
			
			//从数据库中检测玩家手动填写名称是否有重复
			boolean exist = DBManager.getInstance().getWorldDAO().isNameExist(username);
			
			//如果不存在
			if(!exist){
				RoleData roleData = player.getData();
				roleData.setName(username);
				roleData.setFaction(userType);
				roleData.setIsChangName((byte)1);
//				success = DBManager.getInstance().getWorldDAO().saveRole(roleData);
				GameUtils.putToCache(player);
				returnTip.setSuccTip("");
				return returnTip;
				
			}
			return returnTip.setFailTip("用户名已存在");
		}
		
		else{
			int deleteIndex = 0;
			//找到对应名称所在生成表中的id,并置为不可用
			if(userType == 1 || userType == 2 || userType == 3){//男性
				synchronized(this){
					for(UsernameStatus u : avaliableMaleUsernameStatusList){
						if(username.equals(u.getFullName())){
							u.setStatus(0);
							//数据库中置为不可用
							success = DBManager.getInstance().getWorldDAO().getUsernameStatusDAO().saveUsernameStatus(u);
							break;
						}
						
						//alter 9-29
						deleteIndex ++;
					}
					//alter 9-29
					//内存中清除掉已经用掉的名字
					avaliableMaleUsernameStatusList.remove(deleteIndex);
				}
			}
			if(userType == 4 || userType == 5 || userType == 6){//女性
				synchronized(this){
					for(UsernameStatus u : avaliableFemaleUsernameStatusList){
						if(username.equals(u.getFullName())){
							u.setStatus(0);
							//数据库中置为不可用
							success = DBManager.getInstance().getWorldDAO().getUsernameStatusDAO().saveUsernameStatus(u);
							break;
						}
						//alter 9-29
						deleteIndex ++;
					}
					//alter 9-29
					//内存中清除掉已经用掉的名字
					avaliableFemaleUsernameStatusList.remove(deleteIndex);
				}
			}
			
			//如果更行随机名称表中名称可用状态成功，则执行改变玩家名称操作
			if(success){
				RoleData roleData = player.getData();
				roleData.setName(username);
				roleData.setFaction(userType);
				roleData.setIsChangName((byte)1);
//				success = DBManager.getInstance().getWorldDAO().saveRole(roleData);
				
				//*******************      rms
				RespModuleSet rms=new RespModuleSet(ProcotolType.USER_INFO_RESP);
				rms.addModule(roleData);
				AndroidMessageSender.sendMessage(rms,player);
				//********************   rms
				GameUtils.putToCache(player);
				return returnTip.setSuccTip("");
			}
			return returnTip.setFailTip("");
			
		}
	}
	
	/**
	 * 判断玩家名是否合法
	 * 1、1-6个字（字母，数字，汉字都算1个字）
	 * 2、只能字母（区分大小写），数字，汉字
	 * @param name 玩家输入的字符串
	 * @param which 1--玩家改名   2--州市县改名
	 * @return
	 */
	public TipUtil isNameLegal(String name,int which){
		TipUtil tipUtil = new TipUtil(ProcotolType.USER_INFO_RESP);
		String regPlayer = "[0-9a-zA-Z[\u4E00-\u9FA5]-]{1,6}";
		String regNation = "[0-9a-zA-Z[\u4E00-\u9FA5]]{1,4}";
		String useReg = "";
		if(1 == which){
			useReg = regPlayer;
			if(name.length() > 6){//为分开提示而增加的判断
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					return tipUtil.setFailTip("Can not be more than 6 letters.");
				}else{
					return tipUtil.setFailTip("不能超过6个字符");
				}
				
			}
		}
		else{
			useReg = regNation;
			if(name.length() > 4){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					return tipUtil.setFailTip("Can not be more than 4 letters.");
				}else{
					return tipUtil.setFailTip("不能超过4个字符");
				}
				
			}
		}
		Pattern pat = Pattern.compile(useReg); 
		Matcher matcher = pat.matcher(name);
		if(matcher.matches()){
			for(String str : disallowStrList){
				if(name.contains(str)){
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						return tipUtil.setFailTip("Name contains shielding letters.");
					}else{
						return tipUtil.setFailTip("名称中含有屏蔽字符");
					}
					
				}
			}
			return tipUtil.setSuccTip("");
		}
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			return tipUtil.setFailTip("Please input legal letters.");
		}else{
			return tipUtil.setFailTip("请输入合法字符");//名字为字母数字汉字组成
		}
		
	}
	
	public void releaseName(PlayerCharacter player){
		
		String lastTimeFullName = player.getData().getLastTimeTempUseFullName();
		logger.info("==================释放玩家名" + lastTimeFullName);
		if(!"".equals(lastTimeFullName)){
			//从内存List中找到上次随机的名称，并释放,能找到则说明是在选择名称没有提交前就退出游戏
			//因为提交后会从List中删除，不可能找到
			for(UsernameStatus u : avaliableMaleUsernameStatusList){
				if(lastTimeFullName.equals(u.getFullName())){
					u.setStatus(1);
					logger.info("男名释放成功");
					break;
				}
			}
			for(UsernameStatus u : avaliableFemaleUsernameStatusList){
				if(lastTimeFullName.equals(u.getFullName())){
					u.setStatus(1);
					logger.info("女名释放成功");
					break;
				}
			}
		}
	}
	
	
}
