package com.multipong.client;

import java.awt.Point;

/**
 * Interface for objects that can be converted to Messages, in order to keep
 * Server and Rest of players in sync.
 * 
 * @author patriknygren
 * @param <Message>
 */
public interface Sendable {

	public String getPosition();	
	
	public Point getPoint();
	
	public int getVx();
	
	public int getVy();
	
}
