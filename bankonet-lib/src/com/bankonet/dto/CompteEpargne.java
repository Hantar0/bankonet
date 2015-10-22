package com.bankonet.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="compte")
@DiscriminatorValue("E")
public class CompteEpargne extends Compte {
	//Integer id;
	/*String login;
	String nom;
	String prenom;
	String libelle = "";
	private Double solde;*/
	
	public CompteEpargne() {
		
	}
	
	public CompteEpargne(String login, String nom, String prenom, int nbCompteEpargnes) {
		super("", 0.0, nom, prenom, login);
		super.setLogin(login);
		super.setNom(nom);
		super.setPrenom(prenom);
		nbCompteEpargnes++;
		super.setNumero("CE" + nbCompteEpargnes);
		super.setLibelle("[" + nom + "]_[" + prenom + "]_EPARGNE_" + nbCompteEpargnes);
	}

	public CompteEpargne(String login, String nom, String prenom, Double solde, String numero, String libelle) {
		super(numero, solde, nom, prenom, login);
		super.setLogin(login);
		super.setNom(nom);
		super.setPrenom(prenom);
		super.setNumero(numero);
		super.setLibelle(libelle);
	}

	public String toString() {
		return super.getLibelle() + "=solde:" + getSolde() + "&nom:" + super.getNom() + "&prenom:" + super.getPrenom() + "&login:" + super.getLogin()
				+ "&numero:" + getNumero();
	}

	public String toStringUser() {
		return getLibelle() + "=solde:" + super.getSolde() + "&numero:" + getNumero();
	}
	
	/*public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}*/
/*
	public String getNumero() {
		return super.getNumero();
	}

	public void setNumero(String numero) {
		super.numero = numero;
	}

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde += solde;
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

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}*/
	


	public String getLibelle() {
		return super.getLibelle();
	}
}
