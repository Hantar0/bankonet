package com.bankonet.commands;

import com.bankonet.dto.Client;
import com.bankonet.metier.client.FindService;
import com.bankonet.presentation.RecupKeyEntry;

public class RemoveClientCommand implements TheCommand {

	private RecupKeyEntry keyEntry;
	private FindService findService;

	public RemoveClientCommand(RecupKeyEntry keyEntry, FindService findService) {
		super();
		this.keyEntry = keyEntry;
		this.findService = findService;
	}

	@Override
	public int getId() {
		return 9;
	}

	@Override
	public String getLibelleMenu() {
		return "Supprimer un client";
	}

	@Override
	public void execute() {
		String key = keyEntry.getKeyString("nom client");
		Client client = findService.findBy(key, "nom");

		key = keyEntry.getKeyString("validation suppression (V/A)");
		if (key.equals("Y")) {
			findService.delete(client);
			System.out.println("Suppression effectuée");
		} else {
			System.out.println("Opération annulée");
		}
	}

}
