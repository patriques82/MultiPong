package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.WallHitMessage;

/**
 * The Ball game object. Both tracks, by following messages, and calculates interpolation in between messages.
 * Sends wall hit events.
 */
public class Ball implements GameObject, MessageSender<BallMessage>, MessageTracker<BallMessage> {
	
	private ClientFacade clientFacade;
	private int worldWidth, worldHeight;
	private int vx, vy; 	 // velocity (x, y)
	private Rectangle rect;
	
	private BallMessage ballMessage;
	private WallHitMessage wallHitMessage;
	
	Ball(ClientFacade facade, int worldWidth, int worldHeight, BallMessage ballProps) {
		this.clientFacade = facade;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.vx = ballProps.vx;
		this.vy = ballProps.vy;
		rect = new Rectangle(ballProps.x, ballProps.y, ballProps.d, ballProps.d); 
		ballMessage = new BallMessage();
		wallHitMessage = new WallHitMessage();
	}

	@Override
	public void tick() {
		setPosition(rect.x + vx, rect.y + vy);
		if(upperWallCollision()) {
			bounceY();
		}
		if(verticalWallCollision()) {
			bounceX();
		}
		if(lowerWallCollision()) {
			wallHitMessage.x = rect.x;
			wallHitMessage.y = rect.y;
			clientFacade.emitEvent(wallHitMessage); // You lost the game!
			bounceY();
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillOval(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	public void setPosition(int x, int y) {
		rect.setLocation(x, y);
	}

	@Override
	public void setSpeed(int vx, int vy) {
		this.vx = vx;
		this.vy = vy;
	}

	private boolean upperWallCollision() {
		return rect.y <= 0; // upper wall
	}
	
	private boolean verticalWallCollision() {
		return rect.x <= 0 || (rect.x + rect.width) >= worldWidth; // left or right wall
	}
	
	private boolean lowerWallCollision() {
		return (rect.y + rect.height) >= worldHeight; // lower wall
	}
	
	public void bounceX() {
		vx *= -1;
	}

	public void bounceY() {
		vy *= -1;
	}

	public Rectangle getBoundingRect() {
		return rect;
	}

	@Override
	public BallMessage toMessage() {
		ballMessage.x = rect.x;
		ballMessage.y = rect.y;
		ballMessage.vx = vx;
		ballMessage.vy = vy;
		return ballMessage;
	}

	@Override
	public void track(BallMessage m) {
		// TODO: implement me!!!!
	}

}
