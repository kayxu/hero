package com.joymeng.web.entity;

import java.util.List;

public class ExtremeBox {

	/**
	 * 转动人数
	 */
	private int rollNum;
	
	/**
	 * 转动次数
	 */
	private int rollTimes;
	
	/**
	 * 刷新人数
	 */
	private int refreshNum;
	
	/**
	 * 刷新次数
	 */
	private int refreshTimes;
	
	/**
	 * 获得宝箱总积分
	 */
	private int totalScore;
	
	/**
	 * 奖品ID列表
	 */
	private List<Integer> awardIDList;

	public int getRollNum() {
		return rollNum;
	}

	public void setRollNum(int rollNum) {
		this.rollNum = rollNum;
	}

	public int getRollTimes() {
		return rollTimes;
	}

	public void setRollTimes(int rollTimes) {
		this.rollTimes = rollTimes;
	}

	public int getRefreshNum() {
		return refreshNum;
	}

	public void setRefreshNum(int refreshNum) {
		this.refreshNum = refreshNum;
	}

	public int getRefreshTimes() {
		return refreshTimes;
	}

	public void setRefreshTimes(int refreshTimes) {
		this.refreshTimes = refreshTimes;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public List<Integer> getAwardIDList() {
		return awardIDList;
	}

	public void setAwardIDList(List<Integer> awardIDList) {
		this.awardIDList = awardIDList;
	}
	
	
}
