package com.bankonet.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bankonet.cache.CacheClient;
import com.bankonet.cache.CacheCompte;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.dto.Client;
import com.bankonet.dto.Compte;
import com.bankonet.dto.CompteCourant;
import com.bankonet.dto.CompteEpargne;
import com.bankonet.metier.SyncDataService;

public class BankOfficerService {
	private String login;
	private String nom;
	private String prenom;
	private String mdp;
	private CacheClient mapClient;
	private SyncDataService saveFiles;
	private CacheCompte mapCompte;

	public BankOfficerService(CacheClient mapClient, SyncDataService saveFiles, CacheCompte mapCompte) {
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
		Compte compte;
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
				compte= newAccount(type, client);
				saveFiles.addAccount(client, compte);
			} else if (action.equals("setDiscovered")) {
				setDiscovered(client);
				//saveFiles.updateAccount();
			}
		}
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

	public Compte newAccount(TypeCompte type, Client client) {

		Compte nouveauCompte;
		if (type.equals(TypeCompte.CC)) {
			nouveauCompte = new CompteCourant(login, client.getNom(), client.getPrenom(),
					client.getNumberAccount(TypeCompte.CC));
			client.addCompte(nouveauCompte, TypeCompte.CC);
			client.setComptes_courant(client.getCCLibelle());
			mapCompte.ajouterCompte(nouveauCompte, TypeCompte.CC);
		} else {
			nouveauCompte = new CompteEpargne(login, client.getNom(), client.getPrenom(),
					client.getNumberAccount(TypeCompte.CE));
			client.addCompte(nouveauCompte, TypeCompte.CE);
			client.setComptes_epargne(client.getCELibelle());
			mapCompte.ajouterCompte(nouveauCompte, TypeCompte.CE);
		}
		return nouveauCompte;
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
		Compte compte = newAccount(TypeCompte.CC, client);
		if (mapClient.ajouterClient(client)) {
			saveFiles.openAccount(client, compte);
		}
	}
}
