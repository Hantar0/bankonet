package com.bankonet.commands;

import com.bankonet.constantes.TypeCompte;
import com.bankonet.presentation.BankOfficerService;

public class AddAccountCCommand implements TheCommand {

private BankOfficerService bankOfficerService;
	
	public AddAccountCCommand(BankOfficerService bankOfficerService) {
		super();
		this.bankOfficerService = bankOfficerService;
	}
	
	@Override
	public int getId() {
		return 3;
	}

	@Override
	public String getLibelleMenu() {
		return "Ajouter un compte courant";
	}

	@Override
	public void execute() {
		bankOfficerService.chooseClient(TypeCompte.CC,"createAccount");		
	}

}
