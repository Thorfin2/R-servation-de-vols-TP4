package aeroport.AeroportVilleFactory;

import java.util.HashSet;
import java.util.Set;

public final class AeroportVilleFactory implements IAeroportVilleFactory {

    public AeroportVille createAeroport(String nomAeroport, Ville ville, String nomVille) {
        if (ville == null) {
            ville = new Ville(nomVille, null);
        }

        return new AeroportVille(new Aeroport(nomAeroport, ville), ville);
    }

    public AeroportVille createVille(String nomVille, Aeroport aeroport, String nomAeroport) {
        if (aeroport == null) {
            aeroport = new Aeroport(nomAeroport, null);
        }

        return new AeroportVille(aeroport, new Ville(nomVille, aeroport));
    }


    ///            AeroportVille            ///


    public final class AeroportVille {

        private Aeroport aeroport;
        private Ville ville;

        protected AeroportVille(Aeroport aeroport, Ville ville) {
            this.aeroport = aeroport;
            this.ville = ville;
        }

        public Aeroport getAeroport() {
            return aeroport;
        }

        public Ville getVille() {
            return ville;
        }

    }



    ///               Aeroport              ///

    public final class Aeroport {
    
        private String nom;
        private Set<Ville> villes;
        
    
        protected Aeroport(String nom, Ville ville) {
            this.nom = nom;
            villes = new HashSet<Ville>();
            if (ville != null) {
                villes.add(ville);
                ville.addAeroport(this);
            }
        }
    
        // Nom
        public String getNom() {
            return nom;
        }
    
        public void setNom(String nom) {
            this.nom = nom;
        }
    
        // Villes
        public void addVille(Ville ville) {
            villes.add(ville);
            ville.addAeroportWithoutBidirectional(this);
        }
    
        public void removeVille(Ville ville) {
            villes.remove(ville);
            ville.removeAeroport(this);
        }
    
        public void addVilleWithoutBidirectional(Ville ville) {
            villes.add(ville);
        }
    
        public void removeVilleWithoutBidirectional(Ville ville) {
            villes.remove(ville);
        }
    
        public Set<Ville> getVilles() {
            return villes;
        }
    
        public Ville getVille(String nomVille) {
            for (Ville v : villes) {
                if(v.getNom().equals(nomVille)) {
                    return v;
                }
            }
            return null;
        }

        // equals
        @Override
        public boolean equals(Object obj) {
            try {
                return ((Aeroport) obj).getNom().equals(this.nom);
            } catch (Exception e){
                return false;
            }
        }
    
        // toString
        @Override
        public String toString() {
            String str = "----------------------\nAeroport:   "+this.nom+"\n-----------------------\n";
            str += "\tDessert les villes: ";
            for (Ville ville : villes) {
                str += "  " + ville.getNom() + ", ";
            }
            str += "\n-----------------------";
            
            return str;
        }
    
    }


    ///                Ville                ///


    public final class Ville {

        private String nom;
        private Set<Aeroport> aeroports;

        
        protected Ville(String nom, Aeroport aeroport) {
            this.nom = nom;
            aeroports = new HashSet<Aeroport>();
            if (aeroport != null) {
                aeroports.add(aeroport);
                aeroport.addVille(this);
            }
        }
        
        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public void addAeroport(Aeroport aeroport) {
            aeroports.add(aeroport);
            aeroport.addVilleWithoutBidirectional(this);
        }

        public void removeAeroport(Aeroport aeroport) {
            aeroports.remove(aeroport);
            aeroport.removeVilleWithoutBidirectional(this);
        }

        public void addAeroportWithoutBidirectional(Aeroport aeroport) {
            aeroports.add(aeroport);
        }

        public void removeAeroportWithoutBidirectional(Aeroport aeroport) {
            aeroports.remove(aeroport);
        }

        public Set<Aeroport> getAeroports() {
            return aeroports;
        }

        public Aeroport getAeroport(String nomAeroport) {
            for (Aeroport a : aeroports) {
                if(a.getNom().equals(nomAeroport)) {
                    return a;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            String str = "------------------------\nVille:   "+this.nom+"\n-----------------------\n";
            str += "\tDesservie par: ";
            for (Aeroport aeroport : aeroports) {
                str += "  " + aeroport.getNom() + ", ";
            }
            str += "\n-----------------------";

            return str;
        }
        
    }

}


