package com.geonotes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import com.serveur.GestionNotes;
import com.serveur.GestionParcours;
import com.serveur.Note;
import com.serveur.Parcours;

public class GestionParcoursVM {

	Context context;
	GestionNotes gn;
	static GestionParcours gp;
	static List<Note> notes;
	static List<Note> parcours;
	static List<Note> parcoursStartEnd;
	static Note lastNote = null;
	List<Note> parcoursFinal;
	static List<Parcours> listParcours;
	static int etape = 0;
	static Parcours currentParcours;
	
	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes_) {
		notes = notes_;
	}

	public static List<Note> getParcours() {
		return parcours;
	}

	public static void setParcours(List<Note> parcours_) {
		parcours = parcours_;
	}

	public static List<Note> getParcoursStartEnd() {
		return parcoursStartEnd;
	}

	public static void setParcoursStartEnd(List<Note> parcoursStartEnd_) {
		parcoursStartEnd = parcoursStartEnd_;
	}

	public List<Parcours> getListParcours() {
		return listParcours;
	}

	public void setListParcours(List<Parcours> listParcours_) {
		listParcours = listParcours_;
	}

	public static int getEtape() {
		return etape;
	}

	public static void setEtape(int etape) {
		GestionParcoursVM.etape = etape;
	}

	public static Parcours getCurrentParcours() {
		return currentParcours;
	}

	public static void setCurrentParcours(Parcours currentParcours) {
		GestionParcoursVM.currentParcours = currentParcours;
	}

	@Init
	public void init(){
		try {
			//lastNote = new Note();
			//lastNote.setLat(0.0);
			parcoursFinal = new ArrayList<Note>();
			parcours = new ArrayList<Note>();
			parcoursStartEnd = new ArrayList<Note>();
			context = new InitialContext();
			gp = (GestionParcours) context.lookup("GestionParcoursBean/remote");
			gn = (GestionNotes) context.lookup("GestionNotesBean/remote");
			notes = gn.listNotes();
			listParcours=gp.listParcours();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Command("ajouterNote")
	public void ajouterNote(@BindingParam("c") String commentaire, @BindingParam("l") Double lat, @BindingParam("h") Double lng){
		List<Note> l = gn.listNotes();
		int size = l.size()+1;
		gn.ajouter(new Note(""+size, lat, lng, commentaire, new Date(System.currentTimeMillis())));
	}
	@Command("ajouterNotePourParcours")
	public void ajouterNotePourParcours(@BindingParam("n") Note note){
		lastNote=note;
		System.out.println("ajouterNotePourParcours = "+note);
		parcours.add(note);
	}
	@Command("ajouterNoteDepartArrivePourParcours")
	public void ajouterNoteDepartArrivePourParcours(@BindingParam("n") Note note){
		lastNote=note;
		System.out.println("ajouterNoteDepartArrivePourParcours = "+note);
		parcoursStartEnd.add(note);
	}
	@Command("button")
	public void button(){
		System.out.println("start = "+parcoursStartEnd.get(0));
		for (Note note : parcours) {
			System.out.println(""+note);
		}
		System.out.println("End = "+parcoursStartEnd.get(1));
	}
	@Command("genererParcours")
	public void genererParcours(@BindingParam("z") String commentaire, @BindingParam("y") String categorie){
		double distance = distanceParcours();
		parcoursFinal.add(parcoursStartEnd.get(0));
		if(!parcours.isEmpty()){
			for (Note note : parcours) {
				parcoursFinal.add(note);
			}
		}
		parcoursFinal.add(parcoursStartEnd.get(1));
		Note[] parcoursFinalTab = new Note[parcoursFinal.size()];
		for(int j=0; j<parcoursFinal.size(); j++){
			parcoursFinalTab[j]=parcoursFinal.get(j);
		}
		List<Parcours> l = gp.listParcours();
		int size = l.size()+1;
		gp.ajouter(new Parcours(""+size,distance,0,""+commentaire,""+categorie,parcoursStartEnd.get(0).getLon(),parcoursStartEnd.get(0).getLat(),
				parcoursStartEnd.get(1).getLon(),parcoursStartEnd.get(1).getLat(),new Date(System.currentTimeMillis()),parcoursFinalTab));
	}
	
	public static double getLastLat(){
		if(lastNote==null){
			lastNote=notes.get(0);
		}
		//double l = lastNote.getLat();
		//lastNote.setLat(0.0);
		return lastNote.getLat();
	}
	public static double getLastLng(){
		if(lastNote==null){
			lastNote=notes.get(0);
		}
		return lastNote.getLon();
	}
	
	public static double distance(Note note1, Note note2){
		double d2r = Math.PI / 180;
		double d=0;

		try{
		    double dlong = (note2.getLon() - note1.getLon()) * d2r;
		    double dlat = (note2.getLat() - note1.getLat()) * d2r;
		    double a =
		        Math.pow(Math.sin(dlat / 2.0), 2)
		            + Math.cos(note1.getLat() * d2r)
		            * Math.cos(note2.getLat() * d2r)
		            * Math.pow(Math.sin(dlong / 2.0), 2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    d = 6367 * c;

		    return d;

		} catch(Exception e){
		    e.printStackTrace();
		}
		return d;
	}
	
	public static double distanceParcours(){
		double distance = 0;
		if(!parcours.isEmpty()){
			distance+=distance(parcoursStartEnd.get(0), parcours.get(0));
			for(int j=1; j<parcours.size(); j++){
				distance+=distance(parcours.get(j-1), parcours.get(j));
			}
			distance+=distance(parcours.get(parcours.size()-1), parcoursStartEnd.get(1));
		}
		else{
			distance+=distance(parcoursStartEnd.get(0), parcoursStartEnd.get(1));
		}
		
		return distance;
	}
	
	public static List<Note> recupererParcours(){
		List<Note> notes = new ArrayList<Note>();
		for(int j=0; j<currentParcours.getNotes().length; j++){
			notes.add(currentParcours.getNotes()[j]);
		}
		return notes;
	}
	
	public static void supprimerParcours(){
		System.out.println("PARCOURS p = "+currentParcours.getCommentaire());
		gp.supprimer(currentParcours.getId());
	}
	
	@Command("selectionnerParcours")
	public void selectionnerParcours(@BindingParam("p") Parcours parcours){
		currentParcours = parcours;
	}
}
