package com.multipong.shared;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {

//	public final static String HOST = "138.68.66.77";
	public final static String HOST = "localhost";

	public final static int CONNECT_TIMEOUT_MS = 5000;
	
	public final static int TCP_PORT = 54555;
	public final static int UDP_PORT = 54777;

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(RegisterRequest.class);
		kryo.register(PropMessage.class);
		kryo.register(BallMessage.class);
		kryo.register(PaddleMessage.class);
	}
	
	static public interface Message {}
	/**
	 * Message from Client to Server when client wants to establish connection
	 */
	static public class RegisterRequest implements Message {
	}
	
	static public class PropMessage implements Message {
		public int width, height;
		public BallMessage ball;
		public PaddleMessage other;
		public PaddleMessage your;
	}

	static public class BallMessage implements Message {
		public int d, x, y, vx, vy;
	}
	
	static public class PaddleMessage implements Message {
		public String position;
		public int x, y, width, height, vx, vy;
	}

	

}