package com.multipong.client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.PaddleMessage;

public class HorizontalPaddleTest {

	private static ClientFacade clientMock = mock(ClientFacade.class); // dummy mock
	private static int WORLDWIDTH = 10;
	private static int WORLDHEIGHT = 10;
	private static int WIDTH = 20;
	private static int HEIGHT = 5;

	private Ball ball;
	private MyPaddle bottomPaddle, upperPaddle;

	@Before
	public void setUp() throws Exception {
		BallMessage ballProps = new BallMessage();
		ballProps.d = 3;
		ball = new Ball(clientMock, WORLDWIDTH, WORLDHEIGHT, ballProps);
		PaddleMessage props = new PaddleMessage();
		props.height = HEIGHT;
		props.width = WIDTH;
		props.x = WORLDWIDTH/2 - WIDTH/2; 
		props.y = WORLDHEIGHT - HEIGHT;
		props.position = "bottom";
		bottomPaddle = MyPaddle.getPaddle(clientMock, WORLDWIDTH, WORLDHEIGHT, ball, props);
		props.y = 0;
		props.position = "up";
		upperPaddle = MyPaddle.getPaddle(clientMock, WORLDWIDTH, WORLDHEIGHT, ball, props);
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
