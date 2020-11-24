package guicheckers;

import backend.Board;
import backend.Coordinate;
import backend.Piece;
import guicheckers.sprite.*;
import backend.moves.base.Move;
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

    public void drawPieces(Graphics2D graphics, Board board) {
        for (Coordinate coordinate : new CoordinateIterator(board.getBoardSize())) {
            Piece piece = board.getPiece(coordinate);
            if (piece == null) continue;
            drawPiece(graphics, coordinate.getColumn(), coordinate.getRow(), piece);
        }
    }

    public void drawPiece(Graphics2D graphics, int row, int column, Piece piece) {
        pieceSpriteCache.get(piece.getSide().toString(), piece.isKing()).paint(graphics, row, column, cellSize, MARGIN);
    }

    public void drawAvailableMoves(Graphics2D graphics, Board board, List<Move> availableMoves) {
        for (Coordinate coordinate : new CoordinateIterator(board.getBoardSize())) {
            for (Move availableMove : availableMoves) {
                if (!availableMove.getFrom().equals(coordinate))
                    continue;
                availableCell.paint(graphics, coordinate.getColumn(), coordinate.getRow(), cellSize, HIGHLIGHT_MARGIN);
            }
        }
    }

    public void drawSelectedMoves(Graphics2D graphics, Board board, List<Move> selectedMoves) {
        for (var coordinate : new CoordinateIterator(board.getBoardSize())) {
            for (var selectedMove : selectedMoves) {
                Sprite sprite = null;

                if (selectedMove.getFrom().equals(coordinate)) sprite = selectedFromCell;
                else if (selectedMove.getTo().equals(coordinate)) sprite = selectedToCell;

                if(sprite == null)
                    continue;

                sprite.paint(graphics, coordinate.getColumn(), coordinate.getRow(), cellSize, HIGHLIGHT_MARGIN);
            }
        }
    }

    private final static float TEXT_MARGIN = 0.025f;
    public void drawCheckeredPattern(Graphics2D graphics, Board board) {
        int cellTextOffset = (int)(cellSize * TEXT_MARGIN);
        for(var coordinate: new CoordinateIterator(board.getBoardSize())) {
            Sprite sprite = cellSprites.get(coordinate.isEven());
            sprite.paint(graphics, coordinate.getColumn(), coordinate.getRow(), cellSize);
            graphics.setColor(coordinate.isEven() ? Color.WHITE : Color.BLACK);
            graphics.drawString(coordinate.toString(), coordinate.getColumn() * cellSize + cellTextOffset, (coordinate.getRow() + 1) * cellSize - cellTextOffset);
        }
    }

    public void setCellSize(Graphics graphics, Board board) {
        cellSize = graphics.getClipBounds().width / board.getBoardSize();
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
