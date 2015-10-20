package com.bankonet.dto;

public class CompteCourant extends Compte {
	String login;
	String nom;
	String prenom;
	private String libelle = "";
	double découvertAutorise=200;

	public CompteCourant(String login, String nom, String prenom, int nbCompteCourants) {
		super("", 0, nom, prenom, login);
		this.login = login;
		this.nom = nom;
		this.prenom = prenom;
		nbCompteCourants++;
		this.numero = "CC" + nbCompteCourants;
		this.libelle = "[" + nom + "]_[" + prenom + "]_COURANT_" + nbCompteCourants;
	}

	public CompteCourant(String login, String nom, String prenom, double solde, String numero, String libelle) {
		super(numero, solde, nom, prenom, login);
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
	
	public double getDecouvert() {
		return découvertAutorise;
	}
	
	public void setDecouvert(double decouvert) {
		découvertAutorise = decouvert;
	}
	
}
