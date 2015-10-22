package com.bankonet.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.bankonet.cache.CacheClient;
import com.bankonet.cache.CacheCompte;
import com.bankonet.commands.AddAccountCCommand;
import com.bankonet.commands.AddAccountECommand;
import com.bankonet.commands.ChangeClientName;
import com.bankonet.commands.DeleteAllClientCommand;
import com.bankonet.commands.ExitCommand;
import com.bankonet.commands.FindClientByFirstN;
import com.bankonet.commands.FindClientByName;
import com.bankonet.commands.InitBDDCommand;
import com.bankonet.commands.OpenAccountCommand;
import com.bankonet.commands.RemoveClientCommand;
import com.bankonet.commands.ShowAllClientCommand;
import com.bankonet.commands.TheCommand;
import com.bankonet.dao.DaoFactory;
import com.bankonet.dao.DaoFactoryFile;
import com.bankonet.dao.DaoFactoryJPA;
import com.bankonet.dao.DaoFactoryMySql;
import com.bankonet.dao.GestionFichier;
import com.bankonet.dao.GestionMySQL;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.metier.StopApp;
import com.bankonet.metier.SyncDataService;
import com.bankonet.metier.client.FindService;
import com.bankonet.metier.client.InitService;

public class ConseillerApp {
	private CacheClient cacheClient = new CacheClient();
	private CacheCompte cacheCompte = new CacheCompte();
	private GestionFichier gestionFichier = new GestionFichier(cacheClient, cacheCompte);
	private GestionMySQL gestionMySQL = new GestionMySQL(cacheClient, cacheCompte);
	private DaoFactory daoFactory;
	private BankOfficerService bankOfficierService;
	private CompteDao compteDao;
	private ClientDao clientDao;
	private SyncDataService saveFile;
	private StopApp stopApp;
	private InitService initService;
	private List<TheCommand> commands;
	private FindService findService;
	private RecupKeyEntry keyEntry;

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
		case "JPA":
			daoFactory = new DaoFactoryJPA("bankonet-lib", cacheClient, cacheCompte);
			clientDao = daoFactory.getClientDao();
			compteDao = daoFactory.getCompteDao();
			try {
				compteDao.importData(null);
				clientDao.importData(null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			initService = new InitService(clientDao);
			findService = new FindService(clientDao);
			break;
		default:
			System.exit(0);
		}
		saveFile = new SyncDataService(compteDao, clientDao);
		stopApp = new StopApp(saveFile);
		keyEntry = new RecupKeyEntry(cacheCompte);
		// import des datas

		bankOfficierService = new BankOfficerService(cacheClient, saveFile, cacheCompte);

		commands = Arrays.asList(new ExitCommand(stopApp), new OpenAccountCommand(bankOfficierService),
				new ShowAllClientCommand(cacheClient), new AddAccountCCommand(bankOfficierService),
				new AddAccountECommand(bankOfficierService), new InitBDDCommand(initService),
				new FindClientByName(findService, keyEntry), new FindClientByFirstN(findService, keyEntry),
				new ChangeClientName(keyEntry, findService), new RemoveClientCommand(keyEntry, findService),
				new DeleteAllClientCommand(keyEntry, findService, cacheClient));

		loadInterface();

		saveFile.syncFiles();

	}

	private void loadInterface() {
		/*
		 * System.out.println("***** APPLICATION CONSEILLER BANCAIRE ******" +
		 * "\n0. Arrêter le programme" + "\n1. Ouvrir un compte" +
		 * "\n2. Lister tous les clients" + "\n3. Ajouter un compte courant" +
		 * "\n4. Ajouter un compte épargne" + "\n5. Autoriser un découvert" +
		 * "\nVeuillez choisir une action.");
		 */

		System.out.println("***** APPLICATION CONSEILLER BANCAIRE ******");
		for (TheCommand command : commands) {
			System.out.println(command.getId() + " - " + command.getLibelleMenu());
		}

		// Declaration
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		int line = -1;
		boolean find = false;

		try {
			line = Integer.parseInt(keyboard.readLine());
		} catch (NumberFormatException e) {
			System.out.println("Vous n'avez pas saisie un nombre.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (TheCommand command : commands) {
			if (line == command.getId()) {
				command.execute();
				find = true;
			}
		}

		if (!find) {
			System.out.println("Ce nombre ne correspond à aucun choix");
		} /*
			 * 
			 * switch (line) { case "0": stopApp.Stop(); break; case "1":
			 * createData.CreateClient(); break; case "2":
			 * System.out.println(mapClient.showData()); break; case "3":
			 * createData.chooseClient(TypeCompte.CC,"createAccount"); break;
			 * 
			 * case "4": createData.chooseClient(TypeCompte.CE,"createAccount");
			 * break;
			 * 
			 * case "5": createData.chooseClient(TypeCompte.CE,"setDiscovered");
			 * break;
			 * 
			 * default: System.out.println("Choix non valide.");
			 * loadInterface(); break; }
			 */

		loadInterface();

	}
}
