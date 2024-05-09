package aeroport;

import Exceptions.VolDateException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Trajet implements Cloneable {

    private Saut saut;

    public Trajet(Saut saut) {
        this.saut = saut;
    }

    // Saut
    public Saut getSaut() {
        return saut;
    }

    // Durée
    public Duration getDuree() {
        Saut currentSaut = saut;
        Duration totalDuration = Duration.ZERO;
        while (currentSaut != null) {
            totalDuration = totalDuration.plus(currentSaut.obtenirDuree());
            currentSaut = currentSaut.getSautSuivant();
        }
        return totalDuration;
    }    

    // Date de départ
    public LocalDateTime getDateDepart() {
        return saut.getDateDepart();
    }

    // Date d'arrivée
    public LocalDateTime getDateArrivee() {
        Saut saut = this.saut;
        while (saut.getSautSuivant() != null) {
            saut = saut.getSautSuivant();
        }

        return saut.getDateArrivee();
    }
    
    // clone
    @Override
    public Trajet clone() throws CloneNotSupportedException {
        Trajet cloned = (Trajet) super.clone();

        cloned.saut = this.saut.clone();
        
        return cloned;
    }

    // decaler
    /* Decaler un Trajet va décaler tous ses Sauts. */
    public void decaler(Duration duree) throws VolDateException {
        LinkedList<Saut> sautsList = new LinkedList<Saut>();
        Saut saut = this.saut;
        sautsList.addFirst(saut);

        while (saut.getSautSuivant() != null) {
            saut = saut.getSautSuivant();
            sautsList.addFirst(saut);
        }
    
        for (Saut sautL : sautsList) {
            sautL.decaler(duree);
        }
    }

    // toString
    @Override
    public String toString() {
        String str = "±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±";
        str += "\n\t\t\tTrajet:\n";
        str += "±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±";
        str += "\n";
        str += this.saut.toString();
//        str += "±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±±";
//        str += "\n";

        return str;
    }

}
