package com.bankonet.dao.client;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.bankonet.cache.CacheClient;
import com.bankonet.cache.CacheCompte;
import com.bankonet.constantes.TypeCompte;
import com.bankonet.dao.GestionData;
import com.bankonet.dto.Client;

public class ClientDaoJpa implements ClientDao {

	private EntityManagerFactory emFactory;
	private CacheClient cacheClient;
	private CacheCompte cacheCompte;

	public ClientDaoJpa(EntityManagerFactory emFactory, CacheClient cacheClient, CacheCompte cacheCompte) {
		this.emFactory = emFactory;
		this.cacheClient = cacheClient;
		this.cacheCompte = cacheCompte;
	}

	@Override
	public void saveClients() {
		// Ne sert que pour files
	}

	@Override
	public void addClient(Client client) {
		EntityManager em = emFactory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(client);
		et.commit();
		em.close();
	}

	@Override
	public void updateClient(Client client) {
		EntityManager em = emFactory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Client newClient = em.find(Client.class, client.getId());
		if (newClient != null) {
			newClient.setNom(client.getNom());
			newClient.setPrenom(client.getPrenom());
			String cc = client.getCCLibelle();
			String ce = client.getCELibelle();
			newClient.setComptes_courant(cc);
			newClient.setComptes_epargne(ce);
			newClient.setMdp(client.getMdp());
			newClient.setLogin(client.getLogin());			
		}
		em.persist(newClient);
		et.commit();
		em.close();
	}

	@Override
	public void importData(GestionData gestionData) throws SQLException {
		EntityManager em = emFactory.createEntityManager();
		// Renvoie une liste de client
		List<Client> listeClient = em.createQuery("select c from Client c", Client.class).getResultList();
		for (Client c : listeClient) {
			TypeCompte type = TypeCompte.CC;
			if (c.getComptes_courant() != null) {
				String[] listComptes = c.getComptes_courant().split(",");
				for (int i = 0; i < listComptes.length; i++) {
					c.addCompte(cacheCompte.getCompte(listComptes[i], type), type);
				}
			}
			
			if (c.getComptes_epargne() != null) {
				type=TypeCompte.CE;
				String[] listComptes = c.getComptes_epargne().split(",");
				for (int i = 0; i < listComptes.length; i++) {
					c.addCompte(cacheCompte.getCompte(listComptes[i], type), type);
				}
			}

			System.out.println(c.toString());
			cacheClient.ajouterClient(c);
		}
		em.close();
	}

	@Override
	public Client findClient(String libelleSearch, String column) {
		EntityManager em = emFactory.createEntityManager();
		String request = "select c from Client c where c." + column + "='" + libelleSearch + "'";
		Query query = em.createQuery(request);

		Client client = (Client) query.getResultList().get(0);
		em.close();
		return client;
	}

	@Override
	public void delete(Client client) {
		EntityManager em = emFactory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		Client c = em.find(Client.class, client.getId());
		if (c != null) {
			em.remove(c);
		}
		et.commit();
		em.close();
		cacheClient.remove(client);
	}

	@Override
	public void truncateTable() {
		EntityManager em = emFactory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.createNativeQuery("truncate table client").executeUpdate();
		et.commit();
		em.close();
		cacheClient.clean();

	}

}
