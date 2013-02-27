package com.joymeng.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.client.net.LoginResp;
import com.joymeng.core.base.net.request.EchoRequest;
import com.joymeng.core.base.net.response.EchoResp;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.hero.Hero;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.game.net.request.EnterGameRequest;
import com.joymeng.game.net.request.FightRequest;
import com.joymeng.game.net.request.HeroRequest;
import com.joymeng.game.net.response.EnterGameResp;
import com.joymeng.game.net.response.HeroResp;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyNormalMessage;
import com.joymeng.services.core.message.JoyNormalMessage.UserInfo;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.message.JoyRequest;

public class ClientHandler extends IoHandlerAdapter {
	static int LOGIN_UID=0;
	static int  heroId=0;
	static UserInfo info=new UserInfo();
	static HeroRequest heroQuest=new HeroRequest();
	public static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("session create...");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info("session closed...");
	}

	private void _handleMessage(IoSession session, JoyBuffer buffer)
			throws Exception {

		// 回到上次标识处
		buffer.reset();

		int messageID = buffer.getInt(JoyNormalMessage.FIX_HEAD_LENGTH);
		int srcInstID = buffer.getInt(JoyNormalMessage.POS_SRC_INSTANCE_ID);
		
	
		// logger.info("from instance:" + srcInstID);
		// logger.info("messageID:" + messageID);
		switch (messageID) {
		case Client.LOGIN_RESP:// 响应用户登录
		{
			LoginResp resp = new LoginResp();
			resp.deserialize(buffer);
			
			LOGIN_UID=resp.getUserId();
			System.out.println("login result=="+resp.getResult()+" LOGIN_UID="+LOGIN_UID);
			if (resp.getResult() == 1) {
				logger.info("login succ...");
				//向游戏服务器发送echo测试消息
				EchoRequest req = new EchoRequest();
//				info.setUid(Client.UID);
				req.setDestInstanceID(Client.INST_GAME_SVR);
				req.setUserInfo(info);
				req.setContent("echo test");
				session.write(req);

			} else {
				logger.info("login fail...");
			}
		}
			break;
		case Client.ECHO_RESP:// 响应echo
		{
			EchoResp message = new EchoResp();
			message.deserialize(buffer);
			//进入游戏服务器
			EnterGameRequest req=new EnterGameRequest();
			req.setUserInfo(message.getUserInfo());
			req.setUid(LOGIN_UID);
			req.setDestInstanceID(Client.INST_GAME_SVR);
			session.write(req);
		}
			break;
		case ProcotolType.ENTER_GAME_RESP:
			EnterGameResp resp=new EnterGameResp();
			resp.deserialize(buffer);
			info=resp.getUserInfo();
			//请求玩家信息
//			UserRequest req=new UserRequest();
//			req.setType((byte)1);
//			req.setUserId(Client.LOGIN_UID);
//			req.setCityName("test");
//			req.setCountryId((byte)1);
//			req.setStateId((byte)1);
//			req.setCityId((byte)1);
//			sendRequest(req,resp.getUserInfo(),session);
			
			//战斗指令 0
//			FightRequest req3=new FightRequest();
//			req3.setType((byte)0);
//			req3.setAttackUser(230);//mytest1
//			req3.setAttackHero(1495);
//			req3.setSoldier1("1:100;2:300;3:100;5:300");
//			req3.setDefenceUser(275);//mytest2 
//			req3.setDefenceHero(1496);
//			req3.setSoldier2("1:300;2:100;3:300;4:100");
//			sendRequest(req3,resp.getUserInfo(),session);
			//战斗指令 1
			FightRequest req3=new FightRequest();
			req3.setType(FightConst.FIGHTBATTLE_LADDER);
//			req3.setAttackUser(295);//mytest1
			req3.setAttackHero(1698);
			req3.setSoldier1("1:100;2:300;3:100;5:300");
//			req3.setCampId(2);
			req3.setId(1);
			sendRequest(req3,resp.getUserInfo(),session);
			//发送刷新将领
//			HeroRequest req=new HeroRequest();
//			req.setId(2);
//			req.setType(ProcotolType.HERO_REFRESH);
//			sendRequest(req,resp.getUserInfo(),session);
			break;
		case ProcotolType.HERO_RESP:
			HeroResp message=new HeroResp();
			message.deserialize(buffer);
			
//			HeroRequest req2=new HeroRequest();
			if(message.getType()==ProcotolType.HERO_REFRESH){
				logger.info("刷新将领");
				//发送招募将领
//				Hero[] array=message.getArray();
//				req2.setType(ProcotolType.HERO_GET);
//				req2.setId(array[0].getId());
//				sendRequest(req2,message.getUserInfo(),session);
			}
			else if(message.getType()==ProcotolType.HERO_GET){//发送获得将领列表
				logger.info("获得将领");
				//开始训练
				heroQuest.setType(ProcotolType.HERO_TRAINSTART);
				heroQuest.setId(heroId);
				heroQuest.setTrainIndex((byte)0);
				heroQuest.setTrainType((byte)0);
//				req2.setType(ProcotolType.HERO_LIST);
//				sendRequest(req2,message.getUserInfo(),session);
			}else if(message.getType()==ProcotolType.HERO_LIST){
				//发送删除将领
//				req2.setType(ProcotolType.HERO_DEL);
//				req2.setId(	message.getPlayerHero()[0].getId());
//				sendRequest(req2,message.getUserInfo(),session);
					//发送战斗指令
				
				//发送学习技能指令
//				req2.setType(ProcotolType.HERO_ADDSKILL);
//				req2.setId(42);
//				req2.setSkillId(1);
//				sendRequest(req2,message.getUserInfo(),session);
				//删除将领技能
//				req2.setType(ProcotolType.HERO_DELSKILL);
//				req2.setId(42);
//				req2.setSkillId(1);
//				sendRequest(req2,message.getUserInfo(),session);
			}
			break;
		case 1111:
			buffer.skip(46);
			int msgId = buffer.getInt();
//			logger.info("将领模块="+ProcotolType.HERO_RESP);
			List<Hero> list=new ArrayList<Hero>();
			if(msgId==ProcotolType.HERO_RESP){
				byte num=buffer.get();
				logger.info("将领模块="+ProcotolType.HERO_RESP+" num="+num);
				for(int i=0;i<num;i++){
					byte type=buffer.get();
					switch(type){
					case ClientModule.NTC_DTCD_PLAYERHERO:
						PlayerHero phero=new PlayerHero();
						phero.deserialize(buffer);
//						phero.print();
						break;
					case ClientModule.NTC_DTCD_HERO:
						Hero hero=new Hero();
						hero.deserialize(buffer);
						hero.print();
						list.add(hero);
						break;
					case ClientModule.NTC_ROLEDATA:
						RoleData rd=new RoleData();
						rd.deserialize(buffer);
//						rd.print();
						break;
					}
				}
				if(list.size()!=0){
					//发送招募将领
//					heroId=list.get(0).getId();
//					heroQuest.setType(ProcotolType.HERO_GET);
//					heroQuest.setId(heroId);
//					sendRequest(heroQuest,info,session);
				}
			}
//			logger.info("clientHandle 接收到通用模块数据 received Resp : " + msgId + "\t" + Integer.toHexString(msgId));   //commandId
			break;
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
//		System.out.println("message receved");
		IoBuffer temp = (IoBuffer) message;
		JoyBuffer buffer = JoyBuffer.wrapBuffer(temp, false);

		buffer.mark();

		// 得到一个无符号字节
		int len = buffer.getInt();
		int type = buffer.getUnsigned();

		switch (type) {
		case 0xFF:
			int port = buffer.getInt();
			String address = buffer.getPrefixedString(1);
			logger.info("redirect to address:" + address + " port:" + port);
			Client.connect(address, port, true);
			break;

		case JoyProtocol.PROTOCOL_NORMAL:
			_handleMessage(session, buffer);
			break;

		default:
			logger.info("error msg...");
		}

	}
	/**
	 * 发送消息
	 * @param req
	 * @param info
	 * @param session
	 */
	public void sendRequest(JoyRequest req,UserInfo info,IoSession session){
		req.setUserInfo(info);
		req.setDestInstanceID(Client.INST_GAME_SVR);
		session.write(req);
	}

}