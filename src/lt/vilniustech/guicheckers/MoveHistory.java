package lt.vilniustech.guicheckers;

import lt.vilniustech.Board;
import lt.vilniustech.guicheckers.events.MoveHistoryChangeListener;
import lt.vilniustech.guicheckers.events.MoveHistoryChangeSupport;
import lt.vilniustech.moves.base.AbstractMove;

import javax.swing.*;
import java.util.List;

public class MoveHistory extends JList<AbstractMove> {

    public MoveHistory(Board board) {
        this.movesModel = new DefaultListModel<>();
        this.setModel(movesModel);
        this.moveHistoryChangeSupport = new MoveHistoryChangeSupport();

        addListSelectionListener(listSelectionEvent -> {

            int to = movesModel.indexOf(getSelectedValue());
            int from = movesModel.indexOf(getPreviousSelection());

            if(to > from) toTheFuture(board, to, from);
            if(to < from) toThePast(board, to, from);

            previousSelection = getSelectedValue();

            moveHistoryChangeSupport.emit(getSelectedValue());
        });
    }

    public boolean isPresent() {
        return movesModel.size() == 0 || movesModel.lastElement() == previousSelection;
    }

    public void toThePast(Board board, int to, int from) {
        for(int i = from; i > to; i--) {
            movesModel.get(i).revert(board);
        }
    }

    public void toTheFuture(Board board, int to, int from) {
        for(int i = from; i <= to; i++) {
            movesModel.get(i).apply(board);
        }
    }

    public void addMoves(List<AbstractMove> moves) {
        movesModel.clear();
        movesModel.addAll(moves);
        setPreviousSelection(moves.get(moves.size() - 1));
        moveHistoryChangeSupport.emit(getSelectedValue());
    }

    public void addMove(AbstractMove move) {
        movesModel.addElement(move);
        setPreviousSelection(move);
        moveHistoryChangeSupport.emit(getSelectedValue());
    }

    public void addHistoryChangeListener(MoveHistoryChangeListener listener) {
        moveHistoryChangeSupport.addHistoryChangeListener(listener);
    }

    public void removeHistoryChangeListener(MoveHistoryChangeListener listener) {
        moveHistoryChangeSupport.removeHistoryChangeListener(listener);
    }

    private AbstractMove getPreviousSelection() {
        if(previousSelection == null)
            previousSelection = movesModel.get(movesModel.getSize() - 1);
        return previousSelection;
    }

    private void setPreviousSelection(AbstractMove previousSelection) {
        this.previousSelection = previousSelection;
        setSelectedIndex(movesModel.indexOf(previousSelection));
    }

    private AbstractMove previousSelection;

    private final DefaultListModel<AbstractMove> movesModel;
    private final MoveHistoryChangeSupport moveHistoryChangeSupport;

}
