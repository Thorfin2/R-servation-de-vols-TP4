package reservation;

public class Annulee extends EtatReservation {

    @Override
    public EtatReservation annuler(Reservation r) {
        return this;
    }

    @Override
    public EtatReservation payer(Reservation r) {
        return this;
    }

    @Override
    public EtatReservation confirmer(Reservation r) {
        return this;
    }

    @Override
    public String toString() {
        return "Annulée";
    }

}