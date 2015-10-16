package com.bankonet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapCompte {
	static Map<String,CompteCourant> listCC = new HashMap<>();
	static Map<String,CompteEpargne> listCE = new HashMap<>();

	public static void ajouterCompte(Compte compte, TypeCompte type) {
		if(type==TypeCompte.CC) {
			listCC.put(compte.getLibelle(),(CompteCourant) compte);
		} else {
			listCE.put(compte.getLibelle(),(CompteEpargne) compte);
		}
	}
	
	public static Compte getCompte(String libelle, TypeCompte type) {
		if (type==TypeCompte.CC) {
			return listCC.get(libelle);
		} else {
			return listCE.get(libelle);
		}		 				
	}
}
