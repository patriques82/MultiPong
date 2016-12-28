package com.multipong.server;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Test;

import com.multipong.shared.Network.BallHitMessage;
import com.multipong.shared.Network.Message;
import com.multipong.shared.Network.Position;
import com.multipong.shared.Network.WorldProperties;


public class ClientsManagerTest {
	
	ClientsManager manager;

	@After
	public void tearDown() throws Exception {
		manager = null;
	}

	@Test
	public void addTest() {
		manager = new ClientsManager(4);
		manager.add(new Client() {
			public void send(Message message) {}
		});
		assertFalse(manager.isFull());
		manager.add(new Client() {
			public void send(Message message) {}
		});
		assertFalse(manager.isFull());
		manager.add(new Client() {
			public void send(Message message) {}
		});
		assertFalse(manager.isFull());
		manager.add(new Client() {
			public void send(Message message) {}
		});
		assertTrue(manager.isFull());
	}
	
	@Test
	public void initGameTest() {
		manager = new ClientsManager(1);
		manager.add(new Client() {
			public void send(Message message) {
				assertTrue(message instanceof WorldProperties);
				if(message instanceof WorldProperties) {
					WorldProperties props = (WorldProperties) message;
					assertThat(props.your.position, is(equalTo(Position.RIGHT)));
					assertThat(props.other.position, is(equalTo(Position.LEFT)));
				}
			}
		});
		manager.initGame();
	}

	@Test
	public void sendToAllExceptTest() {
		BallHitMessage ballMess = new BallHitMessage();
		ballMess.position = Position.RIGHT;
		manager = new ClientsManager(2);
		manager.add(new Client() {  // right
			public void send(Message message) {
				assertNull(message);
			}
		});
		manager.add(new Client() { 	// left
			public void send(Message message) {
				assertTrue(message instanceof BallHitMessage);
				if(message instanceof BallHitMessage) {
					assertThat(((BallHitMessage) message).position, is(equalTo(Position.RIGHT)));
				}
			}
		});
		manager.sendToAllExcept(ballMess, Position.RIGHT);
	}

}
