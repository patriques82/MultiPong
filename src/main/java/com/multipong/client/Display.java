package com.multipong.client;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * Singleton. Controls the view of the game
 * 
 * @author patriknygren
 *
 */
public class Display {
	
	private int width, height;
	private JFrame frame;
	private Canvas canvas;
	private static Display display;

	private Display(int w, int h) {
		width = w;
		height = h;

		frame = new JFrame();
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // center on screen

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false); // only frame can have focus

		frame.add(canvas);
		frame.pack();
	}
	
	static Display createDisplay(int w, int h) {
		if(display == null) {
			display = new Display(w, h);
		}
		return display;
	}

	Canvas getCanvas() {
		return canvas;
	}
	
	void addKeyListener(KeyListener l) {
		frame.addKeyListener(l);
	}

	int getWidth() {
		return width;
	}

	int getHeight() {
		return height;
	}

}
