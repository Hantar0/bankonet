package com.bankonet.dao.client;

import java.io.File;
import java.sql.SQLException;
import java.util.Map.Entry;

import com.bankonet.cache.CacheClient;
import com.bankonet.constantes.Constants;
import com.bankonet.dao.GestionData;
import com.bankonet.dao.GestionFichier;
import com.bankonet.dto.Client;

public class ClientDaoFile implements ClientDao {
	GestionFichier GF;
	CacheClient mapClient;

	public ClientDaoFile(GestionFichier GF, CacheClient mapClient) {
		this.GF = GF;
		this.mapClient = mapClient;
	}

	public void saveClients() {
		File f = new File(Constants.fileClient);
		f.delete();

		for (Entry<String, Client> entry : mapClient.listClient.entrySet()) {
			GF.ajouterDansFichier(Constants.fileClient, entry.getValue().toString());
		}
	}

	@Override
	public void addClient(Client client) {
		saveClients();
	}

	@Override
	public void updateClient(Client client) {
		saveClients();
	}

	@Override
	public void importData(GestionData gestionData) throws SQLException {
		gestionData.importData(Constants.fileClient);			
	}

	@Override
	public Client findClient(String libelleSearch, String column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Client client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void truncateTable() {
		// TODO Auto-generated method stub
		
	}


}
