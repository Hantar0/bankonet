package com.bankonet.commands;

import com.bankonet.cache.CacheCompte;
import com.bankonet.cache.CacheClient;
import com.bankonet.dto.Client;
import com.bankonet.presentation.ManageAccount;

public class TransferCommand implements TheCommand {

	private ManageAccount manageAccount;
	private CacheClient cacheClient;
	private CacheCompte cacheCompte;
	private Client user;	

	public TransferCommand(ManageAccount manageAccount, CacheClient cacheClient, CacheCompte cacheCompte,
			Client user) {
		super();
		this.manageAccount = manageAccount;
		this.cacheClient = cacheClient;
		this.cacheCompte = cacheCompte;
		this.user = user;
	}

	@Override
	public int getId() {
		return 4;
	}

	@Override
	public String getLibelleMenu() {
		return "Effectuer un virement entre vos comptes";
	}

	@Override
	public void execute() {
		manageAccount.transfer(cacheClient, cacheCompte, user);
	}

}
