package reservation;

import Exceptions.SoldeInsuffisantException;

import java.util.HashSet;
import java.util.Set;

public class Client {

    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private double solde;

    private Set<Reservation> reservations;

    
    public Client(String nom, String prenom, double solde) {
        this.nom = nom;
        this.prenom = prenom;
        this.solde = solde;

        this.adresse = "";
        this.telephone = "";
        this.email = "";

        this.reservations = new HashSet<Reservation>();
    }

    // Arguments inutiles...
    public Client(String nom, String prenom, String adresse, String telephone, String email, double solde) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.solde = solde;

        this.reservations = new HashSet<Reservation>();
    }

    // Nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Prenom
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Adresse
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Telephone
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Solde
    public double getSolde() {
        return solde;
    }
    
    // Reservations
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }

    // Debiter
    public void debiter(double prix) throws SoldeInsuffisantException {
        if (prix > solde) {
            throw new SoldeInsuffisantException();
        }
        this.solde -= prix;
    }

    // Rembourser
    public void rembourser(double prix) {
        this.solde += prix;
    }

    // toString
    @Override
    public String toString() {
        String str = "€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€";
        str += "\nClient:   ";
        str += this.nom.toUpperCase() + " " + this.prenom;
        str += "€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€";
        if (!this.adresse.isEmpty()) {
            str += "\n\t\t\tAdresse:\t" + this.adresse;
        }
        if (!this.telephone.isEmpty()) {
            str += "\n\t\t\tTelephone:\t" + this.telephone;
        }
        if (!this.email.isEmpty()) {
            str += "\n\t\t\tEmail:\t\t" + this.email;
        }
        str += "\n\t\t\tSolde:\t\t" + this.solde;
        str += "\n";
        str += "€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€";
        str += "\n";
        
        return str;
    }

}