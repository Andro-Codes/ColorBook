package pavishka.babyphone.rocketanimation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;


public class TempRocketData {
    public static int particleLife;
    public static int particleSpeed;
    public static int rocketHeight;
    public static int rocketSpeed;
    public static int rocketWidth;
    public static int screenWidth;
    public static int starHeight;
    public static int starNo;

    public static Bitmap getCombinedBitmap(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, new Matrix(), null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, (Paint) null);
        return createBitmap;
    }
}
