package com.joymeng.game.common;

import java.util.List;

import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipModule;
import com.joymeng.game.net.client.ClientModule;

public class MessageUtil implements TipModule{
	private TipMessage tip;
	private List<ClientModule> moduleLst;//对象
	@Override
	public TipMessage getTip() {
		return this.tip;
	}

	@Override
	public void setTip(TipMessage tip) {
		this.tip = tip;
	}

	/**
	 * @return GET the moduleLst
	 */
	public List<ClientModule> getModuleLst() {
		return moduleLst;
	}

	/**
	 * @param SET moduleLst the moduleLst to set
	 */
	public void setModuleLst(List<ClientModule> moduleLst) {
		this.moduleLst = moduleLst;
	}
	
}
