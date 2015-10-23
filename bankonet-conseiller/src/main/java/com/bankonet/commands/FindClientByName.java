package com.bankonet.commands;

import com.bankonet.dto.Client;
import com.bankonet.metier.client.FindService;
import com.bankonet.presentation.RecupKeyEntry;

public class FindClientByName implements TheCommand {

	private FindService findService;
	private RecupKeyEntry keyEntry;
	
	public FindClientByName(FindService findService, RecupKeyEntry keyEntry) {
		super();
		this.findService = findService;
		this.keyEntry = keyEntry;
	}

	@Override
	public int getId() {
		return 6;
	}

	@Override
	public String getLibelleMenu() {
		return "Trouver un client par son nom";
	}

	@Override
	public void execute() {
		String key = keyEntry.getKeyString("nom client");
		Client client = findService.findBy(key, "nom");
		System.out.println(client.toString());
	}

}
