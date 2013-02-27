package com.joymeng.game.job;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.shop.GoodsManager;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.World;

/**
 * 定时处理玩家数据
 * @author admin
 *
 */
public class GoodsUpdateJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//处理热门道具
		String time=TimeUtils.now().format(TimeUtils.FORMAT);
		GoodsManager.getInstance().mathGoodProps(time,time);
		ArenaManager.getInstance().saveAll();
		ArenaManager.getInstance().check();
	}

}
