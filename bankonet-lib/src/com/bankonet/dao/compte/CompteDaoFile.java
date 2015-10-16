package com.bankonet.dao.compte;

import java.io.File;
import java.util.Map.Entry;

import com.bankonet.cache.MapCompte;
import com.bankonet.constantes.Constantes;
import com.bankonet.dao.GestionFichier;
import com.bankonet.metier.CompteCourant;
import com.bankonet.metier.CompteEpargne;

public class CompteDaoFile implements CompteDao {
	GestionFichier GF;
	MapCompte mapCompte;

	public CompteDaoFile(GestionFichier GF, MapCompte mapCompte) {
		this.GF = GF;
		this.mapCompte = mapCompte;
	}

	public void saveComptes() {
		File f = new File(Constantes.fileComptes);
		f.delete();

		for (Entry<String, CompteCourant> entry : mapCompte.getListeCC().entrySet()) {
			GF.ajouterDansFichier(Constantes.fileComptes, entry.getValue().toString());
		}

		for (Entry<String, CompteEpargne> entry : mapCompte.getListeCE().entrySet()) {
			GF.ajouterDansFichier(Constantes.fileComptes, entry.getValue().toString());
		}
	}
}
