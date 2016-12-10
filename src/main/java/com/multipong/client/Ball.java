package com.multipong.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.WallHitMessage;

/**
 * Both tracks by following messages and calculates interpolation in between messages.
 */
public class Ball implements GameObject, MessageSender<BallMessage>, MessageTracker<BallMessage> {
	
	private ClientFacade clientFacade;
	private Point upperLeft; // upper left corner
	private int worldWidth, worldHeight;
	private int vx, vy; 	 // velocity (x, y)
	private int diameter;
	private Rectangle rect;
	
	private BallMessage ballMessage;
	private WallHitMessage wallHitMessage;
	
	Ball(ClientFacade facade, int worldWidth, int worldHeight, int diameter, int x, int y, int vx, int vy) {
		this.clientFacade = facade;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.diameter = diameter;
		this.upperLeft = new Point(x, y);
		this.vx = vx;
		this.vy = vy;
		rect = new Rectangle(upperLeft, new Dimension(diameter, diameter));
		ballMessage = new BallMessage();
		wallHitMessage = new WallHitMessage();
	}
	
	@Override
	public void tick() {
		upperLeft.x += vx;
		upperLeft.y += vy;
		if(upperWallCollision()) {
			bounceY();
		}
		if(verticalWallCollision()) {
			bounceX();
		}
		if(lowerWallCollision()) {
			wallHitMessage.x = upperLeft.x;
			wallHitMessage.y = upperLeft.y;
			clientFacade.emitEvent(wallHitMessage); // You lost the game!
			bounceY();
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillOval(upperLeft.x, upperLeft.y, diameter, diameter);
	}

	@Override
	public void setPosition(int x, int y) {
		upperLeft.x = x;
		upperLeft.y = y;
	}

	@Override
	public void setSpeed(int vx, int vy) {
		this.vx = vx;
		this.vy = vy;
	}

	private boolean upperWallCollision() {
		return upperLeft.y <= 0; // upper wall
	}
	
	private boolean verticalWallCollision() {
		return upperLeft.x <= 0 || (upperLeft.x + diameter) >= worldWidth; // left or right wall
	}
	
	private boolean lowerWallCollision() {
		return (upperLeft.y + diameter) >= worldHeight; // lower wall
	}
	
	private void bounceX() {
		vx *= -1;
	}

	private void bounceY() {
		vy *= -1;
	}

	@Override
	public Rectangle getBoundingRect() {
		return rect;
	}

	@Override
	public BallMessage toMessage() {
		ballMessage.x = upperLeft.x;
		ballMessage.y = upperLeft.y;
		ballMessage.vx = vx;
		ballMessage.vy = vy;
		return ballMessage;
	}

	@Override
	public void track(BallMessage m) {
		// TODO: implement me!!!!
	}

}
