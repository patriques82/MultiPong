package com.multipong.client;

import com.multipong.shared.Network.Message;

public interface ClientFacade {
	
	void connect();
	
	/**
	 * Send message, called by Game at scheduled times
	 */
	void send();
	
	/**
	 * Message to send on event
	 * @param m
	 */
	void emitEvent(Message m);
	
	World getWorld();
	
	Display getDisplay();

}
