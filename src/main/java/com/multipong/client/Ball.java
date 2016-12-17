package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.WallHitMessage;

public class Ball extends GameObject implements MessageSender<BallMessage>, MessageTracker<BallMessage> {
	private ClientFacade clientFacade;
	private int worldWidth, worldHeight;
	
	private BallMessage ballMessage;
	private WallHitMessage wallHitMessage;
	
	Ball(ClientFacade facade, int worldWidth, int worldHeight, BallMessage ballProps) {
		super(ballProps.x, ballProps.y, ballProps.d, ballProps.d); 
		setSpeed(ballProps.vx, ballProps.vy);
		this.clientFacade = facade;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		ballMessage = new BallMessage();
		wallHitMessage = new WallHitMessage();
	}

	@Override
	public void tick() {
		setPosition(getX() + getVx(), getY() + getVy());
		if(upperWallCollision()) {
			bounceY();
		}
		if(verticalWallCollision()) {
			bounceX();
		}
		if(lowerWallCollision()) {
			wallHitMessage.x = getX();
			wallHitMessage.y = getY();
			clientFacade.emitEvent(wallHitMessage); // You lost the game!
			bounceY();
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillOval(getX(), getY(), getWidth(), getHeight());
	}

	public void bounceX() {
		setVx(getVx() * -1);
	}

	public void bounceY() {
		setVy(getVy() * -1);
	}

	@Override
	public BallMessage toMessage() {
		ballMessage.x = getX();
		ballMessage.y = getY();
		ballMessage.vx = getVx();
		ballMessage.vy = getVy();
		return ballMessage;
	}

	@Override
	public void trackMessage(BallMessage m) {
		setPosition(m.x, m.y);
		setSpeed(m.vx, m.vy);
	}

	private boolean upperWallCollision() {
		return getY() <= 0;
	}
	
	private boolean verticalWallCollision() {
		return leftWallCollision() || rightWallCollision();
	}
	
	private boolean rightWallCollision() {
		return (getX() + getWidth()) >= worldWidth;
	}

	private boolean leftWallCollision() {
		return getX() <= 0;
	}
	
	private boolean lowerWallCollision() {
		return (getY() + getHeight()) >= worldHeight;
	}
	

}
