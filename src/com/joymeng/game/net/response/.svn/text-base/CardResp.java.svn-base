package com.joymeng.game.net.response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.card.Card;
import com.joymeng.game.domain.card.CardManager;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class CardResp extends JoyResponse {

	private static final Logger logger = LoggerFactory.getLogger(CardResp.class);
	
	byte result;
	int errorCode;
	byte type;
	byte chance;
	byte rotateChance;
	int nextTime;
	List<Card> showCardsList;
	
	/**
	 * 翻开的牌们
	 */
	List<Card> showCards;
	
	Card[] cards;
	
	public CardResp() {
		super(ProcotolType.CARD_RESP);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		switch(type){
		
		case ProcotolType.ENTER://进入
		case ProcotolType.GET_CHANCE://倒计时到，获取翻牌机会
		case ProcotolType.ROTATE_CARD_FACE://旋转牌
		case ProcotolType.GET_AWARD://领取奖品
			 out.put(chance);
			 out.put(rotateChance);
			 out.putInt(nextTime);
			 out.put((byte)cards.length);
			 //logger.info("=======resp========");
			 //logger.info("chance:" + chance);
			 //logger.info("rotateChance:" + rotateChance);
			 //logger.info("nextTime:" + nextTime);
			 //logger.info("cards.length:" + cards.length);
			 for(Card card : cards){
				 if(card.getWhat() != 0){//如果当前牌处于翻开状态
					 //logger.info("0--翻开");
					 out.put((byte)0);
					 out.putInt(card.getWhat());
					 out.putInt(card.getValue());
					 //logger.info("what:" + card.getWhat());
					 //logger.info("value:" + card.getValue());
				 }
				 else{//如果当前牌处于背面未翻开
					 //logger.info("1--未翻开");
					 out.put((byte)1);
					 out.put(card.getFace());
					 //logger.info("face:" + card.getFace());
				 }
			 }
			 //logger.info("============resp end=============");
			 break;
		case ProcotolType.FLIP_CARDS://翻牌
			 out.put(chance);
			 out.put(rotateChance);
			 out.putInt(nextTime);
			 out.put((byte)cards.length);
			 //logger.info("=======resp========");
			 //logger.info("chance:" + chance);
			 //logger.info("rotateChance:" + rotateChance);
			 //logger.info("nextTime:" + nextTime);
			 //logger.info("cards.length:" + cards.length);
			 for(Card card : cards){
				 if(card != null){
					 if(card.getWhat() != 0){//如果当前牌处于翻开状态
						 //logger.info("0--翻开");
						 out.put((byte)0);
						 out.putInt(card.getWhat());
						 out.putInt(card.getValue());
						 //logger.info("what:" + card.getWhat());
						 //logger.info("value:" + card.getValue());
					 }
					 else{//如果当前牌处于背面未翻开
						 //logger.info("1--未翻开");
						 out.put((byte)1);
						 out.put(card.getFace());
						 //logger.info("face:" + card.getFace());
					 }
				 }
			 }
			 out.put((byte)showCardsList.size());
			 for(Card card : showCardsList){
				 out.put(card.getIndex());
				 //logger.info("翻开牌索引：" + card.getIndex());
			 }
			 break;
		case ProcotolType.OUT_SHOW://外部显示倒计时和翻牌机会
		case ProcotolType.OUT_GET_CHANCE://牌局外倒计时到，获取翻牌机会增加
			out.put(chance);
			out.putInt(nextTime);
			//logger.info("-------------------------------------chance=" + chance + "nextTime=" + nextTime);
			break;
		}
		
		
			
		
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public Card[] getCards() {
		return cards;
	}

	public void setCards(Card[] cards) {
		this.cards = cards;
	}

	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public List<Card> getShowCards() {
		return showCards;
	}

	public void setShowCards(List<Card> showCards) {
		this.showCards = showCards;
	}

	public byte getChance() {
		return chance;
	}

	public void setChance(byte chance) {
		this.chance = chance;
	}

	public byte getRotateChance() {
		return rotateChance;
	}

	public void setRotateChance(byte rotateChance) {
		this.rotateChance = rotateChance;
	}

	public int getNextTime() {
		return nextTime;
	}

	public void setNextTime(int nextTime) {
		this.nextTime = nextTime;
	}

	public List<Card> getShowCardsList() {
		return showCardsList;
	}

	public void setShowCardsList(List<Card> showCardsList) {
		this.showCardsList = showCardsList;
	}

	
	
	
	
	
	
	
	
	
	
	

}
