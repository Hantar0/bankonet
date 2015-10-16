package com.bankonet.dao;

import com.bankonet.cache.MapClient;
import com.bankonet.cache.MapCompte;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.client.ClientDaoFile;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.dao.compte.CompteDaoFile;

public class DaoFactoryFile implements DaoFactory {
	GestionFichier GF;
	MapClient mapClient;
	MapCompte mapCompte;
	//On va récupérer les Dao pour le type file
	public DaoFactoryFile(GestionFichier GF, MapClient mapClient, MapCompte mapCompte) {
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
