package com.bankonet.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bankonet.cache.CacheClient;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.cache.CacheAccount;
import com.bankonet.dao.DaoFactory;
import com.bankonet.dao.DaoFactoryFile;
import com.bankonet.dao.DaoFactoryMySql;
import com.bankonet.dao.GestionFichier;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.dto.Client;
import com.bankonet.metier.ImportFile;
import com.bankonet.metier.SyncDataService;
import com.bankonet.metier.StopApp;
import com.bankonet.metier.UserService;

public class ClientApp {

	private String login;
	private Client user;
	private CacheClient cacheClient = new CacheClient();
	private CacheAccount cacheCompte = new CacheAccount();
	private RecupKeyEntry recupKeyEntry = new RecupKeyEntry(cacheCompte);
	private GestionFichier gestionFichier = new GestionFichier(cacheClient, cacheCompte);
	private ImportFile importFile = new ImportFile(gestionFichier);
	private DaoFactory daoFactory;
	private UserService userService;
	private CompteDao compteDao;
	private ClientDao clientDao;
	private SyncDataService saveFile;
	private StopApp stopApp;
	private manageAccount manageAccount;

	public void start(String dao) throws Exception {
		switch (dao) {
		case "Files":
			daoFactory = new DaoFactoryFile(gestionFichier, cacheClient, cacheCompte);
			break;
		case "MySql":
			daoFactory = new DaoFactoryMySql(cacheClient, cacheCompte);
			break;
		default:
			throw new Exception();
		}

		clientDao = daoFactory.getClientDao();
		compteDao = daoFactory.getCompteDao();
		saveFile = new SyncDataService(compteDao, clientDao);
		stopApp = new StopApp(saveFile);

		// import des datas
		importFile.importF();
		logIn();
		userService = new UserService(daoFactory, user, cacheCompte);
		manageAccount = new manageAccount(userService, recupKeyEntry, user, cacheCompte);
		loadInterface();

		saveFile.sync();

	}

	private void loadInterface() {
		System.out.println("***** APPLICATION CLIENT ******" + "\n0. Arrêter le programme"
				+ "\n1. Consulter les soldes des comptes" + "\n2. Effectuer un dépôt" + "\n3. Effectuer un retrait"
				+ "\n4. Effectuer un virement entre vos comptes" + "\nVeuillez choisir une action.");

		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		String[] result = {};

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
			manageAccount.manage("deposit");
			break;

		case "3":
			manageAccount.manage("withdrawl");
			break;

		case "4":
			manageAccount.transfer(cacheClient, cacheCompte, user);
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
		user = cacheClient.getClient(login);
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
