package lt.vilniustech.guicheckers.sprite;

import java.awt.*;

public class ColorEllipseSprite extends ColorSprite {

    public ColorEllipseSprite(Color color) {
        super(color);
    }

    @Override
    protected void drawShape(Graphics2D graphics, int x, int y, int width, int height) {
        graphics.fillOval(x, y, width, height);
    }
}
