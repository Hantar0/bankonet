package com.bankonet.metier;

import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;

public class SaveFiles {
	private CompteDao compteDao;
	private ClientDao clientDao;
	public SaveFiles(CompteDao compteDao, ClientDao clientDao) {
		this.compteDao=compteDao;
		this.clientDao=clientDao;
	}

	public void saveData() {
		compteDao.saveComptes();
		clientDao.saveClients();
	}
	
	public void saveComptes() {
		compteDao.saveComptes();
	}
	
	public void saveClients() {
		clientDao.saveClients();
	}
}
