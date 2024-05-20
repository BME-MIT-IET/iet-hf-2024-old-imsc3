package View;

import java.awt.event.MouseEvent;

/**
 * Clickable - kattintható objektumok interfésze
 */
public interface Clickable {
    /**
     * isIn - megadja, hogy az egér a kattintható objektumon belül van-e
     * @param e - egérkattintás
     * @return - igaz, ha a kattintható objektumon belül van az egér
     */

    boolean isIn(MouseEvent e);

    /**
     * clickAction - kattintás eseménye
     * @param e - egérkattintás
     */
    void clickAction(MouseEvent e);

    /**
     * isDraggable - megadja, hogy a kattintható objektum húzható-e
     * @return - igaz, ha mozgatható
     */
    boolean isDraggable();
}
