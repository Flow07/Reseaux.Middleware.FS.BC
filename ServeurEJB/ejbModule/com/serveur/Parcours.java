package com.serveur;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Parcours implements Serializable {

	@Id
	private String id;
	private double distance;
	private int denivele;
	private String commentaire;
	private String categorie;
	private double lonStart;
	private double latStart;
	private double lonEnd;
	private double latEnd;
	private Date dateCreation;
	private Note[] notes;
	
	public Parcours(){
		super();
	}
	
	public Parcours(String id){
		super();
		this.id=id;
	}
	public Parcours(String id, double distance, int denivele, String commentaire,
			String categorie, double lonStart, double latStart, double lonEnd,
			double latEnd, Date dateCreation, Note[] notes) {
		super();
		this.id=id;
		this.distance = distance;
		this.denivele = denivele;
		this.commentaire = commentaire;
		this.categorie = categorie;
		this.lonStart = lonStart;
		this.latStart = latStart;
		this.lonEnd = lonEnd;
		this.latEnd = latEnd;
		this.dateCreation=dateCreation;
		this.notes = notes;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getDenivele() {
		return denivele;
	}
	public void setDenivele(int denivele) {
		this.denivele = denivele;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public double getLonStart() {
		return lonStart;
	}
	public void setLonStart(double lonStart) {
		this.lonStart = lonStart;
	}
	public double getLatStart() {
		return latStart;
	}
	public void setLatStart(double latStart) {
		this.latStart = latStart;
	}
	public double getLonEnd() {
		return lonEnd;
	}
	public void setLonEnd(double lonEnd) {
		this.lonEnd = lonEnd;
	}
	public double getLatEnd() {
		return latEnd;
	}
	public void setLatEnd(double latEnd) {
		this.latEnd = latEnd;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Note[] getNotes() {
		return notes;
	}
	public void setNotes(Note[] notes) {
		this.notes = notes;
	}
	
}
