package com.bankonet.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bankonet.cache.MapClient;
import com.bankonet.cache.MapCompte;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.dao.DaoFactory;
import com.bankonet.dao.DaoFactoryFile;
import com.bankonet.dao.DaoFactoryMySql;
import com.bankonet.dao.GestionFichier;
import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;
import com.bankonet.metier.CreateData;
import com.bankonet.metier.ImportFile;
import com.bankonet.metier.SaveFiles;
import com.bankonet.metier.StopApp;

public class ConseillerApp {
	private MapClient mapClient = new MapClient();
	private MapCompte mapCompte = new MapCompte();
	private GestionFichier gestionFichier = new GestionFichier(mapClient, mapCompte);
	private ImportFile importFile = new ImportFile(gestionFichier);
	private DaoFactory daoFactory;
	private CreateData createData;
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
		createData = new CreateData(mapClient, saveFile, mapCompte);
		loadInterface();

		saveFile.saveData();

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
