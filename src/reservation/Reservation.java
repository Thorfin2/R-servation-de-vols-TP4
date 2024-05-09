package reservation;

import Exceptions.SoldeInsuffisantException;
import Exceptions.VolAnnuleException;
import aeroport.Vol;
import aeroport.Vol.EtatVol;

public class Reservation {

    private Vol vol;
    private Client client;
    private double prix;
    private EtatReservation etat;


    public Reservation(Vol vol, Client client, double prix) throws Exception {
        if (vol.getEtat().equals(EtatVol.ANNULE)) {
            throw new VolAnnuleException("La réservation n'a pas pu être effectuée. Ce vol est annulé.");
        }

        this.vol = vol;
        vol.addReservation(this);

        this.client = client;
        this.client.addReservation(this);

        this.prix = prix;
        this.etat = new EnAttente();
    }

    // Vol
    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    // Client
    public Client getClient() {
        return client;
    }

    public double getPrix() {
        return prix;
    }

    // Confirmer
    public void confirmer() {
        this.etat = this.etat.confirmer(this);
    }

    // Annuler
    public void annuler() {
        this.etat = this.etat.annuler(this);
        if (this.vol.getEtat().equals(EtatVol.ACTIF)) {
            // Si l'état du vol est ACTIF ==> Seulement la réservation est annulée.
            // Si l'état du vol est ANNULE ==> Le vol est annulé, et cette fonction est appelée par le vol
            this.vol.removeReservation(this);
        }
    }

    // Payer
    public void payer() throws SoldeInsuffisantException {
        this.etat = this.etat.payer(this);
    }

    // Rembourser
    protected void rembourser() {
        this.client.rembourser(this.prix);
    }

    // Débiter
    protected void debiter() throws SoldeInsuffisantException {
        this.client.debiter(this.prix);
    }

    // Etat
    public EtatReservation getEtat() {
        return etat;
    }

    @Override
    public String toString() {
        String str = "=== === === === === ===\n\tRéservation:\n=== === === === === ===\n";
        str += "\t" + vol.toString();
        str += "\t" + client.toString();
        str += "\tPrix:\t" + this.prix;
        str += "\tEtat:\t" + this.etat.toString();
        str += "\n";

        return str;
    }

}
