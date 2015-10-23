package com.bankonet.dao.compte;

import java.sql.SQLException;

import com.bankonet.dao.GestionData;
import com.bankonet.dto.Compte;

public interface CompteDao {
	public void saveComptes();

	public void updatesComptes(Compte compte);

	public void addComptes (Compte compte);

	public void importData(GestionData gestionData) throws SQLException;
}
