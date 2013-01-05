package com.serveur;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface GestionParcours {

	public void ajouter(Parcours parcours);
	
	public List<Parcours> listParcours();

	public void supprimer(String id);
	
	public Parcours findParcours(Parcours parcours);
	
}
