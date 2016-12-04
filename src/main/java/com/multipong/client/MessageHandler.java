package com.multipong.client;

/**
 * Generic Interface for handling messages received from Server. In order for messages 
 * to be interpreted correctly this interface must be implemented.
 * 
 * @author patriknygren
 * @param <Message> type parameter that specifies the specific message to handle
 */
public interface MessageHandler<Message> {
	
	/**
	 * Method that handler message in specified way.
	 * @param m
	 */
	public void handle(Message m);

}
