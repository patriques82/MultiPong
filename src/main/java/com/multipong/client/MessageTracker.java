package com.multipong.client;

/**
 * Interface that tracks a game object on server.
 *
 * @param <Message> the message used to set the tracked game object
 */
public interface MessageTracker<Message> {
	
	/**
	 * Sets properties of the game object according to message spec
	 * @param m
	 */
	public void track(Message m);
}
