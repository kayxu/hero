package com.joymeng.web.web.background;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joymeng.web.service.biz.BizWar;

@Controller
@RequestMapping("/wars")
public class WarsController extends BaseController {
	
	 @RequestMapping
	  public String enterWar(HttpServletRequest request,ModelMap modelMap) {
		 modelMap.put("msg", "enter");
		 return "background/wars";
	 }
	 
	 @RequestMapping(params = "method=start")
	  public String startWar(HttpServletRequest request,ModelMap modelMap) {
		  BizWar.getInstance()._startWar(request,modelMap);
		  return "background/wars";
	  }
	 
	 @RequestMapping(params = "method=stop")
	  public String stopWar(HttpServletRequest request,ModelMap modelMap) {
		 BizWar.getInstance()._stopWar(request,modelMap);
		 return "background/wars";
	  }

	@Override
	public int _getCountPage() {
		// TODO Auto-generated method stub
		return 0;
	}
}
