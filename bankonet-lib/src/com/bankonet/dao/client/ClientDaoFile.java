package com.bankonet.dao.client;

import java.io.File;
import java.util.Map.Entry;

import com.bankonet.cache.CacheClient;
import com.bankonet.constantes.Constants;
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
}
