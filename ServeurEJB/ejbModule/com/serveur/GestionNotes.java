package com.serveur;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface GestionNotes {

	public void ajouter(Note note);
	
	/**
	 * 
	 * @return L'ensemble des notes présentes dans la base
	 */
	public List<Note> listNotes();
	
	/**
	 * 
	 * @param listId
	 * @return la liste des notes dont l'ID est passé en paramètre
	 */
	public List<Note> listNotes(List<String> listId);
	
	public Note rechercheNote(String id);
}
