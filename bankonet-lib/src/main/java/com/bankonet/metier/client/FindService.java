package com.bankonet.metier.client;

import com.bankonet.cache.CacheClient;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dto.Client;

public class FindService {
	private ClientDao clientDao;


	public FindService(ClientDao clientDao) {
		super();
		this.clientDao = clientDao;
	}


	public Client findBy(String libelleSearch, String column) {
		return clientDao.findClient(libelleSearch, column);
		
	}


	public void setName(Client client) {
		clientDao.updateClient(client);		
	}


	public void delete(Client client) {
		clientDao.delete(client);		
	}


	public void deleteAll(CacheClient cacheClient) {
		clientDao.truncateTable();
		
	}
}
