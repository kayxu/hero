package com.joymeng.web.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.Instances;

/**
 * GM管理
 * @author Administrator
 *
 */
public abstract class AbstractBusiness implements Instances{
	
	protected Logger  logger = LoggerFactory.getLogger(AbstractBusiness.class);
	
	/**
     * 根据客户端协议拼查询条件
     *
     * @param request 客户端发起的协议包
     */
    public abstract String doSelectCondition(HttpServletRequest request);
    
    /**
	 * 返回 获取总页数
	 * @param count
	 * @return
	 */
    public abstract int _getCountPage(int count);
    
    /**
     * 获取当前页码
     * @param request
     * @return
     */
    public int _getCurrentPage(HttpServletRequest request){
    	if(request.getParameter("pages") != null){
    		return Integer.parseInt(request.getParameter("pages"));//分页页码变量
    	}
    	return 0;
    }
}
