package com.multipong.client;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.multipong.shared.Network.BallHitMessage;
import com.multipong.shared.Network.BallProperties;

public class BallTest {
	
	private static ClientFacade clientMock = mock(ClientFacade.class); // dummy mock
	private static int WORLDWIDTH = 10;
	private static int WORLDHEIGHT = 10;
	private Ball ball;

	@Before
	public void setUp() throws Exception {
		ball = new Ball(clientMock, WORLDWIDTH, WORLDHEIGHT, new BallProperties());
	}

	@After
	public void tearDown() throws Exception {
		ball = null;
	}

	@Test
	public void testTickNormal() {
		ball.setPosition(0, 1);
		ball.setSpeed(2, 2);
		ball.tick();
		assertThat(ball.getX(), is(equalTo(2)));
		assertThat(ball.getY(), is(equalTo(3)));
		ball.tick();
		assertThat(ball.getX(), is(equalTo(4)));
		assertThat(ball.getY(), is(equalTo(5)));
	}

	@Test
	public void testTickUpperWallCollison() {
		ball.setPosition(1, 1);
		ball.setSpeed(0, -1); // vx, vy
		ball.tick(); // collides with wall
		assertThat(ball.getX(), is(equalTo(1)));
		assertThat(ball.getY(), is(equalTo(0)));
		assertThat(ball.getVy(), is(equalTo(1))); // new speed
		ball.tick();
		assertThat(ball.getX(), is(equalTo(1)));
		assertThat(ball.getY(), is(equalTo(1)));
	}
	
	@Test
	public void testTickLowerWallCollison() {
		ball.setPosition(0, 9);
		ball.setSpeed(0, 1); // vx, vy
		ball.tick(); // collides with wall
		assertThat(ball.getX(), is(equalTo(0)));
		assertThat(ball.getY(), is(equalTo(WORLDHEIGHT)));
		assertThat(ball.getVy(), is(equalTo(-1))); // new speed
		ball.tick();
		assertThat(ball.getX(), is(equalTo(0)));
		assertThat(ball.getY(), is(equalTo(9)));
	}
	
	@Test
	public void testTickLeftWallCollison() {
		ball.setPosition(1, 0);
		ball.setSpeed(-1, 0); // vx, vy
		ball.tick(); // collides with wall
		assertThat(ball.getX(), is(equalTo(0)));
		assertThat(ball.getVx(), is(equalTo(1))); // new speed
		assertThat(ball.getY(), is(equalTo(0)));
		ball.tick();
		assertThat(ball.getX(), is(equalTo(1)));
		assertThat(ball.getY(), is(equalTo(0)));
	}

	@Test
	public void testTickRightWallCollison() {
		ball.setPosition(9, 0);
		ball.setSpeed(1, 0); // vx, vy
		ball.tick(); // collides with wall
		assertThat(ball.getX(), is(equalTo(WORLDWIDTH)));
		assertThat(ball.getVx(), is(equalTo(-1))); // new speed
		assertThat(ball.getY(), is(equalTo(0)));
		ball.tick();
		assertThat(ball.getX(), is(equalTo(9)));
		assertThat(ball.getY(), is(equalTo(0)));
	}
	
	@Test
	public void testToMessage() {
		BallHitMessage mess = ball.toMessage();
		assertThat(mess.x, is(equalTo(ball.getX())));
		assertThat(mess.y, is(equalTo(ball.getY())));
		assertThat(mess.vx, is(equalTo(ball.getVx())));
		assertThat(mess.vy, is(equalTo(ball.getVy())));
	}
	
	@Test
	public void testTrackMessage() {
		BallHitMessage mess = new BallHitMessage();
		mess.x = 11;
		mess.y = 11;
		mess.vx = 5;
		mess.vy = 5;
		ball.trackMessage(mess);
		assertThat(ball.getX(), is(equalTo(mess.x)));
		assertThat(ball.getY(), is(equalTo(mess.y)));
		assertThat(ball.getVx(), is(equalTo(mess.vx)));
		assertThat(ball.getVy(), is(equalTo(mess.vy)));
	}

}
