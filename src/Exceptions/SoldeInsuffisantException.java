package Exceptions;

public class SoldeInsuffisantException extends Exception {

    public final static String errorMessage = "Solde insuffisant pour effectuer cette réservation";

    public SoldeInsuffisantException() {
        super(errorMessage);
    }
    
}