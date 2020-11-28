package backend.manager;

import backend.events.EventEmitter;
import backend.events.EventSubscriber;
import backend.events.SubscriptionSupport;
import backend.manager.events.event.SideSwitchEvent;
import backend.manager.events.event.WinnerDecidedEvent;
import backend.side.Side;

import java.util.List;

public class GameSides implements SubscriptionSupport {

    public GameSides(List<Side> playingSides) {
        this.eventEmitter = new EventEmitter();

        this.playingSides = playingSides;
        if(this.playingSides.size() > 0)
            setCurrentSide(this.playingSides.get(0));
    }

    public void setCurrentSide(Side side) {
        if (playingSides.contains(side)) {
            currentSide = side;
        }
    }

    public boolean isFinished() {
        return playingSides.size() < 2;
    }

    public Side getWinner() {
        if(playingSides.size() == 1)
            return playingSides.get(0);

        return this.playingSides.isEmpty() ? Side.DRAW : null;
    }

    public void draw() {
        playingSides.clear();
        emitWinnerDecided();
    }

    public void surrender(Side side) {
        playingSides.remove(side);
        emitWinnerDecided();
    }

    public void setWinner(Side side) {
        playingSides.clear();
        playingSides.add(side);
        emitWinnerDecided();
    }

    public List<Side> getPlayingSides() {
        return playingSides;
    }

    public Side getCurrentSide() {
        return currentSide;
    }

    public void next() {
        if(playingSides.size() < 1)
            return;

        var index = playingSides.indexOf(currentSide);
        var nextIndex = (index + 1) % playingSides.size();
        currentSide = playingSides.get(nextIndex);
    }

    private Side currentSide;
    private final List<Side> playingSides;

    private void emitWinnerDecided() {
        var winner = getWinner();
        if(winner == null)
            return;

        eventEmitter.emit(new WinnerDecidedEvent(winner));
    }

    private void emitSideSwitch() {
        eventEmitter.emit(new SideSwitchEvent(currentSide));
    }

    @Override
    public void subscribe(EventSubscriber<?> subscriber) {
        eventEmitter.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(EventSubscriber<?> subscriber) {
        eventEmitter.unsubscribe(subscriber);
    }

    private final EventEmitter eventEmitter;
}
