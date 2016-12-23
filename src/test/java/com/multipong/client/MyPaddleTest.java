package com.multipong.client;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.multipong.shared.Network.PaddleMessage;

public class MyPaddleTest {

	@Test
	public void testToMessage() {
		PaddleMessage props = new PaddleMessage();
		props.position = "up";
		props.width = 10;
		props.height = 3;
		props.x = 5;
		props.y = 6;
		MyPaddle paddle = MyPaddle.getPaddle(null, 0, 0, null, props);
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
		PaddleMessage props = new PaddleMessage();
		props.position = "unknown position";
		IllegalArgumentException exception = null;
		try {
			MyPaddle.getPaddle(null, 0, 0, null, props);
		} catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), is(equalTo("Unknown paddle position")));
			exception = e;
		}
		assertNotNull(exception);
	}

}
