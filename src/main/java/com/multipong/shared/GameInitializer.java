package com.multipong.shared;

import com.multipong.shared.Network.PropMessage;

public interface GameInitializer {
	
	/**
	 * This is the first method called after connection.
	 * @param props
	 */
	public void initGame(PropMessage props);

}
