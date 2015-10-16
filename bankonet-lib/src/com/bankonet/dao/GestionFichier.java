package com.bankonet.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map.Entry;

import com.bankonet.cache.MapClient;
import com.bankonet.cache.MapCompte;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.metier.Client;
import com.bankonet.metier.CompteCourant;
import com.bankonet.metier.CompteEpargne;

public class GestionFichier {
	MapClient mapClient;
	MapCompte mapCompte;

	public GestionFichier(MapClient mapClient, MapCompte mapCompte) {
		this.mapClient = mapClient;
		this.mapCompte = mapCompte;
	}

	public void ajouterDansFichier(String nomFichier, String element) {
		File f = new File(nomFichier);

		try {
			FileWriter fw = new FileWriter(f, true);

			fw.write(element);
			fw.write("\r\n");

			fw.close();
		} catch (IOException exception) {
			System.out.println("Erreur lors de l'écriture : " + exception.getMessage());
		}
	}

	public void LireFichier(String nomFichier) {
		String[] splitElements = null;
		String[] splitFirstElement = null;
		ArrayList<String> elements = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(nomFichier));
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					elements.add(line);
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
			} finally {
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String e : elements) {
			splitElements = e.split("&");
			splitFirstElement = splitElements[0].split("=");
			initData(splitElements, splitFirstElement, nomFichier);
		}
	}

	private void initData(String[] splitElements, String[] splitFirstElement, String nomFichier) {
		if (nomFichier.contains("clients")) {

			String login = splitFirstElement[0];
			String nom = splitFirstElement[1].split(":")[1];
			String prenom = splitElements[1].split(":")[1];
			String mdp = splitElements[4].split(":")[1];
			String[] ComptesC = splitElements[2].split(":");
			String[] ComptesE = splitElements[3].split(":");

			Client client = new Client(nom, prenom, login, mdp);

			if (ComptesC.length > 1) {
				String[] listeCC = ComptesC[1].split(",");

				for (int i = 0; i < listeCC.length; i++) {
					client.addCompte(mapCompte.getCompte(listeCC[i], TypeCompte.CC), TypeCompte.CC);
				}
			}

			if (ComptesE.length > 1) {
				String[] listeCE = splitElements[3].split(":")[1].split(",");

				for (int i = 0; i < listeCE.length; i++) {
					client.addCompte(mapCompte.getCompte(listeCE[i], TypeCompte.CE), TypeCompte.CE);
				}
			}
			mapClient.ajouterClient(client);

		} else if (nomFichier.contains("comptes")) {

			String libelle = splitFirstElement[0];
			double solde = Double.parseDouble(splitFirstElement[1].split(":")[1]);
			String nom = splitElements[1].split(":")[1];
			String prenom = splitElements[2].split(":")[1];
			String login = splitElements[3].split(":")[1];
			String numero = splitElements[4].split(":")[1];

			if (splitFirstElement[0].contains("COURANT")) {
				CompteCourant compteC = new CompteCourant(login, nom, prenom, solde, numero, libelle);
				mapCompte.ajouterCompte(compteC, TypeCompte.CC);
			} else {
				CompteEpargne compteE = new CompteEpargne(login, nom, prenom, solde, numero, libelle);
				mapCompte.ajouterCompte(compteE, TypeCompte.CE);
			}

		}
	}
}
