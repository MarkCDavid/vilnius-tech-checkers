package guicheckers.controls;

import api.dto.*;
import guicheckers.renderer.GUIRenderer;
import guicheckers.events.CellClickListener;
import guicheckers.events.CellClickSupport;
import guicheckers.utility.MouseReleasedListener;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    public GamePanel() {
        this.renderer = new GUIRenderer();
        this.cellClickSupport = new CellClickSupport();
        this.addMouseListener(getMouseReleasedListener());
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public void setInteractables(List<Coordinate> interactables) {
        this.interactables = interactables;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public void setBoardSideLength(Integer boardSideLength) {
        this.boardSideLength = boardSideLength;
    }

    public void invalidateData() {
        boardSideLength = null;
        cells = new ArrayList<>();
        interactables = new ArrayList<>();
        moves = new ArrayList<>();
    }


    @Override
    public void paint(Graphics graphics) {
        if(boardSideLength == null)
            return;

        paint2D((Graphics2D) graphics);
    }

    private void paint2D(Graphics2D graphics) {
        renderer.setCellSize(graphics, boardSideLength);
        renderer.drawCheckeredPattern(graphics, cells);
        renderer.drawInteractables(graphics, interactables);
        renderer.drawMoves(graphics, moves);
        renderer.drawPieces(graphics, cells);
    }

    private final GUIRenderer renderer;
    private final CellClickSupport cellClickSupport;

    public void addCellClickListener(CellClickListener listener) {
        cellClickSupport.addCellClickListener(listener);
    }

    public void removeCellClickListener(CellClickListener listener) {
        cellClickSupport.removeCellClickListener(listener);
    }
    private MouseReleasedListener getMouseReleasedListener() {
        return new MouseReleasedListener() {
            public void mouseReleased(MouseEvent e) {
                var row = e.getY() / renderer.getCellSize();
                var column = e.getX() / renderer.getCellSize();
                cellClickSupport.emit(new Coordinate(column, row));
            }
        };
    }

    private List<Move> moves;
    private List<Coordinate> interactables;
    private List<Cell> cells;
    private Integer boardSideLength;
}
