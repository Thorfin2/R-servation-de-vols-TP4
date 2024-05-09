package reservation;

public class Confirmee extends EtatReservation {

    @Override
    public EtatReservation annuler(Reservation r) {
        r.rembourser();
        return new Annulee();
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
        return "Confirmée";
    }

}