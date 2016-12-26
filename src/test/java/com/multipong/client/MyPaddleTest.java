package com.multipong.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.multipong.shared.Network.PaddleMessage;
import com.multipong.shared.Network.PaddleProperties;

public class MyPaddleTest {

	private static int WIDTH = 10;
	private static int HEIGHT = 3;

	@Test
	public void testToMessage() {
		PaddleProperties props = new PaddleProperties();
		props.position = "up";
		props.width = WIDTH;
		props.height = HEIGHT;
		props.x = 5;
		props.y = 6;
		MyPaddle paddle = MyPaddle.getPaddle(null, null, props);
		paddle.setVx(7);
		paddle.setVy(8);
		PaddleMessage mess = paddle.toMessage();
		assertThat(mess.position, is(equalTo("up")));
		assertThat(mess.x, is(equalTo(5)));
		assertThat(mess.y, is(equalTo(6)));
		assertThat(mess.vx, is(equalTo(7)));
		assertThat(mess.vy, is(equalTo(8)));
	}

	@Test
	public void testIllegalArgumentException() {
		PaddleProperties props = new PaddleProperties();
		props.position = "unknown position";
		IllegalArgumentException exception = null;
		try {
			MyPaddle.getPaddle(null, null, props);
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is(equalTo("Unknown paddle position")));
			exception = e;
		}
		assertNotNull(exception);
	}

}
