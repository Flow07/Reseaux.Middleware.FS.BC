package com.geonotes;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

public class GestionParcoursClientVM{

	Context context;
	GestionNotes gn;
	GestionParcours gp;
	List<Parcours> listParcours;
	static Parcours currentParcours;
	static int etape = 0;
	
	public List<Parcours> getListParcours() {
		return listParcours;
	}

	public void setListParcours(List<Parcours> listParcours) {
		this.listParcours = listParcours;
	}

	@Init
	public void init(){
		try {
			context = new InitialContext();
			gp = (GestionParcours) context.lookup("GestionParcoursBean/remote");
			gn = (GestionNotes) context.lookup("GestionNotesBean/remote");
			listParcours = gp.listParcours();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static int getEtape() {
		return etape;
	}

	public static void setEtape(int etape) {
		GestionParcoursClientVM.etape = etape;
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
	
	public static List<Note> recupererParcours(){
		List<Note> notes = new ArrayList<Note>();
		for(int j=0; j<currentParcours.getNotes().length; j++){
			notes.add(currentParcours.getNotes()[j]);
		}
		return notes;
	}
	
	@Command("selectionnerParcours")
	public void selectionnerParcours(@BindingParam("p") Parcours parcours){
		currentParcours = parcours;
	}
	
	public static void sendMail(String to, String subject, String body){
		String bodyFinal = ""+body+"\nhttps://maps.google.fr/maps?saddr=";
		bodyFinal+=""+currentParcours.getNotes()[0].getLon()+","+currentParcours.getNotes()[0].getLat()+"+to:";
		for(int j=1; j<currentParcours.getNotes().length; j++){
			bodyFinal+=""+currentParcours.getNotes()[j].getLon()+","+currentParcours.getNotes()[j].getLat();
			if(j!=currentParcours.getNotes().length-1){
				bodyFinal+="+to:";
			}
			System.out.println("Parcours Suivant : "+bodyFinal);
		}
		String mailTo=""+to+"?subject="+subject+"&body="+bodyFinal;
		//mailTo = mailTo.replace("&", "");
		System.out.println("mailTo = "+mailTo);
		 try {
			 URI uriMailTo = new URI("mailto", mailTo, null);
             Desktop.getDesktop().mail(uriMailTo);
         } catch (IOException ex) {
             ex.printStackTrace();
         } catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
