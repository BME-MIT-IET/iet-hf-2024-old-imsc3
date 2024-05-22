package Model;

import Controller.Controller;

import java.io.Serializable;

/**
 * A Saboteur osztály valósítja meg a szabotőrrel kapcsolatos metódusokat
 */
public class Saboteur extends Player implements Serializable {

    private Controller controller;

    public Saboteur(Controller controller) {
        this.controller = controller;
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    /**
     * Csúszóssá teszi azt amin áll, ha tudja, meghívja azon pályaelem Lubricated függvényét.
     * @return sikeresség
     */
    public boolean lubricate(){
        boolean lubricated=false;
        if(isIgnoreStates()) {
            lubricated = standingOn.lubricated();
        }
        else if(state == PlayerActionState.SPECIAL_ACTION) {
            lubricated = standingOn.lubricated();
            if (lubricated) {
                state = PlayerActionState.TURN_OVER;
                controller.turnOver();
            }
        }
        return lubricated;

    }

    /**
     * A szabotőr elérhető akcióit adja vissza arra az elemre amit átadunk neki
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
            //0
            if(step.canLubricate(this))
                actions[0] = ActionType.LUBRICATE;
            //1
            if(step.canRedirect(this))
                actions[1] = ActionType.REDIRECT;
            //3
            if(step.canGlue(this))
                actions[3] = ActionType.GLUE;
            //5
            if (step.canPierce(this))
                actions[5] = ActionType.PIERCE;
            return actions;
        }
        return actions;
    }

    /**
     * A szabotőr pozícióját adja vissza
     * @return - A szabotőr pozíciója
     */
    public Steppable getLocation() {
        return standingOn;
    }

}
