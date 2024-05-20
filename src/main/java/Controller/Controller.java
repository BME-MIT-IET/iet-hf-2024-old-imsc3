package Controller;

import Model.*;
import View.*;

import java.awt.*;
import java.io.*;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A modell és a view egymással való működésében segít, a control funkciót látja el, singleton osztály
 */
public class Controller implements Serializable {

    private static Controller controller = null;
    private final boolean frameCounter = false;

    //Singleton
    private Controller() {
    }

    static public Controller getInstance() {
        if (controller == null)
            controller = new Controller();

        return controller;
    }
    final String WrongAttr = "wrong attribute name";
    final String fellDownStr = "fellDown";
    final String stuckStr = "stuck";
    final String standingOnStr = "standingOn";
    final String stateStr = "state";
    final String heldItemsStr = "heldItems";
    final String pipesStr = "pipes";
    final String createdPickupablesStr = "createdPickupables";
    final String playersStr = "players";
    final String saboteurPointsStr = "saboteurPoints";
    final String mechanicPointsStr = "mechanicPoints";
    final String pointsToWinStr = "pointsToWin";
    final String moveActionStr = "moveAction";
    final String specialActionStr = "specialAction";
    final String turnOverStr = "turnOver";
    final String brokenStr = "broken";
    final String waterCapacityStr = "waterCapacity";
    final String heldWaterStr = "heldWater";
    final String maximumPipesStr = "maximumPipes";
    final String activeInStr = "activeIn";
    final String activeOutStr = "activeOut";
    final String readyToPierceStr = "readyToPierce";
    final String lubricatedStr = "lubricated";
    final String gluedStr = "glued";
    final String beingHeldStr = "beingHeld";
    final String nodesStr = "nodes";
    /**
     * Játék elkezdését mutató boolean
     */
    private boolean started = false;
    /**
     * Játék teljes futását mutató boolean
     */
    private final boolean running = true;
    /**
     *  A játékosok actionkezeléséhez szükséges lista.
     */
    private final LinkedList<Player> turnOrder = new LinkedList<>();
    /**
     * Adott típusú objektumok tárolása.
     */
    private final LinkedList<Player> players = new LinkedList<>();
    private final LinkedList<Mechanic> mechanics = new LinkedList<>();
    private final LinkedList<Saboteur> saboteurs = new LinkedList<>();
    private final LinkedList<Steppable> steppables = new LinkedList<>(); //ez nincs benne a doksiban de kell
    private final LinkedList<WaterNode> nodes = new LinkedList<>();
    private final LinkedList<Pump> pumps = new LinkedList<>();
    private final LinkedList<Spring> springs = new LinkedList<>();
    private final LinkedList<Cistern> cisterns = new LinkedList<>();
    private final LinkedList<Pipe> pipes = new LinkedList<>();
    private final LinkedList<PickupAble> pickupables = new LinkedList<>();
    /**
     * Hashmap azért, hogy a nevükkel lehessen azonosítani az objektumokat.
     */
    private final HashMap<String, Object> objectCatalog = new HashMap<>();
    public static File filetoWrite = null;
    /**
     * Determinisztikusságot mutató bool
     */
    public static boolean deterministic = true;
    /**
     * Pontok tárolására használt singleton
     */
    private final PointCounter counter = PointCounter.getInstance();
    /**
     * Ciszternáknál a pumpák nevének generálásához használt futóváltozó
     */
    public int createdPumpNumber = 1;
    /**
     * Ciszternáknál a csövek nevének generálásához használt futóváltozó
     */
    public int createdPipeNumber = 1;
    /**
     * Az új játék ablaknál létrehozott szerelők száma-1(indexe)
     */
    private int mechNumber=-1;
    /**
     * Az új játék ablaknál létrehozott szabotőrök száma-1(indexe)
     */
    private int sabNumber=-1;

    /**
     * A panelt (JPanel) reprezentáló osztály
     */
    private final AppPanel panel = new AppPanel();
    /**
     * A frame-et (JFrame) reprezentáló osztály
     */
    private final AppFrame frame = new AppFrame(panel);
    /**
     * A főmenü ablaka
     */
    private final MenuView menuView = new MenuView();
    /**
     * Az új játék létrehozásának ablaka
     */
    private final NewGameView newGameView = new NewGameView();
    /**
     * A futó játék ablaka
     */
    private final GameView gameView = new GameView(frame);

    /**
     * A gameloop-ért fellelős függvény
     */
    public void run(){
        int frames=0;
        long previousTimeInNanoSec=System.nanoTime();
        long previousTimeInMilliSec=System.currentTimeMillis();
        int FPS=120;
        double timePerFrame=1000000000.0/FPS;
        double elapsedFrames=0;
        while(running){
            elapsedFrames+=(System.nanoTime()-previousTimeInNanoSec)/timePerFrame;
            previousTimeInNanoSec=System.nanoTime();
            if(elapsedFrames>=1){
                update();
                if (frame != null)
                    panel.repaint();
                frames++;
                elapsedFrames--;
            }
            //For FPS count
            if(System.currentTimeMillis()-previousTimeInMilliSec>=1000){
                if(frameCounter) System.out.println(frames);
                frames=0;
                previousTimeInMilliSec=System.currentTimeMillis();
            }
        }
    }

    /**
     * Ez az update hívódik meg a gameloopból, ez osztja szét a különböző nézetek felé
     */
    public void update(){
        switch (WindowOptions.windowOption) {
            case MENU -> menuView.update();
            case NEWGAME -> newGameView.update();
            case GAME -> gameView.update();
            default -> {
                throw new IllegalStateException("Unexpected value: " + WindowOptions.windowOption);
            }
        }
    }

    /**
     * Ez a paint hívódik a frame-repaint hatására, ez osztja szét a különböző nézetek felé
     * @param g - A frame grafikus megjelenítése, amire rajzolunk
     */
    public void paint(Graphics g) {
        switch (WindowOptions.windowOption) {
            case MENU -> menuView.paint(g);
            case NEWGAME -> newGameView.paint(g);
            case GAME -> gameView.paint(g);
            default -> {
                throw new IllegalStateException("Unexpected value: " + WindowOptions.windowOption);
            }
        }
    }

    /**
     * Visszaadja az éppen aktív játékost, azaz aki a FIFO tetején van
     */
    public Player getActivePlayer() {
        return turnOrder.getFirst();
    }

    /**
     * A passzolás kivitelezésére használt függvény
     */
    public void pass(){
        if(turnOrder.getFirst().getState()==PlayerActionState.SPECIAL_ACTION) {
            turnOrder.getFirst().setState(PlayerActionState.TURN_OVER);
            turnOver();
        }
    }

    /**
     * A létrehozást megoldó függvény, amit parancsként kiadva tudunk új játékelemet helyezni a pályára
     * @param name - a játékelem neve
     * @param type - a játékelem típusa
     * @param x - a játékelem kirajzolásához tartozó x koordináta
     * @param y - a játékelem kirajzolásához tartozó y koordináta
     */
    public void create(String name, String type,int x, int y) {

        switch (type) {
            case "mechanic" -> {
                Mechanic mechanic = new Mechanic();
                players.add(mechanic);
                mechanics.add(mechanic);
                objectCatalog.put(name, mechanic);
                MechanicView mechanicView = new MechanicView(0, 0, mechanic, gameView);
                gameView.addMechanicView(mechanicView);
            }
            case "saboteur" -> {
                Saboteur saboteur = new Saboteur();
                players.add(saboteur);
                saboteurs.add(saboteur);
                objectCatalog.put(name, saboteur);
                SaboteurView saboteurView = new SaboteurView(0, 0, saboteur, gameView);
                gameView.addSaboteurView(saboteurView);
            }
            case "pump" -> {
                Pump pump = new Pump(Controller.getInstance());
                steppables.add(pump);
                nodes.add(pump);
                pumps.add(pump);
                pickupables.add(pump);
                objectCatalog.put(name, pump);
                PumpView pumpView = new PumpView(x,y,25,pump,null,gameView);
                gameView.addPumpView(pumpView);
            }
            case "spring" -> {
                Spring spring = new Spring();
                steppables.add(spring);
                nodes.add(spring);
                springs.add(spring);
                objectCatalog.put(name, spring);
                SpringView springView = new SpringView(x,y,30,spring,gameView);
                gameView.addSpringView(springView);
            }
            case "cistern" -> {
                Cistern cistern = new Cistern();
                steppables.add(cistern);
                nodes.add(cistern);
                cisterns.add(cistern);
                objectCatalog.put(name, cistern);
                CisternView cisternView = new CisternView(x,y,30,cistern,gameView);
                gameView.addCisternView(cisternView);
            }
            case "pipe" -> {
                Pipe pipe = new Pipe(PointCounter.getInstance());
                steppables.add(pipe);
                pipes.add(pipe);
                pickupables.add(pipe);
                objectCatalog.put(name, pipe);
            }
            default -> IO_Manager.writeError("not valid type name", Controller.filetoWrite != null);
        }
        IO_Manager.write(name + " created", Controller.filetoWrite != null);
    }

    /**
     * Az összekötés parancs függvénye, összeköti a neki adott csövet egy node-al
     * @param pipeName - az összekötni kívánt cső
     * @param wNodeName - az összekötni kívánt node
     */
    public void connect(String pipeName, String wNodeName) {

        if (!pipes.contains((Pipe)objectCatalog.get(pipeName))) {
            IO_Manager.writeError("wrong pipe name", Controller.filetoWrite != null);
            return;
        }
        Pipe p = (Pipe) objectCatalog.get(pipeName);

        if (!nodes.contains((WaterNode) objectCatalog.get(wNodeName))) {
            IO_Manager.writeError("wrong node name", Controller.filetoWrite != null);
            return;
        }
        WaterNode w = (WaterNode) objectCatalog.get(wNodeName);

        if (w.AddPipe(p) && p.AddWaterNode(w)) {
            IO_Manager.write(pipeName + ".nodes = " + listWrite(p.getNodes()), Controller.filetoWrite != null);
            IO_Manager.write(wNodeName + ".pipes = " + listWrite(w.getPipes()), Controller.filetoWrite != null);
        }

    }

    /**
     * A mozgás parancsának függvénye, a neki adott játékos a neki adott léphető elemre lép, ha tud
     * @param playerName - a mozgó játékos
     * @param steppableName - az az elem amire mozog
     */
    public void move(String playerName, String steppableName) {

        if (!players.contains((Player) objectCatalog.get(playerName))) {
            IO_Manager.writeError("wrong player name", Controller.filetoWrite != null);
            return;
        }
        Player p = (Player) objectCatalog.get(playerName);

        if (!steppables.contains((Steppable) objectCatalog.get(steppableName))) {
            IO_Manager.writeError("wrong steppable name", Controller.filetoWrite != null);
            return;
        }
        Steppable s = (Steppable) objectCatalog.get(steppableName);
        Steppable prev = p.getStandingOn();

        if (p.Move(s)) {
            IO_Manager.write(steppableName + ".players = " + listWrite(s.getPlayers()), Controller.filetoWrite != null);
            IO_Manager.write(playerName + ".standingOn = " + steppableName, Controller.filetoWrite != null);
            if (prev != null)
                if (prev.getPlayers().isEmpty())
                    IO_Manager.write(getObjectName(prev) + ".players = null", Controller.filetoWrite != null);
                else
                    IO_Manager.write(getObjectName(prev) + ".players = " + listWrite(prev.getPlayers()), Controller.filetoWrite != null);

        }
    }

    /**
     * A lyukasztás parancs függvénye, a neki adott játékos kilyukasztja azt az elemet amin áll(csövet),ha tudja
     * @param playerName - a lyukasztó játékos
     */
    public void pierce(String playerName) {

        if (!players.contains((Player) objectCatalog.get(playerName))) {
            IO_Manager.writeError("wrong player name", Controller.filetoWrite != null);
            return;
        }
        Player p = (Player) objectCatalog.get(playerName);

        if (p.Pierce())

            IO_Manager.write(playerName + ".standingOn.broken = " + "true", Controller.filetoWrite != null);

    }
    /**
     * A ragacsozás parancs függvénye, a neki adott játékos bekeni ragaccsal azt az elemet amin áll(csövet),ha tudja
     * @param playerName - a ragacsozó játékos
     */
    public void glue(String playerName) {

        if (!players.contains((Player) objectCatalog.get(playerName))) {
            IO_Manager.writeError("wrong player name", Controller.filetoWrite != null);
            return;
        }
        Player p = (Player) objectCatalog.get(playerName);

        if (p.Glue())

            IO_Manager.write(playerName + ".standingOn.glued = " + "true", Controller.filetoWrite != null);

    }

    /**
     * A csúszósá tétel parancsának függvénye, a neki adott szabotőr csúszóssá teszi azt az elemet amin áll(csövet),ha tudja
     * @param saboteurName - a csúszóssá tévő szabotőr
     */
    public void lubricate(String saboteurName) {


        if (!saboteurs.contains((Saboteur) objectCatalog.get(saboteurName))) {
            IO_Manager.writeError("wrong saboteur name", Controller.filetoWrite != null);
            return;
        }
        Saboteur s = (Saboteur) objectCatalog.get(saboteurName);

        if (s.Lubricate())
            IO_Manager.write(saboteurName + ".standingOn.lubricated = " + "true", Controller.filetoWrite != null);
    }

    /**
     * A javítás parancs függvénye, a neki adott szerelő megjavítja azt az elemet amin áll,ha tudja
     * @param mechanicName - a lyukasztó játékos
     */
    public void repair(String mechanicName) {

        if (!mechanics.contains((Mechanic) objectCatalog.get(mechanicName))) {
            IO_Manager.writeError("wrong mechanic name", Controller.filetoWrite != null);
            return;
        }
        Mechanic m = (Mechanic) objectCatalog.get(mechanicName);

        if (m.Repair()) {
            IO_Manager.write(mechanicName + ".standingOn.broken = false", Controller.filetoWrite != null);
            if (pipes.contains((Pipe) m.getStandingOn())) {
                IO_Manager.write(mechanicName + ".standingOn.readyToPierce = false", Controller.filetoWrite != null);
            }
        }

    }

    /**
     * Az átirányításért parancsért felelős függvény, meghívja az adott játékos redirect függvényét a paraméterként megadott csövekkel.
     * @param playerName - a játékos aki átirányít
     * @param inPipeName - az a cső amit bevezetőnek akar
     * @param outPipeName - az a cső amit kivezetőnek akar
     */
    public void redirect(String playerName, String inPipeName, String outPipeName) {

        if (!players.contains((Player) objectCatalog.get(playerName))) {
            IO_Manager.writeError("wrong player name", Controller.filetoWrite != null);
            return;
        }
        Player p = (Player) objectCatalog.get(playerName);

        if (!pipes.contains((Pipe)objectCatalog.get(inPipeName))) {
            IO_Manager.writeError("wrong pipe name", Controller.filetoWrite != null);
            return;
        }
        Pipe in = (Pipe) objectCatalog.get(inPipeName);

        if (!pipes.contains((Pipe)objectCatalog.get(outPipeName))) {
            IO_Manager.writeError("wrong pipe name", Controller.filetoWrite != null);
            return;
        }
        Pipe out = (Pipe) objectCatalog.get(outPipeName);

        if (p.Redirect(in, out)) {
            IO_Manager.write(playerName + ".standingOn.activeIn = " + inPipeName, Controller.filetoWrite != null);
            IO_Manager.write(playerName + ".standingOn.activeOut = " + outPipeName, Controller.filetoWrite != null);
        }

    }

    /**
     * A felvevés parancsért felelős függvény, meghívja az adott szerelőre és Pickupable-re a szerelő PickUp függvényét.
     * @param mechanicName - a szerelő aki felvesz
     * @param pickupName - a felvehető játékelem amit felvesz
     */
    public void pickup(String mechanicName, String pickupName) {

        if (!mechanics.contains((Mechanic) objectCatalog.get(mechanicName))) {
            IO_Manager.writeError("wrong mechanic name", Controller.filetoWrite != null);
            return;
        }
        Mechanic m = (Mechanic) objectCatalog.get(mechanicName);

        if (!pickupables.contains((PickupAble) objectCatalog.get(pickupName))) {
            IO_Manager.writeError("wrong pickup name", Controller.filetoWrite != null);
            return;
        }
        PickupAble p = (PickupAble) objectCatalog.get(pickupName);

        if (m.PickUp(p)) {
            IO_Manager.write(mechanicName + ".heldItems = " + pickupName, Controller.filetoWrite != null);

        }
    }

    /**
     * A lerakás parancsért felelős függvény, meghívja az adott szerelő PlaceDown függvényét
     * @param mechanicName - a szerelő aki lerak
     */
    public void placedown(String mechanicName) {

        if (!mechanics.contains((Mechanic) objectCatalog.get(mechanicName))) {
            IO_Manager.writeError("wrong mechanic name", Controller.filetoWrite != null);
            return;
        }
        Mechanic m = (Mechanic) objectCatalog.get(mechanicName);

        if (m.PlaceDown())
            IO_Manager.write(mechanicName + ".heldItems = null", Controller.filetoWrite != null);

    }
    /**
     * Attribútumok lekérdezésének parancsát teljesítő függvény
     * @param objectName - az objektum neve amit lekérdezünk
     * @param attribName - az objektum egyik attribútumának neve amit le akarunk kérdezni
     */
    public void stateGet(String objectName, String attribName) {
        String output = objectName + "." + attribName + " = ";
        Object o = objectCatalog.get(objectName);
        if (mechanics.contains((Mechanic) objectCatalog.get(objectName))) {
            switch (attribName) {
                case fellDownStr -> output += Boolean.toString(((Mechanic) o).isFellDown());
                case stuckStr -> output += Boolean.toString(((Mechanic) o).isStuck());
                case standingOnStr -> output += getObjectName(((Mechanic) o).getStandingOn());
                case stateStr -> output += getObjectName(((Mechanic) o).getState());
                case heldItemsStr -> output += getObjectName(((Mechanic) o).getHeldItems());
                default -> IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else if (saboteurs.contains((Saboteur) objectCatalog.get(objectName))) {
            switch (attribName) {
                case fellDownStr -> output += Boolean.toString(((Saboteur) o).isFellDown());
                case stuckStr -> output += Boolean.toString(((Saboteur) o).isStuck());
                case standingOnStr -> output += getObjectName(((Saboteur) o).getStandingOn());
                case stateStr -> output += getObjectName(((Saboteur) o).getState());
                default -> IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else if (cisterns.contains((Cistern) objectCatalog.get(objectName))) {
            switch (attribName) {
                case createdPickupablesStr -> output += listWrite(((Cistern) o).getCreatedPickupables());
                case pipesStr -> output += listWrite(((Cistern) o).getPipes());
                case playersStr -> output += listWrite(((Cistern) o).getPlayers());
                default -> IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else if (springs.contains((Spring) objectCatalog.get(objectName))) {
            switch (attribName) {
                case pipesStr -> output += listWrite(((Spring) o).getPipes());
                case playersStr -> output += listWrite(((Spring) o).getPlayers());
                default -> IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else if (pumps.contains((Pump) objectCatalog.get(objectName))) {
            switch (attribName) {
                case brokenStr -> output += Boolean.toString(((Pump) o).isBroken());
                case waterCapacityStr -> output += Integer.toString(((Pump) o).getWaterCapacity());
                case heldWaterStr -> output += Integer.toString(((Pump) o).getHeldWater());
                case maximumPipesStr -> output += Integer.toString(((Pump) o).getMaximumPipes());
                case activeInStr -> output += getObjectName(((Pump) o).getActiveIn());
                case activeOutStr -> output += getObjectName(((Pump) o).getActiveOut());
                case pipesStr -> output += listWrite(((Pump) o).getPipes());
                case playersStr -> output += listWrite(((Pump) o).getPlayers());
                default -> IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else if (pipes.contains((Pipe) objectCatalog.get(objectName))) {
            switch (attribName) {
                case brokenStr -> output += Boolean.toString(((Pipe) o).isBroken());
                case waterCapacityStr -> output += Integer.toString(((Pipe) o).getWaterCapacity());
                case heldWaterStr -> output += Integer.toString(((Pipe) o).getHeldWater());
                case readyToPierceStr -> output += Boolean.toString(((Pipe) o).isReadyToPierce());
                case lubricatedStr -> output += Boolean.toString(((Pipe) o).isLubricated());
                case gluedStr -> output += Boolean.toString(((Pipe) o).isGlued());
                case beingHeldStr -> output += Boolean.toString(((Pipe) o).isBeingHeld());
                case nodesStr -> output += listWrite(((Pipe) o).getNodes());
                case playersStr -> output += listWrite(((Pipe) o).getPlayers());
                default -> IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else if (objectName.equals("counter")) {
            switch (attribName) {
                case saboteurPointsStr -> output += Integer.toString(PointCounter.getInstance().GetSaboteurPoints());
                case mechanicPointsStr -> output += Integer.toString(PointCounter.getInstance().GetMechanicPoints());
                case pointsToWinStr -> output += Integer.toString(PointCounter.getInstance().GetPointsToWin());
                default -> IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else {
            IO_Manager.writeError("wrong object name", Controller.filetoWrite != null);
        }

        IO_Manager.write(output, Controller.filetoWrite != null);
    }

    /**
     * Attribútumok beállításának parancsát teljesítő függvény
     * @param objectName - az objektum neve aminek egyik értékét állítjuk be
     * @param attribName - az objektum egyik attribútumának neve amit be akarunk állítani
     * @param attribValue - az érték amire be akarjuk állítani
     */
    public void stateSet(String objectName, String attribName, String attribValue) {
        Object o = objectCatalog.get(objectName);
        if (mechanics.contains((Mechanic) objectCatalog.get(objectName))) {
            switch (attribName) {
                case fellDownStr:
                    ((Mechanic) o).setFellDown(Boolean.parseBoolean(attribValue));
                    break;
                case stuckStr:
                    ((Mechanic) o).setStuck(Boolean.parseBoolean(attribValue));
                    break;
                case stateStr:
                    switch (attribValue) {
                        case moveActionStr -> ((Mechanic) o).setState(PlayerActionState.MOVE_ACTION);
                        case specialActionStr -> ((Mechanic) o).setState(PlayerActionState.SPECIAL_ACTION);
                        case turnOverStr -> ((Mechanic) o).setState(PlayerActionState.TURN_OVER);
                    }
                    break;
                default:
                    IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else if (saboteurs.contains((Saboteur) objectCatalog.get(objectName))) {
            switch (attribName) {
                case fellDownStr:
                    ((Saboteur) o).setFellDown(Boolean.parseBoolean(attribValue));
                    break;
                case stuckStr:
                    ((Saboteur) o).setStuck(Boolean.parseBoolean(attribValue));
                    break;

                case stateStr:
                    switch (attribValue) {
                        case moveActionStr -> ((Saboteur) o).setState(PlayerActionState.MOVE_ACTION);
                        case specialActionStr -> ((Saboteur) o).setState(PlayerActionState.SPECIAL_ACTION);
                        case turnOverStr -> ((Saboteur) o).setState(PlayerActionState.TURN_OVER);
                    }
                    break;
                default:
                    IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else if (cisterns.contains((Cistern) objectCatalog.get(objectName))) {
            IO_Manager.writeError("no attribute to set", Controller.filetoWrite != null);
        } else if (springs.contains((Spring) objectCatalog.get(objectName))) {
            IO_Manager.writeError("no attribute to set", Controller.filetoWrite != null);
        } else if (pumps.contains((Pump) objectCatalog.get(objectName))) {
            switch (attribName) {
                case brokenStr -> ((Pump) o).setBroken(Boolean.parseBoolean(attribValue));
                case waterCapacityStr -> ((Pump) o).setWaterCapacity(Integer.parseInt(attribValue));
                case heldWaterStr -> ((Pump) o).setHeldWater(Integer.parseInt(attribValue));
                case maximumPipesStr -> ((Pump) o).setMaximumPipes(Integer.parseInt(attribValue));
                case activeInStr -> {
                    if (pipes.contains((Pipe) objectCatalog.get(attribValue)))
                        ((Pump) o).setActiveIn((Pipe) objectCatalog.get(attribValue));
                }
                case activeOutStr -> {
                    if (pipes.contains((Pipe) objectCatalog.get(attribValue)))
                        ((Pump) o).setActiveOut((Pipe) objectCatalog.get(attribValue));
                }
                default -> IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else if (pipes.contains((Pipe) objectCatalog.get(objectName))) {
            switch (attribName) {
                case brokenStr -> ((Pipe) o).setBroken(Boolean.parseBoolean(attribValue));
                case waterCapacityStr -> ((Pipe) o).setWaterCapacity(Integer.parseInt(attribValue));
                case heldWaterStr -> ((Pipe) o).setHeldWater(Integer.parseInt(attribValue));
                case readyToPierceStr -> ((Pipe) o).setReadyToPierce(Boolean.parseBoolean(attribValue));
                case lubricatedStr -> ((Pipe) o).setLubricated(Boolean.parseBoolean(attribValue));
                case gluedStr -> ((Pipe) o).setGlued(Boolean.parseBoolean(attribValue));
                case beingHeldStr -> ((Pipe) o).setBeingHeld(Boolean.parseBoolean(attribValue));
                default -> IO_Manager.writeError(WrongAttr, Controller.filetoWrite != null);
            }
        } else {
            IO_Manager.writeError("wrong object name", Controller.filetoWrite != null);
        }
        //Kiírjuk a változást
        stateGet(objectName, attribName);
    }

    /**
     * A következő játékos/kör parancsának függvénye, elintéz mindent ami egy körben történik
     */
    public void turnOver() {
        turnOrder.add(turnOrder.removeFirst());
        turnOrder.getFirst().setState(PlayerActionState.MOVE_ACTION);
        if (turnOrder.getFirst().isFellDown()) {
            turnOrder.getFirst().setFellDown(false);
            SecureRandom random = new SecureRandom();
            int chance = random.nextInt(0, nodes.size());
            boolean ignoreStates = Player.isIgnoreStates();
            Player.setIgnoreStates(true);
            turnOrder.getFirst().Move(nodes.get(chance));
            Player.setIgnoreStates(false);
            turnOrder.getFirst().setState(PlayerActionState.TURN_OVER);
            turnOver();
        }
        if (!turnOrder.getFirst().getStandingOn().canMoveFromHere())
            turnOrder.getFirst().setState(PlayerActionState.SPECIAL_ACTION);
        nextTurn();
        for (Pipe p : pipes) {
            if (!p.isReadyToPierce()) {
                p.setReadyToPierceTimer(p.getReadyToPierceTimer() - 1);
                if (p.getReadyToPierceTimer() == 0)
                    p.setReadyToPierce(true);
            }
        }
        if (getActivePlayer().isStuck()) {
            if (getActivePlayer().getGlueLength() == 0)
                getActivePlayer().setStuck(false);
            else {
                getActivePlayer().setGlueLength(getActivePlayer().getGlueLength() - 1);
                getActivePlayer().setState(PlayerActionState.TURN_OVER);
                turnOver();
            }
        }
    }

    /**
     * Következő kör indulását kezeli, ciszternánál generálás, pumpák elromlása,
     * waterflow, illetve, hogy valamelyik csapat
     * elérte-e már a megfelelő pontszámot,mert akkor játék vége.
     */
    public void nextTurn() {
        for (WaterNode node : nodes) {
            node.WaterFlow();
        }
        for (Cistern cistern : cisterns) {
            cistern.GeneratePickupables();
        }
        if (!deterministic) {
            for (Pump pump : pumps) {
                SecureRandom random = new SecureRandom();
                int chance = random.nextInt(0, 5);
                if (chance == 0) {
                    pump.setBroken(true);
                    IO_Manager.write(getObjectName(pump) + ".broken = true", Controller.filetoWrite != null);
                }
            }
        }
        //end of game?
        if (started) {
            if (counter.GetMechanicPoints() >= counter.GetPointsToWin() || counter.GetSaboteurPoints() >= counter.GetPointsToWin())
                endGame();
        }
    }

    /**
     * A ciszternán generálás parancsának függvénye, meghívja az adott ciszterna GeneratePickupables függvényét a megadott nevekkel.
     * @param cisternName - a ciszterna neve amin generáltatunk
     * @param pipeName - a generált cső neve
     * @param pumpName - a generált pumpa neve
     */
    public void generate(String cisternName, String pipeName, String pumpName) {
        if (!cisterns.contains((Cistern) objectCatalog.get(cisternName))) {
            IO_Manager.writeError("wrong cistern name", Controller.filetoWrite != null);
            return;
        }
        Cistern c = (Cistern) objectCatalog.get(cisternName);

        c.GeneratePickupables();

        IO_Manager.write("This is pipe:" + pipeName);
        IO_Manager.write("This is pump:" + pumpName);
        IO_Manager.write(cisternName + ".createdPickupables = " + listWrite(((Cistern) objectCatalog.get(cisternName)).getCreatedPickupables()), Controller.filetoWrite != null);
    }

    /**
     * A vízfolyás parancs függvénye, adott csomópontra hívja meg a vízfolyást.
     * @param wNodeName - a csomópont neve
     */
    public void waterFlow(String wNodeName) {

        if (!nodes.contains((WaterNode) objectCatalog.get(wNodeName))) {
            IO_Manager.writeError("wrong node name", Controller.filetoWrite != null);
            return;
        }
        WaterNode w = (WaterNode) objectCatalog.get(wNodeName);

        w.WaterFlow();


    }

    /**
     * Szerializált mentés parancs kivitelezésére használt függvény
     * @param filename - a fájl amibe mentünk
     */
    public void save(String filename) {
        try {
            FileOutputStream fops = new FileOutputStream("program.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fops);
            oos.writeObject(this);
        } catch (Exception e) {
            System.err.println("Error saving state to " + filename + ": " + e.getMessage());
        }
        IO_Manager.write("saved state to program.txt", Controller.filetoWrite != null);
    }

    /**
     * Szerializált betöltés parancs kivitelezésére használt függvény
     * @param filename - a fájl amiből betöltünk
     */
    public void load(String filename) {
        try {
            FileInputStream fis = new FileInputStream("program.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            controller = (Controller) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error loading state from " + filename + ": " + e.getMessage());
        }
        IO_Manager.write("loaded state from program.txt", Controller.filetoWrite != null);
    }

    /**
     * A játék végét lefuttató függvény
     */
    public void endGame() {
        started = false;
        gameView.end();
        IO_Manager.write("game over", Controller.filetoWrite != null);
        IO_Manager.write("saboteurPoints = " + counter.GetSaboteurPoints(), Controller.filetoWrite != null);
        IO_Manager.write("mechanicPoints = " + counter.GetMechanicPoints(), Controller.filetoWrite != null);

    }
    /**
     * Visszaadja az objektkatalógusban található elemet, ha benne van
     * @param o - az objektum amit keresünk
     * @return - az objektum neve
     */
    public String getObjectName(Object o) {
        for (Map.Entry<String, Object> e : objectCatalog.entrySet()) {
            if (o.equals(e.getValue()))
                return e.getKey();
        }
        return null;
    }
    /**
     * A játék kezdete, megvizsgálja, hogy van-e elég játékos, random elhelyezi őket egy waternode-on,
     * /mevizsgálja, hogy minden csőnek mindkét vége bekötött-e (funkcionális követelmény), illetve
     * beállítja a kezdeti akciókat.
     */
    public void startGame() {


        for (Pipe p :pipes) {
            PipeView pipeView = new PipeView(
                    gameView.getDrawableByCorrespondingModel(p.getNodes().getFirst()),
                    gameView.getDrawableByCorrespondingModel(p.getNodes().getLast()),
                    5,
                    p,
                    gameView);
            gameView.addPipeView(pipeView);
        }
        if (mechanics.size() < 2 || saboteurs.size() < 2) {
            IO_Manager.writeError("Not enough players on the map", Controller.filetoWrite != null);
            return;
        }
        for (Player p : players) {
            p.RemovePlayer();
            SecureRandom random = new SecureRandom();
            int chance = random.nextInt(0, nodes.size());
            p.Move(nodes.get(chance));
        }
        for (Pipe p : pipes) {
            if (p.getNodes().getFirst() == null || p.getNodes().getLast() == null) {
                IO_Manager.writeError("There is at least one pipe where one of the endpoints is not connected", Controller.filetoWrite != null);
                return;
            }
        }

        //IDE KELL EGY BFS-KERESES A GRAFIKUSBA

        /* START
           Ha be akarjuk állítani másra a nyerési pontot azt itt kell majd
           counter.setPointsToWin(50);
         */
        turnOrder.addAll(mechanics);
        turnOrder.addAll(saboteurs);
        started = true;
        gameView.setStarted(true);
        turnOrder.getFirst().setState(PlayerActionState.MOVE_ACTION);
        if (!turnOrder.getFirst().getStandingOn().canMoveFromHere())
            turnOrder.getFirst().setState(PlayerActionState.SPECIAL_ACTION);
        Player.setIgnoreStates(false);
    }


    /**
     * Kiírja egy lista elemeinek neveit a megadott kimenet szerint
     * @param list - a lista amit kiiratunk
     * @return - a kiiratott szöveg
     */
    private String listWrite(LinkedList<?> list) {
        StringBuilder out = new StringBuilder();
        try {
            for (int i = 0; i < list.size(); i++) {
                out.append(getObjectName(list.get(i)));
                if (i != list.size() - 1) out.append(", ");
            }
        } catch (Exception e) {
            System.err.println("Error occurred while writing the list: " + e.getMessage());
        }
        return out.toString();

    }

    /**
     * Eltávolítja az adott objektumot abból a listá(k)ból, ami(k)ben benne van.
     * @param p - az objektum amit eltávolítunk (Csak Pipe-ra kell)
     */
    public void removeObject(Pipe p) {
        pipes.remove(p);
    }

    /**
     * Az új játék ablaknál egy szerelő színét és nevét beállító függvény
     * @param temp - a szerelő neve
     * @param cp - a colorpicker amivel színt választottunk
     * @return - a szerelő nézete
     */
    public MechanicView makeMechanic(String temp, ColorPicker cp) {
        Mechanic m= (Mechanic) objectCatalog.get(temp);
        if(mechanics.contains(m)){
            MechanicView mechanicView= (MechanicView) gameView.getDrawableByCorrespondingModel(m);
            mechanicView.setColor(cp.getUserColor());
            mechNumber++;
            mechanicView.setNumber(mechNumber);
            return mechanicView;
        }
return null;
    }
    /**
     * Az új játék ablaknál egy szabotőr színét és nevét beállító függvény
     * @param temp - a szabotőr neve
     * @param cp - a colorpicker amivel színt választottunk
     * @return - a szabotőr nézete
     */
    public SaboteurView makeSaboteur(String temp, ColorPicker cp) {
        Saboteur s= (Saboteur) objectCatalog.get(temp);
        if(saboteurs.contains(s)){
            SaboteurView saboteurView= (SaboteurView) gameView.getDrawableByCorrespondingModel(s);
            saboteurView.setColor(cp.getUserColor());
            sabNumber++;
            saboteurView.setNumber(sabNumber);
            return saboteurView;
        }
        return null;
    }


    /**
     * A frame (JFrame) gettere
     * @return - a frame
     */
    public AppFrame getFrame() {
        return frame;
    }
    /**
     * Az objekt katalógus gettere
     * @return - a katalógus
     */
    public HashMap<String, Object> getObjectCatalog() {
        return objectCatalog;
    }
    /**
     * a főmenü gettere
     * @return a főmenü
     */
    public MenuView getMenuView() {
        return menuView;
    }

    /**
     * az új játék ablak gettere
     * @return az ablak
     */
    public NewGameView getNewGameView() {
        return newGameView;
    }

    /**
     * a futó játék ablakának gettere
     * @return az ablak
     */
    public GameView getGameView() {
        return gameView;
    }

    public LinkedList<Mechanic> getMechanics() {
        return mechanics;
    }

    public LinkedList<Saboteur> getSaboteurs() {
        return saboteurs;
    }
}
