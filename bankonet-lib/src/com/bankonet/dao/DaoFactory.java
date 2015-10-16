package com.bankonet.dao;

import com.bankonet.dao.client.ClientDao;
import com.bankonet.dao.compte.CompteDao;

public interface DaoFactory {
	// On d�finit pour chaque type de DDB les �l�ments que l'on doit r�cup�rer
	CompteDao getCompteDao();
	ClientDao getClientDao();
}
