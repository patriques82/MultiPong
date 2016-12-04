package com.multipong.client;

public interface MessageSender<Message, T extends Sendable> {

	public void setSender(T sendable);

	/**
	 * Sets properties of the game object according to message spec
	 * @param m
	 */
	public abstract Message toMessage();
}
