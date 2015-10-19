package com.bankonet.metier;

import com.bankonet.constantes.Constants;
import com.bankonet.dao.GestionFichier;

public class ImportFile {
	
	private GestionFichier gestionFichier;

	public ImportFile(GestionFichier gestionFichier) {
		this.gestionFichier = gestionFichier;
	}

	public  void importF() {
		gestionFichier.LireFichier(Constants.fileComptes);
		gestionFichier.LireFichier(Constants.fileClient);
	}
}
