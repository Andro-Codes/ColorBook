package pavishka.coloring.book.Activities.Screens;


public class ScreenInfo {
    public int colorSeq;
    public int imageId;
    public String txt;
    int anInt;

    public ScreenInfo(int i, int i2, String str, int i3) {
        this.imageId = i2;
        this.txt = str;
        this.colorSeq = i;
        this.anInt = i3;
    }

    public int getColorId() {
        return this.anInt;
    }


    public String getTxt() {
        return this.txt;
    }

}
