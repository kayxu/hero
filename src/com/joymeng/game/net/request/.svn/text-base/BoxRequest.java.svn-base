package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.domain.box.BoxConst;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class BoxRequest extends JoyRequest{
	
	byte cmd;
	
	int number;
	
	public byte getCmd() {
		return cmd;
	}

	public void setCmd(byte cmd) {
		this.cmd = cmd;
	}
	

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public BoxRequest() {
		super(ProcotolType.BOX_REQ);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		System.out.println("receive "+BoxRequest
				.class.getName());
		// todo
		cmd = in.get();
		switch (cmd) {
		case BoxConst.OPEN_BOX://打开宝箱
			break;
		case BoxConst.REFRESH_BOX://刷新宝箱
			break;
		case BoxConst.START://转动抽奖
			break;
		case BoxConst.RECEIVE_AWARD://领取奖励
			break;
		case BoxConst.BUY://购买钻石
			number = in.getInt();
			break;
		}
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(cmd);
		switch (cmd) {
		case BoxConst.OPEN_BOX:// 打开宝箱
			break;
		case BoxConst.REFRESH_BOX://刷新宝箱
			break;
		case BoxConst.START://转动抽奖
			break;
		case BoxConst.RECEIVE_AWARD://领取奖品
			break;
		case BoxConst.BUY://购买钻石
			out.putInt(number);
			break;
		}
	}

}
