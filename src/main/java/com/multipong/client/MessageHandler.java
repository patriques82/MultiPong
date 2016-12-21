package com.multipong.client;

import com.multipong.shared.Network.Message;

/**
 * Interface for objects that tracks/sends an object on/to server.
 *
 * @param <Message> the message used to set the tracked game object
 */
public interface MessageHandler<M extends Message> {

	/**
	 * Sets properties of the game object according to message spec.
	 * @param m
	 */
	public M toMessage();

	/**
	 * Sets properties of the game object according to message spec
	 * @param m
	 */
	public void trackMessage(M m);

}
