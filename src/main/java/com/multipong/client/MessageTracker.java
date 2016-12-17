package com.multipong.client;

import com.multipong.shared.Network.Message;

/**
 * Interface that tracks a game object on server.
 *
 * @param <Message> the message used to set the tracked game object
 */
public interface MessageTracker<M extends Message> {
	
	/**
	 * Sets properties of the game object according to message spec
	 * @param m
	 */
	public void trackMessage(M m);
}
