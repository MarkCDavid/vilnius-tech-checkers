package lt.vilniustech.guicheckers;

import lt.vilniustech.*;
import lt.vilniustech.manager.GameManager;
import lt.vilniustech.rulesets.english.EnglishCheckers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUIGame extends JPanel {

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        drawBoard(graphics);
    }

    public void drawBoard(Graphics2D graphics) {
        Board board = gameManager.getBoard();
        int boardSize = board.getRuleset().getBoardSize();
        int cellSize = graphics.getClipBounds().width / boardSize;
        for(int row = 0; row < boardSize; row++) {
            for(int column = 0; column < boardSize; column++) {
                int offset = (row + 1) % 2;
                boolean blackCell = (column + offset) % 2 == 0;
                graphics.setColor(blackCell ? Color.BLACK : Color.LIGHT_GRAY);
                graphics.fillRect(row * cellSize, column * cellSize, cellSize, cellSize);

                Coordinate coordinate = new Coordinate(row, column);
                Cell cell = board.getCell(coordinate);
                Piece piece = cell.getPiece();
                if(piece == null) continue;
                Side side = piece.getSide();
                if(side == Side.BLACK) {
                    if(piece.isKing()) drawPiece(graphics, blackKing, row, column, cellSize, MARGIN);
                    else drawPiece(graphics, blackPiece, row, column, cellSize, MARGIN);
                }
                else if (side == Side.WHITE) {
                    if(piece.isKing()) drawPiece(graphics, whiteKing, row, column, cellSize, MARGIN);
                    else drawPiece(graphics, whitePiece, row, column, cellSize, MARGIN);
                }
            }
        }
    }

    public void drawPiece(Graphics2D graphics, BufferedImage image, int row, int column, int cellSize, int margin) {
        graphics.drawImage(
                image,
                row * cellSize + margin / 2,
                column * cellSize + margin / 2,
                cellSize - margin,
                cellSize - margin,
                null
        );
    }




    public GUIGame(GameManager gameManager) throws IOException {
        this.gameManager = gameManager;
        blackPiece = ImageIO.read(new File("src/lt/vilniustech/guicheckers/img/BlackPiece.png"));
        blackKing = ImageIO.read(new File("src/lt/vilniustech/guicheckers/img/BlackKing.png"));
        whitePiece = ImageIO.read(new File("src/lt/vilniustech/guicheckers/img/WhitePiece.png"));
        whiteKing = ImageIO.read(new File("src/lt/vilniustech/guicheckers/img/WhiteKing.png"));
    }

    private final GameManager gameManager;

    private final BufferedImage blackPiece;
    private final BufferedImage blackKing;
    private final BufferedImage whitePiece;
    private final BufferedImage whiteKing;

    private static final int SCREEN_SIZE = 800;
    private static final int MARGIN = 20;

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Checkers");
        frame.add(new GUIGame(new GameManager(new EnglishCheckers())));
        frame.pack();
        Insets insets = frame.getInsets();
        frame.setSize(SCREEN_SIZE, SCREEN_SIZE + insets.top + insets.bottom);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }




}
