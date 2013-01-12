package com.serveur;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GestionParcoursBean implements GestionParcours{

	@PersistenceContext
	EntityManager em;
	
	public void ajouter(Parcours parcours) {
		em.persist(parcours);
	}

	public void supprimer(Long id) {
		em.createQuery("delete from Parcours p where id = "+id).executeUpdate();
	}

	public List<Parcours> listParcours() {
		return em.createQuery("SELECT p FROM Parcours p").getResultList();
	}

	public Parcours findParcours(Parcours parcours) {
		return em.find(Parcours.class, parcours);
	}

}
