package aeroport.AeroportVilleFactory;

import aeroport.AeroportVilleFactory.AeroportVilleFactory.Aeroport;
import aeroport.AeroportVilleFactory.AeroportVilleFactory.AeroportVille;
import aeroport.AeroportVilleFactory.AeroportVilleFactory.Ville;

public interface IAeroportVilleFactory {
    public AeroportVille createAeroport(String nomAeroport, Ville ville, String nomVille);
    public AeroportVille createVille(String nomVille, Aeroport aeroport, String nomAeroport);
}
