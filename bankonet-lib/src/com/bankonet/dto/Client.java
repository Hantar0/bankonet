package com.bankonet.dto;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.bankonet.constantes.TypeCompte;

@Entity
// @Table(name='client')
public class Client {

	@Id
	@GeneratedValue
	private Integer id;

	private String nom;
	private String prenom;
	private String login;
	private String mdp;
	private String comptes_courant;
	private String comptes_epargne;

	@Transient
	private ArrayList<CompteCourant> listeComptesCourants = new ArrayList<>();
	@Transient
	private ArrayList<CompteEpargne> listeComptesEpargnes = new ArrayList<>();

	public Client() {

	}

	public Client(String nom, String prenom, String login, String mdp) {
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.mdp = mdp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getComptes_courant() {
		return comptes_courant;
	}

	public void setComptes_courant(String comptesC) {
		if(comptes_courant!=null&&comptes_courant.contains("[")) {
			this.comptes_courant +=","+ comptesC;
		} else {
			this.comptes_courant = ""+ comptesC;
		}
	}

	public String getComptes_epargne() {
		return comptes_epargne;
	}

	public void setComptes_epargne(String comptesE) {
		if(comptes_epargne!=null&&comptes_epargne.contains("[")) {
			this.comptes_epargne +=","+ comptesE;
		} else {
			this.comptes_epargne = ""+ comptesE;
		}
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
				+ "&comptes_epargnes:" + getCELibelle() + "&mdp:" + getMdp() + "&id:" + getId();
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
