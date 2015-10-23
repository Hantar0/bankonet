package com.bankonet.commands;

public interface TheCommand {
	int getId();
	String getLibelleMenu();
	void execute();
}
