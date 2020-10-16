package backend.moves.finalization;

public class FinalizationArguments {

    public boolean isPromote() {
        return promote;
    }

    public void setPromote(boolean promote) {
        this.promote = promote;
    }

    public boolean isSwitchSide() {
        return switchSide;
    }

    public void setSwitchSide(boolean switchSide) {
        this.switchSide = switchSide;
    }

    private boolean promote;

    private boolean switchSide;
}
