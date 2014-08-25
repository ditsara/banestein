package com.geekconx.mayadan.banestein;

import com.squareup.otto.Bus;

public class MainBus {
	private static final Bus BUS = new Bus();
	public static Bus getInstance() {
		return BUS;
	}
}
