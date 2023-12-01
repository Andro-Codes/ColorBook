package pavishka.coloring.book.Activities.Screens;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;


public class ScreenDsaActivity {
    protected int anInt;
    protected int anInt1;
    protected Bitmap bitmap;
    protected boolean[] booleans;
    protected Queue<FloodFillRange> floodFillRanges;
    protected int[] ints;
    protected int[] ints1;
    protected int anInt2;


    public ScreenDsaActivity(int i, int i2) {
        this.bitmap = null;
        this.ints1 = new int[]{0, 0, 0};
        this.anInt2 = 0;
        this.anInt1 = 0;
        this.anInt = 0;
        this.ints = new int[]{0, 0, 0};
        this.anInt2 = ScreenConstable.drawWidth;
        this.anInt1 = ScreenConstable.drawHeight;
        setFillColor(i2);
        setTargetColor(i);
    }

    protected boolean lies(int i) {
        int[] iArr = ScreenConstable.expPixels;
        int i2 = (iArr[i] >>> 16) & 255;
        int i3 = (iArr[i] >>> 8) & 255;
        int i4 = iArr[i] & 255;
        int[] iArr2 = this.ints;
        int i5 = iArr2[0];
        int[] iArr3 = this.ints1;
        return i2 >= i5 - iArr3[0] && i2 <= iArr2[0] + iArr3[0] && i3 >= iArr2[1] - iArr3[1] && i3 <= iArr2[1] + iArr3[1] && i4 >= iArr2[2] - iArr3[2] && i4 <= iArr2[2] + iArr3[2];
    }

    protected void ittle(int i, int i2) {
        int i3 = (this.anInt2 * i2) + i;
        int i4 = i;
        do {
            if (ScreenConstable.TYPE_FILL != 0) {
                int i5 = this.anInt2;
                int i6 = (i3 % i5) % ScreenConstable.ppWidth;
                int i7 = (i3 / i5) % ScreenConstable.ppHeight;
                if (ScreenConstable.selectedPatternBitmap != null) {
                    if (i6 < 0 || i7 < 0) {
                        ScreenConstable.pixels[i3] = this.anInt;
                    } else {
                        ScreenConstable.pixels[i3] = ScreenConstable.selectedPatternBitmap.getPixel(i6, i7);
                    }
                }
            } else {
                ScreenConstable.pixels[i3] = this.anInt;
            }
            boolean[] zArr = this.booleans;
            zArr[i3] = true;
            i4--;
            i3--;
            if (i4 < 0 || zArr[i3]) {
                break;
            }
        } while (lies(i3));
        int i8 = i4 + 1;
        int i9 = (this.anInt2 * i2) + i;
        do {
            if (ScreenConstable.TYPE_FILL != 0) {
                int i10 = this.anInt2;
                int i11 = (i9 % i10) % ScreenConstable.ppWidth;
                int i12 = (i9 / i10) % ScreenConstable.ppHeight;
                if (ScreenConstable.selectedPatternBitmap != null) {
                    if (i11 < 0 || i12 < 0) {
                        ScreenConstable.pixels[i9] = this.anInt;
                    } else {
                        ScreenConstable.pixels[i9] = ScreenConstable.selectedPatternBitmap.getPixel(i11, i12);
                    }
                }
            } else {
                ScreenConstable.pixels[i9] = this.anInt;
            }
            boolean[] zArr2 = this.booleans;
            zArr2[i9] = true;
            i++;
            i9++;
            if (i >= this.anInt2 || zArr2[i9]) {
                break;
            }
        } while (lies(i9));
        this.floodFillRanges.offer(new FloodFillRange(this, i8, i - 1, i2));
    }

    protected void cloxsde() {
        this.booleans = new boolean[ScreenConstable.pixels.length];
        this.floodFillRanges = new LinkedList();
    }


    public void floodFill(int i, int i2) {
        System.err.println("boook:: floodFill");
        cloxsde();
        int i3 = (this.anInt2 * i2) + i;
        Log.d("dsds", "MyConstant.expPixels " + i3 + ": " + ScreenConstable.expPixels[i3]);
        int i4 = ScreenConstable.expPixels[(this.anInt2 * i2) + i];
        int[] iArr = this.ints;
        iArr[0] = (i4 >> 16) & 255;
        iArr[1] = (i4 >> 8) & 255;
        iArr[2] = i4 & 255;
        ittle(i, i2);
        while (this.floodFillRanges.size() > 0) {
            FloodFillRange remove = this.floodFillRanges.remove();
            int i5 = this.anInt2;
            int i6 = remove.f4Y;
            int i7 = remove.startX;
            int i8 = i6 + 1;
            int i9 = (i8 * i5) + i7;
            int i10 = i6 - 1;
            int i11 = (i5 * i10) + i7;
            while (i7 <= remove.endX) {
                if (remove.f4Y > 0 && !this.booleans[i11] && lies(i11)) {
                    ittle(i7, i10);
                }
                if (remove.f4Y < this.anInt1 - 1 && !this.booleans[i9] && lies(i9)) {
                    ittle(i7, i8);
                }
                i9++;
                i11++;
                i7++;
            }
        }
    }

    public int getFillColor() {
        return this.anInt;
    }

    public void setFillColor(int i) {
        this.anInt = i;
    }


    public void setTolerance(int i) {
        System.err.println("boook:: setTolerance");
        this.ints1 = new int[]{i, i, i};
    }

    public void setTargetColor(int i) {
        this.ints[0] = Color.red(i);
        this.ints[1] = Color.green(i);
        this.ints[2] = Color.blue(i);
    }


    public class FloodFillRange {
        public int endX;
        public int f4Y;
        public int startX;

        public FloodFillRange(ScreenDsaActivity queueLinearFloodFiller, int i, int i2, int i3) {
            this.startX = i;
            this.endX = i2;
            this.f4Y = i3;
        }
    }
}
