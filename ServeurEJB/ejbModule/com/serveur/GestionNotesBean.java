package com.serveur;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GestionNotesBean implements GestionNotes{

	@PersistenceContext
	EntityManager em;

	public void ajouter(Note note) {
		em.persist(note);
	}

	/**
	 * 
	 * @return L'ensemble des notes présentes dans la base
	 */
	public List<Note> listNotes() {
		return em.createQuery("SELECT p FROM Note p").getResultList();
	}

	/**
	 * 
	 * @param listId
	 * @return la liste des notes dont l'ID est passé en paramètre
	 */
	public List<Note> listNotes(List<String> listId) {
		/*List<Note> notes = new ArrayList<Note>();
		for (String s : listId) {
			notes.add(em.find(Note.class, s));
		}*/
		return em.createQuery("SELECT p FROM Note p ORDER BY p.id").getResultList();
	}

	public Note rechercheNote(String id) {
		return em.find(Note.class, id);
	}

}
