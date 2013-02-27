package com.joymeng.web.web.background;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joymeng.core.db.cache.mongo.MongoTest;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.shop.GoodsManager;
import com.joymeng.web.entity.Sale;

@Controller
@RequestMapping(value = "/bg_output")
public class BgOutputController {
	 @RequestMapping(method = RequestMethod.GET)
	  public String handle(ModelMap modelMap) {
		 return "background/bg_output";
	 }
}
