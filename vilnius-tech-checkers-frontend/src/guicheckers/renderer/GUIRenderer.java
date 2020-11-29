package guicheckers.renderer;

import api.dto.Cell;
import api.dto.Coordinate;
import api.dto.Move;
import api.dto.Piece;
import guicheckers.sprite.*;

import java.awt.Graphics2D;
import java.awt.Color;
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

    public void drawPieces(Graphics2D graphics, List<Cell> cells) {
        for(var cell : cells) {
            if (cell.getPiece() == null)
                continue;
            drawPiece(graphics, cell.getCoordinate(), cell.getPiece());
        }
    }

    public void drawPiece(Graphics2D graphics, Coordinate coordinate, Piece piece) {
        pieceSpriteCache.get(String.valueOf(piece.getSide().getValue()), piece.getPromotionLevel() > 1).paint(graphics, coordinate.getX(), coordinate.getY(), cellSize, MARGIN);
    }

    public void drawInteractables(Graphics2D graphics, List<Coordinate> interactables) {
        for (var interactable : interactables) {
            availableCell.paint(graphics, interactable.getX(), interactable.getY(), cellSize, HIGHLIGHT_MARGIN);
        }
    }

    public void drawMoves(Graphics2D graphics, List<Move> moves) {
        for (var move : moves) {
            selectedFromCell.paint(graphics, move.getFrom().getX(), move.getFrom().getY(), cellSize, HIGHLIGHT_MARGIN);
            selectedToCell.paint(graphics, move.getTo().getX(), move.getTo().getY(), cellSize, HIGHLIGHT_MARGIN);
        }
    }

    private final static float TEXT_MARGIN = 0.025f;
    public void drawCheckeredPattern(Graphics2D graphics, List<Cell> cells) {
        int cellTextOffset = (int) (cellSize * TEXT_MARGIN);

        for (var cell : cells) {
            var coordinate = cell.getCoordinate();
            var odd = oddCoordinate(coordinate);
            var sprite = cellSprites.get(!odd);
            sprite.paint(graphics, coordinate.getX(), coordinate.getY(), cellSize);
            graphics.setColor(!odd ? Color.WHITE : Color.BLACK);

            var representation = buildCoordinateRepresentation(coordinate);
            graphics.drawString(representation, coordinate.getX() * cellSize + cellTextOffset, (coordinate.getY() + 1) * cellSize - cellTextOffset);
        }
    }

    private static String buildCoordinateRepresentation(Coordinate coordinate) {
        return String.format("(%s, %s)", coordinate.getX(), coordinate.getY());
    }

    public void setCellSize(Graphics2D graphics, int boardSize) {
        cellSize = graphics.getClipBounds().width / boardSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    public boolean oddCoordinate(Coordinate coordinate) {
        return (coordinate.getX() + coordinate.getY()) % 2 == 0;
    }

    private int cellSize;

    private final Sprite availableCell;
    private final Sprite selectedFromCell;
    private final Sprite selectedToCell;

    private final SpritePair<ColorRectSprite> cellSprites;
    private final PieceSpriteCache pieceSpriteCache;
}
