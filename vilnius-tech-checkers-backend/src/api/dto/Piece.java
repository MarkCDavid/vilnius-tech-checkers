package api.dto;

public class Piece {

    public Piece(int sideIndex, int promotionLevel) {
        this.sideIndex = sideIndex;
        this.promotionLevel = promotionLevel;
    }

    public int getSideIndex() {
        return sideIndex;
    }

    public int getPromotionLevel() {
        return promotionLevel;
    }

    private final int sideIndex;
    private final int promotionLevel;
}
