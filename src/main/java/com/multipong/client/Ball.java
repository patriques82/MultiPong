package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.multipong.shared.Network.BallHitMessage;
import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.WallHitMessage;

public class Ball extends GameObject implements MessageHandler<BallHitMessage> {

	private ClientFacade clientFacade;
	private int worldWidth, worldHeight;
	
	private BallHitMessage ballHit;
	private WallHitMessage wallHitMessage;
	
	Ball(ClientFacade facade, int worldWidth, int worldHeight, BallMessage ballProps) {
		super(ballProps.x, ballProps.y, ballProps.d, ballProps.d); 
		setSpeed(ballProps.vx, ballProps.vy);
		this.clientFacade = facade;
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		ballHit = new BallHitMessage();
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
	public BallHitMessage toMessage() {
		ballHit.x = getX();
		ballHit.y = getY();
		ballHit.vx = getVx();
		ballHit.vy = getVy();
		return ballHit;
	}

	@Override
	public void trackMessage(BallHitMessage message) {
		setPosition(message.x, message.y);
		setSpeed(message.vx, message.vy);
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
