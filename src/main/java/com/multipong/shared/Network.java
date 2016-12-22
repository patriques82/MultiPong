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
		kryo.register(Response.class);
		kryo.register(WaitForOthersResponse.class);
		kryo.register(GameIsFullResponse.class);
		kryo.register(WorldPropertiesResponse.class);
		kryo.register(Message.class);
		kryo.register(BallMessage.class);
		kryo.register(PaddleMessage.class);
		kryo.register(WallHitMessage.class);
	}

	/**
	 * Message from Client to Server when client wants to establish connection
	 */
	static public class RegisterRequest { }
	
	static public interface Response { }
	
	static public class WaitForOthersResponse implements Response { }

	static public class GameIsFullResponse implements Response { }
	
	static public class WorldPropertiesResponse implements Response {
		public int width, height;
		public BallMessage ball;
		public PaddleMessage other;
		public PaddleMessage your;
	}

	/**
	 * This represents the messages that describe the current game state
	 */
	static public abstract class Message {
		public int x, y, vx, vy;
	}

	static public class BallMessage extends Message {
		public int d;
	}
	
	static public class PaddleMessage extends Message {
		public String position;
		public int width, height;
	}

	static public class WallHitMessage extends Message {
		public String position;
	}

}