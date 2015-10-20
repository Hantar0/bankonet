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
	private DaoFactory daoFactory;
	private CacheAccount cacheCompte;

	public UserService(DaoFactory daoFactory, Client user, CacheAccount cacheCompte) {
		this.user = user;
		this.cacheCompte = cacheCompte;
		this.daoFactory=daoFactory;
	}

	public String[] manage(String action, String account, Double sum, boolean commit) throws SoldeLimitException {

		if (action.equals("deposit")) {
			deposit(account, sum);
		} else if (action.equals("withdrawl")) {
			withdrawl(account, sum);
		}
		TypeCompte type = null;
		if(account.contains("COURANT")){
			type = TypeCompte.CC;
		} else {
			type = TypeCompte.CE;
		}
		Compte compte = cacheCompte.getCompte(account, type);
		String finalSolde = "" + compte.getSolde();		
		String[] toPrint = { account, finalSolde };
		
		if(commit) {
			daoFactory.getCompteDao().updatesComptes(compte);			
		}
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
		//daoFactoryFile.getCompteDao().saveComptes();
	}

	public void withdrawl(String accountD, double sum) throws SoldeLimitException {
		Compte chosenAccountD = cacheCompte.getCompte(accountD, getType(accountD));
		if (sum > chosenAccountD.getSolde()) {
			throw new SoldeLimitException();
		}
		chosenAccountD.setSolde(-sum);
		//daoFactoryFile.getCompteDao().saveComptes();
	}

	private TypeCompte getType(String account) {
		if (account.contains("COURANT")) {
			return TypeCompte.CC;
		} else {
			return TypeCompte.CE;
		}
	}
}
