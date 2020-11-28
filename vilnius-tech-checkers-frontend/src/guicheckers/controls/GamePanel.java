package guicheckers.controls;

import api.endpoints.GameService;
import backend.Coordinate;
import backend.manager.GameManager;
import backend.moves.base.Move;
import guicheckers.GUIRenderer;
import guicheckers.events.CellClickListener;
import guicheckers.events.CellClickSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private final GameService gameService;

    @Override
    public void paint(Graphics graphics) {
        renderer.setCellSize(graphics, gameService.getBoardSize());
        drawBoard((Graphics2D) graphics);
    }

    private void drawBoard(Graphics2D graphics) {
        renderer.drawCheckeredPattern(graphics, gameService.getBoardSize());

        if(drawHighlights)
            drawHighlights(graphics);

        renderer.drawPieces(graphics, gameService);
    }

    private void drawHighlights(Graphics2D graphics) {
        if (selectedMoves.isEmpty())
            renderer.drawAvailableMoves(graphics, gameService);
        else
            renderer.drawSelectedMoves(graphics, gameService, new api.dto.Coordinate(0, 0));
    }

    private boolean drawHighlights = true;

    public void setDrawHighlights(boolean drawHighlights) {
        this.drawHighlights = drawHighlights;
    }


    public void setSelectedMoves(List<Move> selectedMoves) {
        this.selectedMoves = selectedMoves;
    }

    public List<Move> getSelectedMoves() {
        return this.selectedMoves;
    }

    private List<Move> selectedMoves = new ArrayList<>();

    public GamePanel(GameService gameService) {
        this.gameService = gameService;
        this.renderer = new GUIRenderer();
        this.cellClickSupport = new CellClickSupport();


        addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
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

    private final CellClickSupport cellClickSupport;

    public void addCellClickListener(CellClickListener listener) {
        cellClickSupport.addCellClickListener(listener);
    }

    public void removeCellClickListener(CellClickListener listener) {
        cellClickSupport.removeCellClickListener(listener);
    }
}
