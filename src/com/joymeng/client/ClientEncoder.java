package com.joymeng.client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.exception.JoyException;
import com.joymeng.services.core.message.JoyProtocol;



class ClientEncoder extends ProtocolEncoderAdapter
{

	static Logger logger = LoggerFactory.getLogger(ClientEncoder.class);
	
	
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception
	{
		
		if(message instanceof JoyProtocol == false)
		{
			throw new JoyException(message.getClass().getName());
		}
		
		JoyProtocol joyMessage = (JoyProtocol)message;
		
		if (joyMessage.getMessageID() < 0)
		{
			logger.error(message.getClass().getName() + "'s msgCode should larger than 0");
			return;
		}
	    
	    
		JoyBuffer joyBuffer = JoyBuffer.allocate(256);
		joyMessage.serialize(joyBuffer);
		
		
	    int length = joyBuffer.getInt(JoyProtocol.POS_LENGTH);
		if(length >= JoyProtocol.MAX_PROTOCOL_LEN)
		{
			
			logger.error("send package length beyond 51200, this package is dropped");
			return;
		}
		
		byte[] buffer = joyBuffer.arrayToPosition();
		out.write(IoBuffer.wrap(buffer));
	}

}
