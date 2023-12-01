package pavishka.coloring.book.VectorArt;


public class Carding {
    int anInt;
    int anInt1;
    int anInt2;

    public Carding(int i, int i2, int i3) {
        this.anInt = i;
        this.anInt1 = i2;
        this.anInt2 = i3;
    }

    public int getColor() {
        return this.anInt2;
    }

    public int getColumn() {
        return this.anInt1;
    }

    public int getRow() {
        return this.anInt;
    }
}
