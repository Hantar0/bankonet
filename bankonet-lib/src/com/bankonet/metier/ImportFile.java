package com.bankonet.metier;

import com.bankonet.constantes.Constantes;
import com.bankonet.dao.GestionFichier;

public class ImportFile {
	
	private GestionFichier gestionFichier;

	public ImportFile(GestionFichier gestionFichier) {
		this.gestionFichier = gestionFichier;
	}

	public  void importF() {
		gestionFichier.LireFichier(Constantes.fileComptes);
		gestionFichier.LireFichier(Constantes.fileClient);
	}
}
