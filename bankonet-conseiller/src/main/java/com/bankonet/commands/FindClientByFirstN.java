package com.bankonet.commands;

import com.bankonet.dto.Client;
import com.bankonet.metier.client.FindService;
import com.bankonet.presentation.RecupKeyEntry;

public class FindClientByFirstN implements TheCommand{

	private FindService findService;
	private RecupKeyEntry keyEntry;

	public FindClientByFirstN(FindService findService, RecupKeyEntry keyEntry) {
		this.findService = findService;
		this.keyEntry = keyEntry;
	}

	@Override
	public int getId() {
		return 7;
	}

	@Override
	public String getLibelleMenu() {
		return "Trouver un client par son prénom";
	}

	@Override
	public void execute() {
		String key = keyEntry.getKeyString("prénom client");
		Client client = findService.findBy(key, "prenom");
		System.out.println(client.toString());		
	}

}
