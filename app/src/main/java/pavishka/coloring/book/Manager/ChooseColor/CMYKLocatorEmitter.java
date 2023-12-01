package pavishka.coloring.book.Manager.ChooseColor;

import java.util.ArrayList;
import java.util.List;


public class CMYKLocatorEmitter implements CMYKObservable {
    private int color;
    private List<CMYKObserver> observers = new ArrayList();


    public void nimas(int i, boolean z, boolean z2) {
        this.color = i;
        for (CMYKObserver colorObserver : this.observers) {
            colorObserver.onColor(i, z, z2);
        }
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void subscribe(CMYKObserver colorObserver) {
        if (colorObserver != null) {
            this.observers.add(colorObserver);
        }
    }

    @Override
    public void unsubscribe(CMYKObserver colorObserver) {
        if (colorObserver != null) {
            this.observers.remove(colorObserver);
        }
    }
}
