package com.joymeng.game.net.response;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class TestResp extends JoyResponse {

	String playerName;
	
	int playerId;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public TestResp() {
		super(ProcotolType.TEST_RESP);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		playerName = in.getPrefixedString();
		playerId = in.getInt();
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.putPrefixedString(playerName);
		out.putInt(playerId);
		
	}

}
