package com.bankonet.metier;

import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.dto.Client;
import com.bankonet.dto.Compte;

public class SyncDataService {
	private CompteDao compteDao;
	private ClientDao clientDao;
	public SyncDataService(CompteDao compteDao, ClientDao clientDao) {
		this.compteDao=compteDao;
		this.clientDao=clientDao;
	}

	public void syncFiles() {
		compteDao.saveComptes();
		clientDao.saveClients();
	}

	public void openAccount(Client client, Compte compte) {
		compteDao.addComptes(compte);
		clientDao.addClient(client);
		
	}

	public void addAccount(Client client, Compte compte) {
		compteDao.addComptes(compte);
		clientDao.updateClient(client);		
	}
	

}
