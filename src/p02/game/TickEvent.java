package p02.game;

public class TickEvent extends GameEvent {
    private final int tickCount;

    public TickEvent(int tickCount) {
        this.tickCount = tickCount;
    }

    public int getTickCount() {
        return tickCount;
    }
}