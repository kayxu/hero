package com.joymeng.web.service.biz;

import java.util.List;

import org.springframework.stereotype.Component;

import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.hero.PlayerHero;

@Component
public class BizHero {
	
	public List<PlayerHero>  getHeros(int userId){
		List<PlayerHero> list=	DBManager.getInstance().getWorldDAO().getAllPlayerHero(userId);
		return list;
	}
	public PlayerHero getHero(int heroId){
		PlayerHero ph=DBManager.getInstance().getWorldDAO().getPlayerHero(heroId);
		return ph;
	}
}
