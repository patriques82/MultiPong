package com.multipong.client;

import com.multipong.shared.Network.Message;

/**
 * Interface for objects that tracks/sends an object on/to server.
 *
 * @param <Message> the message used to set the tracked game object
 */
public interface MessageHandler<M extends Message> {

	/**
	 * Game object properties to send message.
	 * @return message
	 */
	public M toMessage();

	/**
	 * Sets game object according to received message.
	 * @param message
	 */
	public void trackMessage(M m);

}
