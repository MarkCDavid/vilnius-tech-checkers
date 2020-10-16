package guicheckers.sprite;

import java.awt.*;

public abstract class ColorSprite implements Sprite {

    public ColorSprite(Color color) {
        this.color = color;
    }

    public void paint(Graphics2D graphics, int x, int y, int width, int height, float margin) {
        margin = bound(margin, 0.0f, 1.0f);
        int widthMargin = actualMargin(width, margin);
        int heightMargin = actualMargin(height, margin);
        graphics.setColor(color);
        drawShape(
                graphics,
                x * width + widthMargin / 2,
                y * height + heightMargin / 2,
                width - widthMargin,
                height - heightMargin
        );
    }

    protected abstract void drawShape(Graphics2D graphics, int x, int y, int width, int height);

    private final Color color;
}
