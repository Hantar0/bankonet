package com.bankonet.commands;

import com.bankonet.dto.Client;
import com.bankonet.metier.client.FindService;
import com.bankonet.presentation.RecupKeyEntry;

public class ChangeClientName implements TheCommand {

	private RecupKeyEntry keyEntry;
	private FindService findService;

	public ChangeClientName(RecupKeyEntry keyEntry, FindService findService) {
		super();
		this.keyEntry = keyEntry;
		this.findService = findService;
	}

	@Override
	public int getId() {
		return 8;
	}

	@Override
	public String getLibelleMenu() {
		return "Changer le nom d'un client";
	}

	@Override
	public void execute() {
		String key = keyEntry.getKeyString("nom client");
		Client client = findService.findBy(key, "nom");
		System.out.println(client.toString());	

		key = keyEntry.getKeyString("nouveau nom client");
		client.setNom(key);
		findService.setName(client);
	}

}
