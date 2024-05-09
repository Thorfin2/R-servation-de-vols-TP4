package reservation;

import Exceptions.SoldeInsuffisantException;

public class EnAttente extends EtatReservation {

    @Override
    public EtatReservation confirmer(Reservation r) {
        return this;
    }

    @Override
    public EtatReservation annuler(Reservation r) {
        return new Annulee();
    }

    @Override
    public EtatReservation payer(Reservation r) throws SoldeInsuffisantException {
        r.debiter();
        return new Payee();
    }

    @Override
    public String toString() {
        return "En Attente";
    }

}