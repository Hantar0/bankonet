package com.bankonet.dao.compte;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.bankonet.cache.CacheCompte;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.dao.GestionData;
import com.bankonet.dto.Compte;
import com.bankonet.dto.CompteCourant;

public class CompteDaoJpa implements CompteDao {
	private EntityManagerFactory emFactory;
	private CacheCompte cacheCompte;

	public CompteDaoJpa(EntityManagerFactory emFactory, CacheCompte cacheCompte) {
		this.emFactory = emFactory;
		this.cacheCompte = cacheCompte;
	}

	@Override
	public void saveComptes() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatesComptes(Compte compte) {
	}

	@Override
	public void addComptes(Compte compte) {
		EntityManager em = emFactory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(compte);
		et.commit();
		em.close();
	}

	@Override
	public void importData(GestionData gestionData) throws SQLException {
		EntityManager em = emFactory.createEntityManager();
		// Renvoie une liste de compte
		List<Compte> listeCompte = em.createQuery("select c from Compte c", Compte.class).getResultList();
		for (Compte c : listeCompte) {
			TypeCompte typeCompte;
			System.out.println(c.toString());
			if(c instanceof CompteCourant) {
				typeCompte = TypeCompte.CC;
			} else {
				typeCompte = TypeCompte.CE;
			}
			cacheCompte.ajouterCompte(c, typeCompte);;
		}
		em.close();
	}

}
