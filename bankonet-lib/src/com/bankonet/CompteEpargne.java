package com.bankonet;

public class CompteEpargne extends Compte {
	String login;
	String nom;
	String prenom;
	String libelle = "";
	
	public CompteEpargne(String login, String nom, String prenom, int nbCompteEpargnes) {
		super("", 0);
		this.login = login;
		this.nom = nom;
		this.prenom = prenom;
		nbCompteEpargnes++;
		this.numero = "CE" + nbCompteEpargnes;
		this.libelle = "[" + nom + "]_[" + prenom + "]_EPARGNE_" + nbCompteEpargnes;
	}

	public CompteEpargne(String login, String nom, String prenom, double solde, String numero, String libelle) {
		super(numero, solde);
		this.login = login;
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}

	public String toString() {
		return libelle + "=solde:" + super.getSolde() + "&nom:" + nom + "&prenom:" + prenom + "&login:" + login
				+ "&numero:" + numero;
	}

	public String toStringUser() {
		return libelle + "=solde:" + super.getSolde() + "&numero:" + numero;
	}
}
