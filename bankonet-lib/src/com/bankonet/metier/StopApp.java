package com.bankonet.metier;

public class StopApp {
	SyncDataService saveFiles;	
	
	public StopApp(SyncDataService saveFiles) {
		this.saveFiles = saveFiles;
	}


	public void Stop() {
		System.out.println("Close BDD");
		System.out.println("Close Application");
		saveFiles.sync();
		System.exit(0);
	}
}
