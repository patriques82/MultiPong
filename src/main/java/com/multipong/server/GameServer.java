package com.multipong.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import com.multipong.shared.Network;
import com.multipong.shared.Network.*;

public class GameServer extends Server {
	
	private Server server;

	public static void main (String[] args) throws IOException {
		GameServer server = new GameServer();
		server.start();
	}
	
	public GameServer() throws IOException {
		server = new Server();
		server.start();
		Network.register(server);
		server.bind(Network.TCP_PORT, Network.UDP_PORT);
		
		server.addListener(new Listener() {
			public void received (Connection conn, Object object) {
				// If client wants to register send game properties
				if (object instanceof Register) {
		            conn.sendTCP(getProperties());
				}

			}
		});
	}
	
	private PropMessage getProperties() {
		PropMessage prop = new PropMessage();
		// World
		prop.width = Conf.WIDTH;
		prop.height = Conf.HEIGHT;
		// Ball
		prop.ball = new BallMessage();
		prop.ball.x = Conf.BALL_START_X;
		prop.ball.y = Conf.BALL_START_Y;
		prop.ball.diameter = Conf.BALL_DIAMETER;
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
		prop.your.x = 40;
		prop.your.y = 40;
		return prop;
	}
	

}
