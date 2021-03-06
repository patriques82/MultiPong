package com.multipong.client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.multipong.shared.Network.Position;
import com.multipong.shared.Network.BallProperties;
import com.multipong.shared.Network.PaddleProperties;


public class HorizontalPaddleTest {

	private static ClientFacade clientMock = mock(ClientFacade.class); // dummy mock
	private static int WORLDWIDTH = 10;
	private static int WORLDHEIGHT = 10;
	private static int DIAMETER = 3;
	private static int WIDTH = 20;
	private static int HEIGHT = 5;

	private Ball ball;
	private MyPaddle bottomPaddle, upperPaddle;

	@Before
	public void setUp() throws Exception {
		BallProperties ballProps = new BallProperties();
		ballProps.diameter = DIAMETER;
		ball = new Ball(clientMock, WORLDWIDTH, WORLDHEIGHT, ballProps);
		PaddleProperties props = new PaddleProperties();
		props.height = HEIGHT;
		props.width = WIDTH;
		props.position = Position.BOTTOM;
		bottomPaddle = MyPaddle.getPaddle(clientMock, ball, props);
		props.position = Position.UP;
		upperPaddle = MyPaddle.getPaddle(clientMock, ball, props);
	}

	@After
	public void tearDown() throws Exception {
		ball = null;
		bottomPaddle = null;
		upperPaddle = null;
	}
	
	@Test
	public void testBottomPaddleBounce() {
		ball.setVy(1);
		ball.setPosition(bottomPaddle.getX(), bottomPaddle.getY());
		assertTrue(ball.collidesWith(bottomPaddle));
		bottomPaddle.tick();
		assertThat(ball.getVy(), is(equalTo(-1)));
	}

	@Test
	public void testUpperPaddleBounce() {
		ball.setVy(-1);
		ball.setPosition(upperPaddle.getX(), upperPaddle.getY());
		assertTrue(ball.collidesWith(upperPaddle));
		upperPaddle.tick();
		assertThat(ball.getVy(), is(equalTo(1)));
	}

}
