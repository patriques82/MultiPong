package com.multipong.server;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.multipong.shared.Network.*;

public class MessageFactoryTest {

	@Before
	public void setUp() throws Exception {
		MessageFactory.init();
	}
	
	
	@Test
	public void testGameIsFull() {
		assertTrue(MessageFactory.gameIsFull() instanceof GameIsFull);
	}

	@Test
	public void testWaitForOthers() {
		assertTrue(MessageFactory.waitForOthers() instanceof WaitForOthers);
	}

	@Test
	public void testInvalidPosition() {
		IllegalArgumentException exception = null;
		try {
			MessageFactory.worldProperties("unknown position");
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is(equalTo("Unknown paddle position")));
			exception = e;
		}
		assertNotNull(exception);
	}

	@Test
	public void testWorldProps() {
		WorldProperties props = MessageFactory.worldProperties("left");
		assertEquals(props.width, MessageFactory.WORLD_WIDTH);
		assertEquals(props.height, MessageFactory.WORLD_HEIGHT);
	}

	@Test
	public void testBallProperties() {
		WorldProperties props = MessageFactory.worldProperties("left");
		assertTrue(props.ballProps instanceof BallProperties);
		assertThat(props.ballProps.vx + props.ballProps.vy, is(equalTo(MessageFactory.BALL_SPEED)));
	}

	@Test
	public void testOtherPaddleProperties() {
		WorldProperties props = MessageFactory.worldProperties("left");
		assertTrue(props.other instanceof PaddleProperties);
		assertThat(props.other.position, is(equalTo("right")));
		assertThat(props.other.range, is(equalTo(MessageFactory.WORLD_HEIGHT)));
		int x = MessageFactory.WORLD_WIDTH - MessageFactory.PADDLE_THICKNESS;
		int y = MessageFactory.WORLD_HEIGHT/2 - MessageFactory.PADDLE_LENGTH/2;
		assertThat(props.other.x, is(equalTo(x)));
		assertThat(props.other.y, is(equalTo(y)));
		assertThat(props.other.height, is(equalTo(MessageFactory.PADDLE_LENGTH)));
		assertThat(props.other.width, is(equalTo(MessageFactory.PADDLE_THICKNESS)));
	}

	@Test
	public void testYourPaddleProperties() {
		WorldProperties props = MessageFactory.worldProperties("bottom");
		assertTrue(props.your instanceof PaddleProperties);
		assertThat(props.your.position, is(equalTo("bottom")));
		assertThat(props.your.range, is(equalTo(MessageFactory.WORLD_WIDTH)));
		int x = MessageFactory.WORLD_WIDTH/2 - MessageFactory.PADDLE_LENGTH/2;
		int y = MessageFactory.WORLD_HEIGHT - MessageFactory.PADDLE_THICKNESS;
		assertThat(props.your.x, is(equalTo(x)));
		assertThat(props.your.y, is(equalTo(y)));
		assertThat(props.your.height, is(equalTo(MessageFactory.PADDLE_THICKNESS)));
		assertThat(props.your.width, is(equalTo(MessageFactory.PADDLE_LENGTH)));
	}

}

