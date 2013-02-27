package com.joymeng.web.entity;

import java.util.ArrayList;
import java.util.List;

public class CardOperationInfo {

	/**
	 * 翻牌人数
	 */
	private int flipPlayerNum;
	
	/**
	 * 翻牌的次数
	 */
	private int flipTimes;
	
	/**
	 * 旋转牌人数
	 */
	private int rotatePlayerNum;
	
	/**
	 * 旋转牌的次数
	 */
	private int rotateTimes;
	
	/**
	 * 翻开的总牌数
	 */
	private int totalFlipNums;
	
	/**
	 * 奖品id列表
	 */
	private List<Integer> awardIdList = new ArrayList<Integer>();
	
	/**
	 * 奖品数目列表
	 */
	private List<Integer> awardNumList = new ArrayList<Integer>();

	public int getFlipPlayerNum() {
		return flipPlayerNum;
	}

	public void setFlipPlayerNum(int flipPlayerNum) {
		this.flipPlayerNum = flipPlayerNum;
	}

	public int getFlipTimes() {
		return flipTimes;
	}

	public void setFlipTimes(int flipTimes) {
		this.flipTimes = flipTimes;
	}

	public int getRotatePlayerNum() {
		return rotatePlayerNum;
	}

	public void setRotatePlayerNum(int rotatePlayerNum) {
		this.rotatePlayerNum = rotatePlayerNum;
	}

	public int getRotateTimes() {
		return rotateTimes;
	}

	public void setRotateTimes(int rotateTimes) {
		this.rotateTimes = rotateTimes;
	}

	public int getTotalFlipNums() {
		return totalFlipNums;
	}

	public void setTotalFlipNums(int totalFlipNums) {
		this.totalFlipNums = totalFlipNums;
	}

	public List<Integer> getAwardIdList() {
		return awardIdList;
	}

	public void setAwardIdList(List<Integer> awardIdList) {
		this.awardIdList = awardIdList;
	}

	public List<Integer> getAwardNumList() {
		return awardNumList;
	}

	public void setAwardNumList(List<Integer> awardNumList) {
		this.awardNumList = awardNumList;
	}

	
	
	
	
}
