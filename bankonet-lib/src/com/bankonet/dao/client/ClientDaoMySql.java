package com.bankonet.dao.client;

import java.sql.SQLException;

import com.bankonet.dao.ConnectionSQL;
import com.bankonet.dao.GestionData;
import com.bankonet.dao.GestionFichier;
import com.bankonet.dto.Client;
import com.bankonet.dto.Compte;

public class ClientDaoMySql implements ClientDao {

	private ConnectionSQL connectionSQL;
	private String request;

	@Override
	public void saveClients() {
		// Ne fait rien. Sert pour les Files uniquement

	}

	@Override
	public void addClient(Client client) {
		connectionSQL = new ConnectionSQL();
		connectionSQL.connectBDD();

		request = "INSERT INTO CLIENTS(`login`,`nom`,`prenom`,`mdp`,`comptes_courant`,`comptes_epargne`) VALUES " + "('"
				+ client.getLogin() + "', '" + client.getNom() + "', '" + client.getPrenom() + "', '" + client.getMdp()
				+ "', '" + client.getCCLibelle() + "', '" + client.getCELibelle() + "')";

		connectionSQL.BDDUpdate(request);
		connectionSQL.closeBDD();
	}

	@Override
	public void updateClient(Client client) {
		connectionSQL = new ConnectionSQL();
		connectionSQL.connectBDD();

		request = "UPDATE CLIENTS SET comptes_courant ='" + client.getCCLibelle() + "', comptes_epargne ='"
				+ client.getCELibelle() + "' WHERE login ='" + client.getLogin()+"'";
		;

		connectionSQL.BDDUpdate(request);
		connectionSQL.closeBDD();
	}

	@Override
	public void importData(GestionData gestionData) throws SQLException {
		gestionData.importData("Clients");

	}

}
