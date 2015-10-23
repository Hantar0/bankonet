package com.bankonet.commands;

import com.bankonet.cache.CacheClient;

public class ShowAllClientCommand implements TheCommand {

	private CacheClient cacheClient;
	
	public ShowAllClientCommand(CacheClient cacheClient) {
		super();
		this.cacheClient = cacheClient;
	}

	@Override
	public int getId() {
		return 2;
	}

	@Override
	public String getLibelleMenu() {
		return "Lister tous les clients";
	}

	@Override
	public void execute() {
		System.out.println(cacheClient.showData());
	}

}
