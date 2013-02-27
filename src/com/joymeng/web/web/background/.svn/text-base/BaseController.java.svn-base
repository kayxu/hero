package com.joymeng.web.web.background;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.joymeng.game.common.Instances;
import com.joymeng.game.db.GameDAO;

public abstract class BaseController implements Instances {
	
	/**
	 * 获取总页数
	 * @return
	 */
	public abstract int _getCountPage();

	/**
	 * 返回分页数据
	 * @param request
	 * @param modelMap
	 * @param doCode
	 */
	public void loadDataAll(HttpServletRequest request,ModelMap modelMap,String doCode) {
		if (request.getParameter("pages") != null) {
    	    int pags =  Integer.parseInt(request.getParameter("pages"));//分页页码变量
    	    modelMap.put("page", pageCodes(pags, _getCountPage(), doCode));
		}
	}
	
	/**
	 * 分页 html 
	 * @param pages 当前页码
	 * @param all 总页数
	 * @param doCode 请求类型
	 * @return
	 */
	public String pageCodes(int pages,int all,String doCode){
		StringBuffer sb = new StringBuffer();
		 int listbegin = (pages - (int) Math.ceil((double) GameDAO.LISTSTEP / 2));//从第几页开始显示分页信息
         if (listbegin < 1) {
       	     listbegin = 1;
         }
       //显示数据部分
       //<显示分页信息
       //<显示上一页
       if (pages > 1) {
       	sb.append(
                    "<a href=\"#\" onclick=\""+doCode+0+"')\"> 首页 </a>");
       	sb.append(
           "<a href=\"#\" onclick=\""+doCode+(pages-1)+"')\" >| 上一页 </a>");
       }//>显示上一页
       //<显示分页码
       for (int i = listbegin; i <=all; i++) {
           if (i != pages) {//如果i不等于当前页
           	sb.append(
               "<a href=\"#\" onclick=\""+doCode+i+"')\" >[" + i + "]</a>");
           } else {
           	sb.append("[" + i + "]");
           }
       }//显示分页码>
       sb.append(
 	          "<input id=\"pages\" name=\"pages\" maxlength=\"3\" size=\"1\" value="+pages+" type=\"hidden\"/>");
       //<显示下一页
       if (pages != all) {
       	sb.append(
           "<a href=\"#\" onclick=\""+doCode+(pages+1)+"')\"> 下一页 </a>");
       }//>显示下一页
       if (all >1 && pages != all) {
    	
       	sb.append(
           "<a href=\"#\" onclick=\""+doCode+(all)+"')\">| 末页 </a>");
       }
       System.out.println(sb.toString());
       return sb.toString();
	}
	
	
}
