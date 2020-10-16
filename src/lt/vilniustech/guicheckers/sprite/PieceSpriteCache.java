package lt.vilniustech.guicheckers.sprite;

import java.awt.Color;
import java.util.*;

public class PieceSpriteCache {

    public PieceSpriteCache(ImageSprite piece, ImageSprite king) {
        this.spritePairCache = new HashMap<>();
        this.piece = piece;
        this.king = king;
        this.colors = Arrays.asList(
                Color.decode("#d9b70d"),
                Color.decode("#6e6e6e"),
                Color.decode("#c75656"),
                Color.decode("#540f99"),
                Color.decode("#298fcf")
        );

    }

    public ImageSprite get(String side, boolean isKing) {
        if(!spritePairCache.containsKey(side)) {
            spritePairCache.put(side, buildPieceSpritePair());
        }

        return spritePairCache.get(side).get(!isKing);
    }

    private SpritePair<ImageSprite> buildPieceSpritePair() {
        var pair = new SpritePair<>(piece.copy(), king.copy());

        for (ImageSprite sprite : pair) {
            var image = sprite.getImage();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int rgba = image.getRGB(x, y);
                    var colorWith = this.colors.get(this.spritePairCache.size());
                    var color = new Color(rgba, true);
                    color = new Color(
                            map(0, 255, 0, color.getRed(), colorWith.getRed()),
                            map(0, 255, 0, color.getGreen(), colorWith.getGreen()),
                            map(0, 255, 0, color.getBlue(), colorWith.getBlue()),
                            color.getAlpha()
                    );
                    image.setRGB(x, y, color.getRGB());
                }
            }

        }

        return pair;
    }


    private int map(int originalFrom, int originalTo, int targetFrom, int targetTo, int value) {
        float originalDelta = (float)(originalTo - originalFrom);
        float targetDelta = (float)(targetTo - targetFrom);
        float scale = targetDelta / originalDelta;
        return Math.round(value * scale + targetFrom);
    }

    private final List<Color> colors;

    private final ImageSprite piece;
    private final ImageSprite king;

    private final Map<String, SpritePair<ImageSprite>> spritePairCache;

}
