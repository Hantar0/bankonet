package com.bankonet.metier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bankonet.cache.MapClient;
import com.bankonet.cache.MapCompte;
import com.bankonet.constantes.TypeCompte;

public class CreateData {
	private String login;
	private String nom;
	private String prenom;
	private String mdp;
	private MapClient mapClient;
	private SaveFiles saveFiles;
	private MapCompte mapCompte;

	public CreateData(MapClient mapClient, SaveFiles saveFiles, MapCompte mapCompte) {
		this.login = "";
		this.nom = "";
		this.prenom = "";
		this.mdp = "";
		this.mapClient = mapClient;
		this.saveFiles = saveFiles;
		this.mapCompte = mapCompte;
	}

	public void chooseClient(TypeCompte type, String action) {
		System.out.println("Client de la banque : \n" + mapClient.showLoginClient());
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		Client client;
		try {
			System.out.println("Saisir le login du client :");
			login = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		client = mapClient.getClient(login);
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
		saveFiles.saveData();
	}

	private void setDiscovered(Client client) {
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
		CompteCourant chosenCC = (CompteCourant) mapCompte.getCompte(account, TypeCompte.CC);
		if (chosenCC == null) {
			System.out.println("Ce choix ne correspond à aucun compte courant.\n");
			setDiscovered(client);
		} else {

			chosenCC.setDecouvert(setDiscoveredSum());
		}
	}

	private double setDiscoveredSum() {
		String amount = "";
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Saisir le montant :");
		try {
			amount = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (estUnEntier(amount)) {
			System.out.println("La somme saisie n'est pas un nombre valide.\n");
			setDiscoveredSum();
			return 0;
		} else {
			return Double.parseDouble(amount);
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

	public void newAccount(TypeCompte type, Client client) {

		Compte nouveauCompte;
		if (type.equals(TypeCompte.CC)) {
			nouveauCompte = new CompteCourant(login, client.getNom(), client.getPrenom(),
					client.getNumberAccount(TypeCompte.CC));
			client.addCompte(nouveauCompte, TypeCompte.CC);
			mapCompte.ajouterCompte(nouveauCompte, TypeCompte.CC);
		} else {
			nouveauCompte = new CompteEpargne(login, client.getNom(), client.getPrenom(),
					client.getNumberAccount(TypeCompte.CE));
			client.addCompte(nouveauCompte, TypeCompte.CE);
			mapCompte.ajouterCompte(nouveauCompte, TypeCompte.CE);
		}
	}

	public void CreateClient() {
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
		if (mapClient.ajouterClient(client)) {
			saveFiles.saveData();
		}
	}
}
