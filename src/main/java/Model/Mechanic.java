package Model;

import Controller.Controller;

import java.io.Serializable;

/**
 * Szerelő megvalósítására használt osztály
 */
public class Mechanic extends Player implements Serializable {
    /**
     * felvett pályaelem
     */
    private PickupAble heldItems = null;

    /**
     * Pályaelem megjavítására használt függvény, ha specialAction-ben van a játékos, akkor javít.
     */
    public boolean repair() {
        boolean repaired=false;
        if(isIgnoreStates()) {
            repaired = standingOn.repaired();
        }
        else if(state == PlayerActionState.SPECIAL_ACTION) {
            repaired = standingOn.repaired();
            if (repaired) {
                state = PlayerActionState.TURN_OVER;
                Controller.getInstance().turnOver();
            }
        }
        if(!repaired)
            IO_Manager.writeInfo(Controller.getInstance().getObjectName(standingOn) + "is not broken", Controller.filetoWrite != null);
        return repaired;
    }

    /**
     * Pályaelem felvételére használt függvény, a felvett pályaelemet a játékosnak beadjuk, és csak akkor tudja felvenni ha mellette áll
     * @param item - felvett pályaelem
     */
    public boolean pickUp(PickupAble item) {
        boolean pickedup=false;
        if(isIgnoreStates()) {
            pickedup = standingOn.pickedUpFrom(item);
        }
        else if(state == PlayerActionState.SPECIAL_ACTION) {
            pickedup = standingOn.pickedUpFrom(item);
            if (pickedup) {
                state = PlayerActionState.TURN_OVER;
                Controller.getInstance().turnOver();
            }
        }
        if (pickedup) {
            item.pickedUp(standingOn);
            heldItems = item;
        }
        else
            IO_Manager.writeInfo(Controller.getInstance().getObjectName(item) + " can't be picked up", Controller.filetoWrite != null);

        return pickedup;
    }

    /**
     * Pályaelem lerakására használt függvény.
     * Változott az előző dokumentum óta, nem kell paraméter hiszen csak saját attribútumot kezel.
     */
    public boolean placeDown() {

        boolean successful = false;
        if(isIgnoreStates()) {
            successful = heldItems.placedDown(standingOn);
        }
        else if(state == PlayerActionState.SPECIAL_ACTION) {
            successful = heldItems.placedDown(standingOn);
            if (successful) {
                state = PlayerActionState.TURN_OVER;
                Controller.getInstance().turnOver();
            }
        }
        if (successful)
            heldItems = null;
        return successful;
    }

    /**
     * A szerelő elérhető akcióit adja vissza arra az elemre amit átadunk neki
     * @param step - az elem amire nézzük az akciókat
     * @return - Az akciók tömbje
     */
    public ActionType[] availableActions(Steppable step){
        ActionType[] actions = new ActionType[6];
        if(state.equals(PlayerActionState.MOVE_ACTION)){
            if(step.canMoveToHere(this)) {
                actions[2] = ActionType.MOVE;
                return actions;
            }
        }
        if(state.equals(PlayerActionState.SPECIAL_ACTION)){
            //2
            if (step.canMoveToHere(this))
                actions[2] = ActionType.MOVE;
            else if (!standingOn.canMoveFromHere() || standingOn == step)
                actions[2] = ActionType.PASS;
            //1
            if(step.canRedirect(this))
                actions[1] = ActionType.REDIRECT;
            //3
            if(step.canGlue(this))
                actions[3] = ActionType.GLUE;
            //4
            if(step.canPickUpPipe(this)){
                actions[4] = ActionType.PICKUP_PIPE;
            } else if (step.canPlaceDown(this)) {
                actions[4] = ActionType.PLACEDOWN;
            }
            //5
            if(step.canPickUpPump(this)){
                actions[5] = ActionType.PICKUP_PUMP;
            } else if (step.canRepair(this)) {
                actions[5] = ActionType.REPAIR;
            } else if (step.canPierce(this)) {
                actions[5] = ActionType.PIERCE;
            }
            return actions;
        }
        return actions;
    }
    /**
     * Felvett pályaelem gettere
     * @return a pályaelem
     */
    public PickupAble getHeldItems() {
        return heldItems;
    }
}
