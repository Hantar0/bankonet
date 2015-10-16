package com.bankonet.metier;

public class StopApp {
	SaveFiles saveFiles;	
	
	public StopApp(SaveFiles saveFiles) {
		this.saveFiles = saveFiles;
	}


	public void Stop() {
		System.out.println("Close BDD");
		System.out.println("Close Application");
		saveFiles.saveData();
		System.exit(0);
	}
}
