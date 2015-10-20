package com.bankonet.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import com.bankonet.cache.CacheClient;
import com.bankonet.cache.CacheAccount;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.dao.ConnectionSQL;
import com.bankonet.dao.DaoFactory;
import com.bankonet.dao.DaoFactoryFile;
import com.bankonet.dao.DaoFactoryMySql;
import com.bankonet.dao.GestionFichier;
import com.bankonet.dao.GestionMySQL;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.metier.SyncDataService;
import com.bankonet.metier.StopApp;

public class ConseillerApp {
	private CacheClient mapClient = new CacheClient();
	private CacheAccount mapCompte = new CacheAccount();
	private GestionFichier gestionFichier = new GestionFichier(mapClient, mapCompte);
	private GestionMySQL gestionMySQL = new GestionMySQL(mapClient, mapCompte);
	private DaoFactory daoFactory;
	private BankOfficerService createData;
	private CompteDao compteDao;
	private ClientDao clientDao;
	private SyncDataService saveFile;
	private StopApp stopApp;

	public void start(String dao) {
		switch (dao) {
		case "Files":
			daoFactory = new DaoFactoryFile(gestionFichier, mapClient, mapCompte);
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
			daoFactory = new DaoFactoryMySql(mapClient, mapCompte);
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
		saveFile = new SyncDataService(compteDao, clientDao);
		stopApp = new StopApp(saveFile);

		// import des datas
		
		
		createData = new BankOfficerService(mapClient, saveFile, mapCompte);
		loadInterface();

		saveFile.syncFiles();

	}
	
	private void loadInterface() {
		System.out.println("***** APPLICATION CONSEILLER BANCAIRE ******" + "\n0. Arrêter le programme"
				+ "\n1. Ouvrir un compte" + "\n2. Lister tous les clients" + "\n3. Ajouter un compte courant"
				+ "\n4. Ajouter un compte épargne" + "\n5. Autoriser un découvert" + "\nVeuillez choisir une action.");

		// Declaration
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
			createData.CreateClient();
			break;
		case "2":
			System.out.println(mapClient.showData());
			break;
		case "3":
			createData.chooseClient(TypeCompte.CC,"createAccount");
			break;

		case "4":
			createData.chooseClient(TypeCompte.CE,"createAccount");
			break;
			
		case "5":
			createData.chooseClient(TypeCompte.CE,"setDiscovered");
			break;

		default:
			System.out.println("Choix non valide.");
			loadInterface();
			break;
		}

		loadInterface();

	}
}
