package pavishka.coloring.book.Manager.PhotoEditor;

import android.graphics.Path;


public class PhotoLocation {
    String string;
    int anInt;
    int color;
    boolean is_Pattern;
    Path path;

    public PhotoLocation(Path path, int i, int i2, boolean z, String str) {
        this.path = path;
        this.color = i;
        this.anInt = i2;
        this.is_Pattern = z;
        this.string = str;
    }

    public int getBrush_width() {
        return this.anInt;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public Path getPath() {
        return this.path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getPattern_Name() {
        return this.string;
    }

    public boolean isIs_Pattern() {
        return this.is_Pattern;
    }

}
