package com.bankonet.metier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bankonet.cache.CacheAccount;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.dao.DaoFactory;
import com.bankonet.dao.DaoFactoryFile;
import com.bankonet.dto.Client;
import com.bankonet.dto.Compte;

public class UserService {

	private double solde;
	private String account = "";
	private Client user;
	private String libelleAccounts = "";
	private DaoFactoryFile daoFactoryFile;
	private CacheAccount cacheCompte;

	public UserService(DaoFactory daoFactory, Client user, CacheAccount cacheCompte) {
		this.user = user;
		this.daoFactoryFile = (DaoFactoryFile) daoFactory;
		this.cacheCompte = cacheCompte;
	}

	public String[] manage(String action, String account, Double sum) throws soldeLimitException {

		if (action.equals("deposit")) {
			deposit(account, sum);
		} else if (action.equals("withdrawl")) {
			withdrawl(account, sum);
		}

		String finalSolde = "" + cacheCompte.getCompte(account, TypeCompte.CC).getSolde();
		daoFactoryFile.getCompteDao().saveComptes();
		String[] toPrint = { account, finalSolde };
		return toPrint;
	}

	public boolean estUnEntier(String chaine) {
		try {
			Integer.parseUnsignedInt(chaine);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public void deposit(String account, double sum) {
		Compte chosenAccountC = cacheCompte.getCompte(account, getType(account));
		chosenAccountC.setSolde(sum);
		daoFactoryFile.getCompteDao().saveComptes();
	}

	public void withdrawl(String accountD, double sum) throws soldeLimitException {
		Compte chosenAccountD = cacheCompte.getCompte(accountD, getType(accountD));
		if (sum > chosenAccountD.getSolde()) {
			throw new soldeLimitException();
		}
		chosenAccountD.setSolde(-sum);
		daoFactoryFile.getCompteDao().saveComptes();
	}

	private TypeCompte getType(String account) {
		if (account.contains("COURANT")) {
			return TypeCompte.CC;
		} else {
			return TypeCompte.CE;
		}
	}
}
