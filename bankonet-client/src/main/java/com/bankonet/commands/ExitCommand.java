package com.bankonet.commands;

import com.bankonet.metier.StopApp;

public class ExitCommand implements TheCommand{
	StopApp stopApp;

	public ExitCommand(StopApp stopApp) {
		this.stopApp = stopApp;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public String getLibelleMenu() {
		return "Arrêter le programme";
	}

	@Override
	public void execute() {
		stopApp.Stop();
	}

}
