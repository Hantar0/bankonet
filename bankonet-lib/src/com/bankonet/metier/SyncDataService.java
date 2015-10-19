package com.bankonet.metier;

import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;

public class SyncDataService {
	private CompteDao compteDao;
	private ClientDao clientDao;
	public SyncDataService(CompteDao compteDao, ClientDao clientDao) {
		this.compteDao=compteDao;
		this.clientDao=clientDao;
	}

	public void sync() {
		compteDao.saveComptes();
		clientDao.saveClients();
	}
	

}
