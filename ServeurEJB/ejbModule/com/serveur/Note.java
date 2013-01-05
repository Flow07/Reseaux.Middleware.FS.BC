package com.serveur;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Note implements Serializable{

	@Id
	private String id;
	private double lon;
	private double lat;
	private String commentaire;
	private Date dateCreation;
	
	public Note(){
		super();
	}
	
	public Note(String id){
		super();
		this.id=id;
	}
	
	public Note(String id, double lon, double lat, String commentaire, Date dateCreation) {
		super();
		this.id=id;
		this.lon = lon;
		this.lat = lat;
		this.commentaire = commentaire;
		this.dateCreation = dateCreation;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	public String toString() {
		return "id = "+this.id+" commentaire = "+this.commentaire;
	}
}
