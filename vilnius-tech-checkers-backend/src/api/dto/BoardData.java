package api.dto;

public class BoardData {

    public BoardData(Integer sideLength, Integer cellCount) {
        this.sideLength = sideLength;
        this.cellCount = cellCount;
    }

    public Integer getSideLength() {
        return sideLength;
    }

    public Integer getCellCount() {
        return cellCount;
    }

    private final Integer sideLength;
    private final Integer cellCount;
}
