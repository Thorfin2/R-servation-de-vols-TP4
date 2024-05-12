import aeroport.AeroportVilleFactory.AeroportVilleFactory;
import aeroport.AeroportVilleFactory.AeroportVilleFactory.Aeroport;
import aeroport.AeroportVilleFactory.AeroportVilleFactory.AeroportVille;
import aeroport.AeroportVilleFactory.AeroportVilleFactory.Ville;
import aeroport.AeroportVilleFactory.IAeroportVilleFactory;
import aeroport.*;
import aeroport.Saut.SautBuilder;
import reservation.Client;
import reservation.Reservation;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



/**
 * Notre classe démontre l'utilisation de différentes classes et de leurs interactions dans l'application.
 * Elle crée des instances des classes AeroportVille, Ville, Aeroport, Etape, Saut, Trajet, Compagnie, Vol, Client et Réservation.
 * Elle effectue également des opérations comme la confirmation, le paiement et l'annulation des réservations.
 *
 * Remarque: Cette classe dépend de l'interface IAeroportVilleFactory qui n'est pas montrée ici.
 * Nous avons également utilise le Factory design pattern pour créer les instances de AeroportVille.
 */

//Mehdi Znata and Ibrahim Diab

public class Start {  

    public static void main(String[] args) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String dd = "01/05/2024 13:20";
            String da = "01/05/2024 14:30";

            IAeroportVilleFactory avFactory = new AeroportVilleFactory();

            AeroportVille paris_cdg = avFactory.createVille("Paris", null, "CDG");
            Ville paris = paris_cdg.getVille();
            Aeroport cdg = paris_cdg.getAeroport();

            AeroportVille paris_orly = avFactory.createAeroport("Orly", paris, paris.getNom());
            Aeroport orly = paris_orly.getAeroport();
            paris.addAeroport(orly);

            AeroportVille lyon_lys = avFactory.createAeroport("LYS", null, "Lyon");
            Ville lyon = lyon_lys.getVille();
            Aeroport lys = lyon_lys.getAeroport();

            Etape depart = new Etape(cdg, LocalDateTime.parse(dd, formatter));
            Etape arrivee = new Etape(lys, LocalDateTime.parse(da, formatter));

            String dd2 = "05/05/2024 12:45";
            String da2 = "05/05/2024 13:55";

            Etape depart2 = new Etape(lys, LocalDateTime.parse(dd2, formatter));
            Etape arrivee2 = new Etape(orly, LocalDateTime.parse(da2, formatter));
            Saut saut2 = SautBuilder.partDe(depart2).arriveA(arrivee2).build();

            Saut saut1 = SautBuilder.partDe(depart).arriveA(arrivee).avecSautSuivant(saut2).build();

            Trajet trajet1 = new Trajet(saut1);
            Trajet trajet2 = trajet1.clone();
            trajet2.decaler(Duration.ofHours(5));

            System.out.println("----------------------------------------------------------------");
            System.out.println("Trajet1 : " + trajet1.getDateDepart() + " -> " + trajet1.getDateArrivee());
            System.out.println("Durée : " + trajet1.getDuree());
            System.out.println("----------------------------------------------------------------");

            System.out.println("----------------------------------------------------------------");
            System.out.println("Trajet2 : " + trajet2.getDateDepart() + " -> " + trajet2.getDateArrivee());
            System.out.println("Durée : " + trajet2.getDuree());
            System.out.println("----------------------------------------------------------------");

            Compagnie airFrance = new Compagnie("Air France");
            Vol vol1 = new Vol("123", trajet1, 500, airFrance);

            System.out.println(paris);
            System.out.println(lyon);
            System.out.println(cdg);
            System.out.println(lys);
//            System.out.println(depart);
//            System.out.println(arrivee);
//            System.out.println(saut1);
//            System.out.println(trajet1);
            System.out.println(vol1);


            // RESERVATION 1
            Client client1 = new Client("Test", "Client1", "Clermont-Ferrand", "0712123456", "client_test@gmail.com", 5555.5);
            System.out.println(client1);
            Reservation res_c1 = new Reservation(vol1, client1, 400.6);
            System.out.println("Vol1 : Nombre de réservations: " + vol1.getNombreDeReservations());
            res_c1.confirmer();
            System.out.println("Etat Reservation de ["+client1.getPrenom()+"] :" + res_c1.getEtat().toString());
            res_c1.payer();
            System.out.println("Etat Reservation de ["+client1.getPrenom()+"] :"+ res_c1.getEtat().toString());
            res_c1.confirmer();
            System.out.println("Etat Reservation de ["+client1.getPrenom()+"] :" + res_c1.getEtat().toString());
            System.out.println("Prix Reservation de ["+client1.getPrenom()+"] :" + res_c1.getPrix());
            System.out.println("Solde ["+client1.getNom()+"] après confirmation: " + client1.getSolde());
            res_c1.annuler();
            System.out.println("Solde ["+client1.getPrenom()+"] après annulation: " + client1.getSolde());


            System.out.println("\nVol1 : Nombre de réservations: " + vol1.getNombreDeReservations() + "\n\n");

            // RESERVATION 2
            Client client2 = new Client("Test", "Client2", "Clermont-Ferrand", "0712345656", "client2_test@gmail.com", 3000.88);
            System.out.println(client2);
            Reservation res_c2 = new Reservation(vol1, client2, 512);
            res_c2.payer();
            res_c2.confirmer();
            System.out.println("Prix Reservation de ["+client2.getPrenom()+"] :" + res_c2.getPrix());
            System.out.println("Solde ["+client2.getNom()+"] après confirmation: " + client2.getSolde());

            //

            System.out.println("\nVol1 : Nombre de réservations: " + vol1.getNombreDeReservations() + "\n\n");

            // RESERVATION 3
            Client client3 = new Client("Test", "Client3", 5000);
            System.out.println(client3);
            Reservation res_c3 = new Reservation(vol1, client3, 512);
            res_c3.payer();
            res_c3.annuler();
            System.out.println("Prix Reservation de ["+client3.getPrenom()+"] :" + res_c3.getPrix());
            System.out.println("Solde ["+client3.getNom()+"] après annulation: " + client3.getSolde());


            System.out.println("\nVol1 : Nombre de réservations: " + vol1.getNombreDeReservations() + "\n\n");

            // RESERVATION 4
            try {
                Client client4 = new Client("Test", "Client4", 300.5);
                new Reservation(vol1, client4, 400.6);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            //

            System.out.println("\nVol1 : Nombre de réservations: " + vol1.getNombreDeReservations() + "\n\n");

            // VOL ANNULÉ
            vol1.annuler();
            System.out.println(vol1);
            System.out.println("Etat Vol1: " + vol1.getEtat());
            System.out.println("Solde Client1: " + client1.getSolde());
            System.out.println("Solde Client2: " + client2.getSolde());
            System.out.println("Solde Client3: " + client3.getSolde());
            //

            System.out.println("\n");

            for(Vol v : airFrance.getVols()){
                System.out.println(v.getNumero());
            }

            System.out.println(vol1.getCompagnie().getNom());

            vol1.setCompagnie(null);
            System.out.println(vol1.getCompagnie());

            for(Vol v : airFrance.getVols()) {
                System.out.println(v.getNumero());
            }

            System.out.println("\n");

            // RESERVATION 5 : Doit renvoyer une erreur
            Client client5 = new Client("Test", "Client5", 3500.5);
            new Reservation(vol1, client5, 400.6);
            //

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
