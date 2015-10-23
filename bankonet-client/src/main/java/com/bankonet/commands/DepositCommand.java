package com.bankonet.commands;

import com.bankonet.presentation.ManageAccount;

public class DepositCommand implements TheCommand {

	private ManageAccount manageAccount;

	public DepositCommand(ManageAccount manageAccount) {
		this.manageAccount = manageAccount;
	}

	@Override
	public int getId() {
		return 2;
	}

	@Override
	public String getLibelleMenu() {
		return "Effectuer un dépôt";
	}

	@Override
	public void execute() {
		manageAccount.manage("deposit");
	}

}
