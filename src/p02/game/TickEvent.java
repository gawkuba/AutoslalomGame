package p02.game;

// zdarzenie odpowiadające za upływ czasu w grze
public class TickEvent extends GameEvent {
    private final int tickCount;

    public TickEvent(int tickCount) {
        this.tickCount = tickCount;
    }

}