package lt.vilniustech.rulesets.italian;

import lt.vilniustech.Board;
import lt.vilniustech.Piece;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;

import java.util.function.Predicate;

public class ItalianCheckersFilters {

    public static Predicate<Move> getCaptureMoveFilter() {
        return move -> move instanceof CaptureMove;
    }

    public static Predicate<Move> getExcludeMenCaptureKingMoveFilter(Board board) {
        return move -> {
            CaptureMove captureMove = (CaptureMove) move;
            Piece fromPiece = board.getCell(captureMove.getFrom()).getPiece();
            Piece overPiece = board.getCell(captureMove.getOver()).getPiece();
            if(fromPiece.isKing()) return true;
            else return !overPiece.isKing();
        };
    }
}
