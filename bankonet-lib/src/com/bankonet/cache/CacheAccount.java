package com.bankonet.cache;

import java.util.HashMap;
import java.util.Map;

import com.bankonet.constantes.TypeCompte;
import com.bankonet.dto.Compte;
import com.bankonet.dto.CompteCourant;
import com.bankonet.dto.CompteEpargne;

public class CacheAccount {
	private Map<String,CompteCourant> listCC = new HashMap<>();
	private Map<String,CompteEpargne> listCE = new HashMap<>();

	public void ajouterCompte(Compte compte, TypeCompte type) {
		if(type==TypeCompte.CC) {
			getListeCC().put(compte.getLibelle(),(CompteCourant) compte);
		} else {
			getListeCE().put(compte.getLibelle(),(CompteEpargne) compte);
		}
	}
	
	public Compte getCompte(String libelle, TypeCompte type) {
		if (type==TypeCompte.CC) {
			return listCC.get(libelle);
		} else {
			return listCE.get(libelle);
		}		 				
	}
	
	public Map<String,CompteCourant> getListeCC() {
		return listCC;
	}
	
	public Map<String,CompteEpargne> getListeCE() {
		return listCE;
	}
}
