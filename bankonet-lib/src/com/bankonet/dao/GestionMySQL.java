package com.bankonet.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bankonet.cache.CacheAccount;
import com.bankonet.cache.CacheClient;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.dto.Client;
import com.bankonet.dto.Compte;
import com.bankonet.dto.CompteCourant;
import com.bankonet.dto.CompteEpargne;

public class GestionMySQL extends GestionData {

	private CacheAccount cacheCompte;
	private CacheClient cacheClient;
	private ConnectionSQL connectionSQL;
	private String request;

	public GestionMySQL(CacheClient cacheClient, CacheAccount cacheCompte) {
		this.cacheClient = cacheClient;
		this.cacheCompte = cacheCompte;
	}

	@Override
	public void importData(String typeTable) throws SQLException {
		connectionSQL = new ConnectionSQL();
		connectionSQL.connectBDD();
		ResultSet result;
		if(typeTable.equals("Comptes")) {

			request = "SELECT * FROM COMPTES";

			result= connectionSQL.BDDQuery(request);
			
			while (result.next()) {
				String libelle = result.getString("libelle");
				int solde = result.getInt("solde");
				String numero = result.getString("numero");
				String login = result.getString("login");
				String nom = result.getString("nom");
				String prenom = result.getString("prenom");
				TypeCompte type;
				Compte compte;
				if(libelle.contains("COURANT")) {
					compte = new CompteCourant(login, nom, prenom, solde, numero, libelle);
					 type = TypeCompte.CC;
				} else {
					 compte = new CompteEpargne(login, nom, prenom, solde, numero, libelle);
					 type = TypeCompte.CE;
				}
				
				cacheCompte.ajouterCompte(compte, type);
				
			}
		} else if(typeTable.equals("Clients")) {
			request = "SELECT * FROM CLIENTS";

			result = connectionSQL.BDDQuery(request);

			while (result.next()) {
				String login = result.getString("login");
				String nom = result.getString("nom");
				String prenom = result.getString("prenom");
				String mdp = result.getString("mdp");
				String comptes_courant = result.getString("comptes_courant");
				String comptes_epargne = result.getString("comptes_epargne");
				Client client = new Client(nom, prenom, login, mdp);
				
				if(comptes_courant!=null && comptes_courant!="") {
					String[] listeCC = comptes_courant.split(",");
					for(int i=0; i< listeCC.length;i++) {
						client.addCompte(cacheCompte.getCompte(listeCC[i], TypeCompte.CC), TypeCompte.CC);
					}
				}
				
				if(comptes_epargne!=null && comptes_epargne!="") {
					String[] listeCE = comptes_epargne.split(",");
					for(int i=0; i< listeCE.length;i++) {
						client.addCompte(cacheCompte.getCompte(listeCE[i], TypeCompte.CE), TypeCompte.CE);
					}
				}
				
				cacheClient.ajouterClient(client);				
			}
		}
		

		connectionSQL.closeBDD();

	}

}
