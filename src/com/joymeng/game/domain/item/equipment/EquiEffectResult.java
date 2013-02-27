package com.joymeng.game.domain.item.equipment;

public enum EquiEffectResult 
{
	SUCCESSFULL(true,""),
	FAILED(false,"");
	;
	
	
	
	private String equiOrResult;
	private boolean status;
	
	private EquiEffectResult(boolean st,String re)
	{
		status = st;
		equiOrResult = re;
	}
	/**
	 * 设置 equiOrResult
	 * @param equiOrResult the equiOrResult to set
	 */
	public void setEquiOrResult(String equiOrResult) {
		this.equiOrResult = equiOrResult;
	}

	/**
	 * 是否成功
	 * @return
	 */
	public boolean getStatus(){
		return status;
	}
	
	/**
	 * 原因 或 新ID
	 * @return
	 */
	public String getResult(){
		return equiOrResult;
	}
}
