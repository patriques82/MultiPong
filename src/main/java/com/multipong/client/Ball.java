package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

public class Ball implements GameObject {
	
	private Point upperLeft; // upper left corner
//	private double angle; 	 // radians
	private int vx, vy; 	 // velocity (x, y)
	private int diameter;

	public Ball(int worldWidth, int worldHeight, int diameter) {
		upperLeft = getUpperLeft(worldWidth, worldHeight);
		vx = ThreadLocalRandom.current().nextInt(-5, 6);
		vy = ThreadLocalRandom.current().nextInt(-5, 6);
		this.diameter = diameter;
	}

	@Override
	public void tick() {
		upperLeft.x += vx;
		upperLeft.y += vy;
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.WHITE);
		g2d.fillOval(upperLeft.x, upperLeft.y, diameter, diameter);
	}

	@Override
	public Point getUpperLeft(int worldWidth, int worldHeight) {
		return new Point(worldWidth/2, worldHeight/2);
	}
	
}
