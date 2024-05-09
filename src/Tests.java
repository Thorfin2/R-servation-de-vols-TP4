import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Exceptions.VolDateException;
import Exceptions.AeroportException;
import Exceptions.EtapesManquantesException;
import Exceptions.SoldeInsuffisantException;
import Exceptions.VolAnnuleException;
import Exceptions.VolCompletException;
import aeroport.Compagnie;
import aeroport.Etape;
import aeroport.Saut;
import aeroport.Trajet;
import aeroport.Vol;
import aeroport.AeroportVilleFactory.AeroportVilleFactory;
import aeroport.AeroportVilleFactory.IAeroportVilleFactory;
import aeroport.AeroportVilleFactory.AeroportVilleFactory.Aeroport;
import aeroport.AeroportVilleFactory.AeroportVilleFactory.AeroportVille;
import aeroport.Saut.SautBuilder;
import reservation.Client;
import reservation.Reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    static private Compagnie airFrance;
    static private AeroportVille paris_cdg;
    static private Aeroport cdg;
    static private AeroportVille lyon_lys;
    static private Aeroport lys;
    static private Etape depart;
    static private Etape arrivee;
    static private Saut saut;
    static private Trajet trajet;


    @BeforeAll
    public static void setUp() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dd = "01/05/2024 13:00";
        String da = "01/05/2024 14:00";

        IAeroportVilleFactory avFactory = new AeroportVilleFactory();

        paris_cdg = avFactory.createVille("Paris", null, "CDG");
        cdg = paris_cdg.getAeroport();

        lyon_lys = avFactory.createAeroport("LYS", null, "Lyon");
        lys = lyon_lys.getAeroport();

        depart = new Etape(cdg, LocalDateTime.parse(dd, formatter));
        arrivee = new Etape(lys, LocalDateTime.parse(da, formatter));

        saut = SautBuilder.partDe(depart).arriveA(arrivee).build();

        trajet = new Trajet(saut);

        airFrance = new Compagnie("Air France");
    }




    ///          TESTS DE RÉUSSITE          ///



    @Test
    public void ConfirmationVolAvantPayer() throws Exception {
        Vol vol = new Vol("123", trajet, 500, airFrance);

        Client client = new Client("Test", "Client", "Clermont-Ferrand", "0712123456", "client_test@gmail.com", 5555.5);
        double oldSolde = client.getSolde();

        Reservation res = new Reservation(vol, client, 400.6);

        res.confirmer();

        assertAll(
                "Test de confirmation d'un vol avant payer",
                () -> assertThat(res.getEtat().toString(), equalTo("En Attente")),
                () -> assertThat(vol.getNombreDeReservations(), equalTo(1)),
                () -> assertThat(client.getSolde(), equalTo(oldSolde))
        );
    }

    @Test
    public void ConfirmationVolApresPayer() throws Exception {
        Vol vol = new Vol("123", trajet, 500, airFrance);

        Client client = new Client("Test", "Client", 5555.5);
        double oldSolde = client.getSolde();

        Reservation res = new Reservation(vol, client, 400.6);

        res.payer();
        res.confirmer();

        assertAll(
                "Test de confirmation d'un vol après avoir payer",
                () -> assertThat(res.getEtat().toString(), equalTo("Confirmée")),
                () -> assertThat(vol.getNombreDeReservations(), equalTo(1)),
                () -> assertThat(client.getSolde(), equalTo(oldSolde - res.getPrix()))
        );
    }

    @Test
    public void RemboursementPayerSansConfirmer() throws Exception {
        Vol vol = new Vol("123", trajet, 500, airFrance);

        Client client = new Client("Test", "Client", 5555.5);
        double oldSolde = client.getSolde();

        Reservation res = new Reservation(vol, client, 400.6);

        res.payer();
        res.annuler();

        assertAll(
                "Test de remboursement d'une réservation non confirmée",
                () -> assertThat(res.getEtat().toString(), equalTo("Annulée")),
                () -> assertThat(vol.getNombreDeReservations(), equalTo(0)),
                () -> assertThat(client.getSolde(), equalTo(oldSolde - res.getPrix()))
        );
    }

    @Test
    public void RemboursementPayerEtConfirmer() throws Exception {
        Vol vol = new Vol("123", trajet, 500, airFrance);

        Client client = new Client("Test", "Client", 5555.5);
        double oldSolde = client.getSolde();

        Reservation res = new Reservation(vol, client, 400.6);

        res.payer();
        res.confirmer();
        res.annuler();

        assertAll(
                "Test de remboursement d'une réservation confirmée",
                () -> assertThat(res.getEtat().toString(), equalTo("Annulée")),
                () -> assertThat(vol.getNombreDeReservations(), equalTo(0)),
                () -> assertThat(client.getSolde(), equalTo(oldSolde))
        );
    }

    @Test
    public void RemboursementVolAnnule() throws Exception {
        Vol vol = new Vol("123", trajet, 500, airFrance);

        Client client1 = new Client("Marie", "Client1", 5555.5);
        double oldSolde1 = client1.getSolde();
        Reservation res1 = new Reservation(vol, client1, 300);

        Client client2 = new Client("Arnaud", "Client2", 1234.5);
        double oldSolde2 = client2.getSolde();
        Reservation res2 = new Reservation(vol, client2, 300);


        Client client3 = new Client("Richard", "Client3", 5678.9);
        double oldSolde3 = client3.getSolde();
        Reservation res3 = new Reservation(vol, client3, 300);

        res1.payer();

        res2.payer();
        res2.confirmer();

        res3.confirmer();

        vol.annuler();

        assertAll(
                "Test de remboursement d'un vol annulé",
                () -> assertThat(client1.getSolde(), equalTo(oldSolde1 - res1.getPrix())),
                () -> assertThat(client2.getSolde(), equalTo(oldSolde2)),
                () -> assertThat(client3.getSolde(), equalTo(oldSolde3))
        );
    }




    ///            TESTS D'ECHEC            ///


    /* Pour tester: DateDépart < DateArrivée */
    @Test
    public void VolDates() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dd = "01/05/2024 13:00";
        String da = "01/05/2024 10:00";

        Etape d = new Etape(Tests.cdg, LocalDateTime.parse(dd, formatter));
        Etape a = new Etape(Tests.lys, LocalDateTime.parse(da, formatter));

        assertThat(exceptionOf(() -> SautBuilder.partDe(d).arriveA(a).build()), instanceOf(VolDateException.class));
    }

    @Test
    public void VolComplet() throws Exception {
        Vol vol = new Vol("123", trajet, 2, airFrance);

        Client client1 = new Client("Test", "Client", 1234.5);
        new Reservation(vol, client1, 400.6);

        Client client2 = new Client("Test", "Client", 5678.9);
        new Reservation(vol, client2, 400.6);

        Client client3 = new Client("Test", "Client", 1357.9);
        assertThat(exceptionOf(() -> new Reservation(vol, client3, 400.6)), instanceOf(VolCompletException.class));
    }

    @Test
    public void SoldeInsuffisant() throws Exception {
        Vol vol = new Vol("123", trajet, 500, airFrance);

        Client client = new Client("Test", "Client", 200);
        Reservation res = new Reservation(vol, client, 450.6);

        try {
            res.payer();
            fail("Expected SoldeInsuffisantException was not thrown");
        } catch (SoldeInsuffisantException e) {
            assertThat(e.getMessage(), is(SoldeInsuffisantException.errorMessage));
        }
    }

    /* Pour tester la réservation d'un Vol annulé */

    @Test
    public void VolAnnule() throws Exception {
        Vol vol = new Vol("123", trajet, 500, airFrance);
        vol.annuler();

        Client client = new Client("Test", "Client", 5555.5);

        assertThat(exceptionOf(() -> new Reservation(vol, client, 450.6)), instanceOf(VolAnnuleException.class));
    }

    /* Pour tester: AeroportDeDépart != AeroportD'Arrivée */


    @Test
    public void VolMemeAeroport() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dd = "01/05/2024 13:00";
        String da = "01/05/2024 14:00";

        Etape d = new Etape(Tests.cdg, LocalDateTime.parse(dd, formatter));
        Etape a = new Etape(Tests.cdg, LocalDateTime.parse(da, formatter));

        assertThat(exceptionOf(() -> SautBuilder.partDe(d).arriveA(a).build()), instanceOf(AeroportException.class));
    }

    @Test
    public void VolSuivantIncompatible() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dd = "31/04/2024 13:00";
        String da = "31/04/2024 14:00";

        Etape depart_saut2 = new Etape(lys, LocalDateTime.parse(dd, formatter));
        Etape arrivee_saut2 = new Etape(cdg, LocalDateTime.parse(da, formatter));
        Saut saut2 = SautBuilder.partDe(depart_saut2).arriveA(arrivee_saut2).build();

        assertThat(exceptionOf(() ->  SautBuilder.partDe(depart).arriveA(arrivee).avecSautSuivant(saut2).build()), instanceOf(VolDateException.class));
    }

    @Test
    public void SautEtapesManquantes() throws Exception {
        assertThat(exceptionOf(() -> SautBuilder.partDe(depart).build()), instanceOf(EtapesManquantesException.class));
    }

    //Help you to handle exception. :-)
    public static Throwable exceptionOf(Callable<?> callable) {
        try {
            callable.call();
            return null;
        } catch (Throwable t) {
            return t;
        }
    }

    public static Throwable exceptionOf(Runnable runnable) {
        try {
            runnable.run();
            return null;
        } catch (Throwable t) {
            return t;
        }
    }

}