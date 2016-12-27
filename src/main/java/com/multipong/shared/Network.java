package com.multipong.shared;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {

	public final static String HOST = "138.68.66.77";
//	public final static String HOST = "localhost";

	public final static int CONNECT_TIMEOUT_MS = 5000;
	
	public final static int TCP_PORT = 54555;
	public final static int UDP_PORT = 54777;

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Message.class);
		kryo.register(RegisterRequest.class);
		kryo.register(WaitForOthers.class);
		kryo.register(GameIsFull.class);
		kryo.register(WorldProperties.class);
		kryo.register(BallProperties.class);
		kryo.register(PaddleProperties.class);
		kryo.register(ObjectMessage.class);
		kryo.register(Message.class);
		kryo.register(BallMessage.class);
		kryo.register(PaddleMessage.class);
		kryo.register(BallHitMessage.class);
		kryo.register(WallHitMessage.class);
	}

	static public interface Message { }

	/**
	 * Message from Client to Server when client wants to establish connection
	 */
	static public class RegisterRequest implements Message { }
	
	static public class WaitForOthers implements Message { 
		public int total, waiting;
	}

	static public class GameIsFull implements Message {
		public int players;
	}
	
	static public class WorldProperties implements Message {
		public int width, height;
		public BallProperties ballProps;
		public PaddleProperties other;
		public PaddleProperties your;
	}

	static public class BallProperties implements Message {
		public int diameter, vx, vy;
	}
	
	static public class PaddleProperties implements Message {
		public String position;
		public int range, width, height, x, y;
	}

	/**
	 * This represents the messages that describe the current game state
	 */
	static public abstract class ObjectMessage implements Message {
		public int x, y, vx, vy;
	}

	static public class BallMessage extends ObjectMessage { }
	
	static public class PaddleMessage extends ObjectMessage {
		public String position;
	}
	
	static public class BallHitMessage extends ObjectMessage {
		public String position;
	}

	static public class WallHitMessage extends ObjectMessage {
		public String position;
	}

}