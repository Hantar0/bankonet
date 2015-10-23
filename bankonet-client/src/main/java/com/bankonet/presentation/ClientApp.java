package com.bankonet.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.bankonet.cache.CacheClient;
import com.bankonet.cache.CacheCompte;
import com.bankonet.commands.DepositCommand;
import com.bankonet.commands.ExitCommand;
import com.bankonet.commands.GetInfoCommand;
import com.bankonet.commands.TheCommand;
import com.bankonet.commands.TransferCommand;
import com.bankonet.commands.WithdrawlCommand;
import com.bankonet.dao.ConnectionSQL;
import com.bankonet.dao.DaoFactory;
import com.bankonet.dao.DaoFactoryFile;
import com.bankonet.dao.DaoFactoryMySql;
import com.bankonet.dao.GestionData;
import com.bankonet.dao.GestionFichier;
import com.bankonet.dao.GestionMySQL;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.dto.Client;
import com.bankonet.metier.StopApp;
import com.bankonet.metier.SyncDataService;
import com.bankonet.metier.client.UserService;

public class ClientApp {

	private ConnectionSQL connectionSQL = new ConnectionSQL();
	private String login;
	private Client user;
	private CacheClient cacheClient = new CacheClient();
	private CacheCompte cacheCompte = new CacheCompte();
	private RecupKeyEntry recupKeyEntry = new RecupKeyEntry(cacheCompte);
	private GestionFichier gestionFichier = new GestionFichier(cacheClient, cacheCompte);
	private GestionMySQL gestionMySQL  = new GestionMySQL(cacheClient, cacheCompte);
	private DaoFactory daoFactory;
	private UserService userService;
	private CompteDao compteDao;
	private ClientDao clientDao;
	private SyncDataService saveData;
	private StopApp stopApp;
	private ManageAccount manageAccount;
	private List<TheCommand> commands;
	private GestionData gestionData;

	public void start(String dao) {
		switch (dao) {
		case "Files":
			daoFactory = new DaoFactoryFile(gestionFichier, cacheClient, cacheCompte);
			clientDao = daoFactory.getClientDao();
			compteDao = daoFactory.getCompteDao();
			try {
				compteDao.importData(gestionFichier);
				clientDao.importData(gestionFichier);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "MySql":
			daoFactory = new DaoFactoryMySql(cacheClient, cacheCompte);
			clientDao = daoFactory.getClientDao();
			compteDao = daoFactory.getCompteDao();
			try {
				compteDao.importData(gestionMySQL);
				clientDao.importData(gestionMySQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.exit(0);
		}

		clientDao = daoFactory.getClientDao();
		compteDao = daoFactory.getCompteDao();
		saveData = new SyncDataService(compteDao, clientDao);
		stopApp = new StopApp(saveData);		
		
		logIn();
		userService = new UserService(daoFactory, user, cacheCompte);
		manageAccount = new ManageAccount(userService, recupKeyEntry, user, cacheCompte);		

		commands = Arrays.asList(new ExitCommand(stopApp), new GetInfoCommand(user),
				new DepositCommand(manageAccount), new WithdrawlCommand(manageAccount),
				new TransferCommand(manageAccount, cacheClient, cacheCompte, user));
		
		loadInterface();
	}

	private void loadInterface() {
		/*
		 * System.out.println("***** APPLICATION CLIENT ******" +
		 * "\n0. Arrêter le programme" + "\n1. Consulter les soldes des comptes"
		 * + "\n2. Effectuer un dépôt" + "\n3. Effectuer un retrait" +
		 * "\n4. Effectuer un virement entre vos comptes" +
		 * "\nVeuillez choisir une action.");
		 */

		System.out.println("***** APPLICATION CLIENT ******");
		for (TheCommand command : commands) {
			System.out.println(command.getId() + " - " + command.getLibelleMenu());
		}

		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		int line = -1;
		String[] result = {};
		boolean find = false;
		
		try {
			line = Integer.parseInt(keyboard.readLine());
		} catch (NumberFormatException e) {
			System.out.println("Vous n'avez pas saisie un nombre.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (TheCommand command : commands) {
			if(line == command.getId()) {
				command.execute();
				find = true;
			}
		}
		
		if(!find) {
			System.out.println("Ce nombre ne correspond à aucun choix");
		}
				
		
		/*
		 * 
		 * switch (line) { case "0": stopApp.Stop(); break;
		 * 
		 * case "1": System.out.println(user.getInfoUser()); break;
		 * 
		 * case "2": manageAccount.manage("deposit"); break;
		 * 
		 * case "3": manageAccount.manage("withdrawl"); break;
		 * 
		 * case "4": manageAccount.transfer(cacheClient, cacheCompte, user);
		 * break;
		 * 
		 * default: System.out.println(
		 * "\nAucun choix ne correspond à votre sélection.\n"); loadInterface();
		 * break; }
		 */

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
