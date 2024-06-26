package Model;

import java.io.Serializable;

/**
 * Megvalósítja a játék pontjainak számítását
 */
public class PointCounter implements Serializable {
    /**
     * Singleton megvalósítása
     */
    static private PointCounter counter;
    private PointCounter() {}

    static public PointCounter getInstance() {
        if (counter == null) {
            counter = new PointCounter();
        }
        return counter;
    }
    /**
     * a szerelők pontja
     */
    private int mechanicPoints =0;
    /**
     * a szabotőrök pontja
     */
    private int saboteurPoints =0;
    /**
     * a nyeréshez szükséges pontok
     */
    private int pointsToWin = 50;

    /**
     * @param amount az a mennyiség, amivel a szabotőrök pontjai nőnek
     */
    public void addSaboteurPoints(int amount) {
        saboteurPoints+=amount;
    }

    /**
     * @param amount az a mennyiség, amivel a szerelők pontjai nőnek
     */
    public void addMechanicPoints(int amount) {
        mechanicPoints+=amount;
    }

    /**
     * A szabotőrök pontjainak gettere
     * @return a pontok
     */
    public int getSaboteurPoints(){
        return saboteurPoints;
    }

    /**
     * A szerelők pontjainak gettere
     * @return a pontok
     */
    public int getMechanicPoints(){
        return mechanicPoints;
    }

    /**
     * A győzelemhez szükséges pontok gettere
     * @return a pontok
     */
    public int getPointsToWin(){ return pointsToWin;}

    /**
     * A győzelemhez szükséges pontok settere
     * @param points - a pontok
     */
    public void setPointsToWin(int points){
        pointsToWin = points;
    }

}
