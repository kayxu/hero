package com.joymeng.game.domain.question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;

public class UserQuestionManager {

	private static final Logger logger = LoggerFactory.getLogger(UserQuestionManager.class);
	
	private static UserQuestionManager userQuestionManager;
	
	public static UserQuestionManager getInstance(){
		
		if(null == userQuestionManager){
			userQuestionManager = new UserQuestionManager();
		}
		return userQuestionManager;
	}
	
	/**
	 * 提交玩家联系客服信息
	 * @param player 玩家
	 * @param content String 联系客服问题内容
	 * @return
	 */
	public boolean postUserQuestion(PlayerCharacter player,String content){
		
		//如果玩家2次联系客服时间间隔小于1小时，则阻止
		if((TimeUtils.nowLong() - player.getData().getLastPostQuestionTime()) < 1 * 60 * 60 * 1000){
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(
						new TipMessage("You ask question too frequently, please try again later!", ProcotolType.USER_QUESTION_RESP,
								GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(
						new TipMessage("您的提问过于频繁，请稍后重试！", ProcotolType.USER_QUESTION_RESP,
								GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}
			
			//logger.info("玩家2次联系客服时间间隔小于1小时，阻止");
			return false;
		}
		
		UserQuestion userQuestion = new UserQuestion();
		userQuestion.setUserid(player.getData().getUserid());
		userQuestion.setContent(content);
		
		//向数据库中增加玩家联系客服问题
		boolean success = DBManager.getInstance().getWorldDAO().getUserQuestionDAO().addUserQuestion(userQuestion);
		
		return success;
		
	}
	
	
}
