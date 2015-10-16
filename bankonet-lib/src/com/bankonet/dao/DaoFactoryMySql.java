package com.bankonet.dao;

import com.bankonet.cache.MapClient;
import com.bankonet.cache.MapCompte;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.client.ClientDaoMySql;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.dao.compte.CompteDaoMySql;

public class DaoFactoryMySql implements DaoFactory {
	//On va récupérer les Dao pour le type file

	public DaoFactoryMySql(MapClient mapClient, MapCompte mapCompte) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CompteDao getCompteDao() {
		return new CompteDaoMySql();
	}

	@Override
	public ClientDao getClientDao() {
		return new ClientDaoMySql();
	}

}
