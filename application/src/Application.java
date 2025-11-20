import nl.saxion.app.SaxionApp;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Application implements Runnable {

    BufferedImage fairyScaled;
    BufferedImage playerScaled;
    String fairyScaledPath = "resources/fairy_scaled.png";
    playerScaledPath = "resources/player_scaled.png";

    public static void main(String[] args) {
        SaxionApp.start(new Application(), 1536, 1024);
    }

    public void run() {
        loadSprites();
        intro();
        enterGame();
        gameHUD();
    }

    public void intro() {
        SaxionApp.drawImage("resources/fairyButchersFrontPage.png", 0, 0);
        SaxionApp.pause();
    }

    public void enterGame() {
        SaxionApp.clear();
        SaxionApp.drawImage("resources/backgroundPictureBattleground.png", 0, 0);

        // Draw scaled fairy sprite
        SaxionApp.drawImage(fairyScaledPath, 700, 700);
    }

    public void gameHUD() {
        SaxionApp.drawRectangle(20, 20, 500, 20);
        SaxionApp.setFill(Color.blue);
        SaxionApp.drawRectangle(20, 47, 700, 10);
        SaxionApp.drawLine(0, 700, 1260, 700);
        SaxionApp.drawRectangle(470, 760, 60, 60);
        SaxionApp.drawRectangle(540, 760, 60, 60);
        SaxionApp.drawRectangle(610, 760, 60, 60);
        SaxionApp.drawRectangle(680, 760, 60, 60);
        SaxionApp.setFill(Color.magenta);
        SaxionApp.drawRectangle(1250, 100, 25, 500);
        SaxionApp.setFill(Color.GRAY);
        SaxionApp.drawRectangle(1180, 15, 50, 50);
    }

    public void loadSprites() {
        // Keep your existing hard-coded call but use the generalized function
        fairyScaled = loadSprite("resources/FairyNo2.png", fairyScaledPath, 4);
        playerScaled = loadSprite("resources/quake E.png", playerScaledPath, 4);
    }

    // Generalized sprite loader function
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

            // Save scaled version if output path is provided
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
