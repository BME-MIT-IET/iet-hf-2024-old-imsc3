package Model;

import Controller.Controller;

import java.io.Serializable;

/**
 * Spring valósítja meg a vízforrásokkal kapcsolatos funkciókat
 */
public class Spring extends WaterNode implements Serializable {


    /**
     * @param pickup az adott elem, amit fel akarunk venni
     * @return amennyiben az adott elemet fel lehet venni a forrásról true-val tér vissza, különben false-szal
     */
    @Override
    public boolean pickedUpFrom(PickupAble pickup) {

        int pickupIdx = pipes.indexOf(pickup);
        if (pickupIdx != -1) {
            pipes.remove(pickupIdx);
            return true;
        }
        return false;

    }

    /**
     * @param pickup a Pipe amit le akarunk rakni
     * @return amennyiben sikeresen le lehet helyezni a forrásra a csövet, true-val tér vissza, különben false-szal
     */
    @Override
    public boolean placedDownTo(Pipe pickup) {
        boolean successful = addPipe(pickup);
        if (successful)
            pickup.addWaterNode(this);
        return successful;

    }

    /**
     *
     * @param pickup a Pump amit le akarunk rakni
     * @return false-szal tér vissza, mivel egy forrásra nem lehet pumpát helyezni
     */
    @Override
    public boolean placedDownTo(Pump pickup) {
        IO_Manager.writeInfo("Can't place it here", Controller.filetoWrite != null);
        return false;
    }

    /**
     * Le lehet-e rakni csövet erre
     * @param p -cső
     * @return lehet-e
     */
    @Override
    boolean canBePlacedDown(Pipe p) {
        return pipes.isEmpty();
    }

    /**
     * Le lehet-e rakni pumpát erre
     * @param p -pumpa
     * @return nem lehet
     */
    @Override
    boolean canBePlacedDown(Pump p) {
        return false;
    }

    /**
     * Le lehet-e rakni erre azt amit a szerelő fog
     * @param m - a szerelő
     * @return lehet-e
     */
    @Override
    public boolean canPlaceDown(Mechanic m){
        if (m.getHeldItems() == null)
            return false;
        return m.standingOn==this && m.getHeldItems().canBePlacedDownTo(this);
    }


    /**
     * A vízfolyással foglalkozik, a rákötött csőnek ad egy egységnyi vizet
     */
    @Override
    public void waterFlow() {
        if(pipes.getFirst()!=null) {
            int gained = pipes.getFirst().gainWater(1);
            IO_Manager.write(Controller.getInstance().getObjectName(pipes.get(0)) + " gained " + gained, Controller.filetoWrite != null);
        }
    }

    /**
     * @param p forráshoz hozzáadandó cső
     * @return ha a forrásra még nincsen cső kötve, akkor rákötik a forrásra, és visszatér true-val, különben false-szal
     */
    @Override
    public boolean addPipe(Pipe p) {
        boolean valid = pipes.isEmpty();
        if (valid)
            pipes.add(p);
        else
            IO_Manager.writeInfo("Can't place " + Controller.getInstance().getObjectName(p) + " here, because " +
                    Controller.getInstance().getObjectName(this) + " already has a pipe connected", Controller.filetoWrite != null);
        return valid;

    }
}
