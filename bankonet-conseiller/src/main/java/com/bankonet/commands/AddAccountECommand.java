package com.bankonet.commands;

import com.bankonet.constantes.TypeCompte;
import com.bankonet.presentation.BankOfficerService;

public class AddAccountECommand implements TheCommand {

private BankOfficerService bankOfficerService;
	
	public AddAccountECommand(BankOfficerService bankOfficerService) {
		super();
		this.bankOfficerService = bankOfficerService;
	}
	
	@Override
	public int getId() {
		return 4;
	}

	@Override
	public String getLibelleMenu() {
		return "Ajouter un compte épargne";
	}

	@Override
	public void execute() {
		bankOfficerService.chooseClient(TypeCompte.CE,"createAccount");		
	}

}
