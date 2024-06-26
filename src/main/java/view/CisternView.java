package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Model.ActionType;
import Model.Cistern;
import Model.Player;
import Model.Steppable;

/**
 * CisternView - víztárolók megjelenítése
 */
public class CisternView extends Drawable implements Clickable, CreatePopUpBar, SteppableView {

    private final Cistern cistern;
    private final int r;
    private GameView gameView = null;
    private BufferedImage sprite;

    /**
     * CisternView konstruktor
     * @param x - víztároló x koordinátája
     * @param y - víztároló y koordinátája
     * @param r - víztároló sugara
     * @param c - víztároló modell
     * @param v - ablak, amelyen a víztároló megjelenik
     */
    public CisternView(int x, int y, int r, Cistern c, Window v) {
        super(x, y, v);
        this.r = r;
        cistern = c;
        sprite = Pictures.getCisternFilledImg();
        sprite = ImageUtility.scaleImage(sprite, 2*r);
        if (WindowOptions.windowOption == WindowOptions.GAME)
            gameView = (GameView) v;
    }

    /**
     * CisternView megjelenítése
     * @param g - a kirajzolásért felelős Graphics objektum
     */
    @Override
    public void paint(Graphics g) {
        //g.drawPolygon(r);
        AppFrame.setGraphicQuality(g);
        g.drawImage(sprite, x - sprite.getWidth(null) / 2, y - sprite.getHeight(null)/2, null);
    }

    /**
     * CisternView frissítése
     */
    @Override
    public void update() {

    }

    /**
     * A víztárolóhoz tartozó hitboxon belül kapott kattintás kezelése
     * @param e - egérkattintás
     * @return - hitboxon belüli kattintás esetén true, egyébként false
     */
    @Override
    public boolean isIn(MouseEvent e) {
        return Math.sqrt(Math.pow((double)e.getX() - x, 2) + Math.pow((e.getY() - y), 2)) < r;
    }

    /**
     * Kattintás kezelése
     * @param e - egérkattintás
     */
    @Override
    public void clickAction(MouseEvent e) {
        if (gameView.cisternDisplay != null)
            gameView.removeCisternDisplay();
        createPopUpBar(x, y, 100, gameView, this);
    }

    @Override
    public Object getCorrespondingModelElement() {
        return cistern;
    }

    /**
     * Alapértelmezett pozíció lekérdezése
     * @return - alapértelmezett pozíció
     */
    @Override
    public Point getDefaultPlayerPosition() {
        return new Point(x, y - r - 20);
    }

    /**
     * Forgási középpont lekérdezése
     * @return - forgási középpont
     */
    @Override
    public Point getRotationCenter() {

        return new Point(x, y);
    }

    /**
     * Játékos szöge lekérdezése
     * @param p - játékos
     * @return - játékos szöge
     */
    public double getPlayerAngle(Player p) {
        return super.getPlayerAngle(p, cistern.getPlayers());
    }

    @Override
    public Steppable getCorrespondingModelSteppable() {
        return cistern;
    }

    /**
     * Víztároló mozgatható-e
     * @return - mozgatható-e
     */
    @Override
    public boolean isDraggable() {
        return true;
    }

    /**
     * display létrehozása
     */
    public void displayCreated() {
        ActionType[] at = new ActionType[6];
        at[4] = ActionType.PICKUP_PIPE;
        at[5] = ActionType.PICKUP_PUMP;
        createPopUpBarWithActions(x, y, 100, view, this, at);
    }
}
