package com.joymeng.web.service.biz;

import java.util.List;

import org.springframework.stereotype.Component;

import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.hero.PlayerHero;
@Component
public class BizBuild {
	public List<PlayerBuilding>  getBuild(int userId){
		List<PlayerBuilding> list = DBManager.getInstance().getWorldDAO().getBuildDAO()
				.getAllPlayerBuilding(userId);
		return list;
	}
}
