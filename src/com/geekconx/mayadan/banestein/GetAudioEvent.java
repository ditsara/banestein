package com.geekconx.mayadan.banestein;

import java.io.File;

public class GetAudioEvent {
	private File result;

	public GetAudioEvent(File result) {
		this.result = result;
	}

	public File getResult() {
		return result;
	}
}
