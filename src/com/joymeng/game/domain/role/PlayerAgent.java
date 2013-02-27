package com.joymeng.game.domain.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.event.GameEventListener;
import com.joymeng.game.common.Instances;

/**
 * 角色代理模块，相当于BasePlayerModel
 * 
 * @author Shaolong Wang
 * 
 */
public interface PlayerAgent extends GameEventListener, Instances {
	public static final Logger logger = LoggerFactory
			.getLogger(PlayerAgent.class);

	/**
	 * 写入数据库VO
	 * 
	 * @param data
	 */
	public void writeToEntity(RoleData data);

	/**
	 * 载入VO中数据
	 * 
	 * @param data
	 */
	public void readFromEntity(RoleData data);
}
