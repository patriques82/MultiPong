package com.multipong.client;

import java.awt.Color;

import com.multipong.shared.Network.PaddleProperties;

class OtherPaddle extends Paddle {

	OtherPaddle(PaddleProperties paddleProps) {
		super(paddleProps, Color.GREEN);
	}

}
