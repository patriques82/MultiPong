package com.multipong.client;

import com.multipong.shared.Network.Message;

public interface MessageSender<M extends Message> {

	/**
	 * Sets properties of the game object according to message spec.
	 * @param m
	 */
	public M toMessage();
}
