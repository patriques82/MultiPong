package com.multipong.server;

import java.util.Random;

import com.esotericsoftware.kryonet.Connection;
import com.multipong.shared.Network.*;

public class MessageBus {
	
	public Message response(Object request) {
		return null;
	}
	
	public void registerClient(Connection conn) {
		RandPythagorean randPythagorean = new RandPythagorean(Conf.BALL_SPEED);
		WorldProperties prop = new WorldProperties();
		// World
		prop.width = Conf.WIDTH;
		prop.height = Conf.HEIGHT;
		// Ball
		prop.ball = new BallMessage();
		prop.ball.d = Conf.BALL_DIAMETER;
		prop.ball.x = Conf.BALL_START_X;
		prop.ball.y = Conf.BALL_START_Y;
		prop.ball.vx = randPythagorean.getX(); // set random speed-x
		prop.ball.vy = randPythagorean.getY(); // set random speed-y
		// other
		prop.other = new PaddleMessage();
		prop.other.position = "up";
		prop.other.height = Conf.PADDLE_THICKNESS;
		prop.other.width = Conf.PADDLE_LENGTH;
		// your
		prop.your = new PaddleMessage();
		prop.your.position = "bottom";
		prop.your.height = Conf.PADDLE_THICKNESS;
		prop.your.width = Conf.PADDLE_LENGTH;
		prop.your.x = Conf.PADDLE_BOTTOM_UPPER_LEFT_X;
		prop.your.y = Conf.PADDLE_BOTTOM_UPPER_LEFT_Y;
		conn.sendTCP(prop);
	}
	
	/**
	 * Used to calculate random vx and vy for a specific speed
	 */
	private static class RandPythagorean {
		private int x, y;
		public RandPythagorean(int hypotenuse) {
			Random rand = new Random();
			x = rand.nextInt((hypotenuse - 0) + 1);
			y = hypotenuse - x;
		}
		int getX() {
			return x;
		}
		int getY() {
			return y;
		}
	}
	
}
