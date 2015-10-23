package com.bankonet.dao.compte;

import java.sql.SQLException;

import com.bankonet.dao.ConnectionSQL;
import com.bankonet.dao.GestionData;
import com.bankonet.dto.Compte;

public class CompteDaoMySql implements CompteDao {

	private ConnectionSQL connectionSQL;
	private String request;

	@Override
	public void saveComptes() {
		// Ne fait rien. Sert pour les Files uniquement
	}

	@Override
	public void updatesComptes(Compte compte) {
		connectionSQL = new ConnectionSQL();
		connectionSQL.connectBDD();

		request = "UPDATE COMPTE SET solde =" + compte.getSolde() + " WHERE libelle ='" + compte.getLibelle()+"'";
		;

		connectionSQL.BDDUpdate(request);
		connectionSQL.closeBDD();
	}

	@Override
	public void addComptes(Compte compte) {
		connectionSQL = new ConnectionSQL();
		connectionSQL.connectBDD();
		
		request = "INSERT INTO COMPTE(`libelle`,`solde`,`numero`,`nom`,`prenom`,`login`) VALUES "+
		"('"+compte.getLibelle()+"', "+compte.getSolde()+", '"+compte.getNumero()+"', '"+compte.getNom()+
		"', '"+compte.getPrenom()+"', '"+compte.getLogin()+"')"
		;
		
		
		connectionSQL.BDDUpdate(request);
		connectionSQL.closeBDD();
	}

	@Override
	public void importData(GestionData gestionData) throws SQLException {
		gestionData.importData("Comptes");
		
	}
}
