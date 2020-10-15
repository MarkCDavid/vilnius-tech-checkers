package lt.vilniustech.guicheckers.sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSprite implements Sprite {

    public static ImageSprite invalidSprite() {
        return new ImageSprite(getInvalidImage());
    }

    public ImageSprite(String path) {
        this.image = getImage(path);
    }

    public ImageSprite(BufferedImage image) {
        this.image = image;
    }

    public void paint(Graphics2D graphics, int x, int y, int width, int height, float margin) {
        margin = bound(margin, 0.0f, 1.0f);
        int widthMargin = actualMargin(width, margin);
        int heightMargin = actualMargin(height, margin);
        graphics.drawImage(
                image,
                x * width + widthMargin / 2,
                y * height + heightMargin / 2,
                width - widthMargin,
                height - heightMargin,
                null
        );
    }

    private static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException exception) {
            return getInvalidImage();
        }
    }

    private static BufferedImage getInvalidImage() {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, Color.BLACK.getRGB());
        image.setRGB(1, 1, Color.BLACK.getRGB());
        image.setRGB(0, 1, Color.MAGENTA.getRGB());
        image.setRGB(1, 0, Color.MAGENTA.getRGB());
        return image;
    }

    private final BufferedImage image;
}
