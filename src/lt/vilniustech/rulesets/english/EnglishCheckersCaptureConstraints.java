package lt.vilniustech.rulesets.english;

import lt.vilniustech.Board;
import lt.vilniustech.Side;
import lt.vilniustech.moves.CaptureMove;
import lt.vilniustech.moves.Move;
import lt.vilniustech.rulesets.CaptureConstraints;

import java.util.List;
import java.util.stream.Collectors;

public class EnglishCheckersCaptureConstraints implements CaptureConstraints {

    public EnglishCheckersCaptureConstraints(Board board, Move move) {
        this.board = board;
        this.move = move;
    }

    @Override
    public List<Move> filterMoves(List<Move> availableMoves) {
        if(multipleCaptures) {
            return availableMoves.stream()
                    .filter(move -> move instanceof CaptureMove && move.getFrom().equals(this.move.getTo()))
                    .collect(Collectors.toList());
        }
        return availableMoves;
    }

    @Override
    public Side getNextSide(Side side) {
        return multipleCaptures ? side : Side.opposite(side);
    }

    @Override
    public void setMultipleCaptures(boolean multipleCaptures) {
        this.multipleCaptures = multipleCaptures;
    }


    private boolean multipleCaptures;

    private final Board board;
    private final Move move;
}
