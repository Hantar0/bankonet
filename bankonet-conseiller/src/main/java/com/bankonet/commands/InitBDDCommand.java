package com.bankonet.commands;

import com.bankonet.metier.client.InitService;

public class InitBDDCommand implements TheCommand {

	private InitService initService;

	public InitBDDCommand(InitService initService) {
		super();
		this.initService = initService;
	}

	@Override
	public int getId() {
		return 5;
	}

	@Override
	public String getLibelleMenu() {
		return "Initialiser la BDD";
	}

	@Override
	public void execute() {
		initService.init(); 		
	}

}
