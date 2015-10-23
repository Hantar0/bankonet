package com.bankonet.metier.client;

import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.client.ClientDaoJpa;
import com.bankonet.dto.Client;

public class InitService {	
	
	private ClientDao clientDao;


	public InitService(ClientDao clientDao) {
		super();
		this.clientDao = clientDao;
	}


	public void init() {
		Client client1 = new Client("polo", "hiro", "poro", "poro");
		Client client2 = new Client("thomas", "colm", "thco", "thco");
		Client client3 = new Client("kayle", "breov", "kabr", "kabr");
		Client client4 = new Client("stan", "wise", "stwi", "stwi");
		Client client5 = new Client("edouard", "comte", "edco", "edco");
		
		clientDao.addClient(client1);
		clientDao.addClient(client2);
		clientDao.addClient(client3);
		clientDao.addClient(client4);
		clientDao.addClient(client5);
		
	}
}
