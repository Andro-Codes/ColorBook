package pavishka.coloring.book.Manager.ChooseColor;


public interface CMYKObservable {
    int getColor();

    void subscribe(CMYKObserver colorObserver);

    void unsubscribe(CMYKObserver colorObserver);
}
