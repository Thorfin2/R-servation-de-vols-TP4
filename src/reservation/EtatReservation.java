package reservation;

import Exceptions.SoldeInsuffisantException;

public abstract class EtatReservation {
    public abstract EtatReservation confirmer(Reservation r);
    public abstract EtatReservation annuler(Reservation r);
    public abstract EtatReservation payer(Reservation r) throws SoldeInsuffisantException;

    /*
     * Une réservation est initialement EnAttente.
     * Si le client:
     * - confirmer: X
     * - annule ==> La Réservation sera annulée.
     * - paie ==> La Réservation sera payée (mais non confirmée).
     * 
     * Le client doit confirmer une réservation pour qu'elle soit remboursée en cas d'annulation.
     * Sinon, elle ne sera pas remboursée.
     * 
     * Une réservation EnAttente peut être:
     * - annulée
     * - payée
     * 
     * Une réservation Payée peut être:
     * - annulée
     * - confirmée
     * 
     * Une réservation Confirmée peut être:
     * - annulée ==> remboursée
     * 
     */
}