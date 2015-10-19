package com.bankonet.dao;

import com.bankonet.cache.CacheClient;
import com.bankonet.cache.CacheAccount;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.client.ClientDaoFile;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.dao.compte.CompteDaoFile;

public class DaoFactoryFile implements DaoFactory {
	GestionFichier GF;
	CacheClient mapClient;
	CacheAccount mapCompte;
	//On va récupérer les Dao pour le type file
	public DaoFactoryFile(GestionFichier GF, CacheClient mapClient, CacheAccount mapCompte) {
		this.GF=GF;		
		this.mapClient=mapClient;
		this.mapCompte=mapCompte;
	}
	
	@Override
	public CompteDao getCompteDao() {
		return new CompteDaoFile(GF, mapCompte);
	}

	@Override
	public ClientDao getClientDao() {
		return new ClientDaoFile(GF, mapClient);
	}

}
