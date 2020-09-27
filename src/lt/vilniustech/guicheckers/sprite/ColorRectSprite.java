package lt.vilniustech.guicheckers.sprite;

import java.awt.*;

public class ColorRectSprite extends ColorSprite {

    public ColorRectSprite(Color color) {
        super(color);
    }

    @Override
    protected void drawShape(Graphics2D graphics, int x, int y, int width, int height) {
        graphics.fillRect(x, y, width, height);
    }
}
