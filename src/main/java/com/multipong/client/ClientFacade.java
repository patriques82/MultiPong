package com.multipong.client;

import com.multipong.shared.Network.Message;

public interface ClientFacade {
	
	void connect();
	
	void scheduledSend();
	
	void emitEvent(Message m);
	
	World getWorld();
	
	Display getDisplay();

}
