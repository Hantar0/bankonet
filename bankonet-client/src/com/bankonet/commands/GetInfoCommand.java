package com.bankonet.commands;

import com.bankonet.dto.Client;

public class GetInfoCommand implements TheCommand{
	Client user;

	@Override
	public int getId() {
		return 1;
	}

	public GetInfoCommand(Client user) {
		this.user = user;
	}

	@Override
	public String getLibelleMenu() {
		return "Consulter les soldes des comptes";
	}

	@Override
	public void execute() {
		System.out.println(user.getInfoUser());
	}

}
