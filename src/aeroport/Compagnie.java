package aeroport;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Compagnie {

    private String nom;
    private Set<Vol> vols = new HashSet<Vol>();


    public Compagnie(String nom) {
        this.nom = nom;
    }

    // Nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Vols
    public Collection<Vol> getVols() {
        return vols;
    }

    public Set<Vol> getVolsParNumero(String numero) {
        Set<Vol> volsAvecCeNumero = new HashSet<Vol>();
        for(Vol vol : vols) {
            if(vol.getNumero().equals(numero)) {
                volsAvecCeNumero.add(vol);
            }
        }

        return volsAvecCeNumero;
    }

    public void setVols(Set<Vol> vols) {
        for(Vol v : this.vols) {
            v.setCompagnieWithoutBidirectional(null);
        }

        this.vols = vols;

        if(this.vols != null) {
            for (Vol v : this.vols) {
                v.setCompagnieWithoutBidirectional(this);
            }
        }
    }

    public void addVol(Vol vol) {
        vol.setCompagnieWithoutBidirectional(this);
        this.vols.add(vol);
    }

    public void removeVol(Vol vol) {
        vol.setCompagnieWithoutBidirectional(null);
        this.vols.remove(vol);
    }

    protected void setVolsWithoutBidirectional(Set<Vol> vols) {
        this.vols = vols;
    }

    protected void addVolWithoutBidirectional(Vol vol) {
        this.vols.add(vol);
    }

    protected void removeVolWithoutBidirectional(Vol vol) {
        this.vols.remove(vol);
    }

    // toString
    @Override
    public String toString() {
        String str = "=== === === === === ===\tCompagnie:\n=== === === === === ===\n";
        str += this.nom;
        str += "\nListe des vols :\n";
        for (Vol vol : vols) {
            str += " \t-\t" + vol.getNumero() + "\n";
        }
        str += "\n";

        return str;
    }

}
