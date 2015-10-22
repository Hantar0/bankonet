package com.bankonet.presentation;

import com.bankonet.cache.CacheCompte;
import com.bankonet.cache.CacheClient;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.dto.Client;
import com.bankonet.dto.Compte;
import com.bankonet.metier.SoldeLimitException;
import com.bankonet.metier.client.UserService;

public class ManageAccount {
	private UserService userService;
	private RecupKeyEntry recupKeyEntry;
	private Client user;
	private CacheCompte cacheCompte;

	public ManageAccount(UserService userService, RecupKeyEntry recupKeyEntry, Client user, CacheCompte cacheCompte) {
		this.userService = userService;
		this.recupKeyEntry = recupKeyEntry;
		this.user = user;
		this.cacheCompte = cacheCompte;
	}

	public void manage(String action) {
		String typeAction = "";
		if (action.equals("deposit")) {
			typeAction = "créditer";
		} else {
			typeAction = "débiter";
		}
		String libelleAccounts = user.getLibellesAccount();
		System.out.println("Compte à " + typeAction);
		System.out.println("Comptes courants & épargnes : " + libelleAccounts.replace(",", " | "));
		String account = recupKeyEntry.verifyAccount("compte");

		Compte chosenAccount = cacheCompte.getCompte(account, getType(account));
		Double sum = chooseSum();

		String[] result = {};
		try {
			result = userService.manage(action, account, sum, true);
		} catch (SoldeLimitException e) {
			withdrawl(account, sum, false);
			result[0] = chosenAccount.getLibelle();
			result[1] = chosenAccount.getSolde() + "";

		} finally {
			System.out.println("La solde du compte " + result[0] + " est désormais de : " + result[1] + "\n");
		}
	}

	private double chooseSum() {
		double sum = recupKeyEntry.getKeyDouble("somme");
		return sum;
	}

	private double withdrawl(String accountD, Double sum, boolean commit) {
		try {
			userService.manage("withdrawl", accountD, sum, commit);
		} catch (SoldeLimitException e) {
			System.out.println(e.toString());
			sum = chooseSum();
			sum = withdrawl(accountD, sum, commit);
		}
		return sum;
	}

	public void transfer(CacheClient mapClient, CacheCompte cacheCompte, Client user) {
		String libelleAccounts = user.getLibellesAccount();
		System.out.println("Compte à débiter");
		System.out.println("Comptes courants & épargnes : " + libelleAccounts.replace(",", " | "));
		String accountD = recupKeyEntry.verifyAccount("compte");
		Compte chosenAccountD = cacheCompte.getCompte(accountD, getType(accountD));

		System.out.println("Compte à créditer");
		System.out.println("Comptes courants & épargnes : " + libelleAccounts.replace(",", " | "));
		String accountC = recupKeyEntry.verifyAccount("compte");
		Compte chosenAccountC = cacheCompte.getCompte(accountC, getType(accountC));

		Double sum = chooseSum();
		if (sum != 0) {
			sum = withdrawl(accountD, sum, true);
			try {
				userService.manage("deposit", accountC, sum, true);
			} catch (SoldeLimitException e) {
				// On ne rentrera jamais dans ce catch
			}
			System.out.println("La solde du compte " + chosenAccountC.getLibelle() + " est désormais de : "
					+ chosenAccountC.getSolde() + "\n");
			System.out.println("La solde du compte " + chosenAccountD.getLibelle() + " est désormais de : "
					+ chosenAccountD.getSolde() + "\n");
		} else {
			System.out.println("Abandon du retrait.");
		}
	}

	private TypeCompte getType(String account) {
		if (account.contains("COURANT")) {
			return TypeCompte.CC;
		} else {
			return TypeCompte.CE;
		}
	}

}
