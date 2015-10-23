package com.bankonet.commands;

import com.bankonet.cache.CacheClient;
import com.bankonet.dto.Client;
import com.bankonet.metier.client.FindService;
import com.bankonet.presentation.RecupKeyEntry;

public class DeleteAllClientCommand implements TheCommand {

	private RecupKeyEntry keyEntry;
	private FindService findService;
	private CacheClient cacheClient;

	public DeleteAllClientCommand(RecupKeyEntry keyEntry, FindService findService, CacheClient cacheClient) {
		super();
		this.keyEntry = keyEntry;
		this.findService = findService;
		this.cacheClient= cacheClient;
	}

	@Override
	public int getId() {
		return 10;
	}

	@Override
	public String getLibelleMenu() {
		return "Supprimer tous les clients";
	}

	@Override
	public void execute() {

		String key = keyEntry.getKeyString("validation suppression (V/A)");
		if (key.equals("V")) {
			findService.deleteAll(cacheClient);
			System.out.println("Suppression effectuée");
		} else {
			System.out.println("Opération annulée");
		}
		
	}

}
