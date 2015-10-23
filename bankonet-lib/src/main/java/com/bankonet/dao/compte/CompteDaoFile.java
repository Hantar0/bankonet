package com.bankonet.dao.compte;

import java.io.File;
import java.sql.SQLException;
import java.util.Map.Entry;

import com.bankonet.cache.CacheCompte;
import com.bankonet.constantes.Constants;
import com.bankonet.dao.GestionData;
import com.bankonet.dao.GestionFichier;
import com.bankonet.dto.Compte;
import com.bankonet.dto.CompteCourant;
import com.bankonet.dto.CompteEpargne;

public class CompteDaoFile implements CompteDao {
	GestionFichier GF;
	CacheCompte mapCompte;

	public CompteDaoFile(GestionFichier GF, CacheCompte mapCompte) {
		this.GF = GF;
		this.mapCompte = mapCompte;
	}

	public void saveComptes() {
		File f = new File(Constants.fileComptes);
		f.delete();

		for (Entry<String, CompteCourant> entry : mapCompte.getListeCC().entrySet()) {
			GF.ajouterDansFichier(Constants.fileComptes, entry.getValue().toString());
		}

		for (Entry<String, CompteEpargne> entry : mapCompte.getListeCE().entrySet()) {
			GF.ajouterDansFichier(Constants.fileComptes, entry.getValue().toString());
		}
	}

	@Override
	public void updatesComptes(Compte compte) {
		saveComptes();
	}

	@Override
	public void addComptes(Compte compte) {
		saveComptes();
	}

	@Override
	public void importData(GestionData gestionData) throws SQLException {
		gestionData.importData(Constants.fileComptes);		
	}

}
