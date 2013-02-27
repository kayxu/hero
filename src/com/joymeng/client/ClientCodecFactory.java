package com.joymeng.client;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;



/**
 * Ĭ�ϵ�MINA���빤��
 * @author ShaoLong Wang
 *
 */
public class ClientCodecFactory implements ProtocolCodecFactory
{
	/**
	 * ������
	 */
	private ProtocolDecoder decoder;
	
	/**
	 * ������
	 */
	private ProtocolEncoder encoder;
	
	
	public ClientCodecFactory()
	{
		decoder = new ClientDecoder();
		encoder = new ClientEncoder();
	}
	
	
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception
	{
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception
	{
		return encoder;
	}

}
