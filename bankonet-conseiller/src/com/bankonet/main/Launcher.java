package com.bankonet.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bankonet.MapClient;
import com.bankonet.StopApp;
import com.bankonet.TypeCompte;
import com.bankonet.createData;
import com.bankonet.gestionFichier;
import com.bankonet.importFile;

public class Launcher {
	public static void main(String[] args) {

		importFile.importF();

		loadInterface();

		gestionFichier.saveData();
	}

	private static void loadInterface() {
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
			StopApp.Stop();
			break;
		case "1":
			createData.CreateClient();
			break;
		case "2":
			System.out.println(MapClient.showData());
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
