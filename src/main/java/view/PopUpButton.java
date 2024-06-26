package view;
import Controller.Controller;
import Model.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class PopUpButton extends Drawable implements Clickable  {

        public PopUpButton(int x, int y, int r, ActionType a, Window v, SteppableView creator) {
            super(x, y, v);
            this.r = r;
            actiontype = a;

            switch (actiontype) {
                    case GLUE -> sprite = Pictures.getGlueImg();
                    case LUBRICATE -> sprite = Pictures.getLubricateImg();
                    case MOVE -> sprite = Pictures.getMoveImg();
                    case PASS -> sprite = Pictures.getPassImg();
                    case PIERCE -> sprite = Pictures.getPierceImg();
                    case REPAIR -> sprite = Pictures.getRepairImg();
                    case REDIRECT -> sprite = Pictures.getRedirectImg();
                    case PLACEDOWN -> sprite = Pictures.getPlaceDownImg();
                    case PICKUP_PIPE, PICKUP_PUMP -> sprite = Pictures.getPickUpImg();
            }
            sprite = ImageUtility.scaleImage(sprite, 2*r);
            drawLoc = ImageUtility.centerImage(new Point(x, y), sprite);
            this.creator = creator;
        }
        private final SteppableView creator;
        private final ActionType actiontype;
        private BufferedImage sprite = null;
        private final int r;
        Point drawLoc;
        public static boolean waitingForParameter = false;
        public static LinkedList<PipeView> parameters = new LinkedList<>();
        public static PopUpButton paramClient;
        private float opacity = 0.0f;
        @Override
        public boolean isIn(MouseEvent e) {
            return Math.sqrt(Math.pow((double)e.getX() - x, 2) + Math.pow(((double) e.getY() - y), 2)) < r;
        }

        @Override
        public void clickAction(MouseEvent e) {
                Controller c = Controller.getInstance();
                Player p = c.getActivePlayer();
                Steppable s = creator.getCorrespondingModelSteppable();
                switch (actiontype) {
                        case GLUE -> c.glue(c.getObjectName(p));
                        case LUBRICATE -> c.lubricate(c.getObjectName(p));
                        case MOVE -> c.move(c.getObjectName(p), c.getObjectName(s));
                        case PASS -> c.pass();
                        case PIERCE -> c.pierce(c.getObjectName(p));
                        case REPAIR -> c.repair(c.getObjectName(p));
                        case REDIRECT -> {
                                waitingForParameter = true;
                                paramClient = this;
                        }
                        case PLACEDOWN -> c.placedown(c.getObjectName(p));
                        case PICKUP_PIPE -> {
                                if (((GameView) view).cisterns.contains((CisternView) creator)) {
                                        Cistern cis = (Cistern) s;
                                        Pipe pipe = cis.getGeneratedPipes().getFirst();
                                        c.pickup(c.getObjectName(p), c.getObjectName(pipe));
                                } else {
                                        c.pickup(c.getObjectName(p), c.getObjectName(s));
                                }
                        }
                        case PICKUP_PUMP -> {
                                Cistern cis = (Cistern) s;
                                Pump pump = cis.getGeneratedPumps().getFirst();
                                c.pickup(c.getObjectName(p), c.getObjectName(pump));
                        }
                }

                ((GameView) view).removePopUpBar();
        }

        @Override
        public synchronized void paint(Graphics g) {
                if (opacity < 0.9f)
                        opacity += 0.05f;
                else
                        opacity = 1.0f;
                AppFrame.setGraphicQuality(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(sprite, drawLoc.x, drawLoc.y, null);
                PopUpBar cd = ((GameView) view).cisternDisplay;
                if (cd != null) {
                        CisternView c = (CisternView) cd.getCreator();
                        g2d.setColor(Color.BLACK);
                        g2d.setFont(new Font("Inter", Font.BOLD, 20));
                        if (actiontype == ActionType.PICKUP_PIPE)
                                g2d.drawString("csövek: " + ((Cistern)c.getCorrespondingModelElement()).getGeneratedPipes().size(), x + r + 15, y);
                        if (actiontype == ActionType.PICKUP_PUMP)
                                g2d.drawString("pumpák: " + ((Cistern)c.getCorrespondingModelElement()).getGeneratedPumps().size(), x + r + 15, y);
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        }
        @Override
        public void update() {

        }
        @Override
        public Object getCorrespondingModelElement() {
                return null;
        }

        @Override
        public boolean isDraggable() {
                return false;
        }

        public static void paramsReceived() {
                Controller c = Controller.getInstance();
                Player p = c.getActivePlayer();
                if (paramClient.actiontype == ActionType.REDIRECT) {
                        c.redirect(c.getObjectName(p), c.getObjectName(parameters.getFirst().getCorrespondingModelElement()), c.getObjectName(parameters.getLast().getCorrespondingModelElement()));
                }
                waitingForParameter = false;
                paramClient = null;
                parameters.clear();
                for (PipeView pipe : c.getGameView().pipes)
                        pipe.selected = false;
        }
}
