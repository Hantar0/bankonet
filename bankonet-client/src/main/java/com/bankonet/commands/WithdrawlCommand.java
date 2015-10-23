package com.bankonet.commands;

import com.bankonet.presentation.ManageAccount;

public class WithdrawlCommand implements TheCommand {
	
	private ManageAccount manageAccount;

	public WithdrawlCommand(ManageAccount manageAccount) {
		this.manageAccount = manageAccount;
	}
	
	@Override
	public int getId() {
		return 3;
	}

	@Override
	public String getLibelleMenu() {
		return "Effectuer un retrait";
	}

	@Override
	public void execute() {
		manageAccount.manage("withdrawl");
	}

}
