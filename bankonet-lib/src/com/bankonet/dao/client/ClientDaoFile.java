package com.bankonet.dao.client;

import java.io.File;
import java.util.Map.Entry;

import com.bankonet.cache.MapClient;
import com.bankonet.constantes.Constantes;
import com.bankonet.dao.GestionFichier;
import com.bankonet.metier.Client;

public class ClientDaoFile implements ClientDao {
	GestionFichier GF;
	MapClient mapClient;

	public ClientDaoFile(GestionFichier GF, MapClient mapClient) {
		this.GF = GF;
		this.mapClient = mapClient;
	}

	public void saveClients() {
		File f = new File(Constantes.fileClient);
		f.delete();

		for (Entry<String, Client> entry : mapClient.listClient.entrySet()) {
			GF.ajouterDansFichier(Constantes.fileClient, entry.getValue().toString());
		}
	}
}
