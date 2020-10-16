package guicheckers;

import backend.Coordinate;
import backend.manager.GameManager;
import backend.moves.base.Move;
import guicheckers.events.CellClickListener;
import guicheckers.events.CellClickSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    @Override
    public void paint(Graphics graphics) {
        renderer.setCellSize(graphics, gameManager.getBoard());
        drawBoard((Graphics2D) graphics);
    }

    private void drawBoard(Graphics2D graphics) {

        renderer.drawCheckeredPattern(graphics, gameManager.getBoard());

        if(drawHighlights)
            drawHighlights(graphics);

        renderer.drawPieces(graphics, gameManager.getBoard());
    }

    private void drawHighlights(Graphics2D graphics) {
        if (selectedMoves.isEmpty())
            renderer.drawAvailableMoves(graphics, gameManager.getBoard(), gameManager.getAvailableMoves());
        else
            renderer.drawSelectedMoves(graphics, gameManager.getBoard(), selectedMoves);
    }

    private boolean drawHighlights = true;

    public void setDrawHighlights(boolean drawHighlights) {
        this.drawHighlights = drawHighlights;
    }


    public GameManager getGameManager() {
        return gameManager;
    }

    public void setSelectedMoves(List<Move> selectedMoves) {
        this.selectedMoves = selectedMoves;
    }

    public List<Move> getSelectedMoves() {
        return this.selectedMoves;
    }

    private List<Move> selectedMoves = new ArrayList<>();

    public GamePanel(GameManager gameManager) {
        this.renderer = new GUIRenderer();
        this.gameManager = gameManager;
        this.cellClickSupport = new CellClickSupport();


        addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(getGameManager().isFinished()) return;
                int row = e.getY() / renderer.getCellSize();
                int column = e.getX() / renderer.getCellSize();
                Coordinate coordinate = new Coordinate(column, row);
                cellClickSupport.emit(coordinate);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) { }

            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }
        });
    }


    private final GUIRenderer renderer;
    private final GameManager gameManager;

    private final CellClickSupport cellClickSupport;

    public void addCellClickListener(CellClickListener listener) {
        cellClickSupport.addCellClickListener(listener);
    }

    public void removeCellClickListener(CellClickListener listener) {
        cellClickSupport.removeCellClickListener(listener);
    }
}
