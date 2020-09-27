package lt.vilniustech.guicheckers.sprite;

import java.awt.*;

public class EmptySprite extends Sprite {

    @Override
    public void paint(Graphics2D graphics, int x, int y, int width, int height, float margin) { }

    public static EmptySprite getInstance() {
        if(instance == null)
            instance = new EmptySprite();
        return instance;
    }

    private EmptySprite() { }

    private static EmptySprite instance;
}
