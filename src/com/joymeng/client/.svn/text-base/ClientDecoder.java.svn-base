package com.joymeng.client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.services.core.message.JoyProtocol;

class ClientDecoder extends CumulativeProtocolDecoder
{

	static Logger logger = LoggerFactory.getLogger(ClientDecoder.class);
	
	/**
	 * ������
	 */
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception 
	{
		//����δ��������
		int remain = in.remaining();
		
		//��Э�鳤�ȱ�ʶ���Ҳ���
		if(remain < JoyProtocol.BYTE_LENGTH)
		{
			return false;
		}
		
		int basePos = in.position();
		int length = in.getInt(basePos + JoyProtocol.POS_LENGTH);
		
		//���峤�Ȳ���
		if(length > remain - JoyProtocol.BYTE_LENGTH)
		{
			return false;
		}
		else if(length >= JoyProtocol.MAX_PROTOCOL_LEN)//������ʱ������ð�
		{
			logger.error("received package length beyond 51200, this package is dropped:" + in.getHexDump());
			//���4���ֽڵİ��峤�ȱ�ʶ�Ͱ��峤��
			in.position(basePos + JoyProtocol.BYTE_LENGTH + length);
			return true;
		}
		
		byte[] dst = new byte[length + JoyProtocol.BYTE_LENGTH];
		
		in.get(dst, 0, length + JoyProtocol.BYTE_LENGTH);
		out.write(IoBuffer.wrap(dst));
		
		return true;
	}

}
