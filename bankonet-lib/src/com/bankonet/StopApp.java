package com.bankonet;

public class StopApp {
	public static void Stop() {
		System.out.println("Close BDD");
		System.out.println("Close Application");
		gestionFichier.saveData();
		System.exit(0);
	}
}
