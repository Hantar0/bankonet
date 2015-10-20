package com.bankonet.dto;

public  class Compte {

	protected String numero;
	private double solde;
	private String nom;
	private String prenom;
	private String login;
	
	
	public Compte(String numero, double solde, String nom, String prenom, String login) {
		this.numero = numero;
		this.solde=solde;
		this.nom=nom;
		this.prenom=prenom;
		this.login=login;
	}
	public String getNumero() {
		return numero;
	}
	
	public double getSolde() {
		return solde;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public String getLogin() {
		return login;
	}
	public void setSolde(double solde) {
		this.solde += solde;
	}

	public String getLibelle() {
		return "";
	}
}
