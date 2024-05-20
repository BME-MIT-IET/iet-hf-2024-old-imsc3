package Model;

import java.io.Serializable;

/**
 * Interface azoknak a pályaelemeknek, amelyeket fel lehet venni, illetve le lehet rakni
 */
public interface PickupAble extends Serializable {
    /**
     * Elem felvétele egy másik elemről
     * @param from melyik elemről vesszük le / csatlakoztatjuk le
     */
    void pickedUp(Steppable from);

    /**
     * Elem letétele egy másik elemre, az előző dokumentumhoz képest van visszatérési értéke
     * @param to melyik elemre tesszük le / csatlakoztatjuk rá
     * @return a letétel sikeressége
     */
    boolean placedDown(Steppable to);

    /**
     * Elemet fel lehet-e venni onnan amit átadunk
     * @param to - ahonnan felvesszük
     * @return fel lehet-e venni
     */
    boolean canBePlacedDownTo(Steppable to);


}
