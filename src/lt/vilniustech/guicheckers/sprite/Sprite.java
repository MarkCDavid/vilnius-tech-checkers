package lt.vilniustech.guicheckers.sprite;

import java.awt.*;

public abstract class Sprite {

    public void paint(Graphics2D graphics, int x, int y, int width, int height) {
        paint(graphics, x, y, width, height, 0.0f);
    }

    public void paint(Graphics2D graphics, int x, int y, int size) {
        paint(graphics, x, y, size, size, 0.0f);
    }

    public void paint(Graphics2D graphics, int x, int y, int size, float margin) {
        paint(graphics, x, y, size, size, margin);
    }

    public abstract void paint(Graphics2D graphics, int x, int y, int width, int height, float margin);

    public float bound(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    public int actualMargin(int value, float margin) {
        return (int)(value * margin);
    }


}
