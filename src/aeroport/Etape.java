package aeroport;

import aeroport.AeroportVilleFactory.AeroportVilleFactory.Aeroport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Etape implements Cloneable {

    private Aeroport aeroport;
    private LocalDateTime date;

    
    public Etape(Aeroport aeroport, LocalDateTime date) {
        this.aeroport = aeroport;
        this.date = date;
    }
    
    public Aeroport getAeroport() {
        return aeroport;
    }

    public void setAeroport(Aeroport aeroport) {
        this.aeroport = aeroport;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // clone
    @Override
    public Etape clone() throws CloneNotSupportedException {
        Etape cloned = (Etape) super.clone();
        
        cloned.aeroport = this.aeroport;
        cloned.date = LocalDateTime.of(
            this.date.getYear(),
            this.date.getMonth(),
            this.date.getDayOfMonth(),
            this.date.getHour(),
            this.date.getMinute(),
            this.date.getSecond()
        );
        
        return cloned;    
    }

    @Override
    public String toString() {
        String str = "------------------------------------------------------";
        str += "\nÉtape:\n";
        str += "------------------------------------------------------";
        str += "\n\t\t\tAeroport :\t" + this.aeroport.getNom();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        str += "\n\t\t\tDate     :\t" + this.date.format(formatter);
        str += "\n";
        str += "------------------------------------------------------";
        str += "\n";

        return str;
    }

}
