package com.bankonet.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bankonet.cache.CacheAccount;
import com.bankonet.constantes.TypeCompte;

public class RecupKeyEntry {
	private CacheAccount cacheCompte;
	
	public RecupKeyEntry(CacheAccount cacheCompte) {
		this.cacheCompte = cacheCompte;
	}

	public String getKeyString(String libelle) {		
		return getKey(libelle);
	}
	
	public double getKeyDouble(String libelle) {
		String key = getKey(libelle);
		double value;
		try
		{
			value = Double.parseDouble(key);
			if(value<0) {
				System.out.println("Le nombre saisie doit être positif.");
				value = getKeyDouble(libelle);
			}
		}
		catch(NumberFormatException e)
		{
			System.out.println("Il ne s'agit pas d'un nombre.");
			value = getKeyDouble(libelle);
		}
		return value;
		
	}
	
	private String getKey(String libelle){
		String keyEntry="";
		System.out.println("Saisir "+libelle+" :");
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		try {
			keyEntry = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(keyEntry.isEmpty()) {
			System.out.println("Le champs est vide");
			keyEntry= getKey(libelle);
		}
		return keyEntry;
	}
	

	public String verifyAccount(String libelle) {
		String acc = getKeyString(libelle);
		if (cacheCompte.getCompte(acc, TypeCompte.CC) == null
				&& cacheCompte.getCompte(acc, TypeCompte.CE) == null) {
			System.out.println("Ce choix ne correspond à aucun compte.\n");
			acc = verifyAccount(libelle);
		}
		return acc;
	}
}
