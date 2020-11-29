package api.dto;

public class Piece {

    public Piece(Side side, Integer promotionLevel) {
        this.side = side;
        this.promotionLevel = promotionLevel;
    }

    public Side getSide() {
        return side;
    }

    public Integer getPromotionLevel() {
        return promotionLevel;
    }

    private final Side side;
    private final Integer promotionLevel;
}
