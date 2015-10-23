package com.bankonet.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.bankonet.cache.CacheCompte;
import com.bankonet.cache.CacheClient;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.client.ClientDaoJpa;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.dao.compte.CompteDaoJpa;

public class DaoFactoryJPA implements DaoFactory {
	
	private EntityManagerFactory emf;
	private CacheCompte cacheCompte;
	private CacheClient cacheClient;
	
	public DaoFactoryJPA(String bdd, CacheClient cacheClient, CacheCompte cacheCompte) {
		this.emf = Persistence.createEntityManagerFactory(bdd);
		this.cacheClient=cacheClient;
		this.cacheCompte=cacheCompte;
	}

	@Override
	public CompteDao getCompteDao() {
		return new CompteDaoJpa(emf, cacheCompte);
	}

	@Override
	public ClientDao getClientDao() {
		return new ClientDaoJpa(emf, cacheClient, cacheCompte);
	}

}
