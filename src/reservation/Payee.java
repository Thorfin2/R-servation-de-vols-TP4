package reservation;

public class Payee extends EtatReservation {

    @Override
    public EtatReservation annuler(Reservation r) {
        return new Annulee();
    }

    @Override
    public EtatReservation payer(Reservation r) {
        return this;
    }

    @Override
    public EtatReservation confirmer(Reservation r) {
        return new Confirmee();
    }

    @Override
    public String toString() {
        return "Payée";
    }

}