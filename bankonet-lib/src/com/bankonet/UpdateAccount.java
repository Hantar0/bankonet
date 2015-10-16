package com.bankonet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UpdateAccount {

	private static double solde =0;
	private static String account = "";
	private static Client user;
	private static String libelleAccounts="";

	public static void manage(Client client, String action) {
		user = client;
		libelleAccounts = user.getCCLibelle();
		
		chooseAccount("Comptes courants : " + libelleAccounts.replace(",", " | "));
		
		chooseSum();
		
		Compte chosenAccount = MapCompte.getCompte(account, getType());
		if(action.equals("deposit"))
		{
			deposit(chosenAccount);
		} else if(action.equals("withdrawal")) {
			withdrawal(chosenAccount);
		}
		
		System.out.println("La solde du compte "+ account +" est désormais de : "+MapCompte.getCompte(account, TypeCompte.CC).getSolde()+"\n");
		gestionFichier.saveComptes();
	}

	private static void chooseAccount(String libelle) {
		System.out.println(libelle);
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Saisir le libelle du compte :");
			account = keyboard.readLine();	
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (MapCompte.getCompte(account, TypeCompte.CC)==null&&MapCompte.getCompte(account, TypeCompte.CE)==null) {
			System.out.println("Ce choix ne correspond à aucun compte.\n");
			chooseAccount(libelle);
		}		
	}
	
	private static void chooseSum() {
		String amount="";
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
			solde=Double.parseDouble(amount);			
		}
	}

	public static boolean estUnEntier(String chaine) {
		try {
			Integer.parseUnsignedInt(chaine);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private static void deposit(Compte chosenAccount) {
		chosenAccount.setSolde(solde);
	}

	private static void withdrawal(Compte chosenAccount) {
		if(solde==0) {
			System.out.println("Abandon du retrait");
			return;
		} else if (solde > chosenAccount.getSolde()) {
			System.out.println("Retrait impossible");
			chooseSum();
		}
		chosenAccount.setSolde(-solde);
		
	}

	public static void transfer(Client client) {
		user = client;
		libelleAccounts = user.getCCLibelle()+" "+user.getCELibelle();
		System.out.println("Compte à débiter");
		chooseAccount("Comptes courants & épargnes : " + libelleAccounts.replace(",", " | "));
		Compte chosenAccountD = MapCompte.getCompte(account, getType());

		System.out.println("Compte à créditer");
		chooseAccount("Comptes courants & épargnes : " + libelleAccounts.replace(",", " | "));
		Compte chosenAccountC = MapCompte.getCompte(account, getType());
		
		chooseSum();
		
		withdrawal(chosenAccountD);
		deposit(chosenAccountC);
		System.out.println("La solde du compte "+ chosenAccountD.getLibelle() +" est désormais de : "+chosenAccountD.getSolde()+"\n");
		System.out.println("La solde du compte "+ chosenAccountC.getLibelle() +" est désormais de : "+chosenAccountC.getSolde()+"\n");
	}
	
	private static TypeCompte getType() {
		if(account.contains("COURANT")) {
			return TypeCompte.CC;
		} else {
			return TypeCompte.CE;
		}
	}
	
	

}
