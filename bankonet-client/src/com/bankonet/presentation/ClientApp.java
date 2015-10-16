package com.bankonet.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bankonet.cache.MapClient;
import com.bankonet.cache.MapCompte;
import com.bankonet.dao.DaoFactory;
import com.bankonet.dao.DaoFactoryFile;
import com.bankonet.dao.DaoFactoryMySql;
import com.bankonet.dao.GestionFichier;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.metier.Client;
import com.bankonet.metier.ImportFile;
import com.bankonet.metier.SaveFiles;
import com.bankonet.metier.StopApp;
import com.bankonet.metier.UpdateAccount;

public class ClientApp {

	private String login;
	private Client user;
	private MapClient mapClient = new MapClient();
	private MapCompte mapCompte = new MapCompte();
	private GestionFichier gestionFichier = new GestionFichier(mapClient, mapCompte);
	private ImportFile importFile = new ImportFile(gestionFichier);
	private DaoFactory daoFactory;
	private UpdateAccount updateAccount;
	private CompteDao compteDao;
	private ClientDao clientDao;
	private SaveFiles saveFile;
	private StopApp stopApp;

	public void start(String dao) throws Exception {
		switch (dao) {
		case "Files":
			daoFactory = new DaoFactoryFile(gestionFichier, mapClient, mapCompte);
			break;
		case "MySql":
			daoFactory = new DaoFactoryMySql(mapClient, mapCompte);
			break;
		default:
			throw new Exception();
		}

		clientDao = daoFactory.getClientDao();
		compteDao = daoFactory.getCompteDao();
		saveFile = new SaveFiles(compteDao, clientDao);
		stopApp = new StopApp(saveFile);

		// import des datas
		importFile.importF();
		logIn();
		updateAccount = new UpdateAccount(daoFactory, user, saveFile, mapCompte);
		loadInterface();

		saveFile.saveData();

	}

	private void loadInterface() {
		System.out.println("***** APPLICATION CLIENT ******" + "\n0. Arrêter le programme"
				+ "\n1. Consulter les soldes des comptes" + "\n2. Effectuer un dépôt" + "\n3. Effectuer un retrait"
				+ "\n4. Effectuer un virement entre vos comptes" + "\nVeuillez choisir une action.");

		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		try {
			line = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		switch (line) {
		case "0":
			stopApp.Stop();
			break;

		case "1":
			System.out.println(user.getInfoUser());
			break;

		case "2":
			updateAccount.manage("deposit");
			break;

		case "3":
			updateAccount.manage("withdrawal");
			break;

		case "4":
			updateAccount.transfer();
			break;

		default:
			System.out.println("\nAucun choix ne correspond à votre sélection.\n");
			loadInterface();
			break;
		}

		loadInterface();

	}

	private void logIn() {
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		String mdp = "";
		try {
			System.out.println("Saisir le login du client :");
			login = keyboard.readLine();
			System.out.println("Saisir le mot de passe du client :");
			mdp = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		user = mapClient.getClient(login);
		if (user == null) {
			System.out.println("Ce client n'éxiste pas !");
			logIn();
			return;
		}

		if (!user.getMdp().equals(mdp)) {
			System.out.println("Connexion impossible");
			logIn();
			return;
		}

	}

}
