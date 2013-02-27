package com.joymeng.game.domain.recharge;

public class PlayerRecharge {

	/**
	 * 玩家id
	 */
	private int userid;
	
	/**
	 * 玩家已经充值的钻数
	 */
	private int alreadyRechargeVal;
	
	/**
	 * 玩家充值后获得的且未领取的奖品的层级和奖品所在包裹id
	 * 字符串解析类型      id:id:id:id:stage;id:id:id:id:stage
	 */
	private String stageAndPackageIds;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getAlreadyRechargeVal() {
		return alreadyRechargeVal;
	}

	public void setAlreadyRechargeVal(int alreadyRechargeVal) {
		this.alreadyRechargeVal = alreadyRechargeVal;
	}

	public String getStageAndPackageIds() {
		return stageAndPackageIds;
	}

	public void setStageAndPackageIds(String stageAndPackageIds) {
		this.stageAndPackageIds = stageAndPackageIds;
	}
	
	
}
