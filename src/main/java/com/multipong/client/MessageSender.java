package com.multipong.client;

public interface MessageSender<Message> {

	/**
	 * Sets properties of the game object according to message spec
	 * @param m
	 */
	public abstract Message toMessage();
}
