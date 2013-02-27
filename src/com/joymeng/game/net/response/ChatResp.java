package com.joymeng.game.net.response;

import com.joymeng.core.chat.Notice;
import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class ChatResp extends JoyResponse {
	Notice notice;
	public ChatResp() {
		super(ProcotolType.CHAT_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// todo
		out.putInt(notice.getPlayerId());
		out.putPrefixedString(notice.getName(),JoyBuffer.STRING_TYPE_SHORT);
		out.put(notice.getType());
		//头像，级别，爵位
		out.put(notice.getIcon());
		out.putShort(notice.getLevel());
		out.put(notice.getCityLevel());
		out.putPrefixedString(notice.getContent(),JoyBuffer.STRING_TYPE_SHORT);
		//接收方的id
		out.putInt(notice.getReceiveId());
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

}
