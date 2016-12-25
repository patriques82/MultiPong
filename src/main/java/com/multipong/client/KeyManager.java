package com.multipong.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Manages the input controls, and records keystrokes
 * 
 * @author patriknygren
 *
 */
class KeyManager implements KeyListener {

	private static boolean[] ARROW_KEYS = new boolean[4];
	private static int UP = 0;
	private static int DOWN = 1;
	private static int LEFT = 2;
	private static int RIGHT = 3;
	
	private static KeyManager mngr;
	
	private KeyManager() {
	}
	
	static KeyManager getKeyManager() {
		if(mngr == null) {
			mngr = new KeyManager();
		}
		return mngr;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		setKeyPressed(e.getKeyCode(), true);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		setKeyPressed(e.getKeyCode(), false);
	}
	
	private void setKeyPressed(int keyCode, boolean pressed) {
		if(keyCode == KeyEvent.VK_UP) {
			ARROW_KEYS[UP] = pressed;
		}
		if(keyCode == KeyEvent.VK_DOWN) {
			ARROW_KEYS[DOWN] = pressed;
		}
		if(keyCode == KeyEvent.VK_LEFT) {
			ARROW_KEYS[LEFT] = pressed;
		}
		if(keyCode == KeyEvent.VK_RIGHT) {
			ARROW_KEYS[RIGHT] = pressed;
		}
	}

	boolean isLeftPressed() {
		return ARROW_KEYS[LEFT];
	}

	boolean isRightPressed() {
		return ARROW_KEYS[RIGHT];
	}

	boolean isUpPressed() {
		return ARROW_KEYS[UP];
	}

	boolean isDownPressed() {
		return ARROW_KEYS[DOWN];
	}

}
