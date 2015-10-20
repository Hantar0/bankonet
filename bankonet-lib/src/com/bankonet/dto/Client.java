package com.bankonet.dto;

import java.util.ArrayList;

import com.bankonet.constantes.TypeCompte;

public class Client {
	private String nom;
	private String prenom;
	private String login;
	private String mdp;
	private ArrayList<CompteCourant> listeComptesCourants = new ArrayList<>();
	private ArrayList<CompteEpargne> listeComptesEpargnes = new ArrayList<>();

	public Client(String nom, String prenom, String login, String mdp) {
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.mdp = mdp;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getCCLibelle() {
		String libelle = "";
		for (CompteCourant cc : listeComptesCourants) {
			libelle += cc.getLibelle() + ",";
		}
		if (libelle.length() > 0) {
			libelle = libelle.substring(0, libelle.length() - 1);
		}
		return libelle;
	}

	public String getCELibelle() {
		String libelle = "";
		for (CompteEpargne ce : listeComptesEpargnes) {
			libelle += ce.getLibelle() + ",";
		}

		if (libelle.length() > 0) {
			libelle = libelle.substring(0, libelle.length() - 1);
		}
		return libelle;
	}

	public void addCompte(Compte compte, TypeCompte type) {
		if (type == TypeCompte.CC) {

			listeComptesCourants.add((CompteCourant) compte);
		} else {
			listeComptesEpargnes.add((CompteEpargne) compte);
		}
	}

	public String toString() {
		return login + "=nom:" + nom + "&prenom:" + prenom + "&comptes_courants:" + getCCLibelle()
				+ "&comptes_epargnes:" + getCELibelle() + "&mdp:" + getMdp();
	}

	public String getInfo() {
		return "login : " + login + "  nom : " + nom + "  prenom : " + prenom + "  nombre de compte courant : "
				+ getNumberAccount(TypeCompte.CC) + "  nombre de compte épargne : " + getNumberAccount(TypeCompte.CE);
	}

	public String getInfoUser() {
		String infos = "";
		for (int i = 0; i < getNumberAccount(TypeCompte.CC); i++) {
			infos += listeComptesCourants.get(i).toStringUser() + "\n";
		}
		for (int i = 0; i < getNumberAccount(TypeCompte.CE); i++) {
			infos += listeComptesEpargnes.get(i).toStringUser() + "\n";
		}
		return infos;
	}

	public int getNumberAccount(TypeCompte cc) {
		if (cc.equals(TypeCompte.CC)) {
			return listeComptesCourants.size();
		} else {
			return listeComptesEpargnes.size();
		}
	}

	public String getLibellesAccount() {
		return getCCLibelle() + " " + getCELibelle();
	}
}
