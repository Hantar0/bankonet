package com.bankonet.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "compte")
@DiscriminatorValue("C")
public class CompteCourant extends Compte {
	// Integer id;
	/*
	 * String login; String nom; String prenom; private String libelle = "";
	 * private Double solde;
	 */

	@Transient
	Double découvertAutorise = 200.0;

	public CompteCourant() {
	}

	public CompteCourant(String login, String nom, String prenom, int nbCompteCourants) {
		super("", 0.0, nom, prenom, login);
		super.setLogin(login);
		super.setNom(nom);
		super.setPrenom(prenom);
		nbCompteCourants++;
		super.setNumero("CC" + nbCompteCourants);
		super.setLibelle("[" + nom + "]_[" + prenom + "]_COURANT_" + nbCompteCourants);
	}

	public CompteCourant(String login, String nom, String prenom, Double solde, String numero, String libelle) {
		super(numero, solde, nom, prenom, login);
		super.setLogin(login);
		super.setNom(nom);
		super.setPrenom(prenom);
		super.setNumero(numero);
		super.setLibelle(libelle);
	}

	public String toString() {
		return super.getLibelle() + "=solde:" + super.getSolde() + "&nom:" + super.getNom() + "&prenom:" + super.getPrenom()
				+ "&login:" + super.getLogin() + "&numero:" + super.getNumero();
	}

	public String toStringUser() {
		return super.getLibelle() + "=solde:" + super.getSolde() + "&numero:" + super.getNumero();
	}

	public Double getDecouvert() {
		return découvertAutorise;
	}

	public void setDecouvert(Double decouvert) {
		découvertAutorise = decouvert;
	}

	public String getLibelle() {
		return super.getLibelle();
	}
}
