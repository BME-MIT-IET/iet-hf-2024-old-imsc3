package View;

import Controller.Controller;

import java.awt.event.*;

/**
 * Egér eseményeket kezelő osztály, implementálja a MouseListener és MouseMotionListener interfészeket
 */
public class MouseIn implements MouseListener, MouseMotionListener {
    /**
     * Kattintás eseményt kezelő függvény
     * @param e - kattintás
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (WindowOptions.windowOption) {
            case MENU -> Controller.getInstance().getMenuView().mouseClicked(e);
            case NEWGAME -> Controller.getInstance().getNewGameView().mouseClicked(e);
            case GAME -> Controller.getInstance().getGameView().mouseClicked(e);
            default -> {}
        }
    }

    /**
     * Egér lenyomás eseményt kezelő függvény
     * @param e - egér esemény
     */
    @Override
    public void mousePressed(MouseEvent e) {
        switch (WindowOptions.windowOption) {
            case MENU -> Controller.getInstance().getMenuView().mousePressed(e);
            case NEWGAME -> Controller.getInstance().getNewGameView().mousePressed(e);
            case GAME -> Controller.getInstance().getGameView().mousePressed(e);
            default -> {}
        }
    }

    /**
     * Egér kattintás elengedése
     * @param e egéresemény
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (WindowOptions.windowOption) {
            case MENU -> Controller.getInstance().getMenuView().mouseReleased(e);
            case NEWGAME -> Controller.getInstance().getNewGameView().mouseReleased(e);
            case GAME -> Controller.getInstance().getGameView().mouseReleased(e);
            default -> {}
        }
    }

    /**
     * mouseEntered eseményt kezelő függvény
     * @param e egéresemény
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        switch (WindowOptions.windowOption) {
            case MENU -> Controller.getInstance().getMenuView().mouseEntered(e);
            case NEWGAME -> Controller.getInstance().getNewGameView().mouseEntered(e);
            case GAME -> Controller.getInstance().getGameView().mouseEntered(e);
            default -> {}
        }
    }

    /**
     * mouseExited eseményt kezelő függvény
     * @param e egéresemény
     */
    @Override
    public void mouseExited(MouseEvent e) {
        switch (WindowOptions.windowOption) {
            case MENU -> Controller.getInstance().getMenuView().mouseExited(e);
            case NEWGAME -> Controller.getInstance().getNewGameView().mouseExited(e);
            case GAME -> Controller.getInstance().getGameView().mouseExited(e);
            default -> {}
        }
    }

    /**
     * mouseDragged eseményt kezelő függvény
     * @param e egéresemény
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        switch (WindowOptions.windowOption) {
            case MENU -> Controller.getInstance().getMenuView().mouseDragged(e);
            case NEWGAME -> Controller.getInstance().getNewGameView().mouseDragged(e);
            case GAME -> Controller.getInstance().getGameView().mouseDragged(e);
            default -> {}
        }
    }

    /**
     * mouseMoved eseményt kezelő függvény
     * @param e egéresemény
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        switch (WindowOptions.windowOption) {
            case MENU -> Controller.getInstance().getMenuView().mouseMoved(e);
            case NEWGAME -> Controller.getInstance().getNewGameView().mouseMoved(e);
            case GAME -> Controller.getInstance().getGameView().mouseMoved(e);
            default -> {}
        }
    }
}
