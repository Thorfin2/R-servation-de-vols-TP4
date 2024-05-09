package aeroport;

import Exceptions.VolCompletException;
import Exceptions.VolDateException;
import reservation.Reservation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Vol {

    private String numero;

    private Trajet trajet;

    private Compagnie compagnie;

    private int maxReservations;
    private List<Reservation> reservations;

    private EtatVol etat;

    public Vol(String numero, Trajet trajet, int maxReservations, Compagnie compagnie) throws VolDateException {
        this.numero = numero;
        this.trajet = trajet;
        this.maxReservations = maxReservations;
        this.compagnie = compagnie;
        this.etat = EtatVol.ACTIF;

        reservations = new ArrayList<Reservation>();
    }

    // Compagnie
    public Compagnie getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(Compagnie compagnie) {
        if(compagnie != null) {
            compagnie.addVolWithoutBidirectional(this);
        }

        if(this.compagnie != null) {
            this.compagnie.removeVolWithoutBidirectional(this);
        }

        this.compagnie = compagnie;
    }

    protected void setCompagnieWithoutBidirectional(Compagnie compagnie) {
        this.compagnie = compagnie;
    }

    // Numero
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    // Depart
    public Etape getDepart() {
        return trajet.getSaut().getDepart();
    }

    public LocalDateTime getDateDepart() {
        return trajet.getSaut().getDateDepart();
    }

    // Arrivee
    public Etape getArrivee() {
        return trajet.getSaut().getArrivee();
    }

    public LocalDateTime getDateArrivee() {
        return trajet.getSaut().getDateArrivee();
    }

    // Duree
    public Duration obtenirDuree() {
        return this.trajet.getDuree();
    }

    // Reservation
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) throws VolCompletException {
        if (reservations.size() >= maxReservations) {
            throw new VolCompletException("La réservation n'a pas pu être effectuée. Ce vol est complet.");

        }
        reservations.add(reservation);
        reservation.setVol(this);
    }

    public boolean removeReservation(Reservation reservation) {
        if (reservations.remove(reservation)) {
            reservation.setVol(null);
            return true;
        }
        return false;
    }

    public int getNombreDeReservations() {
        return reservations.size();
    }

    // Etat
    public EtatVol getEtat() {
        return etat;
    }

    // annuler
    public void annuler() {
        etat = EtatVol.ANNULE;
        for (Reservation res : reservations) {
            res.annuler();
        }
        // reservations.removeAll(reservations); // Pas nécessaire. On peut laisser les réservations (pour l'Analytics par exemple)
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        try {
            return ((Vol) obj).getNumero().equals(this.numero);
        } catch (Exception e) {
            return false;
        }
    }

    // toString
    @Override
    public String toString() {
        String str = "*** *** *** *** *** *** *** *** *** *** *** *** *** ***";
        str += "\n\t\t\tVol:\t";
        str += "No. " + this.numero;
        str += "\n\n" + trajet.toString();
        str += "Compagnie :\t" + this.compagnie.getNom();
        str += "\n";
        str += "*** *** *** *** *** *** *** *** *** *** *** *** *** ***";
        str += "\n";

        return str;
    }

    public enum EtatVol {
        ANNULE,
        ACTIF
    }

}