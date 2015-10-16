package com.bankonet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class createData {
	static String login = "";
	static String type = "";
	static String nom = "";
	static String prenom = "";
	static String mdp = "";

	public static void chooseClient(TypeCompte type, String action) {
		System.out.println("Client de la banque : \n" + MapClient.showLoginClient());
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		Client client;
		try {
			System.out.println("Saisir le login du client :");
			login = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		client = MapClient.getClient(login);
		if (client == null) {
			System.out.println("Ce client n'éxiste pas !");
			chooseClient(type, action);
		} else {
			if (action.equals("createAccount")) {
				newAccount(type, client);
			} else if (action.equals("setDiscovered")) {
				setDiscovered(client);
			}
		}

		gestionFichier.saveData();
	}

	private static void setDiscovered(Client client) {
		System.out.println("Choisir un compte à modifier : \n" + client.getCCLibelle().replace(",", " | "));
		String account = "";
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Saisir le libelle du compte :");
			account = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		CompteCourant chosenCC = (CompteCourant) MapCompte.getCompte(account, TypeCompte.CC);
		if (chosenCC == null) {
			System.out.println("Ce choix ne correspond à aucun compte courant.\n");
			setDiscovered(client);
		} else {
			
			chosenCC.setDecouvert(setDiscoveredSum());
		}
	}
	
	private static double setDiscoveredSum() { 
		String amount="";
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Saisir le montant :");
		try {
			amount = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!UpdateAccount.estUnEntier(amount)) {
			System.out.println("La somme saisie n'est pas un nombre valide.\n");
			setDiscoveredSum();
			return 0; 
		} else {
			return Double.parseDouble(amount);			
		}
	}

	public static void newAccount(TypeCompte type, Client client) {

		Compte nouveauCompte;
		if (type.equals(TypeCompte.CC)) {
			nouveauCompte = new CompteCourant(login, client.getNom(), client.getPrenom(),
					client.getNumberAccount(TypeCompte.CC));
			client.addCompte(nouveauCompte, TypeCompte.CC);
			MapCompte.ajouterCompte(nouveauCompte, TypeCompte.CC);
		} else {
			nouveauCompte = new CompteEpargne(login, client.getNom(), client.getPrenom(),
					client.getNumberAccount(TypeCompte.CE));
			client.addCompte(nouveauCompte, TypeCompte.CE);
			MapCompte.ajouterCompte(nouveauCompte, TypeCompte.CE);
		}
	}

	public static void CreateClient() {
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Saisir le nom du client :");
			nom = keyboard.readLine();
			System.out.println("Saisir le prenom du client :");
			prenom = keyboard.readLine();
			System.out.println("Saisir le login du client :");
			login = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mdp = "default";
		Client client = new Client(nom, prenom, login, mdp);
		newAccount(TypeCompte.CC, client);
		if (MapClient.ajouterClient(client)) {
			gestionFichier.saveData();
		}
	}
}
