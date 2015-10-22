package com.bankonet.dao.client;

import java.sql.SQLException;

import com.bankonet.dao.GestionData;
import com.bankonet.dto.Client;

public interface ClientDao {
	public void saveClients();

	public void addClient(Client client);

	public void updateClient(Client client);

	public void importData(GestionData gestionData) throws SQLException;

	public Client findClient(String libelleSearch, String column);

	void delete(Client client);

	public void truncateTable();
}
