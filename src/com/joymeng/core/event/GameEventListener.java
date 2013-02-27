package com.joymeng.core.event;

import java.util.List;

import com.joymeng.game.net.client.ClientModule;

public interface GameEventListener {
	public List<ClientModule> handleEvent(GameEvent event);
}
