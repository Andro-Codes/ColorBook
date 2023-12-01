package pavishka.coloring.book.Manager.ChooseColor;

import android.view.MotionEvent;


public class Toucher {
    long lastPassedEventTime;
    int minInterval;
    SetStater updatable;


    public Toucher(SetStater updatable) {
        this(16, updatable);
    }

    private Toucher(int i, SetStater updatable) {
        this.lastPassedEventTime = 0L;
        this.minInterval = i;
        this.updatable = updatable;
    }


    public void a(MotionEvent motionEvent) {
        if (this.updatable != null) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.lastPassedEventTime > this.minInterval) {
                this.lastPassedEventTime = currentTimeMillis;
                this.updatable.update(motionEvent);
            }
        }
    }
}
