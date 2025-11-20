import nl.saxion.app.SaxionApp;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Application implements Runnable {

    BufferedImage fairyScaled;
    BufferedImage playerScaled;
    String fairyScaledPath = "resources/fairy_scaled.png";
    String playerScaledPath = "resources/player_scaled.png";

    // Reference dimensions (your original design size)
    private static final int REFERENCE_WIDTH = 1536;
    private static final int REFERENCE_HEIGHT = 1024;

    // Scale factors (calculated dynamically)
    private double scaleX;
    private double scaleY;

    public static void main(String[] args) {
        SaxionApp.start(new Application(), 1536, 1024);
    }

    public void run() {
        calculateScale();
        loadSprites();
        intro();
        enterGame();
        gameHUD();
    }

    // Calculate scaling factors based on actual window size
    private void calculateScale() {
        int actualWidth = SaxionApp.getWidth();
        int actualHeight = SaxionApp.getHeight();

        scaleX = (double) actualWidth / REFERENCE_WIDTH;
        scaleY = (double) actualHeight / REFERENCE_HEIGHT;
    }

    // Helper method to scale X coordinates
    private int scaleX(int x) {
        return (int) (x * scaleX);
    }

    // Helper method to scale Y coordinates
    private int scaleY(int y) {
        return (int) (y * scaleY);
    }

    public void intro() {
        SaxionApp.drawImage("resources/fairyButchersFrontPage.png", 0, 0,
                SaxionApp.getWidth(), SaxionApp.getHeight());
        SaxionApp.pause();
    }

    public void enterGame() {
        SaxionApp.clear();
        SaxionApp.drawImage("resources/backgroundPictureBattleground.png", 0, 0,
                SaxionApp.getWidth(), SaxionApp.getHeight());

        // Draw scaled fairy sprite with dynamic positioning
        SaxionApp.drawImage(fairyScaledPath, scaleX(700), scaleY(700));
    }

    public void gameHUD() {
        // Health bar background
        SaxionApp.drawRectangle(scaleX(20), scaleY(20), scaleX(500), scaleY(20));

        // Health bar fill
        SaxionApp.setFill(Color.blue);
        SaxionApp.drawRectangle(scaleX(20), scaleY(47), scaleX(700), scaleY(10));

        // Horizontal line
        SaxionApp.drawLine(0, scaleY(700), scaleX(1260), scaleY(700));

        // Action buttons (4 squares)
        SaxionApp.drawRectangle(scaleX(470), scaleY(760), scaleX(60), scaleY(60));
        SaxionApp.drawRectangle(scaleX(540), scaleY(760), scaleX(60), scaleY(60));
        SaxionApp.drawRectangle(scaleX(610), scaleY(760), scaleX(60), scaleY(60));
        SaxionApp.drawRectangle(scaleX(680), scaleY(760), scaleX(60), scaleY(60));

        // Side bar
        SaxionApp.setFill(Color.magenta);
        SaxionApp.drawRectangle(scaleX(1250), scaleY(100), scaleX(25), scaleY(500));

        // Settings button
        SaxionApp.setFill(Color.GRAY);
        SaxionApp.drawRectangle(scaleX(1180), scaleY(15), scaleX(50), scaleY(50));
    }

    public void loadSprites() {
        // Calculate dynamic scale based on screen resolution
        int spriteScale = (int) Math.max(1, Math.min(scaleX, scaleY) * 4);

        fairyScaled = loadSprite("resources/FairyNo2.png", fairyScaledPath, spriteScale);
        playerScaled = loadSprite("resources/quake E.png", playerScaledPath, spriteScale);
    }

    public BufferedImage loadSprite(String inputPath, String outputPath, int scale) {
        try {
            BufferedImage original = ImageIO.read(new File(inputPath));

            int w = original.getWidth() * scale;
            int h = original.getHeight() * scale;

            BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = scaled.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(original, 0, 0, w, h, null);
            g2.dispose();

            if (outputPath != null && !outputPath.isEmpty()) {
                ImageIO.write(scaled, "png", new File(outputPath));
            }

            return scaled;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}