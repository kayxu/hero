package com.joymeng.game.domain.recharge;

public class Recharge {

	/**
	 * 充值额度
	 */
	int rechargeVal;
	
	/**
	 * 奖励包裹
	 */
	int packageId;
	
	/**
	 * 关键奖励
	 */
	String keyAward;
	
	/**
	 * 礼包价值
	 */
	int awardVal;

	public int getRechargeVal() {
		return rechargeVal;
	}

	public void setRechargeVal(int rechargeVal) {
		this.rechargeVal = rechargeVal;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getKeyAward() {
		return keyAward;
	}

	public void setKeyAward(String keyAward) {
		this.keyAward = keyAward;
	}

	public int getAwardVal() {
		return awardVal;
	}

	public void setAwardVal(int awardVal) {
		this.awardVal = awardVal;
	}

	@Override
	public String toString() {
		return "Recharge [rechargeVal=" + rechargeVal + ", packageId="
				+ packageId + ", keyAward=" + keyAward + ", awardVal="
				+ awardVal + "]";
	}
	
}
