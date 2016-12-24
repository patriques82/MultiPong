package com.multipong.server;

import org.junit.Assert;
import org.junit.After;
import org.junit.Test;

import com.multipong.shared.Network.Message;
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
		Assert.assertFalse(manager.isFull());
		manager.add(new Client() {
			public void send(Message message) {}
		});
		Assert.assertFalse(manager.isFull());
		manager.add(new Client() {
			public void send(Message message) {}
		});
		Assert.assertFalse(manager.isFull());
		manager.add(new Client() {
			public void send(Message message) {}
		});
		Assert.assertTrue(manager.isFull());
	}
	
	@Test
	public void initGameTest() {
		manager = new ClientsManager(1);
		manager.add(new Client() {
			public void send(Message message) {
				Assert.assertTrue(message instanceof WorldProperties);
			}
		});
		manager.initGame();
	}

}
