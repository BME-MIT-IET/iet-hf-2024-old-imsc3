package view;

import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Drawable - kirajzolható objektumok absztrakt ősosztálya, leszármazottja a JComponent osztály
 */
public abstract class Drawable extends JComponent {
    protected Window view;
    protected int x;
    protected int y;

    /**
     * A kirajzolásért felelős függvény
     *
     * @param g - a kirajzolásért felelős Graphics objektum
     */
    @Override
    public abstract void paint(Graphics g);

    /**
     * Rajzolható objektumok konstruktora
     *
     * @param x - x koordináta
     * @param y - y koordináta
     * @param v - a hozzá tartozó ablak
     */
    public Drawable(int x, int y, Window v) {
        this.x = x;
        this.y = y;
        this.view = v;
    }

    /**
     * Frissíti a kirajzolható objektumot
     */
    public abstract void update();

    public abstract Object getCorrespondingModelElement();

    /**
     * Megjelenítés
     *
     * @param x          - x koordináta
     * @param y          - y koordináta
     * @param graphics2D - a kirajzolásért felelős Graphics2D objektum
     */
    public void paintOnPlayer(int x, int y, Graphics2D graphics2D) {

    }

    public double getPlayerAngle(Player p, LinkedList<Player> players) {
        double[] angles = new double[players.size()];
        for (int i = 0; i < angles.length; ++i) {
            angles[i] = i * 20 - (double)(angles.length - 1) * 10;
        }
        try {
            return angles[players.indexOf(p)];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Player not found in the list", e);
        }
    }

    public int[] setCapX(int x) {
        int[] capX = new int[3];
        capX[0] = x;
        capX[1] = x - 8;
        capX[2] = x + 8;
        return capX;
    }

    public int[] setCapY(int y) {
        int[] capY = new int[3];
        capY[0] = y - 22;
        capY[1] = y - 15;
        capY[2] = y - 15;
        return capY;
    }
}
