package lt.vilniustech.guicheckers;

import lt.vilniustech.Board;
import lt.vilniustech.Coordinate;
import lt.vilniustech.Piece;
import lt.vilniustech.guicheckers.sprite.ColorEllipseSprite;
import lt.vilniustech.guicheckers.sprite.ColorRectSprite;
import lt.vilniustech.guicheckers.sprite.ImageSprite;
import lt.vilniustech.guicheckers.sprite.Sprite;
import lt.vilniustech.moves.base.Move;
import lt.vilniustech.utils.iterator.CoordinateIterator;

import java.awt.*;
import java.util.List;


public class GUIRenderer {

    public GUIRenderer() {
        this.availableCell = new ColorEllipseSprite(Color.decode("#2ec761"));
        this.selectedFromCell = new ColorEllipseSprite(Color.decode("#2ec761"));
        this.selectedToCell = new ColorEllipseSprite(Color.decode("#482ec7"));

        this.blackCell = new ColorRectSprite(Color.decode("#664400"));
        this.whiteCell = new ColorRectSprite(Color.decode("#ffffee"));

        this.blackPiece = new ImageSprite("resources/img/BlackPiece.png");
        this.blackKing =  new ImageSprite("resources/img/BlackKing.png");
        this.whitePiece = new ImageSprite("resources/img/WhitePiece.png");
        this.whiteKing = new ImageSprite("resources/img/WhiteKing.png");
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
        Sprite sprite;
        switch (piece.getSide().toString()) {
            case "Black" -> sprite = piece.isKing() ? blackKing : blackPiece;
            case "White" -> sprite = piece.isKing() ? whiteKing : whitePiece;
            default -> sprite = ImageSprite.invalidSprite();
        }
        sprite.paint(graphics, row, column, cellSize, MARGIN);
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
        for (Coordinate coordinate : new CoordinateIterator(board.getBoardSize())) {
            for (Move selectedMove : selectedMoves) {
                Sprite sprite = null;

                if (selectedMove.getFrom().equals(coordinate)) sprite = selectedFromCell;
                else if (selectedMove.getTo().equals(coordinate)) sprite = selectedToCell;

                if(sprite == null)
                    continue;

                sprite.paint(graphics, coordinate.getColumn(), coordinate.getRow(), cellSize, HIGHLIGHT_MARGIN);
            }
        }
    }

    private final float margin = 0.025f;
    public void drawCheckeredPattern(Graphics2D graphics, Board board) {
        int cellTextOffset = (int)(cellSize * margin);
        for(Coordinate coordinate: new CoordinateIterator(board.getBoardSize())) {
            Sprite sprite = coordinate.isEven() ? this.blackCell : this.whiteCell;
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

    private final Sprite blackCell;
    private final Sprite whiteCell;

    private final Sprite blackPiece;
    private final Sprite blackKing;
    private final Sprite whitePiece;
    private final Sprite whiteKing;
}
