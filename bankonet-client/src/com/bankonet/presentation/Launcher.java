package com.bankonet.presentation;

public class Launcher {

	public static void main(String[] args) {

		ClientApp app = new ClientApp();
		try {
			app.start(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
