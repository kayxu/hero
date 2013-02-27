package com.joymeng.game.domain.fight.result;
/**
 * 战斗奖励
 * @author admin
 * @date 2012-5-3
 * TODO
 */
public class FightReward {
	
//	1：附属城
//	2：战役-----
//	3：通天塔----
//	4：金矿争夺
//	5：金矿脉战斗
//	6：资源争夺
//	7：资源脉争夺
//	8：县长争夺
//	9：市长争夺战-攻打据点
//	10：市长争夺战-攻打市
//	11：州长争夺战-攻打据点
//	12：州长争夺战-攻打州
//	13：国王争夺战-攻打据点
//	14：国王争夺战-攻打国
//	15：1V1战场
//	16：3V3战场
	int point;//节点
	int accomplishing;
    int winrecover;//战败胜愈
    byte type;
	int award;//功勋
	int recover;//战胜胜愈
	int rate;
	int quality;
	int equip;
	int exp;//经验
	int money;//金钱
	int propId1;
	int rate1;
	int propId2;
	int rate2;
	int propId3;
	int rate3;
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getAccomplishing() {
		return accomplishing;
	}
	public void setAccomplishing(int accomplishing) {
		this.accomplishing = accomplishing;
	}
	public int getWinrecover() {
		return winrecover;
	}
	public void setWinrecover(int winrecover) {
		this.winrecover = winrecover;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public int getAward() {
		return award;
	}
	public void setAward(int award) {
		this.award = award;
	}
	public int getRecover() {
		return recover;
	}
	public void setRecover(int recover) {
		this.recover = recover;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public int getEquip() {
		return equip;
	}
	public void setEquip(int equip) {
		this.equip = equip;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getPropId1() {
		return propId1;
	}
	public void setPropId1(int propId1) {
		this.propId1 = propId1;
	}
	public int getRate1() {
		return rate1;
	}
	public void setRate1(int rate1) {
		this.rate1 = rate1;
	}
	public int getPropId2() {
		return propId2;
	}
	public void setPropId2(int propId2) {
		this.propId2 = propId2;
	}
	public int getRate2() {
		return rate2;
	}
	public void setRate2(int rate2) {
		this.rate2 = rate2;
	}
	public int getPropId3() {
		return propId3;
	}
	public void setPropId3(int propId3) {
		this.propId3 = propId3;
	}
	public int getRate3() {
		return rate3;
	}
	public void setRate3(int rate3) {
		this.rate3 = rate3;
	}
	
	
	
}
