package com.bankonet.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bankonet.Client;
import com.bankonet.MapClient;
import com.bankonet.StopApp;
import com.bankonet.UpdateAccount;
import com.bankonet.gestionFichier;
import com.bankonet.importFile;

public class Launcher {
	static String login;
	static Client user;

	public static void main(String[] args) {

		importFile.importF();

		logIn();

		loadInterface();

		gestionFichier.saveData();
	}

	private static void loadInterface() {
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
			StopApp.Stop();
			break;

		case "1":
			System.out.println(user.getInfoUser());
			break;

		case "2":
			UpdateAccount.manage(user, "deposit");
			break;

		case "3":
			UpdateAccount.manage(user, "withdrawal");
			break;
			
		case "4":
			UpdateAccount.transfer(user);
			break;

		default:
			System.out.println("\nAucun choix ne correspond à votre sélection.\n");
			loadInterface();
			break;
		}

		loadInterface();

	}

	private static void logIn() {
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
		user = MapClient.getClient(login);
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
