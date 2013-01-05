package com.serveur;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface GestionNotes {

	public void ajouter(Note note);
	
	/**
	 * 
	 * @return L'ensemble des notes pr�sentes dans la base
	 */
	public List<Note> listNotes();
	
	/**
	 * 
	 * @param listId
	 * @return la liste des notes dont l'ID est pass� en param�tre
	 */
	public List<Note> listNotes(List<String> listId);
	
	public Note rechercheNote(String id);
}
