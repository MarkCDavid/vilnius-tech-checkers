package guicheckers;

import api.dto.Coordinate;
import api.dto.Move;
import api.dto.Piece;
import api.endpoints.GameService;
import backend.Board;
import guicheckers.sprite.*;
import backend.utils.iterator.CoordinateIterator;

import java.awt.*;
import java.util.List;


public class GUIRenderer {

    public GUIRenderer() {

        this.availableCell = new ColorEllipseSprite(Color.decode("#2ec761"));
        this.selectedFromCell = new ColorEllipseSprite(Color.decode("#2ec761"));
        this.selectedToCell = new ColorEllipseSprite(Color.decode("#482ec7"));

        this.cellSprites = new SpritePair<>(
            new ColorRectSprite(Color.decode("#664400")),
            new ColorRectSprite(Color.decode("#ffffee"))
        );

        this.pieceSpriteCache = new PieceSpriteCache(
            new ImageSprite("vilnius-tech-checkers-frontend/resources/img/BasePiece.png"),
            new ImageSprite("vilnius-tech-checkers-frontend/resources/img/BaseKing.png")
        );
    }

    private static final float MARGIN = 0.2f;
    private static final float HIGHLIGHT_MARGIN = MARGIN - 0.1f;

    public void drawPieces(Graphics2D graphics, GameService gameService) {

        for(var cell : gameService.getBoard()) {
            var piece = cell.getPiece();
            if (piece == null)
                continue;
            drawPiece(graphics, cell.getCoordinate().getX(), cell.getCoordinate().getY(), piece);
        }
    }

    public void drawPiece(Graphics2D graphics, int row, int column, Piece piece) {
        pieceSpriteCache.get(String.valueOf(piece.getSideIndex()), piece.getPromotionLevel() > 1).paint(graphics, row, column, cellSize, MARGIN);
    }

    public void drawAvailableMoves(Graphics2D graphics, GameService gameService) {
        for (var interactable : gameService.getInteractable()) {
            availableCell.paint(graphics, interactable.getX(), interactable.getY(), cellSize, HIGHLIGHT_MARGIN);
        }
    }

    public void drawSelectedMoves(Graphics2D graphics, GameService gameService, Coordinate from) {
        for (var move : gameService.getAvailableMoves(from)) {
            selectedFromCell.paint(graphics, move.getFrom().getX(), move.getFrom().getY(), cellSize, HIGHLIGHT_MARGIN);
            selectedToCell.paint(graphics, move.getTo().getX(), move.getTo().getY(), cellSize, HIGHLIGHT_MARGIN);
        }
    }

    private final static float TEXT_MARGIN = 0.025f;
    public void drawCheckeredPattern(Graphics2D graphics, int boardSize) {
        int cellTextOffset = (int)(cellSize * TEXT_MARGIN);
        for(var coordinate: new CoordinateIterator(boardSize)) {
            Sprite sprite = cellSprites.get(coordinate.isEven());
            sprite.paint(graphics, coordinate.getColumn(), coordinate.getRow(), cellSize);
            graphics.setColor(coordinate.isEven() ? Color.WHITE : Color.BLACK);
            graphics.drawString(coordinate.toString(), coordinate.getColumn() * cellSize + cellTextOffset, (coordinate.getRow() + 1) * cellSize - cellTextOffset);
        }
    }

    public void setCellSize(Graphics graphics, int boardSize) {
        cellSize = graphics.getClipBounds().width / boardSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    private int cellSize;

    private final Sprite availableCell;
    private final Sprite selectedFromCell;
    private final Sprite selectedToCell;

    private final SpritePair<ColorRectSprite> cellSprites;
    private final PieceSpriteCache pieceSpriteCache;
}
