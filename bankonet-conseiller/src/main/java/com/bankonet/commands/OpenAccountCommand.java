package com.bankonet.commands;

import com.bankonet.presentation.BankOfficerService;

public class OpenAccountCommand implements TheCommand {

	private BankOfficerService bankOfficerService;
	
	public OpenAccountCommand(BankOfficerService bankOfficerService) {
		super();
		this.bankOfficerService = bankOfficerService;
	}

	@Override
	public int getId() {
		return 1;
	}

	@Override
	public String getLibelleMenu() {
		return "Ouvrir un compte";
	}

	@Override
	public void execute() {
		bankOfficerService.CreateClient();		
	}

}
