package com.multipong.client;

/**
 * Abstract class that tracks a game object on server.
 * @author patriknygren
 *
 * @param <Message> the message used to set the tracked game object
 * @param <T> the tracked game object
 */
public interface MessageTracker<Message, T extends GameObject> {
	
	/**
	 * Initializes the tracked object
	 * @param width
	 * @param height
	 * @param message
	 * @return
	 */
	public T init(int width, int height, Message message);

	/**
	 * Sets properties of the game object according to message spec
	 * @param m
	 */
	public void track(Message m);
	
}
