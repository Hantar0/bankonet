package com.bankonet.presentation;

public class Launcher {

	public static void main(String[] args) {

		ConseillerApp app = new ConseillerApp();
		try {
			app.start(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
