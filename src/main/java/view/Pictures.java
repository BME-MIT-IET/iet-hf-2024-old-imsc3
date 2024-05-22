package view;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Előre definiált képek
 */
public class Pictures {
        private static final Map<String, BufferedImage> images = new HashMap<>();

        private static BufferedImage loadImage(String path) {
                if (!images.containsKey(path)) {
                        try {
                                images.put(path, ImageUtility.ImageLoad(path));
                        } catch (Exception e) {
                                System.err.println("Error loading image: " + path + "/n " + e.getMessage());
                                images.put(path, null);
                        }
                }
                return images.get(path);
        }

        public static BufferedImage getBackground() {
                return loadImage("1-bg.png");
        }

        public static BufferedImage getMenuTitle() {
                return loadImage("1-title.png");
        }

        public static BufferedImage getPlayButton() {
                return loadImage("1-button-1.png");
        }

        public static BufferedImage getLoadButton() {
                return loadImage("1-button-2.png");
        }

        public static BufferedImage getExitButton() {
                return loadImage("1-button-3.png");
        }

        public static BufferedImage getMinusButton() {
                return loadImage("2-button-minus.png");
        }

        public static BufferedImage getPlusButton() {
                return loadImage("2-button-plus.png");
        }

        public static BufferedImage getMapPrevButton() {
                return loadImage("2-map-preview.png");
        }

        public static BufferedImage getSaveButton() {
                return loadImage("3-button-save.png");
        }

        public static BufferedImage getScoreDivider() {
                return loadImage("3-score-divider.png");
        }

        public static BufferedImage getCisternFilledImg() {
                return loadImage("3-cistern-filled.png");
        }

        public static BufferedImage getSpringFilledImg() {
                return loadImage("3-spring-filled.png");
        }

        public static BufferedImage getRedirectImg() {
                return loadImage("3-popup-atallit.png");
        }

        public static BufferedImage getPickUpImg() {
                return loadImage("3-popup-felemel.png");
        }

        public static BufferedImage getPlaceDownImg() {
                return loadImage("3-popup-lerak.png");
        }

        public static BufferedImage getPierceImg() {
                return loadImage("3-popup-lyukaszt.png");
        }

        public static BufferedImage getLubricateImg() {
                return loadImage("3-popup-megken.png");
        }

        public static BufferedImage getMoveImg() {
                return loadImage("3-popup-mozog.png");
        }

        public static BufferedImage getPassImg() {
                return loadImage("3-popup-passzol.png");
        }

        public static BufferedImage getGlueImg() {
                return loadImage("3-popup-ragaszt.png");
        }

        public static BufferedImage getRepairImg() {
                return loadImage("3-popup-szerel.png");
        }

        public static BufferedImage getPumpIndicatorImg() {
                return loadImage("3-pump-indicator.png");
        }

        public static BufferedImage getMapPreview2() {
                return loadImage("palya2.png");
        }
}