package com.multipong.client;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.multipong.shared.Network.BallMessage;

public class BallTest {
	
	ClientFacade clientMock = mock(ClientFacade.class);
	BallMessage props;
	Ball ball;

	@Before
	public void setUp() throws Exception {
		props = new BallMessage();
		ball = new Ball(clientMock, 20, 20, props);
	}

	@After
	public void tearDown() throws Exception {
		props = null;
		ball = null;
	}

	@Test
	public void testTick() {
		ball.setPosition(5, 5);
		ball.setSpeed(2, 2);
		ball.tick();
//		assertThat(ball.x);
	}

}
