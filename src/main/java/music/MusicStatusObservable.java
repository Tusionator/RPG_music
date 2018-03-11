package music;

import java.util.Observable;

public class MusicStatusObservable extends Observable {
    public static final int NOTHING_TO_PLAY = 0;
    public static final int PLAYING = 1;
    public static final int PAUSED = 2;
    public static final int STOPPED = 3;

    private int status = 0;

    public void setStatus(int newStatus) {
        status = newStatus;
        setChanged();
        notifyObservers(newStatus);
    }

    public int getStatus() {
        return status;
    }

}
