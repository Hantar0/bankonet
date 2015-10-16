package com.bankonet.metier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bankonet.cache.MapCompte;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.dao.DaoFactory;
import com.bankonet.dao.DaoFactoryFile;

public class UpdateAccount {

	private double solde;
	private String account = "";
	private Client user;
	private String libelleAccounts = "";
	private DaoFactoryFile daoFactoryFile;
	private SaveFiles saveFiles;
	private MapCompte mapCompte;

	public UpdateAccount(DaoFactory daoFactory, Client user, SaveFiles saveFiles, MapCompte mapCompte) {
		this.user = user;
		this.saveFiles = saveFiles;
		daoFactoryFile = (DaoFactoryFile) daoFactory;
		this.mapCompte = mapCompte;
	}

	public void manage(String action) {
		libelleAccounts = user.getCCLibelle();

		chooseAccount("Comptes courants : " + libelleAccounts.replace(",", " | "));

		chooseSum();

		Compte chosenAccount = mapCompte.getCompte(account, getType());
		if (action.equals("deposit")) {
			deposit(chosenAccount);
		} else if (action.equals("withdrawal")) {
			withdrawal(chosenAccount);
		}

		System.out.println("La solde du compte " + account + " est désormais de : "
				+ mapCompte.getCompte(account, TypeCompte.CC).getSolde() + "\n");
		saveFiles.saveComptes();
	}

	private void chooseAccount(String libelle) {
		System.out.println(libelle);
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Saisir le libelle du compte :");
			account = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (mapCompte.getCompte(account, TypeCompte.CC) == null
				&& mapCompte.getCompte(account, TypeCompte.CE) == null) {
			System.out.println("Ce choix ne correspond à aucun compte.\n");
			chooseAccount(libelle);
		}
	}

	private void chooseSum() {
		String amount = "";
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Saisir le montant :");
		try {
			amount = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!estUnEntier(amount)) {
			System.out.println("La somme saisie n'est pas un nombre valide.\n");
			chooseSum();
		} else {
			solde = Double.parseDouble(amount);
		}
	}

	public boolean estUnEntier(String chaine) {
		try {
			Integer.parseUnsignedInt(chaine);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private void deposit(Compte chosenAccount) {
		chosenAccount.setSolde(solde);
	}

	private void withdrawal(Compte chosenAccount) {
		if (solde == 0) {
			System.out.println("Abandon du retrait");
			return;
		} else if (solde > chosenAccount.getSolde()) {
			System.out.println("Retrait impossible");
			chooseSum();
		}
		chosenAccount.setSolde(-solde);

	}

	public void transfer() {
		libelleAccounts = user.getCCLibelle() + " " + user.getCELibelle();
		System.out.println("Compte à débiter");
		chooseAccount("Comptes courants & épargnes : " + libelleAccounts.replace(",", " | "));
		Compte chosenAccountD = mapCompte.getCompte(account, getType());

		System.out.println("Compte à créditer");
		chooseAccount("Comptes courants & épargnes : " + libelleAccounts.replace(",", " | "));
		Compte chosenAccountC = mapCompte.getCompte(account, getType());

		chooseSum();

		withdrawal(chosenAccountD);
		deposit(chosenAccountC);
		System.out.println("La solde du compte " + chosenAccountD.getLibelle() + " est désormais de : "
				+ chosenAccountD.getSolde() + "\n");
		System.out.println("La solde du compte " + chosenAccountC.getLibelle() + " est désormais de : "
				+ chosenAccountC.getSolde() + "\n");
	}

	private TypeCompte getType() {
		if (account.contains("COURANT")) {
			return TypeCompte.CC;
		} else {
			return TypeCompte.CE;
		}
	}

}
