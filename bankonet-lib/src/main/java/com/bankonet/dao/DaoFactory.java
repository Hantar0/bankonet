package com.bankonet.dao;

import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;

public interface DaoFactory {
	// On définit pour chaque type de DDB les éléments que l'on doit récupérer
	CompteDao getCompteDao();
	ClientDao getClientDao();
}
