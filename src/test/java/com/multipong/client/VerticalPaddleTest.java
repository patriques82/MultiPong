package com.multipong.client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.multipong.shared.Network.BallProperties;
import com.multipong.shared.Network.PaddleProperties;

public class VerticalPaddleTest {

	private static ClientFacade clientMock = mock(ClientFacade.class); // dummy mock
	private static int WORLDWIDTH = 10;
	private static int WORLDHEIGHT = 10;
	private static int WIDTH = 5;
	private static int HEIGHT = 20;

	private Ball ball;
	private MyPaddle rightPaddle, leftPaddle;

	@Before
	public void setUp() throws Exception {
		BallProperties ballProps = new BallProperties();
		ballProps.diameter = 3;
		ball = new Ball(clientMock, WORLDWIDTH, WORLDHEIGHT, ballProps);
		PaddleProperties props = new PaddleProperties();
		props.height = HEIGHT;
		props.width = WIDTH;
		props.x = WORLDWIDTH - WIDTH; 
		props.y = WORLDHEIGHT/2 - HEIGHT/2;
		props.position = "right";
		rightPaddle = MyPaddle.getPaddle(clientMock, ball, props);
		props.x = 0;
		props.position = "left";
		leftPaddle = MyPaddle.getPaddle(clientMock, ball, props);
	}

	@After
	public void tearDown() throws Exception {
		ball = null;
		rightPaddle = null;
		leftPaddle = null;
	}
	
	@Test
	public void testRightPaddleBounce() {
		ball.setVx(1);
		ball.setPosition(rightPaddle.getX(), rightPaddle.getY());
		assertTrue(ball.collidesWith(rightPaddle));
		rightPaddle.tick();
		assertThat(ball.getVx(), is(equalTo(-1)));
	}

	@Test
	public void testLeftPaddleBounce() {
		ball.setVx(-1);
		ball.setPosition(leftPaddle.getX(), leftPaddle.getY());
		assertTrue(ball.collidesWith(leftPaddle));
		leftPaddle.tick();
		assertThat(ball.getVx(), is(equalTo(1)));
	}

}
