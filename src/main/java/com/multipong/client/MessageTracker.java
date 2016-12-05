package com.multipong.client;

/**
 * Interface that tracks a game object on server.
 * @author patriknygren
 *
 * @param <Message> the message used to set the tracked game object
 * @param <T> the tracked game object
 */
public interface MessageTracker<Message> {
	
	public void init(Message m);
	
	/**
	 * Sets properties of the game object according to message spec
	 * @param m
	 */
	public void track(Message m);
}
