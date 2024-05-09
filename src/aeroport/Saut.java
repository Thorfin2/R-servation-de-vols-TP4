package aeroport;

import Exceptions.AeroportException;
import Exceptions.EtapesManquantesException;
import Exceptions.VolDateException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Saut implements Cloneable {

    private Etape depart;
    private Etape arrivee;
    private Saut sautSuivant;

    private Saut() {}

    private Saut(Etape depart, Etape arrivee) throws Exception {
        if (depart.getDate().compareTo(arrivee.getDate()) >= 0) {
            throw new VolDateException("La date de départ ne peut pas être postérieure ou égale à la date d'arrivée.");
        }

        if (depart.getAeroport().equals(arrivee.getAeroport())) {
            throw new AeroportException("L'aéroport d'arrivée ne peut pas être le même que l'aéroport de départ.");
        }

        this.depart = depart;
        this.arrivee = arrivee;
    }

    // SautBuilder
    public static class SautBuilder {

        private Etape depart;
        private Etape arrivee;
        private Saut sautSuivant;

        private SautBuilder() {}

        public static SautBuilder partDe(Etape depart) {
            SautBuilder builder = new SautBuilder();

            builder.depart = depart;

            return builder;
        }

        public SautBuilder arriveA(Etape arrivee) throws VolDateException {
            if (this.depart.getDate().compareTo(arrivee.getDate()) >= 0) {
                throw new VolDateException("La date de départ ne peut pas être postérieure ou égale à la date d'arrivée.");
            }

            this.arrivee = arrivee;

            return this;
        }

        public SautBuilder avecSautSuivant(Saut sautSuivant) throws VolDateException {
            if (this.arrivee.getDate().compareTo(sautSuivant.depart.getDate()) >= 0) {
                throw new VolDateException("La date d'arrivée ne peut pas être antérieure ou égale à la date de départ du saut suivant.");
            }

            this.sautSuivant = sautSuivant;

            return this;
        }
        
        public Saut build() throws Exception {
            if (this.depart == null || this.arrivee == null) {
                throw new EtapesManquantesException("Les étapes de départ et d'arrivée doivent être définies pour construire un saut.");
            }

            Saut saut = new Saut(this.depart, this.arrivee);
            if (this.sautSuivant != null) {
                saut.sautSuivant = this.sautSuivant;
            }

            return saut;
        }

    }
    
    // Depart
    public Etape getDepart() {
        return depart;
    }

    public LocalDateTime getDateDepart() {
        return this.depart.getDate();
    }

    // Arrivee
    public Etape getArrivee() {
        return arrivee;
    }

    public LocalDateTime getDateArrivee() {
        return this.arrivee.getDate();
    }

    // SautSuivant
    public Saut getSautSuivant() {
        return sautSuivant;
    }

    // Duree
    public Duration obtenirDuree() {
        if  (   this.depart != null && this.arrivee != null
            &&  this.depart.getDate() != null && this.arrivee.getDate() != null) {
                Instant instantDepart = this.depart.getDate().atZone(ZoneId.systemDefault()).toInstant();
                Instant instantArrivee = this.arrivee.getDate().atZone(ZoneId.systemDefault()).toInstant();
                return Duration.between(instantDepart, instantArrivee);
        }

        return null;
    }

    // Decaler
    /* Un Saut peut être décaler tout seul, à condition que sa nouvelle date d'arrivée soit < la date de départ de son saut suivant */
    public void decaler(Duration duree) throws VolDateException {
        LocalDateTime newDateDepart = depart.getDate().plus(duree);
        LocalDateTime newDateArrivee = arrivee.getDate().plus(duree);
        
        if (sautSuivant == null || (newDateArrivee.isBefore(sautSuivant.depart.getDate()))) {
            depart.setDate(newDateDepart);
            arrivee.setDate(newDateArrivee);
            
        } else {
            throw new VolDateException("La nouvelle date d'arrivée est postérieure ou égale à la date de départ de l'étape suivante.");
        }
    }

    // clone
    @Override
    public Saut clone() throws CloneNotSupportedException {
        Saut cloned = (Saut) super.clone();

        cloned.depart = (Etape) this.depart.clone();
        cloned.arrivee = (Etape) this.arrivee.clone();

        if (this.sautSuivant != null) {
            cloned.sautSuivant = this.sautSuivant.clone();
        }
    
        return cloned;    
    }

    // toString
    @Override
    public String toString() {
        String str = "";
//        str += "\nSaut:\n";
//        str += "//////////////////////////////////////////////";
        str += "\nDepart:  " + this.depart.toString();
        str += "\n============================================\n";
        str += "\nArrivée:  " + this.arrivee.toString();
        if(sautSuivant != null) {
            str += "\nSaut suivant :\n";
            str += sautSuivant.toString();
        }
//        str += "//////////////////////////////////////////////";
        str += "\n";
        
        return str;
    }

}