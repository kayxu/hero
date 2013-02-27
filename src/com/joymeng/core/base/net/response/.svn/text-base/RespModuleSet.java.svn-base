package com.joymeng.core.base.net.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.time.SysTime;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;
/**
 * moduleSet包括多个clientModule
 * @author admin
 * @date 2012-5-17
 * TODO
 */
public class RespModuleSet extends JoyResponse
{
	private static final long serialVersionUID = -2818349231873263902L;
	static final Logger logger = LoggerFactory.getLogger(RespModuleSet.class);
	
	List<ClientModule> moduleList = new ArrayList<ClientModule>();
	int commandId = 0;
	public RespModuleSet(int commandID)
	{
		super(ProcotolType.COMMON_RESP);
		this.commandId = commandID;
	}
	
	public RespModuleSet(int commandID, byte result)
	{
		super(ProcotolType.COMMON_RESP, result);
		this.commandId = commandID;
	}
	
	public void clearAll()
	{
		moduleList.clear();
	}
	
	
	public void addModule(ClientModule module)
	{
		if(module==null){
			return;
		}
		moduleList.add(module);
	}
	
	public void addModules(Collection<ClientModule> modules)
	{
		moduleList.addAll(modules);
	}
	
	public void addModuleBase(Collection<ClientModuleBase> modules)
	{
		if(modules != null && modules.size() > 0){
			moduleList.addAll(modules);
		}
		
	}
	
	public List<ClientModule> getModuleList()
	{
		return moduleList;
	}
	
	@Override
	protected void _serialize(JoyBuffer out)
	{
		out.putInt(commandId);
		switch (commandId) {
//			case Const.S_VERSION_CHECK_MAIN:
//				out.put((byte) 0);
//				break;
			default:
				out.putInt(moduleList.size());
				
				for(ClientModule resp:moduleList)
				{
					if(resp==null){
						logger.info("module is null");
						continue;
					} 
//					System.out.println("type="+resp.getModuleType());
					resp.serialize(out);
				}
				break;
		}
	}
	
	@Override
	protected void _deserialize(JoyBuffer arg0)
	{
		
	}
	
	@Override
	public String toString()
	{
		return Integer.toHexString(commandId) + ":" + moduleList;
	}
}
